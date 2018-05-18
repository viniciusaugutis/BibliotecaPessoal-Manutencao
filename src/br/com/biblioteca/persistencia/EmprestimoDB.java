package br.com.biblioteca.persistencia;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.biblioteca.dominio.Emprestimo;
import br.com.biblioteca.dominio.Livro;
import br.com.biblioteca.dominio.Pessoa;

public class EmprestimoDB implements GenericDB<Emprestimo, Integer> {

	private Connection con;
	private ResultSet rs;
	private Statement stm;

	// define o formato do campo Data
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public EmprestimoDB() {

	}

	public void inserir(Emprestimo emprestimo) {

		// cria um comANDo INSERT com os atributos de Emprestimo

		String QUERY = "INSERT INTO emprestimo (id, isbn_livro, id_pessoa, data_emprestimo,data_devolucao) values(DEFAULT,"
				+ emprestimo.getLivro().getIsbn() + "," + emprestimo.getPessoa().getId() + ", '"
				+ new String(dateFormat.format(emprestimo.getDataEmprestimo().getTime())) + "', " + null + ")";

		try {

			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa o comANDo para inserir os dados na tabela
			stm.executeUpdate(QUERY);
		} catch (SQLException e) {
			System.out.println("Erro ao inserir na tabela Emprestimo");
			e.printStackTrace();
		} finally {
			this.fecha(rs, stm, con);
		}
	}

	public void excluir(Emprestimo emprestimo) {

		// cria um comANDo DELETE usANDo o id de Emprestimo
		String QUERY = "delete FROM emprestimo WHERE id = " + emprestimo.getId();

		try {

			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa o comANDo para excluir o autor da tabela
			stm.executeUpdate(QUERY);

		} catch (SQLException e) {
			System.out.println("Erro ao tentar excluir na tabela");
		} finally {
			this.fecha(rs, stm, con);
		}
	}

	public void modificar(Emprestimo emprestimo) {

		// cria um comANDo UPDATE usANDo os atributos de Emprestimo
		String QUERY = "UPDATE emprestimo SET id_pessoa = " + emprestimo.getPessoa().getId() + ", data_emprestimo = '"
				+ new String(dateFormat.format(emprestimo.getDataEmprestimo().getTime()) + "' , isbn_livro = "
						+ emprestimo.getLivro().getIsbn() + "");

		if (emprestimo.getDataDevolucao() == null) {

			QUERY += "WHERE id = " + emprestimo.getId();
		} else {
			QUERY += ", data_devolucao = '" + new String(dateFormat.format(emprestimo.getDataDevolucao().getTime()))
					+ "' WHERE id = " + emprestimo.getId();
		}

		try {
			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa o comANDo para modificar os dados na tabela
			stm.executeUpdate(QUERY);

		} catch (SQLException e) {
			System.out.println("Erro ao modificar a tabela");
			e.printStackTrace();
		} finally {
			this.fecha(rs, stm, con);
		}
	}

