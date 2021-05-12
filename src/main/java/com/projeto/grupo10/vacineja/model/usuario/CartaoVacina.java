package com.projeto.grupo10.vacineja.model.usuario;

import com.projeto.grupo10.vacineja.model.vacina.Vacina;
import com.projeto.grupo10.vacineja.state.Situacao;
import com.projeto.grupo10.vacineja.state.SituacaoEnum;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CartaoVacina {

    @Id
    private String cartaoSus;

    @ManyToOne
    private Vacina vacina;

    private Date dataPrimeiraDose;
    private Date dataSegundaDose;

    private Date dataPrevistaSegundaDose;
    private Date dataAgendamento;

    @Enumerated(EnumType.STRING)
    @NotNull
    private SituacaoEnum situacao;

    public CartaoVacina() {
    }

    public CartaoVacina(String cartaoSus) {
        this.cartaoSus = cartaoSus;
        this.situacao = SituacaoEnum.NAO_HABILITADO;
    }

    public void proximaSituacao(){
        this.getSituacaoAtual().proximaSituacao(this);
    }

    public void proximaSituacao(Vacina vacina, Date dataVacina){
        if (this.vacina != null && !this.vacina.equals(vacina)){
            throw new IllegalArgumentException("As duas doses tomadas por um cidadão devem ser do mesmo tipo.");
        }
        this.getSituacaoAtual().proximaSituacao(this, vacina, dataVacina);
    }

    public void agendarVacinacao(Date dataAgendamento){
        this.getSituacaoAtual().agendarVacinacao(this, dataAgendamento);
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

    public void setDataAgendamento(Date dataAgendamento){
        this.dataAgendamento = dataAgendamento;
    }

    public String getVacinaString() {
        return vacina.getNomeFabricante();
    }
}
