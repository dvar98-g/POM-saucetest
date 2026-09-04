package com.qa.testing.saucetest.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utilidad para elegir elementos aleatorios de una lista, sin repetir.
 * Compartida entre distintos módulos de test que necesitan datos aleatorios
 * (ej. productos aleatorios en cart y checkout).
 */
public final class RandomUtils {

    private RandomUtils() {
        // Clase utilitaria: no debe instanciarse
    }

    /**
     * Elige una cantidad de elementos distintos al azar de una lista, sin modificarla.
     *
     * @param source lista de origen
     * @param amount cantidad de elementos a elegir
     * @param <T>    tipo de los elementos
     * @return una nueva lista con los elementos elegidos, en orden aleatorio
     */
    public static <T> List<T> pickRandom(List<T> source, int amount) {
        List<T> shuffled = new ArrayList<>(source);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, amount);
    }
}