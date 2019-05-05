package com.codesroots.osamaomar.Grz.models.entities;

import java.util.List;

public class ProductDetails {


    private List<product> productdetails;

    public List<product> getProductdetails() {
        return productdetails;
    }

    public void setProductdetails(List<product> productdetails) {
        this.productdetails = productdetails;
    }

    public static class product {
        /**
         * id : 74
         * name : الكولاجين البحري
         * name_en : Marine Collagen
         * description : الكولاجين البحري
         * description_en : Marine Collagen
         * productsizes : [{"id":111,"product_id":74,"start_price":"12","amount":10,"size":"60 س"}]
         * total_rating : [{"product_id":74,"stars":27,"count":7}]
         * productphotos : [{"product_id":74,"id":44,"photo":"http://shopgate.codesroots.com/library/attachment/1555320503900203421.jpg"}]
         * offers : [{"id":84,"percentage":"18","product_id":74}]
         * favourites : []
         */

        private int id;
        private String name;
        private String name_en;
        private String description;
        private String description_en;
        private List<ProductsizesBean> productsizes;
        private List<ColorBean> ColorBean;
        private List<TotalRatingBean> total_rating;
        private List<ProductphotosBean> productphotos;
        private List<OffersBean> offers;
        private List<FavouritesBean> favourites;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName_en() {
            return name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription_en() {
            return description_en;
        }

        public void setDescription_en(String description_en) {
            this.description_en = description_en;
        }

        public List<ProductsizesBean> getProductsizes() {
            return productsizes;
        }

        public void setProductsizes(List<ProductsizesBean> productsizes) {
            this.productsizes = productsizes;
        }

        public List<TotalRatingBean> getTotal_rating() {
            return total_rating;
        }

        public void setTotal_rating(List<TotalRatingBean> total_rating) {
            this.total_rating = total_rating;
        }

        public List<ProductphotosBean> getProductphotos() {
            return productphotos;
        }

        public void setProductphotos(List<ProductphotosBean> productphotos) {
            this.productphotos = productphotos;
        }

        public List<OffersBean> getOffers() {
            return offers;
        }

        public void setOffers(List<OffersBean> offers) {
            this.offers = offers;
        }

        public List<FavouritesBean> getFavourites() {
            return favourites;
        }

        public void setFavourites(List<FavouritesBean> favourites) {
            this.favourites = favourites;
        }

        public static class ProductsizesBean {
            /**
             * id : 111
             * product_id : 74
             * start_price : 12
             * amount : 10
             * size : 60 س
             */

            private int id;
            private int product_id;
            private String start_price;
            private int amount;
            private String size;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public String getStart_price() {
                return start_price;
            }

            public void setStart_price(String start_price) {
                this.start_price = start_price;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }
        }

        public static class TotalRatingBean {
            /**
             * product_id : 74
             * stars : 27
             * count : 7
             */

            private int product_id;
            private int stars;
            private int count;

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public int getStars() {
                return stars;
            }

            public void setStars(int stars) {
                this.stars = stars;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }
        }

        public static class ProductphotosBean {
            /**
             * product_id : 74
             * id : 44
             * photo : http://shopgate.codesroots.com/library/attachment/1555320503900203421.jpg
             */

            private int product_id;
            private int id;
            private String photo;

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }
        }

        public static class ColorBean {
            /**
             * product_id : 31
             * id : 1
             * user_id : 2
             */

            private int product_id;
            private int id;
            private String colorhex;

            public String getColorhex() {
                return colorhex;
            }

            public void setColorhex(String colorhex) {
                this.colorhex = colorhex;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

        }

        public static class FavouritesBean {
            /**
             * product_id : 31
             * id : 1
             * user_id : 2
             */

            private int product_id;
            private int id;
            private int user_id;

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }
        }

        public static class OffersBean {
            /**
             * id : 84
             * percentage : 18
             * product_id : 74
             */

            private int id;
            private String percentage;
            private int product_id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPercentage() {
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
        }
    }
}
