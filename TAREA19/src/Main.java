import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner miScanner = new Scanner(System.in);
        System.out.println("INTRODUCE UN TEXTO PARA CONTAR SUS VOCALES ;) ->");
        String muchoTexto = miScanner.nextLine();
        miScanner.close();
        CuentaVocales.setTexto(muchoTexto);

        // CREAMOS LOS 5 HILOS, 1 POR VOCAL.
        Thread hiloA = new CuentaVocales('a');
        Thread hiloE = new CuentaVocales('e');
        Thread hiloI = new CuentaVocales('i');
        Thread hiloO = new CuentaVocales('o');
        Thread hiloU = new CuentaVocales('u');

        // INICIAMOS LOS HILOS AL MISMO TIEMPO CUMPLIENDO EL PARALELISMO Y CONCURRENCIA REAL.
        hiloA.start();
        hiloE.start();
        hiloI.start();
        hiloO.start();
        hiloU.start();

        // ESPERAMOS A QUE TODOS LOS HILOS TERMINEN CON JOIN.
        try {
            hiloA.join();
            hiloE.join();
            hiloI.join();
            hiloO.join();
            hiloU.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // MOSTRAMOS TOTAL DE VOCALES CONTADAS POR LOS HILOS.
        System.out.println("\n TOTAL DE VOCALES -> " + CuentaVocales.getTotalVocales());
    }
}
