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
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.jdesktop.swingx.JXDatePicker;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

public class ConsultaInventario extends JInternalFrame {

	private JPanel contentPane;
	private DefaultTableModel modelo = new DefaultTableModel();
	private List<Object[]> lista;
	private JLabel lblDataInicial;
	private JLabel lblDataFinal;

	private JXDatePicker txfDataInicial = new JXDatePicker();
	private JXDatePicker txfDataFinal = new JXDatePicker();
	private JButton btnVoltar;
	private JButton btnRelatorio;
	private JComboBox cbTipo = new JComboBox();

	public ConsultaInventario() {

		AplicaLookAndFeel.pegaLookAndFeel();

		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);

		setTitle("Consulta de Inventario");
		setBounds(100, 100, 505, 298);

		cbTipo.addItem("<Selecione>");
		cbTipo.addItem("Relatório de inventário por mês (Textual)");
		cbTipo.addItem("Relatório de inventário por ano (Textual)");
		cbTipo.addItem("Relatório de inventário total (Textual)");
		cbTipo.addItem("Relatório de inventário por mês (Gráfico-Barras)");
		cbTipo.addItem("Relatório de inventário por ano (Gráfico-Barras)");
		cbTipo.addItem("Relatório de inventário por mes(Gráfico-Linhas)");

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lbTipo = new JLabel("Escolha o tipo:");
		lbTipo.setFont(new Font("Tahoma", Font.PLAIN, 12));

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

		btnRelatorio = new JButton("Relat\u00F3rio ");
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LivroDB livrodb = new LivroDB();

				if (verificaCampos()) {

					SimpleDateFormat dataBanco = new SimpleDateFormat("yyyy-MM-dd");

					int testeData = dataBanco.format(txfDataInicial.getDate())
							.compareTo(dataBanco.format(txfDataFinal.getDate()));

					if (testeData <= 0) {

						if (cbTipo.getSelectedIndex() == 0) {
							JOptionPane meuJOPane = new JOptionPane("Por favor selecione um registro",JOptionPane.ERROR_MESSAGE);//
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

						if (cbTipo.getSelectedIndex() == 1) {
							try {
								Relatorios.gerarRelatorio("relatorios\\InventarioPorMes.jasper",
										livrodb.getRelatorioInventarioPorMes(txfDataInicial.getDate(),
												txfDataFinal.getDate()));
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else if (cbTipo.getSelectedIndex() == 2) {
							try {
								Relatorios.gerarRelatorio("relatorios\\InventarioPorAno.jasper",
										livrodb.getRelatorioInventarioPorAno(txfDataInicial.getDate(),
												txfDataFinal.getDate()));
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						} else if (cbTipo.getSelectedIndex() == 3) {

							try {
								Relatorios.gerarRelatorio("relatorios\\InventarioTotal.jasper",
										livrodb.getRelatorioInventarioTotal());
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						} else if (cbTipo.getSelectedIndex() == 4) {

							try {
								Relatorios.gerarRelatorio("relatorios\\GraficoInventarioPorMes.jasper",
										livrodb.getRelatorioInventarioPorMes(txfDataInicial.getDate(),
												txfDataFinal.getDate()));
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else if (cbTipo.getSelectedIndex() == 5) {

							try {
								Relatorios.gerarRelatorio("relatorios\\GraficoInventarioPorAno.jasper",
										livrodb.getRelatorioInventarioPorAno(txfDataInicial.getDate(),
												txfDataFinal.getDate()));
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						} else if (cbTipo.getSelectedIndex() == 6) {

							try {
								Relatorios.gerarRelatorio("relatorios\\GraficoInventarioPorMes - Linhas.jasper",
										livrodb.getRelatorioInventarioPorMes(txfDataInicial.getDate(),
												txfDataFinal.getDate()));
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
					} else {
						JOptionPane meuJOPane = new JOptionPane("Data inicial maior que a final",JOptionPane.ERROR_MESSAGE);//
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

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(16)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup().addComponent(lbTipo)
												.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(cbTipo,
														GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE))
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
						.addGroup(gl_contentPane.createSequentialGroup().addGap(194).addComponent(btnRelatorio,
								GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addComponent(btnVoltar, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE).addGap(39)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lbTipo).addComponent(
						cbTipo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(56)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txfDataFinal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(txfDataInicial, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDataInicial, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDataFinal, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED, 52, Short.MAX_VALUE).addComponent(btnRelatorio)
				.addContainerGap()));

		String[] nomesColuna = { "Livro", "Pessoa", "Data Emprestimo" };

		modelo.setColumnIdentifiers(nomesColuna);
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
