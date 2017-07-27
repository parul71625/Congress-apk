package com.congress.fragment.bills;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.congress.activity.R;
import com.congress.models.BillsModel;
import com.congress.response.BillsResponse;

import java.util.List;


public class ActiveBillsFragment extends Fragment {
    private ListView activeBillslistView;

    public ActiveBillsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bills_active, container, false);
        activeBillslistView = (ListView)view.findViewById(R.id.listViewActiveBill);

        return view;
    }

    public void loadListView() {
        BillsResponse respose = BillsResponse.getInstance();
        List<BillsModel> activeBills = respose.getBillsActive();
        activeBillslistView.setAdapter(new BillsItemAdapter(getActivity(), activeBills, "active"));
    }
}
