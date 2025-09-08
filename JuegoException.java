/**
 * Fecha de creación: 2025-09-07
 * Descripción: Excepción de dominio para validar tamaño del tablero,
 *              coordenadas inválidas y reglas del juego (jugadas no permitidas).
 */
public class JuegoException extends Exception {
    /**
     * Crea una excepción con mensaje compatible para mostrar al usuario.
     * @param message texto descriptivo de la causa (validaciones de juego).
     */
    public JuegoException(String message) { super(message); }
}
