package verwaltung;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.table.*;

import personal.*;

import javax.swing.JTable;

public class VerwaltungGui {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
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
		frame.setBounds(0, 0, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		@SuppressWarnings("serial")
		DefaultTableModel model = new DefaultTableModel() 
		{			
			@Override
			public boolean isCellEditable(int row, int column) 
			{
		       return false;
			}
		};
		table = new JTable(model);
		table.setBounds(0, 20, 450, 300);
		frame.getContentPane().add(table);
		model.addColumn("Vorname");
		model.addColumn("Einstellungsdatum");
		model.addColumn("Gehalt");
		for (Mitarbeiter m : Personalverwaltung.Verwaltung)
		{
		int i = Personalverwaltung.Verwaltung.indexOf(m);	
		model.addRow(new Object[] {Personalverwaltung.Verwaltung.get(i).getName(), Personalverwaltung.Verwaltung.get(i).getErstellungsdatum(), Personalverwaltung.Verwaltung.get(i).getGehalt()});
		}
		
		JButton addButton = new JButton("Add");
		addButton.setBounds(0, 0, 128, 20);
		frame.getContentPane().add(addButton);
		addButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				Personalverwaltung.Verwaltung.add(new Manager("Bob", 30, 40));
				//doppelte loeschen und sortieren
				Personalverwaltung.removedoubles();
				Collections.sort(Personalverwaltung.Verwaltung);
				model.addRow(new Object[] {"Bob", LocalDate.now(), 30});
		    }
		 });

		 JButton removeButton = new JButton("Remove");
		 removeButton.setBounds(128, 0, 128, 20);
		 frame.getContentPane().add(removeButton);
		 removeButton.addActionListener(new ActionListener() {

			 public void actionPerformed(ActionEvent event) {
				 if(table.getSelectedRow() != -1)
					 model.removeRow(table.getSelectedRow());
			 }
		 });
	}
}
