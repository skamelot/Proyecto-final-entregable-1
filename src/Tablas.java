import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.colorchooser.*;

public class Tablas {
	private final static String []ENCABEZADO_FILAS = new String[] {" ","1","2","3","4", "5", "6", "7", "8", "9","10","11","12","13","14","15"};
	private final static String []ENCABEZADO_COLUMNAS = new String[] {" ","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};
	private final static String []ENCABEZADO_SELECCION = new String[] {"Color", "Nombre del terreno", "ID terreno"};
	
	private final static int FILAS = 15;
	private final static int COLUMNAS = 16;
	
	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private Object infoMapa[][];
	
	Tablas(String tipoTabla){
		if(tipoTabla!="Terrenos") {
			//Celdas NO editables, para poder seleccionar el mapa y preguntar datos de las casillas sin que se editen
			modeloTabla = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;
				@Override
			    public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
		}else {
			modeloTabla = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;
				//Con esto la columna ID no puede ser editable pero las demás sí
				boolean[] editables = new boolean[]{
	                    true, true, false
	            };
	            
				@Override
			    public boolean isCellEditable(int row, int column) {
					return editables[column];
			      }
				
				/* Las tablas utilizan esta función para determinar qué mostrar en cada celda. Sin esto no charcha el que salga un botón
			     * para elegir los colores para cada terreno
			     */
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public Class getColumnClass(int c) {
			        return getValueAt(0, c).getClass();
			    }
			};
			modeloTabla.setColumnIdentifiers(ENCABEZADO_SELECCION);
		}
		
		tabla = new JTable(modeloTabla);
	    tabla.setFillsViewportHeight(true);
	    tabla.setRowSelectionAllowed(true);
	    
	    if(tipoTabla.equals("Terrenos"))
	    	tabla.setPreferredScrollableViewportSize(new Dimension(425,150));
	   
	    //Manipulación de tamaño para las columnas
		tamColumnas(tipoTabla);
	}//Fin constructor
	
	private void tamColumnas(String tipoTabla) {
		if(tipoTabla.equals("Terrenos")) {
		    tabla.getColumnModel().getColumn(0).setPreferredWidth(130);//Color
		    tabla.getColumnModel().getColumn(1).setPreferredWidth(225);//Nombre
		    tabla.getColumnModel().getColumn(2).setPreferredWidth(70);//ID
	    }
	}
	
	private void tablaVacia() {
		modeloTabla = (DefaultTableModel) tabla.getModel();
		modeloTabla.setRowCount(0);
	}
	
	public JTable tablaIDTerrenos(String[] datos) {
		tablaVacia();
		
		//Cuenta los terrenos hasta que encuentre un espacio vacío en el arreglo de terrenos distintos
		for(int i=0; i<datos.length; i++) 
			if(datos[i].equals(" "))
				break;
			else
				modeloTabla.addRow(new Object[]{new Color(0,2,39), " ", datos[i]});
		
		return tabla;
	}
	
	public JTable muestraTabla() { 
		return tabla;
	}
}
