
package br.com.biblioteca.ui;

import java.util.Iterator;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;

import br.com.biblioteca.dominio.LivroExcluido;
import br.com.biblioteca.dominio.Livro;
import br.com.biblioteca.persistencia.AutorLivroDB;
import br.com.biblioteca.persistencia.EmprestimoDB;
import br.com.biblioteca.persistencia.LivroExcluidoDB;
import br.com.biblioteca.persistencia.LivroDB;
import utili.AplicaLookAndFeel;
import utili.Limitador;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class LivroUI extends JInternalFrame {

	private JPanel contentPane;
	private JTable tbLivro;
	private DefaultTableModel modelo = new DefaultTableModel();
	private List<Livro> lista;
	JButton btnRemover = new JButton("");
	JButton btnAlterar = new JButton("");

	// metodo construtor
	public LivroUI() {

		AplicaLookAndFeel.pegaLookAndFeel();

		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);

		setTitle("Livro");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		btnRemover.setToolTipText("Remover");
		btnRemover.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/delete2.png")));

		// Instancia os botoes

		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tbLivro.getSelectedRow() != -1) {

					if (JOptionPane.showConfirmDialog(null, "Confirma a exclusão do registro?", "Exclusão",
							JOptionPane.YES_NO_OPTION) == 0) {

						LivroDB livroDB = new LivroDB();

						EmprestimoDB emprestimoDB = new EmprestimoDB();

						// Excluir livro mas antes verifica se ta em um
						// emprestimo, se nao tiver entra no if

						if (emprestimoDB.buscarSeTemEmprestimo(lista.get(tbLivro.getSelectedRow())) == 1) {

							// exclui emprestimo do livro, que já foi devolvido
							emprestimoDB.excluirEmprestimoLivro(lista.get(tbLivro.getSelectedRow()));

							// remove livros do banco de dados de Livro
							try {

								// Antes de excluir livro, salva em um
								// livroExcl para futuras pesquisas do livro
								// excluido
								LivroExcluido livroExcl = new LivroExcluido();
								livroExcl.setIsbnLivro(lista.get(tbLivro.getSelectedRow()).getIsbn());
								livroExcl.setPrecoLivro((lista.get(tbLivro.getSelectedRow()).getPrecoLivro()));
								livroExcl.setDataCompra((lista.get(tbLivro.getSelectedRow()).getDataCompra()));

								LivroExcluidoDB livroExclDB = new LivroExcluidoDB();

								livroExclDB.inserirLivro((livroExcl));

								remover(lista.get(tbLivro.getSelectedRow()));

							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						} else {

							JOptionPane meuJOPane = new JOptionPane("Livro presente em um emprestimo",
									JOptionPane.ERROR_MESSAGE);//
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

						}

						atualizaTabela();

					} else {
						JOptionPane meuJOPane = new JOptionPane("Selecione um registro na tabela",
								JOptionPane.ERROR_MESSAGE);//
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
					}

				}
			}

		});

		btnAlterar.setToolTipText("Alterar");
		btnAlterar.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/alterar2.png")));

		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tbLivro.getSelectedRow() != -1) {
					CadastroLivroUI cadastroLivro = new CadastroLivroUI(1, lista.get(tbLivro.getSelectedRow()));

					BibliotecaPessoal b = BibliotecaPessoal.frame;
					b.desktopPane.add(cadastroLivro);
					cadastroLivro.setVisible(true);

					setVisible(false);

				} else {
					JOptionPane meuJOPane = new JOptionPane("Selecione um registro na tabela",JOptionPane.ERROR_MESSAGE);//
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
				}

			}
		});

		JButton btnNovo = new JButton("");
		btnNovo.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/novo5.png")));
		btnNovo.setToolTipText("Novo");

		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CadastroLivroUI cadastroLivro = new CadastroLivroUI(0, null);

				BibliotecaPessoal b = BibliotecaPessoal.frame;
				b.desktopPane.add(cadastroLivro);
				cadastroLivro.setVisible(true);

				setVisible(false);

			}
		});

		JButton btnVoltar = new JButton("");
		btnVoltar.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/voltar2.png")));
		btnVoltar.setToolTipText("Voltar");

		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		// Define o scrollPane e seus argumentos;
		JScrollPane scrollPane = new JScrollPane();

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(
						gl_contentPane
								.createParallelGroup(
										Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 404,
														Short.MAX_VALUE)
										.addGroup(Alignment.TRAILING,
												gl_contentPane.createSequentialGroup().addComponent(btnVoltar)
														.addGap(18).addComponent(btnNovo).addGap(18)
														.addComponent(btnAlterar).addGap(18).addComponent(btnRemover)))
				.addContainerGap()));
		gl_contentPane
				.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(13)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(btnNovo, GroupLayout.PREFERRED_SIZE, 44,
												GroupLayout.PREFERRED_SIZE)
								.addComponent(btnAlterar, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnVoltar, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnRemover, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
				.addGap(24)));

		// Instanciando a tabela livros com cabeçario, tamanho

		String[] nomesColuna = { "ISBN", "Título", "Editora", "Data Compra" };
		modelo.setColumnIdentifiers(nomesColuna);
		tbLivro = new JTable(modelo);
		tbLivro.getColumnModel().getColumn(0).setPreferredWidth(100);
		tbLivro.getColumnModel().getColumn(1).setPreferredWidth(300);
		tbLivro.getColumnModel().getColumn(2).setPreferredWidth(100);
		tbLivro.getColumnModel().getColumn(3).setPreferredWidth(200);

		tbLivro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnAlterar.setEnabled(true);
				btnRemover.setEnabled(true);

			}
		});

		tbLivro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnAlterar.setEnabled(true);
				btnRemover.setEnabled(true);
			}
		});

		// define que a tabelaLivro fica no viewReportView do scrollPane
		scrollPane.setViewportView(tbLivro);
		contentPane.setLayout(gl_contentPane);

		atualizaTabela();

	}

	private void atualizaTabela() {

		btnRemover.setEnabled(false);
		btnAlterar.setEnabled(false);

		LivroDB livroDB = new LivroDB();

		lista = livroDB.buscarTodos();

		// Cria um iterator para buscar dados no livro
		Iterator<Livro> it = lista.iterator();

		Livro a;

		while (modelo.getRowCount() > 0) { // se tem linhas ele zera tudo para
											// evitar duplicidade
			modelo.removeRow(0);
		}

		while (it.hasNext()) {
			a = it.next();

			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			modelo.addRow(
					new Object[] { a.getIsbn(), a.getTitulo(), a.getEditora(), formato.format(a.getDataCompra()) });
		}
	}

	public void remover(Livro livro) throws SQLException {

		LivroDB livrodb = new LivroDB();
		AutorLivroDB autorlivrodb = new AutorLivroDB();

		autorlivrodb.removerAutoresLivro(livro);
		livrodb.excluir(livro);

	}
}
