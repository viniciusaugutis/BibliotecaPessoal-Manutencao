package br.com.biblioteca.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.biblioteca.dominio.Emprestimo;
import br.com.biblioteca.dominio.Livro;
import br.com.biblioteca.persistencia.EmprestimoDB;
import br.com.biblioteca.persistencia.LivroDB;
import utili.AplicaLookAndFeel;
import utili.Limitador;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EmprestimoUI extends JInternalFrame {

	private JPanel contentPane;
	private JTable tbEmprestimo;
	private DefaultTableModel modelo = new DefaultTableModel();
	private List<Emprestimo> lista;
	JButton btnDevolver = new JButton("");
	JButton btnAlterar = new JButton("");

	public EmprestimoUI() {

		AplicaLookAndFeel.pegaLookAndFeel();

		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);

		btnDevolver.setEnabled(false);
		btnAlterar.setEnabled(false);

		setTitle("Emprestimo");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		btnDevolver.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/devolverLivro.png")));
		btnDevolver.setToolTipText("Devolver");

		btnDevolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tbEmprestimo.getSelectedRow() != -1) {
					if (JOptionPane.showConfirmDialog(null, "Confirma a devolução do livro?", "Devolução",
							JOptionPane.YES_NO_OPTION) == 0) {

						EmprestimoDB emprestimoDB = new EmprestimoDB();
						Emprestimo emprestimo = lista.get(tbEmprestimo.getSelectedRow());
						emprestimo.setDataDevolucao(Calendar.getInstance().getTime());

						Livro livroDevolvido = new Livro();
						livroDevolvido = lista.get(tbEmprestimo.getSelectedRow()).getLivro();

						emprestimoDB.modificar(emprestimo);

						if (emprestimoDB.buscarSeTemEmprestimo(livroDevolvido) == 1) {

							LivroDB livrodb = new LivroDB();
							livrodb.alterarSeLivroEstaEmprestado(livroDevolvido, 'N');

						}
						atualizaTabela();
					}
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

		btnAlterar.setToolTipText("Alterar");
		btnAlterar.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/alterar2.png")));

		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tbEmprestimo.getSelectedRow() != -1) {
					CadastroEmprestimoUI cadastroEmprestimo = new CadastroEmprestimoUI(1,
							lista.get(tbEmprestimo.getSelectedRow()));

					BibliotecaPessoal b = BibliotecaPessoal.frame;
					b.desktopPane.add(cadastroEmprestimo);
					cadastroEmprestimo.setVisible(true);

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
				CadastroEmprestimoUI cadastroEmprestimo = new CadastroEmprestimoUI(0, null);

				BibliotecaPessoal b = BibliotecaPessoal.frame;
				b.desktopPane.add(cadastroEmprestimo);
				cadastroEmprestimo.setVisible(true);

				setVisible(false);

			}
		});

		JButton btnVoltar = new JButton("");
		btnVoltar.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/voltar2.png")));
		btnVoltar.setToolTipText("Voltar");

		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup().addComponent(btnVoltar).addGap(18)
										.addComponent(btnNovo).addGap(18).addComponent(btnAlterar).addGap(18)
										.addComponent(btnDevolver))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)).addContainerGap()));
		gl_contentPane
				.setVerticalGroup(
						gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(
										gl_contentPane.createSequentialGroup().addContainerGap()
												.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 197,
														Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
														.addComponent(btnNovo, GroupLayout.DEFAULT_SIZE, 42,
																Short.MAX_VALUE)
												.addComponent(btnAlterar, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
												.addComponent(btnVoltar, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
												.addComponent(btnDevolver, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))));

		String[] nomesColuna = { "Pessoa", "Data empréstimo", "Livro" };
		modelo.setColumnIdentifiers(nomesColuna);
		tbEmprestimo = new JTable(modelo);

		tbEmprestimo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				btnDevolver.setEnabled(true);
				btnAlterar.setEnabled(true);
			}
		});

		tbEmprestimo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnDevolver.setEnabled(true);
				btnAlterar.setEnabled(true);
			}
		});

		scrollPane.setViewportView(tbEmprestimo);
		tbEmprestimo.getColumnModel().getColumn(0).setPreferredWidth(120);
		tbEmprestimo.getColumnModel().getColumn(1).setPreferredWidth(200);
		tbEmprestimo.getColumnModel().getColumn(2).setPreferredWidth(200);

		contentPane.setLayout(gl_contentPane);

		atualizaTabela();
	}

	private void atualizaTabela() {

		EmprestimoDB emprestimoDB = new EmprestimoDB();
		lista = emprestimoDB.buscarEmprestados();
		Iterator<Emprestimo> it = lista.iterator();

		Emprestimo e;

		while (modelo.getRowCount() > 0) {
			modelo.removeRow(0);
		}

		while (it.hasNext()) {
			e = it.next();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			modelo.addRow(new Object[] { e.getPessoa().getNome(), formatter.format(e.getDataEmprestimo()),
					e.getLivro().getTitulo() });
		}
	}
}
