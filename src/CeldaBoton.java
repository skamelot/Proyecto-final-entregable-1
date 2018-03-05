import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class CeldaBoton extends AbstractCellEditor implements TableCellEditor,ActionListener {
	private static final long serialVersionUID = 1L;
	
	Color currentColor;
    JButton button;
    JColorChooser colorChooser;
    JDialog dialog;
    protected static final String EDIT = "edit";

    CeldaBoton() {
        button = new JButton();
        button.setActionCommand(EDIT);
        button.addActionListener(this);
        button.setBorderPainted(false);

        //"Constructor" para el cuadro de diálogo que se crea al presionar el botón
        colorChooser = new JColorChooser();
        dialog = JColorChooser.createDialog(button, "Seleccione el color del terreno", true,  colorChooser, this, null);
    }

    public void actionPerformed(ActionEvent e) {
        if (EDIT.equals(e.getActionCommand())) {
            //Si el usuario oprimió el botón, aparece el cuadro de selección de color
            button.setBackground(currentColor);
            colorChooser.setColor(currentColor);
            dialog.setVisible(true);

            fireEditingStopped(); //Renderiza el botón de nuevo, sin esto se queda bugeado el programa

        } else { //Si presiona el botón de OK/Aceptar, color actual se vuelve el nuevo color seleccionado
            currentColor = colorChooser.getColor();
        }
    }

    //Muestra el color a renderizar
    public Object getCellEditorValue() {
        return currentColor;
    }

    //Convierte el espacio blanco en un botón "invisible"
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        currentColor = (Color)value;
        return button;
    }
}