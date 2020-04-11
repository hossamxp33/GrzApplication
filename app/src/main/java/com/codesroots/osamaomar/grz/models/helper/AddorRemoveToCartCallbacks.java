package com.codesroots.osamaomar.grz.models.helper;



public interface AddorRemoveToCartCallbacks {

    public void onAddProduct(int pid,int cid,int sid,String colorname,String sizename,int product_count);
    public void onRemoveProduct(int pid,int position);
    public void onChangeCart();
}
