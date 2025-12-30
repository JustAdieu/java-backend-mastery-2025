package main.java.com.adieu.learning.week3.daily.day1;

public class BusinessException extends Exception {
    public BusinessException(String message) {
        super(message);
    }
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
