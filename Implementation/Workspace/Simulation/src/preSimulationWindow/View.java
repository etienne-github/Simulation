package preSimulationWindow;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
		setTitle("Paramétrage de la simulation");

		restTab = new RestManagementPane(model);
		
		simTab = new SimulationWindowPane(model);
		simTab.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("launchSimulation"))
					if (evt.getNewValue().equals(true))
						dispose();
				
			}
		});
		
		tabbedPane = new JTabbedPane();
		tabbedPane.add("Simulation", simTab);
		tabbedPane.add("Service REST", restTab);
		
		add(tabbedPane);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}




}
