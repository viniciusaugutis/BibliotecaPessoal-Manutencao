package br.com.biblioteca.ui;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

import br.com.biblioteca.dominio.Pessoa;

import br.com.biblioteca.dominio.Emprestimo;
import br.com.biblioteca.dominio.Livro;

import br.com.biblioteca.persistencia.PessoaDB;
import utili.AplicaLookAndFeel;
import utili.Limitador;
import br.com.biblioteca.persistencia.EmprestimoDB;
import br.com.biblioteca.persistencia.LivroDB;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import org.jdesktop.swingx.JXDatePicker;

public class CadastroEmprestimoUI extends JInternalFrame {

	private JPanel contentPane;
	private int tipo;
	private Emprestimo emprestimo;
	DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	JComboBox cbLivro = new JComboBox();
	JComboBox cbPessoa = new JComboBox();
	List<Pessoa> listaPessoas = new ArrayList<Pessoa>();
	List<Livro> listaLivro = new ArrayList<Livro>();
	PessoaDB pessoadb = new PessoaDB();
	LivroDB livrodb = new LivroDB();
	private JXDatePicker txfDataEmpr = new JXDatePicker();
	private JLabel lbPessoa = new JLabel("Pessoa:*");
	private JLabel lbdataEmprestrestimo = new JLabel("Data Empr\u00E9stimo:*");
	private JLabel lbLivro = new JLabel("Livro:*");

	private void atualizaComboPessoa() {

		List<Pessoa> Pessoas = pessoadb.buscarTodos();

		Iterator<Pessoa> it = Pessoas.iterator();

		Pessoa a;

		while (it.hasNext()) {
			a = it.next();
			listaPessoas.add(a);
		}

		cbPessoa.addItem("<Selecione>");
		for (int j = 0; j < listaPessoas.size(); j++) {

			cbPessoa.addItem(listaPessoas.get(j).getNome());
		}
	}

	private void atualizaComboLivro() {

		List<Livro> livros = livrodb.buscarTodos();

		Iterator<Livro> itLivro = livros.iterator();

		Livro l = null;

		while (itLivro.hasNext()) {
			l = itLivro.next();
			listaLivro.add(l);
		}

		cbLivro.addItem("<Selecione>");
		for (int j = 0; j < listaLivro.size(); j++) {
			cbLivro.addItem(listaLivro.get(j).getTitulo());
		}

	}

	public CadastroEmprestimoUI(int tipo, Emprestimo emprestimo) {

		AplicaLookAndFeel.pegaLookAndFeel();

		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);

		this.tipo = tipo;
		this.emprestimo = emprestimo;

		atualizaComboPessoa();
		atualizaComboLivro();

		setTitle("Emprestimo");
		setBounds(100, 100, 490, 280);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnCancelar = new JButton("");
		btnCancelar.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/deletar.png")));
		
		btnCancelar.setToolTipText("Cancelar");

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EmprestimoUI emprestimoui = new EmprestimoUI();
				BibliotecaPessoal b = BibliotecaPessoal.frame;
				b.desktopPane.add(emprestimoui);
				emprestimoui.setVisible(true);

