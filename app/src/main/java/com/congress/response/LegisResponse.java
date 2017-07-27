package com.congress.response;

import com.congress.models.LegisModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LegisResponse {
    private static List<LegisModel> legisByState;
    private static List<LegisModel> legisByHouse;
    private static List<LegisModel> legisBySenate;
    private static LegisResponse legisResponse;

    public static LegisResponse getInstance(){
        if(legisResponse == null){
            legisByState = new ArrayList<LegisModel>();
            legisByHouse = new ArrayList<LegisModel>();
            legisBySenate = new ArrayList<LegisModel>();
            legisResponse = new LegisResponse();
        }
        return  legisResponse;
    }

    public void setLegisByState(List<LegisModel> legisByState){
        legisByState = legisByState;
    }

    public void setLegisByHouse(List<LegisModel> legisByHouse){
        legisByHouse = legisByHouse;
    }

    public void setLegisBySenate(List<LegisModel> legisBySenate){
        legisBySenate = legisBySenate;
    }

    public void addLegisByState(LegisModel legisModel){
        legisByState.add(legisModel);
    }

    public void addLegisByHouse(LegisModel legisModel){ legisByHouse.add(legisModel); }

    public void addLegisBySenate(LegisModel legisModel){ legisBySenate.add(legisModel); }

    public void sortLegisByState(){
        Collections.sort(legisByState, new Comparator<LegisModel>() {
            @Override
            public int compare(LegisModel l1, LegisModel l2) {
                int value = l1.getState_name().compareTo(l2.getState_name());
                if(value != 0)
                    return value;
                else{
                    return l1.getLastFirstName().compareTo(l2.getLastFirstName());
                }
            }
        });
    }

    public void sortLegisByHouse(){
        Collections.sort(legisByHouse, new Comparator<LegisModel>() {
            @Override
            public int compare(LegisModel l1, LegisModel l2) {
                return l1.getLastFirstName().compareTo(l2.getLastFirstName());
            }
        });
    }

    public void sortLegisBySenate(){
        Collections.sort(legisBySenate, new Comparator<LegisModel>() {
            @Override
            public int compare(LegisModel l1, LegisModel l2) {
                return l1.getLastFirstName().compareTo(l2.getLastFirstName());
            }
        });
    }

    public List<LegisModel> getLegisByState(){
        return legisByState;
    }

    public List<LegisModel> getLegisByHouse(){
        return legisByHouse;
    }

    public List<LegisModel> getLegisBySenate(){
        return legisBySenate;
    }
}
