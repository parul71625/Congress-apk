package com.congress.response;

import com.congress.models.CommitteeModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Parul on 17-Nov-16.
 */

public class CommitteeResponse {
    private static List<CommitteeModel> committeeHouse;
    private static List<CommitteeModel> committeeSenate;
    private static List<CommitteeModel> committeeJoint;
    private static CommitteeResponse committeeResponse;

    public static CommitteeResponse getInstance(){
        if(committeeResponse == null){
            committeeHouse = new ArrayList<CommitteeModel>();
            committeeSenate = new ArrayList<CommitteeModel>();
            committeeJoint = new ArrayList<CommitteeModel>();
            committeeResponse = new CommitteeResponse();
        }
        return  committeeResponse;
    }

    public void setCommitteeHouse(List<CommitteeModel> committeeHouse){
        committeeHouse = committeeHouse;
    }

    public void setCommitteeSenate(List<CommitteeModel> committeeSenate){
        committeeSenate = committeeSenate;
    }

    public void setCommitteeJoint(List<CommitteeModel> committeeJoint){
        committeeJoint = committeeJoint;
    }

    public void setCommitteeFavorite(List<CommitteeModel> committeeFavorite){
        committeeFavorite = committeeFavorite;
    }

    public void addCommitteeHouse(CommitteeModel CommitteeModel){ committeeHouse.add(CommitteeModel); }

    public void addCommitteeSenate(CommitteeModel CommitteeModel){ committeeSenate.add(CommitteeModel); }

    public void addCommitteeJoint(CommitteeModel CommitteeModel){ committeeJoint.add(CommitteeModel); }

    public void sortCommitteeHouse(){
        Collections.sort(committeeHouse, new Comparator<CommitteeModel>() {
            @Override
            public int compare(CommitteeModel l1, CommitteeModel l2) {
                return l1.getName().compareTo(l2.getName());
            }
        });
    }

    public void sortCommitteeSenate(){
        Collections.sort(committeeSenate, new Comparator<CommitteeModel>() {
            @Override
            public int compare(CommitteeModel l1, CommitteeModel l2) {
                return l1.getName().compareTo(l2.getName());
            }
        });
    }

    public void sortCommitteeJoint(){
        Collections.sort(committeeJoint, new Comparator<CommitteeModel>() {
            @Override
            public int compare(CommitteeModel l1, CommitteeModel l2) {
                return l1.getName().compareTo(l2.getName());
            }
        });
    }

    public List<CommitteeModel> getCommitteeHouse(){
        return committeeHouse;
    }

    public List<CommitteeModel> getCommitteeSenate(){
        return committeeSenate;
    }

    public List<CommitteeModel> getCommitteeJoint(){
        return committeeJoint;
    }

}
