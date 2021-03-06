import java.awt.Color;

public class Mapas {
	private int filas;
	private int columnas;
	private int visitaActual;
	private String[] nombreTerreno;
	private String[] terrenosID;
	private Color[] colorTerreno;
	private String[][] mapeoID;
	private String[][] mapaRecorrido;
	private boolean[][] mapaVisible;
	
	
	Mapas(int f, int c){
		filas = f;
		columnas = c;
		visitaActual = 1;
		mapeoID = null;
		nombreTerreno = null;
		terrenosID = null;
		colorTerreno = null;
		mapaRecorrido = null;
		mapaVisible  = null;
	}
	
	public int getFilas() { return filas; }
	public int getColumnas() { return columnas; }
	public int getVisitaActual() { return visitaActual; }
	public String[] getTerrenosID() {return terrenosID; }
	public String[] getNombre() { return nombreTerreno; }
	public Color[] getColorTerreno() { return colorTerreno; }
	public String[][] getMapeoID() { return mapeoID; }
	public String[][] getMapaRecorrido() { return mapaRecorrido; }
	public boolean[][] getMapaVisible() { return mapaVisible; }
	
	public void setNombre(String[] nombre) { nombreTerreno = nombre; }
	public void setColor(Color color, int pos) { colorTerreno[pos] = color; }
	public void setInicio(int fila, int columna) { mapaRecorrido[fila][columna] = "I, 1"; }
	public void setFinal(int fila, int columna) { mapaRecorrido[fila][columna] = "F"; }
	public void setMapaVisible(int fila, int columna, int posF, int posC) {
		mapaVisible[posF][posC] = true;
		if(posF > 0)//Arriba de la casilla actual
			mapaVisible[posF-1][posC] = true;
		if(posF < fila-1)//Abajo de la casilla actual
			mapaVisible[posF+1][posC] = true;
		if(posC >= 1)//Izquierda de la casilla actual
			mapaVisible[posF][posC-1] = true;
		if(posC < columna-1)//Derecha de la casilla actual
			mapaVisible[posF][posC+1] = true;
	}
	
	public void enmascarar() {
		for(int f=0; f<filas; f++) 
			for(int c=0; c<columnas; c++) 
				mapaVisible[f][c] = false;
	}
	
	public void desenmascarar() {
		for(int f=0; f<filas; f++) 
			for(int c=0; c<columnas; c++) 
				mapaVisible[f][c] = true;
	}
	
	public void actualizaRecorrido(int fila, int columna) { 
		visitaActual++;
		if(mapaRecorrido[fila][columna].isEmpty())
			mapaRecorrido[fila][columna] = String.valueOf(visitaActual);
		else
			mapaRecorrido[fila][columna] += ", "+String.valueOf(visitaActual);
	}
	
	public void crearMapas(String terrenos) {
		mapeoID = new String[filas][columnas];
		mapaRecorrido = new String[filas][columnas];
		mapaVisible = new boolean[filas][columnas];
		String []aux = terrenos.split(",");
		
		for(int i=0; i<filas; i++)
			for(int j=0; j<columnas; j++)
				mapaRecorrido[i][j]="";
		
		for(int i=0,columna=0,fila=0; i<aux.length; i++, columna++) {
			if(columna<columnas) 
				mapeoID[fila][columna] = aux[i];
			else {
				columna = 0;
				fila++;
				mapeoID[fila][columna] = aux[i];
			}
		}
		
		//Buscamos cu�ntos terrenos existen y su ID
		terrenosID = new String[225]; //225 porque en el caso extremo de que cada casilla sea un ID distinto se puede tener 15*15 = 225 IDs distintos
		for(int i=0; i<225; i++)
			terrenosID[i] = " ";
		
		int cantTerrenos = 0;
		for(int f=0; f<filas; f++)
			for(int c=0; c<columnas; c++)
				for(int i=0; i<225; i++) {
					if(terrenosID[i].equals(" ")) {
						terrenosID[i] = mapeoID[f][c];
						cantTerrenos++;
						break;
					}else if(terrenosID[i].equals(mapeoID[f][c]))
						break;
				}
		String[] tmp = new String[cantTerrenos];
		for(int i=0; i<cantTerrenos; i++)
			tmp[i] = terrenosID[i];
		terrenosID = tmp;
		
		colorTerreno = new Color[cantTerrenos];
		for(int i=0; i<cantTerrenos; i++)
			colorTerreno[i] = Color.WHITE;
	}
	
	public void reinicia(int iniF, int iniC, int finF, int finC) {
		visitaActual = 1;
		for(int f=0; f<filas; f++) {
			for(int c=0; c<columnas; c++) {
				if(f == iniF && c == iniC)
					mapaRecorrido[f][c] = "I, 1";
				else if(f == finF && c == finC)
					mapaRecorrido[f][c] = "F";
				else
					mapaRecorrido[f][c] = "";
			}
		}
		enmascarar();
		visitaActual = 1;
	}
}
