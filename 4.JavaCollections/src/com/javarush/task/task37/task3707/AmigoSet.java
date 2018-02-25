package com.javarush.task.task37.task3707;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;


public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E> {

    private static final Object PRESENT = new Object();
    private transient HashMap<E, Object> map;

    public AmigoSet() {
        map = new HashMap<E, Object>();
    }

    public AmigoSet(Collection<? extends E> collection){
        map = new HashMap<>((int)Math.max(16,Math.ceil(collection.size()/.75f)));
        this.addAll(collection);
    }

    @Override
    public Spliterator spliterator() {
        return null;
    }


    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean remove(Object o) {
        return map.keySet().remove(o);
    }


    @Override
    public boolean add(E e) {
        return null == map.put(e,PRESENT);
    }

    @Override
    public Object clone() {
        try {
            AmigoSet copy = (AmigoSet)super.clone();
            copy.map = (HashMap) map.clone();
            return copy;
        }
        catch (Exception e) {
            throw new InternalError();
        }
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(map.size());
        for (E e : map.keySet()) {
            stream.writeObject(e);
        }

        stream.writeObject(HashMapReflectionHelper.callHiddenMethod(map, "capacity"));
        stream.writeObject(HashMapReflectionHelper.callHiddenMethod(map,"loadFactor"));

    }
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {

        stream.defaultReadObject();
        int size = (int) stream.readObject();
        Set<E> set = new HashSet<>();
        for (int i = 0; i < size; i++)
        {
            set.add((E) stream.readObject());
        }
        int capacity  = (int) stream.readObject();
        float loadFactor = (float) stream.readObject();
        map = new HashMap<>(capacity,loadFactor);
        for (E e : set) {
            map.put(e,PRESENT);
        }


    }

}
