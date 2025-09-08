//Descripción: Coordina entradas validadas desde Main con operaciones del modelo.

public class JuegoControlador {

    private final Juego juego;
    private final JuegoVista vista;

    /** Inyecta dependencias del modelo (Juego) y la vista (generadora de textos). */
    public JuegoControlador(Juego juego, JuegoVista vista) {
        this.juego = juego;
        this.vista = vista;
    }

    /**
     * Arranca una partida creando tablero y jugadores.
     * @param tamaño lado del tablero (2..20, con N*N par)
     * @throws JuegoException si el tablero no puede inicializarse
     */
    public void iniciarJuego(int tamaño) throws JuegoException {
        juego.iniciarPartida(tamaño);
    }

    /**
     * Prevalida y revela temporalmente dos fichas. Deja el tablero listo para
     * que la vista las muestre y, posteriormente, se llame a resolverJugada.
     * @throws JuegoException si coordenadas son iguales, fuera de rango o no jugables.
     */
    public void revelarTemporal(int f1, int c1, int f2, int c2) throws JuegoException {
        Tablero t = juego.getTablero();
        if (f1 == f2 && c1 == c2) throw new JuegoException("Debes elegir dos celdas distintas.");
        if (!t.esValida(f1, c1)) throw new JuegoException("Primera celda inválida o no jugable.");
        if (!t.esValida(f2, c2)) throw new JuegoException("Segunda celda inválida o no jugable.");
        t.revelarFicha(f1, c1);
        t.revelarFicha(f2, c2);
    }

    /**
     * Resuelve la jugada ya revelada: si coinciden, empareja y suma punto;
     * si no, oculta ambas y alterna turno. También evalúa final de partida.
     * @return true si hubo acierto (el jugador mantiene turno), false si hubo fallo.
     */
    public boolean resolverJugada(int f1, int c1, int f2, int c2) {
        Tablero t = juego.getTablero();
        boolean acierto = t.coinciden(f1, c1, f2, c2);
        if (acierto) {
            t.emparejar(f1, c1, f2, c2);
            juego.getJugadorActual().ganarPunto();
        } else {
            t.ocultarFicha(f1, c1);
            t.ocultarFicha(f2, c2);
            juego.cambiarTurno();
        }
        // Actualiza estado final (si corresponde); null implica que aún sigue o hay empate.
        juego.verificarGanador();
        return acierto;
    }

    /** @return acceso de solo lectura a Juego (para que Main consulte estado). */
    public Juego getJuego() { return juego; }

    /** @return referencia a la vista (Main imprime sus strings). */
    public JuegoVista getVista() { return vista; }
}
