package ru.kl.linkedlist;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;

public class LinkedListExample {

    public static void fillLinkedList(LinkedList<Integer> source, int position, int... copyElements) {
        checkParameters(source, copyElements, position);

        ListIterator<Integer> litr = source.listIterator(position);
        for (int copyElement : copyElements) {
            litr.add(copyElement);
        }
    }

    private static void checkParameters(LinkedList<Integer> source, int[] copyElements, int position) {
        checkNotNull("source", source);
        checkNotNull("copyElements", copyElements);

        if (position < 0 || position > source.size()) {
            throw new IllegalArgumentException("Position index out of list bounds");
        }
    }

    private static void checkNotNull(String parameter, Object value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException(String.format("The '%s' parameter is null", parameter));
        }
    }
}
