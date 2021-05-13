package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.DTO.*;
import com.projeto.grupo10.vacineja.model.requisitos_vacina.Requisito;
import com.projeto.grupo10.vacineja.model.usuario.*;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.repository.CartaoVacinaRepository;
import com.projeto.grupo10.vacineja.repository.CidadaoRepository;
import com.projeto.grupo10.vacineja.repository.FuncionarioGovernoRepository;
import com.projeto.grupo10.vacineja.state.*;
import com.projeto.grupo10.vacineja.state.Habilitado1Dose;
import com.projeto.grupo10.vacineja.state.Habilitado2Dose;
import com.projeto.grupo10.vacineja.state.NaoHabilitado;
import com.projeto.grupo10.vacineja.state.Tomou1Dose;
import com.projeto.grupo10.vacineja.util.ErroCidadao;
import com.projeto.grupo10.vacineja.util.CalculaIdade;
import com.projeto.grupo10.vacineja.util.ErroEmail;
import com.projeto.grupo10.vacineja.util.email.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static com.projeto.grupo10.vacineja.util.PadronizaString.padronizaSetsDeString;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.*;


@Service
public class CidadaoServiceImpl implements CidadaoService {

    private static final String MENSSAGEM_EMAIL_ALERTA = "Ola! \nVocê esta apto para receber a %dª dose da vacina! " +
            "\nPor favor acesse o sistema Vacine Já para agendar sua vacinação";
    private static final String ASSUNTO_EMAIL_ALERTA = "Vacinação %s dose";

    @Autowired
    private CidadaoRepository cidadaoRepository;

    @Autowired
    private FuncionarioGovernoRepository funcionarioGovernoRepository;

    @Autowired
    private CartaoVacinaRepository cartaoVacinaRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private LoteService loteService;

    @Override
    public Optional<Cidadao> getCidadaoById(String cpf) {
        return this.cidadaoRepository.findById(cpf);
    }

    /**
     * Salva um cidadao no repository
     * @param cidadao
     * @author Holliver Costa
     */
    private void salvarCidadao(Cidadao cidadao){
        this.cidadaoRepository.save(cidadao);
    }

    private FuncionarioGoverno adicionarFuncionarioGoverno(FuncionarioCadastroDTO funcionarioCadastroDTO, String cpfCidadao) {
        FuncionarioGoverno funcionarioGoverno = new FuncionarioGoverno(cpfCidadao, funcionarioCadastroDTO.getCargo(),
                funcionarioCadastroDTO.getLocalTrabalho());

        this.funcionarioGovernoRepository.save(funcionarioGoverno);

        return funcionarioGoverno;
    }


    @Override
    public boolean validaCidadaoSenha(CidadaoLoginDTO cidadaoLogin) {
        boolean result = false;

        Optional<Cidadao> cidadao = this.getCidadaoById(cidadaoLogin.getCpfLogin());

        if (cidadao.isPresent()) {
            result = cidadao.get().getSenha().equals(cidadaoLogin.getSenhaLogin());
        }

        return result;
    }

    public boolean validaLoginComoFuncionario(CidadaoLoginDTO cidadaoLogin) {
        return cidadaoLogin.getTipoLogin().equals("Funcionario") && !this.isFuncionario(cidadaoLogin.getCpfLogin());
    }

    public boolean validaLoginComoAdministrador(CidadaoLoginDTO cidadaoLogin) {
        return cidadaoLogin.getTipoLogin().equals("Administrador") && !this.isAdmin(cidadaoLogin.getCpfLogin());
    }

    private boolean isFuncionario(String id) {
        boolean result = false;

        Optional<Cidadao> cidadao = this.getCidadaoById(id);

        if (cidadao.isPresent()) {
            result = cidadao.get().isFuncionario();
        }

        return result;
    }

