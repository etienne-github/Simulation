package preSimulationWindow;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class SpeciesTableModel extends DefaultTableModel {

	private String[] columnNames;
	private ArrayList<Object[]> data; // donnees du tableau
	
	public SpeciesTableModel() {
		columnNames = new String[] { "Espece", "Population", "", "", "" };
		data = new ArrayList<Object[]>();
		data.add(new Object[]{"", "", "", "", ""});
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] c) {
		this.columnNames = c;
	}

	public Object[][] getData() {
		Object[][] dataArray = new Object[data.size()][5];
		
		for (int i = 0; i < data.size(); i++) {
			for (int j = 0; j < 5; j++) {
				dataArray[i][j] = data.get(i)[j];
			}	
		}
		
		return dataArray;
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data.get(row)[col];
	}

	public void setValueAt(Object value, int row, int col) {
		data.get(row)[col] = value;
        fireTableCellUpdated(row, col);
    }	
	
	public void addLine(int idx) {
		data.add(idx, new Object[]{"", "", "", "", ""});
		fireTableRowsInserted(idx-1, idx+1);
	}
	
	public void deleteLine(int idx) {
		data.remove(idx);
		fireTableRowsDeleted(idx-1, idx-1);
	}
	
	// TODO
	public void setSpecies(int idx, int speciesIdx) {
		
	}

	
}