	public List<Emprestimo> buscarTodos() {

		// declara um ArrayList para receber os dados da tabela

		List<Emprestimo> lista = new ArrayList<Emprestimo>();
		String QUERY = "SELECT  * FROM emprestimo ORDER BY id";

		try {

			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa a consulta de todos os registros
			rs = stm.executeQuery(QUERY);

			// percorre o ResultSet lendo os dados de Emprestimo
			while (rs.next()) {

				int id = rs.getInt("id");
				int isbnLivro = rs.getInt("isbn_livro");
				int idPessoa = rs.getInt("id_pessoa");
				Date dataEmprestimo = rs.getDate("data_emprestimo");
				Date dataDevolucao = rs.getDate("data_devolucao");

				Livro livro = new LivroDB().buscarPorIsbn(isbnLivro);
				Pessoa pessoa = new PessoaDB().buscarPorID(idPessoa);

				// cria um Emprestimo com os dados de um registro
				Emprestimo emprestimo = new Emprestimo(id, pessoa, dataEmprestimo, dataDevolucao, livro);

				// adiciona o Emprestimo no ArrayList
				lista.add(emprestimo);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar tabela");
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

	public Emprestimo buscarPorID(Integer id) {

		// cria um SELECT  para retornar um Emprestimo pelo id
		String QUERY = "SELECT  * FROM Emprestimo WHERE id = " + id;

		Emprestimo emprestimo = null;

		try {
			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa a consulta
			rs = stm.executeQuery(QUERY);

			// cria um objeto Emprestimo com os dados retornados
			if (rs.next()) {
				id = rs.getInt("ID");
				int isbnLivro = rs.getInt("isbn_livro");
				int idPessoa = rs.getInt("id_pessoa");
				Date dataEmprestimo = rs.getDate("data_emprestimo");
				Date dataDevolucao = rs.getDate("data_devolucao");

				Livro livro = new LivroDB().buscarPorIsbn(isbnLivro);
				Pessoa pessoa = new PessoaDB().buscarPorID(idPessoa);

				// cria um Emprestimo com os dados de um registro
				emprestimo = new Emprestimo(id, pessoa, dataEmprestimo, dataDevolucao, livro);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar na tabela");
		} finally {
			this.fecha(rs, stm, con);
		}
		return emprestimo;
	}

	public List<Emprestimo> buscarEmprestados() {

		// declara um ArrayList para receber os dados da tabela

		List<Emprestimo> lista = new ArrayList<Emprestimo>();
		String QUERY = "SELECT  * FROM emprestimo WHERE data_devolucao IS NULL";

		try {

			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa a consulta de todos os registros
			rs = stm.executeQuery(QUERY);

			// percorre o ResultSet lendo os dados de Emprestimo
			while (rs.next()) {

				int id = rs.getInt("id");
				int isbnLivro = rs.getInt("isbn_livro");
				int idPessoa = rs.getInt("id_pessoa");
				Date dataEmprestimo = rs.getDate("data_emprestimo");
				Date dataDevolucao = rs.getDate("data_devolucao");

				Livro livro = new LivroDB().buscarPorIsbn(isbnLivro);
				Pessoa pessoa = new PessoaDB().buscarPorID(idPessoa);

				// cria um Emprestimo com os dados de um registro
				Emprestimo emprestimo = new Emprestimo(id, pessoa, dataEmprestimo, dataDevolucao, livro);

				// adiciona o Emprestimo no ArrayList
				lista.add(emprestimo);
			}

		} catch (SQLException e) {
			System.out.println("Erro ao consultar tabela");
		}

		finally {
			this.fecha(rs, stm, con);
		}
		return lista;
	}

	public int buscarSeTemEmprestimo(Livro livro) {

		String QUERY = "SELECT  * FROM emprestimo WHERE ISBN_LIVRO = " + livro.getIsbn() + " ORDER BY id";
		int auxiliarExcluir = 1;

		try {

			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa a consulta de todos os registros
			rs = stm.executeQuery(QUERY);

			// percorre o ResultSet lendo os dados de Emprestimo
			while (rs.next()) {

				Date dataDevolucao = rs.getDate("data_devolucao");

				if (dataDevolucao == null) {
					auxiliarExcluir = 0;
					// se retorna 0 é que ele tem emprestimo
				}

			}

		} catch (SQLException e) {
			System.out.println("Erro ao consultar tabela");
			e.printStackTrace();
		}

		finally {
			this.fecha(rs, stm, con);
		}

		return auxiliarExcluir;
	}

	public void excluirEmprestimoLivro(Livro livro) {

		// cria um comANDo DELETE usANDo o id de Emprestimo
		String QUERY = "delete FROM emprestimo WHERE isbn_livro = " + livro.getIsbn();

		try {

			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa o comANDo para excluir o autor da tabela
			stm.executeUpdate(QUERY);

		} catch (SQLException e) {
			System.out.println("Erro ao tentar excluir na tabela");
		} finally {
			this.fecha(rs, stm, con);
		}
	}

	public ResultSet getRelatorioEmprestimo() throws SQLException {

		Connection con = Conexao.criarConexao();
		String QUERY = "SELECT  emprestimo.id, emprestimo.data_emprestimo, pessoa.nome, livro.titulo,emprestimo.data_devolucao FROM livro, pessoa, emprestimo WHERE pessoa.id = emprestimo.id_Pessoa AND livro.isbn = emprestimo.isbn_livro  AND emprestimo.data_Devolucao IS NULL ORDER BY emprestimo.id";

		PreparedStatement stmt = con.prepareStatement(QUERY);

		ResultSet rs = stmt.executeQuery();

		return rs;
	}

}