    /**
     * Metodo responsavel pelo auto cadastro de um funcionario, ao final desse metodo o funcionario se encontrar
     * num estado de aguardando aprovação do administrador
     *
     * @param headerToken         - Token do cidadão que esta realizando seu cadastro de funcionario
     * @param cadastroFuncionario -Um objeto com as seguintes informações -cpf do cidadão, local de trabalho e a função-
     * @throws ServletException
     * @author Caetano Albuquerque
     */
    public void cadastroFuncionario(String headerToken, FuncionarioCadastroDTO cadastroFuncionario) throws ServletException {
        String id = jwtService.getCidadaoDoToken(headerToken);

        Optional<Cidadao> cidadaoOpt = this.getCidadaoById(id);

        if (cidadaoOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario não encontrado");
        }

        Cidadao cidadao;
        cidadao = cidadaoOpt.get();
        cidadao.setFuncionarioGoverno(this.adicionarFuncionarioGoverno(cadastroFuncionario, id));
        this.salvarCidadao(cidadao);
    }

    /**
     * Metodo responsavel por listar todos os cidadãos que estão aguardando a autorização do administrador para
     * virarem funcionarios
     *
     * @return - Um ArrayList com todos os funcionarios com o cadastro pendente
     * @throws ServletException
     * @author Caetano Albuquerque
     */
    public ArrayList<String> getUsuariosNaoAutorizados() throws ServletException {
        ArrayList<String> funcionariosNaoAutorizados = new ArrayList<String>();
        for (Cidadao cidadao : this.cidadaoRepository.findAll()) {
            if (cidadao.aguardandoAutorizacaoFuncionario()) {
                funcionariosNaoAutorizados.add(cidadao.getCpf());
            }
        }
        return funcionariosNaoAutorizados;
    }

    /**
     * Metodo responsavel por autorixar o cadastro de um funcionario
     *
     * @param cpfFuncionario - Cpf do funcionario que tera seu cadastro aprovado
     * @throws ServletException
     * @author Caetano Albuquerque
     */
    public void autorizarCadastroFuncionario(String cpfFuncionario) throws ServletException {
        Optional<Cidadao> cidadaoOpt = this.getCidadaoById(cpfFuncionario);

        if (cidadaoOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario não cadastrado");
        }if(!cidadaoOpt.get().aguardandoAutorizacaoFuncionario()){
            throw new IllegalArgumentException("Usuario não é um funcionario não autorizado");
        }

        Cidadao cidadao = cidadaoOpt.get();
        cidadao.autorizaCadastroFuncionario();

        this.funcionarioGovernoRepository.save(cidadao.getFuncionarioGoverno());
    }

    private boolean isAdmin(String id) {
        return id.equals("00000000000");
    }

    private boolean loginAsAdmin(String tipoLogin) {
        return tipoLogin.equals("Administrador");
    }

    public String teste(String authorizationHeader) throws ServletException {
        String id = jwtService.getCidadaoDoToken(authorizationHeader);
        String tipoLogin = jwtService.getTipoLogin(authorizationHeader);

        if (!isAdmin(id) || !loginAsAdmin(tipoLogin))
            throw new ServletException("Usuario não é admin");

        return "Oie deu certo";
    }


    public void verificaTokenFuncionario(String authHeader) throws ServletException {
        String id = jwtService.getCidadaoDoToken(authHeader);
        String tipoLogin = jwtService.getTipoLogin(authHeader);

        if (!isFuncionario(id) && tipoLogin.equals("Funcionario"))
            throw new ServletException("Usuario não é um Funcionário cadastrado!");

    }

    /**
     * Metodo que cadastra um cidadao a partir de um cidadao DTO, verifica se o cidadao ja esta cadastrado e se o email colocoado é valido
     * @param cidadaoDTO
     * @author Holliver Costa
     * @return
     */

