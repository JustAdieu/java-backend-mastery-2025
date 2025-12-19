package com.adieu.pojo;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Person类的equals方法和hashCode方法测试
 *
 * @author  adieu
 * @date    2025-12-18
 */
@DisplayName("Person类equals方法和hashCode方法测试类")
public class PersonEqualsHashCodeTest {

    /**
     *  equals相等情况测试
     *  1.相同对象，返回 true
     *  2.id相同，返回 true
     * @param person1
     * @param person2
     * @param expectedResult
     */
    @ParameterizedTest
    @MethodSource("equalProvider")
    @DisplayName("测试equals方法中相等情况")
    void testPersonEquals_EqualScenarios(Person person1, Person person2, boolean expectedResult){
        assertEquals(expectedResult, person1.equals(person2));
    }

    private static Stream<Arguments> equalProvider(){
        Person p1 = new Person();
        return Stream.of(
                // 相同对象，返回 true
                Arguments.of(p1,p1,true),
                // id相同，返回 true
                Arguments.of(createPerson("1"), createPerson("1"), true),
                // id都为null,返回 true
                Arguments.of(createPerson(null), createPerson(null), false)
        );
    }

    /**
     * 测试不相等的情况
     * 1. id不同，返回 false
     * 2. 比较对象其中一个为null，另一个不是，返回 false
     * 3. 两个类不同，返回 false
     * 4. 其中任意一个id为null，返回 false
     * @param person1
     * @param person2
     * @param expectedResult
     */
    @ParameterizedTest
    @MethodSource("unequalProvider")
    @DisplayName("测试equals方法中不相等情况")
    void testPersonEquals_UnequalScenarios(Person person1, Person person2, boolean expectedResult){
        assertEquals(expectedResult, person1.equals(person2));
    }

    private static Stream<Arguments> unequalProvider(){
        Student s1 = new Student();
        s1.setId("1");
        return Stream.of(
                // id不同，返回 false
                Arguments.of(createPerson("1"), createPerson("2"), false),
                // 比较对象其中一个为null，另一个不是，返回 false
                Arguments.of(createPerson("1"), null, false),
                // 两个类不同，返回 false
                Arguments.of(createPerson( "1"),s1,false),
                // 其中任意一个id为null，返回 false
                Arguments.of(createPerson(null), createPerson("1"), false)
        );
    }

    /**
     * 测试hashCode和equals协同方法
     * 1. 相同对象，返回true
     * 2. id相同，返回true
     * 3. id不同，返回false
     * 4. 比较对象中一个为null，另一个不是，返回false
     * 5. 两个类不同，返回false
     * 6. 其中任意一个id为null，返回false
     * @param p1
     * @param p2
     * @param expectedResult
     */
    @ParameterizedTest
    @MethodSource("hashCodeProvider")
    @DisplayName("测试hashCode和equals协同方法")
    void testPersonHashCode(Person p1,Person p2,boolean expectedResult){
        assertEquals(expectedResult,p1.equals(p2) == ( p1.hashCode() == p2.hashCode()));
    }

    private static Stream<Arguments> hashCodeProvider(){
        Person p1 = new Person();
        p1.setId("1");
        Student s1 = new Student();
        s1.setId("1");
        return Stream.of(
                //1. 相同对象，hashCode和equals结果相同
                Arguments.of(p1,p1,true),
                //2. id相同，hashCode和equals结果相同
                Arguments.of(createPerson("1"), createPerson("1"), true),
                //3. id不同，hashCode和equals结果相同
                Arguments.of(createPerson("1"), createPerson("2"), true),
                //4. 两个类不同，hashCode和equals结果相同
                Arguments.of(createPerson("1"),s1,true),
                //5. 两个id都为null，对象不为null,hashCode和equals结果不同
                Arguments.of(createPerson(null), createPerson(null), false),
                //6. 其中一个id为null，另一个不为null,hashCode和equals结果相同
                Arguments.of(createPerson(null), createPerson("1"), true)
        );
    }

    /**
     * 测试hashCode兼容HashSet
     *
     * @author   adieu
     * @date    2025-12-19
     */
    @Test
    @DisplayName("测试hashCode兼容HashSet")
    void testPersonHashCode_HashSetCompatibility(){
        Person p1 = createPerson("1");
        Person p2 = createPerson("1");
        Person p3 = createPerson("2");

        HashSet<Person> set = new HashSet<>();
        set.add(p1);
        set.add(p2);
        set.add(p3);

        assertEquals(2,set.size());
    }


    private static Person createPerson(String id){
        Person p = new Person();
        p.setId(id);
        return p;
    }

}
