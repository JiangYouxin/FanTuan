package com.fantuan;

import com.fantuan.model.FanTuan;
import com.fantuan.model.NewHistoryItem;
import com.fantuan.model.Person;

import android.content.Context;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import com.googlecode.androidannotations.annotations.*; 
import com.googlecode.androidannotations.annotations.sharedpreferences.*; 
import com.googlecode.androidannotations.api.Scope;

@EBean(scope = Scope.Singleton)
public class FanTuanManager {
    public interface Observer {
        void onModelChanged();
    }

    @RootContext
    Context mContext;

    @Pref
    MainPref_ mPref;

    private FanTuan mFanTuan;
    private ArrayList<Observer> mObservers;
    private Gson gson = new Gson();

    @AfterInject
    void init() {
        String data = mPref.data().get();
        if (data != null && data.length() > 0)
            mFanTuan = gson.fromJson(data, FanTuan.class);
        else
            mFanTuan = new FanTuan();
        mObservers = new ArrayList<Observer>();
    }

    @Background
    void save() {
        String data = gson.toJson(mFanTuan);
        mPref.edit().data().put(data).apply();
    }

    private void notifyAllObservers() {
        for (Observer ob: mObservers)
            ob.onModelChanged();
    }

    public void registerObserver(Observer ob) {
        mObservers.add(ob);
    }

    public void unregisterObserver(Observer ob) {
        mObservers.remove(ob);
    }

    public ArrayList<Person> getPersonList() {
        return mFanTuan.persons;
    }

    public ArrayList<NewHistoryItem> getHistoryList() {
        return mFanTuan.newHistory;
    }

    public int suggestWhoPay(String[] names) {
        int result = 0;
        double minCurrent = Double.MAX_VALUE;
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            double current = getCurrentByName(name);
            if (current < minCurrent) {
                minCurrent = current;
                result = i;
            }
        }
        return result;
    }

    public double getCurrentByName(String name) {
        for (Person p: mFanTuan.persons) {
            if (name.equals(p.name))
                return p.current;
        }
        return 0;
    }

    public boolean nameExists(String name) {
        return findPersonByName(name) != null;
    }

    private Person findPersonByName(String name) {
        for (Person p: mFanTuan.persons) {
            if (p.name.equals(name))
                return p;
        }
        return null;
    }

    private Person findPersonByNameOrAddNew(String name, boolean genName) {
        Person p = findPersonByName(name);
        if (p == null) {
            p = new Person();
            p.name = name;
            p.needRename = genName;
            mFanTuan.persons.add(p);
        }
        return p;
    }

    public ArrayList<Person> generatePersonList(String [] names, int whoPay, double current) {
        ArrayList<Person> list = new ArrayList<Person>();
        double perPerson = current / names.length;
        for (int i = 0; i < names.length; i++) {
            Person p = new Person();
            p.name = names[i]; 
            p.current = -perPerson;
            if (i == whoPay)
                p.current += current;
            list.add(p);
        }
        return list;
    }

    public String[] generateNames(int count) {
        String[] names = new String[count];
        names[0] = mContext.getString(R.string.gen_new_name_0);
        for (int i = 1; i < count; i++) 
            names[i] = mContext.getString(R.string.gen_new_name, i);
        return names;
    }

    public void mergePersonList(ArrayList<Person> list, boolean genName) {
        for (Person person: list) {
            Person p = findPersonByNameOrAddNew(person.name, genName);
            p.current += person.current;
        }
        NewHistoryItem item = new NewHistoryItem();
        item.persons = list;
        item.time = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM)
                .format(new Date());
        mFanTuan.newHistory.add(item);
        save();
        notifyAllObservers();
    }

    public void modifyName(Person p, String name) {
        if (!name.equals(p.name))
            p.needRename = false;
        p.name = name;
        save();
        notifyAllObservers();
    }

    public void removePerson(String name) {
        Person p = findPersonByName(name);
        if (p != null) {
            mFanTuan.persons.remove(p);
            save();
            notifyAllObservers();
        }
    }

    public void clearHistory() {
        mFanTuan.newHistory.clear();
        save();
        notifyAllObservers();
    }

    public void clearAll() {
        mFanTuan.newHistory.clear();
        mFanTuan.persons.clear();
        save();
        notifyAllObservers();
    }
}
