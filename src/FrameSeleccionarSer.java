import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrameSeleccionarSer extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private ButtonGroup seres;
	private JRadioButton rdbtnSerUno;
	private JRadioButton rdbtnSerDos;
	private JRadioButton rdbtnSerTres;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JLabel lblSerUno;
	private JLabel lblSerDos;
	private JLabel lblSerTres;
	private String seleccion;

	public FrameSeleccionarSer(Seres ser[], int serSeleccionado) {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 628,240);
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
		
		seleccion = "";
		
		if(ser[0] != null)
			lblSerUno = cargaImagen(ser[0].getRutaImg(), 129, 116);
		else
			lblSerUno = new JLabel();
		lblSerUno.setBounds(37, 13, 129, 116);
		contentPanel.add(lblSerUno);
		
		if(ser[1] != null)
			lblSerDos = cargaImagen(ser[0].getRutaImg(), 129, 116);
		else
			lblSerDos = new JLabel();
		lblSerDos.setBounds(241, 9, 129, 116);
		contentPanel.add(lblSerDos);
		
		if(ser[2] != null)
			lblSerTres = cargaImagen(ser[0].getRutaImg(), 129, 116);
		else
			lblSerTres = new JLabel();
		lblSerTres.setBounds(452, 9, 129, 116);
		contentPanel.add(lblSerTres);
		
		rdbtnSerUno = new JRadioButton("");
		rdbtnSerUno.setBounds(8, 9, 186, 132);
		contentPanel.add(rdbtnSerUno);
		
		rdbtnSerDos = new JRadioButton("");
		rdbtnSerDos.setBounds(213, 9, 186, 132);
		contentPanel.add(rdbtnSerDos);
		
		rdbtnSerTres = new JRadioButton("");
		rdbtnSerTres.setBounds(418, 9, 186, 132);
		contentPanel.add(rdbtnSerTres);
		
		seres = new ButtonGroup();
		seres.add(rdbtnSerUno);
		seres.add(rdbtnSerDos);
		seres.add(rdbtnSerTres);
		
		if(ser[0] == null) {
			lblSerUno.setEnabled(false);
			lblSerUno.setVisible(false);
			rdbtnSerUno.setEnabled(false);
			rdbtnSerUno.setVisible(false);
		}
		if(ser[1] == null) {
			lblSerDos.setEnabled(false);
			lblSerDos.setVisible(false);
			rdbtnSerDos.setEnabled(false);
			rdbtnSerDos.setVisible(false);
		}
		if(ser[2] == null) {
			lblSerTres.setEnabled(false);
			lblSerTres.setVisible(false);
			rdbtnSerTres.setEnabled(false);
			rdbtnSerTres.setVisible(false);
		}
		
		if(serSeleccionado == 0)
			rdbtnSerUno.setSelected(true);
		else if(serSeleccionado == 1)
			rdbtnSerDos.setSelected(true);
		else
			rdbtnSerTres.setSelected(true);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnSerUno.isSelected())
					seleccion = ser[0].getRutaImg();
				else if(rdbtnSerDos.isSelected())
					seleccion = ser[1].getRutaImg();
				else
					seleccion = ser[2].getRutaImg();
				dispose();
			}
		});
		btnAceptar.setBounds(507, 164, 97, 25);
		contentPanel.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seleccion = "";
				dispose();
			}
		});
		btnCancelar.setBounds(12, 164, 97, 25);
		contentPanel.add(btnCancelar);
		
	}
	
	private JLabel cargaImagen(String ruta, int ancho, int alto) {
		JLabel imgSer = new JLabel();
		imgSer.setSize(ancho,alto);
		ImageIcon img = new ImageIcon(getClass().getResource(ruta));
		Image img2 = img.getImage();
		Image img3 = img2.getScaledInstance(imgSer.getWidth(), imgSer.getHeight(), Image.SCALE_SMOOTH);
		img = new ImageIcon(img3);
		imgSer.setIcon(img);
		return imgSer;
	}
	
	public String elegirSer() {
		setVisible(true);
		return seleccion;
	}
}
