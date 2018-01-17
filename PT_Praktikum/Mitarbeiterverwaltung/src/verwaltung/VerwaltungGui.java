package verwaltung;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.table.*;
import javax.swing.JTable;

public class VerwaltungGui {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Personalverwaltung pw = new Personalverwaltung();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VerwaltungGui window = new VerwaltungGui();
					window.frame.setSize(1200, 800);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VerwaltungGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(36, 19, 278, 198);
		frame.getContentPane().add(textPane);
		textPane.setText(textPane.getText()+ Personalverwaltung.Verwaltung.get(0).toString());
		textPane.setText(textPane.getText()+  "\n" + Personalverwaltung.Verwaltung.get(1).toString());
		
		DefaultTableModel model = new DefaultTableModel();
		table = new JTable(model);
		table.setBounds(415, 187, 100, 200);
		frame.getContentPane().add(table);
		model.addColumn("Vorname");
		model.addColumn("Einstellungsdatum");
		model.addColumn("Gehalt");
		model.addRow(new Object[] {Personalverwaltung.Verwaltung.get(0).getName(), Personalverwaltung.Verwaltung.get(0).getErstellungsdatum(), Personalverwaltung.Verwaltung.get(0).getGehalt()});
		
	}
}
