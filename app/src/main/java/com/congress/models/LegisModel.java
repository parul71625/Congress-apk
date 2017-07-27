package com.congress.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Parul on 17-Nov-16.
 */

public class LegisModel implements Serializable{

    private static final String Tag = LegisModel.class.getSimpleName();

    private String bioguide_id;
    private String birthday;
    private String chamber;
    private String first_name;
    private String last_name;
    private String state_name;
    private int district;
    private String party;
    private String term_start;
    private String term_end;
    private String phone;
    private String oc_email;
    private String state;
    private String fax;
    private boolean isFavorite = false;
    private String title;
    private String office;
    private String facebook_id;
    private String twitter_id;
    private String website;

    public String getFacebook_id(){
        return facebook_id;
    }

    public String getTwitter_id(){
        return twitter_id;
    }

    public String getWebsite(){
        return website;
    }

    public String getOffice(){
        if(office != null && !office.isEmpty())
            return office;
        else
            return "N.A";
    }

    public String getFullName(){
        return title + ". " + last_name + ", " + first_name;
    }

    public String getBioguide_id() {
        return bioguide_id;
    }

    public String getState_name(){
        return state_name;
    }

    public String getLastFirstName() {
        return last_name + ", " + first_name;
    }

    public String getStateInfo() {
        return "(" + party + ") " + state_name + " - District " + district;
    }

    public int getDistrict() {
        return district;
    }

    public String getParty(){
        return party;
    }

    public String getChamber(){
        return chamber.substring(0, 1).toUpperCase() + chamber.substring(1);
    }

    public String getBirthday(){
        if(birthday != null && !birthday.isEmpty())
            return birthday;
        else
            return "N.A";
    }

    public String getFax(){
        if(fax != null && !fax.isEmpty())
            return fax;
        else
            return "N.A";
    }

    public String getPhone(){
        if(phone != null && !phone.isEmpty())
            return phone;
        else
            return "N.A";
    }

    public String getOc_email(){
        if(oc_email != null && !oc_email.isEmpty())
            return oc_email;
        else
            return "N.A";
    }

    public String getState(){
        return state;
    }

    public String getFormatedStartTerm(){
        return getFormatedTime(term_start);
    }

    public String getFormatedEndTerm(){
        return getFormatedTime(term_end);
    }

    public String getFormatedBirthday(){
        return getFormatedTime(birthday);
    }

    public boolean getisFavorite(){
        return isFavorite;
    }

    public void setisFavorite(boolean favorite){
        isFavorite = favorite;
    }

    public int getTermProgress(){
        try {
            Date startTime = new SimpleDateFormat("yyyy-MM-dd").parse(term_start);
            Date endTime = new SimpleDateFormat("yyyy-MM-dd").parse(term_end);
            long start_time = startTime.getTime();
            long end_time = endTime.getTime();
            double value = ((double)(System.currentTimeMillis() - start_time) / (double)(end_time - start_time)) * 100;
            int termProgress = (int) Math.round(value);
            return termProgress;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getFormatedTime(String formatTime){
        try {
            Calendar introDate = new GregorianCalendar();
            Date introTime = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(formatTime);
            introDate.setTime(introTime);
            String introducedOn = new SimpleDateFormat("MMM").format(introDate.getTime()) + " " + introDate.get(Calendar.DAY_OF_MONTH) + ", " + introDate.get(Calendar.YEAR);
            return introducedOn;
        } catch (ParseException e) {
            e.printStackTrace();
            return formatTime;
        }
    }
}
