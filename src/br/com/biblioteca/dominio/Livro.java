package br.com.biblioteca.dominio;

import java.util.Date;

public class Livro {

	private int isbn;

	private String titulo;
	private int anoEdicao;
	private int edicao;
	private String editora;
	private char situacao;
	private double precoLivro;
	private Date dataCompra;

	public Livro() {

	}

	public Livro(int isbn, String titulo, int anoEdicao, int edicao, String editora, char situacao, double preco,
			Date dataCompra) {

		this.isbn = isbn;
		this.titulo = titulo;
		this.anoEdicao = anoEdicao;
		this.edicao = edicao;
		this.editora = editora;
		this.situacao = situacao;
		this.dataCompra = dataCompra;
		this.precoLivro = preco;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public double getPrecoLivro() {
		return precoLivro;
	}

	public void setPrecoLivro(double precoLivro) {
		this.precoLivro = precoLivro;
	}

	public Date getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getAnoEdicao() {
		return anoEdicao;
	}

	public void setAnoEdicao(int anoEdicao) {
		this.anoEdicao = anoEdicao;
	}

	public int getEdicao() {
		return edicao;
	}

	public void setEdicao(int edicao) {
		this.edicao = edicao;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public char getSituacao() {
		return situacao;
	}

	public void setSituacao(char situacao) {
		this.situacao = situacao;
	}

	@Override

	public String toString() {
		return "Livro [isbn=" + isbn + ", titulo=" + titulo + "]";
	}

}