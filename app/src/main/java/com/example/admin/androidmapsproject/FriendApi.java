package com.example.admin.androidmapsproject;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by car_e on 6/7/2016.
 */
public class FriendApi implements InterfaceApi<Friend>{

    @Override
    public boolean Save(Friend friend) {
        return false;
    }

    @Override
    public boolean Update(Friend friend) {
        return false;
    }

    @Override
    public boolean Delete(Friend friend) {
        return false;
    }

    @Override
    public ArrayList<Friend> GetAll() throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public ArrayList<Friend> GetBy(Friend friend) {

        ApiServiceGet getService = new ApiServiceGet();

        getService.execute(ConstantApi.url + "friend/getFriends/" + String.valueOf(friend.getId()), "getMyFriends");

        try {
            return getService.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }


    public class ApiServiceGet extends AsyncTask<String,Void,ArrayList<Friend>> {

        @Override
        protected ArrayList<Friend> doInBackground(String... params) {

            String cadena = params[0];
            URL url;
            ArrayList<Friend> transactions = new ArrayList<>();

            try {
                if (params[1] == "getMyFriends") {
                    url = new URL(cadena);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Accept", "application/json");

                    int responseCode = connection.getResponseCode();

                    StringBuilder result = new StringBuilder();

                    if (responseCode == HttpURLConnection.HTTP_OK) {

                        InputStream in = new BufferedInputStream(connection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }

                        JSONArray jsonArray = new JSONArray(result.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Friend friend = new Friend(jsonObject.getString("fullName"),getRandomIcon());
                            friend.setId(jsonObject.getInt("id"));
                            transactions.add(friend);
                        }
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            return transactions;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
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
}
