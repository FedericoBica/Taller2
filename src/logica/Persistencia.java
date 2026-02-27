package logica;

import java.io.*;
import logica.excepciones.PersistenciaException;

public class Persistencia {

    public void respaldar(String nomArch, CapaLogica capa)
            throws PersistenciaException {

        try {
            FileOutputStream f = new FileOutputStream(nomArch);
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(capa);

            o.close();
            f.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new PersistenciaException("Error al respaldar datos");
        }
    }

    public CapaLogica recuperar(String nomArch)
            throws PersistenciaException {

        try {
            FileInputStream f = new FileInputStream(nomArch);
            ObjectInputStream o = new ObjectInputStream(f);

            CapaLogica capa = (CapaLogica) o.readObject();

            o.close();
            f.close();

            return capa;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new PersistenciaException("Error al recuperar datos");
        }
    }
}