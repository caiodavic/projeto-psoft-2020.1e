package com.projeto.grupo10.vacineja.controllers;

import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.model.vacina.VacinaDTO;
import com.projeto.grupo10.vacineja.service.VacinaService;
import com.projeto.grupo10.vacineja.util.ErroVacina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// É necessário realizar uma verificação toda vez que uma ação com vacinação for feita, não faço ideia como fazer sem ficar tudo uma bagunça
@RestController
@RequestMapping("/api")
@CrossOrigin
public class VacinaControllerAPI {
    @Autowired
    VacinaService vacinaService;

    // TO-DO exception handling
    @PostMapping("/vacina")
    public ResponseEntity<?> criaVacina(@RequestBody VacinaDTO vacinaDTO){

        if(vacinaDTO.getNumDosesNecessarias() > 2) return ErroVacina.numMaxDoses();

        Vacina vacina = vacinaService.criaVacina(vacinaDTO);
        return new ResponseEntity<>(vacina, HttpStatus.CREATED);
    }

    // TO-DO exception handling
    @GetMapping("/vacina")
    public ResponseEntity<?> listaVacinas(){
        List<Vacina> vacinasList = vacinaService.listarVacinas();

        if(vacinasList.isEmpty()){
            return  ErroVacina.semVacinasCadastradas();
        }
        return new ResponseEntity<>(vacinasList,HttpStatus.OK);
    }

    // TO-DO exception handling
    @GetMapping("/vacina/{nome_fabricante}/lotes")
    public ResponseEntity<?> getLotesPorFabricante(@PathVariable ("nome_fabricante") String nomeFabricante){
        Vacina vacina = vacinaService.fetchVacina(nomeFabricante);

        return new ResponseEntity<>(vacina,HttpStatus.OK);
    }
}
