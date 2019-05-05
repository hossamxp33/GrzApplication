package com.codesroots.osamaomar.Grz.models.entities;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private String name, photo, description, price, afteroffer;
    private float rate, offerpercentage;
    private int ratecount, productid, amount, favid, discountpercentage;
    private List<ProductDetails.product.ProductphotosBean> photos = new ArrayList<>();
    private List<ProductDetails.product.ProductsizesBean> sizes = new ArrayList<>();
    private List<ProductDetails.product.ColorBean> colores = new ArrayList<>();

    private boolean freecharge;

    public int getDiscountpercentage() {
        return discountpercentage;
    }

    public void setDiscountpercentage(int discountpercentage) {
        this.discountpercentage = discountpercentage;
    }

    public float getOfferpercentage() {
        return offerpercentage;
    }

    public void setOfferpercentage(float offerpercentage) {
        this.offerpercentage = offerpercentage;
    }

    public int getFavid() {
        return favid;
    }

    public void setFavid(int favid) {
        this.favid = favid;
    }

    public boolean isFavoret() {
        return favoret;
    }

    public void setFavoret(boolean favoret) {
        this.favoret = favoret;
    }

    private boolean favoret;
    private String charge_rules;
    private boolean hasoffer = false;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<ProductDetails.product.ColorBean> getColores() {
        return colores;
    }

    public void setColores(List<ProductDetails.product.ColorBean> colores) {
        this.colores = colores;
    }

    public List<ProductDetails.product.ProductsizesBean> getSizes() {
        return sizes;
    }

    public void setSizes(List<ProductDetails.product.ProductsizesBean> sizes) {
        this.sizes = sizes;
    }

    public List<ProductDetails.product.ProductphotosBean> getPhotos() {
        return photos;
    }

    public void setPhotos(List<ProductDetails.product.ProductphotosBean> photos) {
        this.photos = photos;
    }


    public boolean isFreecharge() {
        return freecharge;
    }

    public void setFreecharge(boolean freecharge) {
        this.freecharge = freecharge;
    }

    public String getCharge_rules() {
        return charge_rules;
    }

    public void setCharge_rules(String charge_rules) {
        this.charge_rules = charge_rules;
    }

    public boolean isHasoffer() {
        return hasoffer;
    }

    public void setHasoffer(boolean hasoffer) {
        this.hasoffer = hasoffer;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getPrice() {
        if (price == null)
            return "0";
        else
            return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAfteroffer() {
        return afteroffer;
    }

    public void setAfteroffer(String afteroffer) {
        this.afteroffer = afteroffer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getRatecount() {
        return ratecount;
    }

    public void setRatecount(int ratecount) {
        this.ratecount = ratecount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
