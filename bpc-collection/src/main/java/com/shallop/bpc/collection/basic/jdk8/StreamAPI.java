package com.shallop.bpc.collection.basic.jdk8;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.*;

/**
 * @since v2.2.0
 * Created by nkcoder on 1/20/16.
 */
public class StreamAPI {

    public void testSteamIntro() {

        // from iterator to stream
        List<String> wordList = Arrays.asList("regular", "expression", "specified", "as", "a", "string",
            "must");
//        int countByIterator = 0;
//        for (String word: wordList) {
//            if (word.length() > 7) {
//                countByIterator++;
//            }
//        }
//        long countByStream= wordList.stream().filter(w -> w.length() > 7).count();
//        long countByParallelStream = wordList.parallelStream().filter(w -> w.length() > 7).count();
//        System.out.println("countByIterator: " + countByIterator + ", countByStream: " + countByStream +
//            ", countByParallelStream: " + countByParallelStream);

    }

    public void testCreateStream() {
        // create stream from array
        Stream<Integer> integerStream = Stream.of(10, 20, 30, 40);
        String[] cityArr = {"Beijing", "Shanghai", "Chengdu"};
        Stream<String> cityStream = Stream.of(cityArr);
        Stream<String> cityStream2 = Arrays.stream(cityArr, 0, 1);
        Stream<String> emptyStream = Stream.empty();

        // create infinite stream
        Stream<String> echos = Stream.generate(() -> "echo");
        Stream<Integer> integers = Stream.iterate(0, num -> num + 1);
        integers.peek(num -> System.out.println(num)).toArray();

        // create by other api
        try (Stream<String> lines = Files.lines(Paths.get("test.txt"))) {

        } catch (Exception e) {

        }
        String content = "AXDBDGXC";
        Stream<String> contentStream = Pattern.compile("[ABC]{1,3}").splitAsStream(content);
        contentStream.peek(line -> System.out.println(line)).toArray();

    }

    public void testStreamTransformation() {
        // filter, map, flatMap
        List<String> langList = Arrays.asList("Java", "Python", "Swift", "HTML");
        Stream<String> filterStream = langList.stream().filter(lang -> lang.equalsIgnoreCase("java"));
        Stream<String> mapStream = langList.stream().map(String::toUpperCase);
        String java = "java";

        Stream<String> cityStream = Stream.of("Beijing", "Shanghai", "Shenzhen");
        // [['B', 'e', 'i', 'j', 'i', 'n', 'g'], ['S', 'h', 'a', 'n', 'g', 'h', 'a', 'i'], ...]
        Stream<Stream<Character>> characterStream1 = cityStream.map(city -> characterStream(city));
        Stream<String> cityStreamCopy = Stream.of("Beijing", "Shanghai", "Shenzhen");
        // ['B', 'e', 'i', 'j', 'i', 'n', 'g', 'S', 'h', 'a', 'n', 'g', 'h', 'a', 'i', ...]
        Stream<Character> characterStream2 = cityStreamCopy.flatMap(city -> characterStream(city));
//        characterStream1.forEach(s -> s.peek(c -> System.out.println(c)).toArray());
//        characterStream2.peek(s -> System.out.println(s)).toArray();

        // limit, skip, concat, peek
        Stream<Integer> limitStream = Stream.of(18, 20, 12, 35, 89).sorted().limit(3);
        Stream<Integer> skipStream = Stream.of(18, 20, 12, 35, 89).sorted(Comparator.reverseOrder()).skip(1);
        Stream<Integer> concatStream = Stream.concat(Stream.of(1, 2, 3), Stream.of(4, 5, 6));
        limitStream.peek(i -> System.out.println(i)).count();
        skipStream.peek(i -> System.out.println(i)).count();
        concatStream.peek(i -> System.out.println(i)).count();

        // stateful transformation: distinct, sorted
        Stream<String> distinctStream = Stream.of("Beijing", "Tianjin", "Beijing").distinct();
        Stream<String> sortedStream = Stream.of("Beijing", "Shanghai", "Chengdu").sorted(Comparator.comparing
            (String::length).reversed());

    }

    private Stream<Character> characterStream(String s) {
        List<Character> result = new ArrayList<>();
        for (char c: s.toCharArray()) result.add(c);
        return result.stream();
    }


