import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JRadioButton;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private JLabel lblInfoMapa;
	private JLabel lblCompuesto;
	private JLabel lblFilas;
	private JLabel lblColumnas;
	private JLabel lblInfoTerreno;
	
	private JTextField txtRutaMapa;
	private JTextField txtFilas;
	private JTextField txtColumnas;
	
	private JButton btnCargarMapa;
	private Mapas mapa;
	private JScrollPane panelTerrenos;
	private JTable tablaTerrenos;
	private Tablas modeloTablaTerrenos;
	private JTextField txtTotal;
	private JButton btnContinuar;
	private Tablas modeloTablaPreview;
	private JTable tablaPreview;
	private JLabel lblPreview;
	private JScrollPane panelPreview;
	private JLabel lblNodos;
	private ButtonGroup nodos;
	private JRadioButton rdbtnInicio;
	private JRadioButton rdbtnFinal;
	private JTextField txtInicio;
	private JTextField txtFinal;
	private int iniF;
	private int iniC;
	private int finF;
	private int finC;
	
	public MainFrame() {
		setTitle("Proyecto 1 - Elementos b\u00E1sicos para mapa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 642);
		setResizable(false);
		setLocationRelativeTo(null); //Siempre se abre en el centro de la pantalla independientemente de la resolución
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtRutaMapa = new JTextField();
		txtRutaMapa.setEditable(false);
		txtRutaMapa.setBounds(10, 12, 411, 20);
		contentPane.add(txtRutaMapa);
		txtRutaMapa.setColumns(10);
		
		btnCargarMapa = new JButton("Cargar Mapa");
		btnCargarMapa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser selector = new JFileChooser(); //Archivo a cargar
		        FileNameExtensionFilter filtro = new FileNameExtensionFilter(".txt", "txt", "text"); //Archivos para buscar: "Muestra nombre en la ventana" , "extensión sin punto", "tipo de archivo"
		        selector.setFileFilter(filtro); //Le decimos al selector de archivo que sólo busque .txt
		        selector.setAcceptAllFileFilterUsed(false);//Determina si puede aparecer la opción "Todos los archivos" en el selector o solo el filtro que se indique. Con false solo muestra el filtro
		        int archivo = selector.showOpenDialog(null); //Archivo elegido, int porque las comparaciones de Java son con int, es decir: Cancelar puede ser -1, un archivo elegido es número entero, etc
		        if(archivo == JFileChooser.APPROVE_OPTION) { //En este caso APPROVE_OPTION vale 0; también este if valida que sea un .txt
		        	//selector tiene opciones útiles para manipular con getSelectedFile
		            txtRutaMapa.setText(selector.getSelectedFile().getPath());
		            Archivos arch = new Archivos();
		            try {
						if( arch.leerArchivo(selector.getSelectedFile().getPath())  ) {
							
							//Lectura de archivo y creación del contenido del mapa
							mapa = new Mapas(arch.getFilas(), arch.getColumnas());
							mapa.crearMapas(arch.getTerrenos());
							txtFilas.setText(String.valueOf(mapa.getFilas()));
							txtColumnas.setText(String.valueOf(mapa.getColumnas()));
							txtTotal.setText(String.valueOf(mapa.getColumnas() * mapa.getFilas()));
							//Mostrando tabla de ID terrenos
							tablaTerrenos = modeloTablaTerrenos.tablaIDTerrenos(mapa.getTerrenosID());
							tablaTerrenos.setEnabled(true);
							//Preparamos el editor y render para los colores de terreno
							tablaTerrenos.setDefaultRenderer(Color.class, new ColorBoton(true));
					        tablaTerrenos.setDefaultEditor(Color.class, new CeldaBoton());
					        //Demás componentes visibles/editables
					        tablaTerrenos.setVisible(true);
					        nodos.clearSelection();
					        rdbtnInicio.setEnabled(true);
					        rdbtnFinal.setEnabled(true);
					        txtInicio.setText("");
					        txtFinal.setText("");
					        btnContinuar.setEnabled(true);
					        //Preparamos el preview
					        tablaPreview = modeloTablaPreview.tablaPreview(mapa.getMapeoID(), mapa.getFilas(), mapa.getColumnas());
					        tablaPreview.setEnabled(true);
					        //Añadimos un actionListener al modelo de la tabla para que se coloreé el preview
					        //TableModelEvent e: Contiene fila y columna del dato que se modificó en la tabla
					        //Para poder hacer uso del dato necesitamos que la tabla nos diga qué es: tabla.getValueAt(e.fila, e.columna);
					        tablaTerrenos.getModel().addTableModelListener(new TableModelListener() {
					        	  public void tableChanged(TableModelEvent e) {
					        		 int fila = e.getFirstRow();
					        		 int columna = e.getColumn();
					        		 //case 0: cambio de color
					        		 //case 1: nombre
					        		 switch(columna) {
					        		 case 0:
					        			 mapa.setColor((Color) tablaTerrenos.getValueAt(fila, columna), fila);
					        			 mapa.desenmascarar();
					        			 tablaPreview = modeloTablaPreview.coloreaTabla(mapa.getMapeoID(), mapa.getMapeoID(),  mapa.getColorTerreno(), mapa.getTerrenosID(), mapa.getMapaVisible());
					        			 break;
					        		 case 1:
					        			 break;
					        		 default:
					        			 break;
					        		 }
					        	  }
					        });
					        
					        tablaPreview.addMouseListener(new java.awt.event.MouseAdapter() {
					        	@Override
					        	 public void mouseClicked(java.awt.event.MouseEvent evt) {
					        	    int f = tablaPreview.rowAtPoint(evt.getPoint());
					        	    int c = tablaPreview.columnAtPoint(evt.getPoint());
					        	    
					        	    if (f >= 0 && c >= 1) {
					        	    	String fila = Tablas.ENCABEZADO_FILAS[f], columna = Tablas.ENCABEZADO_COLUMNAS[c];
					        	    	
					        	    	if(rdbtnInicio.isSelected()) {
					        	    		txtInicio.setText(columna+fila);
					        	    		iniF = f;
					        	    		iniC = c;
					        	    	}
					        	    	else if(rdbtnFinal.isSelected()) {
					        	    		txtFinal.setText(columna+fila);
					        	    		finF = f;
					        	    		finC = c;
					        	    	}
					        	    }
					        	 }
					        });
						}
						else {
							JOptionPane.showMessageDialog(getRootPane(), arch.getError());
							txtFilas.setText("");
							txtColumnas.setText("");
							txtTotal.setText("");
							tablaTerrenos = modeloTablaTerrenos.muestraTabla();
							tablaPreview = modeloTablaPreview.muestraTabla();
							txtInicio.setText("");
					        txtFinal.setText("");
					        nodos.clearSelection();
					        rdbtnInicio.setEnabled(false);
					        rdbtnFinal.setEnabled(false);
					        btnContinuar.setEnabled(false);
						}
						
					} catch (IOException e) { e.printStackTrace(); }
		        }
			}
		});
		btnCargarMapa.setBounds(431, 11, 153, 23);
		contentPane.add(btnCargarMapa);
		
		lblInfoMapa = new JLabel("INFORMACI\u00D3N DEL MAPA");
		lblInfoMapa.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoMapa.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblInfoMapa.setBounds(10, 50, 574, 23);
		contentPane.add(lblInfoMapa);
		
		lblCompuesto = new JLabel("El mapa est\u00E1 compuesto por ");
		lblCompuesto.setFont(new Font("Arial", Font.PLAIN, 11));
		lblCompuesto.setBounds(10, 87, 153, 14);
		contentPane.add(lblCompuesto);
		
		lblFilas = new JLabel("filas y");
		lblFilas.setFont(new Font("Arial", Font.PLAIN, 11));
		lblFilas.setBounds(215, 87, 42, 14);
		contentPane.add(lblFilas);
		
		txtTotal = new JTextField();
		txtTotal.setHorizontalAlignment(SwingConstants.CENTER);
		txtTotal.setEditable(false);
		txtTotal.setBounds(476, 84, 42, 20);
		contentPane.add(txtTotal);
		txtTotal.setColumns(10);
		
		lblColumnas = new JLabel("columnas. Teniendo un total de                        casillas.");
		lblColumnas.setFont(new Font("Arial", Font.PLAIN, 11));
		lblColumnas.setBounds(310, 87, 274, 14);
		contentPane.add(lblColumnas);
		
		lblInfoTerreno = new JLabel("TERRENOS DEL MAPA");
		lblInfoTerreno.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoTerreno.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblInfoTerreno.setBounds(10, 122, 574, 23);
		contentPane.add(lblInfoTerreno);
		
		txtFilas = new JTextField();
		txtFilas.setHorizontalAlignment(SwingConstants.CENTER);
		txtFilas.setEditable(false);
		txtFilas.setBounds(163, 84, 42, 20);
		contentPane.add(txtFilas);
		txtFilas.setColumns(10);
		
		txtColumnas = new JTextField();
		txtColumnas.setHorizontalAlignment(SwingConstants.CENTER);
		txtColumnas.setEditable(false);
		txtColumnas.setBounds(258, 84, 42, 20);
		contentPane.add(txtColumnas);
		txtColumnas.setColumns(10);
		
		modeloTablaTerrenos = new Tablas("Terrenos",0,0);
		tablaTerrenos = modeloTablaTerrenos.muestraTabla();
		tablaTerrenos.setEnabled(true);
		tablaTerrenos.setVisible(false);
		panelTerrenos = new JScrollPane(tablaTerrenos, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelTerrenos.setBounds(85, 156, 425, 96);
		contentPane.add(panelTerrenos);
		
		lblPreview = new JLabel("PREVIEW");
		lblPreview.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPreview.setHorizontalAlignment(SwingConstants.CENTER);
		lblPreview.setBounds(10, 263, 574, 20);
		contentPane.add(lblPreview);
		
		modeloTablaPreview = new Tablas("Preview", 1, 15);
		tablaPreview = modeloTablaPreview.muestraTabla();
		tablaPreview.setEnabled(false);
		panelPreview = new JScrollPane(tablaPreview, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelPreview.setBounds(85, 294, 425, 210);
		contentPane.add(panelPreview);
		
		lblNodos = new JLabel("SELECCIONE LOS NODOS PRINCIPALES DANDO CLICK SOBRE LA TABLA PREVIEW");
		lblNodos.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNodos.setHorizontalAlignment(SwingConstants.CENTER);
		lblNodos.setBounds(10, 507, 574, 20);
		contentPane.add(lblNodos);
		
		txtInicio = new JTextField();
		txtInicio.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtInicio.setHorizontalAlignment(SwingConstants.CENTER);
		txtInicio.setForeground(Color.WHITE);
		txtInicio.setBackground(Color.DARK_GRAY);
		txtInicio.setEditable(false);
		txtInicio.setBounds(204, 535, 42, 20);
		contentPane.add(txtInicio);
		txtInicio.setColumns(10);
		
		txtFinal = new JTextField();
		txtFinal.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtFinal.setHorizontalAlignment(SwingConstants.CENTER);
		txtFinal.setForeground(Color.WHITE);
		txtFinal.setBackground(Color.DARK_GRAY);
		txtFinal.setEditable(false);
		txtFinal.setBounds(425, 535, 42, 20);
		contentPane.add(txtFinal);
		txtFinal.setColumns(10);
		
		rdbtnInicio = new JRadioButton("Nodo inicio");
		rdbtnInicio.setEnabled(false);
		rdbtnInicio.setFont(new Font("Arial", Font.PLAIN, 11));
		rdbtnInicio.setBounds(115, 534, 85, 23);
		contentPane.add(rdbtnInicio);
		
		rdbtnFinal = new JRadioButton("Nodo final");
		rdbtnFinal.setEnabled(false);
		rdbtnFinal.setFont(new Font("Arial", Font.PLAIN, 11));
		rdbtnFinal.setBounds(342, 534, 85, 23);
		contentPane.add(rdbtnFinal);
		
		nodos = new ButtonGroup();
		nodos.add(rdbtnInicio);
		nodos.add(rdbtnFinal);
		
		btnContinuar = new JButton(" CONTINUAR ");
		btnContinuar.setEnabled(false);
		btnContinuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Validamos que no tenga nombres vacíos
				for(int i=0; i<tablaTerrenos.getRowCount(); i++) {
					String nombre = tablaTerrenos.getValueAt(i, 1).toString(); //columna nombre
					if(nombre.isEmpty()) {
						JOptionPane.showMessageDialog(getRootPane(), "No puede tener nombres de terrenos vacíos. \nID del terreno con nombre vacío: "+tablaTerrenos.getValueAt(i, 2).toString());
						return;
					}else if(nombre.length()<3) {
						JOptionPane.showMessageDialog(getRootPane(), "Ingrese nombres de terrenos significativos. \nVerificar nombre para ID: "+tablaTerrenos.getValueAt(i, 2).toString());
						return;
					}else {
						String[] nombres = new String[tablaTerrenos.getRowCount()];
						for(int j=0; j<tablaTerrenos.getRowCount(); j++) {
							if(nombre.equals(tablaTerrenos.getValueAt(j,1).toString()) && j!=i) {
								JOptionPane.showMessageDialog(getRootPane(), "¡COLISIÓN DE NOMBRES! El nombre para los ID "+tablaTerrenos.getValueAt(i, 2).toString()+" y "+tablaTerrenos.getValueAt(j,2).toString()+"\n son idénticos. Por favor verifique los nombres de ambos terrenos.");
								return;
							}else
								nombres[j] = tablaTerrenos.getValueAt(j,1).toString();
						}
						mapa.setNombre(nombres);
					}
				}//Fin for de nombres
					
				//Los terrenos pasaron la prueba de los nombres, ahora sigue verificar colores
				for(int i=0; i<tablaTerrenos.getRowCount(); i++) {
					if(!mapa.getColorTerreno()[i].equals(tablaTerrenos.getValueAt(i, 0))) {
						JOptionPane.showMessageDialog(getRootPane(), "No ha seleccionado color para el ID "+tablaTerrenos.getValueAt(i, 2)+". \nSeleccione su color por favor.");
						return;
					}else {
						for(int j=0; j<tablaTerrenos.getRowCount(); j++) {
							if(tablaTerrenos.getValueAt(i, 0).equals(tablaTerrenos.getValueAt(j,0)) && j!=i) {
								JOptionPane.showMessageDialog(getRootPane(), "¡COLISIÓN DE COLORES! Los colores para los ID "+tablaTerrenos.getValueAt(i, 2).toString()+" y "+tablaTerrenos.getValueAt(j,2).toString()+"\n son idénticos. Por favor seleccione distintos colores para ambos.");
								return;
							}
						}
					}
				}//Fin for de colores
				
				/*
				 * Tan simple como eso. ¿Cómo funciona? Facilongo... 
				 * En la clase Mapas existe un arreglo String donde se almacena el color correspondiente a cada ID.
				 * ¿cómo sé cuál es cada ID? Se almacenan en orden de aparición, por lo que la tabla y el arreglo tienen el mismo orden.
				 * El arreglo se inicializa con valores blanco, mientras que la tabla tiene valores negros.
				 * En caso de que no coincidan es porque no se ha elegido color, caso contrario la tabla y el arreglo tendrán el mismo valor.
				 * Y al igual que los nombres, evitamos colores repetidos
				 * 
				 */
				
				//Finalmente validamos que ya se haya seleccionado un nodo inicial y final.
				if(txtInicio.getText().isEmpty()) {
					JOptionPane.showMessageDialog(getRootPane(), "¡Oops! Aún no sé de dónde vamos a iniciar.\nPor favor seleccione el nodo inicial.");
					return;
				}else if(txtFinal.getText().isEmpty()) {
					JOptionPane.showMessageDialog(getRootPane(), "Conocemos el inicio pero no el final.\nPor favor seleccione el nodo final.");
					return;
				}
				
				//Después de haber validado todo, pasamos a la creación de seres
				/*FrameSeres fs = new FrameSeres();
				boolean siguiente = fs.siguienteFrame();
				if(siguiente) {
					FrameJuego juego = new FrameJuego(txtInicio.getText(), iniF, iniC,txtFinal.getText(), finF, finC, mapa);
					juego.setVisible(true);
				}*/
				FrameJuego juego = new FrameJuego(txtInicio.getText(), iniF, iniC,txtFinal.getText(), finF, finC, mapa);
				juego.setVisible(true);
			}
		});
		btnContinuar.setBounds(85, 566, 425, 38);
		contentPane.add(btnContinuar);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
