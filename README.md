# Humoresque
Extensible HAHA transpiler.

![Logo](assets/logo.svg)

## Overview
Humouresque is a transpiler/code extraction utility to turn HAHA proofs into programs. Any function written using the [Hoare Advanced Homework Assistant (HAHA)](http://haha.mimuw.edu.pl/) can be converted into a function in any of the supported target languages. Currently only Java is suppported as a transpilation target. A few things to note:
+ The transpiler project and [parser project](https://github.com/lambdacasserole/haha-parser/) are separate, though this one depends on that one.
+ This project uses precisely *none* of the original HAHA source code. It may therefore be the case that code that works just fine in HAHA causes the parser to choke. This can usually be worked around with minor syntax tweaks.
+ This project is not formally verified, nor does it generate/discharge any verification conditions. Hoare logic annotations are completely ignored, except to be erased during transpilation (actually, they're converted to comments, but same thing).

## Building
This is a [Maven](https://maven.apache.org/) project and can be built like one. To generate classes:

```bash
mvn compile
```

Or to package everything up as a jarfile:

```bash
mvn package
```

Personally, however, I prefer to open up the project in [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/) and build the jarfile as an artifact through the UI and project system.

## Installation
This project is intended to be compiled into a jarfile and used as a standalone utility. If you like, however, you can pull this package into your Maven project straight from here using JitPack. Add JitPack as a repository first:

```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Then add this project as a dependency:

```
<dependencies>
    <dependency>
        <groupId>com.github.lambdacasserole</groupId>
        <artifactId>haha-transpiler</artifactId>
        <version>v1.0</version>
    </dependency>
</dependencies>
```

## Usage
With the built jarfile, there are a few different application modes at your disposal. Briefly, use the program like this:

```
java -jar humoresque.jar [-efat] <input_file>
```

The options are quite straightforward:

| Option        | Values              | Required? | Description                                                               |
|---------------|---------------------|-----------|---------------------------------------------------------------------------|
| input_file    | Any                 | Yes       | The file containing unseen data.                                          |
| `-e`          | None                | No        | Enumeration mode. Prints out the names of all functions in the file.      |
| `-f`          | `fname1,fname2,...` | No        | Transpile only specific functions with names in the comma-delimited list. |
| `-a`          | `fname`             | No        | Arity mode. Gets the number of arguments taken by the specified function. |
| `-t`          | Target language     | No        | Specify target language. Defaults to Java. Only Java currently supported. |

Results are written to standard output. The `plain` format produced comma-delimited output with headings. The `coq` format option will produce a lookup structure compatible with [the Coq proof assistant](https://coq.inria.fr/).
