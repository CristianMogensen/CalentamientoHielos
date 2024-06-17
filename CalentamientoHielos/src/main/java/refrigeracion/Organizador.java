package refrigeracion;

public class Organizador {
    /**
     * Tiempo actual de refrigeración del hielo que se encuentra dentro del
     * refrigerador.
     */
    private int tiempoRefrigerado;

    /**
     * Hielo que se encuentra actualmente adentro del refrigerador.
     */
    private int hieloRefrigerado;

    /**
     * Tiempo mínimo de refrigeración de cada hielo.
     */
    private int tiempoARefrigerar;

    /**
     * Temperatura de cada hielo, en orden.
     */
    private int[] temperaturas;

    /**
     * Construye un Organizador para que calcule los próximos movimientos en
     * la simulación para que no se derrita ningún hielo.
     * @param temperaturasDeHielos int[], con las temperaturas de cada hielo
     *                             en orden. COMENTARIO: por el momento no se
     *                             usa cada temperatura, así que podría
     *                             reemplazarse por un int en todo el algoritmo
     *                             para que utilice solamente la cantidad de
     *                             hielos que hay.
     */
    public Organizador(int[] temperaturasDeHielos) {
        this.tiempoRefrigerado = 0;
        this.hieloRefrigerado = -1;
        this.tiempoARefrigerar = 0;

        if (temperaturasDeHielos == null) {
            throw new NullPointerException("Error al crear Organizador: " +
                    "temperaturasDeHielos null");
        } else {
            this.temperaturas = temperaturasDeHielos;
        }

        // Ejecuta la verificacion de las temperaturas de los hielos. En caso
        // de que no se pueda asegurar que ningún hielo se derretirá, se
        // arroja excepción.
        if (!this.verificarSimulacion(temperaturasDeHielos)) {
            throw new RuntimeException("Error al crear Organizador: no se " +
                    "pudo garantizar el funcionamiento efectivo del algoritmo");
        }
    }

    /**
     * Verifica que la simulación sea válida. Ó, en otras palabras, que el
     * algoritmo será efectivo para que no se derrita ningún hielo.
     * @param temperaturasDeHielos int[] con las temperaturas de cada hielo
     *                             en orden.
     * @return true si se puede utilizar el algoritmo.
     */
    private boolean verificarSimulacion (int[] temperaturasDeHielos) {
        boolean resultado = true;
        int cantidadDeHielos = temperaturasDeHielos.length;
        int temperaturaMaxima = -1;

        // Se inicializan las variables utilizadas para el calculo.
        int iteraciones = 0;

        // Se calcula la cantidad de iteraciones que se necesitarían en base
        // a la cantidad de hielos presentes en la simulación, para calcular
        // la temperatura máxima que deben tener para que no se derritan con
        // este algoritmo.
        if (cantidadDeHielos == 1) {
            throw new RuntimeException("Simulacion absurda: 1 refrigerador y " +
                    "1 hielo no presentan un problema");
        } else if (cantidadDeHielos == 2) {
            iteraciones = 1;
            temperaturaMaxima = -2;
        } else {
            iteraciones = 2*(cantidadDeHielos - 1) - 3;

            // Se calcula la temperatura máxima que puede tener un hielo para que
            // no se derrita ninguno y el algoritmo sea efectivo.
            temperaturaMaxima -= iteraciones*(3+iteraciones)/2;
        }

        // Se verifica que la temperatura máxima no haya sido sobrepasada.
        for (int i = 0; i < cantidadDeHielos; i++) {
            if (temperaturasDeHielos[i] > temperaturaMaxima) {
                resultado = false;
                break;
            }
        }

        this.tiempoARefrigerar = iteraciones;

        return resultado;
    }

    /**
     * Función auxiliar que le sirve al usuario para calcular la temperatura
     * máxima que debe tener cada hielo para que el algoritmo funcione.
     * @param cantidadDeHielos int, cantidad de hielos en la simulacion.
     * @return int, temperatura máxima que debe tener cada hielo.
     */
    public static int calcularTemperaturaMaxima (int cantidadDeHielos) {
        int temperaturaMaxima = -1;

        // Se inicializan las variables utilizadas para el calculo.
        int iteraciones = 0;

        // Se calcula la cantidad de iteraciones que se necesitarían en base
        // a la cantidad de hielos presentes en la simulación, para calcular
        // la temperatura máxima que deben tener para que no se derritan con
        // este algoritmo.
        if (cantidadDeHielos == 2) {
            iteraciones = 1;
            temperaturaMaxima = -2;
        } else if (cantidadDeHielos > 2) {
            iteraciones = 2*(cantidadDeHielos - 1) - 3;

            // Se calcula la temperatura máxima que puede tener un hielo para que
            // no se derrita ninguno y el algoritmo sea efectivo.
            temperaturaMaxima -= iteraciones*(3+iteraciones)/2;
        }

        return temperaturaMaxima;
    }

    /**
     * Actualiza las temperaturas de los hielos calentados o enfriados.
     * @param temperaturasDeHielos int[]
     */
    public void actualizarTemperaturas (int[] temperaturasDeHielos) {
        if (temperaturasDeHielos == null) {
            throw new NullPointerException("Error al actualizar temperaturas " +
                    "de Organizador: temperaturasDeHielos null");
        } else {
            this.temperaturas = temperaturasDeHielos;
        }
    }

    /**
     * Calcula el próximo movimiento para que no se derritan los hielos.
     * @return int, con el movimiento del hielo a refrigerar. Si es -1 no se
     * debe quitar el hielo del refrigerador.
     */
    public int calcular() {
        return this.algoritmo_estable();
    }

    /**
     * Algoritmo 1 estable. Asegura que cada hielo NO se derrita en las
     * condiciones iniciales específicas. El resultado final es que llegará a
     * las temperaturas iniciales y será un bucle que se repetirá sin fin.
     * @return int, movimiento a realizar.
     */
    private int algoritmo_estable () {
        // Se declara la variable resultado que indicará el próximo
        // movimiento a realizar en la simulación.
        int hielo_a_refrigerar;

        // Si no se asignó un hielo a refrigerar, se asigna el primero.
        if (this.hieloRefrigerado == -1) {
            hielo_a_refrigerar = 0;
            this.hieloRefrigerado = 0;
            this.tiempoRefrigerado = 1;
        } else {
            // Sino, se elige el próximo hielo a refrigerar (en caso de que
            // haya que poner otro hielo en el refrigerador).

            // Se verifica que se haya cumplido el tiempo de refrigeración.
            if (this.tiempoRefrigerado == this.tiempoARefrigerar) {
                // Elige el próximo hielo a refrigerar.
                if (this.hieloRefrigerado == (this.temperaturas.length - 1)) {
                    this.hieloRefrigerado = 0;
                    hielo_a_refrigerar = 0;
                } else {
                    this.hieloRefrigerado += 1;
                    hielo_a_refrigerar = this.hieloRefrigerado;
                }

                // Reinicia el tiempo de refrigerado del hielo.
                this.tiempoRefrigerado = 1;
            } else {
                // Si no se cumplió el tiempo necesario de refrigeración para
                // el hielo actual, se indica que se debe dejar permanecer el
                // hielo en el refrigerador.
                hielo_a_refrigerar = -1;
                this.tiempoRefrigerado += 1;
            }
        }

        return hielo_a_refrigerar;
    }
}
