package br.com.biblioteca.ui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.biblioteca.persistencia.ConsultasDB;
import br.com.biblioteca.persistencia.EmprestimoDB;
import br.com.biblioteca.persistencia.LivroDB;
import utili.AplicaLookAndFeel;
import utili.Limitador;
import utili.Relatorios;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.jdesktop.swingx.JXDatePicker;
import javax.swing.ImageIcon;

public class ConsultaEmprestimo extends JInternalFrame {

	private JPanel contentPane;
	private JTextField txfNome;
	private JTable tabelaEmprestimos;
	private DefaultTableModel modelo = new DefaultTableModel();
	private List<Object[]> lista;
	JButton btnPesquisar = new JButton("");
	private JLabel lblDataInicial;
	private JLabel lblDataFinal;

	private JXDatePicker txfDataInicial = new JXDatePicker();
	private JXDatePicker txfDataFinal = new JXDatePicker();
	private JButton btnVoltar;
	private JLabel lblNomeDoLivro;
	private JTextField txfLivro;
	private JButton btnRelatorio;

	public ConsultaEmprestimo() {

		AplicaLookAndFeel.pegaLookAndFeel();

		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);

		setTitle("Consulta de Emprestimos");
		setBounds(100, 100, 505, 414);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lbNome = new JLabel("Nome da Pessoa:");
		lbNome.setFont(new Font("Tahoma", Font.PLAIN, 12));

		txfNome = new JTextField();
		txfNome.setColumns(10);
		btnPesquisar.setToolTipText("Pesquisar");
		btnPesquisar.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/pesquisar.png")));

		btnPesquisar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if (verificaCampos()) {

					ConsultasDB consultasDB = new ConsultasDB();
					lista = consultasDB.consultaEmprestimos(txfNome.getText(), txfLivro.getText(),
							txfDataInicial.getDate(), txfDataFinal.getDate());
					Iterator<Object[]> it = lista.iterator();
					Object[] linha;

					while (modelo.getRowCount() > 0) {
						modelo.removeRow(0);
					}

					while (it.hasNext()) {
						linha = it.next();
						modelo.addRow(linha);

					}

				}
			}
		});

		JScrollPane scrollPane = new JScrollPane();

		lblDataInicial = new JLabel("Data Inicial:");
		lblDataInicial.setFont(new Font("Tahoma", Font.PLAIN, 12));

		lblDataFinal = new JLabel("Data Final:");
		lblDataFinal.setFont(new Font("Tahoma", Font.PLAIN, 12));

		btnVoltar = new JButton("");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		btnVoltar.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/voltar2.png")));
		btnVoltar.setToolTipText("Voltar");

		lblNomeDoLivro = new JLabel("Nome do Livro:");
		lblNomeDoLivro.setFont(new Font("Tahoma", Font.PLAIN, 12));

		txfLivro = new JTextField();
		txfLivro.setColumns(10);

		btnRelatorio = new JButton("Relat\u00F3rio ");
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmprestimoDB emprestimodb = new EmprestimoDB();
				try {
					Relatorios.gerarRelatorio("relatorios\\emprestimos.jasper", emprestimodb.getRelatorioEmprestimo());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addGap(16)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(lblNomeDoLivro, GroupLayout.PREFERRED_SIZE, 94,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(txfLivro, GroupLayout.PREFERRED_SIZE, 304,
																GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnPesquisar,
												GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup().addComponent(lbNome)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txfNome, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblDataInicial, GroupLayout.PREFERRED_SIZE, 64,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txfDataInicial, GroupLayout.PREFERRED_SIZE, 145,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(lblDataFinal, GroupLayout.PREFERRED_SIZE, 64,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txfDataFinal, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
								.addComponent(btnVoltar, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)))
						.addGroup(Alignment.TRAILING,
								gl_contentPane.createSequentialGroup().addGap(14).addComponent(scrollPane,
										GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)))
						.addContainerGap())
				.addGroup(Alignment.TRAILING,
						gl_contentPane.createSequentialGroup().addContainerGap(208, Short.MAX_VALUE)
								.addComponent(btnRelatorio, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
								.addGap(192)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addComponent(btnVoltar, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE).addGap(24)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNomeDoLivro, GroupLayout.PREFERRED_SIZE, 15,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(txfLivro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addComponent(btnPesquisar, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txfNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lbNome))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txfDataFinal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(txfDataInicial, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDataInicial, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDataFinal, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE).addGap(18)
				.addComponent(btnRelatorio).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		String[] nomesColuna = { "Livro", "Pessoa", "Data Emprestimo" };

		modelo.setColumnIdentifiers(nomesColuna);
		tabelaEmprestimos = new JTable(modelo);

		scrollPane.setViewportView(tabelaEmprestimos);
		contentPane.setLayout(gl_contentPane);
	}

	private boolean verificaCampos() {
		boolean opc = true;

		String txt = "Campos Obrigatórios: ";

		if (txfDataInicial.getDate() == null) {
			opc = false;
			txt += "\nData Inicial";
			lblDataInicial.setForeground(Color.red);
		} else {
			lblDataInicial.setForeground(Color.black);
		}

		if (txfDataFinal.getDate() == null) {
			opc = false;
			txt += "\nData Final";
			lblDataFinal.setForeground(Color.red);
		} else {
			lblDataFinal.setForeground(Color.black);
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
}
