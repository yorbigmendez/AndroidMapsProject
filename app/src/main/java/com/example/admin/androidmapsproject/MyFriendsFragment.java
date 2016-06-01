package com.example.admin.androidmapsproject;

/**
 * Created by Juan on 5/31/2016.
 */

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyFriendsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyFriendsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFriendsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private List<FriendItem> myFriends = new ArrayList<FriendItem>();

    public MyFriendsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFriendsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFriendsFragment newInstance(String param1, String param2) {
        MyFriendsFragment fragment = new MyFriendsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_friends, container, false);

        populateMyFriends();

        //populate listView
        ArrayAdapter<FriendItem> adapter = new MyListAdapter();
        ListView listView = (ListView)view.findViewById(R.id.myFriendsListView);
        listView.setAdapter(adapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class FriendItem{

        private String name;
        private int iconId;

        public FriendItem(String name, int iconId){
            this.iconId = iconId;
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public int getIconId() {
            return iconId;
        }
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
        this.myFriends.add(new FriendItem("Juan",getRandomIcon()));
        this.myFriends.add(new FriendItem("Marco",getRandomIcon()));
        this.myFriends.add(new FriendItem("Ana",getRandomIcon()));
        this.myFriends.add(new FriendItem("Santiago",getRandomIcon()));
        this.myFriends.add(new FriendItem("Carlos", getRandomIcon()));
        this.myFriends.add(new FriendItem("Martin",getRandomIcon()));
        this.myFriends.add(new FriendItem("Samuel",getRandomIcon()));
        this.myFriends.add(new FriendItem("Ivan",getRandomIcon()));
        this.myFriends.add(new FriendItem("Sofia",getRandomIcon()));
        this.myFriends.add(new FriendItem("Vannesa",getRandomIcon()));
        this.myFriends.add(new FriendItem("Erick",getRandomIcon()));
        this.myFriends.add(new FriendItem("Julio",getRandomIcon()));
    }

    private class MyListAdapter extends ArrayAdapter<FriendItem>{

        public MyListAdapter() {
            super(getActivity(), R.layout.item_view, myFriends);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            //View view = inflater.inflate(R.layout.fragment_my_friends, container);

            View itemView = convertView;
            if(itemView == null){
                itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_view, parent, false);
            }

            FriendItem currentFriend = myFriends.get(position);

            ImageView imageView = (ImageView)itemView.findViewById(R.id.imageView);
            imageView.setImageResource(currentFriend.getIconId());

            TextView textView = (TextView)itemView.findViewById(R.id.itemText);
            textView.setText(currentFriend.getName());

            return itemView;
        }
    }
}
