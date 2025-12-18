---
Day4：Maven
Author: Adieu
Date: 2025/12/12
---

[toc]

# Maven

## 核心基础

### Maven核心认识

* **作用和价值：**

  * **统一结构**

    Maven定义了标准化的项目目录结构，所有遵循Maven规范的项目结构一致，降低了团队协作，项目维护的成本

    ```
    项目根目录
    ├── pom.xml          # Maven 核心配置文件（POM）
    ├── src
    │   ├── main         # 主代码/资源目录（生产环境）
    │   │   ├── java     # 核心Java源码（固定路径）
    │   │   └── resources# 配置文件（如application.yml、mybatis.xml）
    │   └── test         # 测试代码/资源目录（测试环境）
    │       ├── java     # 测试Java源码（如JUnit用例）
    │       └── resources# 测试专用配置
    └── target           # 构建输出目录（编译后的class、打包后的jar/war）
    ```

  * **自动化构建**

    Java 项目构建包含 “编译、测试、打包、安装、部署” 等一系列流程，手动执行（如用 `javac` 编译、`jar` 命令打包）效率低且易出错。

    Maven 提供了**一键式构建命令**，通过简单指令自动化完成全流程：

    - `mvn compile`：编译主源码
    - `mvn test`：编译 + 运行测试用例
    - `mvn package`：编译 + 测试 + 打包（生成 jar/war 到 target）
    - `mvn install`：打包后安装到本地仓库（供本地其他项目依赖）
    - `mvn deploy`：安装后部署到远程仓库（供团队共享）

  * **依赖管理**

    Java 项目依赖大量第三方库（如 Spring、MyBatis），手动管理存在三大痛点：

    - 需手动下载 jar 包，易遗漏 / 版本错误；
    - 依赖存在 “传递性”（如 Spring-core 依赖 commons-logging），手动梳理复杂；
    - 多环境 jar 包版本冲突难以解决。

    Maven 的依赖管理能力：

    - **自动下载**：只需在 POM 中声明依赖，Maven 自动从仓库下载 jar 包；
    - **传递依赖**：自动解析并下载依赖的依赖（可通过 `exclusions` 排除不需要的依赖）；
    - **版本控制**：统一管理依赖版本，避免版本冲突（如通过 `dependencyManagement` 统一声明版本）；
    - **依赖范围**：区分依赖的使用场景（如 `compile` 编译运行都需要、`test` 仅测试用、`provided` 容器提供无需打包）。

* **Maven三大核心要素**

  * **Pom**是Maven的核心，Maven所有操作都是基于POM执行的。

    **核心作用**：

    - 定义项目基本信息（GAV 坐标、描述、开发者等）；
    - 声明项目依赖（第三方库、自定义模块）；
    - 配置构建规则（编译插件、打包方式、JDK 版本等）；
    - 定义项目继承 / 聚合（多模块项目核心）。

  * **坐标（GAV）-- 项目/依赖的唯一标识**

    | 维度       | 含义                                                         |
    | ---------- | ------------------------------------------------------------ |
    | GroupId    | 组织 / 公司标识（通常是反向域名，避免冲突）                  |
    | ArtifactId | 项目 / 模块名称（具体的产品名）                              |
    | Version    | 版本号（遵循语义化版本，如主版本。次版本。修订版，可加 SNAPSHOT 表示快照版） |

  * **仓库 -- 依赖的“存储仓库”**

    1. 本地仓库

    2. 远程仓库

       1. 中央仓库
       2. 私服
       3. 其他公共仓库

    3. 仓库的查找顺序

       本地仓库 → 私服（若配置） → 中央仓库 → 其他远程仓库（按配置顺序）。

* **核心设计理念：约定优于配置**

  Maven 预先定义了一套默认规则（约定），开发者无需手动配置这些规则，仅需在 “不符合约定” 时才做少量配置。

## JUnit实战测试

### 搭建项目

首先，为了方便我们学习Maven搭建项目的架构，我们采取的是Java中的Maven搭建方式，方便我们从开始就可以很好实操。首先项目结构如下：

<img src="C:\Users\Adieu\AppData\Roaming\Typora\typora-user-images\image-20251216215119940.png" alt="image-20251216215119940" style="zoom:50%;" />

我们可以通过IDEA中的选项将例如main下的java目录标记为源代码根目录。

搭建完基本框架后我们需要的是在xml中进行导入依赖以及加载依赖，随后就可以编写测试代码了。

Calculator：

```java
package com.demo;

public class Calculator {
    public int add(int a,int b){
        return a + b;
    }
}

```

CalculatorTest：

```java
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

```

然后再手动运行测试方法或者使用Maven中的Test可以一键测试所有的测试方法，最终生成一个完备的报告显示测试情况。

### 依赖排除

在上面的情况中由于测试比较简单，我们没法很好理解到项目中的依赖冲突情况。所以接下来我们会手动添加依赖冲突版本再解决一下这个问题。不过在此之前，我们先要了解一下什么是依赖冲突。

> 依赖冲突是指项目引入的多个依赖简介引用了同一个类库的不同版本，导致最终运行时使用的版本不符合预期进而引发报错。本质就是Maven会自动下载依赖的间接依赖，这个过程中如果同一个类库的不同版本被引用，就会产生冲突。

OK，这个时候解决依赖冲突就是一个很有必要的问题了。首先我们要先知道哪些版本产生了冲突，点击maven中的显示图：
<img src="C:\Users\Adieu\AppData\Roaming\Typora\typora-user-images\image-20251217195948397.png" alt="image-20251217195948397" style="zoom:50%;" />

随后我们就可以在图中清晰看到哪些产生了冲突。解决冲突的方法通常是使用exclusion进行排除，我们可以在terminal中使用`mvn dependency:tree -Dverbase`命令查看目前的直接依赖以及间接依赖。然后根据需求再到对应的依赖中使用exclusion进行解决依赖冲突。

### 多模块项目管理

