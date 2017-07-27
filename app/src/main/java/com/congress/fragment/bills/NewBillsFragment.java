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


public class NewBillsFragment extends Fragment {

    private ListView newBillslistView;

    public NewBillsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bills_new, container, false);
        newBillslistView = (ListView)view.findViewById(R.id.listViewNewBill);
        return view;
    }

    public void loadListView() {
        BillsResponse respose = BillsResponse.getInstance();
        List<BillsModel> newBills = respose.getBillsNew();
        newBillslistView.setAdapter(new BillsItemAdapter(getActivity(), newBills, "new"));
    }
}


