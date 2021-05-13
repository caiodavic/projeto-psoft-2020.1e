package com.projeto.grupo10.vacineja.job;

import com.projeto.grupo10.vacineja.model.usuario.Cidadao;
import com.projeto.grupo10.vacineja.repository.CidadaoRepository;
import com.projeto.grupo10.vacineja.service.CidadaoService;
import com.projeto.grupo10.vacineja.service.CidadaoServiceImpl;
import com.projeto.grupo10.vacineja.state.Tomou1Dose;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Verificador programado responsável por checar as Data de Segunda dose dos Cidadãos. Quando a data chegar o state do cidadão
 * é automáticamente modificado (isso acontece toda vez que o programa é executado ou às 00hrs todos os dias).
 * @author fernandollisboa
 */
@Component
@EnableScheduling
public class VerificadorDataSegundaDose implements InitializingBean {
    @Autowired
    CidadaoService cidadaoService;

    @Autowired
    CidadaoRepository cidadaoRepository;

    /**
     * Método que verifica se a data da segunda dose dos cidadãos chegou. É agendada para todos os dia a meia-noite
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void run() {
        cidadaoService.verificaDataSegundaDose();
    }


    /**
     * Método que roda toda vez que o Programa
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        run();
    }
}
