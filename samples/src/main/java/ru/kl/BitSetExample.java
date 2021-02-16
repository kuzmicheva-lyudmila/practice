package ru.kl;

import java.util.Arrays;
import java.util.BitSet;

public class BitSetExample {

    public static void main(String[] args) {
        BitSet bitSet = new BitSet();
        bitSet.set(0);
        bitSet.set(1);  // zero-based
        System.out.println(bitSet);
        System.out.println(bitSet.size());

        long[] longs = bitSet.toLongArray();
        System.out.println(Arrays.toString(longs));

        BitSet second = new BitSet();
        second.set(7, 15);

        bitSet.and(second);
        System.out.println(bitSet);

        bitSet.stream().forEach(System.out::println);
    }
}
