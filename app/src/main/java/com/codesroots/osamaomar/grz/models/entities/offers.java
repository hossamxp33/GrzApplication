package com.codesroots.osamaomar.grz.models.entities;

import java.util.List;

public class offers {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * percentage : 20
         * product_id : 41
         * end_date : 04/24/2019
         * product : {"id":41,"name":"dwe","name_en":"petadin","description":"xasxas","description_en":"xasxas","productsizes":[{"id":13,"amount":3,"start_price":"100","product_id":41}],"productphotos":[{"id":13,"photo":"http://shopgate.codesroots.com/library/attachment/pd13.jpg","product_id":41}],"total_rating":[{"product_id":41,"stars":2,"count":1}]}
         */

        private int id;
        private String percentage="1";
        private int product_id;
        private String end_date;
        private ProductDetails.product product;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPercentage() {
            if (percentage.matches(""))
                return "1";
            else
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public ProductDetails.product getProduct() {
            return product;
        }

        public void setProduct(ProductDetails.product product) {
            this.product = product;
        }

    }
}
