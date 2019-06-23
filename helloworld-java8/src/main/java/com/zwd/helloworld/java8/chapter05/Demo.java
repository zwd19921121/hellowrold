package com.zwd.helloworld.java8.chapter05;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Demo {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("hello","world");
        List<String[]> collect = words.stream()
                .map(world -> world.split(" "))
                .distinct()
                .collect(Collectors.toList());


        List<Stream<String>> collect1 = words.stream()
                .map(word -> word.split(" "))
                .map(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        List<String> collect2 = words.stream().map(w -> w.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        System.out.println(collect2);

        List<Integer> numbers = Arrays.asList(1,2,3,4,5);
        List<Integer> squares = numbers.stream().map(n -> n * n)
                .collect(Collectors.toList());

        List<Integer> numbers1 = Arrays.asList(1,2,3);
        List<Integer> numbers2 = Arrays.asList(3,4);
        numbers.stream()
                .flatMap(i -> numbers2.stream()
                .map(j -> new int[]{i,j}))
                .collect(Collectors.toList());

    }
}
