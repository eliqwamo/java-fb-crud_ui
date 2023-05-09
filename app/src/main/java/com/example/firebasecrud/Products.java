package com.example.firebasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;


public class Products extends AppCompatActivity {


    ListView products_lv;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Intent i = this.getIntent();
        ArrayList<Product> carList = i.getParcelableArrayListExtra("list");

        products_lv = findViewById(R.id.products_lv);
        productAdapter = new ProductAdapter(carList, getApplicationContext());
        products_lv.setAdapter(productAdapter);
    }
}