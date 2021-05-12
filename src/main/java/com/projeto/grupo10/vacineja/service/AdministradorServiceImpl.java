package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.DTO.RequisitoDTO;
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
    RequisitoService requisitoService;

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
        String id = jwtService.getCidadaoDoToken(headerToken);
        String tipoLogin = jwtService.getTipoLogin(headerToken);

        if(!isAdmin(id) || !loginAsAdmin(tipoLogin)) {
            throw new IllegalArgumentException("O usuário não tem permissão de Administrador!");
        }
    }

    /**
     * Adiciona uma nova comorbidade no sistema
     * @param requisito comorbidade a ser adicionada
     * @param headerToken token com o acesso atual
     * @throws ServletException caso o token esteja expirado ou nao pertença a um admin
     * @throws IllegalArgumentException caso a comorbidade ja existe no sistema
     */
    @Override
    public void adicionaNovaComorbidade(RequisitoDTO requisito, String headerToken) throws ServletException, IllegalArgumentException{
        this.verificaLoginAdmin(headerToken);
        requisitoService.setNovaComorbidade(requisito);
    }

    /**
     * Adiciona uma nova profissao no sistema
     * @param requisito profissao a ser adicionada
     * @param headerToken token com o acesso atual
     * @throws ServletException caso o token esteja expirado ou nao pertença a um admin
     * @throws IllegalArgumentException caso a profissao ja existe no sistema
     */
    @Override
    public void adicionaNovaProfissao(RequisitoDTO requisito, String headerToken) throws ServletException, IllegalArgumentException {
        this.verificaLoginAdmin(headerToken);
        requisitoService.setNovaProfissao(requisito);
    }
}
