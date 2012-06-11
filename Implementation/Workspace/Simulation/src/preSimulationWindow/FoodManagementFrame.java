package preSimulationWindow;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class FoodManagementFrame extends JFrame {

	//speciesTable.removeColumn(speciesTable.getColumnModel().getColumn(1));
	private ArrayList<String> speciesList;
	private JComboBox speciesChoice;
	
	private SpeciesTableModel tableModel;
	private JTable foodTable;

	private ImageIcon addIcon; // icone pour ajouter des especes
	private ImageIcon removeIcon; // icone pour enlever des especes
	private ImageIcon infoIcon; // icone pour afficher les caracteristiques de
								// l'espece
	private JButton okButton;
	
	private JPanel panel;
	
	public FoodManagementFrame (ArrayList<String> sList) {
		addIcon = new ImageIcon("img/add.png");
		removeIcon = new ImageIcon("img/remove.png");
		infoIcon = new ImageIcon("img/info.png");
		
		tableModel = new SpeciesTableModel();
		foodTable = new JTable(tableModel);
		speciesChoice = new JComboBox(speciesList.toArray());
		
		
		okButton = new JButton("ok");
	}
}
