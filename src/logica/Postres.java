package logica;

import java.io.Serializable;
import java.util.TreeMap;

public class Postres implements Serializable {

    private static final long serialVersionUID = 1L;

    private TreeMap<String, Postre> postres;

    public Postres() {
        postres = new TreeMap<>();
    }

    public TreeMap<String, Postre> getPostres() {
        return postres;
    }
}