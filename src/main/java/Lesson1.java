/**
 * Copyright © 2014, Oracle and/or its affiliates. All rights reserved.
 * <p>
 * JDK 8 MOOC Lesson 1 homework
 */

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Speakjava (simon.ritter@oracle.com)
 */
public class Lesson1 {
    /**
     * Run the exercises to ensure we got the right answers
     */
    public void runExercises() {
        System.out.println("JDK 8 Lambdas and Streams MOOC Lesson 1");
        System.out.println("Running exercise 1 solution...");
        exercise1();
        System.out.println("Running exercise 2 solution...");
        exercise2();
        System.out.println("Running exercise 3 solution...");
        exercise3();
        System.out.println("Running exercise 4 solution...");
        exercise4();
        System.out.println("Running exercise 5 solution...");
        exercise5();
    }

    /**
     * All exercises should be completed using Lambda expressions and the new
     * methods added to JDK 8 where appropriate. There is no need to use an
     * explicit loop in any of the code. Use method references rather than full
     * lambda expressions wherever possible.
     */
    /**
     * Exercise 1
     * <p>
     * Create a string that consists of the first letter of each word in the list of Strings provided.
     */
    private void exercise1() {
        List<String> list = Arrays.asList(
                "alpha", "bravo", "charlie", "delta", "echo", "foxtrot");

        final String result = list.stream().map(item -> item.substring(0, 1)).collect(Collectors.joining());
        System.out.println(result);
    }

    /**
     * Exercise 2
     * <p>
     * Remove the words that have odd lengths from the list.
     */
    private void exercise2() {
        List<String> list = new ArrayList<>(Arrays.asList(
                "alpha", "bravo", "charlie", "delta", "echo", "foxtrot"));

        list.removeIf(item -> item.length() % 2 == 1);
        System.out.println(list);
    }

    /**
     * Exercise 3
     * <p>
     * Replace every word in the list with its upper case equivalent.
     */
    private void exercise3() {
        List<String> list = new ArrayList<>(Arrays.asList(
                "alpha", "bravo", "charlie", "delta", "echo", "foxtrot"));

        list.replaceAll(item -> item.substring(0, 1).toUpperCase() + item.substring(1));
        System.out.println(list);
    }

    /**
     * Exercise 4
     * <p>
     * Convert every key-value pair of the map into a string and append them all into a single string, in iteration
     * order.
     */
    private void exercise4() {
        Map<String, Integer> map = new TreeMap<>();
        map.put("c", 3);
        map.put("b", 2);
        map.put("a", 1);

        final String result = map.entrySet().stream()
                .map(entry -> entry.getKey() + entry.getValue())
                .collect(Collectors.joining());
        System.out.println(result);
    }

    /**
     * Exercise 5
     * <p>
     * Create a new thread that prints the numbers from the list.
     */
    private void exercise5() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        final Thread thread = new Thread(() -> list.forEach(System.out::println));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            thread.interrupt();
        }
    }

    /**
     * Main entry point for application
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Lesson1 lesson = new Lesson1();
        lesson.runExercises();
    }
}
