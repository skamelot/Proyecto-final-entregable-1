
public class Nodos {
	private String nombre;
	private String padre;
	private String[] hijos;
	private String visita;
	private int fila;
	private int columna;
	
	private Nodos siguiente;
	private Nodos anterior;
	
	Nodos(int fila, int columna, String nombre, String padre, String[] hijos, String visita){
		this.fila = fila;
		this.columna = columna;
		this.nombre = nombre;
		this.padre = padre;
		this.hijos = hijos;
		this.visita = visita;
		siguiente = null;
		anterior = null;
	}
	
	public int getFila() { return fila; }
	public int getColumna() { return columna; }
	public String getNombre() { return nombre; }
	public String getPadre() { return padre; }
	public String[] getHijos() { return hijos; }
	public String getVisita() { return visita; }
	
	public void setPadre(String p) { padre = p; }
	public void setHijos(String[] h) { hijos = h; }
	
	public void setSiguiente(Nodos s) { siguiente = s; }
	public Nodos getSiguiente() { return siguiente; }
	
	public void setAnterior(Nodos a) { anterior = a; }
	public Nodos getAnterior() { return anterior; }
}
