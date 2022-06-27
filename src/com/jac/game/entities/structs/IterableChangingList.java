package com.jac.game.entities.structs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/** An array list class that can be added to while iterating through it.
 * Works by adding and removing at the end of the iteration.
 */
public class IterableChangingList<T> implements Iterable<T>{

    protected ArrayList<T> contents;
    protected ArrayList<T> toAdd;
    protected ArrayList<T> toRemove;

    public IterableChangingList(){
        contents = new ArrayList<T>();
        toAdd = new ArrayList<T>();
        toRemove = new ArrayList<T>();
    }

    public void remove(T obj){
        toRemove.add(obj);
    }

    public void add(T obj){
        toAdd.add(obj);
    }

    public T get(int i){
        return contents.get(i);
    }

    public boolean contains(T obj){
        return contents.contains(obj);
    }

    public void updateContents(){
        for(T obj : toRemove){
            contents.remove(obj);
        }
        for(T obj : toAdd){
            contents.add(obj);
        }
        toAdd.clear();
        toRemove.clear();
    }

    public int size(){
        return contents.size();
    }

    @Override
    public Iterator iterator() {
        return contents.iterator();
    }

    public ArrayList<T> getAdding(){
        return toAdd;
    }

    public void clear(){
        contents.clear();
        toAdd.clear();
        toRemove.clear();
    }

    public void sort(Comparator comparator){
        contents.sort(comparator);
    }

}
