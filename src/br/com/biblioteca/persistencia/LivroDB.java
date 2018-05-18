package br.com.biblioteca.persistencia;

//imports omitidos...
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import br.com.biblioteca.dominio.Autor;
import br.com.biblioteca.dominio.AutorLivro;
import br.com.biblioteca.dominio.Livro;

public class LivroDB implements GenericDB<Livro, Integer> {

	private Connection con;
	private ResultSet rs;
	private Statement stm;
	private SimpleDateFormat dataBanco = new SimpleDateFormat("yyyy-MM-dd");

	public LivroDB() {

	}

	public void inserir(Livro livro) {

		// livro contem tudo que veio da outra tela

		// cria um comando INSERT com os atributos de Livro

		String QUERY = "INSERT INTO livro VALUES(DEFAULT," + "'" + livro.getTitulo() + "'," + livro.getAnoEdicao() + ", "
				+ livro.getEdicao() + ", '" + livro.getEditora() + "', '" + livro.getSituacao() + "',"
				+ livro.getPrecoLivro() + ",'" + dataBanco.format(livro.getDataCompra()) + "')";

		try {

			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa o comando para inserir os dados na tabela LIVRO
			stm.executeUpdate(QUERY);

		} catch (SQLException e) {
			System.out.println("Erro ao inserir na tabela LIVRO/AUTORLIVRO");
			e.printStackTrace();
		} finally {
			this.fecha(rs, stm, con);
		}

	}

