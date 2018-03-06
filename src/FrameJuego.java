import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class FrameJuego extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Mapas mapa;
	private Tablas modeloTabla;
	private JTable tablaMapa;
	private String ini;
	private String fin;
	private JLabel lblInfo;
	private JTextField txtInicio;
	private JTextField txtFinal;
	private JTextField txtPosActual;
	private JTextField txtVisita;
	private JScrollPane panelMapa;

	public FrameJuego(String ini, String fin, Mapas propiedades ) {
		setTitle("Proyecto 1 - Elementos b\u00E1sicos para mapa");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 550);
		setLocationRelativeTo(null); //Siempre se abre en el centro de la pantalla independientemente de la resolución
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.ini = ini;
		this.fin = fin;
		mapa = propiedades;
		
		txtInicio = new JTextField(this.ini);
		txtInicio.setBackground(Color.DARK_GRAY);
		txtInicio.setForeground(Color.WHITE);
		txtInicio.setHorizontalAlignment(SwingConstants.CENTER);
		txtInicio.setEditable(false);
		txtInicio.setBounds(101, 8, 43, 20);
		contentPane.add(txtInicio);
		txtInicio.setColumns(10);
		
		txtFinal = new JTextField(this.fin);
		txtFinal.setForeground(Color.WHITE);
		txtFinal.setBackground(Color.DARK_GRAY);
		txtFinal.setEditable(false);
		txtFinal.setHorizontalAlignment(SwingConstants.CENTER);
		txtFinal.setBounds(216, 8, 43, 20);
		contentPane.add(txtFinal);
		txtFinal.setColumns(10);
		
		txtPosActual = new JTextField(this.ini);
		txtPosActual.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtPosActual.setForeground(Color.WHITE);
		txtPosActual.setBackground(Color.DARK_GRAY);
		txtPosActual.setHorizontalAlignment(SwingConstants.CENTER);
		txtPosActual.setEditable(false);
		txtPosActual.setBounds(473, 8, 43, 20);
		contentPane.add(txtPosActual);
		txtPosActual.setColumns(10);
		
		txtVisita = new JTextField(String.valueOf(mapa.getVisitaActual()));
		txtVisita.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		txtVisita.setBackground(Color.DARK_GRAY);
		txtVisita.setForeground(Color.WHITE);
		txtVisita.setHorizontalAlignment(SwingConstants.CENTER);
		txtVisita.setEditable(false);
		txtVisita.setBounds(598, 8, 43, 20);
		contentPane.add(txtVisita);
		txtVisita.setColumns(10);
		
		lblInfo = new JLabel("Nodo inicial:                    Nodo final:                                                               Posici\u00F3n actual:                    Visita actual:");
		lblInfo.setFont(new Font("Arial", Font.PLAIN, 11));
		lblInfo.setBounds(38, 11, 618, 14);
		contentPane.add(lblInfo);
		
		//Se crea el mapa a mostrar
		modeloTabla = new Tablas("Juego", mapa.getFilas(), mapa.getColumnas());
		//Cargamos el mapa primero
		tablaMapa = modeloTabla.tablaPreview(mapa.getMapeoID());
		//Y después lo coloreamos. Si no cargamos el mapa no muestra nada
		tablaMapa = modeloTabla.coloreaTabla(mapa.getMapeoID(), mapa.getColorTerreno(), mapa.getTerrenosID());
		panelMapa = new JScrollPane(tablaMapa, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelMapa.setBounds(10, 39, 674, 320);
		contentPane.add(panelMapa);
	}
}
