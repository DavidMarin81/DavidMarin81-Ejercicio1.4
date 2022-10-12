/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ad.teis.persistencia;

import ad.teis.model.Persona;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mfernandez
 */
public class RandomAccessPersistencia implements IPersistencia {

    private static final int LONG_BYTES_PERSONA = 35;

    @Override
    public void escribirPersona(Persona persona, String ruta) {

        try (
                 RandomAccessFile raf = new RandomAccessFile(ruta, "rw");) {
            raf.writeLong(persona.getId());
            StringBuilder sb = new StringBuilder(persona.getDni());
            sb.setLength(9);
            raf.writeChars(sb.toString());
            //raf.writeUTF(sb.toString());

            raf.writeInt(persona.getEdad());
            raf.writeFloat(persona.getSalario());
            raf.writeBoolean(persona.isBorrado());

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        }
    }

    @Override
    public Persona leerDatos(String ruta) {

        long id = 0;
        String dni = "";
        int edad = 0;
        float salario = 0;
        boolean borrado = false;
        StringBuilder sb = new StringBuilder();
        Persona persona = null;
        try (
                 RandomAccessFile raf = new RandomAccessFile(ruta, "r");) {

            id = raf.readLong();
            for (int i = 0; i <= 8; i++) {
                sb.append(raf.readChar());
            }

            dni = sb.toString();

            edad = raf.readInt();
            salario = raf.readFloat();
            borrado = raf.readBoolean();

            persona = new Persona(id, dni, edad, salario, borrado);

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        }
        return persona;

    }

    public void escribirPersonas(ArrayList<Persona> personas, String ruta) {
        long longitudBytes = 0;
        if (personas != null) {
            try (
                     RandomAccessFile raf = new RandomAccessFile(ruta, "rw");) {

                longitudBytes = raf.length();
                raf.seek(longitudBytes);
                for (Persona persona : personas) {
                    raf.writeLong(persona.getId());
                    StringBuilder sb = new StringBuilder(persona.getDni());
                    sb.setLength(9);
                    raf.writeChars(sb.toString());
                    //raf.writeUTF(sb.toString());

                    raf.writeInt(persona.getEdad());
                    raf.writeFloat(persona.getSalario());
                    raf.writeBoolean(persona.isBorrado());
                }

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                System.out.println("Se ha producido una excepción: " + ex.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Se ha producido una excepción: " + ex.getMessage());
            }
        }

    }

    public ArrayList<Persona> leerTodo(String ruta) {
        long id ;
        String dni ;
        int edad;
        float salario;
        boolean borrado;
        StringBuilder sb = new StringBuilder();
        Persona persona = null;
        ArrayList<Persona> personas = new ArrayList<>();
        try (
                 RandomAccessFile raf = new RandomAccessFile(ruta, "r");) {

            do {
                id = raf.readLong();
                sb = new StringBuilder();
                for (int i = 0; i <= 8; i++) {
                    sb.append(raf.readChar());
                }

                dni = sb.toString();

                edad = raf.readInt();
                salario = raf.readFloat();
                borrado = raf.readBoolean();

                persona = new Persona(id, dni, edad, salario, borrado);
                personas.add(persona);

            } while (raf.getFilePointer() < raf.length());

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        }
        return personas;

    }

    public Persona leerPersona(int posicion, String ruta) {
        long id = 0;
        String dni = "";
        int edad = 0;
        float salario = 0;
        boolean borrado = false;
        StringBuilder sb = new StringBuilder();
        Persona persona = null;

        try (
                 RandomAccessFile raf = new RandomAccessFile(ruta, "r");) {

            raf.seek(converToBytePosition(posicion));
            id = raf.readLong();
            for (int i = 0; i <= 8; i++) {
                sb.append(raf.readChar());
            }

            dni = sb.toString();

            edad = raf.readInt();
            salario = raf.readFloat();
            borrado = raf.readBoolean();

            persona = new Persona(id, dni, edad, salario, borrado);

        } catch (EOFException ex) {
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        }

        return persona;
    }

    private long converToBytePosition(int posicion) {
        if (posicion == 0) {
            return posicion;
        } else {
            return LONG_BYTES_PERSONA * (posicion - 1);
        }
    }

    public Persona add(int posicion, String ruta, Persona persona) {
        try (
                 RandomAccessFile raf = new RandomAccessFile(ruta, "rw");) {

            raf.seek(converToBytePosition(posicion));

            raf.writeLong(persona.getId());
            StringBuilder sb = new StringBuilder(persona.getDni());
            sb.setLength(9);
            raf.writeChars(sb.toString());
            //raf.writeUTF(sb.toString());

            raf.writeInt(persona.getEdad());
            raf.writeFloat(persona.getSalario());
            raf.writeBoolean(persona.isBorrado());

        } catch (FileNotFoundException ex) {
            persona = null;
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        } catch (IOException ex) {
            persona = null;
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        }
        return persona;
    }
    
    public float sumarSalario(int posicion, String ruta, float incremento) {
        float nuevoSalario = 0;
        try (
                 RandomAccessFile raf = new RandomAccessFile(ruta, "rw");) {

            posicion = posicion * 35 - 5;
            
            raf.seek(posicion);
            
            float salarioActual = raf.readFloat();
            System.out.println("Salario Actual " + salarioActual);
            nuevoSalario = salarioActual + incremento;
            raf.seek(posicion);
            raf.writeFloat(nuevoSalario);
            System.out.println("Nuevo salario: " + nuevoSalario);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        }
        return nuevoSalario;
    }
    
    public boolean borrar(int posicion, String ruta, boolean borrado) {
      try (
                 RandomAccessFile raf = new RandomAccessFile(ruta, "rw");) {

            posicion = posicion * 35 - 1;
            
            raf.seek(posicion);
            
            raf.writeBoolean(borrado);
            
            System.out.println("Estado actual: " + borrado);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        }
        return borrado;
    }
    
}
