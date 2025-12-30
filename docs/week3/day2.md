---
week3	day2
author	adieu
date	2025/12/29
---

[toc]

# 文件IO基础

## File

### File 类核心 API

- 重点学习方法：
  - 构造方法：`File(String pathname)`、`File(String parent, String child)`
  - 元数据操作：`exists()`、`isFile()`、`isDirectory()`、`length()`、`createNewFile()`、`mkdir()`、`delete()`
  - 路径操作：`getAbsolutePath()`、`getPath()`、`listFiles()`

## IO流

### 字节流

#### 抽象基类：InputStream & OutputStream

1. **类定位**

- 所有**字节输入流**的父类是 `InputStream`，所有**字节输出流**的父类是 `OutputStream`。
- 二者均为**抽象类**，无法直接实例化，仅定义字节流的核心操作规范。

2. **核心面试考点：为什么是抽象类？**

- **设计思想**：采用**模板方法模式**，定义字节读写的通用流程，将具体实现交给子类（如文件流、网络流）。
- **场景适配**：不同的字节数据源（文件、网络、内存）的读写逻辑差异极大，抽象类可屏蔽底层差异，提供统一的 `read()`/`write()` 接口。
- **扩展性**：新增数据源类型时，只需继承抽象类并实现抽象方法，无需修改上层代码。

3. **核心方法（InputStream）**

| 方法                 | 作用                                                         | 效率对比           |
| -------------------- | ------------------------------------------------------------ | ------------------ |
| `int read()`         | 读取**单个字节**，返回字节的 ASCII 码（0-255），读到末尾返回 -1 | 低（频繁磁盘 IO）  |
| `int read(byte[] b)` | 读取**批量字节**到数组 `b`，返回实际读取的字节数，末尾返回 -1 | 高（减少 IO 次数） |
| `void close()`       | 关闭流，释放资源                                             | -                  |

> 这里如果我们关注到InputStream源码的话，可以发现read(byte[])的实现底层都是使用了抽象方法read()进行实现的，也就是说子类只需要实现read()这个单字节的逻辑处理就可以复用read(byte[])，无需再重写read(byte[])方法了，这样的设计思想就十分符合模板方法模式，将与子类相关的实现逻辑进行屏蔽，父类需要关心的就是共性逻辑的实现，既降低了代码的耦合性，也提高了代码的复用性。

[^注意]:上面的知识点其实存在一些需要勘误的地方，我们如果关注到InputStream源码的话会发现它实现的read(byte[])逻辑其实是for循环调用了read()，如果子类重用这个逻辑的话意味着其实read(byte[])并没有达到减少IO次的作用，而对于部分子类而言例如FileInputStream会重写这个逻辑，调用native声明的方法来达到批量读取的效果。这里并没有将这个方法声明为抽象方法其实是一种渐进式优化的思想，允许子类针对该逻辑进行优化，但是如果性能差异不大的话也可以复用父类的逻辑。

 #### 节点流：FileInputStream & FileOutputStream

1. **类定位**

- **节点流**：直接对接数据源（文件）的流，是字节流的 “基础实现”。
- **作用**：读写**二进制文件**（图片、视频、压缩包、.class 文件等），也可读写文本文件（但推荐字符流）。

2. **核心技能：手写文件读写代码**

无缓冲的文件复制（效率低，仅作对比）

```java
public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        copy("test.txt", "test2.txt");
        System.out.println("耗时：" + (System.nanoTime() - start));
    }
public static void copy(String src, String dest) throws IOException {
        try(InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest)){
            int len;
            while((len = in.read()) != -1){
                out.write(len);
            }
        }
    }
```

#### 处理流：BufferedInputStream & BufferedOutputStream

1. **类定位**

- **处理流 / 包装流**：不直接对接数据源，而是**包装节点流**，增强节点流的功能。
- **核心功能**：通过内存缓冲区，提升字节流的读写性能。

2. **缓冲原理（面试必背）**

1. **内存缓冲区**：创建缓冲流时，会在内存中开辟一块固定大小的缓冲区（默认 8192 字节，可自定义）。
2. **读操作优化**：调用 `read()` 时，缓冲流会一次性从磁盘读取**缓冲区大小**的字节到内存，后续 `read()` 直接从内存取数据，直到缓冲区为空，再触发下一次磁盘 IO。
3. **写操作优化**：调用 `write()` 时，数据先写入内存缓冲区，当缓冲区满或调用 `flush()` 时，才一次性将数据写入磁盘，减少写 IO 次数。

