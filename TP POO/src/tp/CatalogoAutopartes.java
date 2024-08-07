package tp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CatalogoAutopartes implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Autoparte> autopartes;

    public CatalogoAutopartes() {
        this.autopartes = new ArrayList<>();
    }

    public void agregarAutoparte(Autoparte autoparte) {
        autopartes.add(autoparte);
    }

    public Autoparte buscarAutoparte(String id) {
        for (Autoparte autoparte : autopartes) {
            if (autoparte.getId().equals(id)) {
                return autoparte;
            }
        }
        return null;
    }

    public void eliminarAutoparte(String id) {
        autopartes.removeIf(autoparte -> autoparte.getId().equals(id));
    }

    public List<Autoparte> listarAutopartes() {
        return autopartes;
    }

    public static CatalogoAutopartes cargarCatalogo(String archivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (CatalogoAutopartes) ois.readObject();
        }
    }

    public void guardarCatalogo(String archivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(this);
        }
    }

    public void actualizarAutoparte(Autoparte autoparteActualizada) {
        for (int i = 0; i < autopartes.size(); i++) {
            Autoparte autoparte = autopartes.get(i);
            if (autoparte.getId().equals(autoparteActualizada.getId())) {
                autopartes.set(i, autoparteActualizada);
                return;
            }
        }
    }
}
