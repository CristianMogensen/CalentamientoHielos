package refrigeracion;

public class Hielo {
    /**
     * Temperatura actual del cubo de hielo (en °C).
     * Los valores válidos son -273 a 0.
     */
    private int temperatura;

    /**
     * Constante en grados °C para la temperatura máxima del hielo.
     */
    final static int TEMPERATURA_MAXIMA = 0;

    /**
     * Constante en grados °C para la temperatura mínima del hielo.
     */
    final static int TEMPERATURA_MINIMA = -273;

    /**
     * Construye un cubo de hielo, con su identificación única y temperatura
     * actual.
     * @param temperaturaNueva int, temperatura actual del hielo en grados °C
     *                         . Debe estar entre -273 y <0.
     */
    public Hielo (int temperaturaNueva) {
        if (esTemperaturaValida(temperaturaNueva)) {
            this.temperatura = temperaturaNueva;
        } else {
            throw new RuntimeException("Error construyendo Hielo: temperatura" +
                    " invalida");
        }
    }

    /**
     * Devuelve la temperatura actual del hielo correspondiente.
     * @return int, temperatura en grados °C, entre -273 y 0.
     */
    public int temperaturaActual () {
        return this.temperatura;
    }

    /**
     * Verifica que una temperatura esté dentro del rango de las temperaturas
     * del hielo.
     * @param temp int, temperatura a verificar.
     * @return true si está entre -273 y 0.
     */
    public boolean esTemperaturaValida (int temp) {
        return (TEMPERATURA_MINIMA < temp) && (temp < TEMPERATURA_MAXIMA);
    }

    /**
     * Calienta el hielo. Le suma una cantidad de grados a su temperatura
     * actual.
     * @param grados int, en grados °C, a sumarle a la temperatura del hielo.
     * @return true si se alcanzó la temperatura máxima (0°C). false caso
     * contrario.
     */
    public boolean calentar (int grados) {
        this.temperatura += grados;

        return (this.temperatura >= 0);
    }

    /**
     * Enfría el hielo. Le resta una cantidad de grados a su temperatura
     * actual.
     * @param grados int, en grados °C, a restarle a la temperatura del hielo.
     * @return true si se alcanzó la temperatura mínima (-273°C). false caso
     * contrario.
     */
    public boolean enfriar (int grados) {
        this.temperatura -= grados;

        boolean minimaSobrepasada = (this.temperatura <= TEMPERATURA_MINIMA);
        if (minimaSobrepasada) {
            this.temperatura = TEMPERATURA_MINIMA;
        }

        return minimaSobrepasada;
    }
}
