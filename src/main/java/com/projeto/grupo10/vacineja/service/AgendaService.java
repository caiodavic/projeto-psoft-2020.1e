package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.DTO.AgendaDTO;
import com.projeto.grupo10.vacineja.model.agenda.Agenda;

import javax.servlet.ServletException;
import java.util.List;

public interface AgendaService {
    public void agendaVacinação(String headerToken, AgendaDTO agendaDTO) throws ServletException;
    public List<Agenda> getAgendamentobyCpf(String headerToken) throws ServletException;
}
