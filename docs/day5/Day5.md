---
Day5:Git
Author:Adieu
Date:2025/12/17
---

[toc]

# git

## branch

### 核心定义

`branch`是 Git 中用于**隔离开发线**的轻量级指针，本质指向某个 `commit`（提交节点）。默认情况下，Git初始化的时候会创建一个main分支。

可以看出branch实质上是在版本时间线上创建一个时间线，合并的时候，对于main分支中没有的内容会进行合并，而对于已有内容进行更改的部分会继续手动决策保留哪一部分。通过以上的功能，我们可以产生以下这些作用。

### 核心作用

1. 隔离功能开发：比如开发新功能时创建 `feature/xxx` 分支，不影响主分支的稳定代码；
2. 修复紧急问题：创建 `hotfix/xxx` 分支修复线上 Bug，修复后合并回主分支；
3. 多开发者协作：不同开发者在各自分支开发，避免代码冲突。

### 常用命令

```bash
git branch          # 查看本地所有分支（当前分支前标*）
git branch dev      # 创建名为dev的分支（仅创建，不切换）
git checkout dev    # 切换到dev分支（旧版命令）
git switch dev      # 切换到dev分支（新版推荐命令）
git checkout -b dev # 创建并切换到dev分支（常用快捷方式）
git branch -d dev   # 删除本地dev分支（需先合并）
git branch -D dev   # 强制删除本地dev分支（未合并也能删）
```

## commit

### 核心定义

`commit` 是 Git 中**保存代码变更的最小单元**，相当于给当前工作区的代码拍了一张 “快照”，并记录变更说明、作者、时间等信息，每个 `commit` 有唯一的哈希值（如 `a1b2c3d`）作为标识。

### 核心作用

1. 记录代码变更：每次完成一个小功能 / 修复一个 Bug 后，提交形成可追溯的版本；
2. 备份代码：本地代码推到远程，避免本地机器故障导致代码丢失；
3. 触发协作流程：比如推送到远程后可发起 Pull Request/Merge Request 合并代码。

### 常用指令

```bash
git commit
git reset --hard commit哈希码
git reset --soft commit哈希码
git log
git reflog
```

## push

### 核心定义

`push` 是将**本地仓库的分支 / 提交**上传到远程仓库（如 GitHub、GitLab、Gitee）的操作，目的是让协作的其他人能获取你的代码变更。

### 核心作用

1. 同步本地变更：将本地的 `commit` 推送到远程分支，完成代码共享；
2. 备份代码：本地代码推到远程，避免本地机器故障导致代码丢失；
3. 触发协作流程：比如推送到远程后可发起 Pull Request/Merge Request 合并代码。

