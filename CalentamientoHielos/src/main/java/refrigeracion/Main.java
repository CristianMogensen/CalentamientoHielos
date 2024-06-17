package refrigeracion;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        long cronometro = 0;
        long tiempo_inicial, tiempo_final;
        long tiempo_paso = 3000; // 3 segundos

        int velocidad_de_enfriamiento = 2;
        int velocidad_de_calentamiento = 1;

        Refrigerador refrigerador = new Refrigerador(velocidad_de_enfriamiento);
        Ambiente ambiente = new Ambiente(velocidad_de_calentamiento);

        int[] temperaturasHielos = {-10,-10,-10, -10};
        Hielo hielo1 = new Hielo(temperaturasHielos[0]);
        Hielo hielo2 = new Hielo(temperaturasHielos[1]);
        Hielo hielo3 = new Hielo(temperaturasHielos[2]);
        Hielo hielo4 = new Hielo(temperaturasHielos[3]);

        Organizador2 organizador = new Organizador2(temperaturasHielos);

        Simulador simulador = new Simulador(refrigerador, ambiente,
                organizador);
        simulador.agregarHielo(hielo1);
        simulador.agregarHielo(hielo2);
        simulador.agregarHielo(hielo3);
        simulador.agregarHielo(hielo4);

        ArrayList<String> estadoActual;

        while (true) {
            simulador.simular();
            estadoActual = simulador.estadoActual();

            System.out.printf("\n\n");
            for (String linea : estadoActual) {
                System.out.println(linea);
            }

            tiempo_inicial = System.currentTimeMillis();
            while (cronometro < tiempo_paso) {
                tiempo_final = System.currentTimeMillis();
                cronometro = tiempo_final - tiempo_inicial;
            }

            cronometro = 0;
        }
    }
}
