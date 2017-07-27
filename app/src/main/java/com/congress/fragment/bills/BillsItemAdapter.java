package com.congress.fragment.bills;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.congress.activity.R;
import com.congress.models.BillsModel;

import java.util.HashMap;
import java.util.List;

public class BillsItemAdapter extends ArrayAdapter<BillsModel>{

    Context context;
    List<BillsModel> billsModel;
    String type;
    private static LayoutInflater inflater = null;
    private static final String Tag = BillsItemAdapter.class.getSimpleName();

    public BillsItemAdapter(Context context, List<BillsModel> billsModel, String type) {
        super(context, R.layout.item_bills, billsModel);
        this.context = context;
        this.billsModel = billsModel;
        this.type = type;
        inflater =(LayoutInflater)context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BillsHolder holder = null;
        if(convertView == null)
        {
            holder = new BillsHolder();
            convertView = inflater.inflate(R.layout.item_bills, parent, false);
            holder.billId = (TextView)convertView.findViewById(R.id.bills_id);
            holder.shortTitle = (TextView)convertView.findViewById(R.id.bills_shortTitle);
            holder.introducedOn = (TextView) convertView.findViewById(R.id.bills_introducedOn);
            convertView.setTag(holder);
        }
        else
        {
            holder = (BillsHolder)convertView.getTag();
        }
        holder.billId.setText(billsModel.get(position).getBill_id());
        holder.shortTitle.setText(billsModel.get(position).getOfficial_title());
        holder.introducedOn.setText(billsModel.get(position).getIntroduced_on());

        HashMap values = new HashMap();
        values.put("position", position);
        values.put("type", type);
        holder.values = values;
        return convertView;
    }

    public static class BillsHolder
    {
        public HashMap values;
        TextView billId;
        TextView shortTitle;
        TextView introducedOn;
    }
}
