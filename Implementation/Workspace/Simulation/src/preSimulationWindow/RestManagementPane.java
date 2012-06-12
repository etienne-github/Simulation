package preSimulationWindow;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;

import server.SpeciesStats;
import utils.Constants;

@SuppressWarnings("serial")
public class RestManagementPane extends JPanel {

	private ViewModel viewModel;
	private SpeciesStats speciesStats;
	private ArrayList<String> speciesList;
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

	private Border initBorder;
	
	/** Constructeur **/
	public RestManagementPane(ViewModel model) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		/** Initialisation **/
		GridBagConstraints c = new GridBagConstraints();
		
		viewModel = model;
		showSpeciesFlag = false;
		speciesList = new ArrayList<String>();
		speciesStats = new SpeciesStats();

		speciesChoice = new JComboBox();
		fetchSpeciesList();
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
		edibleFoodButton.setMaximumSize(edibleFoodButton.getSize());

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

		initBorder = nom.getBorder();
		
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
					clearPanel(statsPanel);
					edibleFoodList.setText("");
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
					if (r_new == 0) {
						setSpecies();
						addSpeciesOnRest();
						System.out.println("L'espèce " + nom.getText()
								+ " a bien été ajoutée au service Rest.");
					}
					break;
				case 2: // Suppression
					if (speciesChoice.getSelectedIndex() > 0) {
						String msg = "Êtes-vous sûr de vouloir supprimer l'espèce "
								+ speciesChoice.getSelectedItem()
								+ " du service Rest ? \n"
								+ "Cette action est irréversible.";
						int action = JOptionPane.showConfirmDialog(null, msg,
								"Warning", JOptionPane.OK_CANCEL_OPTION);
						if (action == JOptionPane.OK_OPTION) {
							String species = (String) speciesChoice
									.getSelectedItem();
							deleteSpeciesFromRest(species);
							System.out.println("L'espèce " + nom.getText()
									+ " a bien été supprimée du service Rest.");
						}

					} else
						JOptionPane.showMessageDialog(null,
								"No species selected !", "Warning",
								JOptionPane.WARNING_MESSAGE);
					break;
				case 3: // Mise a jour en 3 temps
					/**
					 * Si la case contenant le nom est vide, alors il faut aller
					 * recuperer les stats sur le serveur Si la case nom n'est
					 * pas vide mais ne correpsond pas a l'espece choisie, c'est
					 * que l'utilisateur veut faire une misque a jour d'une
					 * autre espece Sinon si la case contenant le nom n'est pas
					 * vide et qu'elle correspond au nom de l'espece choisie,
					 * c'est-à-dire que l'utilisateur a fait sa mise à jour Donc
					 * il faut la mettre sur le serveur Sinon s
					 **/
					String spec = nom.getText();
					String choice = (String) speciesChoice.getSelectedItem();
					/** Choix d'espece **/
					if (spec.isEmpty() && speciesChoice.getSelectedIndex() > 0) {
						getSpeciesFromRest();
						enablePanel(statsPanel, true);
						setView();

					} else if (!(spec.isEmpty()) && !(choice.equals(spec))) {
						/** L'utilisateur veut modifier une autre espece **/
						getSpeciesFromRest();
						enablePanel(statsPanel, true);
						setView();
					} else if (speciesChoice.getSelectedIndex() == 0) {
						/** L'utilisateur n'a selectionne aucune espece **/
						JOptionPane.showMessageDialog(null,
								"No species selected !", "Warning",
								JOptionPane.WARNING_MESSAGE);
					} else { /** Mise a jour **/
						int r_maj = checkData();
						if (r_maj == 0) {
							setSpecies();
							updateSpeciesOnRest();
							System.out.println("L'espèce "
									+ nom.getText()
									+ " a bien été mise à jour dans le service Rest.");
						}
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
				edibleFoodButton.setVisible(false);
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
		c.gridwidth = 3;
		// statsPanel.add(edibleFoodPanel, c);
		statsPanel.add(edibleFoodListLabel, c);

		c.gridx = 1;
		statsPanel.add(edibleFoodList, c);

		c.gridx = 2;
		statsPanel.add(edibleFoodButton, c);

		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 1;
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

	private void fetchSpeciesList() {
		String[] list = viewModel.getSpeciesList();

		speciesChoice.removeAllItems();
		speciesChoice.addItem("Espèce");

		for (String species : list) {
			speciesList.add(species);
			speciesChoice.addItem(species);
		}

	}

	/** Recuperation des donnees a partir du service Rest **/
	private void getSpeciesFromRest() {
		String species = (String) speciesChoice.getSelectedItem();
		speciesStats = viewModel.getRestServer().getSpecies(species);
	}

	private void showSpeciesList(boolean show) {
		showSpeciesFlag = show;
		speciesChoice.setVisible(showSpeciesFlag);
	}

	/** Affichage des donnees **/
	private void setView() {

		nom.setText(speciesStats.getNom());
		type.setText(speciesStats.getType());
		descriptif.setText(speciesStats.getDescriptif());
		smellPoint.setText(speciesStats.getSmellPoint().toString());
		visionPoint.setText(speciesStats.getVisionPoint().toString());
		movePoint.setText(speciesStats.getMovePoint().toString());
		maxLifetime.setText(speciesStats.getMaxLifetime().toString());
		minimumWeightToDeath.setText(speciesStats.getMinimumWeightToDeath()
				.toString());
		weightConsumeByDay.setText(speciesStats.getWeightConsumeByDay()
				.toString());
		maxNbDaySafe.setText(speciesStats.getMaxNbDaySafe().toString());
		attackPoint.setText(speciesStats.getAttackPoint().toString());
		defendPoint.setText(speciesStats.getDefendPoint().toString());
		initWeight.setText(speciesStats.getInitWeight().toString());
		initAge.setText(speciesStats.getInitAge().toString());
		birthRateByDay.setText(speciesStats.getBirthRateByDay().toString());

		if (speciesStats.getIsHerbivorious()) {
			isHerbivorious.setSelected(true);
			edibleFoodList.setVisible(false);
			edibleFoodListLabel.setVisible(false);
			edibleFoodButton.setVisible(false);
		} else {
			isCarnivorous.setSelected(true);
			setEdibleFoodList();
		}

		if (speciesStats.getIsUseHiddenDefense())
			defenseHidden.setSelected(true);
		else
			defenseFlee.setSelected(true);

		nom.setEnabled(false);
		type.setEnabled(false);
	}

	/** Affichage de la liste des especes comestibles **/
	private void setEdibleFoodList() {
		String list = "";

		for (String s : speciesStats.getEatableFoodList()) {
			System.out.println("miam " + s);
			list += s + ",";
		}
		edibleFoodList.setText(list.substring(0, list.lastIndexOf(",")));
	}

	/** Ouverture d'une nouvelle fenetre pour la gestion de la nourriture **/
	private void foodManagement() {
		System.out
				.println("Ouverture d'une fenetre pour la gestion des especes mangees");
		if (viewModel.getNameList().size() == 0)
				viewModel.setNameList();
		FoodManagementDialog dialog = new FoodManagementDialog(speciesList, viewModel, speciesStats.getEatableFoodList());
		dialog.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void enablePanel(Container container, boolean enablePanel) {
		for (Component comp : container.getComponents()) {
			if (comp instanceof Container)
				enablePanel((Container) comp, enablePanel);
			comp.setEnabled(enablePanel);
		}
	}

	private void clearPanel(Container container) {
		for (Component comp : container.getComponents()) {
			if (comp instanceof JTextComponent) {
				JTextComponent field = (JTextComponent) comp;
				field.setText("");
			}

		}
	}

	private int checkData() {
		String emptyFields = "Attention, les valeurs suivantes doivent etre renseignées : \n";
		String wrongFields = "Attention, les valeurs suivantes sont erronées : \n";
		boolean ok = true; // vrai si tous les champs sont remplis
		boolean wrongF = true; // vrai si tous les champs sont remplis
								// correctement

		if (nom.getText().isEmpty()) {
			emptyFields += "- nom commun de l'espèce \n";
			nom.setBorder(BorderFactory.createLineBorder(Color.RED));
			ok = false;
		}

		if (type.getText().isEmpty()) {
			emptyFields += "- nom scientifique de l'espèce \n";
			type.setBorder(BorderFactory.createLineBorder(Color.RED));
			ok = false;
		}

		try {
			if (smellPoint.getText().isEmpty()) {
				emptyFields += "- portée olfactive \n";
				smellPoint.setBorder(BorderFactory.createLineBorder(Color.RED));
				ok = false;
			} else
				Double.parseDouble(smellPoint.getText());
		} catch (Exception e) {
			wrongFields += "- portée olfactive \n";
			smellPoint.setBorder(BorderFactory.createLineBorder(Color.RED));
			ok = false;
			wrongF = false;
		}

		try {
			if (visionPoint.getText().isEmpty()) {
				emptyFields += "- portée visuelle \n";
				visionPoint.setBorder(BorderFactory.createLineBorder(Color.RED));
				ok = false;
			} else
				Double.parseDouble(visionPoint.getText());
		} catch (Exception e) {
			wrongFields += "- portée visuelle \n";
			visionPoint.setBorder(BorderFactory.createLineBorder(Color.RED));
			ok = false;
			wrongF = false;
		}

		try {
			if (movePoint.getText().isEmpty()) {
				emptyFields += "- distance journalière parcourue \n";
				movePoint.setBorder(BorderFactory.createLineBorder(Color.RED));
				ok = false;
			} else
				Double.parseDouble(movePoint.getText());
		} catch (Exception e) {
			wrongFields += "- distance journalière parcourue \n";
			movePoint.setBorder(BorderFactory.createLineBorder(Color.RED));
			ok = false;
			wrongF = false;
		}

		try {
			if (maxLifetime.getText().isEmpty()) {
				emptyFields += "- durée de vie maximale \n";
				maxLifetime.setBorder(BorderFactory.createLineBorder(Color.RED));
				ok = false;
			} else
				Double.parseDouble(maxLifetime.getText());
		} catch (Exception e) {
			wrongFields += "- durée de vie maximale \n";
			maxLifetime.setBorder(BorderFactory.createLineBorder(Color.RED));
			ok = false;
			wrongF = false;
		}

		try {
			if (minimumWeightToDeath.getText().isEmpty()) {
				emptyFields += "- poids minimum avant famine \n";
				minimumWeightToDeath.setBorder(BorderFactory.createLineBorder(Color.RED));
				ok = false;
			} else
				Double.parseDouble(minimumWeightToDeath.getText());
		} catch (Exception e) {
			wrongFields += "- poids minimum avant famine \n";
			minimumWeightToDeath.setBorder(BorderFactory.createLineBorder(Color.RED));
			ok = false;
			wrongF = false;
		}

		try {
			if (weightConsumeByDay.getText().isEmpty()) {
				emptyFields += "- poids minimum de nourriture mangée quotidiennement \n";
				weightConsumeByDay.setBorder(BorderFactory.createLineBorder(Color.RED));
				ok = false;
			} else
				Double.parseDouble(weightConsumeByDay.getText());
		} catch (Exception e) {
			wrongFields += "- poids minimum de nourriture mangée quotidiennement \n";
			weightConsumeByDay.setBorder(BorderFactory
					.createLineBorder(Color.RED));
			ok = false;
			wrongF = false;
		}

		try {
			if (maxNbDaySafe.getText().isEmpty()) {
				emptyFields += "- durée de vie à jeûn \n";
				maxNbDaySafe.setBorder(BorderFactory.createLineBorder(Color.RED));
				ok = false;
			} else
				Double.parseDouble(maxNbDaySafe.getText());
		} catch (Exception e) {
			wrongFields += "- durée de vie à jeûn \n";
			maxNbDaySafe.setBorder(BorderFactory.createLineBorder(Color.RED));
			ok = false;
			wrongF = false;
		}

		try {
			if (attackPoint.getText().isEmpty()) {
				emptyFields += "- points d'attaque \n";
				attackPoint.setBorder(BorderFactory.createLineBorder(Color.RED));
				ok = false;
			} else
				Double.parseDouble(attackPoint.getText());
		} catch (Exception e) {
			wrongFields += "- points d'attaque \n";
			attackPoint.setBorder(BorderFactory.createLineBorder(Color.RED));
			ok = false;
			wrongF = false;
		}

		try {
			if (defendPoint.getText().isEmpty()) {
				emptyFields += "- points de défense \n";
				defendPoint.setBorder(BorderFactory.createLineBorder(Color.RED));
				ok = false;
			} else
				Double.parseDouble(defendPoint.getText());
		} catch (Exception e) {
			wrongFields += "- points de défense \n";
			defendPoint.setBorder(BorderFactory.createLineBorder(Color.RED));
			ok = false;
			wrongF = false;
		}

		try {
			if (initWeight.getText().isEmpty()) {
				emptyFields += "- poids initial \n";
				initWeight.setBorder(BorderFactory.createLineBorder(Color.RED));
				ok = false;
			} else
				Double.parseDouble(initWeight.getText());
		} catch (Exception e) {
			wrongFields += "- poids initial \n";
			initWeight.setBorder(BorderFactory.createLineBorder(Color.RED));
			ok = false;
			wrongF = false;
		}

		try {
			if (initAge.getText().isEmpty()) {
				emptyFields += "- âge initial \n";
				initAge.setBorder(BorderFactory.createLineBorder(Color.RED));
				ok = false;
			} else
				Double.parseDouble(initAge.getText());
		} catch (Exception e) {
			wrongFields += "- âge initial \n";
			initAge.setBorder(BorderFactory.createLineBorder(Color.RED));
			ok = false;
			wrongF = false;
		}

		try {
			if (birthRateByDay.getText().isEmpty()) {
				emptyFields += "- taux de reproduction journalier\n";
				birthRateByDay.setBorder(BorderFactory.createLineBorder(Color.RED));
				ok = false;
			} else
				Double.parseDouble(birthRateByDay.getText());
		} catch (Exception e) {
			wrongFields += "- taux de reproduction journalier \n";
			birthRateByDay.setBorder(BorderFactory.createLineBorder(Color.RED));
			ok = false;
			wrongF = false;
		}

		if (dietButtons.getSelection() == null) {
			emptyFields += "- type de régime alimentaire \n";
			dietPanel.setBorder(BorderFactory.createLineBorder(Color.RED));

			ok = false;
		}

		if (isCarnivorous.isSelected() && edibleFoodList.getText().isEmpty()) {
			emptyFields += "- régime alimentaire \n";
			dietPanel.setBorder(BorderFactory.createLineBorder(Color.RED));

			ok = false;
		}

		if (defenseButtons.getSelection() == null) {
			emptyFields += "- comportement en cas de repli \n";
			defensePanel.setBorder(BorderFactory.createLineBorder(Color.RED));
			ok = false;
		}

		String message = "";
		if (!wrongF)
			message += wrongFields;
		else if (!ok)
			message += emptyFields;

		if (!message.isEmpty())
			JOptionPane.showMessageDialog(null, message, "Warning",
					JOptionPane.WARNING_MESSAGE);

		if (ok)
			return 0;

		return -1; // 0 si c'est bon, sinon retourne -1
	}

	/** Recuperation des donnees entrees par l'utilisateur **/
	private void setSpecies() {
		speciesStats.setNom(nom.getText());
		speciesStats.setType(type.getText());

		speciesStats.setSmellPoint(Double.parseDouble(smellPoint.getText()));
		speciesStats.setVisionPoint(Double.parseDouble(visionPoint.getText()));
		speciesStats.setMovePoint(Double.parseDouble(movePoint.getText()));
		speciesStats.setMaxLifetime(Double.parseDouble(maxLifetime.getText()));
		speciesStats.setMinimumWeightToDeath(Double
				.parseDouble(minimumWeightToDeath.getText()));
		speciesStats.setWeightConsumeByDay(Double
				.parseDouble(weightConsumeByDay.getText()));
		speciesStats
				.setMaxNbDaySafe(Double.parseDouble(maxNbDaySafe.getText()));
		speciesStats.setAttackPoint(Double.parseDouble(attackPoint.getText()));
		speciesStats.setDefendPoint(Double.parseDouble(defendPoint.getText()));
		speciesStats.setInitWeight(Double.parseDouble(initWeight.getText()));
		speciesStats.setInitAge(Double.parseDouble(initAge.getText()));
		speciesStats.setBirthRateByDay(Double.parseDouble(birthRateByDay
				.getText()));

		if (isHerbivorious.isSelected())
			speciesStats.setIsHerbivorious(true);
		else
			speciesStats.setIsHerbivorious(false);

		if (defenseHidden.isSelected())
			speciesStats.setIsUseHiddenDefense(true);
		else
			speciesStats.setIsUseHiddenDefense(false);

		for (Component c : statsPanel.getComponents()) {
			if (c instanceof JTextArea || c instanceof JTextField)
				((JComponent) c).setBorder(initBorder);
		}
	}

	/** Modification des donnees sur le service Rest **/

	private void updateSpeciesOnRest() {
		viewModel.getRestServer().updateSpecies(speciesStats);
	}

	private void deleteSpeciesFromRest(String species) {
		viewModel.getRestServer().deleteSpecies(species);
		fetchSpeciesList();
		speciesChoice.setSelectedIndex(0);

	}

	private void addSpeciesOnRest() {
		viewModel.getRestServer().createSpecies(speciesStats);
		fetchSpeciesList();
		speciesChoice.setSelectedIndex(0);
	}

}
