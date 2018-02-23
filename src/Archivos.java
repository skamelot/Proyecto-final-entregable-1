import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import com.sun.xml.internal.ws.util.StringUtils;

public class Archivos 
{
	private Scanner archivo; 
	private int filas;
	private int columnas;
	private int terrenos[];
	private String linea; //Para leer el archivo línea por línea
	private String mapaMatriz[][]; //Almacenamos todos los números de la matriz
	
	Archivos(){
		linea = "";
		filas = 0;
		columnas = 0;
		mapaMatriz = null;
	}
	
	public boolean leerArchivo(String ruta) throws IOException {
		try {
			archivo = new Scanner(new File(ruta));
		} catch (FileNotFoundException e) { return false; }
		while(archivo.hasNextLine() && archivo!=null)
		{
		   linea = archivo.nextLine();
		   if(validarInt(linea))
			   filas++;
		   else
			   return false;
		}
		archivo.close();
		return true;
	}
	
	private boolean validarInt(String linea) {
		if(linea.isEmpty())
			return false;
		else {
			for(int i=0; i<linea.length(); i++) {
				if(i == 0 && linea.charAt(i) == ',') 
					if(linea.length() == 1) 
						return false;
				
				else if(linea.charAt(i)!=',' && linea.charAt(i)>='0' && linea.charAt(i)<='9')  {
					String numTerreno = String.valueOf(linea.charAt(i));
					int numeroTerreno = Integer.valueOf(numTerreno);
				}
				
				else if(linea.charAt(i)!=',')
					return false;
			}
		}
		return true;
	}
}//Fin de clase