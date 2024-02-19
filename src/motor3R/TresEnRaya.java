package motor3R;

import java.util.Arrays;
import java.util.Random;

public class TresEnRaya {
    private final char[][] TABLERO;
    private final int DIMENSIONES = 3;
    private final char CARACTER_TABLERO;
    private final char CARACTER_JUGADOR;
    private final char CARACTER_MAQUINA;

    /**
     * Método que devuelve el valor del atributo TABLERO
     *
     * @return el valor de tablero
     */
    public char[][] getTABLERO() {
        return TABLERO;
    }

    /**
     * Crea un tablero de 3x3 vacío para iniciar la partida
     * @param caracterTablero -> caracter que representan un espacio vacío del tablero
     * @param caracterJugador -> caracter asignado al jugador
     * @param caracterMaquina -> caracter asignado a la máquina
     */
    public TresEnRaya(char caracterTablero, char caracterJugador, char caracterMaquina) {
        this.TABLERO = new char[this.DIMENSIONES][this.DIMENSIONES];
        this.CARACTER_TABLERO = caracterTablero;
        this.CARACTER_JUGADOR = caracterJugador;
        this.CARACTER_MAQUINA = caracterMaquina;
        crearTableroInicial(this.CARACTER_TABLERO);
    }

    /**
     * Método que se encarga de crear el tablero inicial de cada partida
     */
    public void crearTableroInicial(char caracter) {
        for (char[] celda : this.TABLERO) {
            Arrays.fill(celda, caracter); //Empleo Arrays.fill() por ser más eficiente que un segundo bucle
        }
    }

    /**
     * Método que se encarga de encontrar en la matriz (tablero) el punto indicado por el usuario
     * y llama al método actualizarTablero() para actualizar la matriz tras el turno del jugador
     *
     * @param row -> número de la fila que teclea el usuario
     * @param col -> número de la columna que teclea el usuario
     */
    public void turnoJugador(int row, int col) {
        for (char[] num : this.TABLERO) {
            for (int j = 0; j < num.length; j++) {
                if (this.TABLERO[row][col] == this.CARACTER_TABLERO) {
                    actualizarTablero(row, col, this.CARACTER_JUGADOR);
                }
            }
        }
    }

    /**
     * Método que se encarga de seleccionar aleatoriamente un punto vacío de la matriz, y llama al método
     * actualizarTablero() para actualizar la matriz tras el turno de la máquina
     */
    public void turnoMaquina() {
        Random posicion = new Random();
        int row = posicion.nextInt(3);
        int col = posicion.nextInt(3);

        //Se garantiza que los valores aleatorios no estarán ocupados por un caracter del jugador ni de la máquina
        while (this.TABLERO[row][col] != this.CARACTER_TABLERO) {
            row = posicion.nextInt(3);
            col = posicion.nextInt(3);
        }

        for (char[] num : this.TABLERO) {
            for (int j = 0; j < num.length; j++) {
                actualizarTablero(row, col, this.CARACTER_MAQUINA);
            }
        }
    }

    /**
     * Método que se encarga de actualizar el tablero tras cada turno
     *
     * @param row      -> número de la fila, bien insertada por el usuario cuando se llama a este método
     *                 en la clase InterfazConsola, bien generada aleatoriamente por la máquina
     * @param col      -> número de la columna, bien insertada por el usuario cuando se llama a este método
     *                 en la clase InterfazConsola, bien generada aleatoriamente por la máquina
     * @param caracter -> toma el valor caracterJugador o caracterMaquina, en función de quién haya jugado ese turno
     */
    public void actualizarTablero(int row, int col, char caracter) {
        String cambiarValor = String.valueOf(this.TABLERO[row][col]);
        cambiarValor = cambiarValor.replace(this.CARACTER_TABLERO, caracter);
        char valorActualizado = cambiarValor.charAt(0);
        this.TABLERO[row][col] = valorActualizado;
    }

    /**
     * Método que analiza si la partida ha finalizado
     * @return true si el jugador o la máquina han hecho tres en raya o si hay empate, y false en caso contrario
     */
    public boolean finPartida() {
        int movimJugador = 0;
        movimJugador++;
        return empate(movimJugador) || ganaJugador() || ganaMaquina();
    }

