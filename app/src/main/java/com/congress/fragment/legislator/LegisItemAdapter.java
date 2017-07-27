package com.congress.fragment.legislator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.congress.activity.R;
import com.congress.models.LegisModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class LegisItemAdapter extends ArrayAdapter<LegisModel>{

    Context context;
    List<LegisModel> legisModels;
    String type;
    private static LayoutInflater inflater = null;
    private static final String Tag = LegisItemAdapter.class.getSimpleName();

    public LegisItemAdapter(Context context, List<LegisModel> legisModels, String type) {
        super(context, R.layout.item_legis, legisModels);
        this.context = context;
        this.legisModels = legisModels;
        this.type = type;
        inflater =(LayoutInflater)context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LegisHolder holder = null;
        if(convertView == null)
        {
            holder = new LegisHolder();
            convertView = inflater.inflate(R.layout.item_legis, parent, false);
            holder.userImage = (ImageView)convertView.findViewById(R.id.userProfileImage);
            holder.fullName = (TextView)convertView.findViewById(R.id.legis_username);
            holder.stateInfo = (TextView)convertView.findViewById(R.id.legis_stateinfo);
            holder.userImageProgressBar = (ProgressBar) convertView.findViewById(R.id.userProfileProgressBar);
            convertView.setTag(holder);
        }
        else
        {
            holder = (LegisHolder)convertView.getTag();
        }
        final LegisHolder finalHolder = holder;
        Picasso
                .with(this.context)
                .load("https://theunitedstates.io/images/congress/225x275/"+legisModels.get(position).getBioguide_id()+".jpg")
                .into(holder.userImage, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        finalHolder.userImage.setVisibility(View.VISIBLE);
                        finalHolder.userImageProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        finalHolder.userImage.setVisibility(View.GONE);
                        finalHolder.userImageProgressBar.setVisibility(View.VISIBLE);
                    }
                });
        holder.fullName.setText(legisModels.get(position).getLastFirstName());
        holder.stateInfo.setText(legisModels.get(position).getStateInfo());
        HashMap values = new HashMap();
        values.put("position", position);
        values.put("type", type);
        holder.values = values;
        return convertView;
    }

    public static class LegisHolder
    {
        public HashMap values;
        ProgressBar userImageProgressBar;
        ImageView userImage;
        TextView fullName;
        TextView stateInfo;
    }
}
