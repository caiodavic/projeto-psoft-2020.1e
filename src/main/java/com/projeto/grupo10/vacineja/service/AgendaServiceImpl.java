package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.DTO.AgendaDTO;
import com.projeto.grupo10.vacineja.model.agenda.Agenda;
import com.projeto.grupo10.vacineja.repository.AgendaRepository;
import com.projeto.grupo10.vacineja.state.Habilitado1Dose;
import com.projeto.grupo10.vacineja.state.Habilitado2Dose;
import com.projeto.grupo10.vacineja.state.Situacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.time.LocalDate;
import java.util.List;

@Service
public class AgendaServiceImpl implements AgendaService{
    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private CidadaoService cidadaoService;
    @Autowired
    private LoteService loteService;
    @Autowired
    private JWTService jwtService;

    /**
     * Agenda uma vacinação,para agendar uma vacinação o cidadao precisa estar habilitado para alguma dose da vacina
     * @param headerToken
     * @param agendaDTO
     * @throws ServletException
     * @author Holliver Costa
     */
    @Override
    public Agenda agendaVacinação(String headerToken, AgendaDTO agendaDTO) throws ServletException {
        String id = jwtService.getCidadaoDoToken(headerToken);
        String cpf_cidadao = jwtService.getCidadaoDoToken(headerToken);
        String retorno = "";
        if(!cidadaoService.getCidadaoById(cpf_cidadao).isPresent())
            throw new IllegalArgumentException("Cidadao nao cadastrado");
        if(agendaDTO.getData().isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Data invalida");
        if(agendaDTO.getData().isAfter(loteService.getMaiorValidadeLotes()))
            throw new IllegalArgumentException("Data maior que a validade dos lotes");

        Situacao situacaoCidadao = cidadaoService.getSituacao(cpf_cidadao);

        if( situacaoCidadao instanceof Habilitado1Dose || situacaoCidadao instanceof Habilitado2Dose){
            Agenda agenda = new Agenda(cpf_cidadao,agendaDTO.getData(),agendaDTO.getHorario(),agendaDTO.getLocal());
            this.agendaRepository.save(agenda);
        }else{
            throw new IllegalArgumentException("Cidadao nao habilitado");
        }

        return agendaRepository.findById(cpf_cidadao).get();
    }



    /**
     * Metodo que pega os agendamentos de um cidadao
     * @param headerToken
     * @return retorna todos os agendamentos do cidadao
     * @throws ServletException
     * @author Holliver Costa
     */

    @Override
    public Agenda getAgendamentobyCpf(String headerToken) throws ServletException {
        String cpf_cidadao = jwtService.getCidadaoDoToken(headerToken);
        String id = jwtService.getCidadaoDoToken(headerToken);
        return agendaRepository.findById(cpf_cidadao).get();
    }
}
