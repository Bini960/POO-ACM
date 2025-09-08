import java.util.Random;

/**
 * Proyecto: Juego de Memoria (Laboratorio 2)
 * Clase: Tablero (Modelo)
 * Autor: Tu Nombre
 * Descripción: Matriz NxN de Ficha. Crea pares, mezcla (Fisher–Yates),
 *              valida coordenadas y resuelve coincidencias. Sin I/O.
 * Nota: Se cambió el mapeo de símbolos a ASCII ("00","01",...) para que
 *       sea legible en cualquier consola (evita "??" de emojis).
 */
public class Tablero {

    private final Ficha[][] fichas;
    private final int tamaño;

    public Tablero(int tamaño) throws JuegoException {
        if (tamaño < 2 || tamaño > 20) {
            throw new JuegoException("El tamaño debe estar entre 2 y 20.");
        }
        int celdas = tamaño * tamaño;
        if ((celdas % 2) != 0) {
            throw new JuegoException("El número de celdas debe ser par para formar pares.");
        }
        this.tamaño = tamaño;
        this.fichas = new Ficha[tamaño][tamaño];
        inicializarTablero();
    }

    public int getTamaño() { return tamaño; }
    public Ficha getFicha(int fila, int col) { return fichas[fila][col]; }

    /** Crea ids en pares (0,0,1,1,...) y baraja. */
    private void inicializarTablero() {
        int n = tamaño * tamaño;

        int[] ids = new int[n];
        for (int i = 0, k = 0; i < n; i += 2, k++) {
            ids[i] = k;
            ids[i + 1] = k;
        }

        mezclar(ids);

        int idx = 0;
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++, idx++) {
                String simb = mapearSimbolo(ids[idx]); // ← ahora ASCII legible
                fichas[i][j] = new Ficha(simb);
            }
        }
    }

    /** Fisher–Yates. */
    private void mezclar(int[] a) {
        Random r = new Random();
        for (int i = a.length - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
        }
    }

    /**
     * Mapea id: símbolo de dos dígitos "00", "01", "02", ...
     * Esto garantiza lectura clara en cualquier consola.
     */
    private String mapearSimbolo(int id) {
        return String.format("%02d", id);
    }

    /** Coordenada jugable: en rango y no revelada/emparejada. */
    public boolean esValida(int fila, int col) {
        if (fila < 0 || fila >= tamaño || col < 0 || col >= tamaño) return false;
        Ficha f = fichas[fila][col];
        return !f.isEmparejada() && !f.isRevelada();
    }

    public void revelarFicha(int fila, int col) { fichas[fila][col].revelar(); }
    public void ocultarFicha(int fila, int col) { fichas[fila][col].ocultar(); }

    /** ¿Mismo símbolo? */
    public boolean coinciden(int f1, int c1, int f2, int c2) {
        return fichas[f1][c1].getSimbolo().equals(fichas[f2][c2].getSimbolo());
    }

    /** Marca ambas como emparejadas (se asume coincidencia). */
    public void emparejar(int f1, int c1, int f2, int c2) {
        fichas[f1][c1].emparejar();
        fichas[f2][c2].emparejar();
    }

    /** ¿Ya no quedan fichas sin emparejar? */
    public boolean tableroCompleto() {
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                if (!fichas[i][j].isEmparejada()) return false;
            }
        }
        return true;
    }
}
