package com.projeto.grupo10.vacineja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.ArrayList;

@Service
public class AdministradorServiceImpl implements AdministradorService{

    @Autowired
    CidadaoService cidadaoService;

    @Autowired
    private JWTService jwtService;

    public ArrayList<String> getUsuariosNaoAutorizados(String headerToken) throws ServletException{
        verificaLoginAdmin(headerToken);

        return this.cidadaoService.getUsuariosNaoAutorizados();
    }

    public void autorizarCadastroFuncionario(String headerToken, String cpfFuncionario) throws ServletException{
        verificaLoginAdmin(headerToken);

        this.cidadaoService.autorizarCadastroFuncionario(cpfFuncionario);
    }

    private boolean isAdmin(String id){
        return id.equals("00000000000");
    }

    private boolean loginAsAdmin(String tipoLogin){ return tipoLogin.equals("Administrador");}

    private void verificaLoginAdmin (String headerToken) throws ServletException{
        String id = jwtService.getCidadaoDoToken(headerToken);
        String tipoLogin = jwtService.getTipoLogin(headerToken);

        if(!isAdmin(id) || !loginAsAdmin(tipoLogin)) {
            throw new IllegalArgumentException();
        }
    }
}
