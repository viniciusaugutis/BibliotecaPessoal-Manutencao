package br.com.biblioteca.persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsultasDB {

	private Connection con;
	private ResultSet rs;
	private Statement stm;

	public List<Object[]> consultaEmprestimos(String nomePessoa, String nomeLivro, Date dataInicial, Date dataFinal) {

		List<Object[]> lista = new ArrayList<Object[]>();
		String QUERY;

		try {

			SimpleDateFormat dataBanco = new SimpleDateFormat("yyyy-MM-dd");

			con = Conexao.criarConexao();
			stm = con.createStatement();

			if ((nomePessoa.equals("") && (nomeLivro.equals("")))) {

				QUERY = "SELECT P.nome, a.data_emprestimo, b.titulo FROM EMPRESTIMO a, LIVRO b, PESSOA p WHERE a.ISBN_LIVRO = b.ISBN AND A.ID_PESSOA = p.id"
						+ " AND a.DATA_DEVOLUCAO IS NULL and a.data_emprestimo BETWEEN '"
						+ dataBanco.format(dataInicial) + "' AND '" + dataBanco.format(dataFinal)
						+ "' ORDER BY b.TITULO";
			} else if (nomePessoa.equals("")) {

				QUERY = "SELECT P.nome, a.data_emprestimo, b.titulo FROM EMPRESTIMO a, LIVRO b, PESSOA p WHERE a.ISBN_LIVRO = b.ISBN AND A.ID_PESSOA = p.id"
						+ " AND a.DATA_DEVOLUCAO IS NULL and upper(b.titulo) LIKE '%" + nomeLivro.toUpperCase()
						+ "%' AND a.data_emprestimo BETWEEN '" + dataBanco.format(dataInicial) + "' AND '"
						+ dataBanco.format(dataFinal) + "' ORDER BY b.TITULO";

			} else if (nomeLivro.equals("")) {

				QUERY = "SELECT P.nome, a.data_emprestimo, b.titulo FROM EMPRESTIMO a, LIVRO b, PESSOA p WHERE a.ISBN_LIVRO = b.ISBN AND A.ID_PESSOA = p.id"
						+ " AND a.DATA_DEVOLUCAO IS NULL and upper(p.nome) LIKE '%" + nomePessoa.toUpperCase()
						+ "%' AND a.data_emprestimo BETWEEN '" + dataBanco.format(dataInicial) + "' AND '"
						+ dataBanco.format(dataFinal) + "' ORDER BY b.TITULO";

			} else {

				QUERY = "SELECT P.nome, a.data_emprestimo, b.titulo FROM EMPRESTIMO a, LIVRO b, PESSOA p WHERE a.ISBN_LIVRO = b.ISBN AND A.ID_PESSOA = p.id"
						+ " AND a.DATA_DEVOLUCAO IS NULL and upper(p.nome) LIKE '%" + nomePessoa.toUpperCase()
						+ "%' and upper(b.titulo) LIKE '%" + nomeLivro.toUpperCase()
						+ "%' AND a.data_emprestimo BETWEEN '" + dataBanco.format(dataInicial) + "' AND '"
						+ dataBanco.format(dataFinal) + "' ORDER BY b.TITULO";

			}

			rs = stm.executeQuery(QUERY);

			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

			while (rs.next()) {
				String titulo = rs.getString("titulo");
				String nome = rs.getString("nome");
				Date dataEmprestimo = rs.getDate("data_emprestimo");
				lista.add(new Object[] { titulo, nome, formato.format(dataEmprestimo) });
			}

		} catch (SQLException e) {

			System.out.println("Erro ao criar conexão");
			e.printStackTrace();
		}

		finally {

			this.fecha(rs, stm, con);
		}

		return lista;
	}

	public List<Object[]> consultaPessoas(String nomePessoa) {

		List<Object[]> lista = new ArrayList<Object[]>();
		String QUERY;

		try {

			con = Conexao.criarConexao();
			stm = con.createStatement();

			if (nomePessoa.equals("")) {

				QUERY = "SELECT * FROM pessoa ORDER BY nome";

			} else {
				QUERY = "SELECT * FROM pessoa WHERE upper(nome) LIKE '%" + nomePessoa.toUpperCase() + "%'";
			}

			rs = stm.executeQuery(QUERY);

			while (rs.next()) {
				String nome = rs.getString("nome");
				String telefone = rs.getString("telefone");
				String email = rs.getString("email");

				lista.add(new Object[] { nome, telefone, email });
			}

		} catch (SQLException e) {

			System.out.println("Erro ao criar conexão");
			e.printStackTrace();
		}

		finally {

			this.fecha(rs, stm, con);
		}

		return lista;
	}

	public List<Object[]> consultaAutores(String nomeAutor) {

		List<Object[]> lista = new ArrayList<Object[]>();
		String QUERY;

		try {

			con = Conexao.criarConexao();
			stm = con.createStatement();

			if (nomeAutor.equals("")) {

				QUERY = "SELECT * FROM autor ORDER BY nome";

			} else {
				QUERY = "SELECT * FROM autor WHERE upper(nome) LIKE '%" + nomeAutor.toUpperCase() + "%'";
			}

			rs = stm.executeQuery(QUERY);

			while (rs.next()) {
				String nome = rs.getString("nome");
				String sobrenome = rs.getString("sobrenome");

				lista.add(new Object[] { nome, sobrenome });
			}

		} catch (SQLException e) {

			System.out.println("Erro ao criar conexão");
			e.printStackTrace();
		}

		finally {

			this.fecha(rs, stm, con);
		}

		return lista;
	}

	public List<Object[]> consultaLivros(String nomeLivro, char situacaoo) {

		List<Object[]> lista = new ArrayList<Object[]>();
		String QUERY = null;

		try {

			con = Conexao.criarConexao();
			stm = con.createStatement();

			if ((nomeLivro.equals("")) && (situacaoo == 'Q')) {

				QUERY = "SELECT * FROM livro ORDER BY isbn";

			} else if ((nomeLivro.equals(""))) {
				
				QUERY = "SELECT * FROM livro WHERE situacao = '" + situacaoo + "' ORDER BY isbn";
			} else if ((situacaoo == 'Q')) {
				QUERY = "SELECT * FROM livro WHERE upper(titulo) LIKE '%" + nomeLivro.toUpperCase() + "%' ORDER BY isbn";

			} else {

				QUERY = "SELECT * FROM livro WHERE situacao = '" + situacaoo + "' and upper(titulo) LIKE '%"
						+ nomeLivro.toUpperCase() + "%' ORDER BY isbn";
			}
			
			

			rs = stm.executeQuery(QUERY);

			while (rs.next()) {
				String titulo = rs.getString("titulo");
				int isbn = rs.getInt("isbn");
				char situacao = rs.getString("situacao").charAt(0);
				double preco = rs.getDouble("preco");
				Date dataCompra = rs.getDate("dataCompra");
				

				SimpleDateFormat formatoBR = new SimpleDateFormat("dd/MM/yyyy");
				
				String situacaoString;
				
				if (situacao == 'S'){
					situacaoString = "Emprestado";
				}else{
					situacaoString = "Não Emprestado";
				}
				
				lista.add(new Object[] {isbn,titulo, preco, formatoBR.format(dataCompra),situacaoString });
			}

		} catch (SQLException e) {

			System.out.println("Erro ao criar conexão");
			e.printStackTrace();
		}

		finally {

			this.fecha(rs, stm, con);
		}

		return lista;
	}

	public void fecha(ResultSet rs, Statement stm, Connection con) {

		if (rs != null) {

			try {

				rs.close();

			} catch (SQLException e) {

			}
		}

		if (stm != null) {
			try {
				stm.close();
			} catch (SQLException e) {
			}

		}

		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
	}

}
