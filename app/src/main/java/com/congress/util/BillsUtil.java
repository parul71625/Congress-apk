package com.congress.util;

import android.os.AsyncTask;

import com.congress.models.BillsModel;
import com.congress.response.BillsResponse;
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

public class BillsUtil extends AsyncTask<Object, Object, List<BillsModel>> {

    private BillsListener billsListener;

    private String billsUrl = "http://andsearchm25.us-west-2.elasticbeanstalk.com/index.php?get=all&type=bills";
    private static final String Tag = BillsUtil.class.getSimpleName();
    public BillsUtil(BillsListener listener) {
        billsListener = listener;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    public interface BillsListener {
        void onLoadedBills();
        void onErrorBills();
    }

    protected List<BillsModel> doInBackground(Object... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        List<BillsModel> data;
        Type arrayListType = new TypeToken<ArrayList<BillsModel>>(){}.getType();
        try {
            URL url = new URL(billsUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            Gson gson = new GsonBuilder().create();
            data = gson.fromJson(reader, arrayListType);

            BillsResponse response = BillsResponse.getInstance();
            for (int i=0;i<data.size();i++){
                BillsModel model = data.get(i);
                if(model.getStatus().equalsIgnoreCase("Active")){
                    response.addBillsActive(model);
                }
                else if(model.getStatus().equalsIgnoreCase("New")){
                    response.addBillsNew(model);
                }
            }

            response.sortBillsActive();
            response.sortBillsNew();

            return response.getBillsActive();
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
    protected void onPostExecute(List<BillsModel> result) {
        if(result.size() > 0){
            billsListener.onLoadedBills();
        }
        else{
            billsListener.onErrorBills();
        }
    }
}
