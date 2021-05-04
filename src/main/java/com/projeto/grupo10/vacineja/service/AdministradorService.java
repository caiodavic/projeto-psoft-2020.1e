package com.projeto.grupo10.vacineja.service;

import javax.servlet.ServletException;
import java.util.ArrayList;

public interface AdministradorService {
    public ArrayList<String> getUsuariosNaoAutorizados(String headerToken) throws ServletException;
    public void autorizarCadastroFuncionario(String headerToken, String cpfFuncionario)  throws ServletException;
}
