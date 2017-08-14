package com.itbackyard.Helper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Wet-extractor
 * Developer Maytham on 08-08-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class Statistic {

    public void wordStatistic(List<String> list) {
        Map<String, Integer> counts = list.parallelStream()
                .flatMap(elm -> Arrays.stream(elm.split(" ")))
                .collect(Collectors.toConcurrentMap(w -> w, w -> 1, Integer::sum));
        System.out.println(counts);
    }

}
