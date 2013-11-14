package com.fantuan;

import com.fantuan.model.FanTuan;
import com.fantuan.model.HistoryItem;
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
        if (data != null && !data.isEmpty())
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

    public ArrayList<HistoryItem> getHistoryList() {
        return mFanTuan.history;
    }

    public void addNewPerson(String name) {
        Person p = new Person();
        p.name = name;
        p.current = 0.0;
        mFanTuan.persons.add(p);
        addHistoryItem(mContext.getString(R.string.history_add_person, name));
       
        save();
        notifyAllObservers();
    }

    public int suggestWhoPay(String[] names) {
        int result = 0;
        double minCurrent = Double.MAX_VALUE;
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            for (Person p: mFanTuan.persons) {
                if (name.equals(p.name) && p.current < minCurrent) {
                    minCurrent = p.current;
                    result = i;
                    break;
                }
            }
        }
        return result;
    }

    private Person findPersonByNameOrAddNew(String name) {
        for (Person p: mFanTuan.persons) {
            if (p.name.equals(name))
                return p;
        }
        Person p = new Person();
        p.name = name;
        mFanTuan.persons.add(p);
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

    public ArrayList<Person> generatePersonList(int count, double current) {
        String[] names = new String[count];
        for (int i = 0; i < count; i++) 
            names[i] = mContext.getString(R.string.gen_new_name, i + 1);
        return generatePersonList(names, 0, current);
    }

    public void mergePersonList(ArrayList<Person> list) {
        for (Person person: list) {
            Person p = findPersonByNameOrAddNew(person.name);
            p.current += person.current;
        }
        save();
        notifyAllObservers();
    }

    private void addHistoryItem(String content) {
        HistoryItem item = new HistoryItem();
        item.time = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM)
                .format(new Date());
        item.content = content;
        mFanTuan.history.add(item);
    }

    public void modifyName(Person p, String name) {
        addHistoryItem(mContext.getString(R.string.history_modify, p.name, name));
        if (!name.equals(p.name))
            p.needRename = false;
        p.name = name;
        save();
        notifyAllObservers();
    }

    public void removePerson(Person p) {
        mFanTuan.persons.remove(p);
        if (mFanTuan.persons.size() > 0) {
            double dCurrent = -p.current / mFanTuan.persons.size();
            for (Person person: mFanTuan.persons)
                person.current -= dCurrent;
        }
        addHistoryItem(mContext.getString(R.string.history_delete, p.name, p.current));
        save();
        notifyAllObservers();
    }

    public void clearHistory() {
        mFanTuan.history.clear();
        save();
        notifyAllObservers();
    }
}
