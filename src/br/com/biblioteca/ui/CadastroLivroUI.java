package br.com.biblioteca.ui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import br.com.biblioteca.dominio.Autor;
import br.com.biblioteca.dominio.AutorLivro;
import br.com.biblioteca.dominio.Livro;
import br.com.biblioteca.persistencia.AutorDB;
import br.com.biblioteca.persistencia.AutorLivroDB;
import br.com.biblioteca.persistencia.LivroDB;
import utili.AplicaLookAndFeel;
import utili.Limitador;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;

import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import org.jdesktop.swingx.JXDatePicker;

@SuppressWarnings("serial")
public class CadastroLivroUI extends JInternalFrame {

	private JPanel PainelPrincipal;
	private JTextField txTitulo;
	private JTextField txEditora;
	private DefaultTableModel modelo = new DefaultTableModel();
	JSpinner spAnoEdicao = new JSpinner();
	JSpinner spEdicao = new JSpinner();
	JCheckBox chbxSituacao = new JCheckBox("");
	final JButton btnExcluir = new JButton("Excluir");
	private JTable tbAutores;
	private JFormattedTextField tfPreco = new JFormattedTextField();
	private JXDatePicker txfData = new JXDatePicker();
	private JComboBox cbxAutor = new JComboBox();
	private JLabel lbTitulo = new JLabel("Titulo:*");
	private int tipo; // tipo 0 = inserir, 1 = alterar
	private Livro livro;
	private JLabel lbEditora = new JLabel("Editora:*");
	private JLabel lbPreco = new JLabel("Preco:*");
	private JLabel lbData = new JLabel("Data Compra:*");
	private JLabel lbAutor = new JLabel("Autor:*");

	public CadastroLivroUI(int tipo, Livro livro) {

		if (tipo == 0) {
			setTitle("Cadastrar Livro");
		} else {
			setTitle("Alterar Livro");
		}

		this.tipo = tipo;
		this.livro = livro;

		AplicaLookAndFeel.pegaLookAndFeel();

		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);

		chbxSituacao.setEnabled(false);

		tfPreco.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
				new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

		setBounds(100, 100, 630, 454);
		PainelPrincipal = new JPanel();
		PainelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(PainelPrincipal);

		JButton btnCancelar = new JButton("");
		btnCancelar.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/deletar.png")));
		btnCancelar.setToolTipText("Cancelar");

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				LivroUI livroui = new LivroUI();
				BibliotecaPessoal b = BibliotecaPessoal.frame;
				b.desktopPane.add(livroui);
				livroui.setVisible(true);

