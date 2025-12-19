package com.adieu.pojo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Person类测试类")
public class PersonTest {
    private final Person person = new Person();

    /**
     * 正常场景测试
     */
    @ParameterizedTest
    @CsvSource({"20,20","1,1","150,150"})
    @DisplayName("测试正常和边界场景")
    void testSetAge_ValidScenarios(Integer inputAge,Integer expectedAge){
        person.setAge(inputAge);
        assertEquals(expectedAge,person.getAge());
    }

    /**
     * 异常场景测试
     */
    @ParameterizedTest
    @MethodSource("invalidAgeProvider")
    @DisplayName("测试异常场景")
    void testSetAge_InvalidScenarios(Integer inputAge,String expectedMsg){
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> person.setAge(inputAge)
        );
        assertEquals(expectedMsg, exception.getMessage());
    }

    private static Stream<Arguments> invalidAgeProvider(){
        return Stream.of(
            Arguments.of(-1,"年龄不合法"),
            Arguments.of(151,"年龄不合法"),
            Arguments.of(null,"年龄不能为空")
        );
    }
}
