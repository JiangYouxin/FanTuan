package com.fantuan;

import com.fantuan.model.FanTuan;
import com.fantuan.model.NewHistoryItem;
import com.fantuan.model.Person;
import com.fantuan.model.PersonHistoryItem;

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

    public ArrayList<PersonHistoryItem> generatePersonHistoryList(String name) {
        ArrayList<PersonHistoryItem> list = new ArrayList<PersonHistoryItem>();
        for (int i = mFanTuan.newHistory.size() - 1; i >= 0; i--) {
            NewHistoryItem item = mFanTuan.newHistory.get(i);
            for (Person p: item.persons) {
                if (p.name.equals(name)) {
                    PersonHistoryItem hItem = new PersonHistoryItem();
                    hItem.historyId = i;
                    hItem.time = item.time;
                    hItem.current = p.current;
                    list.add(hItem);
                }
            }
        }
        return list;
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

    public ArrayList<Person> generatePersonList(String [] names, double current) {
        ArrayList<Person> list = new ArrayList<Person>();
        double perPerson = current / 2;
        Person p = new Person();
        p.name = names[0];
        p.current = -perPerson;
        list.add(p);
        p = new Person();
        p.name = names[1]; 
        p.current = perPerson;
        list.add(p);
        return list;
    }

    public ArrayList<Person> generatePersonList(String [] names, int whoPay, double current) {
        ArrayList<Person> list = new ArrayList<Person>();
        double perPerson = current / names.length;
        for (int i = 0; i < names.length; i++) {
            if (i == whoPay) {
                Person p = new Person();
                p.name = names[i];
                p.current = current;
                list.add(p);
            }
            Person p = new Person();
            p.name = names[i]; 
            p.current = -perPerson;
            list.add(p);
        }
        return list;
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
        String oldName = p.name;
        p.name = name;
        for (NewHistoryItem item: mFanTuan.newHistory) {
            for (Person person: item.persons) {
                if (person.name.equals(oldName))
                    person.name = name;
            }
        }
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
        mPref.edit().lastSelected().put(null).apply();
        save();
        notifyAllObservers();
    }
}
