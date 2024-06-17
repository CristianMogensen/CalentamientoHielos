package refrigeracion;

public class Ambiente {
    /**
     * Velocidad determinada para el calentamiento de un hielo en su interior.
     * Es la cantidad de grados (valor positivo) que le resta a la
     * temperatura de un hielo por cada segundo.
     */
    private int velocidadCalentamiento;

    public Ambiente (int velocidadDeCalentamiento) {
        if (velocidadDeCalentamiento < 0) {
            throw new RuntimeException("Error al crear Ambiente: velocidad de" +
                    " calentamiento invalida");
        } else {
            this.velocidadCalentamiento = velocidadDeCalentamiento;
        }
    }

    /**
     * Devuelve la cantidad de grados a calentar de un hielo que está en el
     * ambiente (fuera del refrigerador).
     * @return int, en grados.
     */
    public int calentar() {
        return this.velocidadCalentamiento;
    }
}
