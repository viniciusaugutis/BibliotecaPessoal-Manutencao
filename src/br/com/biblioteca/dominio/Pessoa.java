package br.com.biblioteca.dominio;

public class Pessoa {

	int id;
	String nome;
	String telefone;
	String email;
	
	
	public Pessoa(int id, String nome, String telefone, String email) throws Exception {

		this.id = id;
		this.setNome(nome);
;
		this.setTelefone(telefone);
		this.setEmail(email);

	}

	public Pessoa(String nome, String telefone, String email) throws Exception {

		this.setNome(nome);
		
		this.setTelefone(telefone);
		this.setEmail(email);

	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) throws Exception {
		if (telefone.length() == 17) {
			this.telefone = telefone;
		} else {
			throw new Exception();
		}
		
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


}
