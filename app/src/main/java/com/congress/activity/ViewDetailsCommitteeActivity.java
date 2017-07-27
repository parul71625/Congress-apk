package com.congress.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.congress.models.CommitteeModel;
import com.congress.response.CommitteeResponse;
import com.congress.response.FavoriteResponse;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import static com.squareup.picasso.Picasso.with;

/**
 * Created by Parul on 19-Nov-16.
 */

public class ViewDetailsCommitteeActivity extends AppCompatActivity {

    private static final String Tag = ViewDetailsCommitteeActivity.class.getSimpleName();
    CommitteeModel model = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdetails_committee);
        setTitle("Committee Info");

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        HashMap values = (HashMap) getIntent().getSerializableExtra("committeeModel");
        String type = values.get("type").toString();
        int position = (int)values.get("position");
        CommitteeResponse response = CommitteeResponse.getInstance();
        if(type.equalsIgnoreCase("house")){
            List<CommitteeModel> committeeModels = response.getCommitteeHouse();
            model = committeeModels.get(position);
        }
        else if(type.equalsIgnoreCase("senate")){
            List<CommitteeModel> committeeModels = response.getCommitteeSenate();
            model = committeeModels.get(position);
        }
        else if(type.equalsIgnoreCase("joint")){
            List<CommitteeModel> committeeModels = response.getCommitteeJoint();
            model = committeeModels.get(position);
        }
        else if(type.equalsIgnoreCase("favorite")){
            FavoriteResponse favoriteResponse = FavoriteResponse.getInstance();
            List<CommitteeModel> committeeModels  = favoriteResponse.getCommitteeFavorite();
            model = committeeModels.get(position);
        }

        loadViewDetailsModule();
    }

    private void loadViewDetailsModule() {

        ImageView chamberImage = (ImageView) findViewById(R.id.viewDetails_committee_cImage);

        String chamber = model.getChamber();
        if(chamber.equalsIgnoreCase("Senate")){
            with(this)
                    .load(R.drawable.ic_senate)
                    .into(chamberImage);
        }
        else if(chamber.equalsIgnoreCase("House")){
            with(this)
                    .load(R.drawable.ic_house)
                    .into(chamberImage);
        }

        ImageView favoriteImage = (ImageView) findViewById(R.id.viewDetails_committee_favoriteButton);
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

        ((TextView) findViewById(R.id.viewDetails_committee_chamber)).setText(model.getChamber());
        ((TextView) findViewById(R.id.viewDetails_committee_comId)).setText(model.getCommittee_id());
        ((TextView) findViewById(R.id.viewDetails_committee_name)).setText(model.getName());
        ((TextView) findViewById(R.id.viewDetails_committee_parent)).setText(model.getParent_committee_id());
        ((TextView) findViewById(R.id.viewDetails_committee_contact)).setText(model.getPhone());
        ((TextView) findViewById(R.id.viewDetails_committee_office)).setText(model.getOffice());
    }

    public void viewDetailsCommitteeFavorite(View view) {
        ImageView image = (ImageView) findViewById(R.id.viewDetails_committee_favoriteButton);
        FavoriteResponse response = FavoriteResponse.getInstance();

        if(!model.getisFavorite()){
            model.setisFavorite(true);
            response.addCommitteeFavorite(model);
            Picasso
                    .with(this)
                    .load(R.drawable.ic_favorite_filled)
                    .into(image);
        }
        else{
            model.setisFavorite(false);
            response.removeCommitteeFavorite(model);
            with(this)
                    .load(R.drawable.ic_favorite)
                    .into(image);
        }
    }
}
