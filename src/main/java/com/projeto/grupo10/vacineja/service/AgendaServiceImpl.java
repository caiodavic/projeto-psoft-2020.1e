package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.model.agenda.Agenda;
import com.projeto.grupo10.vacineja.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.List;

@Service
public class AgendaServiceImpl implements AgendaService{
    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private JWTService jwtService;
    @Override
    public void agendaVacinação(String headerToken,Agenda agendaDTO) throws ServletException {
        String id = jwtService.getCidadaoDoToken(headerToken);

        Agenda agenda = new Agenda(agendaDTO.getCpf(),agendaDTO.getData(),agendaDTO.getHorario(),agendaDTO.getLocal());
        this.agendaRepository.save(agenda);
    }

    @Override
    public void listaHorariosDisponiveis() {

    }


    @Override
    public List<Agenda> getAgendamentobyCpf(String headerToken, String cpf) throws ServletException {
        String id = jwtService.getCidadaoDoToken(headerToken);
        return agendaRepository.findAllByCpf(cpf);
    }
}
