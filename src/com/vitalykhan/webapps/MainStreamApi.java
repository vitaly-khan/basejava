package com.vitalykhan.webapps;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreamApi {
    public static void main(String[] args) {
        int[] array1 = new int[]{1, 3, 2, 3, 3, 2};
        int[] array2 = new int[]{9, 8, 9};
        System.out.println(minValue(array1));
        System.out.println(oddOrEven(Arrays.asList(3, 4, 5, 6, 7, 8, 9)));

        String str = Arrays.stream(array1)
                .mapToObj(String::valueOf)
                .reduce("", String::concat);
        System.out.println(str);

    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce((x, y) -> x * 10 + y)
                .getAsInt();
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {

        int sum = integers.stream()
                .mapToInt(Integer::intValue)
                .sum();
        return integers.stream()
                .filter(x -> (x % 2 != sum % 2))  //thanks to Tanya Poliakh for this idea
                .collect(Collectors.toList());
    }
}
