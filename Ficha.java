/**
 * Descripción: Representa una ficha del tablero con un símbolo y estado.
 *              El estado distingue si está revelada temporalmente o ya emparejada.
 *              No realiza I/O ni conoce sobre control de turnos.
 */
public class Ficha {

    private final String simbolo;

    /** true cuando el jugador la destapa momentáneamente. */
    private boolean revelada;

    /** true cuando la ficha ya pertenece a un par correcto. */
    private boolean emparejada;

    /**
     * Construye una ficha oculta y no emparejada.
     * @param simbolo símbolo que identifica a la ficha (igual en su par).
     */
    public Ficha(String simbolo) {
        this.simbolo = simbolo;
        this.revelada = false;
        this.emparejada = false;
    }

    /** Revela temporalmente la ficha (no la empareja). */
    public void revelar() { this.revelada = true; }

    /**
     * Vuelve a ocultar la ficha si no está emparejada aún.
     * Útil tras un fallo (las dos fichas reveladas no coincidieron).
     */
    public void ocultar() {
        if (!emparejada) this.revelada = false;
    }

    /** Marca la ficha como parte de un par correcto; queda revelada en forma permanente. */
    public void emparejar() {
        this.emparejada = true;
        this.revelada = true;
    }

    /** @return true si está revelada (temporal o por emparejamiento). */
    public boolean isRevelada() { return revelada; }

    /** @return true si ya pertenece a un par correcto. */
    public boolean isEmparejada() { return emparejada; }

    /** @return  símbolo de la ficha (usado para comparar coincidencias). */
    public String getSimbolo() { return simbolo; }
}
