package br.com.biblioteca.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.biblioteca.persistencia.ConsultasDB;
import br.com.biblioteca.persistencia.PessoaDB;
import utili.AplicaLookAndFeel;
import utili.Limitador;
import utili.Relatorios;

import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionEvent;

public class ConsultaPessoa extends JInternalFrame {

	private JPanel contentPane;
	private JTextField txfNomePessoa;
	private List<Object[]> lista;
	private JTable tabelaPessoas;
	private DefaultTableModel modelo = new DefaultTableModel();

	public ConsultaPessoa() {

		AplicaLookAndFeel.pegaLookAndFeel();

		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Consulta de Pessoas");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnVoltar = new JButton("");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		btnVoltar.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/voltar2.png")));
		btnVoltar.setToolTipText("Voltar");

		JLabel lbNomePessoa = new JLabel("Nome da Pessoa:");
		lbNomePessoa.setFont(new Font("Tahoma", Font.PLAIN, 12));

		txfNomePessoa = new JTextField();
		txfNomePessoa.setColumns(10);

		JButton btnPesquisar = new JButton("");

		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ConsultasDB consultasDB = new ConsultasDB();
				lista = consultasDB.consultaPessoas(txfNomePessoa.getText());
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
		});

		btnPesquisar.setIcon(new ImageIcon(AutorUI.class.getResource("/icones/pesquisar.png")));
		btnPesquisar.setToolTipText("Pesquisar");

		JScrollPane scrollPane = new JScrollPane();

		JButton btnRelatrio = new JButton("Relat\u00F3rio ");
		btnRelatrio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PessoaDB pessoadb = new PessoaDB();
				try {
					Relatorios.gerarRelatorio("relatorios\\pessoas.jasper", pessoadb.getRelatorioPessoa());
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
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lbNomePessoa, GroupLayout.PREFERRED_SIZE, 94,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(txfNomePessoa, 260, 260, 260).addGap(18)
										.addComponent(btnPesquisar, GroupLayout.PREFERRED_SIZE, 44,
												GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 420, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(173).addComponent(btnRelatrio))
						.addComponent(btnVoltar, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_contentPane
				.setVerticalGroup(
						gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(btnVoltar, GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
														.addComponent(lbNomePessoa, GroupLayout.PREFERRED_SIZE, 15,
																GroupLayout.PREFERRED_SIZE)
												.addComponent(txfNomePessoa, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(btnPesquisar, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE).addGap(8)
				.addComponent(btnRelatrio)));

		String[] nomesColuna = { "Nome", "Telefone", "Email" };
		modelo.setColumnIdentifiers(nomesColuna);
		tabelaPessoas = new JTable(modelo);
		scrollPane.setViewportView(tabelaPessoas);
		contentPane.setLayout(gl_contentPane);
	}
}
