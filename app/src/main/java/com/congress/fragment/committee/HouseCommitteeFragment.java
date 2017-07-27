package com.congress.fragment.committee;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.congress.activity.R;
import com.congress.models.CommitteeModel;
import com.congress.response.CommitteeResponse;

import java.util.List;


public class HouseCommitteeFragment extends Fragment {

    private ListView committeeHouselistView;

    public HouseCommitteeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_committee_house, container, false);
        committeeHouselistView = (ListView)view.findViewById(R.id.listViewCommitteeByHouse);
        return view;
    }

    public void loadListView() {
        CommitteeResponse respose = CommitteeResponse.getInstance();
        List<CommitteeModel> committeeHouse = respose.getCommitteeHouse();
        committeeHouselistView.setAdapter(new CommitteeItemAdapter(getActivity(), committeeHouse, "house"));
    }
}
