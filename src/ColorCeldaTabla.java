import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorCeldaTabla extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	
	private Color[] color;
	private String[] terrenoID;
	private String[][] mapeoID;
	private boolean[][] mapaVisible;
	
	ColorCeldaTabla(Color[] color, String[] terrenoID, String[][] mapeoID, boolean mapaVisible[][]){
		this.color = color;
		this.terrenoID = terrenoID;
		this.mapeoID = mapeoID;
		this.mapaVisible = mapaVisible;
	}
	
    @Override
    public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column)
    {
       // Llama al constructor padre
       super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
       
       int pos=0;
       for(int i=0; i<terrenoID.length; i++)
    	   if(mapeoID[row][column-1].equals(terrenoID[i])){
    		   pos = i;
    		   break;
    	   }
       //Una vez que nos posicionamos en el color, le asignamos el fondo de celda 
       if(mapaVisible[row][column-1]==true)//Columna-1 porque esto se basa en toda la tabla y no las variables, es decir, también toma en cuenta la columna vacía(números)
    	   this.setBackground(color[pos]);
       else
    	   this.setBackground(Color.BLACK);
       
       // Retornamos el objeto (celda completa) con solo el fondo de color
       return this;
    }
}
