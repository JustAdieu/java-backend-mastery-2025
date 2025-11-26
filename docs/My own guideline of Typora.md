---
A service manual of Typora
Author: Alan Adieu
Date: 2025/1/15
---

# My own guideline of Typora

## Overview

[toc]

## Basic grammar

### Paragraph and line breaks

If you need one blank line to create a new paragraph, you should press `Shift`+`Enter` to create ==a single line break==. Otherwise, pressing `Enter` would create ==two lines== by default.

This is an example of source code:

```markdown
a single line break:
.....+`Shift`+`Enter`
two lines break:
.....+`Enter`
```

This is an example of result:

**a single line break:**
Yo, yo, yo
Hey, man.

**two lines break:**

Yo, yo, yo.

Hey, man.

### Headings

If you want to create a heading, just use 1-6 hash(`#`) characters + `Space`at the start of the line which represents the biggest to the smallest size of heading.

For examples:

```markdown
# This an H1
## This an H2
### This an H3
```

### Blockquotes

When you want to quote some words or list something, you could use `>`+`Space` to start a paragraph .

For examples:

```markdown
> Here are some of the quotes you want to make.
> 
```

This is an example of result:

> May you just want to find some quotes.

### Lists

Two ways you could use to list something. 

==**First way**==

* red
* green
* blue

[^footnote ]: Use  `*` +  `Space`

==**Second way**==

1. red
2. green
3. blue

[^footnote]: Use `1` +  `.`

### Task List

As the Markdown Reference goes:

> Task lists are lists with items marked as either [ ] or [x] (incomplete or complete).

For example:

```markdown
- [ ] a task list item
- [x] completed 
```

This is the result:

- [ ] oh

- [x] oh

### Code Blocks

Just input *```* and language name you want like *java* or *markdown*.

For example:

```markdown
```java+"Enter"
```

### Tables

As the reference goes:

> Input `| First Header  | Second Header |` and press the `return` key. This will create a table with two columns.

They look like:

```markdown
| First | Second |

```

Their effect is this:

| First | Second |
| ----- | ------ |
|       |        |

[^footnote]: Use the ==Setting== to set the size or specification of this table.

### Footnotes

May you just need to write `[^footnote]:`. Then you could note something you want.

### Horizontal Rules 

Inputting `***`or `---` on a blank line and pressing **return** .

Like this:

***

### YAML Front Matter

At the top of the article inputting `---`. Then you could write something?

### Table of Contents

Input `[toc]` and press the `Return` key. 

The affection like this:
[toc]

### Emphasis

This section is divided into these types:

* _italic_ 

[^way]: use `*` or `_` at the start and end of the word(s) you want

* **bold**

[^way]: use `**` or `__`

* ==highlight==

[^way]: use `==`

* ~~Strikethrough~~

[^way]: use `~~`

* `Code`

[^way]: use **`**

* <u>Underline</u>

[^way]: `<u>Underline</u>`

* H~2~O

[^way]: `H~2~O`

* X^2^

[^way]: `X^2^`

* <span style="color:red">this text is red</span>

[^way]:`<span style="color:red">this text is red</span>`

* :smile:

[^way]:input `:smile:`or you could press `win` + `.`.

### Link

#### Inline links

==Format:==

```markdown
This is [an example](http://example.com/ "Title") inline link.

[This link](http://example.net/) has no title attribute.
```

[an example](http://example.com/ "Title")

[This link](http://example.net/)

[^note]:**Internal Links**

> Sometimes you could input like `[Overview](#Overview)`to jump to **Overview** .
> Like this [Overview](#Overview)

#### Reference Links

Well, here are two ways to use reference links.

```markdown
==The first:==
This is [an example][id] reference-style link.

Then, anywhere in the document, you define your link label on a line by itself like this:

[id]: http://example.com/  "Optional Title Here"
==The Second:==
[Google][]
And then define the link:

[Google]: http://google.com/
```

### Bring in pictures or videos

#### Images

The format of insert an image looks like this:

```markdown
![Alt text](/path/to/img.jpg)

![Alt text](/path/to/img.jpg "Optional title")
```

#### Video

```markdown
<video src="xxx.mp4" />
```

