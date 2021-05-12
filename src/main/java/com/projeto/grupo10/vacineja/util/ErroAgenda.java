package com.projeto.grupo10.vacineja.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public class ErroAgenda {
    static final String DATA_INVALIDA = "Data inválida: data inserida antes do dia de hoje (%s)";

    public static ResponseEntity<CustomErrorType> erroDataInvalida(LocalDate data) {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroAgenda.DATA_INVALIDA, data)),
                HttpStatus.NOT_ACCEPTABLE);
    }
}