    public Cidadao cadastraCidadao(CidadaoDTO cidadaoDTO) throws IllegalArgumentException {

        analisaEntradasDoCadastraCidadao(cidadaoDTO);

        CartaoVacina cartaoVacina = new CartaoVacina(cidadaoDTO.getCartaoSus());
        this.cartaoVacinaRepository.save(cartaoVacina);
      
    	Cidadao cidadao = new Cidadao(cidadaoDTO.getNome(), cidadaoDTO.getCpf(), cidadaoDTO.getEndereco(),
    			cidadaoDTO.getCartaoSus(),cidadaoDTO.getEmail() ,cidadaoDTO.getData_nascimento(),cidadaoDTO.getTelefone(),
    			padronizaSetsDeString(cidadaoDTO.getProfissoes()),padronizaSetsDeString(cidadaoDTO.getComorbidades()), cidadaoDTO.getSenha(), cartaoVacina);
    	this.salvarCidadao(cidadao);

    	return this.cidadaoRepository.findById(cidadaoDTO.getCpf()).get();

    }

    /**
     * Metodo privado responsavel por verificar se os atributos de email, cartao do sus, data de nascimento e senha
     * são valores validos. Tambem verifica se ja existe um cpf igual ao que deseja cadastrar.
     * @param cidadaoDTO - DTO contendo as novas informacoes desejadas para o usuario
     * @throws ServletException
     */
    private void analisaEntradasDoCadastraCidadao(CidadaoDTO cidadaoDTO) throws IllegalArgumentException {
        Optional<Cidadao> cidadaoOpt = this.getCidadaoById(cidadaoDTO.getCpf());
        if (cidadaoOpt.isPresent()) {
            throw new IllegalArgumentException("Cidadao cadastrado");
        }
        if (!ErroEmail.validarEmail(cidadaoDTO.getEmail())) {
            throw new IllegalArgumentException("Email invalido");
        }

        if (ErroCidadao.CPFInvalido(cidadaoDTO.getCpf())) {
            throw new IllegalArgumentException("Não é possivel cadastrar um Cidadao com esse cpf");
        }
        if (ErroCidadao.cartaoSUSInvalido(cidadaoDTO.getCartaoSus())) {
            throw new IllegalArgumentException("Não é possivel cadastrar um Cidadao com esse numero de cartao do SUS");
        }
        if (ErroCidadao.senhaInvalida(cidadaoDTO.getSenha())) {
            throw new IllegalArgumentException("Não é possivel cadastrar um Cidadao com essa senha");
        }
        if (ErroCidadao.dataInvalida(cidadaoDTO.getData_nascimento())) {
            throw new IllegalArgumentException("Não é possivel cadastrar um Cidadao com essa data de nascimento");
        }

        CartaoVacina cartaoVacina = new CartaoVacina(cidadaoDTO.getCartaoSus());
        this.cartaoVacinaRepository.save(cartaoVacina);

        Cidadao cidadao = new Cidadao(cidadaoDTO.getNome(), cidadaoDTO.getCpf(), cidadaoDTO.getEndereco(),
                cidadaoDTO.getCartaoSus(), cidadaoDTO.getEmail(), cidadaoDTO.getData_nascimento(), cidadaoDTO.getTelefone(),
                padronizaSetsDeString(cidadaoDTO.getProfissoes()), padronizaSetsDeString(cidadaoDTO.getComorbidades()), cidadaoDTO.getSenha(), cartaoVacina);
        this.salvarCidadao(cidadao);

    }

