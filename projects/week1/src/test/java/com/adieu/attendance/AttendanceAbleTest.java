package com.adieu.attendance;


import com.adieu.pojo.Student;
import com.adieu.pojo.Teacher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 考勤接口单元测试
 * 覆盖场景：
 * 1. 学生打卡返回"上课打卡"
 * 2. 教师打卡返回"授课打卡"
 * 3. 管理员无打卡方法（无需测试，通过编译校验即可）
 *
 * @author  adieu
 * @date    2025-12-18
 */

@DisplayName("AttendanceAble接口测试类")
public class AttendanceAbleTest {

    @Test
    @DisplayName("测试学生教师打卡")
    void testCheckAttendancePloy(){
        AttendanceAble[] attendanceAbles = new AttendanceAble[]{
                new Student(),
                new Teacher()
        };

        String[] expectedResults = {"上课打卡","授课打卡"};

        for(int i = 0;i < attendanceAbles.length;i++){
            assertEquals(expectedResults[i],attendanceAbles[i].checkAttendance());
        }

    }
}
