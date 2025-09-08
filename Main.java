/**
 * Proyecto: Juego de Memoria (Laboratorio 2)
 * Autor: Andrés Castro Morales 25039
 * Fecha de creación: 2025-09-07
 * Descripción: clase con Input/Output (Scanner/println).
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        boolean jugarOtra = true;
        while (jugarOtra) {           // Bucle controlado para permitir reiniciar
            jugarUnaPartida();
            jugarOtra = preguntarReinicio();
        }
        System.out.println("¡Gracias por jugar!");
    }

    /** Ejecuta una partida completa desde la selección del tamaño hasta el resultado final. */
    private static void jugarUnaPartida() {
        Juego juego = new Juego();
        JuegoVista vista = new JuegoVista();
        JuegoControlador ctrl = new JuegoControlador(juego, vista);

        mostrar(vista.renderInicio());

        int tamaño = pedirTamano();
        try {
            ctrl.iniciarJuego(tamaño);
        } catch (JuegoException e) {
            // Error de dominio al construir tablero (p.ej., paridad). Se informa y termina esta partida.
            mostrar(vista.renderError(e.getMessage()));
            return;
        }

        mostrar(vista.renderTablero(juego.getTablero()));

        // Bucle principal: se ejecuta mientras la partida no haya terminado
        while (juego.isPartidaEnCurso()) {
            mostrar(vista.renderTurno(juego.getJugadorActual()));
            Jugador j1 = juego.getJugadores()[0];
            Jugador j2 = juego.getJugadores()[1];
            mostrar(vista.renderPuntajes(j1, j2));

            int[] p1 = pedirCoordenada("Primera ficha - ingresa fila y columna");
            int[] p2 = pedirCoordenada("Segunda ficha - ingresa fila y columna");

            try {
                // Revela temporalmente para que el jugador vea ambas
                ctrl.revelarTemporal(p1[0], p1[1], p2[0], p2[1]);
                mostrar(vista.renderTablero(juego.getTablero()));

                // Resuelve: si acierta, suma punto y mantiene turno; si falla, oculta y cambia turno.
                boolean acierto = ctrl.resolverJugada(p1[0], p1[1], p2[0], p2[1]);
                mostrar(acierto ? vista.renderAcierto() : vista.renderFallo());
                mostrar(vista.renderTablero(juego.getTablero()));
            } catch (JuegoException ex) {
                // Cualquier jugada inválida (repetir celda, fuera de rango, ya emparejada)
                mostrar(vista.renderError(ex.getMessage()));
                // El turno no cambia aquí; el jugador vuelve a intentar en la siguiente iteración.
            }
        }

        // Muestra el resultado final (ganador o empate)
        Jugador ganador = juego.verificarGanador();
        Jugador j1 = juego.getJugadores()[0];
        Jugador j2 = juego.getJugadores()[1];
        mostrar(vista.renderResultadoFinal(ganador, j1, j2));
    }

    /** Imprime una línea en consola (único método de salida). */
    private static void mostrar(String texto) {
        System.out.println(texto);
    }

    /**
     * Pide un tamaño válido (2..20) y exige que N*N sea par (requisito de pares).
     * Maneja InputMismatchException sin abortar el programa.
     * @return tamaño aceptado para construir el tablero.
     */
    private static int pedirTamano() {
        int valor = 0;
        boolean ok = false;
        do {
            System.out.print("Tamaño (2..20, N*N debe ser par): ");
            try {
                valor = SC.nextInt();
                ok = (valor >= 2 && valor <= 20) && ((valor * valor) % 2 == 0);
                if (!ok) System.out.println("Entrada inválida. Intenta de nuevo.");
            } catch (InputMismatchException ex) {
                System.out.println("Debes ingresar un entero.");
                SC.next(); // limpia el token inválido sin cerrar el Scanner global
            }
        } while (!ok);
        return valor;
    }

    /**
     * Solicita coordenadas (fila y columna) en una sola línea.
     * Valida formato; la validación de rango/estado se hace en el modelo/controlador.
     * @param prompt texto a mostrar antes de leer
     * @return arreglo {fila, columna} con enteros leídos
     */
    private static int[] pedirCoordenada(String prompt) {
        int n1 = -1, n2 = -1;
        boolean ok = false;
        do {
            System.out.print(prompt + " (ej: 0 1): ");
            try {
                n1 = SC.nextInt();
                n2 = SC.nextInt();
                ok = true; // rango/estado se valida luego en Tablero/JuegoControlador
            } catch (InputMismatchException ex) {
                System.out.println("Ingresa dos enteros separados por espacio.");
                SC.nextLine(); // limpia toda la línea restante
            }
        } while (!ok);
        return new int[]{n1, n2};
    }

    /**
     * Pregunta si se desea iniciar otra partida (s/n) con validación defensiva.
     * @return true si el usuario responde 's' o 'S'; false si 'n' o 'N'.
     */
    private static boolean preguntarReinicio() {
        System.out.print("¿Deseas jugar otra vez? (s/n): ");
        String r = "";
        // lee el resto de línea si quedó algo en buffer
        if (SC.hasNextLine()) {
            r = SC.nextLine().trim();
            if (r.isEmpty() && SC.hasNextLine()) r = SC.nextLine().trim();
        }
        while (!r.equalsIgnoreCase("s") && !r.equalsIgnoreCase("n")) {
            System.out.print("Responde 's' o 'n': ");
            r = SC.nextLine().trim();
        }
        return r.equalsIgnoreCase("s");
    }
}
