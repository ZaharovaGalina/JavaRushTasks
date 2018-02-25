package com.javarush.task.task36.task3605;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.lang.Character.isLetter;

/* 
Использование TreeSet
*/
public class Solution {
    public static void main(String[] args) throws IOException {
       SortedSet<Character> stringSet = new TreeSet<>();

       // FileReader fr = new FileReader(new File("C:\\Users\\Smart\\Desktop\\work\\2.txt"));
        FileReader fr = new FileReader(new File(args[0]));
        int ch;
        while ((ch = fr.read()) != -1) {
            if (isLetter((char)ch))
            stringSet.add(Character.toLowerCase((char)ch));
        }

       Object[] arr = stringSet.stream().limit(5).toArray();
        for(Object o : arr)
       System.out.print(o);

    }
}
