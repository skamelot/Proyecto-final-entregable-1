import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorearCelda extends DefaultTableCellRenderer {
	private Color color;
	private String coloreaID;
	
	ColorearCelda(Color c, String coloreaID){
		color = c;
		this.coloreaID = coloreaID;
	}
    @Override
    public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column)
    {
       // Llama al Constructor Padre
       super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
       
       String variable = table.getValueAt(row, column).toString();
       // Le coloca el color Negro
       if(variable.equals(coloreaID))
    	   this.setBackground(color);
       else
    	   this.setBackground(Color.GREEN);
       
       // Retorna el Objeto
       return this;
    }

}
