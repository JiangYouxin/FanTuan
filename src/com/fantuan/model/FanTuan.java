package com.fantuan.model;

import java.util.ArrayList;

public class FanTuan {
    public FanTuan() {
        persons = new ArrayList<Person>();
        history = new ArrayList<HistoryItem>();
    }
    public ArrayList<Person> persons;
    public ArrayList<HistoryItem> history;
}
