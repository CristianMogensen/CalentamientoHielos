package refrigeracion;

public class Refrigerador {
    /**
     * Velocidad determinada para el enfriamiento de un hielo en su interior.
     * Es la cantidad de grados (valor positivo) que le resta a la
     * temperatura de un hielo por cada segundo.
     */
    private int velocidadEnfriamiento;

    /**
     * Identificación/índice dentro del arreglo de hielos que identifica al
     * hielo.
     */
    private int hieloAlmacenado;

    /**
     * Tiempo almacenado/refrigerado del hielo adentro del refrigerador.
     */
    private int tiempoAlmacenado;

    public Refrigerador (int velocidadDeEnfriamiento) {
        if (velocidadDeEnfriamiento < 0) {
            throw new RuntimeException("Error al crear Refrigerador: " +
                    "velocidad de calentamiento invalida");
        } else {
            this.velocidadEnfriamiento = velocidadDeEnfriamiento;
        }

        this.hieloAlmacenado = -1;
        this.tiempoAlmacenado = 0;
    }

    /**
     * Devuelve la cantidad de grados a enfríar de un hielo dentro de su
     * interior.
     * La cantidad de grados a enfriar está determinada por: la velocidad de
     * enfriamiento específicada en el refrigerador, más la cantidad de
     * segundos que pasa el hielo dentro del mismo.
     * @return int, en grados.
     */
    public int enfriar() {
        int tiempoAnterior = this.tiempoAlmacenado;
        this.tiempoAlmacenado += 1;
        return this.velocidadEnfriamiento + tiempoAnterior;
    }

    /**
     * Devuelve el índice/identificación del hielo almacenado actual.
     * @return int, índice del hielo actual. -1 quiere decir que no hay hielo
     * adentro del refrigerador.
     */
    public int hielo() {
        return this.hieloAlmacenado;
    }

    /**
     * Cambia el valor del hielo almacenado por el refrigerador a -1. (Quita
     * el hielo actual). Reinicia el tiempo de almacenado/refrigerado del hielo.
     */
    public void quitarHielo() {
        this.hieloAlmacenado = -1;
        this.tiempoAlmacenado = 0;
    }

    /**
     * Pone un hielo en el refrigerador. Cambia el valor del hielo actual
     * almacenado y reinicia el tiempo de refrigeración/almacenado del hielo.
     * @param numeroHielo int, índice/identificación del hielo a poner.
     */
    public void ponerHielo (int numeroHielo) {
        this.hieloAlmacenado = numeroHielo;
        this.tiempoAlmacenado = 0;
    }

    /**
     * Devuelve la cantidad de tiempo que estuvo el hielo refrigerado.
     * @return int
     */
    public int tiempoRefrigerado () {
        return this.tiempoAlmacenado;
    }
}
