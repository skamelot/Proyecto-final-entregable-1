import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Archivos 
{
	private final static int MAX_MAPA = 15;
	
	private Scanner archivo; 
	private boolean errorCarga; //para saber si hubo o no error durante la carga
	private int filas;
	private int columnas;
	private String error;
	private String terrenos;
	private String linea; //Para leer el archivo línea por línea
	
	
	Archivos(){
		errorCarga = false;
		filas = 0;
		columnas = 0;
		error = "";
		terrenos = "";
		linea = "";
	}
	
	public boolean getErrorCarga() { return errorCarga; }
	public int getFilas() { return filas; }
	public int getColumnas() { return columnas; }
	public String getError() { return error; }
	public String getTerrenos() { return terrenos; }
	
		
	public boolean leerArchivo(String ruta) throws IOException {
		try {
			archivo = new Scanner(new File(ruta));
			if(!archivo.hasNextLine()) {
				errorCarga = true;
				error = "ERROR:   El mapa seleccionado está completamente vacío.\n\n";
				return false;
			}
		} catch (FileNotFoundException e) { 
			errorCarga = true;
			error = "ERROR:   El archivo especificado no existe.\n\n";
			return false; 
		}
		while(archivo.hasNextLine() && archivo!=null)
		{
		   linea = archivo.nextLine();
		   if(!validarInt(linea)) {
			   archivo.close();   
			   return false;
		   }
		   filas++;
		   
		   if(filas>MAX_MAPA) {
			   errorCarga = true;
			   error = "ERROR:   Se excedió el límite de filas para cargar mapa.\n\n";
			   archivo.close();
			   return false;
		   }
		}
		archivo.close();
		return true;
	}
	
	private boolean validarInt(String linea) {
		if(linea.isEmpty()) {
			errorCarga = true;
			error = "ERROR:   Se encontró una línea vacía en el mapa.\n\n";
			return false;
		}
		else if(linea.length()==1 && linea.charAt(0)==',') {
			errorCarga = true;
			error = "ERROR:   La línea no contiene caracteres para cargar mapa.\n\n";
			return false;
		}else if(linea.charAt(0)<'0' || linea.charAt(0)>'9') {
			errorCarga = true;
			error = "ERROR:   Se encontró un valor inválido para cargar el mapa.\n\n";
			return false;
		}
		else {
			String terreno = "";
			int col = 0;
			
			for(int i=0; i<linea.length(); i++) { 
				if(linea.charAt(i)>='0' && linea.charAt(i)<='9')  {
					terreno += String.valueOf(linea.charAt(i));
				}else if(linea.charAt(i)==',') {
					if(!terreno.isEmpty()) {
						terrenos += terreno+",";
						col++;
						terreno = "";
					}else {
						errorCarga = true;
						error = "ERROR:   Hay al menos una zona de mapa vacía.\n"
							  + "                 Por favor verifique la integridad del mapa.\n";
						return false;
					}
					
				}else if(linea.charAt(i)!=',') {
					errorCarga = true;
					error = "ERROR:   Se encontró un valor inválido para cargar el mapa.\n\n";
					return false;
				}
				
				if(col>MAX_MAPA) {
					errorCarga = true;
					error = "ERROR:   Se excedió el limite de columnas para cargar el mapa.\n\n";
					return false;
				}
			}
			
			//Como no hay forma de validar el último caracter dentro del for pregunto si hay algo dentro de la variable terreno
			//Esto para evitar que el último caracter sea ',' porque recordemos que si es coma, se asigna el valor de terreno a terrenos
			//Es decir, en la condición de coma (línea 93) no asignamos valor a terreno, sino que lo restauramos a vacío. Es por eso que
			//Si la línea termina con coma, puede estar vacía
			if(!terreno.isEmpty()) {
				terrenos += terreno+",";
				col++;
			}else {
				errorCarga = true;
				error = "ERROR:   Hay al menos una zona de mapa vacía.\n"
					  + "                 Por favor verifique la integridad del mapa.\n";
				return false;
			}
			
			
			if(columnas == 0)
				columnas = col;
			else if (columnas != col) { //Esto porque no puede haber columnas variables
				errorCarga = true;
				error = "ERROR:   El mapa contiene columnas variables, no se pudo generar un mapa correctamente.\n\n";
				return false;
			}
		}
		return true;
	}
}//Fin de clase