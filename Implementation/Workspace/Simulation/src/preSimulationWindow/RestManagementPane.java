package preSimulationWindow;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
	private ArrayList<String> eatableFoodList;
	private boolean showSpeciesFlag;

	/** Choix d'actions a effectuer **/

	private JButton actionChoiceButton;
	private JComboBox actionsList;
	private JComboBox speciesChoice;
	private JLabel actionsLabel;

	/** Caracteristiques de l'espece **/

	private JTextField nom; // nom commun
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

	private JTextArea descriptif;

	private JRadioButton isHerbivorious;
	private JRadioButton isCarnivorous;
	private ButtonGroup dietButtons;
	private JPanel dietPanel;

	private JRadioButton defenseHidden;
	private JRadioButton defenseFlee;
	private ButtonGroup defenseButtons;
	private JPanel defensePanel;

	// TODO faire une fenetre a part pour gerer le regime alimentaire
	private JLabel edibleFoodList;
	private JButton edibleFoodButton;

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
	private JLabel edibleFoodListLabel;

	/** Structuration des champs **/
	private JPanel actionsPanel;
	private JPanel statsPanel;
	private JScrollPane statsScrollPane;

	/** Constructeur **/
	public RestManagementPane(ViewModel model) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		/** Initialisation **/
		GridBagConstraints c = new GridBagConstraints();

		viewModel = model;
		showSpeciesFlag = false;
		eatableFoodList = new ArrayList<String>();
		speciesList = new ArrayList<String>();
		speciesList.add("Espèce");
		for (String species : getAllSpecies()) {
			speciesList.add(species);
		}

		speciesChoice = new JComboBox(speciesList.toArray());
		speciesChoice.setVisible(false);
		actionsList = new JComboBox(Constants.REST_MANAGEMENT_ACTIONS);
		actionsLabel = new JLabel("Action : ");
		actionChoiceButton = new JButton("OK");

		actionsPanel = new JPanel(new GridBagLayout());
		statsPanel = new JPanel(new GridBagLayout());
		statsScrollPane = new JScrollPane();

		nom = new JTextField();
		descriptif = new JTextArea(3, 20);
		descriptif.setLineWrap(true);

		isHerbivorious = new JRadioButton("Herbivore");
		isCarnivorous = new JRadioButton("Carnivore");
		dietButtons = new ButtonGroup();
		dietButtons.add(isHerbivorious);
		dietButtons.add(isCarnivorous);
		dietPanel = new JPanel(new GridLayout(1, 0));
		dietPanel.add(isHerbivorious);
		dietPanel.add(isCarnivorous);

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

		defenseHidden = new JRadioButton("Se cacher");
		defenseFlee = new JRadioButton("Fuir");
		defenseButtons = new ButtonGroup();
		defenseButtons.add(defenseHidden);
		defenseButtons.add(defenseFlee);
		defensePanel = new JPanel(new GridLayout(1, 0));
		defensePanel.add(defenseHidden);
		defensePanel.add(defenseFlee);

		edibleFoodList = new JLabel();
		edibleFoodButton = new JButton("+");

		nomLabel = new JLabel("Nom commun : ");
		descriptifLabel = new JLabel("Description de l'espèce :");
		typeLabel = new JLabel("Nom scientifique : ");
		smellPointLabel = new JLabel("Portée olfactive (en m) : ");
		visionPointLabel = new JLabel("Portée visuelle (en m) : ");
		movePointLabel = new JLabel("Distance parcourue (en m/jour) : ");
		maxLifetimeLabel = new JLabel("Durée de vie maximale : ");
		minimumWeightToDeathLabel = new JLabel(
				"Poids minimum avant famine (en kg) : ");
		weightConsumeByDayLabel = new JLabel(
				"Poids minimum de nourriture mangée (en kg/jour) : ");
		maxNbDaySafeLabel = new JLabel("Durée de vie à jeûn : ");
		attackPointLabel = new JLabel("Points d'attaque : ");
		defendPointLabel = new JLabel("Points de défense : ");
		initWeightLabel = new JLabel("Poids initial (en kg) : ");
		initAgeLabel = new JLabel("Âge initial : ");
		birthRateByDayLabel = new JLabel("Taux de reproduction journalier : ");
		isUseHiddenDefenseLabel = new JLabel("Comportement en cas de repli : ");
		isHerbivoriousLabel = new JLabel("Type de régime alimentaire : ");
		edibleFoodListLabel = new JLabel("Régime alimentaire");

		/** Lien entre les champs & les statistiques **/

		actionsList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean show;
				boolean enablePanel;
				switch (actionsList.getSelectedIndex()) {
				case 0:
					show = false;
					enablePanel = false;
					break;
				case 1:
					enablePanel = true;
					show = false;
					break;
				case 2:
					enablePanel = false;
					show = true;
					break;
				default:
					enablePanel = true;
					show = true;
					break;
				}
				enablePanel(statsPanel, enablePanel);
				showSpeciesList(show);

			}
		});

		actionChoiceButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (actionsList.getSelectedIndex()) {
				case 1: // Ajout
					int r_new = checkData();
					if (r_new == 0 && !(nom.getText().isEmpty()) && !(type.getText().isEmpty())) {
						setSpecies();
						addSpeciesOnRest();
					}
					break;
				case 2: // Suppression
					if (speciesChoice.getSelectedIndex() > 0) {
					String msg = "Êtes-vous sûr de vouloir supprimer l'espèce " + 
								speciesChoice.getSelectedItem() + 
								" du service Rest ? \n" +
								"Cette action est irréversible.";
					int action = JOptionPane.showConfirmDialog(null, msg, "Warning",
							JOptionPane.OK_CANCEL_OPTION);
					if (action == JOptionPane.OK_OPTION) {
						String species = (String) speciesChoice.getSelectedItem();
						deleteSpeciesFromRest(species);
					}
						
					}
					else
						JOptionPane.showMessageDialog(null, "No species selected !",
								"Warning", JOptionPane.WARNING_MESSAGE);
					break;
				case 3: // Mise a jour
					int r_maj = checkData();
					if (r_maj == 0) {
						setSpecies();
						updateSpeciesOnRest();
					}
					break;
				default:
					break;
				}
				
			}
		});

		isHerbivorious.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				edibleFoodList.setVisible(false);
				edibleFoodListLabel.setVisible(false);

			}
		});

		isCarnivorous.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				edibleFoodList.setVisible(true);
				edibleFoodListLabel.setVisible(true);

			}
		});

		edibleFoodButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				foodManagement();
				
			}
		});
		
		/** Structuration & affichage **/

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.2;
		c.fill = GridBagConstraints.HORIZONTAL;
		actionsPanel.add(actionsLabel, c);

		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.2;
		actionsPanel.add(actionsList, c);

		c.gridx = 3;
		c.gridy = 0;
		c.weightx = 0.8;
		actionsPanel.add(speciesChoice, c);

		c.gridx = 5;
		c.gridy = 0;
		c.weightx = 0.2;
		c.fill = GridBagConstraints.NONE;
		actionsPanel.add(actionChoiceButton, c);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.2;
		c.fill = GridBagConstraints.HORIZONTAL;
		statsPanel.add(nomLabel, c);

		c.gridx = 1;
		statsPanel.add(nom, c);

		c.gridx = 0;
		c.gridy = 1;
		statsPanel.add(typeLabel, c);

		c.gridx = 1;
		statsPanel.add(type, c);

		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0.2;
		statsPanel.add(descriptifLabel, c);

		c.gridx = 1;
		c.gridwidth = 1;
		c.gridheight = 3;
		statsPanel.add(descriptif, c);

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.2;
		statsPanel.add(isHerbivoriousLabel, c);

		c.gridx = 1;
		statsPanel.add(dietPanel, c);

		c.gridx = 0;
		c.gridy = 6;
		statsPanel.add(edibleFoodListLabel, c);

		c.gridx = 1;
		statsPanel.add(edibleFoodList, c);
		
		c.gridx = 2;
		statsPanel.add(edibleFoodButton, c);
		
		c.gridx = 0;
		c.gridy = 7;
		statsPanel.add(smellPointLabel, c);

		c.gridx = 1;
		statsPanel.add(smellPoint, c);

		c.gridx = 0;
		c.gridy = 8;
		statsPanel.add(visionPointLabel, c);

		c.gridx = 1;
		statsPanel.add(visionPoint, c);

		c.gridx = 0;
		c.gridy = 9;
		statsPanel.add(movePointLabel, c);

		c.gridx = 1;
		statsPanel.add(movePoint, c);

		c.gridx = 0;
		c.gridy = 10;
		statsPanel.add(maxLifetimeLabel, c);

		c.gridx = 1;
		statsPanel.add(maxLifetime, c);

		c.gridx = 0;
		c.gridy = 11;
		statsPanel.add(minimumWeightToDeathLabel, c);

		c.gridx = 1;
		statsPanel.add(minimumWeightToDeath, c);

		c.gridx = 0;
		c.gridy = 12;
		statsPanel.add(weightConsumeByDayLabel, c);

		c.gridx = 1;
		statsPanel.add(weightConsumeByDay, c);

		c.gridx = 0;
		c.gridy = 13;
		statsPanel.add(maxNbDaySafeLabel, c);

		c.gridx = 1;
		statsPanel.add(maxNbDaySafe, c);

		c.gridx = 0;
		c.gridy = 14;
		statsPanel.add(attackPointLabel, c);

		c.gridx = 1;
		statsPanel.add(attackPoint, c);

		c.gridx = 0;
		c.gridy = 15;
		statsPanel.add(defendPointLabel, c);

		c.gridx = 1;
		statsPanel.add(defendPoint, c);

		c.gridx = 0;
		c.gridy = 16;
		statsPanel.add(isUseHiddenDefenseLabel, c);

		c.gridx = 1;
		statsPanel.add(defensePanel, c);

		c.gridx = 0;
		c.gridy = 17;
		statsPanel.add(initWeightLabel, c);

		c.gridx = 1;
		statsPanel.add(initWeight, c);

		c.gridx = 0;
		c.gridy = 18;
		statsPanel.add(initAgeLabel, c);

		c.gridx = 1;
		statsPanel.add(initAge, c);

		c.gridx = 0;
		c.gridy = 19;
		statsPanel.add(birthRateByDayLabel, c);

		c.gridx = 1;
		statsPanel.add(birthRateByDay, c);

		actionsPanel.setBorder(BorderFactory.createTitledBorder("Action"));
		statsScrollPane.setBorder(BorderFactory
				.createTitledBorder("Caractéristiques de l'espèce"));
		statsScrollPane.setViewportView(statsPanel);
		enablePanel(statsPanel, false);

		this.add(actionsPanel);
		this.add(statsScrollPane);
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

	private void showSpeciesList(boolean show) {
		showSpeciesFlag = show;
		speciesChoice.setVisible(showSpeciesFlag);
	}

	@SuppressWarnings("unused")
	private void setEdibleFoodList() {
		String list = "";
		for (String s : eatableFoodList) {
			list += s + ",";
		}
		edibleFoodList.setText(list.substring(0, list.lastIndexOf(",")));
	}

	/** Ouverture d'une nouvelle fenetre pour la gestion de la nourriture **/
	private void foodManagement() {
		// TODO Auto-generated method stub
		
	}

	private void enablePanel(Container container, boolean enablePanel) {
		for (Component comp : container.getComponents()) {
			comp.setEnabled(enablePanel);
		}
	}

	private int checkData() {
		// TODO verifier si les donnees entrees sont correctes
		return 0; // si c'est bon, sinon retourne -1
	}
	
	// TODO setSpecies suivant les donnees entrees dans la vue
	/** Recuperation des donnees entrees par l'utilisateur **/
	private void setSpecies() {

	}

	/** Modification des donnees sur le service Rest **/

	private void updateSpeciesOnRest() {
		viewModel.getRestServer().updateSpecies(speciesStats);
	}

	private void deleteSpeciesFromRest(String species) {
		viewModel.getRestServer().deleteSpecies(species);
	}

	private void addSpeciesOnRest() {
		viewModel.getRestServer().createSpecies(speciesStats);
	}

}
