package com.lvshen.demo.java8.stream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Collection;

/**
 * Description:Java8 stream流工具类
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2023/9/16 10:22
 * @since JDK 1.8
 */
public class MyCollectionUtils {
    /**
     * 将数据分组，根据方法引用（bean的get方法）
     * 示例：
     * List<ContractTemplate> contractTemplates = listContractTemplateByIds(ids);
     * Map<String, List<ContractTemplate>> map = MyCollectionUtils.groupingBy(contractTemplates, ContractTemplate::getTemplateCode);
     *
     * @param list      为分组的数据
     * @param functions get方法数组
     */
    @SafeVarargs
    public static <T, R> Map<String, List<T>> groupingBy(List<T> list, Function<T, R>... functions) {
        return list.stream().collect(Collectors.groupingBy(t -> groupingBy(t, functions)));
    }

    /**
     * 分组工具根据函数式接口使用分组，将数据根据分组结果进行拆分
     */
    @SafeVarargs
    public static <T, R> String groupingBy(T t, Function<T, R>... functions) {
        if (functions == null || functions.length == 0) {
            throw new NullPointerException("functions数组不可以为空");
        } else if (functions.length == 1) {
            return functions[0].apply(t).toString();
        } else {
            return Arrays.stream(functions).map(fun -> fun.apply(t).toString()).reduce((str1, str2) -> str1 + "|" + str2).get();
        }
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = Maps.newConcurrentMap();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * 按对象属性去重
     * 示例：
     * performanceAddSubtractTotalVos = MyCollectionUtils.distinctByKey(performanceAddSubtractTotalVos, PerformanceAddSubtractVo::getId);
     *
     * @param list         list
     * @param keyExtractor list对象属性
     * @param <T>          list对象
     * @return 去重后的list
     */
    public static <T> List<T> distinctByKey(List<T> list, Function<? super T, ?> keyExtractor) {
        return list.stream().filter(distinctByKey(keyExtractor)).collect(Collectors.toList());
    }

    /**
     * 创建默认大小的hashmap
     *
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> HashMap<K, V> createDefaultHashMap() {
        return Maps.newHashMapWithExpectedSize(16);
    }

    /**
     * 可以使用Java 8的流(Stream)和flatMap()方法将 List<List<String>> 转换成 List<String>
     *
     * @param lists
     * @param <T>
     * @return
     */
    public static <T> List<T> flatList(List<List<T>> lists) {
        if (CollectionUtils.isEmpty(lists)) {
            return ImmutableList.of();
        }
        return lists.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * 属性类型转换
     * 示例：List<Object> originalList = List.of("1", "2", "3");
     * List<Integer> convertedList = convertList(originalList, Integer.class);
     *
     * @param list
     * @param targetType
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> convertList(List<T> list, Class<R> targetType) {
        if (CollectionUtils.isEmpty(list)) {
            return ImmutableList.of();
        }
        return list.stream()
                .map(targetType::cast)
                .collect(Collectors.toList());
    }


    /**
     * map转换成list
     * 示例：
     * Map<String, List<FunctionRole>> rolesByBindingObjects = SystemSdk.getRolesByBindingObjects(request);
     * List<FunctionRole> = rolesByBindingObjects.values().stream().flatMap(Collection::stream).collect(Collectors.toList())
     *
     * @param maps
     * @param <T>
     * @param <R>
     * @return
     */
    public <T, R> List<R> map2FlatList(Map<T, List<R>> maps) {
        if (MapUtils.isEmpty(maps)) {
            return ImmutableList.of();
        }
        return maps.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    /**
     * 将Object的key,value map转换成指定类型
     * 示例：
     * Map<Object, Object> originalMap = new HashMap<>();
     * Map<String, Integer> convertedMap = convertMap(originalMap, String.class, Integer.class);
     *
     * @param originalMap
     * @param keyType
     * @param valueType
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> convertMap(Map<Object, Object> originalMap, Class<K> keyType, Class<V> valueType) {
        if (MapUtils.isEmpty(originalMap)) {
            return ImmutableMap.of();
        }
        return originalMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> keyType.cast(entry.getKey()),
                        entry -> valueType.cast(entry.getValue())
                ));
    }

    /**
     * 从Map的值为List的数据中筛选出每个List的第一条数据，
     * 并将它们组成一个新的List，使用Java 8的流（Stream）和Lambda表达式来实现。
     *
     * @param maps
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> List<V> getMapFirstValue2List(Map<K, List<V>> maps) {
        if (MapUtils.isEmpty(maps)) {
            return ImmutableList.of();
        }
        return maps.values().stream()
                // 过滤掉空的List
                .filter(list -> !list.isEmpty())
                // 获取每个List的第一条数据
                .map(list -> list.get(0))
                // 收集到新的List中
                .collect(Collectors.toList());
    }


    /**
     * 将list中BigDecimal属性元素累加，过滤掉BigDecimal值为null的元素
     * @param list
     * @param mapper
     * @return
     * @param <T>
     */
    public static <T> BigDecimal sumBigDecimalList(List<T> list, Function<T, BigDecimal> mapper) {
        return list.stream()
                .map(mapper)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * list转map
     * List<Person> personList = ... // populate list with Person objects
     * Map<String, Integer> ageMap = ListUtils.extractPropertyToMap(personList, Person::getName, Person::getAge);
     * Map<String, Person> personMap = ListUtils.extractPropertyToMap(personList, Person::getName, Function.identity());
     * @param list
     * @param keyExtractor
     * @param valueExtractor
     * @return
     * @param <T>
     * @param <K>
     * @param <V>
     */
    public static <T, K, V> Map<K, V> extractPropertyToMap(List<T> list, Function<T, K> keyExtractor, Function<T, V> valueExtractor) {
        return list.stream()
                .collect(Collectors.toMap(keyExtractor, valueExtractor));
    }

    /**
     * list转map，map的key为对象的某个属性，value为list的对象
     * @param list
     * @param keyExtractor
     * @return
     * @param <T>
     * @param <K>
     */
    public static <T, K> Map<K, T> extractEntityToMap(List<T> list, Function<T, K> keyExtractor) {
        return extractPropertyToMap(list, keyExtractor, Function.identity());
    }
}
