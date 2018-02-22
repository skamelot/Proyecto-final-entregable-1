import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Archivos 
{
	private Scanner archivo; 
	private int filas;
	private int columnas;
	
	private String linea; //Para leer el archivo línea por línea
	private String mapaMatriz[][]; //Almacenamos todos los números de la matriz
	
	Archivos(){
		linea = "";
		filas = 0;
		columnas = 0;
		mapaMatriz = null;
	}
	
	public void leerArchivo(String ruta) throws IOException {
		try {
			archivo = new Scanner(new File(ruta));
		} catch (FileNotFoundException e) { return; }
		while(archivo.hasNextLine() && archivo!=null)
		{
		   linea += archivo.nextLine()+"\n";
		   filas++;
		   //Se manipula los datos de "línea" para agregarlos a mapaMatriz[][]
		}
		archivo.close();
		System.out.println(linea);
	}
}//Fin de clase