    /**
     * Metodo responsavel por alterar os atributos de Cidadao. Verificas-se quais informacoes deseja-se mudar, de acordo
     * com as informacoes que vem do DTO.
     *
     * @param headerToken      - token do Cidadao que tera seus dados alterardos
     * @param cidadaoUpdateDTO - DTO contendo as novas informacoes desejadas para o usuario
     * @throws ServletException
     */
    @Override
    public Cidadao updateCidadao(String headerToken, CidadaoUpdateDTO cidadaoUpdateDTO)  throws ServletException, IllegalArgumentException{

        Optional<Cidadao> cidadao = getCidadaoById(jwtService.getCidadaoDoToken(headerToken));
        analisaEntradasDoUpdateCidadao(headerToken, cidadaoUpdateDTO, cidadao.get());

        cidadao.get().setComorbidades(Objects.nonNull(cidadaoUpdateDTO.getComorbidades()) ? padronizaSetsDeString(cidadaoUpdateDTO.getComorbidades()) : cidadao.get().getComorbidades());
        cidadao.get().setEmail(Objects.nonNull(cidadaoUpdateDTO.getEmail()) ? cidadaoUpdateDTO.getEmail() : cidadao.get().getEmail());
        cidadao.get().setEndereco(Objects.nonNull(cidadaoUpdateDTO.getEndereco()) ? cidadaoUpdateDTO.getEndereco() : cidadao.get().getEndereco());
        cidadao.get().setSenha(Objects.nonNull(cidadaoUpdateDTO.getSenha()) ? cidadaoUpdateDTO.getSenha() : cidadao.get().getSenha());
        cidadao.get().setNome(Objects.nonNull(cidadaoUpdateDTO.getNome()) ? cidadaoUpdateDTO.getNome() : cidadao.get().getNome());
        cidadao.get().setTelefone(Objects.nonNull(cidadaoUpdateDTO.getTelefone()) ? cidadaoUpdateDTO.getTelefone() : cidadao.get().getTelefone());
        cidadao.get().setProfissoes(Objects.nonNull(cidadaoUpdateDTO.getProfissoes()) ? padronizaSetsDeString(cidadaoUpdateDTO.getProfissoes()) : cidadao.get().getProfissoes());

        this.salvarCidadao(cidadao.get());
        return cidadao.get();
    }

    /**
     * Metodo privado responsavel por verificar se os novos atributos de email, cartao do sus, data de nascimento e senha
     * são valores validos.
     * @param headerToken - token do Cidadao que tera seus dados alterardos
     * @param cidadaoUpdateDTO - DTO contendo as novas informacoes desejadas para o usuario
     * @throws ServletException
     */
    private void analisaEntradasDoUpdateCidadao(String headerToken, CidadaoUpdateDTO cidadaoUpdateDTO, Cidadao cidadao) throws ServletException {
        String id = jwtService.getCidadaoDoToken(headerToken);
        Optional<Cidadao> cidadaoOpt = this.getCidadaoById(id);
        if (cidadaoOpt.isEmpty()){
            throw new IllegalArgumentException("Cidadao não existe");
        }
        if (Objects.nonNull(cidadaoUpdateDTO.getEmail())) {
            if(!ErroEmail.validarEmail(cidadaoUpdateDTO.getEmail())){
                throw new IllegalArgumentException("Novo Email invalido");
            }
        }
        if (Objects.nonNull(cidadaoUpdateDTO.getSenha())){
            if (ErroCidadao.senhaInvalida(cidadaoUpdateDTO.getSenha())) {
                throw new IllegalArgumentException("Não é possivel cadastrar um Cidadao com essa senha");
            }
        }


    }

    /**
     * Metodo responsavel por adicionar uma vacina no cartão de vacinação do cidadão e avaçar o estado de vacinação
     *
     * @param cpfCidadao - Cpf do Cidadão que deve receber a dose da vacina
     * @param vacina     - O tipo da vacina
     * @param dataVacina - A data em que a vacina foi aplicada
     * @author Caetano Albuquerque
     */
    public void recebeVacina(String cpfCidadao, Vacina vacina, Date dataVacina) {
        Optional<Cidadao> cidadaoOpt = this.getCidadaoById(cpfCidadao);

        if (cidadaoOpt.isEmpty()) {
            throw new IllegalArgumentException("Cidadão não cadastrado no sistema");
        }

        Cidadao cidadao = cidadaoOpt.get();

        cidadao.receberVacina(vacina, dataVacina);
        this.cartaoVacinaRepository.save(cidadao.getCartaoVacina());
    }

