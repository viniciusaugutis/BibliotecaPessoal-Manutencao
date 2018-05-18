package br.com.biblioteca.persistencia;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
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
import br.com.biblioteca.dominio.Pessoa;

public class PessoaDB implements GenericDB<Pessoa, Integer> {

	private Connection con;
	private ResultSet rs;
	private Statement stm;

	public PessoaDB() {

	}

	public void inserir(Pessoa pessoa) {

		// cria um comANDo INSERT com os atributos de Pessoa
		String QUERY = "INSERT INTO Pessoa VALUES (DEFAULT,'" + pessoa.getNome() + "', '" + pessoa.getTelefone() + "','"
				+ pessoa.getEmail() + "')";

		try {
			con = Conexao.criarConexao();
			stm = con.createStatement();
			// executa o comANDo para inserir os dados na tabela
			stm.executeUpdate(QUERY);

		} catch (SQLException e) {
			System.out.println("Erro ao inserir na tabela");
			e.printStackTrace();

		} finally {
			this.fecha(rs, stm, con);
		}
	}

	public void excluir(Pessoa pessoa) {

		// cria um comANDo DELETE usANDo o id do Pessoa
		String QUERY = "delete FROM Pessoa WHERE id = " + +pessoa.getId();

		try {

			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa o comANDo para excluir a Pessoa da tabela
			stm.executeUpdate(QUERY);

		} catch (SQLException e) {

			JOptionPane meuJOPane = new JOptionPane("Pessoa está em um emprestimo");//
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

	public void modificar(Pessoa pessoa) {
		// cria um comANDo UPDATE usANDo os atributos de Pessoa

		String QUERY = "UPDATE Pessoa SET nome = '" + pessoa.getNome() + "', telefone= '" + pessoa.getTelefone()
				+ "', email = '" + pessoa.getEmail() + "'  WHERE id = " + pessoa.getId();

		try {
			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa o comANDo para modificar os dados na tabela
			stm.executeUpdate(QUERY);

		} catch (SQLException e) {
			System.out.println("Erro ao inserir na tabela");
		} finally {
			this.fecha(rs, stm, con);
		}
	}

	public List<Pessoa> buscarTodos() {

		// declara um ArrayList para receber os dados da tabela
		List<Pessoa> lista = new ArrayList<Pessoa>();

		String QUERY = "SELECT * FROM Pessoa ORDER BY id";

		try {

			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa a consulta de todos os registros
			rs = stm.executeQuery(QUERY);

			// percorre o ResultSet lendo os dados de Pessoa
			while (rs.next()) {

				int id = rs.getInt("id");
				String nome = rs.getString("nome");

				String telefone = rs.getString("telefone");
				String email = rs.getString("email");

				// cria um Pessoa com os dados de um registro
				Pessoa pessoa = new Pessoa(id, nome, telefone, email);

				// adiciona o Pessoa no ArrayList
				lista.add(pessoa);
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

	public Pessoa buscarPorID(Integer id) {

		// cria um SELECT para retornar um Pessoa pelo id
		String QUERY = "SELECT * FROM pessoa WHERE id = " + id;

		String nome;
		Pessoa pessoa = null;

		try {
			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa a consulta
			rs = stm.executeQuery(QUERY);

			// cria um objeto Pessoa com os dados retornados

			if (rs.next()) {
				id = rs.getInt("ID");
				nome = rs.getString("NOME");

				String telefone = rs.getString("telefone");
				String email = rs.getString("email");

				pessoa = new Pessoa(id, nome, telefone, email);
			}

		} catch (SQLException e) {
			System.out.println("Erro ao consultar na tabela");
			e.printStackTrace();

		} finally {

			this.fecha(rs, stm, con);
		}

		return pessoa;

	}

	public ResultSet getRelatorioPessoa() throws SQLException {

		Connection con = Conexao.criarConexao();
		String QUERY = "SELECT * FROM pessoa ORDER BY id";

		PreparedStatement stmt = con.prepareStatement(QUERY);

		ResultSet rs = stmt.executeQuery();

		return rs;
	}

}