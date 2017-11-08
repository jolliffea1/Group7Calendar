package com.NKU.group7calendar;

import java.time.Year;

public class MyCalendarGUI extends MyCalendar {

    int currentMonth = 0;
    int currentYear = 0;

    public MyCalendarGUI(int year, int month, int dayOfMonth)
    {
        super(year, month, dayOfMonth);
        currentMonth = month;
        currentYear = year;
    }

    public void nextDay() {

        // if day is at the end of month, change month and reset day
        if (get(DATE) == daysInMonth()) {
            nextMonth();
        }else {
            set(DATE, get(DATE) + 1);
        }
    }

    public void currentMonth() {
        set(MONTH, currentMonth);
        set(YEAR, currentYear);
    }

    public void nextMonth()
    {
        currentMonth = (get(MONTH) + 1) % 12;

        set(DATE, 1);
        set(MONTH, (get(MONTH) + 1) % 12);

        if (get(MONTH) == 0) {
            set(YEAR, get(YEAR) + 1);
            currentYear = get(YEAR) + 1;
        }
    }

    public void previousMonth(){
        set(DATE, 1);

        int month = get(MONTH) - 1;
        currentMonth = month;
        if (month < 0) {
            set(MONTH, 11);
            currentYear = get(YEAR) - 1;
            set(YEAR, get(YEAR) - 1);
        } else {
            set(MONTH, month);
        }

    }
    public int getCurrentMonth()
    {
        return currentMonth;
    }
    public int getCurrentYear()
    {
        return currentYear;
    }
    @Override
    public String toString() {
        return getMonthName() + " " + get(DATE) + ", " + get(YEAR);
    }
}
