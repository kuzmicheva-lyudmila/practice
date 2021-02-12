package ru.kl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrentModificationExceptionExample {

    public static void main(String[] args) {
        //failFastIterator();
        //failSaveIterator();
        failSaveWithoutCopy();
    }

    private static void failSaveWithoutCopy() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("ONE", 1);
        map.put("TWO", 2);
        map.put("THREE", 3);
        map.put("FOUR", 4);

        // Getting an Iterator from map
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            System.out.println(key + " : " + map.get(key));

            // This will reflect in iterator. Hence, it has not created separate copy
            map.put("SEVEN", 7);
        }
    }

    private static void failFastIterator() {
        List<String> arr = new ArrayList<>();
        arr.add("One");
        arr.add("Two");
        arr.add("Three");
        arr.add("Four");

        try {
            System.out.println("ArrayList: ");
            Iterator<String> iter = arr.iterator();
            while (iter.hasNext()) {
                System.out.print(iter.next() + ", ");
                iter.remove();  // without error

                arr.add("Five");
                arr.remove(2);  // generate error, because collection
            }
            System.out.println(arr);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void failSaveIterator() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(new Integer[]{1, 3, 5, 8});
        Iterator itr = list.iterator();
        while (itr.hasNext()) {
            Integer no = (Integer) itr.next();
            System.out.println(no);
            if (no == 8) {
                list.add(14); // This will not print, hence it has created separate copy
            }
        }
        System.out.println(list);
    }
}
