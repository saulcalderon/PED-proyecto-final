package entidades;

import javax.swing.JOptionPane;

public class Cola {

    private Nodo inicioCola, finalCola;
    String Cola = "";

    public Cola() {
        inicioCola = null;
        finalCola = null;
    }

    //Método para saber si la cola esta vacia
    public boolean ColaVacia() {
        if (inicioCola == null) {
            return true;
        } else {
            return false;
        }
    }

    //Método para insertar a la cola
    public void Insertar(int auto_id, int alumno_id) {
        Nodo nuevo_nodo = new Nodo();
        nuevo_nodo.auto_id = auto_id;
        nuevo_nodo.alumno_id = alumno_id;

        nuevo_nodo.siguiente = null;

        if (ColaVacia()) {
            inicioCola = nuevo_nodo;
            finalCola = nuevo_nodo;
        } else {
            finalCola.siguiente = nuevo_nodo;
            finalCola = nuevo_nodo;
        }
    }

    //Método para extraer de la cola
    public String Extraer() {
        if (!ColaVacia()) {
            int auto_id = inicioCola.auto_id;
            int alumno_id = inicioCola.alumno_id;

            if (inicioCola == finalCola) {
                inicioCola = null;
                finalCola = null;
            } else {
                inicioCola = inicioCola.siguiente;
            }
            return "Se retiro de la cola el auto con ID: " + auto_id + ", y el alumno con ID: " + alumno_id;
        } else {
            return "";
        }
    }

    //Método para mostrar el contenido de la cola
    public void MostrarContenido() {
        Nodo recorrido = inicioCola;
        String ColaInvertida = "";

        while (recorrido != null) {
            Cola += recorrido.auto_id + " ";
            recorrido = recorrido.siguiente;
        }

        String cadena[] = Cola.split(" ");

        for (int i = cadena.length - 1; i >= 0; i--) {
            ColaInvertida += "\n " + cadena[i];
        }

        JOptionPane.showMessageDialog(null, ColaInvertida);
        Cola = "";
    }

    //Método para obtener al primero de la cola
    public int ObtenerElPrimero() {
        if (!ColaVacia()) {
            int primero = inicioCola.auto_id;

            return primero;
        } else {
            return Integer.MAX_VALUE;
        }
    }

}
