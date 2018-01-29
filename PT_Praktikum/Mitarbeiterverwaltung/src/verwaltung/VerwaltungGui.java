package verwaltung;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.table.*;

import personal.*;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.JComboBox;

public class VerwaltungGui {

	private JFrame frame;
	private JTable table;
	private final JTextField textField = new JTextField();
	private final JTextField textField_1 = new JTextField();
	private final JTextField textField_2 = new JTextField();
	private final JComboBox<String> comboBox = new JComboBox<String>(new String[] {"Angestellter", "Manager"});

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
		DefaultTableModel model = new DefaultTableModel(new Object[] {"Vorname"}, 0) 
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
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		int columnIndex = 2;
		sortKeys.add(new RowSorter.SortKey(columnIndex, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
		
		JButton addButton = new JButton("Add");
		addButton.setBounds(0, 0, 128, 20);
		frame.getContentPane().add(addButton);
		addButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				if(comboBox.getSelectedIndex() == 0 && !textField.getText().isEmpty() && !textField_1.getText().isEmpty())
				{
					Personalverwaltung.Verwaltung.add(new Angestellter(textField.getText(), Integer.parseInt(textField_1.getText())));
					//doppelte loeschen und sortieren
					Personalverwaltung.removedoubles();
					Collections.sort(Personalverwaltung.Verwaltung);
					model.addRow(new Object[] {textField.getText(), LocalDate.now(), Integer.parseInt(textField_1.getText())});
					sorter.sort();
				}				
				if(comboBox.getSelectedIndex() == 1 && !textField.getText().isEmpty() && !textField_1.getText().isEmpty() && !textField_2.getText().isEmpty())
				{
					Personalverwaltung.Verwaltung.add(new Manager(textField.getText(), Integer.parseInt(textField_1.getText()), Integer.parseInt(textField_2.getText())));
					//doppelte loeschen und sortieren
					Personalverwaltung.removedoubles();
					Collections.sort(Personalverwaltung.Verwaltung);
					model.addRow(new Object[] {textField.getText(), LocalDate.now(), Integer.parseInt(textField_1.getText())});
					sorter.sort();
				}
			}
		 });

		 JButton removeButton = new JButton("Remove");
		 removeButton.setBounds(128, 0, 128, 20);
		 frame.getContentPane().add(removeButton);
		 textField_2.setBounds(144, 500, 49, 31);
		 frame.getContentPane().add(textField_2);
		 textField_2.setColumns(10);
		 textField_1.setBounds(85, 500, 49, 31);
		 frame.getContentPane().add(textField_1);
		 textField_1.setColumns(10);
		 textField.setBounds(29, 500, 39, 31);
		 frame.getContentPane().add(textField);
		 textField.setColumns(10);
		 comboBox.setBounds(200, 500, 200, 50);
		 frame.getContentPane().add(comboBox);
		 removeButton.addActionListener(new ActionListener() {

			 public void actionPerformed(ActionEvent event) {
				 if(table.getSelectedRow() != -1)
					 model.removeRow(table.getSelectedRow());
			 }
		 });
	}
}
