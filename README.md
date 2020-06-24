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
