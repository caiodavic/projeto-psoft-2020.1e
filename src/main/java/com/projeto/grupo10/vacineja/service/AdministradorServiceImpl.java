package com.projeto.grupo10.vacineja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.ArrayList;

@Service
public class AdministradorServiceImpl implements AdministradorService{
    private static final String CPF_ADM = "00000000000";

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
        return id.equals(CPF_ADM);
    }

    private boolean loginAsAdmin(String tipoLogin){ return tipoLogin.equals("Administrador");}

    /**
     * Verifica se o token passado é do administrador
     * @param headerToken eh o token do suposto adm
     * @throws ServletException excecao lançada se houver erro de autenticacao
     *
     */
    public void verificaLoginAdmin (String headerToken) throws ServletException{
        String token = "Bearer "+ headerToken;
        String id = jwtService.getCidadaoDoToken(token);
        String tipoLogin = jwtService.getTipoLogin(token);

        if(!isAdmin(id) || !loginAsAdmin(tipoLogin)) {
            throw new IllegalArgumentException("O usuário não tem permissão de Administrador!");
        }
    }
}
