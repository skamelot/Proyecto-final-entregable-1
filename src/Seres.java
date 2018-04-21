
public class Seres {
	private String nombre;
	private String rutaImg;
	private double[][] costos;
	
	Seres(int filas, int columnas){
		nombre = "";
		rutaImg = "";
		costos = new double[filas][columnas];
	}
	
	public String getNombre() { return nombre; }
	public String getRutaImg() { return rutaImg; }
	public double[][] getCostos() { return costos; }
	
	public void setNombre(String nombre) { this.nombre = nombre; }
	public void setRutaImg(String rutaImg) { this.rutaImg = "resources/"+rutaImg+".png"; }
	public void setCostos(double[][] costos) { this.costos = costos; }
}
