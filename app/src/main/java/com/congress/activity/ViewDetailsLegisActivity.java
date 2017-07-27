package com.congress.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.congress.models.LegisModel;
import com.congress.response.FavoriteResponse;
import com.congress.response.LegisResponse;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import static com.squareup.picasso.Picasso.with;

/**
 * Created by Parul on 19-Nov-16.
 */

public class ViewDetailsLegisActivity extends AppCompatActivity {

    private static final String Tag = ViewDetailsLegisActivity.class.getSimpleName();
    LegisModel model = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdetails_legis);
        setTitle("Legislator Info");

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        HashMap values = (HashMap) getIntent().getSerializableExtra("legisModel");
        String type = values.get("type").toString();
        int position = (int)values.get("position");
        LegisResponse response = LegisResponse.getInstance();
        if(type.equalsIgnoreCase("all")){
            List<LegisModel> legisModels = response.getLegisByState();
            model = legisModels.get(position);
        }
        else if(type.equalsIgnoreCase("senate")){
            List<LegisModel> legisModels = response.getLegisBySenate();
            model = legisModels.get(position);
        }
        else if(type.equalsIgnoreCase("house")){
            List<LegisModel> legisModels = response.getLegisByHouse();
            model = legisModels.get(position);
        }
        else if(type.equalsIgnoreCase("favorite")){
            FavoriteResponse favoriteResponse = FavoriteResponse.getInstance();
            List<LegisModel> legisModels  = favoriteResponse.getLegisFavorite();
            model = legisModels.get(position);
        }
        loadViewDetailsModule();
    }

    private void loadViewDetailsModule() {
        ImageView imageView = (ImageView) findViewById(R.id.viewDetails_legis_userProfile);
        Picasso
                .with(this)
                .load("https://theunitedstates.io/images/congress/225x275/"+model.getBioguide_id()+".jpg")
                .into(imageView);

        ImageView partImage = (ImageView) findViewById(R.id.viewDetails_legis_partyImage);

        String party = model.getParty();
        if(party.equalsIgnoreCase("R")){
            ((TextView) findViewById(R.id.viewDetails_legis_partyName)).setText("Republican");

            Picasso
                    .with(this)
                    .load(R.drawable.ic_republican)
                    .into(partImage);
        }
        else if(party.equalsIgnoreCase("D")){
            ((TextView) findViewById(R.id.viewDetails_legis_partyName)).setText("Democrat");

            Picasso
                    .with(this)
                    .load(R.drawable.ic_democrat)
                    .into(partImage);
        }
        else if(party.equalsIgnoreCase("I")){
            ((TextView) findViewById(R.id.viewDetails_legis_partyName)).setText("Independent");

            Picasso
                    .with(this)
                    .load(R.drawable.ic_independent)
                    .into(partImage);
        }

        ImageView favoriteImage = (ImageView) findViewById(R.id.viewDetails_legis_FavoriteButton);
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

        ((ImageView) findViewById(R.id.viewDetails_legis_facebook)).setVisibility(View.VISIBLE);
        ((ImageView) findViewById(R.id.viewDetails_legis_twitter)).setVisibility(View.VISIBLE);
        ((ImageView) findViewById(R.id.viewDetails_legis_webSite)).setVisibility(View.VISIBLE);

        ((TextView) findViewById(R.id.viewDetails_legis_fullName)).setText(model.getFullName());
        ((TextView) findViewById(R.id.viewDetails_legis_email)).setText(model.getOc_email());
        ((TextView) findViewById(R.id.viewDetails_legis_chamber)).setText(model.getChamber());
        ((TextView) findViewById(R.id.viewDetails_legis_contact)).setText(model.getPhone());
        ((TextView) findViewById(R.id.viewDetails_legis_startTerm)).setText(model.getFormatedStartTerm());
        ((TextView) findViewById(R.id.viewDetails_legis_endTerm)).setText(model.getFormatedEndTerm());
        ((TextView) findViewById(R.id.viewDetails_legis_office)).setText(model.getOffice());
        ((TextView) findViewById(R.id.viewDetails_legis_state)).setText(model.getState());
        ((TextView) findViewById(R.id.viewDetails_legis_fax)).setText(model.getFax());
        ((TextView) findViewById(R.id.viewDetails_legis_birthday)).setText(model.getBirthday());
        //Log.d(Tag, "Progress " + model.getTermProgress());
        int progress = model.getTermProgress();
        ((ProgressBar) findViewById(R.id.viewDetails_legis_progressBar)).setProgress(progress);
        ((TextView) findViewById(R.id.viewDetails_legis_progressBar_text)).setText(String.valueOf(progress) + "%");
    }

    public void viewDetailsFacebook(View view) {
        if(model.getFacebook_id() == null){
            Toast.makeText(ViewDetailsLegisActivity.this, "No Facebook Id available" , Toast.LENGTH_LONG).show();
        }
        else{

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + model.getFacebook_id()));
            startActivity(browserIntent);
        }
    }

    public void viewDetailsTwitter(View view) {
        if(model.getTwitter_id() == null){
            Toast.makeText(ViewDetailsLegisActivity.this, "No Twitter Id available" , Toast.LENGTH_LONG).show();
        }
        else{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com/" + model.getTwitter_id()));
            startActivity(browserIntent);
        }

    }

    public void viewDetailsWebsite(View view) {
        if(model.getWebsite() == null){
            Toast.makeText(ViewDetailsLegisActivity.this, "No Website available" , Toast.LENGTH_LONG).show();
        }
        else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getWebsite()));
            startActivity(browserIntent);
        }
    }

    public void viewDetailsLegisFavorite(View view) {
        ImageView image = (ImageView) findViewById(R.id.viewDetails_legis_FavoriteButton);
        FavoriteResponse response = FavoriteResponse.getInstance();
        if(!model.getisFavorite()){
            model.setisFavorite(true);
            response.addLegisFavorite(model);
            Picasso
                    .with(this)
                    .load(R.drawable.ic_favorite_filled)
                    .into(image);
        }
        else{
            model.setisFavorite(false);
            response.removeLegisFavorite(model);
            with(this)
                    .load(R.drawable.ic_favorite)
                    .into(image);
        }
    }
}

