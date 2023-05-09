package com.example.firebasecrud;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private String productName;
    private String productPrice;
    private String productDescription;
    private String productImage;

    public Product() {

    }

    public Product(String productName, String productPrice, String productDescription, String productImage) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }




    public Product(Parcel source) {
        productName = source.readString();
        productPrice = source.readString();
        productDescription = source.readString();
        productImage = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productName);
        dest.writeString(productPrice);
        dest.writeString(productDescription);
        dest.writeString(productImage);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
