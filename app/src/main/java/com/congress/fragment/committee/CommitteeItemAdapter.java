package com.congress.fragment.committee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.congress.activity.R;
import com.congress.models.CommitteeModel;

import java.util.HashMap;
import java.util.List;

public class CommitteeItemAdapter extends ArrayAdapter<CommitteeModel>{

    Context context;
    List<CommitteeModel> committeeModel;
    String type;
    private static LayoutInflater inflater = null;
    private static final String Tag = CommitteeItemAdapter.class.getSimpleName();

    public CommitteeItemAdapter(Context context, List<CommitteeModel> committeeModel, String type) {
        super(context, R.layout.item_committee, committeeModel);
        this.context = context;
        this.committeeModel = committeeModel;
        this.type = type;
        inflater =(LayoutInflater)context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommitteeHolder holder = null;
        if(convertView == null)
        {
            holder = new CommitteeHolder();
            convertView = inflater.inflate(R.layout.item_committee, parent, false);
            holder.committeeId = (TextView)convertView.findViewById(R.id.committee_id);
            holder.committeeName = (TextView)convertView.findViewById(R.id.committee_name);
            holder.committeeChamber = (TextView) convertView.findViewById(R.id.committee_chamber);
            convertView.setTag(holder);
        }
        else
        {
            holder = (CommitteeHolder)convertView.getTag();
        }

        holder.committeeId.setText(committeeModel.get(position).getCommittee_id());
        holder.committeeName.setText(committeeModel.get(position).getName());
        holder.committeeChamber.setText(committeeModel.get(position).getChamber());

        HashMap values = new HashMap();
        values.put("position", position);
        values.put("type", type);
        holder.values = values;
        return convertView;
    }

    public static class CommitteeHolder
    {
        public HashMap values;
        TextView committeeId;
        TextView committeeName;
        TextView committeeChamber;
    }
}