    /**
     * Metodo que deve ser chamado por um funcionario para habilitar os cidadãos que podem ser habilitados
     * para segunda dose
     *
     * @param headerToken - toke do funcionario do governo
     * @author Caetano Albuquerque
     */
    @Override
    public void habilitarSegundaDose(String headerToken) throws ServletException {
        this.verificaTokenFuncionario(headerToken);

        List<Cidadao> cidadaos = this.cidadaoRepository.findAll();
        int qtdCidadaosQuePossoLiberar = this.getQtdDosesSemDependencia();
        String emails = "";

        for (Cidadao cidadao : cidadaos) {
            if (cidadao.getSituacao() instanceof Tomou1Dose && cidadao.getDataPrevistaSegundaDose().before(new Date())) {
                if (qtdCidadaosQuePossoLiberar > 0 && this.loteService.existeLoteDaVacina(cidadao.getTipoVacina())) {
                    cidadao.avancarSituacaoVacina();
                    this.cartaoVacinaRepository.save(cidadao.getCartaoVacina());
                    emails += (cidadao.getEmail() + ", ");
                    qtdCidadaosQuePossoLiberar--;
                } else break;
            }
        }
        if (!emails.equals("")) {
            emails = emails.substring(0, emails.length() - 2);
            Email.enviarAlertaVacinacao(String.format(ASSUNTO_EMAIL_ALERTA, "segunda"),
                    String.format(MENSSAGEM_EMAIL_ALERTA, 2), emails);
        }
    }

    /**
     * Metodo responsavel por calcular a quantidade de cidadãos estão aptos a receber a vacina, seja
     * primeira ou segunda dose
     *
     * @return a quantidade total de pessoas habilitadas
     * @author Caetano Albuquerque
     */
    private int getQtdHabilitados() {
        int result = 0;

        List<Cidadao> cidadaos = this.cidadaoRepository.findAll();

        for (Cidadao cidadao : cidadaos) {
            if (cidadao.getSituacao() instanceof Habilitado1Dose || cidadao.getSituacao() instanceof Habilitado2Dose) {
                result++;
            }
        }


        return result;
    }

    /**
     * Metodo responsavél por calcular a diferença entre a quantidade de doses cadastradas no sistema, e a quantidade
     * de pessoas já habilitadas para receber alguma dose
     *
     * @return a diferença entre a quantidade de doses e a quantidade de pessoas já habilitadas
     * @author Caetano Albuquerque
     */
    private int getQtdDosesSemDependencia() {
        return this.loteService.getQtdVacinaDisponivel() - this.getQtdHabilitados();
    }


    /**
     * Método que verifica se temos doses suficientes para todas as pessoas mais velhas do que a idade a ser habilitada
     *
     * @param requisito idada a ser habilitada
     * @return true caso tenhamos mais doses do que pessoas a serem habilitadas, false caso contrario
     * @author Caio Silva
     */
    public boolean podeAlterarIdade(int idade) {
        List<Cidadao> cidadaos = this.cidadaoRepository.findAll();
        int contProvaveisHabilitados = 0;

        for (Cidadao cidadao : cidadaos) {
            Integer idadeCidadao = CalculaIdade.idade(cidadao.getData_nascimento());
            if (idadeCidadao >= idade && cidadao.getSituacao() instanceof NaoHabilitado)
                contProvaveisHabilitados++;
        }

        return this.getQtdDosesSemDependencia() >= contProvaveisHabilitados + this.getQtdHabilitados();
    }

    /**
     * Método que verifica se temos doses suficientes para todas as pessoas que tenham o requisito que o funcionário quer habilitar
     *
     * @param requisito requisito a ser habilitado
     * @return true caso tenhamos mais doses do que pessoas a serem habilitadas, false caso contrario
     * @author Caio Silva
     */
    public boolean podeHabilitarRequisito(RequisitoDTO requisito) {
        String requisitoPodeHabilitar = requisito.getRequisito();
        Integer idadeRequisito = requisito.getIdade();

        List<Cidadao> cidadaos = this.cidadaoRepository.findAll();
        int contProvaveisHabilitados = 0;

        for (Cidadao cidadao : cidadaos) {
            Integer idadeCidadao = CalculaIdade.idade(cidadao.getData_nascimento());
            Set<String> profissoesCidadao = cidadao.getProfissoes();
            Set<String> comorbidadesCidadao = cidadao.getComorbidades();

            if (profissoesCidadao.contains(requisitoPodeHabilitar) || comorbidadesCidadao.contains(comorbidadesCidadao)) {
                if (idadeCidadao >= idadeRequisito && cidadao.getSituacao() instanceof NaoHabilitado)
                    contProvaveisHabilitados++;
            }
        }
        return this.getQtdDosesSemDependencia() >= contProvaveisHabilitados + this.getQtdHabilitados();
    }

