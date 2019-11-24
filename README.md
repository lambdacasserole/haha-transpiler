# Humoresque
Extensible HAHA transpiler.

![Logo](assets/logo.svg)

## Overview
Humouresque is a transpiler/code extraction utility to turn HAHA proofs into programs. Any function written using the [Hoare Advanced Homework Assistant (HAHA)](http://haha.mimuw.edu.pl/) can be converted into a function in any of the supported target languages. Currently only Java is suppported as a transpilation target. The transpiler project and [parser project](https://github.com/lambdacasserole/haha-parser/) are separate, though this one depends on that one.

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
| input_file    | Any                 | Yes       | The HAHA source file to transpile.                                        |
| `-e`          | None                | No        | Enumeration mode. Prints out the names of all functions in the file.      |
| `-f`          | `fname1,fname2,...` | No        | Transpile only specific functions with names in the comma-delimited list. |
| `-a`          | `fname`             | No        | Arity mode. Gets the number of arguments taken by the specified function. |
| `-t`          | Target language     | No        | Specify target language. Defaults to Java. Only Java currently supported. |

Results are written to standard output.

### Example
As an example, consider the following HAHA source file `sum.haha` that performs addition of two numbers:

```pascal
function sum (x : Z, y : Z) : Z
precondition y >= 0
postcondition sum = x + y
var ans : Z
    n : Z
begin
	ans := x
	{ y >= 0 /\ ans = x }
	n := y
	{ y >= 0 /\ n >= 0 /\ ans = x /\ n = y }
	while n != 0 do
	invariant y >= 0 /\ n >= 0 /\ ans + n = x + y 
	begin
		ans := ans + 1
		{ y >= 0 /\ n > 0 /\ ans + n - 1 = x + y } 
		n := n - 1
		{ y >= 0 /\ n >= 0 /\ ans + n = x + y }
		skip
	end
	{ n = 0 /\ ans + n = x + y }
	sum := ans
end
```

Running Humoresque with `java -jar humoresque.jar sum.haha`, we get the following output:

```java
int sum(int x, int y)
{
	int ans;
	int n;
	int sum;
	ans = x;
	/* y >= 0 /\ ans = x */
	n = y;
	/* y >= 0 /\ n >= 0 /\ ans = x /\ n = y */
	while(n != 0)
	{
		ans = ans + 1;
		/* y >= 0 /\ n > 0 /\ ans + n - 1 = x + y */
		n = n - 1;
		/* y >= 0 /\ n >= 0 /\ ans + n = x + y */
	}
	/* n = 0 /\ ans + n = x + y */
	sum = ans;
	return sum;
}
```

As you can see, all proof-related stuff is erased, aside from annotations which are preserved as comments.

## Limitations
This tool is currently subject to the same limitations as [the parser it's built on](https://github.com/lambdacasserole/haha-parser/), plus a few others:
* This project uses precisely *none* of the original HAHA source code. It may therefore be the case that code that works just fine in HAHA causes the parser to choke. This can usually be worked around with minor syntax tweaks.
* This project is not formally verified, nor does it generate/discharge any verification conditions. Hoare logic annotations are completely ignored, except to be erased during transpilation (actually, they're converted to comments, but same thing).

## Contributing
For most intents and purposes, this project is considered to fulfil its original use case. Bug fixes and suggestions are welcome, however, from any member of the community.

## Acknowledgements
A big thank you to the team at the [University of Warsaw](https://mimuw.edu.pl/en) behind [HAHA](http://haha.mimuw.edu.pl/). This really is an awesome tool for teaching software verification! By name, they're:
* [Tadeusz Sznuk](http://www.mimuw.edu.pl/~tsznuk/)
* Jacek Chrząszcz
* [Aleksy Schubert](http://www.mimuw.edu.pl/~alx/)
* Jakub Zakrzewski
