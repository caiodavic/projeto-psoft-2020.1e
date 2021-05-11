package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.model.agenda.Agenda;

import javax.servlet.ServletException;
import java.util.List;

public interface AgendaService {
    public void agendaVacinação(String headerToken, Agenda agenda) throws ServletException;
    public void listaHorariosDisponiveis();
    public List<Agenda> getAgendamentobyCpf(String headerToken,String cpf) throws ServletException;
}
