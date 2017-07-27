package com.congress.response;

import com.congress.models.BillsModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BillsResponse {
    private static List<BillsModel> billsActive;
    private static List<BillsModel> billsNew;
    private static BillsResponse billResponse;

    public static BillsResponse getInstance(){
        if(billResponse == null){
            billsActive = new ArrayList<BillsModel>();
            billsNew = new ArrayList<BillsModel>();
            billResponse = new BillsResponse();
        }
        return  billResponse;
    }

    public void setBillsActive(List<BillsModel> billsActive){
        billsActive = billsActive;
    }

    public void setBillsNew(List<BillsModel> billsNew){
        billsNew = billsNew;
    }

    public void setBillsFavorite(List<BillsModel> billsFavorite){
        billsFavorite = billsFavorite;
    }

    public void addBillsActive(BillsModel BillsModel){ billsActive.add(BillsModel); }

    public void addBillsNew(BillsModel BillsModel){ billsNew.add(BillsModel); }

    public void sortBillsActive(){
        Collections.sort(billsActive, new Comparator<BillsModel>() {
            @Override
            public int compare(BillsModel l1, BillsModel l2) {
                return l2.getIntroducedOnLong().compareTo(l1.getIntroducedOnLong());
            }
        });
    }

    public void sortBillsNew(){
        Collections.sort(billsNew, new Comparator<BillsModel>() {
            @Override
            public int compare(BillsModel l1, BillsModel l2) {
                return l2.getIntroducedOnLong().compareTo(l1.getIntroducedOnLong());
            }
        });
    }

    public List<BillsModel> getBillsActive(){
        return billsActive;
    }

    public List<BillsModel> getBillsNew(){
        return billsNew;
    }
}
