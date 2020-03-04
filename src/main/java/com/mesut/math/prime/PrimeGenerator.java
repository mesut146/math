package com.mesut.math.prime;

import java.io.*;
import java.util.BitSet;

public class PrimeGenerator {

    public static int plen;//prime total
    public static int[] primes;//prime array

    static {
        init();
    }

    //load from in-jar resource
    public static void init() {
        if (primes != null) {
            //already initialized
            return;
        }
        try {
            PrimeGenerator.readFrom(ClassLoader.getSystemClassLoader().getResourceAsStream("primes.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //https://stackoverflow.com/a/1043247/4579125
    public static BitSet computePrimes(int limit) {
        final BitSet primes = new BitSet();
        primes.set(0, false);
        primes.set(1, false);
        primes.set(2, limit, true);
        for (int i = 0; i * i < limit; i++) {
            if (primes.get(i)) {
                System.out.println(i);
                for (int j = i * i; j < limit; j += i) {
                    primes.clear(j);
                }
            }
        }
        System.out.println("total " + primes.length() + " " + primes.size());
        return primes;
    }

    //generate prim numbers up to @limit and write them to @target
    public static void generate(File target, int limit) throws IOException {
        if (limit < 2) {
            return;
        }
        RandomAccessFile raf = new RandomAccessFile(target, "rw");

        if (limit % 2 == 0) {
            limit--;
        }
        int[] arr = new int[limit / 2];
        int len = arr.length;
        arr[0] = 3;
        //fill with odd numbers
        for (int i = 3, index = 0; i < limit; i += 2) {
            if (i % 3 != 0) {
                arr[index++] = i;
            }
        }
        raf.writeInt(0);//dummy integer for arr size,write to beginning of file
        raf.writeInt(2);
        int totalPrimes = 1;

        for (int i = 0; i < len; i++) {
            if (arr[i] != 0) {
                totalPrimes++;
                //raf.writeInt(arr[i]);//todo to cache too
                for (int j = i + 1; j < len; j++) {
                    if (arr[j] != 0 && arr[j] % arr[i] == 0) {
                        arr[j] = 0;
                    }
                }
            }
            if (i % (len / 10) == 0) {
                System.out.println(i + "/" + len);
            }
        }
        //write actual size
        raf.seek(0);
        raf.writeInt(totalPrimes);
        raf.close();
        System.out.println("generated " + totalPrimes + " primes");
    }

    //read @limit amount of primes from @is
    public static void readFrom(InputStream is, int limit) throws IOException {
        DataInputStream dis = new DataInputStream(is);
        plen = Math.min(dis.readInt(), limit);
        primes = new int[plen];
        for (int i = 0; i < plen; i++) {
            primes[i] = dis.readInt();
        }
        //System.out.println("read " + plen + " primes to cache");
    }

    public static void readFrom(InputStream is) throws IOException {
        readFrom(is, Integer.MAX_VALUE);
    }

}
