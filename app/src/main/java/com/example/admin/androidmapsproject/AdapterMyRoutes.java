package com.example.admin.androidmapsproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Mendez Soto on 5/31/2016.
 */
public class AdapterMyRoutes extends BaseAdapter{
    Context context;
    LayoutInflater inflater;
    protected ArrayList<Route> items;

    public AdapterMyRoutes (Context context, ArrayList<Route> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }

    @Override
    public boolean isEnabled(int arg0)
    {
        return true;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Route> category) {
        for (int i = 0; i < items.size(); i++) {
            items.add(items.get(i));
        }
    }


    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView txtRouteName;
        ImageView routeImg;
        ImageButton btnEdit, btnDelete;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.route_item, parent, false);

        // Locate the TextViews in listview_item.xml
        txtRouteName = (TextView) itemView.findViewById(R.id.txtRouteName);
        routeImg = (ImageView)itemView.findViewById(R.id.imgRoute);

        txtRouteName.setText(items.get(position).getRouteName());
        routeImg.setImageResource(R.drawable.icon_route);

        return itemView;
    }
}
