package com.projeto.grupo10.vacineja.model.usuario;

import com.projeto.grupo10.vacineja.state.NaoHabilitado;
import com.projeto.grupo10.vacineja.state.Situacao;
import com.projeto.grupo10.vacineja.state.SituacaoEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CartaoVacina {

    @Id
    private Long cpfUsuario;
    private Long numeroLote2Dose;
    private Long numeroLote1Dose;
    private Date dataPrimeiraDose;
    private Date dataSegundaDose;
    @Enumerated(EnumType.STRING)
    private SituacaoEnum situacao;

    public CartaoVacina(Long cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
        this.situacao = SituacaoEnum.NAO_HABILITADO;
    }

    public CartaoVacina() {
    }

    public Situacao getSituacaoAtual(){
        return this.situacao.getSituacao();
    }

}
