package com.example.j268sing.shopify;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class listadapter extends ArrayAdapter<list_class> {

    public listadapter(Activity context, ArrayList<list_class> booksList) {
        super(context, 0, booksList);
    }



    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        // Get the {@link AndroidFlavor} object located at this position in the list
        list_class currentlist_class = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID title
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);

        // Get the version name from the current AndroidFlavor object and
        // set this text on the title TextView
        titleTextView.setText(currentlist_class.getmTitle());

        // Find the TextView in the list_item.xml layout with the ID id
        TextView idTextView = (TextView) listItemView.findViewById(R.id.id);

        // Get the version number from the current AndroidFlavor object and
        // set this text on the id TextView
        idTextView.setText("Id = " + currentlist_class.getmId());

        // Find tf /] he ImageView in the list_item.xml layout with the ID image
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);

        // Get the image resource ID from the current AndroidFlavor object and
        // set the image to iconView
        if((currentlist_class.getmImage() != null)|| (currentlist_class.getmImage().length()>0)) {
            Picasso.get().load(currentlist_class.getmImage()).placeholder(R.drawable.ic_launcher_background).into(imageView);
        } else {
            Picasso.get().load(currentlist_class.getmImage()).placeholder(R.drawable.ic_launcher_background).into(imageView);
        }
        return listItemView;
    }


}
