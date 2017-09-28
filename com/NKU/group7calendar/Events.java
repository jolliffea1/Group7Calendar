package group7calendar;

public class Events {
    int month, day, year;
    String descrip, start_time, end_time;

    public void Events(){
        month=0;
        day = 0;
        year = 0;
        descrip = "";
        start_time = "";
        end_time = "";
    }
    public void setEventMonth(int a){
        month = a;
    }
    public void setEventDay(int a){
        day = a;
    }
    public void setEventYear(int a){
        year = a;
    }
    public void setEventDescrip(String a){
        descrip = a;
    }
    public void setEventStartTime(String a){
        start_time = a;
    }
    public void setEventEndTime(String a){
        end_time = a;
    }
    public int getEventMonth(){
        return month;
    }
    public int getEventDay(){
        return day;
    }
    public int getEventYear(){
        return year;
    }
    public String getEventDescrip(){
        return descrip;
    }
    public String getEventStartTime(){
        return start_time;
    }
    public String getEventEndTime(){
        return end_time;
    }
    public String printEvent(){
        return Integer.toString(month)+"/"+Integer.toString(day)+"/"+Integer.toString(year)+" start: "+ start_time+
                " end: " + end_time + " description: " + descrip;
    }

}
