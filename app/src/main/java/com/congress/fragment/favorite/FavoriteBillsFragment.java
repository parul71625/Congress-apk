package com.congress.fragment.favorite;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.congress.activity.R;
import com.congress.fragment.bills.BillsItemAdapter;
import com.congress.models.BillsModel;
import com.congress.response.FavoriteResponse;

import java.util.List;


public class FavoriteBillsFragment extends Fragment {

    private ListView favoriteByBillsListView;

    public FavoriteBillsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_bills, container, false);
        favoriteByBillsListView = (ListView)view.findViewById(R.id.listViewFavoriteByBills);
        return view;
    }

    public void loadListView() {
        FavoriteResponse response = FavoriteResponse.getInstance();
        List<BillsModel> billsFavorite = response.getBillsFavorite();

        if(billsFavorite != null && billsFavorite.size() > 0 ){
            ((TextView)getView().findViewById(R.id.textViewFavoriteByBills)).setVisibility(View.GONE);
            favoriteByBillsListView.setVisibility(View.VISIBLE);
            favoriteByBillsListView.setAdapter(new BillsItemAdapter(getActivity(), billsFavorite, "favorite"));
        }
        else{
            favoriteByBillsListView.setVisibility(View.GONE);
            ((TextView)getView().findViewById(R.id.textViewFavoriteByBills)).setVisibility(View.VISIBLE);
        }
    }
}