```java
/**
 * 带缓冲复制，使用缓冲流
 * @param src
 * @param dest
 * @throws IOException
 */
private static final int BUFFER_SIZE = 4096;
public static void copy2(String src,String dest) throws IOException {
    try(InputStream in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
        OutputStream out = new BufferedOutputStream(new FileOutputStream(dest), BUFFER_SIZE)){
        byte[] buffer = new byte[BUFFER_SIZE];
        int len;
        while((len = in.read(buffer)) != -1){
            out.write(buffer, 0, len);
        }
    }
}
```

**关键知识点**

- **缓冲数组大小选择**：推荐设为 **1024 或 4096 字节**，过小会增加 IO 次数，过大则浪费内存（缓冲区太大时，内存拷贝耗时会抵消 IO 优化的收益）。
- **out.write (buffer, 0, len) 的必要性**：最后一次读取时，缓冲区可能未填满，若直接写 `buffer` 会写入多余的 0 字节，导致目标文件损坏。

#### 资源释放：try-with-resources（工程化必备）

1. **传统资源释放的问题**

- 手动调用 `close()` 时，需在 `finally` 块中处理，代码冗余且易出错（如忘记关闭、关闭顺序错误）。
- 示例：

```java
InputStream in = null;
OutputStream out = null;
try {
    in = new FileInputStream("test.mp4");
    out = new FileOutputStream("copy.mp4");
    // 读写操作
} catch (IOException e) {
    e.printStackTrace();
} finally {
    try {
        if (out != null) out.close();
        if (in != null) in.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

2. **try-with-resources 语法**

（1） 语法格式

```java
try (资源声明1; 资源声明2) {
    // 资源操作
} catch (异常类型 e) {
    // 异常处理
}
```

（2） 核心原理（面试必答）

- 资源类必须实现 **`AutoCloseable` 接口**（`InputStream`/`OutputStream` 及其子类均已实现）。
- JVM 会在 `try` 块执行完毕后，**自动调用资源的 `close()` 方法**，且关闭顺序与声明顺序相反（先关 out，再关 in）。
- 即使发生异常，资源也会被保证关闭，避免内存泄漏。

3. **强制规范**

- 所有流操作**禁止手动写 `close()`**，全部使用 try-with-resources 管理资源。
- 多资源声明时，用分号分隔，无需关注关闭顺序。

### 字符流

#### 核心抽象类：Reader & Writer

1. **类定位**

- 所有**字符输入流**的父类是 `Reader`，所有**字符输出流**的父类是 `Writer`。
- 二者均为**抽象类**，无法直接实例化，仅定义字符流的核心操作规范。

2. **核心面试考点：为什么是抽象类？**

- **设计思想**：采用**模板方法模式**，定义字符读写的通用流程，将具体实现交给子类（如文件流、网络流）。
- **场景适配**：不同的字符数据源（文件、网络、内存）的读写逻辑差异极大，抽象类可屏蔽底层差异，提供统一的 `read()`/`write()` 接口。
- **扩展性**：新增数据源类型时，只需继承抽象类并实现抽象方法，无需修改上层代码。

3. **核心方法（Reader）**

| 方法                    | 作用                                                         | 效率对比           |
| ----------------------- | ------------------------------------------------------------ | ------------------ |
| `int read()`            | 读取**单个字符**，返回字符的 Unicode 码（0-65535），读到末尾返回 -1 | 低（频繁磁盘 IO）  |
| `int read(char[] cbuf)` | 读取**批量字符**到数组 `cbuf`，返回实际读取的字符数，末尾返回 -1 | 高（减少 IO 次数） |
| `void close()`          | 关闭流，释放资源                                             | -                  |

4. **面试必答：read (char []) 比 read () 高效的原因**

- **磁盘 IO 特性**：磁盘读写的最小单位是**扇区**（通常 512 字节），单次 IO 操作的开销远大于内存数据拷贝。
- **read () 缺陷**：每次仅读取 1 字符，即使只需要 1 字符，也会触发 1 次磁盘 IO，读取 1024 字符需要 1024 次 IO。
- **read (char []) 优势**：每次读取 `cbuf.length` 字符（如 4096），读取 1024 字符仅需 1 次 IO，**批量读写大幅减少 IO 次数**，这是效率提升的核心。

> 观察源码我们会发现字节流和字符流的抽象基类中的read抽象方法职责不同，字节流中的抽象方法read声明是int read()而字符流中的抽象方法read声明是int read(char[],int off,int len)，这样做的核心原因在于InputStream强制的是实现最基本的单字节读取逻辑，而Reader强制实现高效的批量字符读取逻辑

#### 节点流：FileReader & FileWriter

1. **类定位**

- **节点流**：直接对接数据源（文件）的流，是字符流的 “基础实现”。
- **作用**：读写**文本文件**（如 .txt、.java 文件等）。

2. **核心技能：手写文件读写代码**

 无缓冲的文件复制（效率低，仅作对比）

```java
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LowEfficiencyCopy {
    public static void copy(String src, String dest) throws IOException {
        // try-with-resources 自动关闭资源
        try (FileReader in = new FileReader(src);
             FileWriter out = new FileWriter(dest)) {

            int len;
            // 每次读取单个字符
            while ((len = in.read()) != -1) {
                out.write(len);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        copy("test.txt", "low_copy.txt");
        System.out.println("无缓冲复制耗时：" + (System.currentTimeMillis() - start) + "ms");
    }
}
```

####  处理流 ：InputStreamReader & OutputStreamWriter & BufferedReader & BufferedWriter

1. **类定位**

- **处理流 / 包装流**：不直接对接数据源，而是**包装节点流**，增强节点流的功能。
- **核心功能**：将字节流转换为字符流，并指定字符编码，解决中文乱码问题。BufferedReader和BufferedWriter通过内存缓冲区，提升字符流的读写性能，并提供按行读写的便捷方法。

2. **核心原理（面试必背）**

* **字节流到字符流的转换**：`InputStreamReader` 从字节流中读取字节数据，根据指定的字符编码将其解码为字符；`OutputStreamWriter` 将字符数据根据指定的字符编码编码为字节，然后写入字节流。

* **字符编码的作用**：字符编码规定了字符与字节之间的映射关系，常见的字符编码有 UTF-8、GBK、ISO-8859-1 等。使用正确的字符编码可以确保字符数据的正确传输和显示，避免乱码。

* **内存缓冲区**：创建缓冲流时，会在内存中开辟一块固定大小的缓冲区（默认 8192 字符，可自定义）。

* **读操作优化**：调用 `read()` 或 `readLine()` 时，缓冲流会一次性从字符流中读取**缓冲区大小**的字符到内存，后续读取直接从内存取数据，直到缓冲区为空，再触发下一次字符流读取。
* **写操作优化**：调用 `write()` 时，数据先写入内存缓冲区，当缓冲区满或调用 `flush()` 时，才一次性将数据写入字符流，减少写操作次数。

3. **代码**

   ```java
   public static void main(String[] args) throws IOException {
       read("test.txt");
       write("test.txt", "666");
   }
   /**
    * 带缓冲的字符写入
    * @param dest
    * @param content
    * @throws IOException
    */
   public static void write(String dest,String content) throws IOException {
       try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dest),StandardCharsets.UTF_8))){
           bw.write(content);
           bw.newLine();
       }
   }
   /**
    * 将字节流转换为字符流读取
    * @param src
    * @throws IOException
    */
   public static void read(String src) throws IOException {
       try(BufferedReader br = new BufferedReader(
               new InputStreamReader(new FileInputStream(src), StandardCharsets.UTF_8))){
           String line;
           if((line = br.readLine())!= null){
               System.out.println(line);
           }
       }
   }
   ```

   单通过read方法我们会发现这个方法里面涉及了三个类的使用，接下来先简单介绍一下BufferedReader，InputStreamReader，FileInputStream。

   **`FileInputStream` (字节流)**

   - **类型**：低级字节输入流 (`InputStream` 的子类)。
   - **作用**：直接与文件系统交互，以**字节**为单位读取文件的原始数据。
   - **特点**：
     - 不关心文件内容的编码格式（如 UTF-8、GBK）。
     - 读取的是二进制数据（0 和 1），需要手动转换为字符。

   **`InputStreamReader` (字符流)**

   - **类型**：字节流到字符流的**桥接器** (`Reader` 的子类)。
   - **作用**：将 `FileInputStream` 读取的**字节流**按照指定的字符集（如 UTF-8）解码为**字符流**。
   - **特点**：
     - 解决了字符编码问题，避免直接操作字节导致乱码。
     - 每次读取一个或多个字符，但**无缓冲**，频繁调用会导致性能问题。

   **`BufferedReader` (字符流)**

   - **类型**：带缓冲的字符输入流 (`Reader` 的子类)。
   - **作用**：在 `InputStreamReader` 之上增加**缓冲区**，批量读取字符到内存，减少磁盘 I/O 次数。
   - **特点**：
     - 提供 `readLine()` 方法，方便按行读取文本。
     - 缓冲机制显著提升读取效率。

#### 面试高频考点汇总

1. **字节流和字符流的区别？**

   答：字节流处理二进制数据，无编码转换；字符流处理文本数据，带编码转换。字节流以字节为单位读写数据，适用于处理各种类型的文件，如图片、视频、音频等；字符流以字符为单位读写数据，适用于处理文本文件，如 .txt、.java 文件等。

2. **为什么会出现中文乱码？怎么解决？**

   答：中文乱码通常是由于编码和解码不一致导致的。在 Java 中，字符数据在内存中以 Unicode 编码存储，当进行文件读写或网络传输时，需要将字符数据转换为字节数据，这个过程称为编码；反之，将字节数据转换为字符数据的过程称为解码。如果编码和解码使用的字符编码不一致，就会导致字符数据的错误转换，从而出现乱码。解决中文乱码的方法是使用正确的字符编码进行编码和解码，在 Java 中，可以使用InputStreamReader和OutputStreamWriter指定字符编码，如 UTF-8。

3. **BufferedReader 的 readLine () 有什么坑？**

   答：readLine()方法会读取一行文本，但不包括换行符。如果文件的最后一行没有换行符，readLine()方法可能会返回null，导致末尾的行数据丢失。为了避免这个问题，可以在读取文件时，使用while ((line = br.readLine()) != null)的循环条件，并在循环体内处理每一行数据。另外，readLine()方法会阻塞线程，直到读取到换行符或文件末尾，因此在处理大文件时，需要注意性能问题。

4. **为什么生产环境不用 FileReader/FileWriter？**

   答：FileReader和FileWriter默认使用系统的字符编码，在跨平台场景下可能导致乱码。此外，它们没有提供缓冲功能，读写性能较低。在生产环境中，推荐使用InputStreamReader和OutputStreamWriter指定字符编码，并结合BufferedReader和BufferedWriter提供缓冲功能，以提高读写性能和避免乱码问题。

### 序列化与反序列化

#### 序列化的核心接口：Serializable

1. **接口定位**

- **标记接口（Marker Interface）**：`Serializable` 接口是一个空接口，不包含任何方法定义。
- **作用**：它的存在是为了**给 Java 虚拟机（JVM）一个明确的信号**，表明实现了该接口的类的对象可以被序列化。

2. **核心面试考点：为什么需要序列化？**

- **对象的本质**：对象在内存中是复杂的结构（包含字段、方法、引用等），无法直接被存储到磁盘文件或通过网络传输。
- **序列化的作用**：将对象的状态信息（即其非 `transient` 字段的值）转换为一个**字节序列**。这个字节序列可以：
  - **持久化存储**：写入到文件、数据库或缓存系统中。
  - **网络传输**：通过网络发送到另一个运行中的 Java 虚拟机（JVM）。
- **反序列化的作用**：将序列化生成的字节序列，**重新恢复**成一个与原对象状态完全相同的新对象。

3. **关键概念：对象状态**

- 序列化保存的是对象的**状态（State）**，而不是对象本身。
- 对象状态由其所有  **非静态（non-static）、非瞬态（non-transient）**字段的值构成。
- **静态字段**属于类，不属于单个对象，因此不会被序列化。
- **瞬态字段**（被 `transient` 修饰）会被显式地排除在序列化过程之外。

#### 序列化与反序列化的工具类：ObjectInputStream & ObjectOutputStream

1. **类定位**

- **处理流 / 包装流**：它们不能直接操作文件，而是必须**包装一个节点流**（如 `FileInputStream` / `FileOutputStream`）来使用。
- **核心功能**：
  - `ObjectOutputStream`：负责将实现了 `Serializable` 接口的对象写入到输出流中（序列化）。
  - `ObjectInputStream`：负责从输入流中读取字节序列，并将其恢复为对象（反序列化）。

#### 版本控制：serialVersionUID

1. **核心作用（面试必答）**

- `serialVersionUID` 是一个**序列化版本号**，用于在反序列化时验证序列化对象的发送者和接收者是否加载了与序列化兼容的类。
- 它的本质是一个 `long` 类型的常量，用来唯一标识一个可序列化类的版本。

2. **为什么必须手动指定？（面试高频）**

- **自动生成的隐患**：如果不手动指定，JVM 会根据类的结构（如字段、方法、接口等）**自动计算**一个默认的 `serialVersionUID`。
- **兼容性问题**：当你修改了类（例如，增加或删除了一个字段），即使这个修改对序列化的数据结构没有影响，JVM 重新计算出的 `serialVersionUID` 也可能会改变。
- **反序列化失败**：如果接收方（或从文件读取时）的类版本与序列化时的类版本的 `serialVersionUID` 不一致，会抛出 `InvalidClassException`，导致反序列化失败。
- **手动指定的好处**：通过显式声明 `serialVersionUID`，你可以**控制类的版本兼容性**。只要版本号不变，即使类有微小改动，反序列化也能成功。

3. **最佳实践**

- **强制指定**：为所有实现了 `Serializable` 接口的类**手动添加**一个 `serialVersionUID`。
- **格式统一**：通常定义为 `private static final long serialVersionUID = 1L;` 或其他你认为合适的数字。
- **版本升级**：当类的修改导致其序列化结构发生**不兼容**的变化时（例如删除了一个重要字段），应该**递增** `serialVersionUID`，以明确告知使用者该版本不兼容。

4. **如果一个类的父类没有实现 `Serializable` 接口，该类可以被序列化吗？**

   答：可以，但有条件。

   - 该类自身必须实现 `Serializable`。
   - 其所有父类必须提供一个**无参构造函数**。
   - 在反序列化时，JVM 会先调用父类的无参构造函数来初始化父类部分的状态，然后再恢复子类的序列化状态。如果父类没有无参构造函数，会抛出 `InvalidClassException`。

### NIO 基础（面试加分项，工作进阶用）

#### NIO 与 BIO 的核心区别

1. **技术定位**

- **BIO (Blocking I/O)**：传统的阻塞式 IO 模型，是 Java 早期的 IO 标准。
- **NIO (Non-blocking I/O)**：JDK 1.4 引入的非阻塞 IO 模型，全称是 `java.nio` (New I/O)。

2. **核心差异对比（面试必背）**

| 特性         | BIO (Blocking I/O)            | NIO (Non-blocking I/O)                 |
| ------------ | ----------------------------- | -------------------------------------- |
| **通信模型** | **面向流** (Stream Oriented)  | **面向缓冲区** (Buffer Oriented)       |
| **阻塞特性** | **阻塞** (Blocking)           | **非阻塞** (Non-blocking)              |
| **选择器**   | 无                            | **Selector** (选择器)                  |
| **线程模型** | 一个连接一个线程              | **Reactor 模式**，一个线程管理多个连接 |
| **核心组件** | `InputStream`, `OutputStream` | `Channel`, `Buffer`, `Selector`        |
| **适用场景** | 连接数较少且固定的场景        | **高并发、高吞吐量**的网络应用         |

3. **核心面试考点：NIO 为什么比 BIO 更适合高并发？**

- **BIO 的瓶颈**：在高并发场景下，BIO 会为每个客户端连接创建一个独立的线程。当连接数成千上万时，会创建大量线程，这会带来巨大的**内存开销**和**线程上下文切换**的性能损耗，最终导致系统崩溃。
- **NIO 的优势**：
  1. **非阻塞 I/O**：线程发起一个 IO 请求后，不必等待数据准备就绪，可以去处理其他任务。当数据准备好后，操作系统会通知线程，线程再进行读写。
  2. **IO 多路复用 (Selector)**：这是 NIO 实现高并发的核心。一个 `Selector` 对象可以监听多个 `Channel` 的事件（如连接建立、数据可读）。一个线程通过 `Selector` 就能管理成百上千个客户端连接，极大地减少了线程数量和上下文切换的开销。

#### NIO 核心组件：Channel 与 Buffer

1. **Channel (通道)**

（1） 概念定位

- **通道**是 NIO 中负责连接数据源与缓冲区的对象，类似于 BIO 中的流，但功能更强大。
- **核心特点**：
  - **双向性**：通道可以同时进行读写操作，而流（Stream）是单向的（输入流或输出流）。
  - **基于缓冲区**：所有数据的读写都必须通过 `Buffer` 对象进行，不能直接读写数据。
  - **支持异步**：通道可以在非阻塞模式下工作。

（2） 常用 Channel 类型

| 通道类型              | 作用                        | 对应 BIO 流                            |
| --------------------- | --------------------------- | -------------------------------------- |
| `FileChannel`         | 用于文件的读写              | `FileInputStream` / `FileOutputStream` |
| `SocketChannel`       | 用于 TCP 网络连接的客户端   | `Socket`                               |
| `ServerSocketChannel` | 用于 TCP 网络连接的服务器端 | `ServerSocket`                         |
| `DatagramChannel`     | 用于 UDP 网络连接           | `DatagramSocket`                       |

2. **Buffer (缓冲区)**

（1） 概念定位

- **缓冲区**是 NIO 中用于临时存储数据的对象，是所有 NIO 操作的数据容器。
- **本质**：一个连续的内存块，附带了一些高效管理数据的索引变量。

（2） 核心属性 (面试高频)

- **容量 (Capacity)**：缓冲区能够容纳的数据元素的最大数量。这是在创建时设定的，并且永远不能改变。
- **限制 (Limit)**：缓冲区中可以被访问的元素的界限。位于 `limit` 之后的元素是不能被读写的。
- **位置 (Position)**：下一个要被读取或写入的元素的索引。读写操作会自动更新 `position`。
- **标记 (Mark)**：一个备忘位置。调用 `mark()` 来设定 `mark = position`，之后可以调用 `reset()` 来将 `position` 恢复到标记的值。

**关系**：`0 <= mark <= position <= limit <= capacity`

（3） 常用 Buffer 类型

- `ByteBuffer` (最常用)
- `CharBuffer`
- `ShortBuffer`
- `IntBuffer`
- `LongBuffer`
- `FloatBuffer`
- `DoubleBuffer`

3. 编码横向对比

```java
public static void main(String[] args){
    try {
        copyFileByStream("C:\\Users\\Adieu\\Desktop\\1.jpg", "C:\\Users\\Adieu\\Desktop\\2.jpg");
        copyFileByNIO("C:\\Users\\Adieu\\Desktop\\1.jpg", "C:\\Users\\Adieu\\Desktop\\2.jpg");
        copyFileByBuffer("C:\\Users\\Adieu\\Desktop\\1.jpg", "C:\\Users\\Adieu\\Desktop\\2.jpg");
        Long start = System.currentTimeMillis();
        copyFileByNIO("C:\\Users\\Adieu\\Desktop\\1.jpg", "C:\\Users\\Adieu\\Desktop\\2.jpg");
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        copyFileByStream("C:\\Users\\Adieu\\Desktop\\1.jpg", "C:\\Users\\Adieu\\Desktop\\2.jpg");
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        copyFileByBuffer("C:\\Users\\Adieu\\Desktop\\1.jpg", "C:\\Users\\Adieu\\Desktop\\2.jpg");
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
/**
 * 使用缓冲流复制文件
 * @param src
 * @param dest
 * @throws IOException
 */
public static void copyFileByBuffer(String src,String dest) throws IOException {
    try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest))){
        byte[] buffer = new byte[4096];
        while((bis.read(buffer))!=-1){
            bos.write(buffer);
        }
    }
}
/**
 * 使用节点流复制文件
 * @param src
 * @param dest
 * @throws IOException
 */
public static void copyFileByStream(String src,String dest) throws IOException {
    try(FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(dest)){
        int len;
        while((len = fis.read())!=-1){
            fos.write(len);
        }
    }
}
/**
 * NIO复制文件
 * @param src
 * @param dest
 * @throws IOException
 */
public static void copyFileByNIO(String src,String dest) throws IOException {
    try(FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(dest);
        FileChannel inchannel = fis.getChannel();
        FileChannel outchannel = fos.getChannel()){
        ByteBuffer bb = ByteBuffer.allocate(4096);
        while(inchannel.read(bb)!=-1){
            bb.flip();
            outchannel.write(bb);
            bb.compact();
        }
    }
}
```

以上结论在面对文件较大时候为NIO > 处理流 >> 节点流，而文件较少的时候可能会出现处理流 > NIO的情况。
