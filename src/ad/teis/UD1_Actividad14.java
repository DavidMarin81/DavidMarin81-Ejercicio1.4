/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ad.teis;

import ad.teis.model.Persona;
import ad.teis.persistencia.DataIOPersistencia;
import ad.teis.persistencia.IPersistencia;
import ad.teis.persistencia.RandomAccessPersistencia;
import java.util.ArrayList;

/**
 *
 * @author mfernandez
 */
public class UD1_Actividad14 {

    private static final String PERSONA_FILE = "personaRandom.dat";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Persona> personas = new ArrayList<>();
        ArrayList<Persona> personasRecuperadas = new ArrayList<>();
        Persona personaA = new Persona(1, "12345678A", 18, 20000.65f, false, "Nombre 1");
        Persona personaB = new Persona(2, "12345678B", 12, 30000.65f, false, "Nombre 2");
        Persona personaC = new Persona(3, "12345678C", 22, 40000.65f, false, "Nombre 3");
        personas.add(personaA);
        personas.add(personaB);
        personas.add(personaC);

        RandomAccessPersistencia random = new RandomAccessPersistencia();

        //random.escribirPersonas(personas, PERSONA_FILE);
        personasRecuperadas = random.leerTodo(PERSONA_FILE);

        int contador = 1;
        for (Persona p : personasRecuperadas) {
            System.out.println("Persona recuperada " + contador + ": " + p);
            contador++;
        }

        int pos = 5;
        Persona personaRecuperada = random.leerPersona(pos, PERSONA_FILE);
        if (personaRecuperada != null) {
            System.out.println("La persona en la posición: " + pos + " es: " + personaRecuperada);
        }
        else{
             System.out.println("La persona en la posición: " + pos + " es null. Puede que no haya objetos de tipo Persona en esa posición.");
        }
        
        //Aumentar el salario a una persona
        random.sumarSalario(pos, PERSONA_FILE, 50.0f);
        random.escribirPersona(personaRecuperada, PERSONA_FILE);
        personaRecuperada = random.leerPersona(pos, PERSONA_FILE);
        if (personaRecuperada != null) {
            System.out.println("La persona en la posición: " + pos + " tiene un incremento del salario: " + personaRecuperada);
        }
        else{
             System.out.println("La persona en la posición: " + pos + " es null. Puede que no haya objetos de tipo Persona en esa posición.");
        }
        
        //Borrar a una persona en una posicion
        random.borrar(5, PERSONA_FILE, true);
        personaRecuperada = random.leerPersona(pos, PERSONA_FILE);
        if (personaRecuperada != null) {
            System.out.println("La persona en la posición: " + pos + " es: " + personaRecuperada);
        }
        else{
             System.out.println("La persona en la posición: " + pos + " es null. Puede que no haya objetos de tipo Persona en esa posición.");
        }
        
        
//        Persona personaZ = new Persona(100, "12345678Z", 23, 10000.5f);
//       
//        pos=25;
//        Persona personaRecuperadaZ = random.add(pos, PERSONA_FILE, personaZ);
//       if (personaRecuperadaZ != null) {
//            System.out.println("La persona añadida en la posición: " + pos + " es: " + personaRecuperadaZ);
//        }
//        else{
//             System.out.println("No se ha añadido la persona en la posición: " + pos);
//        }

    }

}
