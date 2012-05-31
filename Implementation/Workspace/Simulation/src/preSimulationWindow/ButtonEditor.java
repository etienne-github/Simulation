package preSimulationWindow;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/** Classe definisant le comportement des boutons dans le tableau de choix des especes **/
@SuppressWarnings("serial")
public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {

	private JButton editor;
	public ButtonEditor(ImageIcon icon, ActionListener a) {
		super();
		editor = new JButton(icon);
		editor.addActionListener(a);
	}
	
	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		return editor;
	}

}
