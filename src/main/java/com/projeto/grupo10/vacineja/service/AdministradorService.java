package com.projeto.grupo10.vacineja.service;

import javax.servlet.ServletException;
import java.util.ArrayList;

public interface AdministradorService {
    ArrayList<String> getUsuariosNaoAutorizados(String headerToken) throws ServletException;
    void autorizarCadastroFuncionario(String headerToken, String cpfFuncionario)  throws ServletException;
    void verificaLoginAdmin (String headerToken) throws ServletException;
}
