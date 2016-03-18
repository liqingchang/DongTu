package com.jellyape.dongtu.data;

/**
 * Created by kuroterry on 16/2/20.
 */
public interface ISubject {

    public void addObserver(IObserver iObserver);

    public void removeObserver(IObserver iObserver);

    public void notifyUpdate();
}
