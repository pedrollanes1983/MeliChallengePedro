package com.pedro.melisearchsampleapp.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Product {
    @SerializedName("id")
    private String id;
    @SerializedName("site_id")
    private String siteId;
    @SerializedName("price")
    private BigDecimal price;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("condition")
    private String condition;
    @SerializedName("currency_id")
    private String currencyId;
    @SerializedName("available_quantity")
    private Integer availableQuantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SerializedName("title")
    private String title;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getPriceFormatted() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMinimumFractionDigits(2);
        BigDecimal priceDec = price.setScale(2, BigDecimal.ROUND_DOWN);
        return numberFormat.format(priceDec);
    }
}
