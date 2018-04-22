
public class Seres {
	private String rutaImg;
	private double[][] costos;
	
	Seres(int filas, int columnas){
		rutaImg = "";
		costos = new double[filas][columnas];
	}
	
	public String getRutaImg() { return rutaImg; }
	public double[][] getCostos() { return costos; }
	
	public void setRutaImg(String rutaImg) { this.rutaImg = "resources/"+rutaImg+".png"; }
	public void setCostos(double[][] costos) { this.costos = costos; }
}
