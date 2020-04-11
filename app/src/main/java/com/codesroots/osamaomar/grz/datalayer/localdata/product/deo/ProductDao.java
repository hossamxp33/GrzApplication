package com.codesroots.osamaomar.grz.datalayer.localdata.product.deo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.codesroots.osamaomar.grz.datalayer.localdata.product.entities.ProductDB;
import java.util.List;
import io.reactivex.Single;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ProductDao {

    @Insert(onConflict = REPLACE)
    public Long insertProduct(ProductDB product);

    @Update
    public void upateBody(ProductDB product);

    @Query("delete from ProductDB where product_id = :productid ")
    public int deleteProduct(int productid);


    @Query("select * from ProductDB")
    public Single<List<ProductDB>> getproductids();

    @Query("delete from  ProductDB")
    public void deleteall();

    @Query("select count(*) from  ProductDB where product_id = :productid ")
    public int checkifexists(int productid);


    @Query("Update ProductDB set product_count = :newcount where product_id = :productid ")
    public int updateCount(int newcount,int productid);

}
