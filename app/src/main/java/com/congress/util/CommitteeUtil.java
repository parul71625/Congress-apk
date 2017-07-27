package com.congress.util;

import android.os.AsyncTask;

import com.congress.models.CommitteeModel;
import com.congress.response.CommitteeResponse;
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

public class CommitteeUtil extends AsyncTask<Object, Object, List<CommitteeModel>> {

    private CommitteeListener committeeListener;

    private String committeesUrl = "http://andsearchm25.us-west-2.elasticbeanstalk.com/index.php?get=all&type=committee";
    private static final String Tag = CommitteeUtil.class.getSimpleName();
    public CommitteeUtil(CommitteeListener listener) {
        committeeListener = listener;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    public interface CommitteeListener {
        void onLoadedCommittee();
        void onErrorCommittee();
    }

    protected List<CommitteeModel> doInBackground(Object... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        List<CommitteeModel> data;
        Type arrayListType = new TypeToken<ArrayList<CommitteeModel>>(){}.getType();
        try {
            URL url = new URL(committeesUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            Gson gson = new GsonBuilder().create();
            data = gson.fromJson(reader, arrayListType);

            CommitteeResponse response = CommitteeResponse.getInstance();
            for (int i=0;i<data.size();i++){
                CommitteeModel model = data.get(i);
                if(model.getChamber().equalsIgnoreCase("senate")){
                    response.addCommitteeSenate(model);
                }
                else if(model.getChamber().equalsIgnoreCase("house")){
                    response.addCommitteeHouse(model);
                }
                else if(model.getChamber().equalsIgnoreCase("joint")){
                    response.addCommitteeJoint(model);
                }
            }
            response.sortCommitteeHouse();
            response.sortCommitteeSenate();
            response.sortCommitteeJoint();
            return response.getCommitteeHouse();
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
    protected void onPostExecute(List<CommitteeModel> result) {
        if(result.size() > 0){
            committeeListener.onLoadedCommittee();
        }
        else{
            committeeListener.onErrorCommittee();
        }
    }
}
