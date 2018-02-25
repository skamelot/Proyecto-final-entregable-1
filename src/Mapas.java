
public class Mapas {
	private int filas;
	private int columnas;
	private String mapaVisual[][];
	private int mapaVisitas[][];
	
	Mapas(int f, int c){
		filas = f;
		columnas = c;
		mapaVisual = null;
		mapaVisitas = null;
	}
	
	public int getFilas() { return filas; }
	public int getColumnas() { return columnas; }
	
	public void crearMapas(String terrenos) {
		mapaVisual = new String[filas][columnas];
		String []aux = terrenos.split(",");
		for(int i=0,columna=0,fila=0; i<aux.length; i++, columna++) {
			if(columna<columnas) 
				mapaVisual[fila][columna] = aux[i];
			else {
				columna = 0;
				fila++;
				mapaVisual[fila][columna] = aux[i];
			}
		}
		
		mapaVisitas = new int[filas][columnas];
		for(int f=0; f<filas; f++)
			for(int c=0; c<columnas; c++)
				mapaVisitas[f][c] = 0;
	}
}
