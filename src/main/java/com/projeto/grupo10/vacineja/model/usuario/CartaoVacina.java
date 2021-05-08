package com.projeto.grupo10.vacineja.model.usuario;

import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.state.Situacao;
import com.projeto.grupo10.vacineja.state.SituacaoEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CartaoVacina {

    @Id
    private String cpfUsuario;

    @ManyToOne
    private Vacina vacina;

    private Date dataPrimeiraDose;
    private Date dataSegundaDose;

    private Date dataPrevistaSegundaDose;

    @Enumerated(EnumType.STRING)
    private SituacaoEnum situacao;

    public CartaoVacina() {
    }

    public CartaoVacina(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
        this.situacao = SituacaoEnum.NAO_HABILITADO;
    }

    public void proximaSituacao(){
        this.situacao.getSituacao().proximaSituacao(this);
    }

    public void proximaSituacao(Vacina vacina, Date dataVacina){
        if (this.vacina != null && !this.vacina.equals(vacina)){
            throw new IllegalArgumentException("As duas doses tomadas por um cidadão devem ser do mesmo tipo.");
        }
        this.situacao.getSituacao().proximaSituacao(this, vacina, dataVacina);
    }


    public void setDataSegundaDose(Date dataSegundaDose) {
        this.dataSegundaDose = dataSegundaDose;
    }

    public void setSituacao(SituacaoEnum situacao) {
        this.situacao = situacao;
    }

    public void setDataPrimeiraDose(Date dataPrimeiraDose) {
        this.dataPrimeiraDose = dataPrimeiraDose;
    }

    public void setDataPrevistaSegundaDose(Date dataPrevistaSegundaDoseSegundaDose) {
        this.dataPrevistaSegundaDose = dataPrevistaSegundaDoseSegundaDose;
    }

    public Date getDataPrevistaSegundaDose() {
        return dataPrevistaSegundaDose;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public int getQtdDoseVacina() {
        return vacina.getNumDosesNecessarias();
    }

    public int getIntervaloVacina() {
        return this.vacina.getDiasEntreDoses();
    }

    public Date getDataPrimeiraDose() {
        return dataPrimeiraDose;
    }

    public Situacao getSituacaoAtual(){
        return this.situacao.getSituacao();
    }
}
