import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Tablas {
	private final static String []ENCABEZADO_FILAS = new String[] {" ","1","2","3","4", "5", "6", "7", "8", "9","10","11","12","13","14","15"};
	private final static String []ENCABEZADO_COLUMNAS = new String[] {" ","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};
	private final static String []ENCABEZADO_SELECCION = new String[] {"Color", "Nombre del terreno", "ID terreno en .txt"};
	
	private final static int FILAS = 15;
	private final static int COLUMNAS = 16;
	
	private JTable tabla;
	private DefaultTableModel modeloTabla;
	
	
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
				
				boolean[] editables = new boolean[]{
	                    true, true, false
	            };
	            
				@Override
			    public boolean isCellEditable(int row, int column) {
			    		return editables[column];//Con esto la columna ID no puede ser editable pero las demás sí
			      }
			};
			modeloTabla = new DefaultTableModel();
			modeloTabla.setColumnIdentifiers(ENCABEZADO_SELECCION);
		}
		
		tabla = new JTable(modeloTabla);
	    tabla.setFillsViewportHeight(true);
	    tabla.setRowSelectionAllowed(true);
	}//Fin constructor
	
	private void tablaVacia() {
		modeloTabla = (DefaultTableModel) tabla.getModel();
		modeloTabla.setRowCount(0);
	}
	
	public JTable actualizarTabla(String tipoTabla, String[] datos) {
		tablaVacia();
		
		if(tipoTabla.equals("Terrenos")) {
			for(int i=0; i<datos.length; i++)
				modeloTabla.addRow(new Object[]{" ", " ", datos[i]});
		}
		
		return tabla;
	}
	
	public JTable muestraTabla() { //Inventario completo de total o escasos 
		return tabla;
	}
}
