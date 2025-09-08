/**
 * Descripción: Orquesta la partida: mantiene tablero, jugadores,
 *              alterna turnos y detecta final. No realiza I/O.
 */
public class Juego {

    /** Dos jugadores fijos para la partida. */
    private final Jugador[] jugadores = new Jugador[2];

    /** Tablero de juego (composición: pertenece a Juego). */
    private Tablero tablero;

    /** Referencia al jugador en turno. */
    private Jugador jugadorActual;

    /** true si la partida está activa; false si terminó. */
    private boolean partidaEnCurso;

    /**
     * Inicializa tablero y jugadores, y define el jugador inicial.
     * @param tamaño tamaño del tablero (2..20, celdas pares)
     * @throws JuegoException si el tablero no puede construirse (rango/paridad)
     */
    public void iniciarPartida(int tamaño) throws JuegoException {
        this.tablero = new Tablero(tamaño);
        jugadores[0] = new Jugador("Jugador 1");
        jugadores[1] = new Jugador("Jugador 2");
        jugadores[0].setTurnoActivo(true);
        jugadores[1].setTurnoActivo(false);
        jugadorActual = jugadores[0];
        partidaEnCurso = true;
    }

    /** @return tablero actual. */
    public Tablero getTablero() { return tablero; }

    /** @return arreglo de los dos jugadores. */
    public Jugador[] getJugadores() { return jugadores; }

    /** @return jugador que tiene el turno. */
    public Jugador getJugadorActual() { return jugadorActual; }

    /** @return true si el juego no ha finalizado. */
    public boolean isPartidaEnCurso() { return partidaEnCurso; }

    /** Alterna el turno entre jugadores[0] y jugadores[1]. */
    public void cambiarTurno() {
        if (jugadorActual == jugadores[0]) {
            jugadores[0].setTurnoActivo(false);
            jugadores[1].setTurnoActivo(true);
            jugadorActual = jugadores[1];
        } else {
            jugadores[1].setTurnoActivo(false);
            jugadores[0].setTurnoActivo(true);
            jugadorActual = jugadores[0];
        }
    }

    /**
     * Si el tablero está completo, cierra la partida y retorna ganador (o empate).
     * @return jugador ganador; null si hay empate o si aún no termina.
     */
    public Jugador verificarGanador() {
        if (!tablero.tableroCompleto()) return null;
        partidaEnCurso = false;
        if (jugadores[0].getPuntos() > jugadores[1].getPuntos()) return jugadores[0];
        if (jugadores[1].getPuntos() > jugadores[0].getPuntos()) return jugadores[1];
        return null; // empate
    }
}
