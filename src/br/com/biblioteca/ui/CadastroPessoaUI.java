package br.com.biblioteca.ui;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import br.com.biblioteca.dominio.Pessoa;
import br.com.biblioteca.persistencia.PessoaDB;
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
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;
import javax.swing.JDialog;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

@SuppressWarnings("serial")
public class CadastroPessoaUI extends JInternalFrame {

	private JPanel contentPane;
	private JTextField txfNome = new JTextField();
	private int tipo;
	private Pessoa pessoa;
	private JTextField txfEmail = new JTextField();;
	private JFormattedTextField txfTelefone_1;
	MaskFormatter mascaraTelefone;
	MaskFormatter mascara;
	JLabel lbNome = new JLabel("Nome:*");
	JLabel lbEmail = new JLabel("Email:*");
	JLabel lbTelefone = new JLabel("Telefone:*");

	public CadastroPessoaUI(int tipo, Pessoa pessoa) {

		this.tipo = tipo;
		this.pessoa = pessoa;

		AplicaLookAndFeel.pegaLookAndFeel();

		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);

		setBounds(100, 100, 432, 273);
		contentPane = new JPanel();

		// altera a borda da tabela para cadastrar cliente ou alterar cliente

		setContentPane(contentPane);

		JPanel panel = new JPanel();

		try {
			mascaraTelefone = new MaskFormatter("(##) ##### - ####");
			mascaraTelefone.setValidCharacters("0123456789");
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		txfTelefone_1 = new JFormattedTextField(mascaraTelefone);
		txfTelefone_1.setText("(  )       -     ");

		txfNome.setDocument(new Limitador(40));
		txfEmail.setDocument(new Limitador(100));

		String titulo;

		if (this.tipo == 0) {
			titulo = "Cadastrar Pessoa";
			setTitle("Cadastrar Pessoa");
		} else {
			titulo = "Alterar Pessoa";
			setTitle("Alterar Pessoa");

			txfNome.setText(this.pessoa.getNome());
			txfTelefone_1.setText(this.pessoa.getTelefone());
			txfEmail.setText(this.pessoa.getEmail());

		}

		panel.setBorder(new TitledBorder(null, titulo, TitledBorder.LEADING, TitledBorder.TOP, null, null));

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 396, Short.MAX_VALUE)
								.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));

		lbNome.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JButton btnSalvar = new JButton("");
		btnSalvar.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/salvar.png")));
		btnSalvar.setToolTipText("Salvar");

		btnSalvar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				PessoaDB pessoadb = new PessoaDB();

				if (CadastroPessoaUI.this.tipo == 0) {

					if (verificaCampos() && (verificaEmail())) {
						try {
							pessoadb.inserir(new Pessoa(txfNome.getText(), txfTelefone_1.getText(), txfEmail.getText()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						PessoaUI pessoaUi = new PessoaUI();
						BibliotecaPessoal b = BibliotecaPessoal.frame;
						b.desktopPane.add(pessoaUi);
						pessoaUi.setVisible(true);

						setVisible(false);

					}

				} else {

					if (verificaCampos() && (verificaEmail())) {

						CadastroPessoaUI.this.pessoa.setNome(txfNome.getText());

						try {
							CadastroPessoaUI.this.pessoa.setTelefone(txfTelefone_1.getText());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						CadastroPessoaUI.this.pessoa.setEmail(txfEmail.getText());

						pessoadb.modificar(CadastroPessoaUI.this.pessoa);

						PessoaUI pessoaUi = new PessoaUI();
						BibliotecaPessoal b = BibliotecaPessoal.frame;
						b.desktopPane.add(pessoaUi);
						pessoaUi.setVisible(true);

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
				setVisible(false);
				new PessoaUI().setVisible(true);
			}
		});

		lbEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));

		lbTelefone.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel lblExHotmailGmail = new JLabel("Ex: hotmail, gmail,etc");
		lblExHotmailGmail.setFont(new Font("Tahoma", Font.PLAIN, 10));

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(lbEmail)
												.addComponent(lbNome))
								.addGap(5)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(txfNome, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 159,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(
												gl_panel.createSequentialGroup()
														.addPreferredGap(ComponentPlacement.RELATED, 240,
																Short.MAX_VALUE)
														.addComponent(btnSalvar)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(btnCancelar))
										.addGroup(Alignment.LEADING,
												gl_panel.createSequentialGroup()
														.addComponent(txfEmail, GroupLayout.PREFERRED_SIZE, 215,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(lblExHotmailGmail, GroupLayout.DEFAULT_SIZE, 101,
																Short.MAX_VALUE)))
										.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup().addComponent(lbTelefone)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(txfTelefone_1,
										GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)))));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addGap(23)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lbNome).addComponent(txfNome,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(20)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbEmail, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(txfEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblExHotmailGmail, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbTelefone, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(txfTelefone_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCancelar).addComponent(btnSalvar))
				.addContainerGap(49, Short.MAX_VALUE)));

		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);

	}

	private boolean verificaCampos() {
		boolean opc = true;

		String txt = "Campos Obrigatórios: ";

		if (txfNome.getText().equals("")) {
			opc = false;
			txt += "\nNome";
			lbNome.setForeground(Color.red);
		} else {
			lbNome.setForeground(Color.black);

		}

		if (txfEmail.getText().equals("")) {
			opc = false;
			txt += "\nEmail";
			lbEmail.setForeground(Color.red);
		} else {
			lbEmail.setForeground(Color.black);

		}

		if (txfTelefone_1.getText().equals("(  )      -     ") || (txfTelefone_1.getText().length()) != 17) {
			opc = false;
			txt += "\nTelefone";
			lbTelefone.setForeground(Color.red);
		} else {
			lbTelefone.setForeground(Color.black);

		}

		if (opc == false) {

			mensagemComTimer(txt);

		}

		return opc;
	}

	private boolean verificaEmail() {

		boolean opc = true;

		if (!Pattern.matches(".{3,}@.{3,15}\\..{0,10}", txfEmail.getText())) {
			opc = false;
		}

		if (opc == false) {

			mensagemComTimer("Email inválido - Por favor digite novamente");

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
