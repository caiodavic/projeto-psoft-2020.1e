package com.projeto.grupo10.vacineja.controllers;

import com.projeto.grupo10.vacineja.DTO.CidadaoDTO;
import com.projeto.grupo10.vacineja.model.usuario.Cidadao;
import com.projeto.grupo10.vacineja.service.CidadaoService;
import com.projeto.grupo10.vacineja.util.ErroCidadao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UsuarioControllerAPI {

    @Autowired
    CidadaoService cidadaoService;

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
            if(e.getMessage().toString() == "Cidadao cadastrado"){
                return ErroCidadao.erroCidadaoCadastrado(cidadaoDTO.getCpf());
            }
            if(e.getMessage().toString() == "Não é possivel cadastrar um Cidadao com esse cpf"){
                return ErroCidadao.erroCPFInvalido();
            }
            if(e.getMessage().toString() == "Não é possivel cadastrar um Cidadao com esse numero de cartao do SUS"){
                return ErroCidadao.erroCartaoSUSInvalido();
            }
            if(e.getMessage().toString() == "Não é possivel cadastrar um Cidadao com essa senha"){
                return ErroCidadao.erroSenhaInvalida();
            }
            if(e.getMessage().toString() == "Não é possivel cadastrar um Cidadao com essa data de nascimento"){
                return ErroCidadao.erroDataInvalida();
            }

        }
        return new ResponseEntity<CidadaoDTO>(cidadaoDTO, HttpStatus.CREATED);
    }
}
