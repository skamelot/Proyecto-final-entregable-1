import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorCeldaTabla extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	
	private Color[] color;
	private String[] terrenoID;
	
	ColorCeldaTabla(Color[] color, String[] terrenoID){
		this.color = color;
		this.terrenoID = terrenoID;
	}
	
    @Override
    public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column)
    {
       // Llama al constructor padre
       super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
       
       int pos=0;
       String valorID = table.getValueAt(row, column).toString();
       //Extrajimos el ID del terreno, ahora lo que sigue es encontrar su color dentro del arreglo
       for(int i=0; i<terrenoID.length; i++)
    	   if(valorID.equals(terrenoID[i])){
    		   pos = i;
    		   break;
    	   }
       //Una vez que nos posicionamos en el color, le asignamos el fondo de celda 
       this.setBackground(color[pos]);
       
       // Retornamos el objeto (celda completa) con solo el fondo de color
       return this;
    }
}
