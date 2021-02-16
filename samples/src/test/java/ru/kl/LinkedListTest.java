package ru.kl;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.kl.linkedlist.LinkedListExample.fillLinkedList;

class LinkedListTest {

    @Test
    void fillLinkedListInsertAtFirstPositionTest() {
        LinkedList<Integer> list = new LinkedList<>();

        fillLinkedList(list, 0, 3, 6, 8);
        assertEquals("[3, 6, 8]", list.toString());
    }

    @Test
    void fillLinkedListInsertAtMiddlePositionTest() {
        LinkedList<Integer> list = new LinkedList<>(Arrays.asList(2, 5, 6));

        fillLinkedList(list, 1, 3, 6, 8);
        assertEquals("[2, 3, 6, 8, 5, 6]", list.toString());
    }

    @Test
    void fillLinkedListInsertAtLastPositionTest() {
        LinkedList<Integer> list = new LinkedList<>(Arrays.asList(2, 5, 6));

        fillLinkedList(list, 3, 3, 6, 8);
        assertEquals("[2, 5, 6, 3, 6, 8]", list.toString());
    }

    @Test
    void fillEmptyLinkedListTest() {
        assertThrows(IllegalArgumentException.class, () -> fillLinkedList(null, 0, 0));
    }

    @Test
    void fillLinkedListWithOutOfBoundsPosition() {
        LinkedList<Integer> list = new LinkedList<>(Arrays.asList(2, 5, 6));

        assertThrows(IllegalArgumentException.class, () -> fillLinkedList(list, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> fillLinkedList(list, 4, 0));
    }
}
