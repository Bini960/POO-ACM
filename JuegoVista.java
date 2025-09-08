
//Descripción: Genera Strings para representar el estado del juego.

public class JuegoVista {

    public String renderInicio() {
        return """
               --Juego de Memoria--
               - Elige tama\u00f1o del tablero (2-20). El n\u00famero debe ser par
               """;
    }


//Dibuja el tablero. Usamos anchura fija de 2 para alinear

    public String renderTablero(Tablero t) {
        int n = t.getTamaño();
        StringBuilder sb = new StringBuilder();

        // Encabezado columnas
        sb.append("\n   ");
        for (int c = 0; c < n; c++) sb.append(String.format(" %2d", c));
        sb.append("\n");

        // Filas
        for (int f = 0; f < n; f++) {
            sb.append(String.format("%2d ", f));
            for (int c = 0; c < n; c++) {
                Ficha x = t.getFicha(f, c);
                String celda = (x.isRevelada() || x.isEmparejada()) ? x.getSimbolo() : "??";
                sb.append(String.format(" %2s", celda));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String renderTurno(Jugador j) {
        return "\nTurno: " + j.getNombre();
    }

    public String renderPuntajes(Jugador j1, Jugador j2) {
        return "Puntos -> " + j1.getNombre() + ": " + j1.getPuntos() +
               " | " + j2.getNombre() + ": " + j2.getPuntos();
    }

    public String renderResultadoFinal(Jugador ganador, Jugador j1, Jugador j2) {
        if (ganador == null) {
            return "\n¡Empate! " + j1.getPuntos() + " - " + j2.getPuntos();
        }
        return "\nGanó " + ganador.getNombre() + " con " + ganador.getPuntos() + " puntos.";
    }

    public String renderError(String msj) { return "Error: " + msj; }
    public String renderAcierto() { return "¡Par encontrado! Mantienes turno."; }
    public String renderFallo() { return "No coinciden. Cambio de turno."; }
}