    public void testReduction() {

        // create optional
        Optional<Integer> intOpt = Optional.of(10);
        Optional<String> emptyOpt = Optional.empty();
        Optional<Double> doubleOpt = Optional.ofNullable(5.5);
        if (intOpt.isPresent()) {
            intOpt.get();
        }

        doubleOpt.orElse(0.0);
        doubleOpt.orElseGet(() -> 1.0);
        doubleOpt.orElseThrow(RuntimeException::new);
        List<Double> doubleList = new ArrayList<>();
        doubleOpt.ifPresent(doubleList::add);
        // have return type
        Optional<Boolean> addOk = doubleOpt.map(doubleList::add);
        Optional.of(4.0).flatMap(num -> Optional.ofNullable(num * 100)).flatMap(num -> Optional.ofNullable(Math.sqrt
            (num)));


        // simple reduction
        Stream<String> wordStream = Stream.of("You", "may", "assume", "that", "valid", "phone", "number");
        long count = wordStream.count();
        Optional<String> firstWord = wordStream.filter(s -> s.startsWith("Y")).findFirst();
        Optional<String> anyWord = wordStream.filter(s -> s.length() > 3).findAny();
        wordStream.allMatch(s -> s.length() > 3);
        wordStream.anyMatch(s -> s.length() > 3);
        wordStream.noneMatch(s -> s.length() > 3);

        // reduction
        Stream<Integer> numStream = Stream.of(87, 19, 26, 90, 43, 15, 80, 41);
        Optional<Integer> sum1 = numStream.reduce((x, y) -> x + y);
        Integer sum2 = numStream.reduce(0, Integer::sum);
    }

