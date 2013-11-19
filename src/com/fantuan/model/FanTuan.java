package com.fantuan.model;

import java.util.ArrayList;

public class FanTuan {
    public FanTuan() {
        persons = new ArrayList<Person>();
        newHistory = new ArrayList<NewHistoryItem>();
    }
    public ArrayList<Person> persons;
    public ArrayList<NewHistoryItem> newHistory;
}
