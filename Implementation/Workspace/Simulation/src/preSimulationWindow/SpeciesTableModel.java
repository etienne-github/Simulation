package preSimulationWindow;

import javax.swing.table.AbstractTableModel;

import client.RestFacilities;

@SuppressWarnings("serial")
public class SpeciesTableModel extends AbstractTableModel {

	private RestFacilities servRes;	
	private String[] columnNames = {"Espece", "Population", "", "", ""};
	private String[] speciesList;
	private Object[][] data = {
			{"Loup", "123", "A", "S", "I"},
			{"Lapin", "234", "A", "S", "I"},
			{"Chevre", "345", "A", "S", "I"},
			{"Grizzli", "456", "A", "S", "I"}
	};
	
	public SpeciesTableModel(RestFacilities serv) {
		servRes = serv;
		speciesList = getAllSpecies();
		
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public String getColumnName(int idx) {
        return columnNames[idx];
    }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}


	public String[] getColumnNames() {
		return columnNames;
	}
	
	public Object[][] getData() {
		return data;
	}
	
	private String[] getAllSpecies() {
		String str = servRes.getSpeciesList();
		return str.substring(1, str.length()-1).split(", ");
	}

}
