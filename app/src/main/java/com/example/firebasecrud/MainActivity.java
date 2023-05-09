package com.example.firebasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //ELEMENTS
    private Button add_product_btn, get_all_products_btn,
            get_product_by_id_btn, get_product_by_para_btn,
            edit_product_btn, delete_product_btn;
    private EditText productName, productPrice, productDescription;
    private ListView products_lv;

    public static ArrayList<Product> products;
    private ProductAdapter productAdapter;

    //FIREBASE-FIRESTORE
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    public static final String KEY_PRODUCT_NAME = "productName";
    public static final String KEY_PRODUCT_PRICE = "productPrice";
    public static final String KEY_PRODUCT_DESCRIPTION = "productDescription";
    public static final String KEY_PRODUCT_IMAGE = "productImage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        add_product_btn = findViewById(R.id.add_product_btn);
        get_all_products_btn = findViewById(R.id.get_all_products_btn);
        get_product_by_id_btn = findViewById(R.id.get_product_by_id_btn);
        get_product_by_para_btn = findViewById(R.id.get_product_by_para_btn);
        edit_product_btn = findViewById(R.id.edit_product_btn);
        delete_product_btn = findViewById(R.id.delete_product_btn);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productDescription = findViewById(R.id.productDescription);

        add_product_btn.setOnClickListener(this);
        get_all_products_btn.setOnClickListener(this);
        get_product_by_id_btn.setOnClickListener(this);
        get_product_by_para_btn.setOnClickListener(this);
        edit_product_btn.setOnClickListener(this);
        delete_product_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_product_btn:
                createNewProduct();
                break;
            case R.id.get_all_products_btn:
                getAllProducts();
                break;
            case R.id.get_product_by_id_btn:
                getProductById("1Pa16uOY5KrX4NHALa4w");
                break;
            case R.id.get_product_by_para_btn:
                getProductByValue("iPad pro 12.9");
                break;
            case R.id.edit_product_btn:
                editProductById("WPcoBu3jSH2dwFMYVl6B");
                break;
            case R.id.delete_product_btn:
                deleteProductById("1Pa16uOY5KrX4NHALa4w");
                break;
        }
    }

    private void editProductById(String pid) {
        DocumentReference document = database.collection("products").document(pid);
        document
                .update(
                        "productPrice", "5900"
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Product updated", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void deleteProductById(String pid) {
        database.collection("products").document(pid)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Product deleted", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void getProductById(String pid) {



        DocumentReference document = database
                .collection("products")
                .document(pid);

        document
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists()){
                                System.out.println(documentSnapshot.getId() + " ---> " + documentSnapshot.getData());
                            } else {
                                Toast.makeText(getApplicationContext(), "No doc for you", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Error: " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void getProductByValue(String para) {
        database.collection("products")
                .whereEqualTo("productName", para)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                System.out.println(document.getId() + " ---> " + document.getData());
                            }
                        }   else {
                            Toast.makeText(getApplicationContext(), "Error: " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void createNewProduct() {
        //GET PARAMETERS
        String pName = productName.getText().toString().trim();
        String pPrice = productPrice.getText().toString().trim();
        String pDescription = productDescription.getText().toString().trim();
        String pImage = "https://www.apple.com/newsroom/images/product/iphone/geo/Apple-iPhone-14-Pro-iPhone-14-Pro-Max-deep-purple-220907-geo_inline.jpg.large.jpg";

        //MAPPING
        Map<String, Object> data = new HashMap<>();
        data.put(KEY_PRODUCT_NAME, pName);
        data.put(KEY_PRODUCT_PRICE, pPrice);
        data.put(KEY_PRODUCT_DESCRIPTION, pDescription);
        data.put(KEY_PRODUCT_IMAGE, pImage);

        database
                .collection("products")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Product created", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void getAllProducts() {


        database
                .collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        products = new ArrayList<Product>();

                        if(task.isSuccessful()){
                            products = new ArrayList<Product>();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Product prod = document.toObject(Product.class);
                                products.add(prod);
                            }
                            //products_lv = findViewById(R.id.products_lv);
                            //productAdapter = new ProductAdapter(products, getApplicationContext());
                            //products_lv.setAdapter(productAdapter);
                            Intent intent = new Intent(getApplicationContext(), Products.class);
                            intent.putParcelableArrayListExtra("list", products);
                            startActivity(intent);
                        }   else {
                            Toast.makeText(getApplicationContext(), "Error: " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}