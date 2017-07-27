package com.congress.fragment.legislator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.congress.activity.R;
import com.congress.models.LegisModel;
import com.congress.response.LegisResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class SenateFragment extends Fragment {
    private static final String Tag = SenateFragment.class.getSimpleName();
    private ListView legisBySenateListView;
    Map mapIndex;
    Map<String, Integer> treeMap;
    LayoutInflater inflater;
    public SenateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_legis_senate, container, false);
        this.inflater = inflater;
        legisBySenateListView = (ListView)view.findViewById(R.id.listViewLegisBySenate);
        return view;
    }

    public void loadListView() {
        LegisResponse response = LegisResponse.getInstance();
        List<LegisModel> legisBySenate = response.getLegisBySenate();
        getIndexList();
        dislayIndex();
        legisBySenateListView.setAdapter(new LegisItemAdapter(getActivity(), legisBySenate, "senate"));
    }

    private void getIndexList() {
        LegisResponse response = LegisResponse.getInstance();
        List<LegisModel> legisByState = response.getLegisBySenate();
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
        LinearLayout indexLayout = (LinearLayout) getView().findViewById(R.id.legisBySenate_sideIndex);
        TextView textView;
        List<String> indexList = new ArrayList<String>(treeMap.keySet());
        for (String index : indexList) {
            textView = (TextView) inflater.inflate(R.layout.side_index_item, null);
            textView.setText(index);
            textView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    TextView selectedIndex = (TextView) view;
                    legisBySenateListView.setSelection(treeMap.get(selectedIndex.getText()));
                }
            });
            indexLayout.addView(textView);
        }
    }
}