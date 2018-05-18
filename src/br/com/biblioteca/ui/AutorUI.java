package br.com.biblioteca.ui;

import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.Timer;

import br.com.biblioteca.dominio.Autor;
import br.com.biblioteca.persistencia.AutorDB;
import utili.AplicaLookAndFeel;

import java.awt.event.ActionListener;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLIntegrityConstraintViolationException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class AutorUI extends JInternalFrame {

	private JPanel contentPane;
	private JTable tbAutor;
	private DefaultTableModel modelo = new DefaultTableModel();
	private List<Autor> lista;
	JButton btnRemover = new JButton();
	JButton btnnAlterar = new JButton();
	

	public AutorUI() {
		

		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

		// Quando abrir a janela

		AplicaLookAndFeel.pegaLookAndFeel();
		
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		

		setTitle("Autor");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		btnRemover.setToolTipText("Remover");
		btnRemover.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/delete2.png")));

		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (tbAutor.getSelectedRow() != -1) {

					if (JOptionPane.showConfirmDialog(null, "Confirma a exclusão do registro?", "Exclusão",
							JOptionPane.YES_NO_OPTION) == 0) {

						AutorDB autorDB = new AutorDB();

						autorDB.excluir(lista.get(tbAutor.getSelectedRow()));

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

		btnnAlterar.setToolTipText("Alterar");
		btnnAlterar.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/alterar2.png")));

		btnnAlterar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if (tbAutor.getSelectedRow() != -1) {

					CadastroAutorUI cadastroAutor = new CadastroAutorUI(1, lista.get(tbAutor.getSelectedRow()));

					BibliotecaPessoal b = BibliotecaPessoal.frame;
					b.desktopPane.add(cadastroAutor);
					cadastroAutor.setVisible(true);

					setVisible(false);

					cadastroAutor.setVisible(true);

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
			public void actionPerformed(ActionEvent arg0) {

				CadastroAutorUI cadastroAutor = new CadastroAutorUI(0, null);

				BibliotecaPessoal b = BibliotecaPessoal.frame;
				b.desktopPane.add(cadastroAutor);
				cadastroAutor.setVisible(true);

				setVisible(false);

			}
		});

		
		JButton btnVoltar = new JButton("");
		btnVoltar.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/voltar2.png")));
		
		btnVoltar.setToolTipText("Voltar");

		// AÇÃO PARA O BOTAO VOLTAR

		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(
						gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(btnVoltar, GroupLayout.PREFERRED_SIZE, 61,
																GroupLayout.PREFERRED_SIZE)
														.addGap(27)
														.addComponent(btnNovo, GroupLayout.PREFERRED_SIZE, 57,
																GroupLayout.PREFERRED_SIZE)
										.addGap(27)
										.addComponent(btnnAlterar, GroupLayout.PREFERRED_SIZE, 58,
												GroupLayout.PREFERRED_SIZE).addGap(26)
								.addComponent(btnRemover, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(15)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNovo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(btnRemover, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnnAlterar, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
								.addComponent(btnVoltar, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
				.addContainerGap()));

		String[] nomesColuna = { "Nome", "Sobrenome" };
		modelo.setColumnIdentifiers(nomesColuna);
		tbAutor = new JTable(modelo);

		tbAutor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnRemover.setEnabled(true);
				btnnAlterar.setEnabled(true);
			}
		});
		tbAutor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnRemover.setEnabled(true);
				btnnAlterar.setEnabled(true);
			}
		});

		scrollPane.setViewportView(tbAutor);
		contentPane.setLayout(gl_contentPane);

		atualizaTabela();
	}

	private void atualizaTabela() {

		btnnAlterar.setEnabled(false);
		btnRemover.setEnabled(false);

		AutorDB autorDB = new AutorDB();
		lista = autorDB.buscarTodos();
		Iterator<Autor> it = lista.iterator();
		Autor a;

		while (modelo.getRowCount() > 0) {
			modelo.removeRow(0);
		}

		while (it.hasNext()) {
			a = it.next();
			modelo.addRow(new Object[] { a.getNome(), a.getSobreNome() });
		}
	}

}
