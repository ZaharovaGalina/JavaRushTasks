package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.*;

/*
Построй дерево(4)
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {

    Entry<String> root;
    public Map<String, Entry> entries = new LinkedHashMap<>();

    public static void main(String[] args) {
        CustomTree list = new CustomTree();
        for (int i = 1; i < 16; i++) {
            list.add(String.valueOf(i));
        }
        System.out.println("Expected 3, actual is " + ((com.javarush.task.task20.task2028.CustomTree) list).getParent("8"));
        //list.remove("5");
        System.out.println("Expected null, actual is " + ((com.javarush.task.task20.task2028.CustomTree) list).getParent("11"));


        System.out.println(list.size());
        list.remove("2");
        System.out.println(list.size());
    }

    public CustomTree() {
        this.root = new Entry<>("0");
        this.root.lineNumber = 0;
        entries.put("0", this.root);
    }

    @Override
    public boolean add(String s) {

        Entry newEntry = new Entry(s);
        entries.put(s, newEntry);

        int lineNumber = 0;
        boolean success = false;

        while (success == false)
        {
            for (Map.Entry<String, Entry> rowEntry : entries.entrySet())
            {
                Entry ent = rowEntry.getValue();
                if (lineNumber != ent.lineNumber) continue;
                if (ent == newEntry) continue;
                ent.checkChildren();
                if (ent.isAvailableToAddChildren() == false) continue;

                if (ent.availableToAddLeftChildren) ent.leftChild = newEntry;
                else ent.rightChild = newEntry;

                newEntry.parent = ent;
                newEntry.lineNumber = ent.lineNumber+1;
                success = true;
                break;
            }
            lineNumber++;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!entries.containsKey(o)) return false;
        com.javarush.task.task20.task2028.CustomTree.Entry entry = entries.get(o);
        entry.parent = null;
        entries.remove(o);

        entry.checkChildren();

        if (entry.availableToAddLeftChildren == false) this.remove(entry.leftChild.elementName);
        if (entry.availableToAddRightChildren == false) this.remove(entry.rightChild.elementName);
        return true;
    }


    public String getParent(String s) {
        if (!entries.containsKey(s)) return null;
        Entry entry = entries.get(s);
        return entries.get(s).parent.elementName;
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return entries.size() - 1;
    }

    static class Entry<T> implements Serializable{

        String elementName;
        int lineNumber;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        com.javarush.task.task20.task2028.CustomTree.Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            this.availableToAddLeftChildren = true;
            this.availableToAddRightChildren = true;
        }

        public void checkChildren()
        {
            if (leftChild != null) availableToAddLeftChildren = false;
            if (rightChild != null) availableToAddRightChildren = false;
        }

        public boolean isAvailableToAddChildren()
        {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }

        @Override
        public String toString() {
            return "EN:"+elementName+";LN:"+lineNumber+";PEN:"+(parent != null ? parent.elementName : "null");
        }
    }
}