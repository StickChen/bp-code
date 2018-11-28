package com.shallop.bpc.collection.basic.jdk8;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @since v2.2.0
 * Created by nkcoder on 1/15/16.
 */
public class Lambda {

    public void testLambda() {
        String[] cities = {"Beijing", "Tianjin", "Chengdu", "Shanghai"};

        // use expression lambda
        Comparator<String> comp1 = (first, second) -> Integer.compare(first.length(), second.length());
        Arrays.sort(cities, comp1);
        Arrays.sort(cities, (first, second) -> Integer.compare(first.length(), second.length()));

        // use statement lambda
        Comparator<String> comp2 = (first, second) -> { return Integer.compare(first.length(), second.length());};
        Arrays.sort(cities, comp2);
        // statement lambda with multiple lines
        Arrays.sort(cities, (first, second) -> {
            if (first.length() > second.length()) {
                return 1;
            } else if (first.length() == second.length()) {
                return 0;
            } else {
                return -1;
            }
        });

        Arrays.sort(cities, Comparator.comparingInt(String::length));

        for (String city: cities) {
            System.out.println(city);
        }

        // use lambda
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i ++) {
                System.out.println("in t1, i = " + i);
            }
        });
        t1.start();
    }

    public void testFunctionalInterface() {
        // use function reference

        Runnable r = () -> {
            System.out.println("------");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // catch exception
            }
        };

        Callable<String> c = () -> {
            System.out.println("--------");
            Thread.sleep(10);
            return "";
        };

        String[] teamNames = {"Lakers", "Heat", "Warriors"};
        Arrays.sort(teamNames, String::compareToIgnoreCase);
        Arrays.sort(teamNames, (s1, s2) -> s1.compareToIgnoreCase(s2));

    }

    public void testVariableScope(String text, int count) {
        Runnable r = () -> {
            for (int i = 0; i < count; i ++) {
                System.out.println("text" + i);
                Thread.yield();
            }
        };
        new Thread(r).start();
    }

    public void listDirs() {
        File rootDir = new File("/Users/nkcoder");
        File[] files1 = rootDir.listFiles((f) -> f.isDirectory());
        for (File file: files1) {
            System.out.println("file: " + file.getName());
        }

        File[] files2 = rootDir.listFiles(File::isDirectory);
        for (File file: files2) {
            System.out.println("file: " + file.getName());
        }

        List<String> nameList = new ArrayList<>();
        nameList.add("tianjin");
        nameList.add("beijing");
        nameList.forEach((ele) -> System.out.println(ele));
        nameList.removeIf((x) -> x.equalsIgnoreCase("tianjin"));
        nameList.forEach(System.out::println);

    }




    public static void main(String[] args) {
        Lambda lambda = new Lambda();

//        lambda.testLambda();
        lambda.listDirs();
    }
}
