package org.example;

import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {

            Thread t = new Thread(() -> {
                String text = generateRoute("RLRFR", 100);
                int count = 0;
                for (int j = 0; j < text.length(); j++) {
                    if (text.charAt(j) == 'R') {
                        count++;
                    }
                }
                System.out.println(Thread.currentThread().getId() + ": R count is " + count + "\n");
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(count)) {
                        Integer n = sizeToFreq.get(count);
                        sizeToFreq.put(count, n + 1);

                    } else {
                        sizeToFreq.put(count, 1);
                    }
                }

            });
            t.start();
            threadList.add(t);
        }
        for (Thread t : threadList) {
            t.join();
        }
        int max_count = 0;
        int max = 0;
        for (Integer i : sizeToFreq.keySet()) {
            if (sizeToFreq.get(i) > max_count) {
                max_count = sizeToFreq.get(i);
                max = i;
            }
        }
        System.out.println("Самое частое количество повторений " + max + "(встретилось " + sizeToFreq.get(max) + " раз)" + "\n");
        System.out.println("Другие размеры: " + "\n");
        for (Integer i : sizeToFreq.keySet()) {
            if (i != max) {
                System.out.println("-" + i + "(" + sizeToFreq.get(i) + " раз)");
            }
        }


    }
}