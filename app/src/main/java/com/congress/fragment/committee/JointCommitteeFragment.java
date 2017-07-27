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


public class JointCommitteeFragment extends Fragment {

    private ListView committeeJointlistView;

    public JointCommitteeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_committee_joint, container, false);
        committeeJointlistView = (ListView)view.findViewById(R.id.listViewCommitteeByJoint);
        return view;
    }
    public void loadListView() {
        CommitteeResponse respose = CommitteeResponse.getInstance();
        List<CommitteeModel> committeeJoint = respose.getCommitteeJoint();
        committeeJointlistView.setAdapter(new CommitteeItemAdapter(getActivity(), committeeJoint, "joint"));
    }

}
