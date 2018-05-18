package br.com.biblioteca.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.biblioteca.dominio.AutorLivro;
import br.com.biblioteca.dominio.Livro;

public class AutorLivroDB{

	private Connection con;
	private ResultSet rs;
	private Statement stm;
	
	public void adicionarAutorLivro(AutorLivro autorLivro) throws SQLException {
		
	
        String QUERY = "INSERT INTO AUTORLIVRO  VALUES ("+autorLivro.getLivro().getIsbn()+",+"+autorLivro.getAutor().getId()+")";
        
        con = Conexao.criarConexao();
		stm = con.createStatement();
		
		stm.executeUpdate(QUERY);
        
        
        this.fecha(rs, stm, con);
    }

	public void removerAutoresLivro(Livro livro) throws SQLException {
	
		String s = "DELETE FROM AUTORLIVRO WHERE ISBN_LIVRO = " + livro.getIsbn();
				
		try {
					
			con = Conexao.criarConexao();
			stm = con.createStatement();
				
			stm.executeUpdate(s);
					
		} catch (SQLException e) {
					
			System.out.println("Erro ao tentar excluir na tabela");
				
		} finally {
			this.fecha(rs, stm, con);
		}
	}
	
	public List<Integer> retornaAutoresLivro(Livro livro) throws SQLException {
		
		
        String QUERY = "SELECT autor.id from AUTORLIVRO, autor WHERE AUTORLIVRO.ID_AUTOR = autor.id AND AUTORLIVRO.ISBN_LIVRO = "+livro.getIsbn()+"";
       
        con = Conexao.criarConexao();
		stm = con.createStatement();
		
        rs = stm.executeQuery(QUERY);
		
        
        ArrayList<Integer> listaAutorLivro = new ArrayList<Integer>(); 
        
		while (rs.next()) {
				
			// cria um autorlivro com os dados de um registro
			 listaAutorLivro.add(rs.getInt("id")); 
		}  
        
        this.fecha(rs, stm, con);
                
		return listaAutorLivro;
    }

	
	public void fecha(ResultSet rs, Statement stm, Connection con) {
	
		if (rs != null) {
			
			try {
				
				rs.close();
			
			} catch (SQLException e){
				
			}
		}
		
		
		if (stm != null) {
			try {
				stm.close();
			} catch (SQLException e){
			}
			
		}
		
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e){
			}
		}
	}
	
	public ResultSet getRelatorioAutoresLivro() throws SQLException {

		Connection con = Conexao.criarConexao();
		String QUERY = "SELECT livro.titulo, autor.nome from autorlivro, autor, livro WHERE autorlivro.isbn_livro = livro.isbn AND autorlivro.id_autor = autor.id ORDER BY isbn";

		PreparedStatement stmt = con.prepareStatement(QUERY);

		ResultSet rs = stmt.executeQuery();
		
		
		return rs;
	}

	
	
}
