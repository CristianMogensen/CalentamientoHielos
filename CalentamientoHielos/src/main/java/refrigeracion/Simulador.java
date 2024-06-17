package refrigeracion;

import java.util.ArrayList;

public class Simulador {
    /**
     * ArrayList con todos los hielos en el universo. Cada índice es único e
     * identifíca unívocamente a cada cubo de hielo.
     */
    public ArrayList<Hielo> hielos;

    /**
     * Guarda el refrigerador del universo de la simulación.
     */
    public Refrigerador refrigerador;

    /**
     * Guarda el ambiente del universo de la simulación.
     */
    public Ambiente ambiente;

    /**
     * Guarda el organizador del universo de la simulación, quien será el
     * responsable de calcular el siguiente movimiento para que no se
     * descongelen los hielos.
     */
    public Organizador organizador;

    /**
     * Tiempo actual de la simulación.
     */
    private int tiempo;

    /**
     * Crea un Simulador que se encargará de simular el universo con las
     * reglas correspondientes.
     */
    public Simulador(Refrigerador refrigeradorNuevo, Ambiente ambienteNuevo,
                     Organizador organizadorNuevo) {
        if (refrigeradorNuevo == null) {
            throw new NullPointerException("Error al crear Simulador: " +
                    "refrigeradorNuevo null");
        }
        if (ambienteNuevo == null) {
            throw new NullPointerException("Error al crear Simulador: " +
                    "ambienteNuevo null");
        }
        if (organizadorNuevo == null) {
            throw new NullPointerException("Error al crear Simulador: " +
                    "organizadorNuevo null");
        }

        this.hielos = new ArrayList<>();
        this.tiempo = 0;
        this.refrigerador = refrigeradorNuevo;
        this.ambiente = ambienteNuevo;
        this.organizador = organizadorNuevo;
    }

    /**
     * Agrega un hielo a la lista de hielos.
     * @param hieloNuevo hielo nuevo
     */
    public void agregarHielo (Hielo hieloNuevo) {
        this.hielos.add(hieloNuevo);
    }

    /**
     * Agrega/modifica un refrigerador en la simulación.
     * @param refrigeradorNuevo Refrigerador a agregar.
     */
    public void agregarRefrigerador (Refrigerador refrigeradorNuevo) {
        this.refrigerador = refrigeradorNuevo;
    }

    /**
     * Agrega/modifica un ambiente en la simulación.
     * @param ambienteNuevo Ambiente a agregar.
     */
    public void agregarAmbiente (Ambiente ambienteNuevo) {
        this.ambiente = ambienteNuevo;
    }

    /**
     * Simula 1 segundo en la simulación. Calienta los hielos que están en el
     * ambiente y enfría los hielos que están en el refrigerador.
     */
    public void simular() {
        // Se calcula y ejecuta el siguiente movimiento, que implica la
        // colocación de un hielo en el refrigerador o mantener el estado
        // actual.
        this.siguienteMovimiento();

        // Hielo que está actualmente en el refrigerador.
        int idHieloRefrigerado = this.refrigerador.hielo();

        // Se calientan los hielos que están en el ambiente y se enfrían los
        // que estén en el refrigerador.
        for (int indice = 0; indice < this.hielos.size(); indice++) {
            if (indice == idHieloRefrigerado) {
                this.hielos.get(indice).enfriar(refrigerador.enfriar());
            } else {
                this.hielos.get(indice).calentar(ambiente.calentar());
            }
        }

        this.tiempo += 1;
    }

    /**
     * Devuelve el tiempo actual de la simulación.
     * @return int, tiempo >= 0.
     */
    public int tiempoActual() {
        return this.tiempo;
    }

    /**
     * Realiza el siguiente movimiento calculado por el algoritmo.
     */
    public void siguienteMovimiento() {
        int velocidadDeCalentamiento = this.ambiente.calentar();
        int cantidadHielos = this.hielos.size();
        int[] temperaturasHielos = new int[cantidadHielos];
        int proximoMovimiento = -1; // -1 significa que no se pone ni se
                                    // quita ningún hielo del refrigerador.

        // Se verifica que al menos hayan 2 hielos para tener que elegir
        // algun movimiento.
        if (cantidadHielos < 2) {
            throw new RuntimeException("Error en siguienteMovimiento: hay " +
                    "menos de 2 hielos");
        }

        // Se consultan todas las temperaturas de los hielos.
        for (int indice = 0; indice < cantidadHielos; indice++) {
            temperaturasHielos[indice] =
                    this.hielos.get(indice).temperaturaActual();
        }

        // Se llama al organizador para que calcule el próximo movimiento.
        this.organizador.actualizarTemperaturas(temperaturasHielos); // Por el momento no tiene uso, pero se deja por las dudas.
        proximoMovimiento = this.organizador.calcular();

        // Con el movimiento calculado, si el organizador dice que hay que
        // poner otro hielo, se realiza la operación indicada.
        if (proximoMovimiento != -1) {
            this.refrigerador.ponerHielo(proximoMovimiento);
        }
    }

    /**
     * Muestra el estado actual de la simulación, que indica el tiempo
     * actual de la simulación, qué hielo está  siendo refrigerado, por
     * cuanto tiempo, y qué temperatura tiene cada hielo.
     * @return ArrayList de String's con cada línea a imprimir.
     */
    public ArrayList<String> estadoActual() {
        ArrayList<String> estado = new ArrayList<>();

        estado.add("Estado actual de la simulacion:");
        estado.add("--------------------------------");

        estado.add("Tiempo actual: " + this.tiempo);
        estado.add("Tiempo refrigerado: " + this.refrigerador.tiempoRefrigerado());

        String linea = "";
        int cambioTemperatura;

        for (int i = 0; i < hielos.size(); i++) {
            if (this.refrigerador.hielo() == i) {
                linea = "(R) ";
                cambioTemperatura =
                        -2 - this.refrigerador.tiempoRefrigerado() + 1;
            } else {
                linea = "( ) ";
                cambioTemperatura = 1;
            }
            linea += "Hielo " + i + ": ";
            linea += this.hielos.get(i).temperaturaActual();
            linea += " °C";
            linea += "\t[";
            if (cambioTemperatura > 0) {
                linea += "+";
            }
            linea += cambioTemperatura + " °C]";
            estado.add(linea);
        }

        estado.add("--------------------------------");

        return estado;
    }
}
