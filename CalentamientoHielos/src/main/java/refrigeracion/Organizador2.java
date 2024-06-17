package refrigeracion;

public class Organizador2 extends Organizador {
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
     * Variable auxiliar para saber con qué hielo se debe empezar el nuevo
     * ciclo, aprovechando que ya lleva tiempo en el refrigerador.
     */
    private int hieloRepetir;

    /**
     * Construye un Organizador  que utiliza el algoritmo 2 para que calcule
     * los próximos movimientos en la simulación para que no se derrita
     * ningún hielo.
     * @param temperaturasDeHielos int[], con las temperaturas de cada hielo
     *                             en orden. COMENTARIO: por el momento no se
     *                             usa cada temperatura, así que podría
     *                             reemplazarse por un int en todo el algoritmo
     *                             para que utilice solamente la cantidad de
     *                             hielos que hay.
     */
    public Organizador2 (int[] temperaturasDeHielos) {
        super(temperaturasDeHielos);
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
            throw new RuntimeException("Algoritmo no valido para 2 hielos, " +
                    "deben haber minimo 3");
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
        this.hieloRepetir = this.temperaturas.length - 1; // se asigna el último

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
        return this.algoritmo_2();
    }

    private int algoritmo_2 () {
        // Se declara la variable resultado que indicará el próximo
        // movimiento a realizar en la simulación.
        int hielo_a_refrigerar;

        // Si no se asignó un hielo a refrigerar, se asigna el primero.
        if (this.hieloRefrigerado == -1) {
            hielo_a_refrigerar = 0;
            this.hieloRefrigerado = 0;
            this.tiempoRefrigerado = 1;
            this.hieloRepetir = 1;
        } else {
            if (this.tiempoRefrigerado == this.tiempoARefrigerar) {
                if (this.hieloRepetir == this.temperaturas.length) {
                    this.hieloRepetir = 1;
                    hielo_a_refrigerar = -1;
                } else {
                    if (this.hieloRefrigerado == (this.temperaturas.length - 1)) {
                        this.hieloRefrigerado = 0;
                    } else {
                        this.hieloRefrigerado += 1;
                    }
                    hielo_a_refrigerar = this.hieloRefrigerado;
                    this.hieloRepetir += 1;
                }
                this.tiempoRefrigerado = 1;
            } else {
                this.tiempoRefrigerado += 1;
                hielo_a_refrigerar = -1;
            }
        }

        return hielo_a_refrigerar;
    }
}
