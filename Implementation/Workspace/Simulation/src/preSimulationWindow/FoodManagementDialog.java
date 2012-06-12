package preSimulationWindow;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import client.RestFacilities;

@SuppressWarnings("serial")
public class FoodManagementDialog extends JDialog {

	private ArrayList<String> speciesList;
	private JComboBox speciesChoice;

	private RestFacilities server;

	private SpeciesTableModel tableModel;
	private JTable foodTable;

	private ImageIcon addIcon; // icone pour ajouter des especes
	private ImageIcon removeIcon; // icone pour enlever des especes
	private ImageIcon infoIcon; // icone pour afficher les caracteristiques de
								// l'espece
	private JButton okButton;

	private JPanel panel;

	public FoodManagementDialog(ArrayList<String> sList, RestFacilities serv,
			ArrayList<String> foodList) {
		this.setSize(300, 300);
		this.setModal(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		speciesList = sList;
		server = serv;

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		addIcon = new ImageIcon("img/add.png");
		removeIcon = new ImageIcon("img/remove.png");
		infoIcon = new ImageIcon("img/info.png");

		tableModel = new SpeciesTableModel();
		foodTable = new JTable(tableModel);
		speciesChoice = new JComboBox(speciesList.toArray());

		okButton = new JButton("OK");
		
		/** Recuperation des valeurs existantes concernant la nourriture **/
		if (foodList != null) {
			for (String f : foodList) {
				System.out.println(f);
				
				tableModel.addRow(0);
				int idx = speciesList.indexOf(f);
				foodTable.setValueAt(idx, 0, 0);
			}
		}

		/** Gestion du tableau **/

		foodTable.setShowGrid(true);
		foodTable.setFillsViewportHeight(true);
		foodTable.getColumnModel().getColumn(0)
				.setCellEditor(new DefaultCellEditor(speciesChoice));
		foodTable.getColumnModel().getColumn(1)
				.setCellEditor(new DefaultCellEditor(new JTextField()));
		foodTable.getColumnModel().getColumn(2)
				.setCellRenderer(new ButtonRenderer(addIcon));
		foodTable.getColumnModel().getColumn(2)
				.setCellEditor(new ButtonEditor(addIcon, new ActionListener() {
					// Ajout d'une ligne
					@Override
					public void actionPerformed(ActionEvent e) {
						int idx = foodTable.getEditingRow();
						addRow(idx);

					}
				}));
		foodTable.getColumnModel().getColumn(2).setMaxWidth(30);
		foodTable.getColumnModel().getColumn(3)
				.setCellRenderer(new ButtonRenderer(removeIcon));
		foodTable
				.getColumnModel()
				.getColumn(3)
				.setCellEditor(
						new ButtonEditor(removeIcon, new ActionListener() {
							// Suppression d'une lignes
							@Override
							public void actionPerformed(ActionEvent e) {
								int idx = foodTable.getEditingRow();
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
								foodTable.editingCanceled(null);
								removeRow(idx);
							}
						}));
		foodTable.getColumnModel().getColumn(3).setMaxWidth(30);
		foodTable.getColumnModel().getColumn(4)
				.setCellRenderer(new ButtonRenderer(infoIcon));
		foodTable.getColumnModel().getColumn(4)
				.setCellEditor(new ButtonEditor(infoIcon, new ActionListener() {
					// Ouverture de l'onglet d'informations
					@Override
					public void actionPerformed(ActionEvent e) {
						int row = foodTable.getEditingRow();
						String species = getSpecies(row);
						showInfos(species);

					}
				}));
		foodTable.getColumnModel().getColumn(4).setMaxWidth(30);
		foodTable.setRowHeight(30);
		foodTable.removeColumn(foodTable.getColumnModel().getColumn(1));

		/** Action apres avoir clique sur 'OK' **/
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendEdibleFoodList();
				dispose();

			}
		});

		/** Ajout dans la fenetre **/

		panel.add(foodTable);
		panel.add(okButton, BorderLayout.PAGE_END);

		this.add(panel);
		this.setVisible(true);
	}

	protected void sendEdibleFoodList() {
		HashSet<String> edibleFoodList = new HashSet<String>();
		String s = "Regime alimentaire : ";
		String l = "";

		for (int i = 0; i < tableModel.getRowCount(); i++) {
			edibleFoodList.add(getSpecies(i));
		}
		
		for (String e : edibleFoodList) {
			l += e + ", ";
		}
		
		s += (l.substring(0, l.lastIndexOf(",")));
		System.out.println(s);

	}

	private void addRow(int idx) {
		tableModel.addRow(idx);
		/**
		 * le fireTableRowsInserted continue a selectionner les lignes ajoutees,
		 * d'ou le clearSelection() necessaire
		 **/
		foodTable.clearSelection();

	}

	private void removeRow(int idx) {
		tableModel.removeRow(idx);

	}

	private void showInfos(String species) {
		if (!species.isEmpty()) {
			String description = server.getSpeciesDescription(species);
			JOptionPane.showMessageDialog(null, description,
					"Informations sur l'espèce " + species,
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "No species selected !",
					"Warning", JOptionPane.WARNING_MESSAGE);
		}
	}

	/** Recupere le nom de l'espece comme ecrit dans la table **/
	protected String getSpecies(int row) {
		return foodTable.getValueAt(row, 0).toString();
	}
}
