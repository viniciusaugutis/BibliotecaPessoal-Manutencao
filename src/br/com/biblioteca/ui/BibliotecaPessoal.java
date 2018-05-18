package br.com.biblioteca.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.biblioteca.dominio.Pessoa;
import br.com.biblioteca.persistencia.EntidadesDB;
import br.com.biblioteca.persistencia.LivroExcluidoDB;
import utili.AplicaLookAndFeel;
import utili.Relogio;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.Font;

@SuppressWarnings("serial")
public class BibliotecaPessoal extends JFrame {

	private JPanel contentPane;
	JDesktopPane desktopPane = new JDesktopPane();
	static JLabel lbHorario = new JLabel("");

	static BibliotecaPessoal frame = new BibliotecaPessoal();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					frame.setVisible(true);
					Relogio clock = new Relogio(lbHorario, "HH:mm:ss", 1000);
					clock.start();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public BibliotecaPessoal() {

		new EntidadesDB(); // Executa as DML, mas se já existir nem cria;

		AplicaLookAndFeel.pegaLookAndFeel();
		setTitle("Biblioteca Pessoal");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(tela.width, tela.height);

		setResizable(false);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				System.exit(0);

			}
		});

		JMenu mnCadastros = new JMenu("Cadastros");

		mnCadastros.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
		});
		mnCadastros.setMnemonic('c');
		menuBar.add(mnCadastros);

		JMenuItem mntmNewMenuItem = new JMenuItem("Autores");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				AutorUI autorTeste = new AutorUI();

				desktopPane.add(autorTeste);

				autorTeste.setVisible(true);
			}
		});

		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		mnCadastros.add(mntmNewMenuItem);

		JMenuItem mntmLivros = new JMenuItem("Livros");

		// Adiciona um evento para o livros, de action performed
		mntmLivros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				LivroUI livro = new LivroUI();

				desktopPane.add(livro);

				livro.setVisible(true);
			}
		});

		mntmLivros.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
		mnCadastros.add(mntmLivros);

		JMenuItem mntmEmprstimos = new JMenuItem("Empr\u00E9stimos");
		mntmEmprstimos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EmprestimoUI emprestimo = new EmprestimoUI();

				desktopPane.add(emprestimo);

				emprestimo.setVisible(true);
			}
		});
		mntmEmprstimos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		mnCadastros.add(mntmEmprstimos);

		JMenuItem mntmPessoas = new JMenuItem("Pessoas");
		mntmPessoas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				PessoaUI pessoa = new PessoaUI();

				desktopPane.add(pessoa);

				pessoa.setVisible(true);

			}
		});

		mntmPessoas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		mnCadastros.add(mntmPessoas);

		JMenu mnConsultas = new JMenu("Consultas");
		mnConsultas.setMnemonic('t');
		menuBar.add(mnConsultas);

		JMenuItem mntmAutores = new JMenuItem("Autores");
		mntmAutores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ConsultaAutor consultaAut = new ConsultaAutor();

				desktopPane.add(consultaAut);

				consultaAut.setVisible(true);
			}
		});
		mntmAutores.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		mnConsultas.add(mntmAutores);

		JMenuItem mntmLivros_1 = new JMenuItem("Livros");
		mntmLivros_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ConsultaLivro consultaLiv = new ConsultaLivro();

				desktopPane.add(consultaLiv);

				consultaLiv.setVisible(true);
			}
		});

		mntmLivros_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
		mnConsultas.add(mntmLivros_1);

		JMenuItem mntmEmprstimos_1 = new JMenuItem("Empr\u00E9stimos");
		mntmEmprstimos_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ConsultaEmprestimo consultaEmp = new ConsultaEmprestimo();

				desktopPane.add(consultaEmp);

				consultaEmp.setVisible(true);
			}
		});
		mntmEmprstimos_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		mnConsultas.add(mntmEmprstimos_1);

		JMenuItem mntmPessoas_1 = new JMenuItem("Pessoas");
		mntmPessoas_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ConsultaPessoa consultaPes = new ConsultaPessoa();

				desktopPane.add(consultaPes);

				consultaPes.setVisible(true);
			}
		});
		mntmPessoas_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		mnConsultas.add(mntmPessoas_1);

		JMenuItem mntmInventario = new JMenuItem("Inventario");
		mntmInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ConsultaInventario consultaInvent = new ConsultaInventario();

				desktopPane.add(consultaInvent);

				consultaInvent.setVisible(true);

			}
		});
		mntmInventario.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
		mnConsultas.add(mntmInventario);

		JMenu mnCreditos = new JMenu("Creditos");
		mnCreditos.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				ImageIcon fotoCredito;

				fotoCredito = new ImageIcon(getClass().getResource("fotoTrabalho.png"));
				JOptionPane.showMessageDialog(null,
						"- Biblioteca Pessoal S.A. -\n \n \n \n Desenvolvido por: \n Vinicius de Souza Augutis ",
						"Creditos", 0, fotoCredito);

			}
		});

		mnCreditos.setMnemonic('r');
		menuBar.add(mnCreditos);

		JMenu mnSair = new JMenu("Sair");
		mnSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		mnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});

		mnSair.setMnemonic('s');
		menuBar.add(mnSair);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		desktopPane.setBackground(new Color(245, 245, 245));
		contentPane.add(desktopPane, BorderLayout.CENTER);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/telaInicial.jpg")));
		label.setBounds(272, 87, 852, 511);
		desktopPane.add(label);

		JLabel lbTexto = new JLabel("BIBLIOTECA PESSOAL");
		lbTexto.setForeground(Color.BLUE);
		lbTexto.setFont(new Font("Tempus Sans ITC", Font.BOLD, 32));
		lbTexto.setBounds(539, 39, 585, 52);
		desktopPane.add(lbTexto);

		lbHorario.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lbHorario.setForeground(Color.BLUE);
		lbHorario.setBounds(1237, 657, 103, 41);
		desktopPane.add(lbHorario);

	}
}
