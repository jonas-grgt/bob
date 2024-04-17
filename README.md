# 👷‍Bob [![Maven Central](https://img.shields.io/maven-central/v/io.jonasg/bob-annotations.svg)](https://search.maven.org/artifact/io.jonasg/bob-annotations) [![License](https://img.shields.io/github/license/jonas-grgt/bob.svg)](https://opensource.org/licenses/Apache-2.0)


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

### Constructor enforcement

When constructing Java objects,
ensuring that all necessary constructor parameters are provided is essential
for maintaining the integrity and proper initialization of the object's state.
To facilitate strict parameter enforcement and manage the creation process,
a `ConstructorPolicy` can be utilized.

By default, the constructor policy is quite lenient,
allowing objects
to be partially constructed without setting all fields specified in the constructor.
This default behavior is governed by the `ConstructorPolicy.PERMISSIVE` setting.

However, the `ConstructorPolicy` also provides an `ENFORCED` mode.
When this policy is active,
it is mandatory to supply all constructor parameters when creating a new object.
If any required parameters are missing,
the policy enforces strict compliance by throwing an `MandatoryFieldMissingException`.
This ensures that every object is fully initialized as intended,
preventing issues that arise from improperly constructed objects.

```java
@Buildable(constructorPolicy = ENFORCED)
public class Car {
```

Another `ConstructorPolicy` that also enforces all the parameters to be set, but less strictly is
`ENFORCED_ALLOW_NULLS`. By using this policy, you enforce all variables to be set, 
but you can also set them to null, which is not allowed when using `ENFORCED`.

```java
@Buildable(constructorPolicy = ENFORCED_ALLOW_NULLS)
public class Car {
```

### Mandatory Fields

Fields can be designated as mandatory;
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
