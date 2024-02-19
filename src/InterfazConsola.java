import motor3R.TresEnRaya;

import java.util.Scanner;

public class InterfazConsola {
    private TresEnRaya tr = new TresEnRaya('-', 'X', 'O');
    private final Scanner sc = new Scanner(System.in); //Lo incluyo como atributo para evitar pasárselo como parámetro a casi todos los métodos

    public static void main(String[] args) throws InterruptedException {
        InterfazConsola ic = new InterfazConsola();
        ic.presentacion();
    }

    public void presentacion() throws InterruptedException {
        System.out.println("TRES EN RAYA");
        System.out.println("\t1. Modo fácil");
        System.out.println("\t2. Modo intermedio");
        System.out.println("\t3. Modo difícil");
        System.out.println("\t0. Salir");
        System.out.print("\nSelecciona la dificultad [1, 2, 3] o salir [0]: ");
        seleccionarModo();
    }

    public void seleccionarModo() throws InterruptedException {
        int opc;
        boolean partidaCompletada = false; //Variable necesaria para evitar que el programa se quede colgado al final
        do {
            opc = sc.nextInt();
            switch (opc) {
                case 1:
                    modoFacil();
                    partidaCompletada = true;
                    break;
                case 2:
                case 3:
                    modoNoDisponible();
                    partidaCompletada = true;
                    break;
                default:
                    System.out.println("\nEsperamos volver a verte pronto!");
                    break;
            }
        } while (opc != 0 && !partidaCompletada);
    }

    public void modoNoDisponible() throws InterruptedException {
        System.out.println("\n=========================================================================");
        System.out.println("Este modo no se encuentra disponible actualmente. Disculpa las molestias!");
        System.out.println("=========================================================================\n");
        System.out.println("Volviendo a menú principal...\n");
        Thread.sleep(3000);
        presentacion();
    }

    public void modoFacil() throws InterruptedException {
        int movimJugador = 0;
        do {
            mostrarTablero();
            movimientoJugador();
            movimJugador++;
            mostrarTablero();
            if (tr.ganaJugador() || tr.empate(movimJugador)) {
                break;
            }
            movimientoMaquina();
            if (tr.ganaMaquina()) {
                mostrarTablero();
                break;
            }
        } while (!tr.finPartida());
        nuevaPartida();
    }

    public void mostrarTablero() {
        System.out.println("\n\tTABLERO");
        for (char[] tablero : tr.getTABLERO()) {
            for (char celda : tablero) {
                System.out.print("\t" + celda);
            }
            System.out.println();
        }
    }

    public void movimientoJugador() {
        System.out.println("\nIndica las coordenadas en la que se situará tu X: ");
        System.out.print("\t1. Fila: ");
        int row = sc.nextInt();
        System.out.print("\t2. Columna: ");
        int col = sc.nextInt();
        tr.turnoJugador(row, col);
    }

    public void movimientoMaquina() throws InterruptedException {
        System.out.println("\nLa máquina está ejecutando su jugada...");
        Thread.sleep(2000);
        tr.turnoMaquina();
    }

    public void nuevaPartida() throws InterruptedException {
        if (tr.ganaJugador()) {
            System.out.println("\nHas ganado!");
        } else if (tr.ganaMaquina()) {
            System.out.println("\nHe ganado... más suerte la próxima vez!");
        } else {
            System.out.println("\nNo hay ganador... hemos empatado!");
        }
        tr = new TresEnRaya('-', 'X', 'O'); //Creo un nuevo tablero antes de iniciar una nueva partida
        System.out.println("\nVolviendo a menú principal...\n");
        Thread.sleep(3000);
        presentacion();
    }
}