    /**
     * Metodo que pega o estagio de vacinação do cidadao
     * @param cpf
     * @return retorna a situacao do cidadao
     * @author Holliver Costa
     */
    @Override
    public Situacao getSituacao(String cpf){
        return this.getCidadaoById(cpf).get().getSituacao();
    }

     /* Método que habilita cidadaos utilizando a idade como requisito
     *
     * @param requisito idade a ser utilizada como requisito
     * @author Caio Silva
     */
    public void habilitaPelaIdade(Requisito requisito) {
        Integer idadeRequisito = requisito.getIdade();
        List<Cidadao> cidadaos = this.cidadaoRepository.findAll();

        String emails = "";

        for(Cidadao cidadao: cidadaos){
            Integer idadeCidadao = CalculaIdade.idade(cidadao.getData_nascimento());
            if(idadeCidadao >= idadeRequisito && cidadao.getSituacao() instanceof NaoHabilitado) {
                cidadao.avancarSituacaoVacina();
                cartaoVacinaRepository.save(cidadao.getCartaoVacina());
                emails += (cidadao.getEmail() + ", ");
            }
        }
        if (!emails.equals("")) {
            emails = emails.substring(0, emails.length() -2);
            Email.enviarAlertaVacinacao(String.format(ASSUNTO_EMAIL_ALERTA, "primeira"),
                    String.format(MENSSAGEM_EMAIL_ALERTA, 1), emails);
        }
    }

    /**
     * Método que habilita cidadaos utilizando o requisito no parametro como requisito
     *
     * @param requisito requisito que irá habilitar cidadaos
     * @author Caio Silva
     */
    public void habilitaPorRequisito(Requisito requisito) {
        String requisitoPodeHabilitar = requisito.getRequisito();
        Integer idadeRequisito = requisito.getIdade();

        String emails = "";

        List<Cidadao> cidadaos = this.cidadaoRepository.findAll();

        for (Cidadao cidadao : cidadaos) {
            Integer idadeCidadao = CalculaIdade.idade(cidadao.getData_nascimento());
            Set<String> profissoesCidadao = cidadao.getProfissoes();
            Set<String> comorbidadesCidadao = cidadao.getComorbidades();

            if (profissoesCidadao.contains(requisitoPodeHabilitar) || comorbidadesCidadao.contains(comorbidadesCidadao)) {
                if (idadeCidadao >= idadeRequisito && cidadao.getSituacao() instanceof NaoHabilitado) {
                    cidadao.avancarSituacaoVacina();
                    cartaoVacinaRepository.save(cidadao.getCartaoVacina());
                    emails += (cidadao.getEmail() + ", ");
                }
            }
        }
        if (!emails.equals("")) {
            emails = emails.substring(0, emails.length() -2);
            Email.enviarAlertaVacinacao(String.format(ASSUNTO_EMAIL_ALERTA, "primeira"),
                    String.format(MENSSAGEM_EMAIL_ALERTA, 1), emails);
        }
    }

