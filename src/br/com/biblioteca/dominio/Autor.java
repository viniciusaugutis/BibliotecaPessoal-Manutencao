package br.com.biblioteca.dominio;

public class Autor {

	private int id;
	private String nome;
	private String sobreNome;

	public Autor() {

	}

	public Autor(String nome, String sobreNome) {

		this.setNome(nome);
		this.setSobreNome(sobreNome);

	}

	public Autor(int id, String nome, String sobreNome) {

		this.id = id;
		this.setNome(nome);
		this.setSobreNome(sobreNome);
	}

	public String getSobreNome() {
		return sobreNome;
	}

	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
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
