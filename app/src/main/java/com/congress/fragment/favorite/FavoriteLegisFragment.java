package com.congress.fragment.favorite;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.congress.activity.R;
import com.congress.fragment.legislator.LegisItemAdapter;
import com.congress.models.LegisModel;
import com.congress.response.FavoriteResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class FavoriteLegisFragment extends Fragment {
    private static final String Tag = FavoriteLegisFragment.class.getSimpleName();
    private ListView favoriteByLegisListView;
    Map mapIndex;
    Map<String, Integer> treeMap;
    LayoutInflater inflater;
    public FavoriteLegisFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_legis, container, false);
        this.inflater = inflater;
        favoriteByLegisListView = (ListView)view.findViewById(R.id.listViewFavoriteByLegis);
        return view;
    }

    public void loadListView() {
        FavoriteResponse response = FavoriteResponse.getInstance();
        List<LegisModel> legisFavorite = response.getLegisFavorite();
        if(legisFavorite != null && legisFavorite.size() > 0 ){
            ((TextView)getView().findViewById(R.id.textViewFavoriteByLegis)).setVisibility(View.GONE);
            ((LinearLayout)getView().findViewById(R.id.favoriteLegis_sideIndex)).setVisibility(View.VISIBLE);
            favoriteByLegisListView.setVisibility(View.VISIBLE);
            getIndexList();
            dislayIndex();
            favoriteByLegisListView.setAdapter(new LegisItemAdapter(getActivity(), legisFavorite, "favorite"));
        }
        else{
            favoriteByLegisListView.setVisibility(View.GONE);
            ((LinearLayout)getView().findViewById(R.id.favoriteLegis_sideIndex)).setVisibility(View.GONE);
            ((TextView)getView().findViewById(R.id.textViewFavoriteByLegis)).setVisibility(View.VISIBLE);
        }
    }

    private void getIndexList() {
        FavoriteResponse response = FavoriteResponse.getInstance();
        List<LegisModel> legisByState = response.getLegisFavorite();
        mapIndex = new HashMap();
        for (int i = 0; i < legisByState.size(); i++) {
            String name = legisByState.get(i).getLastFirstName();
            String index = name.substring(0, 1);
            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
        treeMap = new TreeMap<String, Integer>(mapIndex);
    }

    private void dislayIndex(){
        LinearLayout indexLayout = (LinearLayout) getView().findViewById(R.id.favoriteLegis_sideIndex);
        indexLayout.removeAllViews();
        TextView textView;
        List<String> indexList = new ArrayList<String>(treeMap.keySet());
        for (String index : indexList) {
            textView = (TextView) inflater.inflate(R.layout.side_index_item, null);
            textView.setText(index);
            textView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    TextView selectedIndex = (TextView) view;
                    favoriteByLegisListView.setSelection(treeMap.get(selectedIndex.getText()));
                }
            });
            indexLayout.addView(textView);
        }
    }
}
