package com.lvshen.demo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lvshen.demo.java8.stream.MyCollectionUtils;
import lombok.Data;
import org.junit.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2024-01-14 14:17
 * @since JDK 1.8
 */
public class SimpleTest {
    @Test
    public void testGroupAndSum() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Alice", "Group1", new BigDecimal("100")));
        personList.add(new Person("Bob", "Group1", new BigDecimal("200")));
        personList.add(new Person("Charlie", "Group2", new BigDecimal("150")));
        personList.add(new Person("Alice", "Group1", new BigDecimal("250")));
        personList.add(new Person("Alice", "Group1", BigDecimal.ZERO));

        Map<String, BigDecimal> sumByGroup = MyCollectionUtils.groupStrKeyAndSum(personList,
                Arrays.asList(Person::getGroup, Person::getName),
                Person::getAmount);

        for (Map.Entry<String, BigDecimal> entry : sumByGroup.entrySet()) {
            System.out.println("Group: " + entry.getKey() + ", Sum: " + entry.getValue());
        }
        System.out.println();

    }

    @Test
    public void testSorted() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Alice", "Group1", "100"));
        personList.add(new Person("Bob", "Group1", "200"));
        personList.add(new Person("Charlie", "Group2", "150"));
        personList.add(new Person("Alice", "Group1", "250"));
        personList.add(new Person("Alice", "Group1", ""));

        Map<String, String> map = Maps.newHashMap();
        map.put("name","DESC");
        map.put("amountStr","ASC");

        // personList.sort(Comparator.comparing(Person::getName,
        //                 Comparator.nullsLast(Comparator.naturalOrder()))
        //         .thenComparing(Person::getAmountStr,Comparator.nullsLast(Comparator.naturalOrder())).reversed());
        // System.out.println(personList);

        //map.put("group","DESC");
        List<String> sortFields = Arrays.asList("name", "amountStr");
        Comparator<Person> comparator = (person1, person2) -> {
            for (Entry<String, String> stringStringEntry : map.entrySet()) {
                String field = stringStringEntry.getKey();
                String sortedType = stringStringEntry.getValue();
                int result = getFieldComparator(field).compare(person1, person2);
                boolean isAscending = "ASC".equals(sortedType);
                if (!isAscending) {
                    result *= -1;
                }
                if (result != 0) {
                    return result;
                }
            }
            return 0;
        };
        personList.sort(comparator);
        System.out.println(personList);

    }

    @Test
    public void testSortedMap() {
        List<Person> personList = Lists.newArrayList();
        Person person1 = new Person();
        person1.setName("Alice");
        Map<String, String> map1 = Maps.newHashMap();
        map1.put("age","19");
        person1.setAttributes(map1);
        Person person2 = new Person();
        person2.setName("Bob");
        Map<String, String> map2 = Maps.newHashMap();
        map2.put("age", "14");
        person2.setAttributes(map2);
        Person person3 = new Person();
        person3.setName("Charlie");
        Map<String, String> map3 = Maps.newHashMap();
        map3.put("age", "10");
        map3.put("height", "90");
        map3.put("score", "60");
        person3.setAttributes(map3);

        Person person4 = new Person();
        person4.setName("niuniu");
        Map<String, String> map4 = Maps.newHashMap();
        map4.put("age", "10");
        map4.put("height", "80");
        map4.put("score", "100");
        person4.setAttributes(map4);

        Person person5 = new Person();
        person5.setName("dalao");
        Map<String, String> map5 = Maps.newHashMap();
        map5.put("age", "10");
        map5.put("height", "80");
        map5.put("score", "70");
        person5.setAttributes(map5);

        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);
        personList.add(person5);


        Map<String, String> map = Maps.newHashMap();
        map.put("age","ASC");
        map.put("height","DESC");
        map.put("score","ASC");

        Comparator<Person> comparator = (personA1, personA2) -> {
            for (Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String sortedType = entry.getValue();
                boolean isAscending = "ASC".equals(sortedType);
                String value1 = personA1.getAttributes().get(key);
                String value2 = personA2.getAttributes().get(key);
                if (value1 != null && value2 != null) {
                    int result = value1.compareTo(value2);
                    if (!isAscending) {
                        result *= -1;
                    }
                    if (result != 0) {
                        return result;
                    }
                }
            }
            return 0;
        };
        personList.sort(comparator);

        System.out.println(personList);
    }

    private static Comparator<Person> getFieldComparator(String field) {
        return (person1, person2) -> {
            try {
                Field declaredField = Person.class.getDeclaredField(field);
                declaredField.setAccessible(true);
                Object value1 = declaredField.get(person1);
                Object value2 = declaredField.get(person2);
                if (value1 instanceof Comparable && value2 instanceof Comparable) {
                    return ((Comparable) value1).compareTo(value2);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return 0;
        };
    }

    @Test
    public void testPlates() {
        List<Plate> plateList = new ArrayList<>();
        plateList.add(new Plate("A1", 5));
        plateList.add(new Plate("A2", 3));
        plateList.add(new Plate("A3", 8));
        plateList.add(new Plate("A4", 2));
        plateList.add(new Plate("A5", 4));
        plateList.add(new Plate("A6", 1));

        int targetEggs = 4;

        //List<List<String>> result = selectPlates(plateList, targetEggs);
        List<List<String>> result = selectPlatesReverse(plateList, targetEggs);
        System.out.println("Selected plates combinations: " + result);
    }

    @Test
    public void testLists() {
        List<List<String>> lists = Arrays.asList(
                Arrays.asList("1", "2", "3"),
                Arrays.asList("2", "3", "4"),
                Arrays.asList("4", "1"),
                Arrays.asList("")
        );

        List<String> strings = MyCollectionUtils.listDuplicateData(lists);
        System.out.println(strings);
    }


    static List<List<String>> selectPlates(List<Plate> plateList, int targetEggs) {
        List<List<String>> result = new ArrayList<>();
        backtrack(plateList, targetEggs, 0, new ArrayList<>(), result);
        return result;
    }

    static List<List<String>> selectPlatesReverse(List<Plate> plateList, int targetEggs) {
        List<List<String>> result = new ArrayList<>();
        List<Plate> collect = plateList.stream().sorted(Comparator.comparing(Plate::getEggs)).collect(Collectors.toList());
        backtrackReverse(collect, targetEggs, 0, new ArrayList<>(), result);
        return result;
    }

    static void backtrack(List<Plate> plateList, int targetEggs, int start, List<
            String> combination, List<List<String>> result) {
        if (targetEggs <= 0) {
            result.add(new ArrayList<>(combination));
            return;
        }

        for (int i = start; i < plateList.size(); i++) {
            Plate plate = plateList.get(i);
            combination.add(plate.getName());
            backtrack(plateList, targetEggs - plate.getEggs(), i + 1, combination, result);
            combination.remove(combination.size() - 1);
        }
    }
    static void backtrackReverse(List<Plate> plateList, int targetEggs, int start, List<
            String> combination, List<List<String>> result) {
        if (targetEggs < 0) {
            return;
        }
        if (targetEggs == 0) {
            result.add(new ArrayList<>(combination));
        } else {
            for (int i = start; i < plateList.size(); i++) {
                Plate plate = plateList.get(i);
                if (plate.getEggs() > targetEggs) {
                    break;
                }
                combination.add(plate.getName());
                backtrackReverse(plateList, targetEggs - plate.getEggs(), i + 1, combination, result);
                combination.remove(combination.size() - 1);
            }
        }
    }





    @Data
    static class Person {
        private String name;
        private String group;
        private BigDecimal amount;
        private Map<String, String> attributes;
        private String amountStr;

        public Person(String name, String group, BigDecimal amount) {
            this.name = name;
            this.group = group;
            this.amount = amount;
        }
        public Person(String name, String group, String amount) {
            this.name = name;
            this.group = group;
            this.amountStr = amount;
        }

        public Person(String name, Map<String, String> attributes) {
            this.name = name;
            this.attributes = attributes;
        }

        public Person() {

        }

        public String getName() {
            return name;
        }

        public String getGroup() {
            return group;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }
    }

    static class Plate {
        private String name;
        private int eggs;

        public Plate(String name, int eggs) {
            this.name = name;
            this.eggs = eggs;
        }

        public String getName() {
            return name;
        }

        public int getEggs() {
            return eggs;
        }
    }
}
