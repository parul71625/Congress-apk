package com.congress.response;

import com.congress.models.BillsModel;
import com.congress.models.CommitteeModel;
import com.congress.models.LegisModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FavoriteResponse {

    private static FavoriteResponse favoriteResponse;

    private static List<BillsModel> billsFavorite;
    private static List<CommitteeModel> committeeFavorite;
    private static List<LegisModel> legisFavorite;

    public static FavoriteResponse getInstance(){
        if(favoriteResponse == null){
            legisFavorite = new ArrayList<LegisModel>();
            committeeFavorite = new ArrayList<CommitteeModel>();
            billsFavorite = new ArrayList<BillsModel>();
            favoriteResponse = new FavoriteResponse();
        }
        return favoriteResponse;
    }

    public void addLegisFavorite(LegisModel LegisModel){
        legisFavorite.add(LegisModel);
        sortLegisByHouse();
    }

    public void addBillsFavorite(BillsModel BillsModel){ billsFavorite.add(BillsModel); }

    public void addCommitteeFavorite(CommitteeModel CommitteeModel){ committeeFavorite.add(CommitteeModel); }

    public void sortLegisByHouse(){
        Collections.sort(legisFavorite, new Comparator<LegisModel>() {
            @Override
            public int compare(LegisModel l1, LegisModel l2) {
                return l1.getLastFirstName().compareTo(l2.getLastFirstName());
            }
        });
    }

    public void removeLegisFavorite(LegisModel LegisModel){ legisFavorite.remove(LegisModel); }

    public void removeBillsFavorite(BillsModel BillsModel){ billsFavorite.remove(BillsModel); }

    public void removeCommitteeFavorite(CommitteeModel CommitteeModel){ committeeFavorite.remove(CommitteeModel); }

    public List<CommitteeModel> getCommitteeFavorite(){
        return committeeFavorite;
    }

    public List<LegisModel> getLegisFavorite(){
        return legisFavorite;
    }

    public List<BillsModel> getBillsFavorite(){
        return billsFavorite;
    }
}
