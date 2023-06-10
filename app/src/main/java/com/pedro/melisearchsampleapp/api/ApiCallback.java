package com.pedro.melisearchsampleapp.api;

/**
 * Interface que se deberá implementar para recibir la respuesta de las llamadas a los métodos de la API
 */
public interface ApiCallback {
    /**
     * Se invoca si la llamada al end-point fue exitosa
     */
    public void onResponse();

    /**
     * Se invoca en caso de que se haya producido un error invocando al end-point
     * @param t Error que se produjo
     */
    public void onFailure(Throwable t);
}