    /**
     * Isso n faz a msm coisa q o outro lá em cima n ?
     */
    /**
     * Atualiza automaticamente Cidadãos aguardando a chegada de SEGUNDA DOSE.
     * TODO tirar os souts
     */
    private void habilitarAutoSegundaDoseCidadaos() {
        int qtdCidadaosQuePossoLiberar = this.getQtdDosesSemDependencia();

        if (qtdCidadaosQuePossoLiberar <= 0) {
            System.out.println("nenhum novo cidadao liberado");
            return;
        }
        List<Cidadao> cidadaos = this.cidadaoRepository.findAll();

        System.out.printf("%d cidadaos liberados:\n", qtdCidadaosQuePossoLiberar);

        for (Cidadao cid : cidadaos) {
            System.out.println(cid.getNome());
            if (cid.getSituacao() instanceof Tomou1Dose) {
                System.out.println(cid.getSituacao());
                cid.avancarSituacaoVacina();
                System.out.println(cid.getSituacao());
                if (qtdCidadaosQuePossoLiberar-- == 0) break;
            }
        }
    }

    @Override
    public void atualizaQtdDoses() {
        habilitarAutoSegundaDoseCidadaos();
    }

    /**
     * Metodo que deve retornar a um determinado cidadao o seu estado na vacinação
     *
     * @param headerToken - token de login do cidadao
     * @return o estado de vacinação do cidadao
     * @throws ServletException
     * @author Caetano Albuquerque
     */
    @Override
    public String getEstadoVacinacao(String headerToken) throws ServletException {
        String id = jwtService.getCidadaoDoToken(headerToken);

        Optional<Cidadao> cidadaoOpt = this.getCidadaoById(id);

        if (cidadaoOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario não encontrado");
        }

        Cidadao cidadao = cidadaoOpt.get();
        return cidadao.getSituacao().toString();
    }

    /**
     * conta quantos cidadaos temos de idade igual ou maior do que a passada por parametro que não estao habilitados
     *
     * @param idade idade usada como parametro para o calculo
     * @return quantidade de cidadaos com idade maior ou igual a idade passada por parametro que não estão habilitados
     * @author Caio Silva
     */
    public int contaCidadaosAcimaIdade(int idade) {
        int qtdCidadaosMaisVelhos = 0;
        List<Cidadao> cidadaos = this.cidadaoRepository.findAll();

        for (Cidadao cidadao : cidadaos) {
            Integer idadeCidadao = CalculaIdade.idade(cidadao.getData_nascimento());
            if (idadeCidadao >= idade && cidadao.getSituacao() instanceof NaoHabilitado)
                qtdCidadaosMaisVelhos++;
        }
        return qtdCidadaosMaisVelhos;
    }

    /**
     * conta quantos cidadãos tem no sistema ainda não habilitados que atendem a esse requisito
     *
     * @param requisito requisito usado como parametro
     * @return quantidade de cidadaos que atendem ao requisito inserido e ainda não estão habilitados
     * @author
     */
    @Override
    public int contaCidadaosAtendeRequisito(RequisitoDTO requisito) {
        int qtdCidadaosMaisVelhos = 0;
        String requisitoPodeHabilitar = requisito.getRequisito();
        Integer idadeRequisito = requisito.getIdade();

        List<Cidadao> cidadaos = this.cidadaoRepository.findAll();

        for (Cidadao cidadao : cidadaos) {
            Integer idadeCidadao = CalculaIdade.idade(cidadao.getData_nascimento());
            Set<String> profissoesCidadao = cidadao.getProfissoes();
            Set<String> comorbidadesCidadao = cidadao.getComorbidades();

            if (profissoesCidadao.contains(requisitoPodeHabilitar) || comorbidadesCidadao.contains(requisitoPodeHabilitar)) {
                if (idadeCidadao >= idadeRequisito && cidadao.getSituacao() instanceof NaoHabilitado)
                    qtdCidadaosMaisVelhos++;
            }
        }
        return qtdCidadaosMaisVelhos;
    }

    @Override
    public List<String> listarCidadaosHabilitados() {
        List<String> cidadaosHabilitados = new ArrayList<>();
        List<Cidadao> cidadaos = this.cidadaoRepository.findAll();

        for (Cidadao cidadao : cidadaos){
            if (cidadao.getSituacao() instanceof Habilitado1Dose || cidadao.getSituacao() instanceof Habilitado2Dose){
                cidadaosHabilitados.add(cidadao.getCpf());
            }
        }

        return cidadaosHabilitados;
    }

}
