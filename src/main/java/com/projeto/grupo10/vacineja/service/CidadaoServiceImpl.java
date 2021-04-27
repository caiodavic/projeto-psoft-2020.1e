package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.model.usuario.Cidadao;
import com.projeto.grupo10.vacineja.model.usuario.CidadaoLoginDTO;
import com.projeto.grupo10.vacineja.repository.CidadaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.Optional;

@Service
public class CidadaoServiceImpl implements CidadaoService{

    @Autowired
    private CidadaoRepository cidadaoRepository;

    @Autowired
    private JWTService jwtService;

    @Override
    public Optional<Cidadao> getCidadaoById(String cpf) {
        return this.cidadaoRepository.findById(cpf);
    }

    @Override
    public void setFuncionarioGoverno(String cargo, String localTrabalho) {

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

    private boolean isAdmin(String id){
        return id.equals("00000000000");
    }

    private boolean ehFuncionario(String id){
        boolean result = false;

        Optional<Cidadao> cidadao = this.getCidadaoById(id);

        if (cidadao.isPresent()){
            result = cidadao.get().isFuncionario();
        }

        return result;
    }

    private boolean ehCidadao(String id){
        boolean result = false;

        Optional<Cidadao> cidadao = this.getCidadaoById(id);

        if (cidadao.isPresent()){
            result = cidadao.get().isCidadao();
        }

        return result;
    }

    public String teste(String authorizationHeader) throws ServletException {
        String id = jwtService.getCidadaoDoToken(authorizationHeader);
        if(!isAdmin(id))
            throw new ServletException("Usuario não é admin");

        return "Oie deu certo";
    }


}
