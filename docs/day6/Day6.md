---
Day6	项目实践
author 	Adieu
date	2025/12/18
---

[toc]

# 项目开发

## 项目选题：校园人员管理系统（进阶级）

### 核心场景

模拟校园内「学生、教师、管理员」的统一管理，重点强化 “接口 + 继承 + 多态” 的设计，同时深度验证`hashCode`/`equals`的边界场景。

### 技术点映射

| 知识点          | 落地场景                                                     |
| --------------- | ------------------------------------------------------------ |
| 封装            | 父类`Person`私有化字段（id、姓名、性别、年龄），年龄设置时校验 1-150 范围，对外暴露 getter/setter； |
| 继承            | 父类`Person`，子类`Student`（学号、专业、成绩）、`Teacher`（工号、教龄、授课科目）、`Admin`（工号、职责）； |
| 多态            | 定义`CampusService`，提供`printPersonInfo(Person person)`方法，传入不同子类对象，自动调用子类重写的`getIdentity()`方法； |
| 接口            | 定义`AttendanceAble`接口（`checkAttendance()`：考勤打卡），`Student`和`Teacher`实现该接口（学生打卡返回 “上课打卡”，教师返回 “授课打卡”），`Admin`不实现（无需考勤）； |
| hashCode/equals | `Person`重写`equals()`（按 id 判断身份唯一）、`hashCode()`（基于 id）；`Student`额外重写（按学号 + id），测试 “同 id 不同学号是否为同一个学生”； |
| toString        | 子类重写`toString()`，差异化输出（如学生：“学生 [id=1, 姓名 = 张三，学号 = 2025001, 专业 = 计算机]”）； |

### 扩展亮点

1. 定义`Comparator<Person>`接口实现类，按年龄 / 姓名排序校园人员，强化接口使用；
2. 测试`HashSet<Person>`：验证添加 “同 id 不同姓名” 的 Person 对象时，Set 是否去重（体现`equals`/`hashCode`的作用）；
3. 多态实战：创建`List<Person>`集合，存入学生、教师、管理员，遍历调用`getIdentity()`，演示 “一个集合存储不同子类对象”。

## 实践日记

### PersonTest类

#### @DisplayName注释

核心作用是**为测试类 / 测试方法设置「人类友好的、自定义的显示名称」**.

![image-20251218195509859](C:\Users\Adieu\AppData\Roaming\Typora\typora-user-images\image-20251218195509859.png)

#### @ParameterizedTest

核心作用是：让**同一个测试方法可以接收多组不同的参数重复执行**，替代传统的 “为每个测试用例写一个独立 @Test 方法” 的方式，大幅减少重复代码，集中管理多场景测试。

#### @CsvSource({"20,20","1,1","150,150"})

核心作用是通过**CSV 格式的字符串**直接定义多组测试参数（支持多类型、多列参数），无需单独写静态方法（如`@MethodSource`），适合参数少、用例简单的场景。

1. 支持多类型参数

只要字符串能转换为目标类型即可，比如混合`int`和`String`：

```java
// 场景：测试年龄+提示语
@CsvSource({"20, 年龄合法","150, 年龄合法","-1, 年龄非法"})
void testAgeCheck(Integer age, String tip) {
    // 测试逻辑...
}
```

2. 自定义分隔符

默认用逗号分隔，若参数本身包含逗号，可自定义分隔符（如竖线`|`）：

```java
@CsvSource(delimiter = '|', value = {"20|20", "1|1", "150|150"})
```

3. 处理 null 值

用`null`关键字表示空值（需注意大小写）：

```java
// 测试null场景：输入null，预期提示"年龄不能为null"
@CsvSource({"null, 年龄不能为null"})
void testSetAge_Null(Integer inputAge, String expectedMsg) {
    // 异常校验逻辑...
}
```

4. 忽略空行 / 注释

可通过`ignoreLeadingAndTrailingWhitespace`忽略参数前后空格，`comments`指定注释符：

```java
@CsvSource(
    value = {
        "20, 20",  // 正常用例
        "# 151, 年龄非法", // 注释行，会被忽略
        "150, 150"
    },
    ignoreLeadingAndTrailingWhitespace = true, // 忽略参数前后空格
    comments = '#' // 以#开头的行视为注释
)
```

#### assertEquals方法

`assertEquals` 是 JUnit（包括 JUnit 4/5）中最核心的**断言方法**，核心作用是：**校验「实际值」和「预期值」是否相等**，如果不相等则抛出断言失败异常，标记当前测试用例失败；如果相等则测试继续执行。

JUnit 5 中，`assertEquals` 是 `org.junit.jupiter.api.Assertions` 类的静态方法（需静态导入），基础语法：

```java
// 基础版：校验实际值 == 预期值
assertEquals(预期值, 实际值);

// 增强版：失败时自定义提示语（便于定位问题）
assertEquals(预期值, 实际值, "失败提示语");
```

#### Stream< Arguments >

1. 核心概念：`Stream`

`Stream` 是 Java 8 引入的流式处理接口，代表一个元素序列，支持函数式操作（如`of()`、`map()`等）。这里用`Stream.of()`创建包含多组测试参数的流，JUnit 5 会逐个读取流中的元素，作为测试方法的入参。

2. 核心概念：`Arguments`

`Arguments` 是 JUnit 5（Jupiter）提供的工具类，位于`org.junit.jupiter.params.provider`包下，作用是**封装一组测试参数**（可以是多个不同类型的值）。

比如`Arguments.of(-1, "年龄必须在0-150之间")`就封装了「输入参数：-1」和「预期结果："年龄必须在 0-150 之间"」这一组参数。

#### @MethodSource

`@MethodSource` 是 JUnit 5 参数化测试中**灵活的参数来源注解**，核心作用是：指定一个**静态方法**作为测试参数的来源，该方法返回的流式数据（如`Stream<Arguments>`）会被拆解为多组参数，依次传入`@ParameterizedTest`标记的测试方法执行。

#### IllegalArgumentException

`RuntimeException`是所有运行时异常的「父类」，是一个**通用异常**，无法体现 “参数非法” 的具体语义；而`IllegalArgumentException`是 Java 官方为「参数校验失败」定义的**专用异常**。