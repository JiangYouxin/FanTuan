package com.fantuan;

import com.fantuan.model.FanTuan;
import com.fantuan.model.Person;

import android.content.Context;

import java.util.ArrayList;

import com.googlecode.androidannotations.annotations.*; 
import com.googlecode.androidannotations.api.Scope;

@EBean(scope = Scope.Singleton)
public class FanTuanManager {
    public interface Observer {
        void onModelChanged();
    }

    @RootContext
    Context mContext;

    private FanTuan mFanTuan;
    private ArrayList<Observer> mObservers;

    @AfterInject
    void init() {
        mFanTuan = new FanTuan();
        mObservers = new ArrayList<Observer>();
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
}
