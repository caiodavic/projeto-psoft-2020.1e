package com.projeto.grupo10.vacineja.DTO;

public class CidadaoLoginDTO {

    private String cpfLogin;
    private String senhaLogin;
    private String tipoLogin;

    public String getCpfLogin(){
        return this.cpfLogin;
    }

    public String getSenhaLogin(){
        return this.senhaLogin;
    }

    public String getTipoLogin(){
        return this.tipoLogin;
    }
}
