import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
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
	private int movimientoHorizontal;
	private int movimientoVertical;
	
	private boolean puedeMover;
	private JLabel imgSer;

	public FrameJuego(String nombreInicio, int posIniF, int posIniC, String nombreFin, int posFinF, int posFinC, Mapas propiedades ) {
		setTitle("Proyecto 1 - Elementos b\u00E1sicos para mapa");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 580);
		setLocationRelativeTo(null); //Siempre se abre en el centro de la pantalla independientemente de la resolución
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
		modeloTabla = new Tablas("Juego", mapa.getFilas(), mapa.getColumnas());
		//Cargamos el mapa primero
		mapa.setInicio(iniFila, iniColumna);
		mapa.setFinal(finFila, finColumna);
		//Y después lo coloreamos. Si no cargamos el mapa no muestra nada
		tablaMapa = modeloTabla.propiedadesJuego(mapa.getMapeoID(), mapa.getColorTerreno(), mapa.getTerrenosID(), iniFila, iniColumna+1, finFila, finColumna+1);
		
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
		imgSer = new JLabel();
		imgSer.setBounds(posX,posY , 20,20);
		contentPane.add(imgSer);
		ImageIcon img = new ImageIcon(getClass().getResource("resources/Mexicoball.png"));
		Image img2 = img.getImage();
		Image img3 = img2.getScaledInstance(imgSer.getWidth(), imgSer.getHeight(), Image.SCALE_SMOOTH);
		img = new ImageIcon(img3);
		imgSer.setIcon(img);
		tablaMapa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent tecla) {
				if(puedeMover) {
					if(tecla.getKeyCode() == KeyEvent.VK_UP) {
						if(fila > 0) {
							fila--;
							mapa.actualizaRecorrido(fila, columna);
							txtPosActual.setText(Tablas.ENCABEZADO_COLUMNAS[columna+1]+Tablas.ENCABEZADO_FILAS[fila]);
							txtVisita.setText(String.valueOf(mapa.getVisitaActual()));
							tablaMapa = modeloTabla.coloreaTabla(mapa.getMapeoID(), mapa.getMapaRecorrido(), mapa.getColorTerreno(), mapa.getTerrenosID());
							imgSer.setLocation(imgSer.getX(),imgSer.getY()-movimientoVertical);
	  	                    repaint();
						}
					}else if(tecla.getKeyCode() == KeyEvent.VK_DOWN) {
						if(fila < mapa.getFilas()-1) {
							fila++;
							mapa.actualizaRecorrido(fila, columna);
							txtPosActual.setText(Tablas.ENCABEZADO_COLUMNAS[columna+1]+Tablas.ENCABEZADO_FILAS[fila]);
							txtVisita.setText(String.valueOf(mapa.getVisitaActual()));
							tablaMapa = modeloTabla.coloreaTabla(mapa.getMapeoID(), mapa.getMapaRecorrido(), mapa.getColorTerreno(), mapa.getTerrenosID());
							imgSer.setLocation(imgSer.getX(),imgSer.getY()+movimientoVertical);
	  	                    repaint();
						}
					}else if(tecla.getKeyCode() == KeyEvent.VK_LEFT) {
						if(columna > 0) {
							columna--;
							mapa.actualizaRecorrido(fila, columna);
							txtPosActual.setText(Tablas.ENCABEZADO_COLUMNAS[columna+1]+Tablas.ENCABEZADO_FILAS[fila]);
							txtVisita.setText(String.valueOf(mapa.getVisitaActual()));
							tablaMapa = modeloTabla.coloreaTabla(mapa.getMapeoID(), mapa.getMapaRecorrido(), mapa.getColorTerreno(), mapa.getTerrenosID());
							imgSer.setLocation(imgSer.getX()-movimientoHorizontal,imgSer.getY());
	  	                    repaint();
						}
					}else if(tecla.getKeyCode() == KeyEvent.VK_RIGHT) {
						if(columna < mapa.getColumnas()-1) {
							columna++;
							mapa.actualizaRecorrido(fila, columna);
							txtPosActual.setText(Tablas.ENCABEZADO_COLUMNAS[columna+1]+Tablas.ENCABEZADO_FILAS[fila]);
							txtVisita.setText(String.valueOf(mapa.getVisitaActual()));
							tablaMapa = modeloTabla.coloreaTabla(mapa.getMapeoID(), mapa.getMapaRecorrido(), mapa.getColorTerreno(), mapa.getTerrenosID());
							imgSer.setLocation(imgSer.getX()+movimientoHorizontal,imgSer.getY());
	  	                    repaint();
						}
					}	
						
					if(txtPosActual.getText().equals(txtFinal.getText()))
						puedeMover = false;
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
		btnNuevo.setBounds(378, 522, 237, 23);
		contentPane.add(btnNuevo);
		
		
	}
}
