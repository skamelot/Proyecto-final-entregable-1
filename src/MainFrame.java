import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.xml.internal.ws.Closeable;
import com.sun.xml.internal.ws.org.objectweb.asm.Label;

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
import javax.swing.ScrollPaneConstants;

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
	private boolean primerMapa = true;
	
	public MainFrame() {
		setTitle("Proyecto 1 - Elementos b\u00E1sicos para mapa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
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
							tablaTerrenos.setDefaultRenderer(Color.class, new CeldaPuchurrable(true));
					        tablaTerrenos.setDefaultEditor(Color.class, new SelectorColor());
					        //Preparamos el preview
					        if(primerMapa) {
					        	lblPreview.setVisible(true);
						        modeloTablaPreview = new Tablas("Preview", mapa.getFilas(), mapa.getColumnas());
								tablaPreview = modeloTablaPreview.tablaPreview(mapa.getMapeoID());
								panelPreview = new JScrollPane(tablaPreview, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
								panelPreview.setBounds(85, 294, 425, 210);
								contentPane.add(panelPreview);
								primerMapa = false;
					        }else {
					        	tablaPreview = modeloTablaPreview.actualizaPreview(mapa.getMapeoID(), mapa.getFilas(), mapa.getColumnas());
					        }
					       
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
					        			 tablaPreview = modeloTablaPreview.colorPreview(mapa.getMapeoID(), mapa.getColorTerreno(), mapa.getTerrenosID());
					        			 break;
					        		 case 1:
					        			 break;
					        		 default:
					        			 break;
					        		 }
					        	  }
					        });
					        
					        btnContinuar.setEnabled(true);
							//Aquí se manipularía del punto 
							//1.2 en adelante
						}
						else {
							JOptionPane.showMessageDialog(getRootPane(), arch.getError());
							txtFilas.setText("");
							txtColumnas.setText("");
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
		panelTerrenos = new JScrollPane(tablaTerrenos, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelTerrenos.setBounds(85, 156, 425, 96);
		contentPane.add(panelTerrenos);
		
		btnContinuar = new JButton(" CONTINUAR ");
		btnContinuar.setEnabled(false);
		btnContinuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnContinuar.setBounds(85, 522, 425, 38);
		contentPane.add(btnContinuar);
		
		lblPreview = new JLabel("PREVIEW");
		lblPreview.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPreview.setHorizontalAlignment(SwingConstants.CENTER);
		lblPreview.setBounds(10, 263, 574, 20);
		lblPreview.setVisible(false);
		contentPane.add(lblPreview);
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
