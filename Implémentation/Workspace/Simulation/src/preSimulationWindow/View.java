package preSimulationWindow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;


import utils.Constants;



@SuppressWarnings("serial")
public class View extends JFrame {

	ViewModel viewModel;
	boolean flag;

	JPanel sizePanel;
	JPanel popPanel;
	JPanel buttonPanel;
	JTextField gridHeight;
	JTextField gridWidth;
	JTextField wolfNumber;
	JTextField hareNumber;

	@SuppressWarnings("rawtypes")
	JComboBox gridSizeCombo;

	JButton randomNumber;
	JButton okButton;
	JButton advancedParamsButton;
	JLabel wolfNbLabel;
	JLabel hareNblabel;
	JLabel gridHLabel;
	JLabel gridWLabel;
	JLabel gridComboLabel;
	Border sizePBorder;
	Border popPBorder;

	public View(ViewModel model) {

		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		setSize(300, 300);
		setTitle("Paramétrage de la simulation");
		viewModel = model;

		flag = false;
		gridSizeCombo = new JComboBox<String>(Constants.PREDEFINED_SIZES);
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
		gridWidth.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				setCustomCombo();
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				setCustomCombo();
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				setCustomCombo();
				
			}
		});
		wolfNumber = new JTextField();
		hareNumber = new JTextField();
		randomNumber = new JButton("Population aléatoire");
		randomNumber.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/** C'est le modèle (ViewModel) qui va effectuer le calcul **/
				IntegerArray array = viewModel.randomAnimals();
				setWolfNumber(array.at(0));
				setHareNumber(array.at(1));
			}
		});

		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StringArray str = checkData();
				if (str.getSize() > 0) {
					String message = "Attention, valeur(s) nulle(s) ou incorrecte(s) : \n";
					for (int i = 0; i < str.getSize(); i++) {
						message += "- " + str.get(i) + " \n";
					}
					JOptionPane.showMessageDialog(null, message, "Attention",
							JOptionPane.WARNING_MESSAGE);
				} else {
					System.out.println("Paramétrage de la simulation terminé");
					sendToModel();
				}
			}
		});
		advancedParamsButton = new JButton("Vue détaillée");
		advancedParamsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!flag)
					toggleAdvanced();
				else
					toggleSimple();

			}
		});
		wolfNbLabel = new JLabel("Nombre de loups : ");
		hareNblabel = new JLabel("Nombre de moutons : ");
		gridHLabel = new JLabel("Hauteur : ");
		gridWLabel = new JLabel("Largeur : ");
		gridComboLabel = new JLabel("Tailles prédéfinies : ");

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
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0;
		popPanel.add(randomNumber, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weighty = 0.2;
		popPanel.add(wolfNbLabel, c);

		c.gridx = 1;
		c.weightx = 0.5;
		popPanel.add(wolfNumber, c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		popPanel.add(hareNblabel, c);

		c.gridx = 1;
		popPanel.add(hareNumber, c);

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
		advancedParamsButton.setText("Vue détaillée");
		flag = false;
	}

	// TODO voir si on peut pas faire ca automatiquement : lister tous les JTextField, et boucler sur leur traitement
	/** Envoyer les donnees au modele et detruire la fenetre **/
	private void sendToModel() {

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("gridWidth", Integer.valueOf(gridWidth.getText()));
		properties.put("gridHeight", Integer.valueOf(gridHeight.getText()));
		properties.put("wolfNumber", Integer.valueOf(wolfNumber.getText()));
		properties.put("hareNumber", Integer.valueOf(hareNumber.getText()));
		viewModel.sendToModel(properties);

		/** Detruire la fenetre **/
		dispose();
	}

	private StringArray checkData() {
		StringArray array = new StringArray();

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

		try {
			Integer.parseInt(wolfNumber.getText());
		} catch (Exception e) {
			array.add("nombre de loups");
		}

		try {
			Integer.parseInt(hareNumber.getText());
		} catch (Exception e) {
			array.add("nombre de lapins");
		}
		return array;
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

	public int getWolfNumber() {
		int res = -1;
		try {
			res = Integer.valueOf(wolfNumber.getText());
		} catch (Exception e) {
			System.out.println("population de loups incorrecte");
			res = -1;
		}
		return res;
	}

	public int getHareNumber() {
		int res = -1;
		try {
			res = Integer.valueOf(hareNumber.getText());
		} catch (Exception e) {
			System.out.println("populaiton de lapins incorrecte");
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

	public void setWolfNumber(int nb) {
		wolfNumber.setText(Integer.toString(nb));
	}

	public void setHareNumber(int nb) {
		hareNumber.setText(Integer.toString(nb));
	}

	public void setCustomCombo() {
		gridSizeCombo.setSelectedIndex(gridSizeCombo.getItemCount() - 1);
	}
}
