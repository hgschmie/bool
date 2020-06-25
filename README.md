# bool - a simple boolean expression parser

evaluates simple boolean expressions.

- TRUE, FALSE (constants)
- AND, OR (boolean operations)
- NOT (negation)
- ( ) (parens)
- arbitrary identifiers can be provided with a simple function. Names can be alphanumeric and _, must start with character or _.

## Usage

```java

    Function<String, Boolean> valueProvider = (name) -> {
        switch(name) {
            case "foo":
            case "bar":
                return Boolean.TRUE;
            case "baz":
            default:
                return Boolean.FALSE;
        }
    };

    BoolEval eval = new BoolEval("foo AND (bar OR NOT baz)");
    boolean result = eval.evaluate(valueProvider);
```

## Maven

```xml
<dependency>
    <groupId>de.softwareforge</groupId>
    <artifactId>bool</artifactId>
    <version>1.0</version>
</dependency>
```

## Changes

 [Keep a Changelog v1.0.0](http://keepachangelog.com/en/1.0.0/).

### Unreleased

### Version 1.0.0 (2020-06-24)

#### Added
* First release

----
[![Build Status](https://travis-ci.org/hgschmie/bool.svg?branch=master)](https://travis-ci.org/hgschmie/bool)[![Latest Release](https://maven-badges.herokuapp.com/maven-central/de.softwareforge/bool/badge.svg)](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22de.softwareforge%22%20AND%20a%3A%22bool%22)
