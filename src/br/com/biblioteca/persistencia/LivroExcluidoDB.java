package br.com.biblioteca.persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.biblioteca.dominio.LivroExcluido;

public class LivroExcluidoDB {

	private Connection con;
	private Statement stm;
	private ResultSet rs;

	public void inserirLivro(LivroExcluido livroExcluido) {

		try {

			String QUERY = "INSERT INTO livroExcluido VALUES (DEFAULT," + livroExcluido.getIsbnLivro() + ","
					+ livroExcluido.getPrecoLivro() + ",'" + livroExcluido.getDataCompra() + "')";

			con = Conexao.criarConexao();
			stm = con.createStatement();
			stm.executeUpdate(QUERY);

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			this.fecha(rs, stm, con);
		}

	}

	public List<LivroExcluido> buscarTodos() {

		ArrayList<LivroExcluido> lista = new ArrayList<LivroExcluido>();

		try {

			String QUERY = "SELECT* FROM livroExcluido ORDER BY idlivroExcluido";

			con = Conexao.criarConexao();
			stm = con.createStatement();

			rs = stm.executeQuery(QUERY);

			while (rs.next()) {

				int isbnLivro = rs.getInt("isbn_Livro");
				double precoLivro = rs.getDouble("precoLivro");
				Date dataCompra = rs.getDate("dataCompra");

				LivroExcluido invent = new LivroExcluido();
				invent.setIsbnLivro(isbnLivro);
				invent.setDataCompra(dataCompra);
				invent.setPrecoLivro(precoLivro);

				lista.add(invent);
			}

		} catch (SQLException e) {

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
