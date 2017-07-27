package com.congress.util;

import android.os.AsyncTask;

import com.congress.models.LegisModel;
import com.congress.response.LegisResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Parul on 17-Nov-16.
 */

public class LegisUtil extends AsyncTask<Object, Object, List<LegisModel>> {

    private LegisListener legisListener;

    private String legisUrl = "http://andsearchm25.us-west-2.elasticbeanstalk.com/index.php?get=all&type=legislator";
    private static final String Tag = LegisUtil.class.getSimpleName();
    public LegisUtil(LegisListener listener) {
        legisListener = listener;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    public interface LegisListener {
        void onLoadedLegis();
        void onErrorLegis();
    }

    protected List<LegisModel> doInBackground(Object... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        List<LegisModel> data;
        Type arrayListType = new TypeToken<ArrayList<LegisModel>>(){}.getType();
        try {
            URL url = new URL(legisUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            Gson gson = new GsonBuilder().create();
            data = gson.fromJson(reader, arrayListType);
            LegisResponse response = LegisResponse.getInstance();
            for (int i=0;i<data.size();i++){
                LegisModel model = data.get(i);
                response.addLegisByState(model);
                if(model.getChamber().equalsIgnoreCase("senate")){
                    response.addLegisBySenate(model);
                }
                else if(model.getChamber().equalsIgnoreCase("house")){
                    response.addLegisByHouse(model);
                }
            }
            response.sortLegisByState();
            response.sortLegisByHouse();
            response.sortLegisBySenate();
            return response.getLegisByState();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<LegisModel> result) {
        if(result.size() > 0){
            legisListener.onLoadedLegis();
        }
        else{
            legisListener.onErrorLegis();
        }
    }
}
