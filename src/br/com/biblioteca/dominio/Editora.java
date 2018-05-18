package br.com.biblioteca.dominio;

public class Editora {

	private int id;
	private String nome;

	public Editora() {

	}

	public Editora(String nome) {
		this.setNome(nome);
	}

	public Editora(int id, String nome) {
		this.id = id;
		this.setNome(nome);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}
