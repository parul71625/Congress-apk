package com.congress.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.congress.models.BillsModel;
import com.congress.response.BillsResponse;
import com.congress.response.FavoriteResponse;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import static com.squareup.picasso.Picasso.with;

/**
 * Created by Parul on 19-Nov-16.
 */

public class ViewDetailsBillsActivity extends AppCompatActivity {

    private static final String Tag = ViewDetailsBillsActivity.class.getSimpleName();
    BillsModel model = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdetails_bills);
        setTitle("Bills Info");

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        HashMap values = (HashMap) getIntent().getSerializableExtra("billsModel");
        String type = values.get("type").toString();
        int position = (int)values.get("position");
        BillsResponse response = BillsResponse.getInstance();
        if(type.equalsIgnoreCase("active")){
            List<BillsModel> billsModels = response.getBillsActive();
            model = billsModels.get(position);
        }
        else if(type.equalsIgnoreCase("new")){
            List<BillsModel> billsModels = response.getBillsNew();
            model = billsModels.get(position);
        }
        else if(type.equalsIgnoreCase("favorite")){
            FavoriteResponse favoriteResponse = FavoriteResponse.getInstance();
            List<BillsModel> billsModels  = favoriteResponse.getBillsFavorite();
            model = billsModels.get(position);
        }
        loadViewDetailsModule();
    }

    private void loadViewDetailsModule() {

        ImageView favoriteImage = (ImageView) findViewById(R.id.viewDetails_Bills_FavoriteButton);
        favoriteImage.setVisibility(View.VISIBLE);
        if(model.getisFavorite()){
            Picasso
                    .with(this)
                    .load(R.drawable.ic_favorite_filled)
                    .into(favoriteImage);
        }
        else{
            with(this)
                    .load(R.drawable.ic_favorite)
                    .into(favoriteImage);
        }
        ((TextView) findViewById(R.id.viewDetails_bills_billId)).setText(model.getBill_id());
        ((TextView) findViewById(R.id.viewDetails_bills_title)).setText(model.getOfficial_title());
        ((TextView) findViewById(R.id.viewDetails_bills_billtype)).setText(model.getBill_type());
        ((TextView) findViewById(R.id.viewDetails_bills_sponsor)).setText(model.getSponsorName());
        ((TextView) findViewById(R.id.viewDetails_bills_chamber)).setText(model.getChamber());
        ((TextView) findViewById(R.id.viewDetails_bills_status)).setText(model.getStatus());
        ((TextView) findViewById(R.id.viewDetails_bills_intro)).setText(model.getIntroduced_on());
        ((TextView) findViewById(R.id.viewDetails_bills_congressUrl)).setText(model.getCongressUrl());
        ((TextView) findViewById(R.id.viewDetails_bills_versionStatus)).setText(model.getVersion_name());
        ((TextView) findViewById(R.id.viewDetails_bills_billUrl)).setText(model.getPdfUrl());
    }

    public void viewDetailsBillsFavorite(View view) {
        ImageView image = (ImageView) findViewById(R.id.viewDetails_Bills_FavoriteButton);
        FavoriteResponse response = FavoriteResponse.getInstance();

        if(!model.getisFavorite()){
            model.setisFavorite(true);
            response.addBillsFavorite(model);
            Picasso
                    .with(this)
                    .load(R.drawable.ic_favorite_filled)
                    .into(image);
        }
        else{
            model.setisFavorite(false);
            response.removeBillsFavorite(model);
            with(this)
                    .load(R.drawable.ic_favorite)
                    .into(image);
        }
    }
}
