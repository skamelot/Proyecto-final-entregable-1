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
			
			String[] cabecera = new String[this.columnas];
			for(int i=0; i<this.columnas; i++)
				cabecera[i] = ENCABEZADO_COLUMNAS[i];
			modeloTabla.setColumnIdentifiers(cabecera);
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
	    }else if(tipoTabla.equals("Preview")) {
	    	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    	tabla.setRowHeight(180/filas);
	    	tabla.getColumnModel().getColumn(0).setPreferredWidth(20);//cabecera
			int ancho = 405 / columnas+2;
			//Ahorita está hardcodeado pero el panel donde se muestra la tabla tiene un ancho de 974 - 20 de la columna vacía = 954/cantidad de columnas
			for(int i=1; i<tabla.getColumnCount(); i++)
				tabla.getColumnModel().getColumn(i).setPreferredWidth(ancho);
	    }
	    else {
	    	tabla.setRowHeight(296/filas);
	    	tabla.getColumnModel().getColumn(0).setPreferredWidth(20);
			int ancho = 954 / (columnas-1);
			
			//Ahorita está hardcodeado pero el panel donde se muestra la tabla tiene un ancho de 974 - 20 de la columna vacía = 954/cantidad de columnas
			for(int i=1; i<tabla.getColumnCount(); i++) {
				tabla.getColumnModel().getColumn(i).setPreferredWidth(ancho);
				tabla.getColumnModel().getColumn(i).setMinWidth(ancho);
			}
	    }
	}
	
	private void tablaVacia() {
		modeloTabla = (DefaultTableModel) tabla.getModel();
		modeloTabla.setRowCount(0);
	}
	
	public JTable muestraTabla() { 
		tablaVacia();
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
	
	public JTable tablaPreview(String mapeoID[][], int filas, int columnas) {
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
		for(int i=0; i<this.columnas; i++)
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
		tabla.getColumnModel().getColumn(0).setPreferredWidth(20);//cabecera
		int ancho = 405 / columnas;
		//Ahorita está hardcodeado pero el panel donde se muestra la tabla tiene un ancho de 974 - 20 de la columna vacía = 954/cantidad de columnas
		for(int i=1; i<tabla.getColumnCount(); i++)
			tabla.getColumnModel().getColumn(i).setPreferredWidth(ancho);
		tabla.repaint();
		return tabla;
	}
	
	
	public JTable coloreaTabla(String[][] mapeoID, String[][] mapeoRecorrido, Color[] color, String[] terrenoID, boolean[][] mapaVisible) {
		tablaVacia();
		
		Object[] data = new Object[columnas];
		for(int f=0; f<filas; f++){
			for(int c=0; c<columnas; c++) {
				if(c==0)
					data[c] = ENCABEZADO_FILAS[f];
				else {
					data[c] = mapeoRecorrido[f][c-1];
				}
			}
			modeloTabla.addRow(data);
		}
		
		for(int c=1; c<tabla.getColumnCount(); c++)
			tabla.getColumnModel().getColumn(c).setCellRenderer(new ColorCeldaTabla(color, terrenoID, mapeoID, mapaVisible));
		
		return tabla;
	}
	
	public JTable propiedadesJuego(String[][] mapeoID, Color[] color, String[] terrenoID, int iniF, int iniC, int finF, int finC, boolean[][] mapaVisible) {
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
			tabla.getColumnModel().getColumn(c).setCellRenderer(new ColorCeldaTabla(color, terrenoID, mapeoID, mapaVisible));
		
		for(int f=0; f<tabla.getRowCount(); f++) {
			for(int c=1; c<tabla.getColumnCount(); c++) {
				if(f==iniF && c==iniC)
					tabla.setValueAt("I, 1", f, c);
				else if(f==finF && c==finC)
					tabla.setValueAt("F", f, c);
				else
					tabla.setValueAt("", f, c);
			}
		}		
		
		tabla.getColumnModel().getColumn(0).setPreferredWidth(20);
		tabla.getColumnModel().getColumn(0).setMaxWidth(20);
		int ancho = 954 / columnas;
		//Ahorita está hardcodeado pero el panel donde se muestra la tabla tiene un ancho de 974 - 20 de la columna vacía = 954/cantidad de columnas
		for(int i=1; i<tabla.getColumnCount(); i++)
			tabla.getColumnModel().getColumn(i).setPreferredWidth(ancho);
		
		return tabla;
	}
	
	public int getAltura() { return tabla.getRowHeight(); }
	public int getAnchura() { return tabla.getColumnModel().getColumn(1).getMinWidth()+1; }
	public int getAnchuraNum() { return tabla.getColumnModel().getColumn(0).getPreferredWidth(); } 
}
