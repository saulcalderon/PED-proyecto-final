package clases;

import javax.swing.JOptionPane;
import java.util.LinkedList;
import java.util.Queue;

public class ClasePrincipal {

    public static void main(String[] args) {

        int opcion = 0, nodo_informacion = 0;
        int alumno = 0;
        int vehiculo = 0;
        int disponibilidad [] = {0,0,0};
        int posicion = 0;
        Cola cola = new Cola();

        do {

            try {

                opcion = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "Menú de Opciones\n\n"
                        + "1. Insertar alumno a la cola\n"
                        + "2. Asignar vehiculo al alumno\n"
                        + "3. Desocupar un vehiculo\n"
                        + "4. Mostrar alumnos en la Cola\n"
                        + "5. Salir.\n\n"));

                switch (opcion) {
                    case 1:
                        nodo_informacion = Integer.parseInt(JOptionPane.showInputDialog(null,
                                "Porfavor ingrese el DUI o carnet de minoridad del alumno."));
                        cola.Insertar(nodo_informacion);
                        break;
                    case 2:
                        if (!cola.ColaVacia()) {
                            
                          vehiculo =  Integer.parseInt(JOptionPane.showInputDialog(null, "Disponibilidad de vehículos: \n\n"
                                  + "El vehículo 1  está ocupado por el alumno :  "+disponibilidad [0]+"    \n"
                                  + "El vehículo 2  está ocupado por el alumno :  "+disponibilidad [1]+"     \n"
                                  + "El vehículo 3  está ocupado por el alumno :  "+disponibilidad [2]+"     \n\n\n"
                                  + "Asigne un vehiculo (1 , 2 o 3)  al alumno : "));
                          posicion = vehiculo - 1;
                          alumno = cola.ObtenerElPrimero();
                          disponibilidad [posicion] = alumno;
                            JOptionPane.showMessageDialog(null, "El alumno  "+ alumno +"  fue asignado al vehículo: "+ vehiculo);
                            cola.Extraer();
                            
                        } else {
                            JOptionPane.showMessageDialog(null, "La cola está vacía");
                        }
                        break;
                    case 3:
                        vehiculo =  Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el vehículo  (1,2 o 3) que quedará disponible: \n"));
                        posicion = vehiculo - 1;
                        disponibilidad [posicion] = 0;
                        break;
                    case 4:
                        cola.MostrarContenido();
                        break;
                    case 5:
                        opcion = 5;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no dispobible.");
                        break;
                }

            } catch (NumberFormatException e) {
            }

        } while (opcion != 5);
    }
}
