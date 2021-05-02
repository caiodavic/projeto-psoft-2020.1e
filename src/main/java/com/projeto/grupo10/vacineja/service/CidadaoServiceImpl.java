package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.model.usuario.Cidadao;
import com.projeto.grupo10.vacineja.model.usuario.CidadaoDTO;
import com.projeto.grupo10.vacineja.model.usuario.CidadaoLoginDTO;
import com.projeto.grupo10.vacineja.model.usuario.FuncionarioCadastroDTO;
import com.projeto.grupo10.vacineja.model.usuario.FuncionarioGoverno;
import com.projeto.grupo10.vacineja.repository.CidadaoRepository;
import com.projeto.grupo10.vacineja.repository.FuncionarioGovernoRepository;
import com.projeto.grupo10.vacineja.util.ErroCidadao;
import com.projeto.grupo10.vacineja.util.ErroLogin;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CidadaoServiceImpl implements CidadaoService{

    @Autowired
    private CidadaoRepository cidadaoRepository;

    @Autowired
    private FuncionarioGovernoRepository funcionarioGovernoRepository;

    @Autowired
    private JWTService jwtService;


    @Override
    public Optional<Cidadao> getCidadaoById(String cpf) {
        return this.cidadaoRepository.findById(cpf);
    }

    public void salvarCidadao(Cidadao cidadao){
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

    private boolean isAdmin(String id){
        return id.equals("00000000000");
    }

    private boolean loginAsAdmin(String tipoLogin){ return tipoLogin.equals("Administrador");}

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

        if (!cidadaoOpt.isPresent()){
            throw new IllegalArgumentException();
        }

        Cidadao cidadao;
        cidadao = cidadaoOpt.get();
        cidadao.setFuncionarioGoverno(this.adicionarFuncionarioGoverno(cadastroFuncionario, id));

        this.salvarCidadao(cidadao);
    }

    public ArrayList<String> getUsuariosNaoAutorizados(String headerToken) throws ServletException {
        String id = jwtService.getCidadaoDoToken(headerToken);
        String tipoLogin = jwtService.getTipoLogin(headerToken);

        if(!isAdmin(id) || !loginAsAdmin(tipoLogin)) {
            throw new IllegalArgumentException();
        }

        ArrayList<String> funcionariosNaoAutorizados = new ArrayList<String>();
        for (Cidadao cidadao : this.cidadaoRepository.findAll()){
            if (cidadao.aguardandoAutorizacaoFuncionario()){
                funcionariosNaoAutorizados.add(cidadao.getCpf());
            }
        }
        return funcionariosNaoAutorizados;
    }

    public void autorizarCadastroFuncionario(String headerToken, String cpfFuncionario)throws ServletException{
        String id = jwtService.getCidadaoDoToken(headerToken);
        String tipoLogin = jwtService.getTipoLogin(headerToken);

        if(!isAdmin(id) || !loginAsAdmin(tipoLogin)) {
            throw new IllegalArgumentException();
        }

        Optional<Cidadao> cidadaoOpt = this.getCidadaoById(cpfFuncionario);

        if (!cidadaoOpt.isPresent() || !cidadaoOpt.get().aguardandoAutorizacaoFuncionario()){
            throw new IllegalArgumentException();
        }

        Cidadao cidadao = cidadaoOpt.get();
        cidadao.autorizaCadastroFuncionario();

        this.salvarCidadao(cidadao);
    }

    public String teste(String authorizationHeader) throws ServletException {
        String id = jwtService.getCidadaoDoToken(authorizationHeader);
        String tipoLogin = jwtService.getTipoLogin(authorizationHeader);

        if(!isAdmin(id) || !loginAsAdmin(tipoLogin))
            throw new ServletException("Usuario não é admin");

        return "Oie deu certo";
    }
    public Cidadao criaCidadao(CidadaoDTO cidadaoDTO) {
    	Cidadao cidadao = new Cidadao(cidadaoDTO.getNome(), cidadaoDTO.getCpf(), cidadaoDTO.getEndereco(),
    			cidadaoDTO.getCartaoSus(),cidadaoDTO.getEmail() ,cidadaoDTO.getData_nascimento(),cidadaoDTO.getTelefone(),
    			cidadaoDTO.getProfissoes(),cidadaoDTO.getComorbidades(), cidadaoDTO.getSenha());
    	return cidadao;
    	
    }
    public Optional<Cidadao> getCidadaoByCpf(String cpf){
    	return cidadaoRepository.findById(cpf);
    }
}