    public void testCollect() {
        // toArray()
        Stream<String> wordStream = Stream.of("You", "may", "assume", "that", "valid", "phone", "number");
        Object[] words1 = Stream.of("You", "may", "assume").toArray();
        String[] words2 = Stream.of("You", "may", "assume").toArray(String[]::new);

        // collector
        Stream.of("You", "may", "assume").collect(HashSet::new, HashSet::add, HashSet::addAll);
        Stream.of("You", "may", "assume").collect(Collectors.toList());
        Stream.of("You", "may", "assume").collect(Collectors.toSet());
        Stream.of("You", "may", "assume").collect(Collectors.toCollection(TreeSet::new));
        Stream.of("You", "may", "assume").collect(Collectors.joining());
        Stream.of("You", "may", "assume").collect(Collectors.joining(", "));
        IntSummaryStatistics summary = Stream.of("You", "may", "assume").collect(Collectors.summarizingInt
            (String::length));
        summary.getMax();
        summary.getMax();
        Stream.of("You", "may", "assume", "you", "can", "fly").parallel().forEach(w -> System.out.println(w));
        Stream.of("You", "may", "assume", "you", "can", "fly").forEachOrdered(w -> System.out.println(w));
        Stream.of(15, 30, 42).collect(Collectors.reducing((x, y) -> x + y));

        // toMap()
        Stream<String> introStream = Stream.of("Get started with UICollectionView and the photo library".split(" "));
        Map<String, String> introMap = introStream.collect(Collectors.toMap(s -> s.substring(0, 1), s -> s));
        introMap.forEach((k, v) -> System.out.println(k + ": " + v));

        Stream<String> introStream2 = Stream.of("Get started with UICollectionView and the photo library".split(" "));
        Map<Integer, String> introMap2 = introStream2.collect(Collectors.toMap(String::length, s -> s,
            (existingValue, newValue) -> existingValue, TreeMap::new));
        introMap2.forEach((k, v) -> System.out.println(k + ": " + v));

        Stream<String> introStream3 = Stream.of("Get started with UICollectionView and the photo library".split(" "));
        Map<Integer, Set<String>> introMap3 = introStream3.collect(Collectors.toMap(s -> s.length(),
            s -> Collections.singleton(s), (existingValue, newValue) -> {
                HashSet<String> set = new HashSet<>(existingValue);
                set.addAll(newValue);
                return set;
            }));
        introMap3.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    public void testGroupingBy() {
        // groupingBy
        Map<String, List<Locale>> countryToLocaleList = Stream.of(Locale.getAvailableLocales())
            .collect(Collectors.groupingBy(l -> l.getDisplayCountry()));
        // predicate
        Map<Boolean, List<Locale>> englishAndOtherLocales = Stream.of(Locale.getAvailableLocales())
            .collect(Collectors.groupingBy(l -> l.getDisplayLanguage().equalsIgnoreCase("English")));
        // partitioningBy
        Map<Boolean, List<Locale>> englishAndOtherLocales2 = Stream.of(Locale.getAvailableLocales())
            .collect(Collectors.partitioningBy(l -> l.getDisplayLanguage().equalsIgnoreCase("English")));

        englishAndOtherLocales.forEach((k, v) -> System.out.println(k + " -> " + Arrays.toString(v.toArray())));
        countryToLocaleList.forEach((k, v) -> System.out.println(k + " -> " + Arrays.toString(v.toArray())));
        englishAndOtherLocales2.forEach((k, v) -> System.out.println(k + " -> " + Arrays.toString(v.toArray())));

        // downstream collector
//        City[] cities = {new City(), new City(), new City()};
//        Map<String, Set<Locale>> countryToLocaleSet = Stream.of(Locale.getAvailableLocales())
//            .collect(Collectors.groupingByConcurrent(l -> l.getDisplayCountry(), Collectors.toSet()));
//
//        Map<String, Long> countryToLocaleCounts = Stream.of(Locale.getAvailableLocales())
//            .collect(Collectors.groupingBy(l -> l.getDisplayCountry(), Collectors.counting()));
//
//        Map<String, Integer> cityToPopulationSum = Stream.of(cities)
//            .collect(Collectors.groupingBy(City::getName, Collectors.summingInt(City::getPopulation)));
//
//        Map<String, Optional<City>> cityToPopulationMax = Stream.of(cities)
//            .collect(Collectors.groupingBy(City::getName, Collectors.maxBy(Comparator.comparing(City::getPopulation))));
//
//        Map<String, Optional<String>> stateToNameMax = Stream.of(cities)
//            .collect(Collectors.groupingBy(City::getState, Collectors.mapping(City::getName, Collectors.maxBy
//                (Comparator.comparing(String::length)))));
//
//        Map<String, Set<String>> stateToNameSet = Stream.of(cities)
//            .collect(Collectors.groupingBy(City::getState, Collectors.mapping(City::getName, Collectors.toSet())));
//
//        Map<String, IntSummaryStatistics> stateToPopulationSummary = Stream.of(cities)
//            .collect(Collectors.groupingBy(City::getState, Collectors.summarizingInt(City::getPopulation)));
//
//        Map<String, String> stateToNameJoining = Stream.of(cities)
//            .collect(Collectors.groupingBy(City::getState, Collectors.reducing("", City::getName,
//                (s, t) -> s.length() == 0 ? t : s + ", " + t)));
//
//        Map<String, String> stateToNameJoining2 = Stream.of(cities)
//            .collect(Collectors.groupingBy(City::getState, Collectors.mapping(City::getName, Collectors.joining(", ")
//            )));

    }

    public void testPrimitiveStream() {
        // create primitive stream
        IntStream intStream = IntStream.of(10, 20, 30);
        IntStream zeroToNintyNine = IntStream.range(0, 100);
        IntStream zeroToHundred = IntStream.rangeClosed(0, 100);
        double[] nums = {10.0, 20.0, 30.0};
        DoubleStream doubleStream = Arrays.stream(nums, 0, 3);

        OptionalInt maxNum = intStream.max();
        int[] intArr = intStream.toArray();
        IntSummaryStatistics intSummary = intStream.summaryStatistics();

        // map to
        Stream<String> cityStream = Stream.of("Beijing", "Tianjin", "Chengdu");
        IntStream lengthStream = cityStream.mapToInt(String::length);

        // box
        Stream<Integer> oneToNine = IntStream.range(0, 10).boxed();

    }

    public void testParallelStream() {
        IntStream scoreStream = IntStream.rangeClosed(10, 30).parallel();

        int[] wordLength = new int[12];
        Stream.of("It", "is", "your", "responsibility").parallel().forEach(s -> {
            if (s.length() < 12) wordLength[s.length()]++;
        });

        LongStream.rangeClosed(5, 10).unordered().parallel().limit(3);
        IntStream.of(14, 15, 15, 14, 12, 81).unordered().parallel().distinct();

//        City[] cities = {new City(), new City(), new City()};
//        Stream.of(cities).parallel().collect(Collectors.groupingByConcurrent(City::getState));

        // ok
        List<String> wordList = new ArrayList<>();
        wordList.add("You");
        Stream<String> wordStream = wordList.stream();
        wordList.add("number");
        wordStream.distinct().count();

        // ConcurrentModificationException
        Stream<String> wordStream2 = wordList.stream();
        wordStream2.forEach(s -> { if (s.length() >= 6) wordList.remove(s);});
        System.out.println(Arrays.toString(wordList.toArray()));

    }


    public static void main(String[] args) {

        StreamAPI streamAPI = new StreamAPI();
//        streamAPI.testSteamIntro();
//        streamAPI.testCreateStream();
//        streamAPI.testStreamTransformation();
//        streamAPI.testCollect();
//        streamAPI.testGroupingBy();

        streamAPI.testParallelStream();


    }

}
