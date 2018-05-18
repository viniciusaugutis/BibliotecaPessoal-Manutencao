package utili;

import java.awt.Color;
import java.awt.GraphicsEnvironment;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class AplicaLookAndFeel {


	public static void pegaLookAndFeel() {
		try {
			
			//UIManager.put ("nimbusBlueGrey", new Color(100,150,255,100));
			
			//UIManager.put ("nimbusOrange", new Color (51,98,140,1));
		
			
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Metal".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (UnsupportedLookAndFeelException e) {
			System.out.println("Erro: " + e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			 System.out.println("Erro: " + e.getMessage());
			 e.printStackTrace();
		} catch (InstantiationException e) {
			System.out.println("Erro: " + e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			 System.out.println("Erro: " + e.getMessage());
			 e.printStackTrace();
		}
	}

	
	
	
	
	
	
	
}