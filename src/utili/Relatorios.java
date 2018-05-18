package utili;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.swing.JDialog;
import javax.swing.JOptionPane;


import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.JRDataSource;

public class Relatorios {

    public static void gerarRelatorio(String caminho, ResultSet rs) {
       
    	
    	try {
        	
            JDialog viewer = new JDialog(new javax.swing.JFrame(), "Relatório", true);
            Dimension dimSizeScreen = viewer.getToolkit().getScreenSize();
            viewer.setSize(dimSizeScreen.width, dimSizeScreen.height);
            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminho, new HashMap<String, Object>(), jrRS);
            JasperViewer jrViewer = new JasperViewer(jasperPrint, false);
            viewer.getContentPane().add(jrViewer.getContentPane());
            viewer.setVisible(true);
            
            rs.close();
            
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null,"Erro ao gerar relatório");
            erro.printStackTrace();
        }
    }

   
}