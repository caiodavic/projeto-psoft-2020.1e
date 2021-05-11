package com.projeto.grupo10.vacineja.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class CalculaIdade {
    public static Integer idade(LocalDate dataNascimento){
        Date dataHoje = new Date();
        SimpleDateFormat sdfy = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdfm = new SimpleDateFormat("MM");
        SimpleDateFormat sdfd = new SimpleDateFormat("dd");

        int anoPessoa = Integer.parseInt(sdfy.format(dataNascimento));
        int anoAtual = Integer.parseInt(sdfy.format(dataHoje));
        int idade = anoAtual - anoPessoa;

        int mesPessoa = Integer.parseInt(sdfm.format(dataNascimento));
        int mesAtual = Integer.parseInt(sdfm.format(dataHoje));

        int diaPessoa = Integer.parseInt(sdfd.format(dataNascimento));
        int diaAtual = Integer.parseInt(sdfd.format(dataHoje));

        if(mesAtual < mesPessoa)
            idade--;

        if(diaAtual < diaPessoa)
            idade--;

        return idade;
    }
}
