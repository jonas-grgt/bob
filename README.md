# 👷‍Bob [![Maven Central](https://img.shields.io/maven-central/v/io.jonasg/bob-annotations.svg)](https://search.maven.org/artifact/io.jonasg/bob-annotations)

🪶Lightweight Builder generator for Java

## Why Bob?

Bob serves as a lightweight alternative to Lombok's `@Builder` annotation.
Its retention policy is `SOURCE`, ensuring it won't clutter your bytecode.
Bob generates a builder in the form of pure Java source code.

## Installation
### Maven
```xml
<dependency>
    <groupId>io.jonasg</groupId>
    <artifactId>bob-annotations</artifactId>
    <version>${bob.version}</version>
    <scope>compile</scope>
</dependency>
<dependency>
    <groupId>io.jonasg</groupId>
    <artifactId>bob-processor</artifactId>
    <version>${bob.version}</version>
    <scope>provided</scope>
</dependency>
```
### Gradle
```groovy
dependencies {
  annotationProcessor "io.jonasg:bob-processor:" + bobVersion
  compileOnly "io.jonasg:bob-annotations:" + bobVersion
}
```

## Getting Started

Annotate the class with `@Buildable` to generate a builder for it.
    
```java
package my.garage;

@Buildable
public class Car {
	
    private Brand brand;
    private String color;
    private BigDecimal price;
	
    public Car(Brand brand, String color, BigDecimal price) {
        this.brand = brand;
        this.color = color;
        this.price = price;
    }
}
```

## Usage

### Basic Usage

By default,
Bob will look for the constructor with the most parameters
and will create setters for all parameters that have a matching field name. 
For parameters
that do not have a corresponding field, the default value for that type will be used.
In example `null` for `Integer` and zero for `int`.

If your class contains multiple constructors that tie for having the most parameters,
the first one will be selected. 
See `@BuildableConstructor` if you want to change this behavior.

```java
@Buildable
public class Car {
    private String color;
    private BigDecimal price;
    
    public Car(Brand brand, int year, String color, BigDecimal price) {
        this.color = color;
        this.price = price;
    }
    
}
```

The generated builder will look like this:
When building a car instance in this way `new CarBuilder().color("red").price(BigDecimal.ZERO).build();`
The car will be instantiated with the following constructor call:

```java
new Car(null, 0, "red", BigDecimal.ZERO);
```

### Different constructor

If you want to use a different constructor instead of the default selected one, annotated it with `@BuildableConstructor`

### Package
    
A `CarBuilder` class will be generated in the same package as the source class with *builder* as suffix.
For the car example this will be `my.garage.CarBuilder`

The location of the builder can be changed:

```java
@Buildable(packageName = "my.other.garage")
public class Car {
```

### Pickup setter methods as buildable

When Bob encounters setters (with or without the set prefix)
and a corresponding field it will add the fields to the final builder.

In the below example,
if though `color` is not part of the constructor it will be part of the final generated Builder
because there is a setter available, which will be used.

```java
@Buildable
public class Car {
    private Brand brand;
    private String color;

    public Car(Brand brand) {
        this.brand = brand;
    }
	
    public void color(String color) {
        this.color = color;
    }
}
```
            
### Field exclusion

```java
@Buildable(excludeFields = {"brand", "color"})
public class Car {
```

### Setter prefix
      
By default Bob will generated setter methods consisting out of *new style setters* (`name(String name)` instead of `setName(String name)` or the default builder pattern setter style `withName(String name)`)
If you want to change the prefix of those setter methods you can:

```java
@Buildable(setterPrefix = "with")
public class Car {
```

### Records

Bob can work with Records and function just as normal java classes

```java
@Buildable
public record Record(String name, int age) {
}
```

### Generics

Bob is not afraid of generics

```java
@Buildable
public class Cup<T, R extends String> {
    private T contents;
    private R topping;
```

Can be used as:
    
```java
Cup<BigDecimal, String> string = new CupBuilder<BigDecimal, String>().topping("cream")
    .contents(BigDecimal.ZERO)
    .build();
```

or alternatively:

```java
CupBuilder.of(BigDecimal.class, String.class)
    .topping("cream")
    .contents(BigDecimal.ZERO)
    .build();
```
