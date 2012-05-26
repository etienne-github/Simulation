package preSimulationWindow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;

import utils.Constants;

@SuppressWarnings("serial")
public class View extends JFrame {

	ViewModel viewModel;
	SpeciesTableModel speciesTableModel; // stockage des donnees lors du choix des especes
	boolean flag; // flag pour savoir si on est en vue simple ou detaillee
	int nbSpecies; // nombre d'especes dans la simulation
	Border sizePBorder;
	Border popPBorder;
	String[] speciesList; // liste de toutes les especes
/*
	ImageIcon addIcon; // icone pour ajouter des especes
	ImageIcon removeIcon; // icone pour enlever des especes
	ImageIcon infoIcon; // icone pour afficher les caracteristiques de l'espece
*/

	JButton randomNumber;
	JButton okButton;
	JButton advancedParamsButton;
	
	JComboBox gridSizeCombo;
	
	JLabel gridHLabel;
	JLabel gridWLabel;
	JLabel gridComboLabel;
	
	JPanel sizePanel;
	JPanel popPanel;
	JPanel buttonPanel;
	
	JTable speciesTable;
	
	JTextField gridHeight;
	JTextField gridWidth;
	
	public View(ViewModel model) {

		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		setSize(300, 300);
		setTitle("Parametrage de la simulation");

		viewModel = model;
		flag = false;
		nbSpecies = 0;
		speciesTableModel = new SpeciesTableModel(viewModel.getRestServer());
		speciesTable = new JTable(speciesTableModel.getData(), speciesTableModel.getColumnNames());
		speciesTable.setShowGrid(false);
		

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
		
		// TODO recuperation de la population
		randomNumber = new JButton("Population aleatoire");
		randomNumber.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/** C'est le modele (ViewModel) qui va effectuer le calcul **/
				Map<String, Integer> rdPop = viewModel.randomAnimals(nbSpecies);
			
			}
		});
		
		/******** Boutons en bas de la fenetre : OK et changement de vue ********/
		
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] str = checkData();
				if (str.length > 0) {
					String message = "Attention, valeur(s) nulle(s) ou incorrecte(s) : \n";
					for (int i = 0; i < str.length; i++) {
						message += "- " + str[i] + " \n";
					}
					JOptionPane.showMessageDialog(null, message, "Attention",
							JOptionPane.WARNING_MESSAGE);
				} else {
					System.out.println("Parametrage de la simulation termine");
					sendToModel();
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

		this.add(sizePanel);

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
		this.add(popPanel);

		buttonPanel.add(advancedParamsButton);
		buttonPanel.add(okButton);
		this.add(buttonPanel);

		setGridSizeFromCombo(gridSizeCombo.getSelectedIndex());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	/** Permet de passer d'une vue simple a une vue detaillee **/
	private void toggleAdvanced() {
		setSize(300, 400);
		gridHLabel.setVisible(true);
		gridHeight.setVisible(true);
		gridWLabel.setVisible(true);
		gridWidth.setVisible(true);
		advancedParamsButton.setText("Vue simple");
		flag = true;

	}

	/** Permet de passer d'une vue detaillee a une vue simple **/
	private void toggleSimple() {
		setSize(300, 300);
		gridHLabel.setVisible(false);
		gridHeight.setVisible(false);
		gridWLabel.setVisible(false);
		gridWidth.setVisible(false);
		advancedParamsButton.setText("Vue detaillee");
		flag = false;
	}

	/** Envoyer les donnees au modele et detruire la fenetre **/
	private void sendToModel() {

		SimProperties properties = new SimProperties();
		properties.setGridWidth(Integer.valueOf(gridWidth.getText()));
		properties.setGridHeight(Integer.valueOf(gridHeight.getText()));
		viewModel.sendToModel(properties);

		/** Detruire la fenetre **/
		dispose();
	}

	private String[] checkData() {
		ArrayList<String> array = new ArrayList<String>();

		try {
			Integer.parseInt(gridWidth.getText());
		} catch (Exception e) {
			array.add("largeur de la grille");
		}

		try {
			Integer.parseInt(gridHeight.getText());
		} catch (Exception e) {
			array.add("hauteur de la grille");
		}

		String[] result = new String[array.size()];
		for (int i = 0; i < array.size(); i++) {
			result[i] = array.get(i);
		}

		return result;
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
			System.out.println("largeur de la grille incorrecte");
			res = -1;
		}
		return res;
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
