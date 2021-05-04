package com.projeto.grupo10.vacineja.controllers;

import com.projeto.grupo10.vacineja.model.lote.Lote;
import com.projeto.grupo10.vacineja.model.lote.LoteDTO;
import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.model.vacina.VacinaDTO;
import com.projeto.grupo10.vacineja.service.LoteService;
import com.projeto.grupo10.vacineja.service.VacinaService;
import com.projeto.grupo10.vacineja.util.ErroLote;
import com.projeto.grupo10.vacineja.util.ErroVacina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// É necessário realizar uma verificação toda vez que uma ação com vacinação for feita
@RestController
@RequestMapping("/api")
@CrossOrigin
public class VacinaControllerAPI {
    @Autowired
    VacinaService vacinaService;

    @Autowired
    LoteService loteService;


    // TO-DO exception handling
    @PostMapping("/vacina")
    public ResponseEntity<?> criaVacina(@RequestBody VacinaDTO vacinaDTO){

        if(vacinaDTO.getNumDosesNecessarias() > 2) return ErroVacina.numMaxDoses();

        try {
            Vacina vacina = vacinaService.criaVacina(vacinaDTO);
            return new ResponseEntity<>(vacina, HttpStatus.CREATED);

        } catch (IllegalArgumentException e){
            return ErroVacina.erroCadastroVacina(e.getMessage());
        }
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
    @GetMapping("/vacina/lote")
    public ResponseEntity<?> listaLotes(){
        List<Lote> loteList = loteService.listaLotes();

        if(loteList.isEmpty()){
            return ErroLote.semLotesCadastrados();
        }

        return new ResponseEntity<>(loteList,HttpStatus.OK);
    }

    @GetMapping("/vacina/lote/{nome_fabricante}")
    public ResponseEntity<?> listaLotesPorFabricante(@PathVariable ("nome_fabricante") String nomeFabricante){
        try {
            Vacina vacina = vacinaService.fetchVacina(nomeFabricante);

            List<Lote> loteList = loteService.listaLotesPorFabricante(nomeFabricante);

            if(loteList.isEmpty()){
                return ErroLote.semLotesCadastrados();
            }

            return new ResponseEntity<>(loteList, HttpStatus.OK);

        }
        catch (NullPointerException e){
            return ErroVacina.erroVacinaNaoCadastrada(nomeFabricante);
        }
    }

    // TO-DO exception handling
    @PostMapping("/vacina/{nome_fabricante}/lote")
    public ResponseEntity<?> criaLote(@PathVariable("nome_fabricante") String nomeFabricante, @RequestBody LoteDTO loteDTO){
        try{
            Vacina vacina = vacinaService.fetchVacina(nomeFabricante);
            Lote lote = loteService.criaLote(loteDTO,vacina);
            return new ResponseEntity<>(lote,HttpStatus.CREATED);

        }
        catch (NullPointerException e){
            return ErroVacina.erroVacinaNaoCadastrada(nomeFabricante);

        }
        catch (IllegalArgumentException e){
            return ErroLote.erroCadastroLote(e.getMessage());
        }

    }


    // TO-DO Isso é um esboço, pra ver se tá sendo removido corretamente de lotes. Provavelmente
    // a forma de aplicar a dose será em outro endpoint (?)
    @GetMapping("/vacina/{nome_fabricante}/{num_lote}")
    public ResponseEntity<?> removeVacina(@PathVariable("nome_fabricante") String nomeFabricante, @PathVariable ("num_Lote") long numLote, @RequestBody int numVacinas){
        try{
            Vacina vacina = vacinaService.fetchVacina(nomeFabricante);
            List<Lote> loteList = new ArrayList<Lote>();

            for(int i = 0; i< numVacinas ; i++){
                loteList.add(loteService.removeDoseLotes(nomeFabricante));
            }

            return new ResponseEntity<>(loteList,HttpStatus.ACCEPTED);
        } catch (NullPointerException e){
            return ErroVacina.erroVacinaNaoCadastrada(nomeFabricante);

        }
    }

}
