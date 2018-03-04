
public class Mapas {
	private int filas;
	private int columnas;
	private int visitaActual;
	private String[] nombreTerreno;
	private String[] terrenosID;
	private String[] colorTerreno;
	private String[][] mapaColoreado;
	private String[][] mapeoID;
	private String[][] mapaVisitas;
	
	
	Mapas(int f, int c){
		filas = f;
		columnas = c;
		visitaActual = 1;
		mapeoID = null;
		nombreTerreno = null;
		terrenosID = null;
		mapaVisitas = null;
		colorTerreno = null;
		mapaColoreado = null;
	}
	
	public int getFilas() { return filas; }
	public int getColumnas() { return columnas; }
	public int getVisitaActual() { return visitaActual; }
	public String[] getTerrenosID() {return terrenosID; }
	public String[] getNombre() { return nombreTerreno; }
	public String[][] getMapeoID() { return mapeoID; }
	public String[][] getMapaColoreado(){ return mapaColoreado;}
	
	public void setNombre(String[] nombre) { nombreTerreno = nombre; }
	
	public void crearMapas(String terrenos) {
		mapeoID = new String[filas][columnas];
		mapaVisitas = new String[filas][columnas];
		String []aux = terrenos.split(",");
		for(int i=0,columna=0,fila=0; i<aux.length; i++, columna++) {
			if(columna<columnas)
				mapeoID[fila][columna] = aux[i];
			else {
				columna = 0;
				fila++;
				mapeoID[fila][columna] = aux[i];
			}
			mapaVisitas[fila][columna] = aux[i] + "-";
		}
		//Buscamos cu�ntos terrenos existen y su ID
		terrenosID = new String[225]; //225 porque en el caso extremo de que cada casilla sea un ID distinto se puede tener 15*15 = 225 IDs distintos
		for(int i=0; i<225; i++)
			terrenosID[i] = " ";
		
		for(int f=0; f<filas; f++)
			for(int c=0; c<columnas; c++)
				for(int i=0; i<225; i++) {
					if(terrenosID[i].equals(" ")) {
						terrenosID[i] = mapeoID[f][c];
						break;
					}else if(terrenosID[i].equals(mapeoID[f][c]))
						break;
				}
	}
	
	
}
