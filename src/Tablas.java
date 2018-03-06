import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;

public class Tablas {
	public final static String []ENCABEZADO_FILAS = new String[] {"1","2","3","4", "5", "6", "7", "8", "9","10","11","12","13","14","15"};
	public final static String []ENCABEZADO_COLUMNAS = new String[] {" ","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};
	private final static String []ENCABEZADO_SELECCION = new String[] {"Color", "Nombre del terreno", "ID terreno"};
	
	private int filas;
	private int columnas;
	private JTable tabla;
	private DefaultTableModel modeloTabla;
	
	Tablas(String tipoTabla, int filas, int columnas){
		this.filas = filas;
		this.columnas = columnas;
		
		if(tipoTabla.equals("Terrenos")){
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
		} else{
			modeloTabla = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;
				@Override
			    public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			this.columnas++;
		}
		
		tabla = new JTable(modeloTabla);
	    tabla.setFillsViewportHeight(true);
	    tabla.setRowSelectionAllowed(true);
	    
	    //Manipulación de tamaño para las columnas
		propiedades(tipoTabla);
	}//Fin constructor
	
	private void propiedades(String tipoTabla) {
		if(tipoTabla.equals("Terrenos")) {
			tabla.setRowHeight(20);
		    tabla.getColumnModel().getColumn(0).setPreferredWidth(120);//Color
		    tabla.getColumnModel().getColumn(1).setPreferredWidth(222);//Nombre
		    tabla.getColumnModel().getColumn(2).setPreferredWidth(70);//ID
	    }else
	    	tabla.setRowHeight(180/filas);
	}
	
	private void tablaVacia() {
		modeloTabla = (DefaultTableModel) tabla.getModel();
		modeloTabla.setRowCount(0);
	}
	
	public JTable muestraTabla() { 
		return tabla;
	}
	
	public JTable tablaIDTerrenos(String[] datos) {
		tablaVacia();
		//Cuenta los terrenos hasta que encuentre un espacio vacío en el arreglo de terrenos distintos
		for(int i=0; i<datos.length; i++) 
			if(datos[i].equals(" "))
				break;
			else
				modeloTabla.addRow(new Object[]{new Color(0,0,0), "", datos[i]});
		
		return tabla;
	}
	
	public JTable tablaPreview(String mapeoID[][]) {
		tablaVacia();
		
		String[] cabecera = new String[this.columnas];
		for(int i=0; i<columnas; i++)
			cabecera[i] = ENCABEZADO_COLUMNAS[i];
		modeloTabla.setColumnIdentifiers(cabecera);
		
		String[] dataID = new String[columnas];
		for(int f=0; f<filas; f++) {
			for(int c=0; c<columnas; c++) {
				if(c==0)
					dataID[c] = ENCABEZADO_FILAS[f];
				else
					dataID[c] = mapeoID[f][c-1];
			}
			modeloTabla.addRow(dataID);
		}
		tabla.getColumnModel().getColumn(0).setPreferredWidth(33);//cabecera
		return tabla;
	}
	
	public JTable actualizaPreview(String mapeoID[][], int filas, int columnas) {
		this.filas = filas;
		this.columnas = columnas+1;
		
		modeloTabla = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
		    public boolean isCellEditable(int row, int column) {
				return false;
			}
			
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int c) {
		        return getValueAt(0, c).getClass();
		    }
		};
		
		String[] cabecera = new String[this.columnas];
		for(int i=0; i<columnas; i++)
			cabecera[i] = ENCABEZADO_COLUMNAS[i];
		modeloTabla.setColumnIdentifiers(cabecera);
		modeloTabla.setRowCount(0);
		tabla.setModel(modeloTabla);
	    tabla.setFillsViewportHeight(true);
	    tabla.setRowSelectionAllowed(true);
	    tabla.setRowHeight(180/filas);
	    
	    String[] dataID = new String[this.columnas];
		for(int f=0; f<filas; f++) {
			for(int c=0; c<this.columnas; c++) {
				if(c==0)
					dataID[c] = ENCABEZADO_FILAS[f];
				else
					dataID[c] = mapeoID[f][c-1];
			}
			modeloTabla.addRow(dataID);
		}
		tabla.getColumnModel().getColumn(0).setPreferredWidth(33);//cabecera
		tabla.repaint();
		return tabla;
	}
	
	public JTable coloreaTabla(String[][] mapeoID, Color[] color, String[] terrenoID) {
		tablaVacia();
		
		Object[] data = new Object[columnas];
		for(int f=0; f<filas; f++){
			for(int c=0; c<columnas; c++) {
				if(c==0)
					data[c] = ENCABEZADO_FILAS[f];
				else {
					data[c] = mapeoID[f][c-1];
				}
			}
			modeloTabla.addRow(data);
		}
		
		for(int c=1; c<tabla.getColumnCount(); c++)
			tabla.getColumnModel().getColumn(c).setCellRenderer(new ColorCeldaTabla(color, terrenoID));
		
		return tabla;
	}
	
	
}
