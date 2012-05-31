package preSimulationWindow;

import java.util.HashMap;

public class SimProperties {

	private Integer gridWidth;
	private Integer gridHeight;
	private Integer wolfNumber;
	private Integer hareNumber;
	 
	
	private HashMap<String, Integer> speciesList;
	public SimProperties() {
		speciesList = new HashMap<String, Integer>();
	}

	public SimProperties(SimProperties properties) {
		gridWidth = new Integer(properties.getGridWidth());
		gridHeight = new Integer(properties.getGridHeight());
		wolfNumber = new Integer(properties.getWolfNumber());
		hareNumber = new Integer(properties.getHareNumber());
	}

	public Integer getGridWidth() {
		return gridWidth;
	}

	public void setGridWidth(Integer gridWidth) {
		this.gridWidth = gridWidth;
	}

	public Integer getGridHeight() {
		return gridHeight;
	}

	public void setGridHeight(Integer gridHeight) {
		this.gridHeight = gridHeight;
	}

	public Integer getWolfNumber() {
		return wolfNumber;
	}

	public void setWolfNumber(Integer wolfNumber) {
		this.wolfNumber = wolfNumber;
	}

	public Integer getHareNumber() {
		return hareNumber;
	}

	public void setHareNumber(Integer hareNumber) {
		this.hareNumber = hareNumber;
	}

}
