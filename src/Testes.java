import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import br.com.biblioteca.dominio.Pessoa;
import br.com.biblioteca.persistencia.LivroDB;
import br.com.biblioteca.persistencia.PessoaDB;
import net.sf.jasperreports.engine.JRException;
import utili.Relatorios;

public class Testes {

	@Test
	public void testGerarRelatorioConsultaLivro() {
		LivroDB livroDB = new LivroDB();
		try {
			Relatorios.gerarRelatorio("relatorios\\livros.jasper", livroDB.getRelatorioLivro());
			assertTrue(true);
		} catch (SQLException e) {
			fail();
		}
	}
	
	@Test
	public void testDigitosTelefone() {
		PessoaDB pessoaDB = new PessoaDB();
		try {
			pessoaDB.inserir(new Pessoa("Andre", "(43) 98503 - 3021", "andre@gmail.com"));
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


}
