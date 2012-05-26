package preSimulationWindow;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/** Classe pour le rendu des boutons dans le tableau de choix des especes **/

@SuppressWarnings("serial")
class ButtonRenderer extends JButton implements TableCellRenderer {

	public ButtonRenderer(ImageIcon icon) {
		super();
		setIcon(icon);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			setBackground(table.getSelectionBackground());
		} else {
			setForeground(table.getForeground());
			setBackground(UIManager.getColor("Button.background"));
		}

		return this;
	}
}
