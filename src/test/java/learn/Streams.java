package learn;

import org.example.Department;
import org.example.Person;
import org.junit.Test;
import org.w3c.dom.events.Event;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.*;

public class Streams {
    private List<Person> prsn = List.of(
            new Person(1, "Ivan", "Ish", 29, "Male", "Russia"),
            new Person(2, "Anna", "Trade", 28, "Female", "Germany"),
            new Person(3, "Maria", "Plane", 31, "Female", "USA"),
            new Person(4, "John", "Known", 32, "Male", "Canada"),
            new Person(5, "Max", "Fry", 42, "Male", "UK"),
            new Person(6, "Mac", "Demarco", 25, "Male", "Canada"),
            new Person(7, "Gretta", "Brown", 21, "Female", "USA"),
            new Person(8, "Jack", "Olsen", 35, "Male", "UK")
    );
    private List<Department> deps = List.of(
            new Department(1, 3, "Head"),
            new Department(2, 1, "West"),
            new Department(3, 2, "East"),
            new Department(4, 1, "France"),
            new Department(5, 2, "Germany"),
            new Department(6, 3, "Japan")
    );

    @Test
    public void creation() throws IOException {
        try {


          //  Stream<String> lines = Files.lines(Paths.get("file.txt"));
            Stream<Path> list = Files.list(Paths.get("./"));
            Stream<Path> walk = Files.walk(Paths.get("./"), 3);
            list.forEach(System.out::println);
            walk.forEach(System.out::println);
        }catch (IOException ep){
            ep.printStackTrace();
        }

        IntStream intStream = IntStream.of(100, 200, 300, 400);
        DoubleStream doubleStream = DoubleStream.of(1.2, 1.2, 1.3, 1.4);
        IntStream range = IntStream.range(10, 100); // 10 ... 99
        IntStream intStream1 = IntStream.rangeClosed(10, 100);//10 ... 100

        int[] ints = {1, 2, 3, 4};
        IntStream stream = Arrays.stream(ints);

        Stream<String> stream1 = Stream.of("1", "2", "3"); //Stream String 1 2 3
        Stream<? extends Serializable> stream2 = Stream.of(1, "2"); // mix object

        Stream<String> build = Stream.<String>builder()
                .add("one")
                .add("two")
                .add("three")
                .build();

        Stream<Person> stream3 = prsn.stream();
        Stream<Person> personStream = prsn.parallelStream(); // parallel

        //  Stream<T> generate = Stream.generate(() -> new Event(UUID.randomUUID(), LocalDateTime.now(), " ");
        Stream<Integer> iterate = Stream.iterate(1994, val -> val + 2); // generate from 1994 step 2
        Stream<Serializable> concat = Stream.concat(stream2, build); //sum few stream

    }

    @Test
    public void terminate() {

        Stream<Person> stream = prsn.stream();
        long count = stream.count();// for next call this stream will not available. it's have to create new stream

        prsn.stream().forEach(person -> System.out.print(person.getFirstName() + " | "));
        System.out.println();
        prsn.stream().forEachOrdered(person -> System.out.print(person.getFirstName() + " | "));
        System.out.println();
        prsn.forEach(person -> System.out.print(person.getFirstName() + " | "));

        Object[] array = prsn.stream().toArray();
        Map<Integer, String> collect = prsn.stream().collect(Collectors.toMap(
                Person::getId,
                person -> String.format("%s %s", person.getFirstName(), person.getLastName())
        ));

        Map<String, List<Person>> collect1 = prsn.stream().collect(Collectors.groupingBy(Person::getCountry)); //groupBy County


        IntStream intStream = IntStream.of(1, 2, 3, 4, 5, 6);
        IntStream intStream1 = IntStream.of(1, 2, 3, 4, 5, 6);
        int asInt = intStream.reduce((left, right) -> left + right).getAsInt(); //or .orElse(0 )  sum range
        int i = intStream1.reduce(Integer::sum).orElse(0);

        System.out.println();
        System.out.println(deps.stream().reduce(this::reducer).toString());  //reducer print like tree

        IntStream.of(200, 300, 400, 500).average();
        IntStream.of(200, 300, 400, 500).max();
        IntStream.of(200, 300, 400, 500).min();
        IntStream.of(200, 300, 400, 500).sum();
        IntStream.of(200, 300, 400, 500).summaryStatistics();

        Optional<Person> max = prsn.stream().max(Comparator.comparingInt(Person::getAge));

        prsn.stream().findAny();
        prsn.stream().findFirst();

        boolean b = prsn.stream().noneMatch(person -> person.getAge() > 60); // TRUE
        boolean b1 = prsn.stream().allMatch(person -> person.getAge() > 18);// TRUE
        boolean b2 = prsn.stream().anyMatch(person -> person.getCountry().equals("Russia")); //TRUE
    }

    @Test
    public void transform() {
        LongStream longStream = IntStream.of(100, 200, 300, 400).mapToLong(Long::valueOf);

        IntStream distinct = IntStream.of(100, 200, 300, 400).distinct(); // delete duplicate

        List<Person> male = prsn.stream().filter(person -> person.getSex().equals("male")).toList();
        List<Person> list = prsn.stream()
                .skip(5)
                .limit(3)
                .toList();

        Stream<String> stream = prsn.stream().sorted(Comparator.comparingInt(Person::getAge))
                .map(person -> String.format("%s %s", person.getFirstName(), person.getLastName()));

        // .peek - set val for all persons

        //it`s work like a trigger
        prsn.stream().takeWhile(person -> person.getAge() > 27).forEach(System.out::println);
        System.out.println("____________");
        prsn.stream().dropWhile(person -> person.getAge() > 27).forEach(System.out::println);


        IntStream.of(100,200,300,400)
                .flatMap(val -> IntStream.of(val-50, val))
                .forEach(System.out::println);

    }

    @Test
    public void real (){

        Stream<Person> sorted = prsn.stream()
                .filter(person -> person.getAge() > 25 && person.getSex().equals("Male"))
                .sorted(Comparator.comparing(Person::getLastName));
        printCustom(sorted);
        System.out.println("----");
        Stream<Person> limit = prsn.stream().sorted(Comparator.comparing(Person::getAge).reversed()).limit(3);
        printCustom(limit);

        IntSummaryStatistics intSummaryStatistics = prsn.stream().mapToInt(Person::getAge).summaryStatistics();
        System.out.println(intSummaryStatistics);

    }


    private void printCustom (Stream<Person> personStream){
        personStream
                .map(person -> String.format(
                        "%4d | %-5s %-5s age %s %s %s",
                        person.getId(),
                        person.getFirstName(),
                        person.getLastName(),
                        person.getAge(),
                        person.getSex(),
                        person.getCountry()
                )).forEach(System.out::println);
    }
    public Department reducer(Department parent, Department child) {
        if (Objects.equals(child.getParent(), parent.getId())) {
            parent.getChild().add(child);
        } else {
            parent.getChild().forEach(subParent -> reducer(subParent, child));
        }
        return parent;
    }

}
