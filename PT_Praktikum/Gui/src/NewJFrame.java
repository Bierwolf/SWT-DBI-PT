import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class NewJFrame extends javax.swing.JFrame implements ActionListener {
	private JPanel jPanel1;
	private JButton jButton1;
	private AbstractAction close;
	private JButton jButton2;
	private JLabel jLabel1;
	private JDialog jDialog1;
	private JRadioButton jRadioButton3;
	private JRadioButton jRadioButton2;
	private JRadioButton jRadioButton1;
	private ButtonGroup buttonGroup1;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				NewJFrame inst = new NewJFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public NewJFrame() {
		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		jPanel1 = new JPanel();
		getContentPane().add(jPanel1, BorderLayout.CENTER);
		jPanel1.setLayout(null);
		jPanel1.add(getJRadioButton1());
		jPanel1.add(getJRadioButton2());
		jPanel1.add(getJRadioButton3());
		jPanel1.add(getJButton1());
		pack(); // Fenster passend layouten
		setSize(1200, 1000);
	}

	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
		}
		return buttonGroup1;
	}

	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setText("jRadioButton1");
			jRadioButton1.setBounds(62, 5, 86, 18);
			getButtonGroup1().add(jRadioButton1);
			jRadioButton1.setActionCommand("BUTTON1 selected!");
			jRadioButton1.addActionListener(this);
		}
		return jRadioButton1;
	}

	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setText("jRadioButton2");
			jRadioButton2.setBounds(153, 5, 86, 18);
			getButtonGroup1().add(jRadioButton2);
			jRadioButton2.setActionCommand("BUTTON2 selected!");
			jRadioButton2.addActionListener(this);
		}
		return jRadioButton2;
	}

	private JRadioButton getJRadioButton3() {
		if (jRadioButton3 == null) {
			jRadioButton3 = new JRadioButton();
			jRadioButton3.setText("jRadioButton3");
			jRadioButton3.setBounds(300, 20, 100, 18);
			getButtonGroup1().add(jRadioButton3);
			jRadioButton3.setActionCommand("BUTTON3 selected!");
			jRadioButton3.addActionListener(this);
		}
		return jRadioButton3;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Auswahl anzeigen!");
			jButton1.setBounds(400, 200, 400, 50);
			jButton1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jButton1ActionPerformed(evt);
				}
			});
		}
		return jButton1;
	}

	private void jButton1ActionPerformed(ActionEvent evt) {
		System.out.println("jButton1.actionPerformed, event=" + evt);
		System.out.println(buttonGroup1.getSelection().getActionCommand());
		getJLabel1().setText(buttonGroup1.getSelection().getActionCommand());
		getJDialog1().pack();
		getJDialog1().setLocationRelativeTo(null);
		getJDialog1().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		System.out.println(evt.getActionCommand());

	}

	private JDialog getJDialog1() {
		if (jDialog1 == null) {
			jDialog1 = new JDialog(this);
			BoxLayout jDialog1Layout = new BoxLayout(jDialog1.getContentPane(), javax.swing.BoxLayout.Y_AXIS);
			jDialog1.getContentPane().setLayout(jDialog1Layout);
			jDialog1.getContentPane().add(getJLabel1());
			jDialog1.getContentPane().add(getJButton2());
		}
		return jDialog1;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("jLabel1");
		}
		return jLabel1;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("jButton2");
			jButton2.setAction(getClose()); // Alternative Methode
		}
		return jButton2;
	}

	private AbstractAction getClose() {
		if (close == null) {
			close = new AbstractAction("close", null) {
				public void actionPerformed(ActionEvent evt) {
					getJDialog1().dispose();
				}
			};
		}
		return close;
	}

}