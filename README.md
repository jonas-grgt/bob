# 👷‍Bob [![Maven Central](https://img.shields.io/maven-central/v/io.jonasg/bob-annotations.svg)](https://search.maven.org/artifact/io.jonasg/bob-annotations) [![License](https://img.shields.io/github/license/jonas-grgt/bob.svg)](https://opensource.org/licenses/Apache-2.0)


🪶Lightweight Builder generator for Java

## Why Bob?

Bob serves as a lightweight alternative to Lombok's `@Builder` annotation with additional 
features such as the ability to create Step Builders.

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
See `@Buildable.Constructor` if you want to change this behavior.

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

When building a car instance in this way `new CarBuilder().color("red").price(BigDecimal.ZERO).build();`
The car will be instantiated with the following constructor call:

```java
new Car(null, 0, "red", BigDecimal.ZERO);
```

Because `brand` and `year` aren't fields the default value for the corresponding types are used.


### Different constructor

If you want to use a different constructor instead of the default selected one, annotated it with `@Buildable.Constructor`

### Builder Strategies

The Strategy enumeration defines the strategies by which builders behave.

#### Permissive

The default strategy,
allows the creation of an object
even if not all constructor parameters are set or if some are set to null.
Fields not explicitly set will default to their inherent values
(e.g., null for object references, 0 for numeric types, and false for booleans).
This strategy is suitable when not all fields need explicit initialization,
allowing more flexibility.

```java
@Buildable
class Car {
```

#### Step Wise

Implements a step builder pattern,
requiring fields
to be set in a structured sequence
defined by the selected constructor's parameters and explicitly marked mandatory fields
(see [Mandatory Fields](#Mandatory-Fields)).
Each step must be completed before proceeding to the next,
ensuring all fields are set before the object can be constructed.

```java
@Buildable(strategy = STEP_WISE)
class Car {
```

#### Strict
Requires all mandatory fields to be explicitly set.
If a field is not set,
or is set to null, the builder throws a `MandatoryFieldMissingException`.
This ensures that the object is fully initialized.

```java
@Buildable(strategy = STRICT)
class Car {
```

#### Allow Nulls
Enables setting mandatory fields to null explicitly,
combinable with `STRICT` and `STEP_WISE`.
If a field is omitted, the builder will throw a `MandatoryFieldMissingException`,
maintaining strict initialization but allowing null values for flexibility.

```java
@Buildable(strategy = { STRICT, ALLOW_NULLS })
class Car {
```
### Mandatory Fields

Fields can be marked as mandatory;
- through the `mandatoryFields` property of `@Buildable`
- through annotating the field with @Buildable.Mandatory.
 
Similar to the constructor parameters in the ENFORCED mode,
the omission of these required fields when building an object will trigger a MandatoryFieldMissingException.
This mechanism ensures that all necessary fields are set before an object is finalized.

```java
@Buildable(mandatoryFields = {"color"})
public class Car {
    private String color;
```

```java
@Buildable
public class Car {
    @Buildable.Mandatory
    private String color;
```
### Change Default Package
    
A `CarBuilder` class will be generated in the same package as the source class with *builder* as suffix.
For the car example this will be `my.garage.CarBuilder`

The location of the builder can be changed:

```java
@Buildable(packageName = "my.other.garage")
public class Car {
```

### Static Factory Method name

The `factoryName` `@Buildable` property allows:
- `STEP_WISE`: Rename builder starting method (e.g., builder to createProductBuilder).
- `PERMISSIVE/STRICT`: Add extra name to static factory method (for documentation/avoid conflicts).

```java
@Buildable(strategy = STEP_WISE, factoryName = "car")
public class Car {
```
Which will generate:
```java
CarBuilder.car();
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
