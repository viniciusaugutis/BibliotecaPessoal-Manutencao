package br.com.biblioteca.ui;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;

import br.com.biblioteca.dominio.Autor;
import br.com.biblioteca.persistencia.AutorDB;
import utili.AplicaLookAndFeel;
import utili.Limitador;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class CadastroAutorUI extends JInternalFrame {

	private JPanel contentPane;
	private JTextField txfNome = new JTextField();
	private int tipo;
	private Autor autor;
	private JTextField txfSobreNome = new JTextField();
	private JLabel lbNome = new JLabel("Nome:*");
	JLabel lbSobreNome = new JLabel("Sobrenome:*");

	public CadastroAutorUI(int tipo, Autor autor) {

		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		
		AplicaLookAndFeel.pegaLookAndFeel();
		
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);


		this.tipo = tipo;
		this.autor = autor;

		setBounds(100, 100, 480, 194);
		contentPane = new JPanel();

		setContentPane(contentPane);

		JPanel panel = new JPanel();

		String titulo;

		txfNome.setDocument(new Limitador(40));
		txfSobreNome.setDocument(new Limitador(40));

		if (this.tipo == 0) {

			titulo = "Cadastrar Autor";
			setTitle("Cadastrar Autor");

		} else {

			titulo = "Alterar Autor";
			setTitle("Alterar Autor");

			txfNome.setText(this.autor.getNome());
			txfSobreNome.setText(this.autor.getSobreNome());

		}

		panel.setBorder(new TitledBorder(null, titulo, TitledBorder.LEADING, TitledBorder.TOP, null, null));

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 448, Short.MAX_VALUE)
								.addContainerGap()));
		gl_contentPane
				.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
								.addContainerGap()));

		lbNome.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JButton btnSalvar = new JButton("");
		btnSalvar.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/salvar.png")));
		
		btnSalvar.setToolTipText("Salvar");

		btnSalvar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				AutorDB autorDB = new AutorDB();

				if (CadastroAutorUI.this.tipo == 0) {

					if (verificaCampos()) {
						autorDB.inserir(new Autor(txfNome.getText(), txfSobreNome.getText()));

						AutorUI autorui = new AutorUI();
						BibliotecaPessoal b = BibliotecaPessoal.frame;
						b.desktopPane.add(autorui);
						autorui.setVisible(true);

						setVisible(false);
					}

				} else {

					if (verificaCampos()) {
						CadastroAutorUI.this.autor.setNome(txfNome.getText());
						CadastroAutorUI.this.autor.setSobreNome(txfSobreNome.getText());

						autorDB.modificar(CadastroAutorUI.this.autor);

						AutorUI autorui = new AutorUI();
						BibliotecaPessoal b = BibliotecaPessoal.frame;
						b.desktopPane.add(autorui);
						autorui.setVisible(true);

						setVisible(false);
					}

				}

			}
		});

		JButton btnCancelar = new JButton("");
		btnCancelar.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/deletar.png")));
		btnCancelar.setToolTipText("Cancelar");

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				AutorUI autorui = new AutorUI();
				BibliotecaPessoal b = BibliotecaPessoal.frame;
				b.desktopPane.add(autorui);
				autorui.setVisible(true);

				setVisible(false);

			}
		});

		lbSobreNome.setFont(new Font("Tahoma", Font.PLAIN, 12));

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel.createSequentialGroup().addContainerGap()
										.addGroup(
												gl_panel.createParallelGroup(Alignment.LEADING)
														.addGroup(gl_panel.createSequentialGroup().addComponent(lbNome)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(txfNome, GroupLayout.PREFERRED_SIZE, 129,
																		GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(lbSobreNome, GroupLayout.DEFAULT_SIZE, 78,
														Short.MAX_VALUE).addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(txfSobreNome, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
								.addGap(26))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup().addComponent(btnSalvar)
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnCancelar).addGap(33)))));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGap(23)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lbNome)
								.addComponent(txfNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
						.addComponent(lbSobreNome, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(txfSobreNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(28)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSalvar, GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
						.addComponent(btnCancelar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addContainerGap()));
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);

	}

	private boolean verificaCampos() {
		boolean opc = true;

		String txt = "Campos Obrigatórios: ";

		if (txfNome.getText().equals("")) {
			opc = false;
			txt += "\nNome,";
			lbNome.setForeground(Color.red);
		} else {
			lbNome.setForeground(Color.black);

		}

		if (txfSobreNome.getText().equals("")) {
			opc = false;
			txt += "\nSobrenome,";
			lbSobreNome.setForeground(Color.red);
		} else {
			lbSobreNome.setForeground(Color.black);

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
