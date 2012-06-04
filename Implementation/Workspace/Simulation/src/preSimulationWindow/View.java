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

	private ViewModel viewModel;
	private SpeciesTableModel tableModel;
	private boolean flag; // flag pour savoir si on est en vue simple ou
							// detaillee
	private Border sizePBorder;
	private Border popPBorder;
	private ArrayList<String> speciesList; // liste de toutes les especes
	private JComboBox speciesChoice;

	private ImageIcon addIcon; // icone pour ajouter des especes
	private ImageIcon removeIcon; // icone pour enlever des especes
	private ImageIcon infoIcon; // icone pour afficher les caracteristiques de
								// l'espece

	private JButton randomNumber;
	private JButton okButton;
	private JButton advancedParamsButton;

	private JComboBox gridSizeCombo;

	private JLabel gridHLabel;
	private JLabel gridWLabel;
	private JLabel gridComboLabel;

	private JPanel sizePanel;
	private JPanel popPanel;
	private JPanel buttonPanel;
	private JPanel simTab;
	private RestManagementPane restTab;

	private JTabbedPane tabbedPane;

	private JTable speciesTable;

	private JTextField gridHeight;
	private JTextField gridWidth;

	public View(ViewModel model) {

		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		setSize(Constants.PRE_SIM_WDW_WIDTH, Constants.PRE_SIM_WDW_HEIGHT);
		setTitle("Parametrage de la simulation");

		viewModel = model;
		flag = false;

		addIcon = new ImageIcon("img/add.png");
		removeIcon = new ImageIcon("img/remove.png");
		infoIcon = new ImageIcon("img/info.png");
		tableModel = new SpeciesTableModel();
		speciesList = new ArrayList<String>();
		speciesList.add("");
		for (String species : getAllSpecies()) {
			speciesList.add(species);
		}
		speciesChoice = new JComboBox(speciesList.toArray());
		speciesTable = new JTable(tableModel);
		simTab = new JPanel();
		simTab.setLayout(new BoxLayout(simTab, BoxLayout.PAGE_AXIS));
		restTab = new RestManagementPane();
		tabbedPane = new JTabbedPane();

		/******** Taille de la grille ********/

		gridSizeCombo = new JComboBox(Constants.PREDEFINED_SIZES);
		gridSizeCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setGridSizeFromCombo(gridSizeCombo.getSelectedIndex());

			}
		});
		sizePanel = new JPanel(new GridBagLayout());
		popPanel = new JPanel(new GridBagLayout());
		buttonPanel = new JPanel();
		gridHeight = new JTextField();
		gridHeight.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setCustomCombo();

			}
		});
		gridWidth = new JTextField();
		gridWidth.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setCustomCombo();

			}
		});

		gridHLabel = new JLabel("Hauteur : ");
		gridWLabel = new JLabel("Largeur : ");
		gridComboLabel = new JLabel("Tailles predefinies : ");

		/******** Population ********/

		randomNumber = new JButton("Population aleatoire");
		randomNumber.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/** C'est le modele (ViewModel) qui va effectuer le calcul **/
				randomAnimals();

			}
		});

		speciesTable.setShowGrid(false);
		speciesTable.setFillsViewportHeight(true);
		speciesTable.getColumnModel().getColumn(0)
				.setCellEditor(new DefaultCellEditor(speciesChoice));
		speciesTable.getColumnModel().getColumn(1)
				.setCellEditor(new DefaultCellEditor(new JTextField()));
		speciesTable.getColumnModel().getColumn(2)
				.setCellRenderer(new ButtonRenderer(addIcon));
		speciesTable.getColumnModel().getColumn(2)
				.setCellEditor(new ButtonEditor(addIcon, new ActionListener() {
					// Ajout d'une ligne
					@Override
					public void actionPerformed(ActionEvent e) {
						int idx = speciesTable.getEditingRow();
						addRow(idx);

					}
				}));
		speciesTable.getColumnModel().getColumn(2).setMaxWidth(30);
		speciesTable.getColumnModel().getColumn(3)
				.setCellRenderer(new ButtonRenderer(removeIcon));
		speciesTable
				.getColumnModel()
				.getColumn(3)
				.setCellEditor(
						new ButtonEditor(removeIcon, new ActionListener() {
							// Suppression d'une lignes
							@Override
							public void actionPerformed(ActionEvent e) {
								int idx = speciesTable.getEditingRow();
								/**
								 * Il faut arreter d'editer la ligne pour
								 * pouvoir supprimer le bouton
								 **/
								/**
								 * Car en fait je demande au botuon de supprimer
								 * la ligne sur laquelle il se trouve
								 **/
								/**
								 * Donc si je supprime la derniere ligne, ca va
								 * buguer car Swing va vouloir repeindre la
								 * derniere ligne
								 **/
								/**
								 * A cause de l'editeur, mais y'a plus rien sauf
								 * le bouton (etat instable....)
								 **/
								speciesTable.editingCanceled(null);
								removeRow(idx);
							}
						}));
		speciesTable.getColumnModel().getColumn(3).setMaxWidth(30);
		speciesTable.getColumnModel().getColumn(4)
				.setCellRenderer(new ButtonRenderer(infoIcon));
		speciesTable.getColumnModel().getColumn(4)
				.setCellEditor(new ButtonEditor(infoIcon, new ActionListener() {
					// Ouverture de l'onglet d'informations
					@Override
					public void actionPerformed(ActionEvent e) {
						int row = speciesTable.getEditingRow();
						String species = getSpecies(row);
						showInfos(species);

					}
				}));
		speciesTable.getColumnModel().getColumn(4).setMaxWidth(30);
		speciesTable.setRowHeight(30);

		/******** Boutons en bas de la fenetre : OK et changement de vue ********/

		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Map<String, Integer> species = checkData();
				if (species != null && species.size() > 0) {
					System.out.println("Parametrage de la simulation termine");
					sendToModel(species);
				}
			}
		});
		advancedParamsButton = new JButton("Vue detaillee");
		advancedParamsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!flag)
					toggleAdvanced();
				else
					toggleSimple();

			}
		});

		/******** Agencement dans la fenetre ********/

		sizePBorder = BorderFactory.createTitledBorder("Taille de la grille");
		popPBorder = BorderFactory.createTitledBorder("Population");

		GridBagConstraints c = new GridBagConstraints();

		sizePanel.setBorder(sizePBorder);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weighty = 0.2;
		c.fill = GridBagConstraints.HORIZONTAL;
		sizePanel.add(gridComboLabel, c);

		c.gridx = 1;
		c.weightx = 0.5;

		sizePanel.add(gridSizeCombo, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0.2;
		sizePanel.add(gridHLabel, c);
		gridHLabel.setVisible(false);

		c.gridx = 1;
		sizePanel.add(gridHeight, c);
		gridHeight.setVisible(false);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weighty = 0.2;
		sizePanel.add(gridWLabel, c);
		gridWLabel.setVisible(false);

		c.gridx = 1;
		sizePanel.add(gridWidth, c);
		gridWidth.setVisible(false);

		simTab.add(sizePanel);

		popPanel.setBorder(popPBorder);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.CENTER;
		c.weightx = 0;
		c.weighty = 0;
		popPanel.add(randomNumber, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.7;
		c.weighty = 0.7;
		popPanel.add(new JScrollPane(speciesTable), c);

		simTab.add(popPanel);

		buttonPanel.add(advancedParamsButton);
		buttonPanel.add(okButton);
		simTab.add(buttonPanel);

		tabbedPane.add("Simulation", simTab);
		tabbedPane.add("Service REST", restTab);
		this.add(tabbedPane);
		setGridSizeFromCombo(gridSizeCombo.getSelectedIndex());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	/** Permet de passer d'une vue simple a une vue detaillee **/
	private void toggleAdvanced() {
		setSize(Constants.PRE_SIM_WDW_WIDTH, Constants.PRE_SIM_WDW_HEIGHT + 100);
		gridHLabel.setVisible(true);
		gridHeight.setVisible(true);
		gridWLabel.setVisible(true);
		gridWidth.setVisible(true);
		advancedParamsButton.setText("Vue simple");
		flag = true;

	}

	/** Permet de passer d'une vue detaillee a une vue simple **/
	private void toggleSimple() {
		setSize(Constants.PRE_SIM_WDW_WIDTH, Constants.PRE_SIM_WDW_HEIGHT);
		gridHLabel.setVisible(false);
		gridHeight.setVisible(false);
		gridWLabel.setVisible(false);
		gridWidth.setVisible(false);
		advancedParamsButton.setText("Vue detaillee");
		flag = false;
	}

	/** Envoyer les donnees au modele et detruire la fenetre **/
	private void sendToModel(Map<String, Integer> speciesList) {
		SimProperties properties = new SimProperties();
		properties.setGridWidth(Integer.valueOf(gridWidth.getText()));
		properties.setGridHeight(Integer.valueOf(gridHeight.getText()));
		viewModel.sendToModel(speciesList, properties);

		/** Detruire la fenetre **/
		dispose();
	}

	private Map<String, Integer> checkData() {
		HashMap<String, Integer> speciesSim = new HashMap<String, Integer>();
		String msg = "";
		/** especes en doublons dans la simulation : affichage d'un message **/
		ArrayList<String> speciesDuplicate = new ArrayList<String>();
		/** especes avec une population erronee : affichage d'un message **/
		ArrayList<String> speciesBadPop = new ArrayList<String>();

		for (int row = 0; row < tableModel.getRowCount(); row++) {
			String species = getSpecies(row);
			if (!species.isEmpty()) {
				if (speciesSim.containsKey(species)) {
					speciesDuplicate.add(species);
				} else {
					int pop = getPop(row);
					if (pop == -1)
						speciesBadPop.add(species);
					else
						speciesSim.put(species, pop);
				}
			}
		}
		if (speciesDuplicate.isEmpty() && speciesBadPop.isEmpty())
			return speciesSim;
		else {
			if (!speciesDuplicate.isEmpty()) {
				msg += "Attention, les especes suivantes sont definies plusieurs fois : \n";
				for (String str : speciesDuplicate) {
					msg += "- " + str + " ;\n";
				}
			}
			if (!speciesBadPop.isEmpty()) {
				msg += "Attention, les populations des especes suivantes sont mal definies : \n";
				for (String str : speciesBadPop) {
					msg += "- " + str + " ;\n";
				}
			}
			msg += "Veuillez corriger les donnees de la simulation";
			JOptionPane.showMessageDialog(null, msg, "Warning",
					JOptionPane.WARNING_MESSAGE);
		}
		return null;
	}

	private void addRow(int idx) {
		tableModel.addRow(idx);
		/**
		 * le fireTableRowsInserted continue a selectionner les lignes ajoutees,
		 * d'ou le clearSelection() necessaire
		 **/
		speciesTable.clearSelection();

	}

	private void removeRow(int idx) {
		tableModel.removeRow(idx);

	}

	private void showInfos(String species) {
		if (!species.isEmpty()) {
			String description = viewModel.getRestServer()
					.getSpeciesDescription(species);
			JOptionPane.showMessageDialog(null, description,
					"Informations sur l'espece " + species,
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "No species selected !",
					"Warning", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void randomAnimals() {

		int tableSize = tableModel.getRowCount();
		for (int i = 0; i < tableSize; i++) {
			if (!(getSpecies(i).isEmpty())) {
				int value = viewModel.randomAnimals();
				tableModel.setValueAt(value, i, 1);
			}
		}

	}

	/*********************************** Getters ***********************************/

	public int getGridWidth() {
		int res = -1;
		try {
			res = Integer.valueOf(gridWidth.getText());
		} catch (Exception e) {
			System.out.println("largeur de la grille incorrecte");
			res = -1;
		}
		return res;
	}

	public int getGridHeight() {
		int res = -1;
		try {
			res = Integer.valueOf(gridHeight.getText());
		} catch (Exception e) {
			System.out.println("hauteur de la grille incorrecte");
			res = -1;
		}
		return res;
	}

	/** Recupere le nom de l'espece comme ecrit dans la table **/
	protected String getSpecies(int row) {
		return speciesTable.getValueAt(row, 0).toString();
	}

	private String[] getAllSpecies() {
		String str = viewModel.getRestServer().getSpeciesList();
		return str.substring(1, str.length() - 1).split(", ");
	}

	private int getPop(int row) {
		try {
			int pop = (Integer)speciesTable.getValueAt(row, 1);
			if (pop <= 0) 
				throw new Exception();
			else
				return pop;
		} catch (Exception e) {
			return -1;
		}
	}

	/*********************************** Setters ***********************************/

	public void setGridSizeFromCombo(int idx) {
		if (idx == gridSizeCombo.getItemCount() - 1) {
			toggleAdvanced();
		} else {
			String size = Constants.PREDEFINED_SIZES[idx];
			String[] array = size.split("x");
			try {
				int width = Integer.valueOf(array[0]);
				setGridWidth(width);
			} catch (Exception e) {
				System.out.println("largeur de la grille incorrecte");
			}
			try {
				int height = Integer.valueOf(array[1]);
				setGridHeight(height);
			} catch (Exception e) {
				System.out.println("hauteur de la grille incorrecte");
			}
		}

	}

	public void setGridWidth(int w) {
		gridHeight.setText(Integer.toString(w));
	}

	public void setGridHeight(int h) {
		gridWidth.setText(Integer.toString(h));
	}

	public void setCustomCombo() {
		gridSizeCombo.setSelectedIndex(gridSizeCombo.getItemCount() - 1);
	}

}
