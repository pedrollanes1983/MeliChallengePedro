package com.pedro.melisearchsampleapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Clase que permite serializar
 */
public class ProductSearchResultList {
    @SerializedName("results")
    private ArrayList<Product> results;

    public ArrayList<Product> getResults() {
        return results;
    }

    public void setResults(ArrayList<Product> results) {
        this.results = results;
    }

    public Product getById(String id) {
        Optional<Product> product = results.stream().filter(searchResult -> searchResult.getId().equals(id)).findAny();
        return product.orElse(null);
    }

    public boolean hasResults() {
        return results != null && !results.isEmpty();
    }
}
