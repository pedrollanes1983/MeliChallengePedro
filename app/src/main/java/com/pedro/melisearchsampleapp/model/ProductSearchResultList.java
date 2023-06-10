package com.pedro.melisearchsampleapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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
        return results.stream().filter(searchResult -> searchResult.getId().equals(id)).findAny().get();
    }

    public boolean hasResults() {
        return results != null && !results.isEmpty();
    }
}
