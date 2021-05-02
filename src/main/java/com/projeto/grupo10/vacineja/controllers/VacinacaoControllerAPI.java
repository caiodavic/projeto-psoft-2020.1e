package com.projeto.grupo10.vacineja.controllers;

import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.model.vacina.VacinaDTO;
import com.projeto.grupo10.vacineja.service.VacinacaoService;
import com.projeto.grupo10.vacineja.service.VacinacaoServiceImpl;
import com.projeto.grupo10.vacineja.util.ErroVacina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class VacinacaoControllerAPI {
    @Autowired
    VacinacaoService vacinacaoService;

    // TO-DO exception handling
    @PostMapping("/vacinacao")
    public ResponseEntity<?> criaVacina(@RequestBody VacinaDTO vacinaDTO){

        if(vacinaDTO.getNumDosesNecessarias() > 2) return ErroVacina.numMaxDoses();

        Vacina vacina = vacinacaoService.criaVacina(vacinaDTO);
        return new ResponseEntity<>(vacina, HttpStatus.CREATED);
    }

    // TO-DO exception handling
    @GetMapping("/vacinacao/")
    public ResponseEntity<?> listaVacinas(){
        List<Vacina> vacinasList = vacinacaoService.listarVacinas();

        if(vacinasList.isEmpty()){
            return  ErroVacina.semVacinasCadastradas();
        }
        return new ResponseEntity<>(vacinasList,HttpStatus.OK);
    }

    // TO-DO exception handling
    @GetMapping("/vacinacao/{nome_fabricante}")
    public ResponseEntity<?> getVacinaPorFabricante(@PathVariable ("nome_fabricante") String nomeFabricante){
        Vacina vacina = vacinacaoService.fetchVacina(nomeFabricante);

        return new ResponseEntity<>(vacina,HttpStatus.OK);
    }
}