    /**
     * Método que analiza si hay empate cuando el tablero está lleno y no hay tres en raya
     * @param movimJugador representa el número de movimientos que ha hecho el jugador hasta ese momento
     * @return true si hay empate y false en caso contrario
     */
    public boolean empate(int movimJugador) {
        //El enunciado nos dice que se sobreentiende que el jugador teclea bien
        //Teniendo en cuenta eso, y dado que el jugador empieza jugando, como mucho realizará 5 movimientos
        //Si realiza 5 movimientos y ganaJugador() y ganaMaquina() devuelven false, significará que hay empate
        //Poniendo movimJugador == 5 y usando && ganamos eficiencia, dado que no se analizarán los métodos ganaJugador()
        //ni ganaMaquina() hasta que el número de movimientos del jugador sea 5
        return movimJugador == 5 && !ganaJugador() && !ganaMaquina();
    }

    /**
     * Método que analiza si el jugador ha ganado la partida
     * @return devuelve true si el jugador ha hecho tres en raya o false en caso contrario
     */
    public boolean ganaJugador() {
        for (int i = 0; i < this.TABLERO.length; i++) {
            for (int j = 0; j < this.TABLERO.length; j++) {
                if (analizarFila(i, this.CARACTER_JUGADOR) ||
                        analizarColumna(j, this.CARACTER_JUGADOR) ||
                        analizarDiagonalPrincipal(this.CARACTER_JUGADOR) ||
                        analizarDiagonalSecundaria(this.CARACTER_JUGADOR)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Método que analiza si la máquina ha ganado la partida
     * @return devuelve true si la máquina ha hecho tres en raya o false en caso contrario
     */
    public boolean ganaMaquina() {
        for (int i = 0; i < this.TABLERO.length; i++) {
            for (int j = 0; j < this.TABLERO.length; j++) {
                if (analizarFila(i, this.CARACTER_MAQUINA) ||
                        analizarColumna(j, this.CARACTER_MAQUINA) ||
                        analizarDiagonalPrincipal(this.CARACTER_MAQUINA) ||
                        analizarDiagonalSecundaria(this.CARACTER_MAQUINA)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Método que analiza si se ha producido un tres en raya en una fila del tablero
     * @param row -> fila que queremos analizar
     * @param caracter -> el caracter correspondiente al jugador o a la máquina
     * @return true si hay tres en raya en alguna fila del tablero o false en caso contrario
     */
    public boolean analizarFila(int row, char caracter) {
        for (int i = 0; i < this.TABLERO.length; i++) {
            if (this.TABLERO[row][i] != caracter) {
                return false;
            }
        }
        return true;
    }

    /**
     * Método que analiza si se ha producido un tres en raya en una columna del tablero
     * @param col -> columna que queremos analizar
     * @param caracter -> el caracter correspondiente al jugador o a la máquina
     * @return true si hay tres en raya en alguna columna del tablero o false en caso contrario
     */
    public boolean analizarColumna(int col, char caracter) {
        for (char[] celda : this.TABLERO) {
            if (celda[col] != caracter) {
                return false;
            }
        }
        return true;
    }

    /**
     * Método que analiza si se ha producido un tres en raya en la diagonal principal del tablero
     * @param caracter -> el caracter correspondiente al jugador o a la máquina
     * @return true si hay tres en raya en la diagonal principal del tablero o false en caso contrario
     */
    public boolean analizarDiagonalPrincipal(char caracter) {
        for (int i = 0; i < this.TABLERO.length; i++) {
            if (this.TABLERO[i][i] != caracter) {
                return false;
            }
        }
        return true;
    }

    /**
     * Método que analiza si se ha producido un tres en raya en la diagonal secundaria del tablero
     * @param caracter -> el caracter correspondiente al jugador o a la máquina
     * @return true si hay tres en raya en la diagonal secundaria del tablero o false en caso contrario
     */
    public boolean analizarDiagonalSecundaria(char caracter) {
        int j = this.TABLERO.length - 1;
        for (int i = 0; i < this.TABLERO.length; i++) {
            if (this.TABLERO[i][j] != caracter || this.TABLERO[j][i] != caracter) {
                return false;
            }
            j--;
        }
        return true;
    }
}