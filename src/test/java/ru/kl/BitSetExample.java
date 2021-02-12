package ru.kl;

import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.assertj.core.api.Assertions.assertThat;

class BitSetExample {

    @Test
    void bitSetTest() {
        BitSet bitSet = new BitSet();
        bitSet.set(10);
        assertThat(bitSet.get(10)).isTrue();

        bitSet.set(20, 30);
        for (int i = 20; i <= 29; i++) {
            assertThat(bitSet.get(i)).isTrue();
        }
        assertThat(bitSet.get(30)).isFalse();

        bitSet.clear(42);
        assertThat(bitSet.get(42)).isFalse();

        bitSet.clear();

        bitSet.set(42);
        bitSet.flip(42);
        assertThat(bitSet.get(42)).isFalse();

        byte[] bytes = bitSet.toByteArray();
        long[] longs = bitSet.toLongArray();
    }

    @Test
    void logicalOperationTest() {
        BitSet first = new BitSet();
        first.set(5, 10);

        BitSet second = new BitSet();
        second.set(7, 15);

        assertThat(first.intersects(second)).isTrue();

        first.and(second);
        assertThat(first.get(7)).isTrue();
        assertThat(first.get(10)).isFalse();

        first.stream().forEach(System.out::print);
    }
}
