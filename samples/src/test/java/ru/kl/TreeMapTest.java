package ru.kl;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TreeMapTest {

    private static final String MAP_VALUE = null;

    @Test
    void testCheckTreeMapOnNullKey() {
        Map<String, String> map = new TreeMap<>();
        assertThrows(NullPointerException.class, () -> map.put(null, MAP_VALUE));

        Map<String, String> mapWithNullKey = new TreeMap<>(Comparator.nullsLast(Comparator.naturalOrder()));
        mapWithNullKey.put(null, MAP_VALUE);
        assertEquals(MAP_VALUE, mapWithNullKey.get(null));
    }
}
