package com.congress.fragment.favorite;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.congress.activity.R;
import com.congress.fragment.committee.CommitteeItemAdapter;
import com.congress.models.CommitteeModel;
import com.congress.response.FavoriteResponse;

import java.util.List;


public class FavoriteCommitteeFragment extends Fragment {

    private ListView favoriteByCommitteeListView;

    public FavoriteCommitteeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_committee, container, false);
        favoriteByCommitteeListView = (ListView)view.findViewById(R.id.listViewFavoriteByCommittee);
        return view;
    }

    public void loadListView() {
        FavoriteResponse response = FavoriteResponse.getInstance();
        List<CommitteeModel> committeeFavorite = response.getCommitteeFavorite();
        if(committeeFavorite != null && committeeFavorite.size() > 0 ){
            ((TextView)getView().findViewById(R.id.textViewFavoriteByCommittee)).setVisibility(View.GONE);
            favoriteByCommitteeListView.setVisibility(View.VISIBLE);
            favoriteByCommitteeListView.setAdapter(new CommitteeItemAdapter(getActivity(), committeeFavorite, "favorite"));
        }
        else{
            favoriteByCommitteeListView.setVisibility(View.GONE);
            ((TextView)getView().findViewById(R.id.textViewFavoriteByCommittee)).setVisibility(View.VISIBLE);
        }
    }
}
