
public class Listas {
	private Nodos cabeza;
	private Nodos ultimo;
	
	Listas(){
		cabeza = null;
		ultimo = null;
	}
	
	public void setCabeza(Nodos n) { cabeza = n; }
	public void setUltimo(Nodos n) { ultimo = n; }
	public Nodos getCabeza() { return cabeza; }
	public Nodos getUltimo() { return ultimo; }
	
	public void insertarUltimo(Nodos n) {
		if(getCabeza() == null) {
			setCabeza(n);
			setUltimo(getCabeza());
		}else {
			getUltimo().setSiguiente(n); 
			n.setAnterior(getUltimo());
			n.setSiguiente(null);
			setUltimo(n);
		}
	}
	
	
	public void insertarNodo(boolean repetir, Nodos n) {
		if(!repetir){
			Nodos tmp = getCabeza();
			//Básicamente recorremos toda la lista
			//Si el nodo ya está en la lista, "actualizamos" su información
			while(tmp != null) {
				if(tmp.getNombre().equals(n.getNombre())) {
					if(tmp == getCabeza()) {
						n.setSiguiente(getCabeza().getSiguiente());
						getCabeza().getSiguiente().setAnterior(n);
						setCabeza(n);
						n.setPadre("");
					}else if(tmp == getUltimo()) {
						//No es lo mismo actualizar el último nodo que insertarlo al final
						//por eso no utilizo la función
						getUltimo().getAnterior().setSiguiente(n);
						n.setAnterior(getUltimo().getAnterior());
						setUltimo(n);
					}else {
						tmp.getAnterior().setSiguiente(n);
						tmp.getSiguiente().setAnterior(n);
						n.setAnterior(tmp.getAnterior());
						n.setSiguiente(tmp.getSiguiente());
					}
					break;
				}
				tmp = tmp.getSiguiente();
			}
			//Si no se encontró en la lista, quiere decir que no existe así que lo mandamos al final de la misma
			if(tmp == null) {
				insertarUltimo(n);
			}
		}else //Bastante sencillo, si estamos repitiendo nada más mandamos el nodo al último
			insertarUltimo(n);
	}
}
