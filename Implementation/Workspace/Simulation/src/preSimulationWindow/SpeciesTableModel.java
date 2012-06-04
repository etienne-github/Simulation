package preSimulationWindow;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class SpeciesTableModel extends AbstractTableModel {

	private String[] columnNames;
	private ArrayList<Object[]> data; // donnees du tableau

	public SpeciesTableModel() {
		columnNames = new String[] { "Espece", "Population", "", "", "" };
		data = new ArrayList<Object[]>();
		data.add(new Object[] { "", "", "", "", "" });
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		if (data != null)
			return data.size();
		else
			return 0;
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data.get(row)[col];
	}

	public String getColumnName(int idx) {
		return columnNames[idx];
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

	public Object[] getLine(int idx) {
		return data.get(idx);
	}

	public void setValueAt(Object value, int row, int col) {
		data.get(row)[col] = value;
		fireTableCellUpdated(row, col);
	}

	public void addRow(int idx) {
		data.add(idx, new Object[] { "", "", "", "", "" });
		fireTableRowsInserted(idx, idx);
	}

	public void removeRow(int idx) {
		data.remove(idx);
		fireTableRowsDeleted(idx, idx);
	}

	public boolean isCellEditable(int row, int col) {
		return true;
	}

}
