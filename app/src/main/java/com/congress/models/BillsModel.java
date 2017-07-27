package com.congress.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class BillsModel implements Serializable{

    private String bill_id;
    private String bill_type;
    private String chamber;
    private String official_title;
    private String short_title;
    private String introduced_on;
    private Sponsor sponsor;
    private History history;
    private LastVersion last_version;
    private CongressUrl urls;
    private boolean isFavorite = false;

    public String getBill_id(){
        return bill_id;
    }

    public String getBill_type(){
        return bill_type.toUpperCase();
    }

    public String getChamber(){
        return chamber.substring(0, 1).toUpperCase() + chamber.substring(1);
    }

    public String getShort_title(){
        if(short_title != null && !short_title.isEmpty())
            return short_title;
        else
            return "N.A";
    }

    public String getIntroduced_on(){
        return getFormatedTime(introduced_on);
    }

    public String getOfficial_title(){
        return official_title;
    }

    public String getSponsorName(){
        return sponsor.getSponsorName();
    }

    public Long getIntroducedOnLong()
    {
        return getFormatedTimeLong(introduced_on);
    }
    public String getStatus(){
        return history.getStatus();
    }

    public String getCongressUrl(){
        return urls.getCongress();
    }

    public String getPdfUrl(){
        return last_version.getPdf();
    }

    public String getVersion_name(){
        return last_version.getVersion_name();
    }

    public boolean getisFavorite(){
        return isFavorite;
    }

    public void setisFavorite(boolean favorite){
        isFavorite = favorite;
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

    public Long getFormatedTimeLong(String formatTime){
        try {
            Date introTime = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(formatTime);
            return introTime.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return Long.valueOf(0);
        }
    }
}

class Sponsor implements Serializable{
    private String first_name;
    private String last_name;
    private String title;

    public String getSponsorName(){
        return title + ". " + last_name + ", " + first_name;
    }
}

class History implements Serializable{
    private boolean active;

    public String getStatus(){
        if(active)
            return "Active";
        else
            return "New";
    }
}

class LastVersion implements Serializable{
    private String version_name;
    private PdfUrl urls;

    public String getVersion_name(){
        if(version_name != null && !version_name.isEmpty())
            return version_name;
        else
            return "N.A";
    }

    public String getPdf(){

        return urls.getPdf();
    }
}

class PdfUrl implements Serializable{
    private String pdf;

    public String getPdf(){
        if(pdf != null && !pdf.isEmpty())
            return pdf;
        else
            return "N.A";
    }
}

class CongressUrl implements Serializable{
    private String congress;

    public String getCongress(){
        if(congress != null && !congress.isEmpty())
            return congress;
        else
            return "N.A";
    }
}