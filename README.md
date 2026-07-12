# 👷‍Bob [![Maven Central](https://img.shields.io/maven-central/v/io.jonasg/bob-annotations.svg)](https://search.maven.org/artifact/io.jonasg/bob-annotations) [![License](https://img.shields.io/github/license/jonas-grgt/bob.svg)](https://opensource.org/licenses/Apache-2.0)

🪶 Lightweight Builder generator for Java. A feature-rich alternative to Lombok's `@Builder` with support for **step builders**, **strict validation**, and more.

## Quick start

Annotate a class with `@Buildable` and Bob generates a fluent builder at compile time:

```java
package my.garage;

@Buildable
public class Car {
    private String color;
    private int year;

    public Car(String color, int year) {
        this.color = color;
        this.year = year;
    }
}
```

Use it right away:

```java
Car car = new CarBuilder()
    .color("red")
    .year(2024)
    .build();
```

The builder is generated in the same package as your class (e.g. `my.garage.CarBuilder`).

## Installation

<details>
<summary>Maven (Java 23+)</summary>

Since Java 23, [annotation processors require explicit configuration](https://bugs.openjdk.org/browse/JDK-8321319) to prevent [supply chain attacks](https://xdev.software/en/news/detail/discovering-the-perfect-java-supply-chain-attack-vector-and-how-it-got-fixed).

```xml
<dependencies>
    <dependency>
        <groupId>io.jonasg</groupId>
        <artifactId>bob-annotations</artifactId>
        <version>${bob.version}</version>
        <scope>compile</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <annotationProcessorPaths>
                    <path>
                        <groupId>io.jonasg</groupId>
                        <artifactId>bob-processor</artifactId>
                        <version>${bob.version}</version>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    </plugins>
</build>
```
</details>

<details>
<summary>Maven (Java 22 and earlier)</summary>

```xml
<dependencies>
    <dependency>
        <groupId>io.jonasg</groupId>
        <artifactId>bob-annotations</artifactId>
        <version>${bob.version}</version>
    </dependency>
    <dependency>
        <groupId>io.jonasg</groupId>
        <artifactId>bob-processor</artifactId>
        <version>${bob.version}</version>
    </dependency>
</dependencies>
```
</details>

<details>
<summary>Gradle</summary>

```groovy
dependencies {
    annotationProcessor "io.jonasg:bob-processor:" + bobVersion
    compileOnly "io.jonasg:bob-annotations:" + bobVersion
}
```
</details>

## @Buildable reference

| Attribute         | Type         | Default      | Description                                                   |
|-------------------|--------------|--------------|---------------------------------------------------------------|
| `strategy`        | `Strategy[]` | `PERMISSIVE` | Controls builder behavior (see [Strategies](#strategies))     |
| `mandatoryFields` | `String[]`   | `{}`         | Field names that must be set before `build()`                 |
| `excludeFields`   | `String[]`   | `{}`         | Fields to exclude from the generated builder                  |
| `setterPrefix`    | `String`     | `""`         | Prefix for setter methods (e.g. `"with"` → `.withColor(...)`) |
| `packageName`     | `String`     | `""`         | Override the package for the generated builder                |
| `factoryName`     | `String`     | `""`         | Name for the static factory method on the builder             |

## How it works

Bob selects the constructor with the most parameters and creates a setter for each parameter whose name matches a field.
Parameters without a matching field receive a default value (`null` for objects, `0` for numbers, `false` for booleans).
If multiple constructors tie for most parameters, the first one wins. Use `@Buildable.Constructor` to pick a different
one.

Public setter methods (matching a field name, with or without `set`/`with` prefix) are also detected and added to the
builder.

## Strategies

| Strategy               | Behavior                                                                                     |
|------------------------|----------------------------------------------------------------------------------------------|
| `PERMISSIVE` (default) | Any field can be left unset and automatically defaults to `null`, `0`, or `false`            |
| `STRICT`               | All fields must be explicitly set; throws `MandatoryFieldMissingException` if not            |
| `STEP_WISE`            | Generates a step-builder interface that enforces constructor parameter order at compile time |
| `ALLOW_NULLS`          | Companion to `STRICT`/`STEP_WISE`, allows setting mandatory fields to `null`                 |

```java
@Buildable(strategy = STRICT)
class Car { ... }
```

```java
@Buildable(strategy = { STRICT, ALLOW_NULLS })
class Car { ... }
```

```java
@Buildable(strategy = STEP_WISE)
class Car { ... }
```

### STEP_WISE in detail

Constructor parameters become ordered steps. Optional fields (see below) are excluded from the step chain and appear as
extra setters in a `BuildStep` interface:

```java
@Buildable(strategy = STEP_WISE)
public class Car {
    private String make;
    private int year;
    private String color;

    public Car(String make, @Buildable.Optional int year, String color) {
        this.make = make;
        this.year = year;
        this.color = color;
    }
}
```

Usage enforces the parameter order, `make` → `color` → optionally `year` → `build()`:

```java
CarBuilder.newBuilder()
    .make("Tesla")
    .color("red")
    .year(2024)     // optional, can be omitted
    .build();
```

## Mandatory & Optional fields

With `STRICT` or `STEP_WISE`, all constructor-matched fields are mandatory by default. Mark fields as optional to opt out.

**On a field:**

```java

@Buildable(strategy = STRICT)
public class Car {
    private String brand;

    @Buildable.Optional
    private int year;
}
```

**On a constructor parameter:**

```java
@Buildable(strategy = STRICT)
public class Car {
    private String brand;
    private int year;

    public Car(String brand, @Buildable.Optional int year) {
        this.brand = brand;
        this.year = year;
    }
}
```

**Additional mandatory fields** beyond constructor parameters can be declared via annotation attributes or field
annotations, and work with all strategies:

```java
@Buildable(mandatoryFields = {"color"})
public class Car {
    @Buildable.Mandatory
    private String color;
```

In `PERMISSIVE` mode, `@Mandatory` fields are enforced at runtime, the builder throws `MandatoryFieldMissingException`
if not set. `@Mandatory` also applies to record components.

In `STRICT` strategy, all constructor parameters are mandatory by default, so placing `@Mandatory` on a constructor
parameter is redundant — a compiler warning is emitted.

> `@Mandatory` and `@Buildable.Defaults` cannot be combined on the same field — doing so causes a compilation error.
> A mandatory field must be explicitly set; a default value for it is contradictory.

> **Note:** `@Buildable.Optional` and `ALLOW_NULLS` cannot be combined - doing so causes a compilation error.

## JSpecify support

Bob respects [JSpecify](https://jspecify.dev/) nullness annotations for per-field nullability decisions, giving you
finer control than the blanket `ALLOW_NULLS` strategy.

### `@Nullable`

When a field or constructor parameter is annotated with `@Nullable`, the generated builder will allow `null` as a valid
value, even when the strategy doesn't include `ALLOW_NULLS`.

```java

@Buildable(strategy = STRICT)
public class Car {
	private String make;
	private @Nullable String model;

	public Car(String make, String model) {
		this.make = make;
		this.model = model;
	}
}
```

`make` is enforced as non-null (`ofNoneNullableField`), while `model` accepts null (`ofNullableField`).

Works on constructor parameters directly:

```java
public Car(String make, @Nullable String model) { ...}
```

And on record components:

```java

@Buildable(strategy = STRICT)
public record Car(String make, @Nullable String model) {
}
```

> `@Nullable` only affects fields that are wrapped in `ValidatableField` i.e., constructor args in 
> `STRICT`/`ALLOW_NULLS` or fields annotated with `@Mandatory`. In `PERMISSIVE`, constructor args are plain fields 
> (always nullable) so `@Nullable` is a no-op unless combined with `@Mandatory`.

### `@NullMarked`

Mark a class or package with `@NullMarked` to indicate that unannotated types are non-null by default. Bob respects this
when deciding the nullability of each field, even under `ALLOW_NULLS`.

```java

@Buildable(strategy = {STRICT, ALLOW_NULLS})
@NullMarked
public class Car {
	private String make;
	private @Nullable String model;

	public Car(String make, String model) {
		this.make = make;
		this.model = model;
	}
}
```

Without `@NullMarked`, `ALLOW_NULLS` would make both `make` and `model` nullable. With `@NullMarked`, only explicitly
`@Nullable` fields (here `model`) are nullable, `make` stays non-null.

`@NullUnmarked` nesting is also respected: a `@NullUnmarked` class inside a `@NullMarked` package restores unspecified
nullness for that class.

### How does this relate to `@Mandatory` / `@Optional`?

| Concern                         | Bob annotation                   | JSpecify annotation                     |
|---------------------------------|----------------------------------|-----------------------------------------|
| Whether a field must be set     | `@Mandatory` / `@Optional`       | —                                       |
| Whether `null` is a valid value | `ALLOW_NULLS` strategy (blanket) | `@Nullable` / `@NullMarked` (per-field) |

They compose: `@Mandatory` + `@Nullable` = "must be set, but null is fine" (generates `NullableValidatableField`).
Without `@Nullable`, `ALLOW_NULLS` was the only way to permit null, and it applied to *all* mandatory fields, JSpecify
gives you per-field control.

## Features

### Default field values

Initialize builder fields with non-zero defaults using `@Buildable.Defaults`:

```java
@Buildable
public class Car {
	private String make;
	private int year;

	public Car(String make, int year) {
		this.make = make;
		this.year = year;
	}

	@Buildable.Defaults
	public static class Defaults {
		public static int year = 1985;
	}
}
```

The generated builder pre-initializes `year` to `1985` instead of `0`:

```java
// year is already set to1985, only override if needed
new CarBuilder()
    .make("Tesla")
    .build();
```

**As a top-level class** (specify which buildable type the defaults belong to):

```java
@Buildable.Defaults(Car.class)
public class CarDefaults {
	public static int year = 1985;
}
```

> Defaults class fields must be `static`. If the defaults class is in a different package from the buildable type,
> fields must be `public`.

**With `STRICT`:** fields with defaults are excluded from mandatory enforcement (i.e., constructor-arg-based
enforcement), the default satisfies the requirement.

> Fields explicitly marked with `@Mandatory` or set through the `@Buildable.mandatoryFields` property 
> **and** also having a default value via `@Buildable.Defaults` is contradictory, doing so causes a compilation error. 
> A field cannot be both mandatory and defaulted.

#### Lazy default values

Defaults declared as `Supplier<T>` are evaluated lazily at `build()` time, not when the builder is constructed.
This is useful when the default depends on runtime values like timestamps or UUIDs:

```java
@Buildable
public class Order {
	private String id;

	public Order(String id) {
		this.id = id;
	}

	@Buildable.Defaults
	public static class Defaults {
		public static Supplier<String> id = () -> UUID.randomUUID().toString();
	}
}
```

The generated builder stores the `Supplier` and calls `.get()` only when `build()` is invoked:

```java
Order order = new OrderBuilder()
    .build();  // UUID generated here
```

If the caller explicitly provides a value via the setter, it is wrapped in a supplier internally:

```java
Order order = new OrderBuilder()
    .id("123")  // defaults are overridden
    .build();
```

This also works with `STRICT` — a `Supplier<T>` default satisfies the mandatory check and the field is excluded from enforcement.

### Test defaults

But what if you wanted to define defaults only for test purposes? 
Then there is no good reason to have `Buildable.Defaults` annotated classes in `src/main` right?
Also no-one wants test defaults on their production classpath!

`@Buildable.TestDefaults` allows you to define defaults in `src/test` while your buildable types live in
`src/main`. It works exactly like `@Buildable.Defaults`, but the generated builder will only pick up the defaults 
**only if the test class is on the classpath**.

Goes in `src/main` and is blissfully unaware of the existence of test defaults:
```java
@Buildable
public class Car {
    private String color;
    private int year;
}
```

Goes into `src/test` and is aware of the existence of test defaults:
```java
@Buildable.TestDefaults(Car.class)
public class CarDefaults {
    public static String color = "blue";
}
```

### Records

All strategies and features also work with records:

```java
@Buildable
public record Record(String name, int age) { }
```

### Generics

```java
@Buildable
public class Cup<T, R extends String> {
    private T contents;
    private R topping;
}

Cup<BigDecimal, String> cup = new CupBuilder<BigDecimal, String>()
		.topping("cream")
		.contents(BigDecimal.ZERO)
		.build();

// or with type tokens:
CupBuilder.of(BigDecimal.class, String.class)
    .topping("cream")
    .contents(BigDecimal.ZERO)
    .build();
```

### Setter prefix

```java
@Buildable(setterPrefix = "with")
public class Car { ... }
// → new CarBuilder().withColor("red")
```

### Field exclusion

```java
@Buildable(excludeFields = {"brand", "color"})
public class Car { ... }
```

### Custom package

```java
@Buildable(packageName = "my.other.garage")
public class Car { ... }
// → my.other.garage.CarBuilder
```

### Static factory method

```java
@Buildable(strategy = STEP_WISE, factoryName = "car")
public class Car { ... }
// → CarBuilder.car().make("...")...
```

For `PERMISSIVE`/`STRICT`, the factory name adds a static method (useful for documentation or avoiding name conflicts).

### Setter pickup

Bob detects methods that match a field name (with or without `set`/`with` prefix) and adds them to the builder:

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
// → new CarBuilder().brand(...).color(...).build()
```

### Different constructor

If Bob picks the wrong constructor, annotate the one you want:

```java
@Buildable
public class Car {
    public Car() { ... }

    @Buildable.Constructor
    public Car(String color, int year) { ... }
}
```
