import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Tablas {
	private final static String []ENCABEZADO_FILAS = new String[] {"1","2","3","4", "5", "6", "7", "8", "9","10","11","12","13","14","15"};
	private final static String []ENCABEZADO_COLUMNAS = new String[] {" ","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};
	
	private final static int FILAS = 15;
	private final static int COLUMNAS = 16;
	
	private JTable tabla;
	private DefaultTableModel modeloTabla;
	
	
	Tablas(){
		//Celdas NO editables
		modeloTabla = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
		    public boolean isCellEditable(int row, int column) {
		    		return false;
		      }
		};
		
		//modeloTabla.setColumnIdentifiers();
		tabla = new JTable(modeloTabla);
	    tabla.setFillsViewportHeight(true);
	    tabla.setRowSelectionAllowed(true);
	}//Fin constructor
}
