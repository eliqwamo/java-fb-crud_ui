package com.example.firebasecrud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class ProductAdapter extends ArrayAdapter<Product> {

    private ArrayList<Product> productsList;
    Context context;

    public ProductAdapter(ArrayList<Product> data, Context context) {
        super(context, R.layout.product_item, data);
        this.productsList = data;
        this.context = context;
    }

    private static class ViewHolder{
        TextView pName, pDesc, pPrice;
        ImageView pImage;
    }



    @NonNull
    @Override
    public View getView(
            int position,
            @Nullable View convertView,
            @NonNull ViewGroup parent) {

        //Get the data item for this position
        Product product = getItem(position);

        //Check if an existing view is being reused
        ViewHolder viewHolder;

        final View result;

        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.product_item, parent, false);

            viewHolder.pName = convertView.findViewById(R.id.pName);
            viewHolder.pPrice = convertView.findViewById(R.id.pPrice);
            viewHolder.pDesc = convertView.findViewById(R.id.pDesc);
            viewHolder.pImage = convertView.findViewById(R.id.pImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //Get the data
        viewHolder.pName.setText(product.getProductName());
        viewHolder.pPrice.setText(product.getProductPrice());
        viewHolder.pDesc.setText(product.getProductDescription());
        Picasso.get()
                .load(product.getProductImage())
                .resize(90, 90)
                .centerCrop()
                .into(viewHolder.pImage);

        return convertView;

    }


}
