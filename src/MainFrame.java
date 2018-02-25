import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

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
							
							Mapas manejadorMapas = new Mapas(arch.getFilas(), arch.getColumnas());
							manejadorMapas.crearMapas(arch.getTerrenos());
							txtFilas.setText(String.valueOf(manejadorMapas.getFilas()));
							txtColumnas.setText(String.valueOf(manejadorMapas.getColumnas()));
							
							//Aquí se manipularía del punto 1.2 en adelante
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
		
		lblColumnas = new JLabel("columnas.");
		lblColumnas.setFont(new Font("Arial", Font.PLAIN, 11));
		lblColumnas.setBounds(310, 87, 75, 14);
		contentPane.add(lblColumnas);
		
		lblInfoTerreno = new JLabel("INFORMACI\u00D3N DEL TERRENO DEL MAPA");
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
