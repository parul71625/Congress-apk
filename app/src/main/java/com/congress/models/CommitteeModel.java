package com.congress.models;

import java.io.Serializable;

/**
 * Created by Parul on 17-Nov-16.
 */

public class CommitteeModel implements Serializable {
    private String chamber;
    private String committee_id;
    private String name;
    private String parent_committee_id;
    private String phone;
    private String subcommittee;
    private String office;
    private boolean isFavorite = false;

    public String getChamber(){
        return chamber.substring(0, 1).toUpperCase() + chamber.substring(1);
    }

    public String getCommittee_id(){
        return committee_id.toUpperCase();
    }

    public String getName(){
        if(name != null && !name.isEmpty())
            return name;
        else
            return "N.A";
    }

    public String getParent_committee_id(){
        if(parent_committee_id != null && !parent_committee_id.isEmpty())
            return parent_committee_id.toUpperCase();
        else
            return "N.A";
    }

    public String getPhone(){
        if(phone != null && !phone.isEmpty())
            return phone;
        else
            return "N.A";
    }

    public String getSubcommittee(){
        if(subcommittee != null && !subcommittee.isEmpty())
            return subcommittee;
        else
            return "N.A";
    }

    public String getOffice(){
        if(office != null && !office.isEmpty())
            return office;
        else
            return "N.A";
    }

    public boolean getisFavorite(){
        return isFavorite;
    }

    public void setisFavorite(boolean favorite){
        isFavorite = favorite;
    }
}
