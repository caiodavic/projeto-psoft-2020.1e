package com.projeto.grupo10.vacineja.DTO;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

public class CidadaoDTO {

	@Digits(integer = 11, fraction = 0, message = "CPF precisa possuir apenas 11 digitos")
	@Size(min = 11, max = 11, message = "CPF precisa possuir apenas 11 digitos")
	private String cpf;

	@NotEmpty(message = "endereço de email não pode ser vazio")
	@NotNull(message = "endereço de email não pode ser nulo")
	@Email(message = "Esse endereço de email não é valido")
	private String email;

	@NotEmpty(message = "nome não pode ser vazio")
	@NotNull(message = "nome não pode ser nulo")
	private String nome;

	@NotEmpty(message = "endereco não pode ser vazio")
	@NotNull(message = "endereco não pode ser nulo")
	private String endereco;

	@Digits(integer = 15, fraction = 0, message = "cartão do SUS precisa possuir apenas 15 digitos")
	@Size(min = 15, max = 15, message = "Cartão do SUS precisa possuir apenas 15 digitos")
	private String cartaoSus;

	@Past(message = "Data de nascimento não pode ser uma data presente ou futura")
	private LocalDate data_nascimento;

	@NotEmpty(message = "telefone não pode ser vazio")
	@NotNull(message = "telefone não pode ser nulo")
	private String telefone;

	@NotEmpty(message = "profissão não pode ser vazia")
	@NotNull(message = "profissão não pode ser nula")
	private Set<String> profissoes;

	@NotEmpty(message = "comorbidade não pode ser vazia")
	@NotNull(message = "comorbidade não pode ser nula")
	private Set<String> comorbidades;

	@NotEmpty(message = "Senha não pode ser vazia")
	@NotNull(message = "Senha não pode ser nula")
	private String senha;

	public String getCpf() {
		return cpf;
	}

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public String getCartaoSus() {
		return cartaoSus;
	}
	public LocalDate getData_nascimento() {
		return data_nascimento;
	}
	public String getTelefone() {
		return telefone;
	}
	public Set<String> getProfissoes() {
		return profissoes;
	}
	public Set<String> getComorbidades() {
		return comorbidades;
	}
	public String getSenha() {
		return senha;
	}
}
