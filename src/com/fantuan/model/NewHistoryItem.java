package com.fantuan.model;

import java.util.ArrayList;

public class NewHistoryItem {
    public String time;
    public ArrayList<Person> persons;

    public NewHistoryItem() {
        persons = new ArrayList<Person>();
    }

    public double getCurrent() {
        double current = persons.get(0).current;
        int count = persons.size();
        if (current > 0)
            current = current / (count - 1) * count;
        else
            current = -current * count;
        return current;
    }

    public String whoPay() {
        String who = null;
        double current = Double.MIN_VALUE;
        for (Person p: persons) {
            if (p.current > current) {
                current = p.current;
                who = p.name;
            }
        }
        return who;
    }
}
