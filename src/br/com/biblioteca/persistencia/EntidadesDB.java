package br.com.biblioteca.persistencia;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class EntidadesDB {

	private Connection con;
	private ResultSet rs;
	private Statement stm;

	public EntidadesDB() {

		try {

			con = Conexao.criarConexao();

			DatabaseMetaData dbmd = con.getMetaData();

			// verifica se a tabela AUTOR já existe no BD

			rs = dbmd.getTables(null, null, "AUTOR", new String[] { "TABLE" });

			if (!rs.next()) {
				stm = con.createStatement();

				// se não houver uma tabela, ela é criada
				stm.executeUpdate(
						"CREATE TABLE autor (id int not null generated always as identity primary key, nome VARCHAR(40), sobreNome VARCHAR(40))");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane meuJOPane = new JOptionPane("Software ou BD já esta em aberto em outra janela", JOptionPane.ERROR_MESSAGE);//
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
			
			System.exit(0);

		} finally {

			// fecha os recursos e a conexão com o BD

			this.fecha(rs, stm, con);

		}

		try {

			con = Conexao.criarConexao();
			DatabaseMetaData dbmd = con.getMetaData();

			// verifica se a tabela LIVRO já existe no BD
			rs = dbmd.getTables(null, null, "LIVRO", new String[] { "TABLE" });

			if (!rs.next()) {

				stm = con.createStatement();

				// se não houver uma tabela, ela é criada
				String QUERY = "CREATE TABLE livro (isbn int generated always as identity primary key,titulo varchar(100) not null, anoedicao int, edicao int, editora varchar(100) not null, situacao char(1) not null, preco double not null, dataCompra Date not null)";

				stm.executeUpdate(QUERY);

			}
			// verifica se a tabela LIVROAUTOR já existe no BD
			rs = dbmd.getTables(null, null, "AUTORLIVRO", new String[] { "TABLE" });

			if (!rs.next()) {

				stm = con.createStatement();
				// se não houver uma tabela, ela é criada

				// TROCAR O TIPO SET PARA UM ARRAY DE AUTORES

				String QUERY = "CREATE TABLE AUTORLIVRO(ISBN_LIVRO int not null, ID_AUTOR int not null, PRIMARY KEY (ISBN_LIVRO,ID_AUTOR), CONSTRAINT ISBN_LIVRO FOREIGN KEY (ISBN_LIVRO) REFERENCES livro (isbn),  CONSTRAINT ID_AUTOR FOREIGN KEY (ID_AUTOR) REFERENCES autor(id))";
				stm.executeUpdate(QUERY);

			}

		} catch (SQLException e) {
			System.out.println("autor\n");

		} finally {

			// fecha os recursos e a conexão com o BD
			this.fecha(rs, stm, con);
		}

		try {

			con = Conexao.criarConexao();

			DatabaseMetaData dbmd = con.getMetaData();

			// verifica se a tabela Pessoa já existe no BD

			rs = dbmd.getTables(null, null, "PESSOA", new String[] { "TABLE" });

			if (!rs.next()) {
				stm = con.createStatement();

				// se não houver uma tabela, ela é criada
				stm.executeUpdate(
						"CREATE TABLE pessoa (id int not null generated always as identity primary key, nome VARCHAR(40) not null, telefone VARCHAR(40) not null, email VARCHAR(100) not null)");
			}

		} catch (SQLException e) {

			System.out.println("pessoa\n");
			e.printStackTrace();

		} finally {

			// fecha os recursos e a conexão com o BD

			this.fecha(rs, stm, con);
		}

		try {
			con = Conexao.criarConexao();

			DatabaseMetaData dbmd = con.getMetaData();

			// verifica se a tabela EMPRESTIMO já existe no BD
			rs = dbmd.getTables(null, null, "EMPRESTIMO", new String[] { "TABLE" });

			if (!rs.next()) {
				stm = con.createStatement();

				// se não houver uma tabela, ela é criada
				String QUERY = "CREATE TABLE EMPRESTIMO (ID INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,ISBN_LIVRO INT REFERENCES LIVRO, ID_PESSOA INT REFERENCES PESSOA, DATA_EMPRESTIMO DATE, DATA_DEVOLUCAO DATE)";
				stm.executeUpdate(QUERY);

			}
		} catch (SQLException e) {
			System.out.println("emprestimo\n");

		} finally {

			// fecha os recursos e a conexão com o BD
			this.fecha(rs, stm, con);
		}

		try {

			con = Conexao.criarConexao();

			DatabaseMetaData dbmd = con.getMetaData();

			// verifica se a tabela Inventario já existe no BD

			rs = dbmd.getTables(null, null, "LIVROEXCLUIDO", new String[] { "TABLE" });

			if (!rs.next()) {
				stm = con.createStatement();

				// se não houver uma tabela, ela é criada
				stm.executeUpdate(
						"CREATE TABLE LIVROEXCLUIDO (idLivroExcluido int not null generated always as identity primary key, ISBN_LIVRO int not null, precoLivro double not null, dataCompra Date not null)");
			}

		} catch (SQLException e) {
			System.out.println("Livro Excluido\n");
			e.printStackTrace();

		} finally {

			// fecha os recursos e a conexão com o BD

			this.fecha(rs, stm, con);

		}

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
				e.printStackTrace();

			}
		}
	}

}
