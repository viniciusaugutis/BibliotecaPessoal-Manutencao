package br.com.biblioteca.dominio;

import java.util.Date;

public class Emprestimo {

	private int id;
	private Pessoa pessoa;
	private Date dataEmprestimo;
	private Date dataDevolucao;
	private Livro livro;

	public Emprestimo() {
	}

	public Emprestimo(Pessoa pessoa, Date dataEmprestimo, Date dataDevolucao, Livro livro) {

		this.pessoa = pessoa;
		this.dataEmprestimo = dataEmprestimo;
		this.dataDevolucao = dataDevolucao;
		this.livro = livro;

	}

	public Emprestimo(int id, Pessoa pessoa, Date dataEmprestimo, Date dataDevolucao, Livro livro) {

		this.id = id;
		this.pessoa = pessoa;
		this.dataEmprestimo = dataEmprestimo;
		this.dataDevolucao = dataDevolucao;
		this.livro = livro;

	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	@Override
	public String toString() {
		return "Emprestimo [Pessoa=" + pessoa + ", dataEmprestimo=" + dataEmprestimo + ", dataDevolucao="
				+ dataDevolucao + "]";
	}

}