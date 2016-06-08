package com.example.admin.androidmapsproject;

import android.content.Context;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mendez Soto on 6/7/2016.
 */
public class AdapterFriends extends BaseAdapter {
    private ArrayList<Friend> myFriends;
    private Context context;

    public AdapterFriends(Context context){
        myFriends = new ArrayList<>();
        this.context = context;
        populateMyFriends();
    }

    public void setArrayListFriends(ArrayList<Friend> m){
        myFriends = m;
    }
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return myFriends.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return myFriends.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return myFriends.get(position).getId();
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = null;
        try{
            v= inflater.inflate(R.layout.item_view,parent,false);
            Friend currentFriend = myFriends.get(position);

            ImageView imageView = (ImageView)v.findViewById(R.id.imageView);
            imageView.setImageResource(currentFriend.getIconId());

            TextView textView = (TextView)v.findViewById(R.id.itemText);
            textView.setText(currentFriend.getName());
        }catch(InflateException e){
            e.printStackTrace();
        }

        return  v;
    }


    private int getRandomIcon(){
        Random random = new Random();
        long range = 16 - 1 + 1; //end - start + 1
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long)(range * random.nextDouble());
        int randomNumber =  (int)(fraction + 1); //fraction + start

        switch (randomNumber){
            case 1:
                return R.drawable.boy1;
            case 2:
                return R.drawable.boy2;
            case 3:
                return R.drawable.boy3;
            case 4:
                return R.drawable.boy4;
            case 5:
                return R.drawable.boy5;
            case 6:
                return R.drawable.boy6;
            case 7:
                return R.drawable.boy7;
            case 8:
                return R.drawable.boy8;
            case 9:
                return R.drawable.boy9;
            case 10:
                return R.drawable.boy10;
            case 11:
                return R.drawable.boy11;
            case 12:
                return R.drawable.boy12;
            case 13:
                return R.drawable.boy13;
            case 14:
                return R.drawable.boy14;
            case 15:
                return R.drawable.boy15;
            case 16:
                return R.drawable.boy16;
            default:
                return R.drawable.boy16;
        }
    }

    private void populateMyFriends(){
        this.myFriends.removeAll(this.myFriends);
        this.myFriends.add(new Friend("Juan",getRandomIcon()));
        this.myFriends.add(new Friend("Marco",getRandomIcon()));
        this.myFriends.add(new Friend("Ana",getRandomIcon()));
        this.myFriends.add(new Friend("Santiago",getRandomIcon()));
        this.myFriends.add(new Friend("Carlos", getRandomIcon()));
        this.myFriends.add(new Friend("Martin",getRandomIcon()));
        this.myFriends.add(new Friend("Samuel",getRandomIcon()));
        this.myFriends.add(new Friend("Ivan",getRandomIcon()));
        this.myFriends.add(new Friend("Sofia",getRandomIcon()));
        this.myFriends.add(new Friend("Vannesa",getRandomIcon()));
        this.myFriends.add(new Friend("Erick", getRandomIcon()));
        this.myFriends.add(new Friend("Julio", getRandomIcon()));
    }
}