				setVisible(false);

			}
		});

		JButton btnSalvar = new JButton("");
		btnSalvar.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/salvar.png")));
		btnSalvar.setToolTipText("Salvar");

		btnSalvar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {

				EmprestimoDB emprestimoDB = new EmprestimoDB();
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

				Date dataEmprestimo;

				if ((verificaCampos() && (verificaLivroEmprestado()))) {

					dataEmprestimo = (txfDataEmpr.getDate());
					
					if (dataEmprestimo.getDate() != new Date().getDate() || dataEmprestimo.getDay() != new Date().getDay() || dataEmprestimo.getMonth() != new Date().getMonth() ) {
						JOptionPane.showMessageDialog(null, "Livro só pode ser emprestado para a data de hoje");
						return;
					}
					if (CadastroEmprestimoUI.this.tipo == 0) {

						// inserir um novo emprestimo

						PessoaDB pessoadb = new PessoaDB();

						emprestimoDB
								.inserir(new Emprestimo(0, (Pessoa) listaPessoas.get(cbPessoa.getSelectedIndex() - 1),
										dataEmprestimo, null, (Livro) listaLivro.get(cbLivro.getSelectedIndex() - 1)));

						// muda status para emprestado
						livrodb.alterarSeLivroEstaEmprestado(listaLivro.get(cbLivro.getSelectedIndex() - 1), 'S');

						EmprestimoUI emprestimoui = new EmprestimoUI();
						BibliotecaPessoal b = BibliotecaPessoal.frame;
						b.desktopPane.add(emprestimoui);
						emprestimoui.setVisible(true);

						setVisible(false);

					} else {

						// modificar um emprestimo existente
						CadastroEmprestimoUI.this.emprestimo
								.setLivro((Livro) listaLivro.get(cbLivro.getSelectedIndex() - 1));
						CadastroEmprestimoUI.this.emprestimo
								.setPessoa((Pessoa) listaPessoas.get(cbPessoa.getSelectedIndex() - 1));
						CadastroEmprestimoUI.this.emprestimo.setDataEmprestimo(dataEmprestimo);
						emprestimoDB.modificar(CadastroEmprestimoUI.this.emprestimo);

						EmprestimoUI emprestimoui = new EmprestimoUI();
						BibliotecaPessoal b = BibliotecaPessoal.frame;
						b.desktopPane.add(emprestimoui);
						emprestimoui.setVisible(true);

						setVisible(false);

					}
				} else {

				}

			}
		});

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, this.tipo == 0 ? "Cadastrar Emprestimo" : "Alterar Emprestimo",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap(314, Short.MAX_VALUE)
						.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(btnCancelar).addGap(18)));
		gl_contentPane
				.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 39,
												GroupLayout.PREFERRED_SIZE)
								.addComponent(btnCancelar, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		lbPessoa.setFont(new Font("Tahoma", Font.PLAIN, 12));

		lbdataEmprestrestimo.setFont(new Font("Tahoma", Font.PLAIN, 12));

		lbLivro.setFont(new Font("Tahoma", Font.PLAIN, 12));

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING,
								gl_panel.createSequentialGroup()
										.addComponent(lbLivro, GroupLayout.PREFERRED_SIZE, 41,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(cbLivro, 0, 395, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lbdataEmprestrestimo, GroupLayout.PREFERRED_SIZE, 106,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(txfDataEmpr, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup().addComponent(lbPessoa)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(cbPessoa, GroupLayout.PREFERRED_SIZE, 333, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addGap(19)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lbPessoa).addComponent(cbPessoa,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(26)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbdataEmprestrestimo, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(txfDataEmpr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(31)
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbLivro, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbLivro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(58)));
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);

		if (this.tipo == 1) {

			cbPessoa.setSelectedItem(pessoadb.buscarPorID(this.emprestimo.getPessoa().getId()).getNome());
			txfDataEmpr.setDate(this.emprestimo.getDataEmprestimo());
			cbLivro.setSelectedItem(livrodb.buscarPorIsbn(this.emprestimo.getLivro().getIsbn()).getTitulo());
		}

	}

	private boolean verificaCampos() {
		boolean opc = true;

		String txt = "Campos Obrigatórios: ";

		if (cbPessoa.getSelectedIndex() == 0) {
			opc = false;
			txt += "\nPessoa";
			lbPessoa.setForeground(Color.red);
		} else {
			lbPessoa.setForeground(Color.black);

		}

		if (txfDataEmpr.getDate() == null) {
			opc = false;
			txt += "\nEmprestimo";
			lbdataEmprestrestimo.setForeground(Color.red);
		} else {
			lbdataEmprestrestimo.setForeground(Color.black);

		}

		if (cbLivro.getSelectedIndex() == 0) {
			opc = false;
			txt += "\nLivro";
			lbLivro.setForeground(Color.red);
		} else {
			lbLivro.setForeground(Color.black);

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

		Timer timer = new Timer(3 * 1000, new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				dialog.dispose();

			}

		});

		timer.start();
		dialog.setVisible(true);
		timer.stop();

	}

	private boolean verificaLivroEmprestado() {
		boolean opc = true;

		String txt = "Livro já emprestado: ";

		EmprestimoDB emprestimo = new EmprestimoDB();

		Livro livro = listaLivro.get(cbLivro.getSelectedIndex() - 1);

		if (tipo == 1 && this.emprestimo.getLivro().getTitulo()
				.equals(listaLivro.get(cbLivro.getSelectedIndex() - 1).getTitulo())) {

			return true;
		}

		if (emprestimo.buscarSeTemEmprestimo(livro) == 0) {
			opc = false;
			lbLivro.setForeground(Color.red);
		} else {
			lbLivro.setForeground(Color.black);

		}

		if (opc == false) {

			mensagemComTimer(txt);

		}

		return opc;
	}

}
