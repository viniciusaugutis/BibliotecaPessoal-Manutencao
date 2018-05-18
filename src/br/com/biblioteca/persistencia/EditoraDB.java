package br.com.biblioteca.persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.biblioteca.dominio.Editora;

public class EditoraDB {

	private Connection con;
	private ResultSet rs;
	private Statement stm;

	public EditoraDB() {

	}
	public List<Editora> buscarTodas() {

		// declara um ArrayList para receber os dados da tabela
		List<Editora> lista = new ArrayList<Editora>();

		String QUERY = "SELECT * FROM editora ORDER BY id";

		try {

			con = Conexao.criarConexao();
			stm = con.createStatement();

			// executa a consulta de todos os registros
			rs = stm.executeQuery(QUERY);

			// percorre o ResultSet lendo os dados de Autor
			while (rs.next()) {

				int id = rs.getInt("id");
				String nome = rs.getString("nome");

				// cria um Autor com os dados de um registro
				Editora editora = new Editora(id, nome);

				// adiciona o Autor no ArrayList
				lista.add(editora);
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

}