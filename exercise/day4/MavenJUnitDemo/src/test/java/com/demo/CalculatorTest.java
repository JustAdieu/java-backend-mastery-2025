package com.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    @Test
    public void testAdd(){
        Calculator c = new Calculator();
        int result = c.add(1,2);
        assertEquals(3,result,"加法结果错误");
    }
}
