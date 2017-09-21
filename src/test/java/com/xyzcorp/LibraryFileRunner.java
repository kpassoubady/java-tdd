package com.xyzcorp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LibraryFileRunner {
    public static void main(String[] args) {
        InputStream inputStream = LibraryFileRunner
                .class.getResourceAsStream("/library.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Parser parser = new Parser("~");
        Stream<Checkout> checkoutStream = parser.parseStream(bufferedReader.lines());

        PenaltyCalculator penaltyCalculator = new PenaltyCalculator(LocalDate::now);

        List<String> collect = checkoutStream
                .sorted(Comparator.comparing(Checkout::getDate))
                .limit(5)
                .map(co -> co.getPerson().getName() + ":" +
                        penaltyCalculator.calculate(co.getDate()))
                .collect(Collectors.toList());

        System.out.println(collect);
    }
}
