/**
 * Descripción: Datos de jugador (nombre, puntaje, bandera de turno).
 *              No realiza I/O; el control de turnos lo gestiona Juego.
 */
public class Jugador {

    private final String nombre;
    private int puntos;
    private boolean turnoActivo;

    /** Construye un jugador con nombre y puntaje 0. */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntos = 0;
        this.turnoActivo = false;
    }

    /** Suma 1 al puntaje (se invoca al acertar un par). */
    public void ganarPunto() { puntos++; }

    /** @return true si este jugador tiene el turno activo. */
    public boolean esTurno() { return turnoActivo; }

    /** Activa/desactiva la bandera de turno. */
    public void setTurnoActivo(boolean b) { turnoActivo = b; }

    /** @return nombre del jugador (para mensajes/tabla de marcadores). */
    public String getNombre() { return nombre; }

    /** @return puntaje actual. */
    public int getPuntos() { return puntos; }
}
