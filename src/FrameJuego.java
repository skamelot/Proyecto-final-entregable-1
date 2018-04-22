import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FrameJuego extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int SERUNO = 0;
	private static final int SERDOS = 1;
	private static final int SERTRES = 2;
	private JPanel contentPane;
	private Mapas mapa;
	private Tablas modeloTabla;
	private JTable tablaMapa;
	private String nombreInicio;
	private String nombreFin;
	private JLabel lblInfo;

	private JTextField txtInicio;
	private JTextField txtFinal;
	private JTextField txtPosActual;
	private JTextField txtVisita;
	private JScrollPane panelMapa;
	private JLabel lblSeleccion;
	private JLabel lblNombre;
	private JTextField txtNombre;
	private JTextField txtID;
	private JTextField txtColor;
	private JTextField txtCoordenada;
	private JLabel lblVisitas;
	private JTextArea txtVisitas;
	private JButton btnNuevo;
	private int fila;
	private int columna;
	private int iniFila;
	private int iniColumna;
	private int finFila;
	private int finColumna;
	private int posX;
	private int posY;
	private int origenX;
	private int origenY;
	private int movimientoHorizontal;
	private int movimientoVertical;
	private int serSeleccionado;
	private boolean puedeMover;
	private boolean repetir;
	private JLabel imgSer;
	private JButton btnCambiarSer;
	
	private Listas arbol;
	private String nombre;
	private String padre;
	private String[] hijos;
	private JButton btnGenerar;
	private Seres[]	ser;

	public FrameJuego(String nombreInicio, int posIniF, int posIniC, String nombreFin, int posFinF, int posFinC, Mapas propiedades, Seres[]	s) {
		setTitle("Proyecto 1 - Elementos b\u00E1sicos para mapa");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 593);
		setLocationRelativeTo(null); //Siempre se abre en el centro de la pantalla independientemente de la resolución
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ser = s;
		this.nombreInicio = nombreInicio;
		this.nombreFin = nombreFin;
		mapa = propiedades;
		iniFila = posIniF;
		iniColumna = posIniC-1;
		finFila = posFinF;
		finColumna = posFinC-1;
		fila = iniFila;
		columna = iniColumna;
		puedeMover = true;
		
		//Se crea el mapa a mostrar
		modeloTabla = new Tablas(Tablas.JUEGO, mapa.getFilas(), mapa.getColumnas());
		//Cargamos el mapa primero
		mapa.setInicio(iniFila, iniColumna);
		mapa.setFinal(finFila, finColumna);
		//Y después lo coloreamos. Si no cargamos el mapa no muestra nada
		mapa.enmascarar();
		mapa.setMapaVisible(propiedades.getFilas(), propiedades.getColumnas(), fila, columna);
		tablaMapa = modeloTabla.tablaJuego(mapa.getMapeoID(), mapa.getColorTerreno(), mapa.getTerrenosID(), iniFila, iniColumna+1, finFila, finColumna+1, mapa.getMapaVisible());
		
		movimientoHorizontal = modeloTabla.getAnchura();//Obtenemos ancho de celda, es importante porque varía según la cantidad de columnas en el mapa
		movimientoVertical = modeloTabla.getAltura();//Al igual que la anchura, la altura también es variable
		posX = (10 + modeloTabla.getAnchuraNum()) + (iniColumna * movimientoHorizontal);
		/*
		 * Posicionamos el punto X en la posición X de la tabla(10) + 1/6 del ancho de celda para dar la apariencia de que está dentro del cuadro
		 * Y el punto Y es más de lo mismo, es la posición Y de la tabla(39) + la altura de la cabecera de la tabla + 1/4 del alto de la celda
		 * 
		 * Eso lo multiplicamos por la posición de columna donde inicia = iniColumna+1, +1 por la primera columna que es la columna donde están los números
		 * Y lo mismo con la fila, la multiplicamos por la cantidad de filas en las que está +1 porque en caso de que el inicio esté en la fila 0 todo se echa a perder
		*/
		posY = (39 + movimientoVertical) + (iniFila * movimientoVertical);
		origenX = posX;
		origenY = posY;
		imgSer = new JLabel();
		imgSer.setBounds(origenX,origenY , 20,20);
		contentPane.add(imgSer);
		
		tablaMapa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent tecla) {
				if(puedeMover) {
					if(tecla.getKeyCode() == KeyEvent.VK_UP) {
						if(fila > 0) {
							if(ser[serSeleccionado].getCostos()[fila-1][columna]!=-1) {
								fila--;
								mapa.actualizaRecorrido(fila, columna);
								txtPosActual.setText(Tablas.ENCABEZADO_COLUMNAS[columna+1]+Tablas.ENCABEZADO_FILAS[fila]);
								txtVisita.setText(String.valueOf(mapa.getVisitaActual()));
								mapa.setMapaVisible(propiedades.getFilas(), propiedades.getColumnas(), fila, columna);
								tablaMapa = modeloTabla.coloreaTabla(mapa.getMapeoID(), mapa.getMapaRecorrido(), mapa.getColorTerreno(), mapa.getTerrenosID(), mapa.getMapaVisible());
								imgSer.setLocation(imgSer.getX(),imgSer.getY()-movimientoVertical);
		  	                    repaint();
		  	                    arbol.insertarNodo(repetir, nuevoNodo());
							}
						}
					}else if(tecla.getKeyCode() == KeyEvent.VK_DOWN) {
						if(fila < mapa.getFilas()-1) {
							if(ser[serSeleccionado].getCostos()[fila+1][columna] != -1) {
								fila++;
								mapa.actualizaRecorrido(fila, columna);
								txtPosActual.setText(Tablas.ENCABEZADO_COLUMNAS[columna+1]+Tablas.ENCABEZADO_FILAS[fila]);
								txtVisita.setText(String.valueOf(mapa.getVisitaActual()));
								mapa.setMapaVisible(propiedades.getFilas(), propiedades.getColumnas(), fila, columna);
								tablaMapa = modeloTabla.coloreaTabla(mapa.getMapeoID(), mapa.getMapaRecorrido(), mapa.getColorTerreno(), mapa.getTerrenosID(), mapa.getMapaVisible());
								imgSer.setLocation(imgSer.getX(),imgSer.getY()+movimientoVertical);
		  	                    repaint();
		  	                    arbol.insertarNodo(repetir, nuevoNodo());
							}
						}
					}else if(tecla.getKeyCode() == KeyEvent.VK_LEFT) {
						if(columna > 0) {
							if(ser[serSeleccionado].getCostos()[fila][columna-1] != -1) {
								columna--;
								mapa.actualizaRecorrido(fila, columna);
								txtPosActual.setText(Tablas.ENCABEZADO_COLUMNAS[columna+1]+Tablas.ENCABEZADO_FILAS[fila]);
								txtVisita.setText(String.valueOf(mapa.getVisitaActual()));
								mapa.setMapaVisible(propiedades.getFilas(), propiedades.getColumnas(), fila, columna);
								tablaMapa = modeloTabla.coloreaTabla(mapa.getMapeoID(), mapa.getMapaRecorrido(), mapa.getColorTerreno(), mapa.getTerrenosID(), mapa.getMapaVisible());
								imgSer.setLocation(imgSer.getX()-movimientoHorizontal,imgSer.getY());
		  	                    repaint();
		  	                    arbol.insertarNodo(repetir, nuevoNodo());
							}
						}
					}else if(tecla.getKeyCode() == KeyEvent.VK_RIGHT) {
						if(columna < mapa.getColumnas()-1) {
							if(ser[serSeleccionado].getCostos()[fila][columna+1] != -1) {
								columna++;
								mapa.actualizaRecorrido(fila, columna);
								txtPosActual.setText(Tablas.ENCABEZADO_COLUMNAS[columna+1]+Tablas.ENCABEZADO_FILAS[fila]);
								txtVisita.setText(String.valueOf(mapa.getVisitaActual()));
								mapa.setMapaVisible(propiedades.getFilas(), propiedades.getColumnas(), fila, columna);
								tablaMapa = modeloTabla.coloreaTabla(mapa.getMapeoID(), mapa.getMapaRecorrido(), mapa.getColorTerreno(), mapa.getTerrenosID(), mapa.getMapaVisible());
								imgSer.setLocation(imgSer.getX()+movimientoHorizontal,imgSer.getY());
		  	                    repaint();
		  	                    arbol.insertarNodo(repetir, nuevoNodo());
							}
						}
					}	
						
					if(txtPosActual.getText().equals(txtFinal.getText())) {
						puedeMover = false;
						btnCambiarSer.setEnabled(false);
						JOptionPane.showMessageDialog(getRootPane(),"Ha llegado al final, gracias por participar.","",JOptionPane.INFORMATION_MESSAGE);
						Archivos arch = new Archivos();
						arch.generarArbol(arbol, repetir, mapa.getMapaRecorrido());
						arch.abrirCarpeta();
					}
				}
			}
		});
		panelMapa = new JScrollPane(tablaMapa, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelMapa.setBounds(10, 39, 974, 320);
		contentPane.add(panelMapa);
		tablaMapa.addMouseListener(new java.awt.event.MouseAdapter() {
        	@Override
       	 public void mouseClicked(java.awt.event.MouseEvent evt) {
       	    int f = tablaMapa.rowAtPoint(evt.getPoint());
       	    int c = tablaMapa.columnAtPoint(evt.getPoint());
       	    
       	    if (f >= 0 && c >= 1) {
       	    	String fila = Tablas.ENCABEZADO_FILAS[f], columna = Tablas.ENCABEZADO_COLUMNAS[c];
       	    	int pos = 0;
       	    	for(int i=0; i<mapa.getTerrenosID().length; i++)
       	    		if( mapa.getMapeoID()[f][c-1].equals(mapa.getTerrenosID()[i]) ){
       	    			pos = i;
       	    			break;
       	    		}
       	    	txtNombre.setText(mapa.getNombre()[pos]);
       	    	txtID.setText(mapa.getTerrenosID()[pos]);
       	    	txtColor.setBackground(mapa.getColorTerreno()[pos]);
       	    	txtCoordenada.setText(columna+fila);
       	    	txtVisitas.setText(mapa.getMapaRecorrido()[f][c-1]);
       	    }
       	 }
       });
		
		txtInicio = new JTextField(this.nombreInicio);
		txtInicio.setBackground(Color.DARK_GRAY);
		txtInicio.setForeground(Color.WHITE);
		txtInicio.setHorizontalAlignment(SwingConstants.CENTER);
		txtInicio.setEditable(false);
		txtInicio.setBounds(249, 11, 43, 20);
		contentPane.add(txtInicio);
		txtInicio.setColumns(10);
		
		txtFinal = new JTextField(this.nombreFin);
		txtFinal.setForeground(Color.WHITE);
		txtFinal.setBackground(Color.DARK_GRAY);
		txtFinal.setEditable(false);
		txtFinal.setHorizontalAlignment(SwingConstants.CENTER);
		txtFinal.setBounds(363, 11, 43, 20);
		contentPane.add(txtFinal);
		txtFinal.setColumns(10);
		
		txtPosActual = new JTextField(this.nombreInicio);
		txtPosActual.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtPosActual.setForeground(Color.WHITE);
		txtPosActual.setBackground(Color.DARK_GRAY);
		txtPosActual.setHorizontalAlignment(SwingConstants.CENTER);
		txtPosActual.setEditable(false);
		txtPosActual.setBounds(627, 11, 43, 20);
		contentPane.add(txtPosActual);
		txtPosActual.setColumns(10);
		
		txtVisita = new JTextField(String.valueOf(mapa.getVisitaActual()));
		txtVisita.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		txtVisita.setBackground(Color.DARK_GRAY);
		txtVisita.setForeground(Color.WHITE);
		txtVisita.setHorizontalAlignment(SwingConstants.CENTER);
		txtVisita.setEditable(false);
		txtVisita.setBounds(749, 11, 43, 20);
		contentPane.add(txtVisita);
		txtVisita.setColumns(10);
		
		lblInfo = new JLabel("Nodo inicial:                    Nodo final:                                                               Posici\u00F3n actual:                    Visita actual:");
		lblInfo.setFont(new Font("Arial", Font.PLAIN, 11));
		lblInfo.setBounds(188, 14, 618, 14);
		contentPane.add(lblInfo);
		
		lblSeleccion = new JLabel("SELECCIONE UNA CASILLA PARA MOSTRAR SU INFORMACI\u00D3N");
		lblSeleccion.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeleccion.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSeleccion.setBounds(160, 370, 674, 14);
		contentPane.add(lblSeleccion);
		
		txtNombre = new JTextField();
		txtNombre.setEditable(false);
		txtNombre.setBounds(262, 406, 134, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtID = new JTextField();
		txtID.setHorizontalAlignment(SwingConstants.CENTER);
		txtID.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtID.setEditable(false);
		txtID.setBounds(474, 406, 57, 20);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		txtColor = new JTextField();
		txtColor.setEditable(false);
		txtColor.setBounds(634, 406, 57, 20);
		contentPane.add(txtColor);
		txtColor.setColumns(10);
		
		txtCoordenada = new JTextField();
		txtCoordenada.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtCoordenada.setHorizontalAlignment(SwingConstants.CENTER);
		txtCoordenada.setEditable(false);
		txtCoordenada.setBounds(778, 406, 57, 20);
		contentPane.add(txtCoordenada);
		txtCoordenada.setColumns(10);
		
		lblNombre = new JLabel("Nombre de terreno:                                                  ID de terreno:                          Color del terreno:                          Coordenada:");
		lblNombre.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNombre.setBounds(160, 410, 674, 14);
		contentPane.add(lblNombre);
		
		lblVisitas = new JLabel("N\u00FAmero de visitas:");
		lblVisitas.setFont(new Font("Arial", Font.PLAIN, 11));
		lblVisitas.setBounds(160, 437, 106, 14);
		contentPane.add(lblVisitas);
		
		txtVisitas = new JTextArea();
		txtVisitas.setEditable(false);
		txtVisitas.setBounds(160, 454, 674, 57);
		contentPane.add(txtVisitas);
		
		btnNuevo = new JButton("Cargar nuevo mapa");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame nuevo = new MainFrame();
				dispose();
				nuevo.setVisible(true);
			}
		});
		btnNuevo.setBounds(597, 524, 237, 23);
		contentPane.add(btnNuevo);
		
		btnCambiarSer = new JButton("Cambiar ser");
		btnCambiarSer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameSeleccionarSer fss = new FrameSeleccionarSer(ser, serSeleccionado);
				String seleccion = fss.elegirSer();
				if(!seleccion.isEmpty() || !seleccion.equals("")) {
					cargaImagen(seleccion);
					reiniciar();
				}
			}
		});
		
		btnGenerar = new JButton("Generar \u00E1rbol");
		btnGenerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(arbol.getCabeza()!=null) {
					Archivos arch = new Archivos();
					arch.generarArbol(arbol, repetir, mapa.getMapaRecorrido());
					arch.abrirCarpeta();
				}
			}
		});
		btnGenerar.setBounds(160, 524, 236, 25);
		contentPane.add(btnGenerar);
		btnCambiarSer.setFont(new Font("Arial", Font.PLAIN, 12));
		btnCambiarSer.setBounds(856, 9, 126, 25);
		contentPane.add(btnCambiarSer);
		
		int repite = JOptionPane.showConfirmDialog(getRootPane(), "¿Desea repetir nodos en el árbol?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	    if (repite == JOptionPane.YES_OPTION) 
	    	repetir = true;
	    else
	    	repetir = false;
	    
	    if(ser[SERUNO] != null && ser[SERUNO].getCostos()[iniFila][iniColumna] != -1) {
	    	serSeleccionado = SERUNO;
			cargaImagen(ser[serSeleccionado].getRutaImg());
	    }else if(ser[SERDOS] != null && ser[SERDOS].getCostos()[iniFila][iniColumna] != -1) {
	    	serSeleccionado = SERDOS;
			cargaImagen(ser[serSeleccionado].getRutaImg());
    	}else if(ser[SERTRES] != null && ser[SERTRES].getCostos()[iniFila][iniColumna] != -1) {
    		serSeleccionado = SERTRES;
			cargaImagen(ser[serSeleccionado].getRutaImg());
    	}else {
    		JOptionPane.showMessageDialog(getRootPane(),"Los seres no pueden ser colocados en la casilla inicial.\n"
    												  + "Por favor vuelva a intentarlo seleccionando ''Cargar nuevo mapa''.","",JOptionPane.WARNING_MESSAGE);
    		puedeMover = false;
    		btnGenerar.setEnabled(false);
    		btnCambiarSer.setEnabled(false);
    	}
	    
	    if(puedeMover) {
	    	arbol = new Listas();
	    	arbol.insertarNodo(repetir, nuevoNodo());
	    }
	}
	
	private void cargaImagen(String ruta) {
		ImageIcon img = new ImageIcon(getClass().getResource(ruta));
		Image img2 = img.getImage();
		Image img3 = img2.getScaledInstance(imgSer.getWidth(), imgSer.getHeight(), Image.SCALE_SMOOTH);
		img = new ImageIcon(img3);
		imgSer.setIcon(img);
		posX = origenX;
		posY = origenY;
		imgSer.setLocation(origenX, origenY);
	}
	
	private void reiniciar() {
		fila = iniFila;
		columna = iniColumna;
		txtVisita.setText("1");
		txtPosActual.setText(nombreInicio);
		mapa.reinicia(iniFila, iniColumna, finFila, finColumna);
		mapa.setMapaVisible(mapa.getFilas(), mapa.getColumnas(), fila, columna);
		tablaMapa = modeloTabla.tablaJuego(mapa.getMapeoID(), mapa.getColorTerreno(), mapa.getTerrenosID(), iniFila, iniColumna+1, finFila, finColumna+1, mapa.getMapaVisible());
		arbol = new Listas();
		arbol.insertarNodo(repetir, nuevoNodo());
	}
	
	private Nodos nuevoNodo() {
		int c = columna + 1;
		int f = fila;
		nombre = Tablas.ENCABEZADO_COLUMNAS[c] + Tablas.ENCABEZADO_FILAS[f];
		//Orden de expansión: abajo, izquierda, arriba, derecha
		hijos = new String[4];
		if(fila < mapa.getFilas()-1 && ser[serSeleccionado].getCostos()[fila+1][columna] != (double)-1) {//abajo
			hijos[0] = Tablas.ENCABEZADO_COLUMNAS[c] + Tablas.ENCABEZADO_FILAS[f+1];
		}else
			hijos[0] = "";
		if(columna >= 1 && ser[serSeleccionado].getCostos()[fila][columna-1] != (double)-1) {//izquierda
			hijos[1] = Tablas.ENCABEZADO_COLUMNAS[c-1] + Tablas.ENCABEZADO_FILAS[f];
		}else
			hijos[1] = "";
		if(fila >= 1 && ser[serSeleccionado].getCostos()[fila-1][columna] != (double)-1) {//arriba
			hijos[2] = Tablas.ENCABEZADO_COLUMNAS[c] + Tablas.ENCABEZADO_FILAS[f-1];
		}else
			hijos[2] = "";
		if(columna < mapa.getColumnas()-1 && ser[serSeleccionado].getCostos()[fila][columna+1] != (double)-1) {//derecha
			hijos[3] = Tablas.ENCABEZADO_COLUMNAS[c+1] + Tablas.ENCABEZADO_FILAS[f];
		}else
			hijos[3] = "";
		padre = "";
		Nodos nodo = new Nodos(fila, columna, nombre, padre, hijos, mapa.getMapaRecorrido()[fila][columna]);
		
		if(!repetir && arbol.getCabeza()!=null) {
			boolean huerfano = true;
			Nodos tmp = arbol.getCabeza();
			while(huerfano) {
				if(tmp.getNombre().equals(nombre)) {
					padre = tmp.getPadre();
					nodo.setPadre(padre);
					huerfano = false;
				}else {
					String[] hijo = tmp.getHijos();
					for(int h = 0; h<hijo.length; h++) {
						if(!hijo[h].equals("") && tmp.getHijos()[h].equals(nodo.getNombre())) {
							padre = tmp.getNombre();
							nodo.setPadre(padre);
							huerfano = false;
						}
					}
				}
				tmp = tmp.getSiguiente();
				if(tmp==null)
					break;
			}
		}
		return nodo;
	}
}
