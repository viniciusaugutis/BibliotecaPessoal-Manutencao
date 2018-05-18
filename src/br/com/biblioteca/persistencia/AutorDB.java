package br.com.biblioteca.persistencia;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import br.com.biblioteca.persistencia.Conexao;
import br.com.biblioteca.dominio.Autor;

public class AutorDB implements GenericDB<Autor, Integer> {

	private Connection con;
	private ResultSet rs;
	private Statement stm;

	public AutorDB() {

	}

	public void inserir(Autor autor) {

		// cria um comando INSERT com os atributos de Autor
		String QUERY = "INSERT INTO autor VALUES (DEFAULT,'" + autor.getNome() + "', '" + autor.getSobreNome() + "')";

		try {
			con = Conexao.criarConexao();
			stm = con.createStatement();
			// executa o comando para inserir os dados na tabela
			stm.executeUpdate(QUERY);

		} catch (SQLException e) {
			System.out.println("Erro ao inserir na tabela");

		} finally {
			this.fecha(rs, stm, con);

		}
	}

	public void excluir(Autor autor) {

		// cria um comando DELETE usando o id do Autor
		String QUERY = "DELETE FROM autor WHERE id = " + autor.getId();

		try {

			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa o comando para excluir o autor da tabela
			stm.executeUpdate(QUERY);

		} catch (SQLException e) {

			JOptionPane meuJOPane = new JOptionPane("Autor está presente em um livro");//
			final JDialog dialog = meuJOPane.createDialog(null, "Atenção!");

			dialog.setModal(true);

			Timer timer = new Timer(3 * 1000, new ActionListener() {

				public void actionPerformed(ActionEvent ev) {
					dialog.dispose();

				}

			});

			timer.start();
			dialog.setVisible(true);
			timer.stop();

		} finally {
			this.fecha(rs, stm, con);
		}
	}

	public void modificar(Autor autor) {
		// cria um comando UPDATE usando os atributos de Autor

		String QUERY = "UPDATE autor SET nome = '" + autor.getNome() + "', sobreNome = '" + autor.getSobreNome()
				+ "' WHERE id = " + autor.getId();

		try {
			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa o comando para modificar os dados na tabela
			stm.executeUpdate(QUERY);

		} catch (SQLException e) {
			System.out.println("Erro ao inserir na tabela");
		} finally {
			this.fecha(rs, stm, con);
		}
	}

	public List<Autor> buscarTodos() {

		// declara um ArrayList para receber os dados da tabela
		List<Autor> lista = new ArrayList<Autor>();

		String QUERY = "SELECT * FROM autor ORDER BY id";

		try {

			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa a consulta de todos os registros
			rs = stm.executeQuery(QUERY);

			// percorre o ResultSet lendo os dados de Autor
			while (rs.next()) {

				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String sobreNome = rs.getString("sobreNome");

				// cria um Autor com os dados de um registro
				Autor autor = new Autor(id, nome, sobreNome);

				// adiciona o Autor no ArrayList
				lista.add(autor);
			}

		} catch (SQLException e) {

			System.out.println("Erro ao consultar tabela");

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

	public Autor buscarPorID(Integer id) {

		// cria um SELECT para retornar um Autor pelo id
		String QUERY = "SELECT * FROM autor WHERE id = " + id + " ORDER BY id";

		String nome;
		String sobreNome;
		Autor autor = null;

		try {
			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa a consulta
			rs = stm.executeQuery(QUERY);

			// cria um objeto Autor com os dados retornados

			if (rs.next()) {
				id = rs.getInt("id");
				nome = rs.getString("nome");
				sobreNome = rs.getString("sobreNome");
				autor = new Autor(id, nome, sobreNome);
			}

		} catch (SQLException e) {
			System.out.println("Erro ao consultar na tabela");

		} finally {

			this.fecha(rs, stm, con);
		}

		return autor;

	}

	public ResultSet getRelatorioAutor() throws SQLException {

		Connection con = Conexao.criarConexao();
		String QUERY = "SELECT * FROM autor ORDER BY id";

		PreparedStatement stmt = con.prepareStatement(QUERY);

		ResultSet rs = stmt.executeQuery();
		
		
		return rs;
	}
}