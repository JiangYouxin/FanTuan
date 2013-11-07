package com.fantuan;

import com.fantuan.model.FanTuan;
import com.fantuan.model.Person;

import android.content.Context;

import java.util.ArrayList;

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

    public void addNewPerson(String name) {
        Person p = new Person();
        p.name = name;
        p.current = 0.0;
        mFanTuan.persons.add(p);
        save();
        notifyAllObservers();
    }

    public int suggestWhoPay(String[] names) {
        int result = -1;
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

    private Person findPersonByName(String name) {
        for (Person p: mFanTuan.persons) {
            if (p.name.equals(name))
                return p;
        }
        return null;
    }

    public void newDeal(String[] names, int whoPay, double current) {
        double perPerson = current / names.length;
        for (int i = 0; i < names.length; i++) {
            Person p = findPersonByName(names[i]);
            p.current -= perPerson;
            if (i == whoPay)
                p.current += current;
        }
        save();
        notifyAllObservers();
    }
}
