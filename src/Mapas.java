
public class Mapas {
	private int filas;
	private int columnas;
	private int visitaActual;
	private String terrenosNombres[];
	private String terrenosID[];
	private String mapaVisual[][];
	private String mapaVisitas[][];
	
	
	Mapas(int f, int c){
		filas = f;
		columnas = c;
		visitaActual = 1;
		mapaVisual = null;
		terrenosNombres = null;
		terrenosID = null;
		mapaVisitas = null;
	}
	
	public int getFilas() { return filas; }
	public int getColumnas() { return columnas; }
	public String[] getTerrenosID() {return terrenosID; }
	
	public void crearMapas(String terrenos) {
		mapaVisual = new String[filas][columnas];
		mapaVisitas = new String[filas][columnas];
		String []aux = terrenos.split(",");
		for(int i=0,columna=0,fila=0; i<aux.length; i++, columna++) {
			if(columna<columnas) 
				mapaVisual[fila][columna] = aux[i];
			else {
				columna = 0;
				fila++;
				mapaVisual[fila][columna] = aux[i];
			}
			mapaVisitas[fila][columna] = " ";
		}
		
		//Buscamos cuántos terrenos existen y su ID
		terrenosID = new String[225]; //225 porque en el caso extremo de que cada casilla sea un ID distinto se puede tener 15*15 = 225 IDs distintos
		for(int i=0; i<225; i++)
			terrenosID[i] = " ";
		
		for(int f=0; f<filas; f++)
			for(int c=0; c<columnas; c++)
				for(int i=0; i<225; i++) {
					if(terrenosID[i].equals(" ")) {
						terrenosID[i] = mapaVisual[f][c];
						break;
					}else if(terrenosID[i].equals(mapaVisual[f][c]))
						break;
						
				}
	}
}
