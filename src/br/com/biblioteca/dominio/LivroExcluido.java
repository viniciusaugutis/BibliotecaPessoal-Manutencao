package br.com.biblioteca.dominio;

import java.util.Date;

// feito na forma de armazenar os dados de livros ja excluidos do sistema
// o restante dos livros armazenados na classe livro tambem "sao" livros
//e para nao causar redundancia de dados, so armazena aqui se ja foi excluido

public class LivroExcluido {

	private int isbnLivro;
	private double precoLivro;
	private Date dataCompra;

	public LivroExcluido() {

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

	public int getIsbnLivro() {
		return isbnLivro;
	}

	public void setIsbnLivro(int isbLivro) {
		this.isbnLivro = isbLivro;
	}

}
