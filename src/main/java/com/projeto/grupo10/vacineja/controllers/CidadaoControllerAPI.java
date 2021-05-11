package com.projeto.grupo10.vacineja.controllers;



import com.projeto.grupo10.vacineja.model.agenda.Agenda;
import com.projeto.grupo10.vacineja.DTO.CidadaoUpdateDTO;
import com.projeto.grupo10.vacineja.model.usuario.Cidadao;
import com.projeto.grupo10.vacineja.DTO.CidadaoDTO;
import com.projeto.grupo10.vacineja.DTO.FuncionarioCadastroDTO;
import com.projeto.grupo10.vacineja.service.*;
import com.projeto.grupo10.vacineja.util.ErroCidadao;
import com.projeto.grupo10.vacineja.util.ErroLogin;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CidadaoControllerAPI {

    @Autowired
    CidadaoService cidadaoService;

    @Autowired
    VacinaService vacinaService;

    @Autowired
    LoteService loteService;

    @Autowired
    AgendaService agendaService;

    @Autowired
    JWTService jwtService;

    /**
     * Cria um cidadao a partir de um CidadaoDTO
     * @param cidadaoDTO
     * @return retorna o cidadaoDTO
     */

    @RequestMapping(value = "/usuario/cadastra-cidadao", method = RequestMethod.POST)
    public ResponseEntity<?> cadastraCidadao(@RequestBody CidadaoDTO cidadaoDTO) {

        Optional<Cidadao> cidadaos = cidadaoService.getCidadaoById(cidadaoDTO.getCpf());
        String emailCidadao = cidadaoDTO.getEmail();

        try{
            cidadaoService.cadastraCidadao(cidadaoDTO);
        } catch (IllegalArgumentException e){
            if(e.getMessage().toString() == "Email invalido"){
                return ErroCidadao.erroEmailInvalido();
            }
            else if(e.getMessage().toString() == "Cidadao cadastrado"){
                return ErroCidadao.erroCidadaoCadastrado(cidadaoDTO.getCpf());
            }
        }
        return new ResponseEntity<CidadaoDTO>(cidadaoDTO, HttpStatus.CREATED);
    }

    /**
     * Metodo para o cadastro do funcionario
     * @param headerToken - token do cidadao que esta tentando viarr funcionario
     * @param cadastroFuncionario -Um objeto com as seguintes informações -cpf do cidadão, local de trabalho e a função-
     * @return response entity adequada, dizendo se o cidadao foi definido com funcionario
     *
     * @author Caetano Albuquerque
     */
    @RequestMapping(value = "/cidadao/cadastrar-funcionario", method = RequestMethod.POST)
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> cadastrarFuncionario(@RequestHeader("Authorization") String headerToken,
                                        @RequestBody FuncionarioCadastroDTO cadastroFuncionario){

        try{
            cidadaoService.cadastroFuncionario(headerToken, cadastroFuncionario);
        }

        catch (IllegalArgumentException iae){
            return ErroCidadao.erroUsuarioNaoEncontrado();
        }
        catch (ServletException e){
            return ErroLogin.erroTokenInvalido();
        }

        return new ResponseEntity<String>("Cidadão definido como funcionario, aguardando aprovação do administrador.",
                HttpStatus.OK);
    }

    /**
     * Altera os valores de um Cidadao a partir de uma cidadaoUpdateDTO. É necessária a apresentação do token de Cidadão para relizar essa
     * ação. O unico valor que não pode ser alterado é o cpf do Cidadao, visto que é sua primaryKey.
     *
     * @param headerToken eh o token do cidadão
     * @param cidadaoUpdateDTO eh o dto da vacina a ser criada
     * @return response entity adequada, contendo o Cidadao atualizado
     */

    @RequestMapping(value = "/cidadao/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> updateCidadao(@RequestHeader("Authorization") String headerToken,
                                           @RequestBody CidadaoUpdateDTO cidadaoUpdateDTO) {

        Optional<Cidadao> cidadao = cidadaoService.getCidadaoById(cidadaoUpdateDTO.getCpf());


        try{
            cidadaoService.updateCidadao(headerToken, cidadaoUpdateDTO, cidadao.get());
        }

        catch (IllegalArgumentException iae){
            return ErroCidadao.erroUsuarioNaoEncontrado();
        }
        catch (ServletException e){
            return ErroLogin.erroTokenInvalido();
        }
        return new ResponseEntity<Cidadao>(cidadao.get(),HttpStatus.ACCEPTED);
    }

    /**
     * O cidadao agenda sua vacinação com o seu cpf e informando o horario, data e local da vacinação
     * @param headerToken
     * @param agenda
     * @return retorna o agendamento feito
     */
    @RequestMapping(value = "/cidadao/AgendaVacina", method = RequestMethod.POST)
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> AgendamentoVacina(@RequestHeader("Authorization") String headerToken,
                                           @RequestBody Agenda agenda) {
        try {
            agendaService.agendaVacinação(headerToken, agenda);
        } catch (ServletException e) {
            return ErroLogin.erroTokenInvalido();
        }
        return new ResponseEntity<Agenda>(agenda, HttpStatus.CREATED);
    }

    /**
     * Pega os agendamentos feitos por um cidadao usando o cpf
     * @param headerToken
     * @param cpf
     * @return Retorna todos os agendamentos feitos pelo cidadao
     */
    @RequestMapping(value = "/cidadao/AgendaVacina/{cpf}", method = RequestMethod.GET)
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> listaAgendamentoCidadao(@RequestHeader("Authorization") String headerToken,
                                               @RequestParam String cpf) {
        try{
            List<Agenda> agenda = agendaService.getAgendamentobyCpf(headerToken,cpf);
            return new ResponseEntity<List<Agenda>>(agenda,HttpStatus.OK);
        }catch (ServletException e){
            return ErroLogin.erroTokenInvalido();
        }
    }

    /**
     * Metodo responsavel por pegar o estado de vacinação
     * @param headerToken - token cidadao que quer ver o seu estado
     * @return - um ResponseEntity contendo o estado de vacinação do cidadão
     *
     * @author Caetano Albuquerque
     */
    @RequestMapping(value = "/cidadao/estadoVacinacao", method = RequestMethod.GET)
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> getEstadoVacinacao(@RequestHeader("Authorization") String headerToken){

        String estadoVacinacao = "";

        try{
            estadoVacinacao = cidadaoService.getEstadoVacinacao(headerToken);
        }

        catch (IllegalArgumentException iae){
            return ErroCidadao.erroUsuarioNaoEncontrado();
        }
        catch (ServletException e){
            return ErroLogin.erroTokenInvalido();
        }

        return new ResponseEntity<String>(estadoVacinacao, HttpStatus.OK);
    }
}
