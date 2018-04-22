import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

public class FrameSeres extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private Seres ser[];
	private Tablas modeloTabla;
	private JTable tablaSeres;
	private JLabel lblSerUno;
	private JLabel lblSerTres;
	private JLabel lblSerDos;
	private JScrollPane panelTabla;
	private JButton btnCancelar;
	private JButton btnContinuar;
	private JCheckBox habilitarSerUno;
	private JCheckBox habilitarSerDos;
	private JCheckBox habilitarSerTres;
	private JLabel lblNota1;
	private JLabel lblNota2;
	
	public FrameSeres(Mapas propiedades) {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 600,425);
		setType(Type.UTILITY);
		setResizable(false);
		setLocationRelativeTo(null);
		setModal (true);
		setAlwaysOnTop (true);
		setModalityType (ModalityType.APPLICATION_MODAL);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		ser = new Seres[3];
		ser[0] = new Seres(propiedades.getFilas(), propiedades.getColumnas());
		ser[0].setRutaImg("Mexicobolita");
		ser[1] = new Seres(propiedades.getFilas(), propiedades.getColumnas());
		ser[1].setRutaImg("Popeye");
		ser[2] = new Seres(propiedades.getFilas(), propiedades.getColumnas());
		ser[2].setRutaImg("Johnny");
		
		lblSerUno = imagenSer(ser[0].getRutaImg());
		lblSerUno.setBounds(37, 13, 110, 130);
		contentPanel.add(lblSerUno);
		
		lblSerDos = imagenSer(ser[1].getRutaImg());
		lblSerDos.setBounds(242, 13, 110, 130);
		contentPanel.add(lblSerDos);
		
		lblSerTres = imagenSer(ser[2].getRutaImg());
		lblSerTres.setBounds(472, 13, 110, 130);
		contentPanel.add(lblSerTres);
		
		habilitarSerUno = new JCheckBox("");
		habilitarSerUno.setBounds(8, 70, 25, 25);
		contentPanel.add(habilitarSerUno);
		habilitarSerUno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(habilitarSerUno.isSelected())
					modeloTabla.puedeEditar(Tablas.COLUMNA_SERUNO, true);
				else 
					modeloTabla.puedeEditar(Tablas.COLUMNA_SERUNO,false);
				
			}
		});
		
		habilitarSerDos = new JCheckBox("");
		habilitarSerDos.setBounds(210, 70, 25, 25);
		contentPanel.add(habilitarSerDos);
		habilitarSerDos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(habilitarSerDos.isSelected())
					modeloTabla.puedeEditar(Tablas.COLUMNA_SERDOS, true);
				else 
					modeloTabla.puedeEditar(Tablas.COLUMNA_SERDOS,false);
				
			}
		});
		
		habilitarSerTres = new JCheckBox("");
		habilitarSerTres.setBounds(445, 70, 25, 25);
		contentPanel.add(habilitarSerTres);
		habilitarSerTres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(habilitarSerTres.isSelected()) 
					modeloTabla.puedeEditar(Tablas.COLUMNA_SERTRES, true);
				else 
					modeloTabla.puedeEditar(Tablas.COLUMNA_SERTRES, false);
			}
		});
		
		modeloTabla = new Tablas(Tablas.SERES, propiedades.getTerrenosID().length, Tablas.ENCABEZADO_SERES.length);
		tablaSeres = modeloTabla.tablaSeres(propiedades.getTerrenosID(), propiedades.getColorTerreno(), propiedades.getNombre());
		panelTabla = new JScrollPane(tablaSeres, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelTabla.setBounds(8, 156, 570, 151);
		contentPanel.add(panelTabla);
		
		lblNota1 = new JLabel("NOTA:");
		lblNota1.setFont(new Font("Arial", Font.BOLD, 12));
		lblNota1.setBounds(14, 323, 47, 16);
		contentPanel.add(lblNota1);
		
		btnContinuar = new JButton("CONTINUAR");
		btnContinuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double[] costos = new double[tablaSeres.getRowCount()];			
				double[][] tmp = new double[propiedades.getFilas()][propiedades.getColumnas()];
				
				if(habilitarSerUno.isSelected()) {
					for(int f=0; f<tablaSeres.getRowCount(); f++) {
						if(tablaSeres.getValueAt(f, Tablas.COLUMNA_SERUNO).toString().equals("NA") || tablaSeres.getValueAt(f, Tablas.COLUMNA_SERUNO).toString().equals("na"))
							costos[f] = -1;
						else {
							try {
								double costo = Double.valueOf(tablaSeres.getValueAt(f, Tablas.COLUMNA_SERUNO).toString());
								if(costo >= 0)
									costos[f] = costo;
								else {
									JOptionPane.showMessageDialog(getRootPane(),"Por favor verifique el costo para SerUno en ID "+tablaSeres.getValueAt(f, 1).toString()+".","",JOptionPane.WARNING_MESSAGE);
									return;
								}
							}catch(Exception err) {
								JOptionPane.showMessageDialog(getRootPane(),"Por favor verifique el costo para SerUno en ID "+tablaSeres.getValueAt(f, 1).toString()+".","",JOptionPane.WARNING_MESSAGE);
								return;
							}
						}
					}
					
					for(int f=0; f<propiedades.getFilas(); f++) {
						for(int c=0; c<propiedades.getColumnas(); c++) {
							//recorremos la matriz para encontrar el valor de su ID y asignar su costo en otra matriz para facilitar calculos posteriores
							for(int id=0; id<propiedades.getTerrenosID().length; id++) {
								if(propiedades.getMapeoID()[f][c].equals(propiedades.getTerrenosID()[id])) {
									tmp[f][c] = costos[id];
									break;
								}
							}
						}
					}
					ser[0].setCostos(tmp);
				}else
					ser[0] = null;
				
				if(habilitarSerDos.isSelected()) {
					for(int f=0; f<tablaSeres.getRowCount(); f++) {
						if(tablaSeres.getValueAt(f, Tablas.COLUMNA_SERDOS).toString().equals("NA") || tablaSeres.getValueAt(f, Tablas.COLUMNA_SERDOS).toString().equals("na"))
							costos[f] = -1;
						else {
							try {
								double costo = Double.valueOf(tablaSeres.getValueAt(f, Tablas.COLUMNA_SERDOS).toString());
								if(costo >= 0)
									costos[f] = costo;
								else {
									JOptionPane.showMessageDialog(getRootPane(),"Por favor verifique el costo para SerDOS en ID "+tablaSeres.getValueAt(f, 1).toString()+".","",JOptionPane.WARNING_MESSAGE);
									return;
								}
							}catch(Exception err) {
								JOptionPane.showMessageDialog(getRootPane(),"Por favor verifique el costo para SerDos en ID "+tablaSeres.getValueAt(f, 1).toString()+".","",JOptionPane.WARNING_MESSAGE);
								return;
							}
						}
					}
		
					for(int f=0; f<propiedades.getFilas(); f++) {
						for(int c=0; c<propiedades.getColumnas(); c++) {
							//recorremos la matriz para encontrar el valor de su ID y asignar su costo en otra matriz para facilitar calculos posteriores
							for(int id=0; id<propiedades.getTerrenosID().length; id++) {
								if(propiedades.getMapeoID()[f][c].equals(propiedades.getTerrenosID()[id])) {
									tmp[f][c] = costos[id];
									break;
								}
							}
						}
					}
					ser[1].setCostos(tmp);
				}else
					ser[1] = null;
				
				if(habilitarSerTres.isSelected()) {
					for(int f=0; f<tablaSeres.getRowCount(); f++) {
						if(tablaSeres.getValueAt(f, Tablas.COLUMNA_SERTRES).toString().equals("NA") || tablaSeres.getValueAt(f, Tablas.COLUMNA_SERTRES).toString().equals("na"))
							costos[f] = -1;
						else {
							try {
								double costo = Double.valueOf(tablaSeres.getValueAt(f, Tablas.COLUMNA_SERTRES).toString());
								if(costo >= 0)
									costos[f] = costo;
								else {
									JOptionPane.showMessageDialog(getRootPane(),"Por favor verifique el costo para SerTres en ID "+tablaSeres.getValueAt(f, 1).toString()+".","",JOptionPane.WARNING_MESSAGE);
									return;
								}
							}catch(Exception err) {
								JOptionPane.showMessageDialog(getRootPane(),"Por favor verifique el costo para SerTres en ID "+tablaSeres.getValueAt(f, 1).toString()+".","",JOptionPane.WARNING_MESSAGE);
								return;
							}
						}
					}
		
					for(int f=0; f<propiedades.getFilas(); f++) {
						for(int c=0; c<propiedades.getColumnas(); c++) {
							//recorremos la matriz para encontrar el valor de su ID y asignar su costo en otra matriz para facilitar calculos posteriores
							for(int id=0; id<propiedades.getTerrenosID().length; id++) {
								if(propiedades.getMapeoID()[f][c].equals(propiedades.getTerrenosID()[id])) {
									tmp[f][c] = costos[id];
									break;
								}
							}
						}
					}
					ser[2].setCostos(tmp);
				}else
					ser[2] = null;
				
				dispose();
			}
		});
		
		lblNota2 = new JLabel("Si desea que un ser no pueda pasar por un terreno, por favor escriba NA en su costo.");
		lblNota2.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNota2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNota2.setBounds(62, 320, 492, 25);
		contentPanel.add(lblNota2);
		btnContinuar.setBounds(445, 355, 137, 25);
		contentPanel.add(btnContinuar);
		
		btnCancelar = new JButton("CANCELAR");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ser = null;
				dispose();
			}
		});
		btnCancelar.setBounds(8, 355, 137, 25);
		contentPanel.add(btnCancelar);
		
	}
	
	public Seres[] siguienteFrame() {
		setVisible(true);
		return ser;
	}
	
	private JLabel imagenSer(String ruta) {
		JLabel imgSer = new JLabel();
		imgSer.setSize(110, 130);
		ImageIcon img = new ImageIcon(getClass().getResource(ruta));
		Image img2 = img.getImage();
		Image img3 = img2.getScaledInstance(imgSer.getWidth(), imgSer.getHeight(), Image.SCALE_SMOOTH);
		img = new ImageIcon(img3);
		imgSer.setIcon(img);
		return imgSer;
	}
}
