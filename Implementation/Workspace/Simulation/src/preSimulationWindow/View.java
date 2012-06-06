package preSimulationWindow;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

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
