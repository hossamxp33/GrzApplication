package com.codesroots.osamaomar.grz.datalayer.localdata.product.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ProductDB")
public class ProductDB {


    public ProductDB(int product_id, int productsize_id, int productcolor_id) {
        this.product_id = product_id;
        this.productsize_id = productsize_id;
        this.productcolor_id = productcolor_id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "product_id")
    private int product_id;

    @ColumnInfo(name = "productsize_id")
    private int productsize_id;

    @ColumnInfo(name = "productcolor_id")
    private int productcolor_id;


    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getProductsize_id() {
        return productsize_id;
    }

    public void setProductsize_id(int productsize_id) {
        this.productsize_id = productsize_id;
    }

    public int getProductcolor_id() {
        return productcolor_id;
    }

    public void setProductcolor_id(int productcolor_id) {
        this.productcolor_id = productcolor_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
