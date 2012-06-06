package preSimulationWindow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import server.SpeciesStats;
import utils.Constants;

@SuppressWarnings("serial")
public class RestManagementPane extends JPanel {

	private ViewModel viewModel;
	private SpeciesStats speciesStats;
	private ArrayList<String> speciesList;
	
	/** Choix d'actions a effectuer **/
	
	private JComboBox actionsList;
	private JComboBox speciesChoice;
	
	/** Caracteristiques de l'espece **/

	private JTextField nom; // nom commun
	private JTextField descriptif;
	private JTextField isHerbivorious;
	private JTextField type; // nom "scientifique"
	private JTextField smellPoint; // by meter
	private JTextField visionPoint; // by meter
	private JTextField movePoint; // by meter per day
	private JTextField maxLifetime;
	private JTextField minimumWeightToDeath;
	private JTextField weightConsumeByDay;
	private JTextField maxNbDaySafe;
	private JTextField attackPoint;
	private JTextField defendPoint;
	private JCheckBox isUseHiddenDefense;
	private JTextField initWeight;
	private JTextField initAge;
	private JTextField birthRateByDay;
	
	// Comment l'afficher ?
	private ArrayList<String> eatableFoodList;
	

	/** Validation **/
	
	private JButton okButton;
	
	/** Structuration des champs **/
	private JPanel actionsPanel;
	private JPanel statsPanel;
	private JPanel okPanel;
	
	/** Constructeur **/
	public RestManagementPane(ViewModel model) {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		viewModel = model;
		speciesList = new ArrayList<String>();
		speciesList.add("");
		for (String species : getAllSpecies()) {
			speciesList.add(species);
		}
		speciesChoice = new JComboBox(speciesList.toArray());
		actionsList = new JComboBox(Constants.REST_MANAGEMENT_ACTIONS);
		
		actionsPanel = new JPanel(new GridBagLayout());
		statsPanel = new JPanel(new GridBagLayout());
		okPanel = new JPanel(new GridBagLayout());
		
		
	}
	
	
	/** Methodes **/
	
	private String[] getAllSpecies() {
		String str = viewModel.getRestServer().getSpeciesList();
		return str.substring(1, str.length() - 1).split(", ");
	}
	
	@SuppressWarnings("unused")
	/** Recuperation des donnees a partir du service Rest **/
	private void getSpeciesFromRest() {
		String species = nom.getText();
		speciesStats = viewModel.getRestServer().getSpecies(species);
	}
	
	// TODO
	/** Recuperation des donnees entrees par l'utilisateur **/
	private void setSpecies() {

	}
	
	/** Modification des donnees sur le service Rest**/
	// TODO
	private void updateSpeciesOnRest() {
		
	}
	
	// TODO
	private void deleteSpeciesFromRest() {
		
	}
	
	// TODO
	private void addSpeciesOnRest() {
		
	}
	
}