	public void excluir(Livro livro) {

		// cria um comando DELETE usando o id do Livro
		String QUERY = "delete FROM livro WHERE  isbn = " + livro.getIsbn();

		try {
			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa o comando para excluir o livro da tabela
			stm.executeUpdate(QUERY);

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "Erro ao tentar excluir livro");

		} finally {
			this.fecha(rs, stm, con);
		}
	}

	public void modificar(Livro livro) {

		// cria um comando para excluir todos os autores do livro
		String QUERY1 = "DELETE FROM AUTORLIVRO WHERE  ISBN_LIVRO = " + livro.getIsbn();

		// cria um comando UPDATE usando os atributos de livro

		String QUERY2 = "UPDATE livro set titulo = '" + livro.getTitulo() + "', anoedicao = " + livro.getAnoEdicao()
				+ ", edicao = " + livro.getEdicao() + ", editora = '" + livro.getEditora() + "', situacao = '"
				+ livro.getSituacao() + "', preco = " + livro.getPrecoLivro() + ", dataCompra = '"
				+ dataBanco.format(livro.getDataCompra()) + "' WHERE  isbn = " + livro.getIsbn();

		try {

			con = Conexao.criarConexao();
			stm = con.createStatement();

			// exclui autores do livro
			stm.executeUpdate(QUERY1);

			// executa o comando para modificar os dados na tabela
			stm.executeUpdate(QUERY2);

		} catch (SQLException e) {
			System.out.println("Erro ao modificar a tabela");

		} finally {
			this.fecha(rs, stm, con);
		}
	}

	public List<Livro> buscarTodos() {

		// declara um ArrayList para receber os dados da tabela
		List<Livro> lista = new ArrayList<Livro>();

		String QUERY = "SELECT * FROM livro ORDER BY isbn";

		Livro livro = null;

		try {

			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa a consulta de todos os registros

			rs = stm.executeQuery(QUERY);

			// percorre o ResultSet lendo os dados de Livro

			while (rs.next()) {

				// cria um livro com os dados de um registro
				int isbn = rs.getInt("isbn");
				String titulo = rs.getString("titulo");
				int anoEdicao = rs.getInt("anoedicao");
				int edicao = rs.getInt("edicao");
				String editora = rs.getString("editora");
				char situacao = rs.getString("situacao").charAt(0);
				double preco = rs.getDouble("preco");
				Date dataCompra = rs.getDate("dataCompra");

				livro = new Livro(isbn, titulo, anoEdicao, edicao, editora, situacao, preco, dataCompra);

				// adiciona o Livro no ArrayList
				lista.add(livro);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar tabela");
			e.printStackTrace();
		} finally {
			this.fecha(rs, stm, con);
		}

		return lista;
	}

	public List<Livro> buscarTodosLivrosNaoEmprestados() {

		// declara um ArrayList para receber os dados da tabela
		List<Livro> lista = new ArrayList<Livro>();

		String QUERY = "SELECT * FROM livro WHERE  situacao = 'N' ORDER BY isbn";

		Livro livro = null;

		try {

			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa a consulta de todos os registros

			rs = stm.executeQuery(QUERY);

			// percorre o ResultSet lendo os dados de Livro

			while (rs.next()) {

				// cria um livro com os dados de um registro
				int isbn = rs.getInt("isbn");
				String titulo = rs.getString("titulo");
				int anoEdicao = rs.getInt("anoedicao");
				int edicao = rs.getInt("edicao");
				String editora = rs.getString("editora");
				char situacao = rs.getString("situacao").charAt(0);
				double preco = rs.getDouble("preco");
				Date dataCompra = rs.getDate("dataCompra");

				livro = new Livro(isbn, titulo, anoEdicao, edicao, editora, situacao, preco, dataCompra);

				// adiciona o Livro no ArrayList
				lista.add(livro);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar tabela");
			e.printStackTrace();
		} finally {
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

	public Livro buscarPorIsbn(Integer isbn) {

		// cria um SELECT para retornar um Livro pelo id
		String QUERY = "SELECT * FROM livro WHERE  isbn = " + isbn;
		Livro livro = null;

		try {
			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa a consulta
			rs = stm.executeQuery(QUERY);

			// cria um objeto Autor com os dados retornados
			if (rs.next()) {

				// cria um Autor com os dados de um registro
				String titulo = rs.getString("titulo");
				int anoEdicao = rs.getInt("anoedicao");
				int edicao = rs.getInt("edicao");
				String editora = rs.getString("editora");
				char situacao = rs.getString("situacao").charAt(0);
				double preco = rs.getDouble("preco");
				Date dataCompra = rs.getDate("dataCompra");

				livro = new Livro(isbn, titulo, anoEdicao, edicao, editora, situacao, preco, dataCompra);

			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar na tabela LIVRO");
		} finally {
			this.fecha(rs, stm, con);
		}
		return livro;
	}

	public void alterarSeLivroEstaEmprestado(Livro livro, char estado) {

		try {

			String QUERY = "update livro set situacao = '" + estado + "' WHERE  isbn = " + livro.getIsbn() + "";

			con = Conexao.criarConexao();

			stm = con.createStatement();
			stm.executeUpdate(QUERY);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.fecha(rs, stm, con);
		}

	}

	// nao tem mais ID, isbn já define o livro unicamente
	public Livro buscarPorID(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Livro buscarLivroPorDados(Livro livro) {

		int isbn = 0;
		Livro livroComIsbn = null;

		// cria um SELECT para retornar um Livro pelo id
		String QUERY = "SELECT * FROM livro WHERE  titulo = '" + livro.getTitulo() + "' and anoedicao = "
				+ livro.getAnoEdicao() + " and edicao = " + livro.getEdicao() + " and editora = '" + livro.getEditora()
				+ "'and situacao = '" + livro.getSituacao() + "'and preco = " + livro.getPrecoLivro()
				+ "and dataCompra = '" + dataBanco.format(livro.getDataCompra()) + "'";

		try {
			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa a consulta
			rs = stm.executeQuery(QUERY);

			// cria um objeto Autor com os dados retornados
			if (rs.next()) {

				// cria um Autor com os dados de um registro
				isbn = rs.getInt("isbn");
				String titulo = rs.getString("titulo");
				int anoEdicao = rs.getInt("anoedicao");
				int edicao = rs.getInt("edicao");
				String editora = rs.getString("editora");
				char situacao = rs.getString("situacao").charAt(0);
				double preco = rs.getDouble("preco");
				Date dataCompra = rs.getDate("dataCompra");

				livroComIsbn = new Livro(isbn, titulo, anoEdicao, edicao, editora, situacao, preco, dataCompra);

			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar na tabela LIVRO");
			e.printStackTrace();
		} finally {
			this.fecha(rs, stm, con);
		}

		return livroComIsbn;
	}

	public ResultSet getRelatorioLivro() throws SQLException {

		Connection con = Conexao.criarConexao();
		String QUERY = "SELECT * FROM livro ORDER BY isbn";

		PreparedStatement stmt = con.prepareStatement(QUERY);

		ResultSet rs = stmt.executeQuery();

		return rs;
	}

	public ResultSet getRelatorioInventarioPorMes(Date dataInicial, Date dataFinal) throws SQLException {

		SimpleDateFormat dataBanco = new SimpleDateFormat("yyyy-MM-dd");
		
		Connection con = Conexao.criarConexao();
		String QUERY = "SELECT month(liv.dataCompra) AS mesLivro,SUM(liv.preco) FROM livro liv WHERE  liv.dataCompra BETWEEN '"+dataBanco.format(dataInicial)+"' AND '"+dataBanco.format(dataFinal)+"' group by month(liv.dataCompra)";
		PreparedStatement stmt = con.prepareStatement(QUERY);

		ResultSet rs = stmt.executeQuery();

		return rs;
	}

	public ResultSet getRelatorioInventarioPorAno(Date dataInicial, Date dataFinal) throws SQLException {

		Connection con = Conexao.criarConexao();
		String QUERY = "SELECT year(liv.dataCompra) AS AnoLivro, SUM(liv.preco) FROM  livro liv WHERE  liv.dataCompra BETWEEN '"+dataBanco.format(dataInicial)+"' AND '"+dataBanco.format(dataFinal)+"' group by year(liv.dataCompra)";
		PreparedStatement stmt = con.prepareStatement(QUERY);

		ResultSet rs = stmt.executeQuery();

		return rs;
	}
	
	public ResultSet getRelatorioInventarioTotal() throws SQLException {

		Connection con = Conexao.criarConexao();
		String QUERY = "SELECT SUM(liv.preco) FROM livro liv";

		PreparedStatement stmt = con.prepareStatement(QUERY);

		ResultSet rs = stmt.executeQuery();

		return rs;
	}
	


}
