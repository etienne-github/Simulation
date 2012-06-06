package preSimulationWindow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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

	// FIXME FIXME FIXME : une espece ne peut pas changer de nom ??? Comme elle
	// ne peut pas changer de type, il va falloir rendre
	// ces champs editables ou non suivant l'action voulue
	// FIXME #2 maxNbDaySafeLabel ???

	private JTextField nom; // nom commun
	private JTextArea descriptif;
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
	private JTextField initWeight;
	private JTextField initAge;
	private JTextField birthRateByDay;
	private JComboBox isUseHiddenDefense;

	private JLabel nomLabel;
	private JLabel descriptifLabel;
	private JLabel isHerbivoriousLabel;
	private JLabel typeLabel;
	private JLabel smellPointLabel;
	private JLabel visionPointLabel;
	private JLabel movePointLabel;
	private JLabel maxLifetimeLabel;
	private JLabel minimumWeightToDeathLabel;
	private JLabel weightConsumeByDayLabel;
	private JLabel maxNbDaySafeLabel;
	private JLabel attackPointLabel;
	private JLabel defendPointLabel;
	private JLabel initWeightLabel;
	private JLabel initAgeLabel;
	private JLabel birthRateByDayLabel;
	private JLabel isUseHiddenDefenseLabel;

	// Comment l'afficher ?
	private ArrayList<String> eatableFoodList;

	/** Validation **/

	private JButton okButton;

	/** Structuration des champs **/
	private JPanel actionsPanel;
	private JPanel okPanel;
	private JScrollPane statsPanel;

	/** Constructeur **/
	public RestManagementPane(ViewModel model) {
		
		/** Initialisation **/
		
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
		okPanel = new JPanel(new GridBagLayout());
		statsPanel = new JScrollPane();

		nom = new JTextField();
		descriptif = new JTextArea();
		isHerbivorious = new JTextField();
		type = new JTextField();
		smellPoint = new JTextField();
		visionPoint = new JTextField();
		movePoint = new JTextField();
		maxLifetime = new JTextField();
		minimumWeightToDeath = new JTextField();
		weightConsumeByDay = new JTextField();
		maxNbDaySafe = new JTextField();
		attackPoint = new JTextField();
		defendPoint = new JTextField();
		initWeight = new JTextField();
		initAge = new JTextField();
		birthRateByDay = new JTextField();
		isUseHiddenDefense = new JComboBox(new String[] { "Se cacher", "Fuir" });
		okButton = new JButton("OK");

		nomLabel = new JLabel("Nom commun : ");
		descriptifLabel = new JLabel("Descriptif :");
		isHerbivoriousLabel = new JLabel("Type d'animal : ");
		typeLabel = new JLabel("Nom scientifique : ");
		smellPointLabel = new JLabel("Portee olfactive (en m) : ");
		visionPointLabel = new JLabel("Portee visuelle (en m) : ");
		movePointLabel = new JLabel("Distance parcourue (en m/jour) : ");
		maxLifetimeLabel = new JLabel("Duree de vie maximale : ");
		minimumWeightToDeathLabel = new JLabel("Poids minimum avant famine : ");
		weightConsumeByDayLabel = new JLabel(
				"Poids minimum de nourriture par jour : ");
		maxNbDaySafeLabel = new JLabel("wtf");
		attackPointLabel = new JLabel("Points d'attaque : ");
		defendPointLabel = new JLabel("Points de defense : ");
		initWeightLabel = new JLabel("Poids initial (en kgs) : ");
		initAgeLabel = new JLabel("Age initial : ");
		birthRateByDayLabel = new JLabel("Taux de reproduction journalier : ");
		isUseHiddenDefenseLabel = new JLabel("Comportement en cas de repli : ");

		/** Lien entre les champs & les statistiques **/
		
		actionsList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				switch(checkData()) {
				case 0: // ajouter une espece
					break;
				case 1: // supprimer une espece
					break;
				case 2: // MAJ une espece
					break;
				default: // en cas d'erreur dans la verification des donnees, checkData() renvoie -1
					break;
				}
			}
		});
		
		/** Structuration & affichage **/
		
		this.add(actionsPanel);
		this.add(statsPanel);
		this.add(okPanel);
	}

	private int checkData() {
		// TODO verifier si les donnees entrees sont correctes
		return 0;
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

	// TODO modif de la vue suivant l'action qu'on veut faire (affichage de la liste des especes et/ou edition des cases nom et type)
	@SuppressWarnings("unused")
	private void showSpeciesList() {

	}

	// TODO
	/** Recuperation des donnees entrees par l'utilisateur **/
	@SuppressWarnings("unused")
	private void setSpecies() {

	}

	/** Modification des donnees sur le service Rest **/
	// TODO
	@SuppressWarnings("unused")
	private void updateSpeciesOnRest() {

	}

	// TODO
	@SuppressWarnings("unused")
	private void deleteSpeciesFromRest() {

	}

	// TODO
	@SuppressWarnings("unused")
	private void addSpeciesOnRest() {

	}

}
