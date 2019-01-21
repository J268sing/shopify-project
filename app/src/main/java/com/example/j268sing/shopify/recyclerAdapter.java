package com.example.j268sing.shopify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.text.TextUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView count;
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail = (TextView)itemView.findViewById(R.id.item_detail);
            count = (TextView)itemView.findViewById(R.id.count);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //get the image and title of the clicked item from main activity to display in cardview
        String titl = MainActivity.clickedTitle;
        String im = MainActivity.clickedImage;

        //get the product name and total quantatity of product available to display in card cvew
        String productName = collectionDetails.productName.get(position);
        String availableQuantatity = String.valueOf(collectionDetails.availableQuantatity.get(position));

        List<String> str = Arrays.asList(TextUtils.split(productName, " "));
        int k = str.size();
        List<String> str1 = Arrays.asList("");
        str1 = new ArrayList<>(str1);;
        for (int x = 1; x < k;x++ ){
            str1.add(str.get(x));
        }
        productName = TextUtils.join(" ",str1);
        availableQuantatity = "Available: " + availableQuantatity;

        //display the items in card view
        viewHolder.itemTitle.setText(productName);
        viewHolder.itemDetail.setText(titl);
        viewHolder.count.setText(availableQuantatity);
        Picasso.get().load(im).placeholder(R.drawable.ic_launcher_background).into(viewHolder.itemImage);
    }

    @Override
    public int getItemCount() {
        return collectionDetails.productName.size();
    }
}


































