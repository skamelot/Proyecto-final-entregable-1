import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Component;

public class Tablas {
	public final static String []ENCABEZADO_FILAS = new String[] {"1","2","3","4", "5", "6", "7", "8", "9","10","11","12","13","14","15"};
	public final static String []ENCABEZADO_COLUMNAS = new String[] {" ","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};
	public final static String []ENCABEZADO_SERES = new String[] {"Color", "ID", "Nombre del terreno", "Costos ser 1", "Costos ser 2", "Costos ser 3"};
	private final static String []ENCABEZADO_SELECCION = new String[] {"Color", "Nombre del terreno", "ID terreno"};
	
	public final static int TERRENOS = 1;
	public final static int SERES = 2;
	public final static int PREVIEW = 3;
	public final static int JUEGO = 4;
	
	public final static int COLUMNA_SERUNO = 3;
	public final static int COLUMNA_SERDOS = 4;
	public final static int COLUMNA_SERTRES = 5;
	
	private int filas;
	private int columnas;
	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private boolean[] editables;
	
	Tablas(int tipoTabla, int filas, int columnas){
		this.filas = filas;
		this.columnas = columnas;
		
		if(tipoTabla == TERRENOS){
			editables = new boolean[]{
					true, true, false
	        }; 
			modeloTabla = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;
				//Con esto la columna ID no puede ser editable pero las demás sí 
	            
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
		} else if(tipoTabla == SERES) {
			editables = new boolean[]{
					false, false, false, true, false, false
	        }; 
			modeloTabla = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;
				//Con esto solo la columna ser1 puede ser editable
				//color, id, nombre terreno, ser1, ser2, ser3
				@Override
			    public boolean isCellEditable(int row, int column) { return editables[column]; }
			};
			modeloTabla.setColumnIdentifiers(ENCABEZADO_SERES);
		}else{
			modeloTabla = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;
				@Override
			    public boolean isCellEditable(int row, int column) { return false; }
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
	
	private void propiedades(int tipoTabla) {
		if(tipoTabla == TERRENOS) {
			tabla.setRowHeight(20);
		    tabla.getColumnModel().getColumn(0).setPreferredWidth(120);//Color
		    tabla.getColumnModel().getColumn(1).setPreferredWidth(222);//Nombre
		    tabla.getColumnModel().getColumn(2).setPreferredWidth(70);//ID
	    }else if(tipoTabla == PREVIEW) {
	    	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    	tabla.setRowHeight(180/filas);
	    	tabla.getColumnModel().getColumn(0).setPreferredWidth(20);//cabecera
			int ancho = 405 / columnas+2;
			for(int i=1; i<tabla.getColumnCount(); i++)
				tabla.getColumnModel().getColumn(i).setPreferredWidth(ancho);
	    }else if(tipoTabla == SERES) {
	    	//Tiene un ancho de 570
	    	tabla.setRowHeight(20);
		    tabla.getColumnModel().getColumn(0).setPreferredWidth(70);//Color
		    tabla.getColumnModel().getColumn(1).setPreferredWidth(70);//ID
		    tabla.getColumnModel().getColumn(2).setPreferredWidth(120);//Nombre
		    tabla.getColumnModel().getColumn(3).setPreferredWidth(100);//Ser 1
		    tabla.getColumnModel().getColumn(4).setPreferredWidth(100);//Ser 2
		    tabla.getColumnModel().getColumn(5).setPreferredWidth(100);//Ser 3
	    }else {
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
	
	public int getAltura() { return tabla.getRowHeight(); }
	public int getAnchura() { return tabla.getColumnModel().getColumn(1).getMinWidth()+1; }
	public int getAnchuraNum() { return tabla.getColumnModel().getColumn(0).getPreferredWidth(); } 
	
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
	
	public JTable tablaJuego(String[][] mapeoID, Color[] color, String[] terrenoID, int iniF, int iniC, int finF, int finC, boolean[][] mapaVisible) {
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
	
	@SuppressWarnings("serial")
	public JTable tablaSeres(String[] terrenosID, Color[] colores, String[] nombreTerrenos) {
		tablaVacia();
		
		String[] datos = new String[ENCABEZADO_SERES.length];
		for(int f=0; f<terrenosID.length; f++) {
			datos[0] = "";
			for(int c=1; c<ENCABEZADO_SERES.length; c++) { //color, id, nombre, ser1, ser2, ser3. Desde 1 porque establesco el color por separado de los datos
				if(c == 1)
					datos[c] = terrenosID[f];
				else if(c == 2)
					datos[c] = nombreTerrenos[f];
				else
					datos[c] = "";
			}
			modeloTabla.addRow(datos);
		}
		
		tabla.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
			@Override
		    public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column)
		    {
		       super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
		       for(int i=0; i<terrenosID.length; i++)
		    	   if(tabla.getValueAt(row, 1).equals(terrenosID[i])) {
		    		   this.setBackground(colores[i]);
		    		   break;
		    	   }
		       return this;
		    }
		});
		
		return tabla;
	}
	
	public String[] costosSer(int ser) {
		String[] costos = new String[tabla.getRowCount()];
		for(int f=0; f<tabla.getRowCount(); f++) {
			costos[f] = tabla.getValueAt(f, ser).toString();
		}
		return costos;
	}
	
	public void puedeEditar(int columna, boolean editable) { 
		editables[columna] = editable; 
		//Esto funciona fácil, en el constructor del modelo tabla, es decir casi a inicio del constructor
		//declaramos que para editar la celda se basara en una variable "editables"
		//entonces si cambiamos el valor de "editables" se ve afectado en la tabla a la hora de querer editar
		for(int f=0; f<tabla.getRowCount(); f++)
			tabla.setValueAt("", f, columna);
		//"Limpiamos" los costos del ser, tanto si se habilita como deshabilita
	}
}