				setVisible(false);

			}
		});

		JButton btnSalvar = new JButton("");
		btnSalvar.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/salvar.png")));
		btnSalvar.setToolTipText("Salvar");

		btnSalvar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				LivroDB livroDB = new LivroDB();
				// cria um Set com os autores inseridos no JTable
				Set<Autor> autores = new HashSet<Autor>();
				for (int i = 0; i < modelo.getRowCount(); i++) {
					autores.add(new Autor((Integer) tbAutores.getValueAt(i, 0), (String) tbAutores.getValueAt(i, 1),
							(String) tbAutores.getValueAt(i, 2)));
				}

				if (CadastroLivroUI.this.tipo == 0) {

					if (verificaCampos()) {
						// inserir um novo livro

						Livro livroAcabadoDeCriar = new Livro(0, txTitulo.getText(),
								Integer.parseInt(spAnoEdicao.getModel().getValue().toString()),
								Integer.parseInt(spEdicao.getModel().getValue().toString()), txEditora.getText(),
								(chbxSituacao.isSelected()) ? 'S' : 'N',
								Double.parseDouble(tfPreco.getText().replace(',', '.')), txfData.getDate());

						livroDB.inserir(livroAcabadoDeCriar);

						AutorLivro autorlivro = new AutorLivro();
						LivroDB livrodb = new LivroDB();
						AutorDB autordb = new AutorDB();

						Autor a = null;

						Iterator<Autor> it = autores.iterator();

						while (it.hasNext()) {
							a = it.next();

							AutorLivroDB autorlivrodb = new AutorLivroDB();

							autorlivro.setAutor(a);

							autorlivro.setLivro(livrodb.buscarLivroPorDados(livroAcabadoDeCriar));

							try {
								autorlivrodb.adicionarAutorLivro(autorlivro);

							} catch (SQLException e1) {

								e1.printStackTrace();
							}
						}

						LivroUI livroui = new LivroUI();
						BibliotecaPessoal b = BibliotecaPessoal.frame;
						b.desktopPane.add(livroui);
						livroui.setVisible(true);

						setVisible(false);
					}

				} else {

					if (verificaCampos()) {
						// modificar um livro existente

						CadastroLivroUI.this.livro.setTitulo(txTitulo.getText());
						CadastroLivroUI.this.livro
								.setAnoEdicao(Integer.parseInt(spAnoEdicao.getModel().getValue().toString()));
						CadastroLivroUI.this.livro
								.setEdicao(Integer.parseInt(spEdicao.getModel().getValue().toString()));
						CadastroLivroUI.this.livro.setEditora(txEditora.getText());
						CadastroLivroUI.this.livro.setSituacao(chbxSituacao.isSelected() ? 'S' : 'N');

						livroDB.modificar(CadastroLivroUI.this.livro);

						AutorLivroDB autorlivrodb = new AutorLivroDB();

						// Deleta todos os autores daquele livro para criar
						// novos
						try {
							autorlivrodb.removerAutoresLivro(CadastroLivroUI.this.livro);
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}

						AutorLivro autorlivro = new AutorLivro();
						LivroDB livrodb = new LivroDB();

						Autor a = null;

						Iterator<Autor> it = autores.iterator();

						while (it.hasNext()) {
							a = it.next();

							autorlivro.setAutor(a);
							autorlivro.setLivro(CadastroLivroUI.this.livro);

							try {
								autorlivrodb.adicionarAutorLivro(autorlivro);

							} catch (SQLException e1) {

								e1.printStackTrace();
							}
						}

						LivroUI livroui = new LivroUI();
						BibliotecaPessoal b = BibliotecaPessoal.frame;
						b.desktopPane.add(livroui);
						livroui.setVisible(true);

						setVisible(false);
					}
				}

			}
		});

		JPanel painelDados = new JPanel();

		painelDados.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				this.tipo == 0 ? "Cadastrar Livro" : "Alterar Livro", TitledBorder.LEADING, TitledBorder.TOP, null,
				null));

		JPanel panel = new JPanel();
		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		panel.setBorder(new TitledBorder(null, "Autores", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_PainelPrincipal = new GroupLayout(PainelPrincipal);
		gl_PainelPrincipal.setHorizontalGroup(gl_PainelPrincipal.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_PainelPrincipal.createSequentialGroup()
						.addGroup(gl_PainelPrincipal.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING,
										gl_PainelPrincipal.createSequentialGroup().addContainerGap()
												.addComponent(btnSalvar).addGap(18).addComponent(btnCancelar))
						.addComponent(painelDados, GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap(34, Short.MAX_VALUE)));
		gl_PainelPrincipal
				.setVerticalGroup(
						gl_PainelPrincipal.createParallelGroup(Alignment.TRAILING)
								.addGroup(
										gl_PainelPrincipal.createSequentialGroup()
												.addComponent(painelDados, GroupLayout.DEFAULT_SIZE, 202,
														Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(panel, GroupLayout.PREFERRED_SIZE, 151,
														GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_PainelPrincipal.createParallelGroup(Alignment.LEADING)
										.addComponent(btnCancelar, GroupLayout.PREFERRED_SIZE, 39,
												GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));

		// Scroll da tabela de autores
		JScrollPane scrollPane = new JScrollPane();

		// define as colunas da tabela
		String[] nomesColuna = { "Código", "Nome", "Sobrenome" };
		modelo.setColumnIdentifiers(nomesColuna);
		// instancia a tabela e adiciona ao JScrollPane
		tbAutores = new JTable(modelo);

		scrollPane.setViewportView(tbAutores);

		tbAutores.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnExcluir.setEnabled(true);
			}
		});

		tbAutores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnExcluir.setEnabled(true);

			}
		});

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup()
						.addContainerGap().addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE).addContainerGap()));
		panel.setLayout(gl_panel);

		lbTitulo.setFont(new Font("Tahoma", Font.PLAIN, 12));

		txTitulo = new JTextField();
		txTitulo.setColumns(10);

		JLabel lbAnoEdicao = new JLabel("Ano da Edi\u00E7\u00E3o:");
		lbAnoEdicao.setFont(new Font("Tahoma", Font.PLAIN, 12));

		spAnoEdicao.setModel(new SpinnerNumberModel(2000, 1900, 2050, 1));

		JLabel lbEdicao = new JLabel("Edi\u00E7\u00E3o:");
		lbEdicao.setFont(new Font("Tahoma", Font.PLAIN, 12));

		spEdicao.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));

		lbEditora.setFont(new Font("Tahoma", Font.PLAIN, 12));

		txEditora = new JTextField();
		txEditora.setColumns(10);

		JLabel lbEmprestado = new JLabel("Emprestado");
		lbEmprestado.setFont(new Font("Tahoma", Font.PLAIN, 12));

		lbAutor.setFont(new Font("Tahoma", Font.PLAIN, 12));

		List<Autor> lista = new ArrayList();

		AutorDB autorDB = new AutorDB();
		List<Autor> autores = autorDB.buscarTodos();

		Iterator<Autor> it = autores.iterator();

		Autor a;

		while (it.hasNext()) {
			a = it.next();
			lista.add(a);
		}
		
		List<Autor> listaOriginal = new ArrayList<>();
		
		for (Autor autor : lista) {
			listaOriginal.add(autor);
		}

		cbxAutor.addItem("<Selecione>");

		for (int j = 0; j < lista.size(); j++) {
			cbxAutor.addItem(lista.get(j).getNome());
		}

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if ((lista.size() != 0) && (cbxAutor.getSelectedIndex() != 0)) {

					AutorDB autordb = new AutorDB();

					// busca o objeto selecionado no JComboBox
					int indexAutorAdicionado = cbxAutor.getSelectedIndex();
					Autor a = autordb.buscarPorID(lista.get(cbxAutor.getSelectedIndex() - 1).getId());
					

					// adiciona o Id e o Nome do autor ao model do JTable

					boolean jaTemAutor = false;

					if (modelo.getRowCount() == 0) {
						modelo.addRow(new Object[] { a.getId(), a.getNome(), a.getSobreNome() });
						lista.remove(indexAutorAdicionado - 1);
						cbxAutor.removeItemAt(indexAutorAdicionado);
					} else {

						for (int i = 0; i < modelo.getRowCount(); i++) {

							if (tbAutores.getValueAt(i, 0).equals(a.getId())) {
								jaTemAutor = true;

							}

						}

						if (jaTemAutor == false) {
							modelo.addRow(new Object[] { a.getId(), a.getNome(), a.getSobreNome() });
							lista.remove(indexAutorAdicionado - 1);
							cbxAutor.removeItemAt(indexAutorAdicionado);
							
						} else {

							JOptionPane meuJOPane = new JOptionPane("Autor já adicionado no Livro",JOptionPane.ERROR_MESSAGE);//
							final JDialog dialog = meuJOPane.createDialog(null, "Atenção!");

							dialog.setModal(true);

							Timer timer = new Timer(2 * 1000, new ActionListener() {

								public void actionPerformed(ActionEvent ev) {
									dialog.dispose();

								}

							});
							timer.start();
							dialog.setVisible(true);
							timer.stop();

						}
					}
				} else {

					JOptionPane meuJOPane = new JOptionPane("Não existem autores cadastrados ou opção inválida",JOptionPane.ERROR_MESSAGE);//
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

		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int idAutor = Integer.parseInt(tbAutores.getModel().getValueAt(tbAutores.getSelectedRow(), 0).toString());
				modelo.removeRow(tbAutores.getSelectedRow());
				
				for (Autor autor : listaOriginal) {
					if (autor.getId() == idAutor) {
						cbxAutor.addItem(autor.getNome());
					}
				}
				tbAutores.validate();
				btnExcluir.setEnabled(false);
			}

		});
		btnExcluir.setEnabled(false);

		lbPreco.setFont(new Font("Tahoma", Font.PLAIN, 12));

		lbData.setFont(new Font("Tahoma", Font.PLAIN, 12));

		GroupLayout gl_painelDados = new GroupLayout(painelDados);
		gl_painelDados.setHorizontalGroup(gl_painelDados.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDados.createSequentialGroup().addContainerGap()
						.addGroup(gl_painelDados.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_painelDados.createSequentialGroup().addComponent(lbTitulo)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(txTitulo, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE))
						.addGroup(gl_painelDados.createSequentialGroup()
								.addGroup(gl_painelDados.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_painelDados.createSequentialGroup()
												.addComponent(chbxSituacao, GroupLayout.PREFERRED_SIZE, 21,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(lbEmprestado, GroupLayout.PREFERRED_SIZE, 82,
														GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE).addComponent(lbPreco, GroupLayout.PREFERRED_SIZE, 43,
														GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_painelDados.createSequentialGroup().addComponent(lbAnoEdicao)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(spAnoEdicao,
												GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)))
								.addGap(18)
								.addGroup(gl_painelDados.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_painelDados.createSequentialGroup()
												.addComponent(lbEdicao, GroupLayout.PREFERRED_SIZE, 43,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(spEdicao,
														GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
										.addComponent(tfPreco, GroupLayout.PREFERRED_SIZE, 69,
												GroupLayout.PREFERRED_SIZE))
								.addGap(13)
								.addGroup(gl_painelDados.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_painelDados.createSequentialGroup().addGap(7)
												.addComponent(lbEditora, GroupLayout.PREFERRED_SIZE, 51,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(txEditora,
														GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_painelDados.createSequentialGroup().addGap(10).addComponent(lbData)
												.addGap(18).addComponent(txfData, GroupLayout.PREFERRED_SIZE, 140,
														GroupLayout.PREFERRED_SIZE))))
						.addGroup(
								gl_painelDados.createSequentialGroup()
										.addComponent(lbAutor, GroupLayout.PREFERRED_SIZE, 45,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(cbxAutor, GroupLayout.PREFERRED_SIZE, 289,
												GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnAdicionar, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnExcluir, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)))
				.addContainerGap()));
		gl_painelDados.setVerticalGroup(gl_painelDados.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDados.createSequentialGroup().addContainerGap()
						.addGroup(gl_painelDados.createParallelGroup(Alignment.BASELINE)
								.addComponent(txTitulo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
						.addComponent(lbTitulo, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
				.addGroup(gl_painelDados.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbAnoEdicao, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(spAnoEdicao, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(txEditora, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lbEditora, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbEdicao, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(spEdicao, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(
						gl_painelDados.createParallelGroup(Alignment.LEADING)
								.addComponent(chbxSituacao, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_painelDados.createParallelGroup(Alignment.BASELINE)
										.addComponent(lbEmprestado, GroupLayout.PREFERRED_SIZE, 23,
												GroupLayout.PREFERRED_SIZE)
								.addComponent(lbPreco, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
								.addComponent(txfData, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(tfPreco, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lbData, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)))
				.addGap(25)
				.addGroup(gl_painelDados.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbAutor, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbxAutor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(btnExcluir).addComponent(btnAdicionar)).addContainerGap()));
		painelDados.setLayout(gl_painelDados);
		PainelPrincipal.setLayout(gl_PainelPrincipal);

		class RowListener implements ListSelectionListener {
			public void valueChanged(ListSelectionEvent event) {
				btnExcluir.setEnabled(true);

			}
		}
		// 20 caracteres
		txTitulo.setDocument(new Limitador(100));
		txEditora.setDocument(new Limitador(100));

		if (this.tipo == 1) {
			txTitulo.setText(this.livro.getTitulo());
			txEditora.setText(this.livro.getEditora());
			spAnoEdicao.setValue(this.livro.getAnoEdicao());
			spEdicao.setValue(this.livro.getEdicao());
			chbxSituacao.setSelected(this.livro.getSituacao() == 'S' ? true : false);
			tfPreco.setValue(this.livro.getPrecoLivro());
			txfData.setDate(this.livro.getDataCompra());

			LivroDB livrodb = new LivroDB();
			AutorLivroDB autorlivrodb = new AutorLivroDB();

			List<Integer> listaCodigoAutoresDoLivro = new ArrayList<Integer>();

			try {
				listaCodigoAutoresDoLivro = autorlivrodb.retornaAutoresLivro(this.livro);

			} catch (SQLException e1) {

				e1.printStackTrace();
			}

			for (int i = 0; i < listaCodigoAutoresDoLivro.size(); i++) {

				modelo.addRow(new Object[] { autorDB.buscarPorID(listaCodigoAutoresDoLivro.get(i)).getId(),
						autorDB.buscarPorID(listaCodigoAutoresDoLivro.get(i)).getNome(),
						autorDB.buscarPorID(listaCodigoAutoresDoLivro.get(i)).getSobreNome() });
			}

		}

	}

	private boolean verificaCampos() {
		boolean opc = true;

		String txt = "Campos Obrigatórios: ";

		if (txTitulo.getText().equals("")) {
			opc = false;
			txt += "\nTitulo";
			lbTitulo.setForeground(Color.red);
		} else {
			lbTitulo.setForeground(Color.black);

		}

		if (txEditora.getText().equals("")) {
			opc = false;
			txt += "\nEditora";
			lbEditora.setForeground(Color.red);
		} else {
			lbEditora.setForeground(Color.black);

		}

		if (tfPreco.getText().equals("")) {
			opc = false;
			txt += "\nPreço";
			lbPreco.setForeground(Color.red);
		} else {
			lbPreco.setForeground(Color.black);

		}

		if (txfData.getDate() == null) {
			opc = false;
			txt += "\nData";
			lbData.setForeground(Color.red);
		} else {
			lbData.setForeground(Color.black);

		}

		if (tbAutores.getRowCount() == 0) {
			opc = false;
			txt += "\nAutor";
			lbAutor.setForeground(Color.red);
		} else {
			lbAutor.setForeground(Color.black);

		}

		if (opc == false) {

			mensagemComTimer(txt);

		}

		return opc;
	}

	private void mensagemComTimer(String texto) {

		JOptionPane meuJOPane = new JOptionPane(texto,JOptionPane.ERROR_MESSAGE);//
		final JDialog dialog = meuJOPane.createDialog(null, "Atenção!");

		dialog.setModal(true);

		Timer timer = new Timer(4 * 1000, new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				dialog.dispose();

			}

		});

		timer.start();
		dialog.setVisible(true);
		timer.stop();

	}

}
