package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.DTO.CidadaoDTO;
import com.projeto.grupo10.vacineja.DTO.CidadaoLoginDTO;
import com.projeto.grupo10.vacineja.DTO.CidadaoUpdateDTO;
import com.projeto.grupo10.vacineja.DTO.FuncionarioCadastroDTO;
import com.projeto.grupo10.vacineja.model.usuario.*;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.repository.CidadaoRepository;
import com.projeto.grupo10.vacineja.repository.FuncionarioGovernoRepository;
import com.projeto.grupo10.vacineja.state.Habilitado1Dose;
import com.projeto.grupo10.vacineja.state.Habilitado2Dose;
import com.projeto.grupo10.vacineja.state.Tomou1Dose;
import com.projeto.grupo10.vacineja.util.ErroEmail;
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

    @Autowired
    private CidadaoRepository cidadaoRepository;

    @Autowired
    private FuncionarioGovernoRepository funcionarioGovernoRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private LoteService loteService;

    @Override
    public Optional<Cidadao> getCidadaoById(String cpf) {
        return this.cidadaoRepository.findById(cpf);
    }

    private void salvarCidadao(Cidadao cidadao){
        this.cidadaoRepository.save(cidadao);
    }

    private FuncionarioGoverno adicionarFuncionarioGoverno(FuncionarioCadastroDTO funcionarioCadastroDTO, String cpfCidadao){
        FuncionarioGoverno funcionarioGoverno = new FuncionarioGoverno(cpfCidadao ,funcionarioCadastroDTO.getCargo(),
                funcionarioCadastroDTO.getLocalTrabalho());

        this.funcionarioGovernoRepository.save(funcionarioGoverno);

        return funcionarioGoverno;
    }

    @Override
    public boolean validaCidadaoSenha (CidadaoLoginDTO cidadaoLogin){
        boolean result = false;

        Optional<Cidadao> cidadao = this.getCidadaoById(cidadaoLogin.getCpfLogin());

        if (cidadao.isPresent()){
            result = cidadao.get().getSenha().equals(cidadaoLogin.getSenhaLogin());
        }

        return result;
    }

    public boolean validaLoginComoFuncionario (CidadaoLoginDTO cidadaoLogin){
        return cidadaoLogin.getTipoLogin().equals("Funcionario") && !this.isFuncionario(cidadaoLogin.getCpfLogin());
    }

    public boolean validaLoginComoAdministrador (CidadaoLoginDTO cidadaoLogin){
        return cidadaoLogin.getTipoLogin().equals("Administrador") && !this.isAdmin(cidadaoLogin.getCpfLogin());
    }

    private boolean isFuncionario(String id){
        boolean result = false;

        Optional<Cidadao> cidadao = this.getCidadaoById(id);

        if (cidadao.isPresent()){
            result = cidadao.get().isFuncionario();
        }

        return result;
    }

    public void cadastroFuncionario(String headerToken, FuncionarioCadastroDTO cadastroFuncionario) throws ServletException {
        String id = jwtService.getCidadaoDoToken(headerToken);

        Optional<Cidadao> cidadaoOpt = this.getCidadaoById(id);

        if (cidadaoOpt.isEmpty()){
            throw new IllegalArgumentException();
        }

        Cidadao cidadao;
        cidadao = cidadaoOpt.get();
        cidadao.setFuncionarioGoverno(this.adicionarFuncionarioGoverno(cadastroFuncionario, id));

        this.salvarCidadao(cidadao);
    }

    public ArrayList<String> getUsuariosNaoAutorizados() throws ServletException {
        ArrayList<String> funcionariosNaoAutorizados = new ArrayList<String>();
        for (Cidadao cidadao : this.cidadaoRepository.findAll()){
            if (cidadao.aguardandoAutorizacaoFuncionario()){
                funcionariosNaoAutorizados.add(cidadao.getCpf());
            }
        }
        return funcionariosNaoAutorizados;
    }

    public void autorizarCadastroFuncionario(String cpfFuncionario)throws ServletException{
        Optional<Cidadao> cidadaoOpt = this.getCidadaoById(cpfFuncionario);

        if (cidadaoOpt.isEmpty() || !cidadaoOpt.get().aguardandoAutorizacaoFuncionario()){
            throw new IllegalArgumentException();
        }

        Cidadao cidadao = cidadaoOpt.get();
        cidadao.autorizaCadastroFuncionario();

        this.salvarCidadao(cidadao);
    }

    private boolean isAdmin(String id){
        return id.equals("00000000000");
    }
    private boolean loginAsAdmin(String tipoLogin){ return tipoLogin.equals("Administrador");}
    public String teste(String authorizationHeader) throws ServletException {
        String id = jwtService.getCidadaoDoToken(authorizationHeader);
        String tipoLogin = jwtService.getTipoLogin(authorizationHeader);

        if(!isAdmin(id) || !loginAsAdmin(tipoLogin))
            throw new ServletException("Usuario não é admin");

        return "Oie deu certo";
    }


    public void verificaTokenFuncionario(String authHeader) throws ServletException {
        String id = jwtService.getCidadaoDoToken(authHeader);
        String tipoLogin = jwtService.getTipoLogin(authHeader);

        if(!isFuncionario(id) && tipoLogin.equals("Funcionario"))
            throw new ServletException("Usuario não é um Funcionário cadastrado!");

    }


    public void cadastraCidadao(CidadaoDTO cidadaoDTO) {
        Optional<Cidadao> cidadaoOpt = this.getCidadaoById(cidadaoDTO.getCpf());
        if(cidadaoOpt.isPresent()){
            throw new IllegalArgumentException("Cidadao cadastrado");
        }
        if(!ErroEmail.validarEmail(cidadaoDTO.getEmail())){
            throw new IllegalArgumentException("Email invalido");
        }
    	Cidadao cidadao = new Cidadao(cidadaoDTO.getNome(), cidadaoDTO.getCpf(), cidadaoDTO.getEndereco(),
    			cidadaoDTO.getCartaoSus(),cidadaoDTO.getEmail() ,cidadaoDTO.getData_nascimento(),cidadaoDTO.getTelefone(),
    			padronizaSetsDeString(cidadaoDTO.getProfissoes()),padronizaSetsDeString(cidadaoDTO.getComorbidades()), cidadaoDTO.getSenha());
    	this.salvarCidadao(cidadao);

    }

    @Override
    public Cidadao updateCidadao(String headerToken, CidadaoUpdateDTO cidadaoUpdateDTO, Cidadao cidadao) throws ServletException {

        String id = jwtService.getCidadaoDoToken(headerToken);

        Optional<Cidadao> cidadaoOpt = this.getCidadaoById(id);

        if (cidadaoOpt.isEmpty()){
            throw new IllegalArgumentException();
        }

        cidadao.setCartaoSus(Objects.nonNull(cidadaoUpdateDTO.getCartaoSus()) ? cidadaoUpdateDTO.getCartaoSus() : cidadao.getCartaoSus());
        cidadao.setComorbidades(Objects.nonNull(cidadaoUpdateDTO.getComorbidades()) ? padronizaSetsDeString(cidadaoUpdateDTO.getComorbidades()) : cidadao.getComorbidades());
        cidadao.setData_nascimento(Objects.nonNull(cidadaoUpdateDTO.getData_nascimento()) ? cidadaoUpdateDTO.getData_nascimento() : cidadao.getData_nascimento());
        cidadao.setEmail(Objects.nonNull(cidadaoUpdateDTO.getEmail()) ? cidadaoUpdateDTO.getEmail() : cidadao.getEmail());
        cidadao.setEndereco(Objects.nonNull(cidadaoUpdateDTO.getEndereco()) ? cidadaoUpdateDTO.getEndereco() : cidadao.getEndereco());
        cidadao.setSenha(Objects.nonNull(cidadaoUpdateDTO.getSenha()) ? cidadaoUpdateDTO.getSenha() : cidadao.getSenha());
        cidadao.setNome(Objects.nonNull(cidadaoUpdateDTO.getNome()) ? cidadaoUpdateDTO.getNome() : cidadao.getNome());
        cidadao.setTelefone(Objects.nonNull(cidadaoUpdateDTO.getTelefone()) ? cidadaoUpdateDTO.getTelefone() : cidadao.getTelefone());
        cidadao.setProfissoes(Objects.nonNull(cidadaoUpdateDTO.getProfissoes()) ? padronizaSetsDeString(cidadaoUpdateDTO.getProfissoes()) : cidadao.getProfissoes());
        this.salvarCidadao(cidadao);
        return cidadao;
    }

    /**
     * Metodo responsavel por ministrar uma dose da vacina ao cidadão que esteja apto a receber uma vacina,
     * a partir disso caso essa seja a segunda dose ou a vacina ministrada seja de dose unica o estado de
     * vacinação passa para finalizado e se for a primeira dose de uma vacina que tem duas doses o cidadão deve
     * ir para o estado de "tomou primeira dose"
     * @param headerToken - token do funcionario que deve ministrar a dose
     * @param cpfCidadao - Cpf do cidadão q deve receber a dose
     * @param vacina - O tipo da vacina que sera ministrada
     * @param dataVacina - Data em que foi realizada a vacinação
     * @throws ServletException
     */
    @Override
    public void ministraVacina(String headerToken, String cpfCidadao, Vacina vacina, Date dataVacina) throws ServletException {
        this.verificaTokenFuncionario(headerToken);

        Optional<Cidadao> cidadaoOpt = this.getCidadaoById(cpfCidadao);

        if (cidadaoOpt.isEmpty()){
            throw new IllegalArgumentException("Cidadão não cadastrado no serviço");
        }

        Cidadao cidadao = cidadaoOpt.get();

        this.recebeVacina(cidadao, vacina, dataVacina);
    }

    /**
     * Metodo responsavel por adicionar uma vacina no cartão de vacinação do cidadão e avaçar o estado de vacinação
     * @param cidadao - Cidadaõ que deve receber a dose da vacina
     * @param vacina - O tipo da vacina
     * @param dataVacina - A data em que a vacina foi aplicada
     */
    private void recebeVacina(Cidadao cidadao, Vacina vacina, Date dataVacina) {
        cidadao.receberVacina(vacina, dataVacina);
        this.cidadaoRepository.save(cidadao);
    }

    /**
     * Metodo que deve ser chamado por um funcionario para habilitar os cidadãos que podem ser habilitados
     * para segunda dose
     * @param headerToken - toke do funcionario do governo
     */
    @Override
    public void habilitarSegundaDose(String headerToken) throws ServletException {
        this.verificaTokenFuncionario(headerToken);

        List<Cidadao> cidadaos = this.cidadaoRepository.findAll();
        int qtdCidadaosQuePossoLiberar = this.getQtdDosesSemDependencia();

        for (Cidadao cidadao : cidadaos){
            if (cidadao.getSituacao() instanceof Tomou1Dose){
                if (qtdCidadaosQuePossoLiberar > 0){
                    cidadao.avancarSituacaoVacina();
                }else break;
            }
        }

    }

    /**
     * Metodo responsavel por calcular a quantidade de cidadãos estão aptos a receber a vacina, seja
     * primeira ou segunda dose
     * @return
     */
    private int getQtdHabilitados(){
        int result = 0;

        List<Cidadao> cidadaos = this.cidadaoRepository.findAll();

        for (Cidadao cidadao : cidadaos){
            if (cidadao.getSituacao() instanceof Habilitado1Dose || cidadao.getSituacao() instanceof Habilitado2Dose){
                result ++;
            }
        }
        return result;
    }

    /**
     * Metodo responsavél por calcular a diferença entre a quantidade de doses cadastradas no sistema, e a quantidade
     * de pessoas já habilitadas para receber alguma dose
     * @return
     */
    private int getQtdDosesSemDependencia(){
        return this.loteService.getQtdVacinaDisponivel() - this.getQtdHabilitados();
    }


    //TODO
    @Override
    public void atualizaQtdDoses(int qtdDoses) {
       return;
    }
}
