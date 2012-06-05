package preSimulationWindow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;

import simulation.SimProperties;
import utils.Constants;

@SuppressWarnings("serial")
public class View extends JFrame {


	private RestManagementPane restTab;
	private SimulationWindowPane simTab;
	private JTabbedPane tabbedPane;

	public View(ViewModel model) {

		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		setSize(Constants.PRE_SIM_WDW_WIDTH, Constants.PRE_SIM_WDW_HEIGHT);
		setTitle("Parametrage de la simulation");

		restTab = new RestManagementPane();
		simTab = new SimulationWindowPane(model);
		tabbedPane = new JTabbedPane();

		tabbedPane.add("Simulation", simTab);
		tabbedPane.add("Service REST", restTab);
		this.add(tabbedPane);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}




}
