# Bob
Lightweight Builder generator for Java

## Why Bob?

Bob serves as a lightweight alternative to Lombok's `@Builder` annotation.

## Getting Started

Annotate the class you which to build with `@Buildable`
    
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
    
    // getters toString and hashcode left out for brevity
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

```java
@Buildable
public class Car {
    private String color;
    private BigDecimal price;
    
    public Car(Brand brand, int year, String color, BigDecimal price) {
        this.color = color;
        this.price = price;
    }
    
    // getters toString and hashcode left out for brevity
}
```

The generated builder will look like this:
When building a car instance in this way `new CarBuilder().color("red").price(BigDecimal.ZERO).build();`
The car will be instantiated with the following constructor call:

```java
new Car(null, 0, "red", BigDecimal.ZERO);
```

If you want to use a different constructor instead of the default selected one, annotated it with `@BuildableConstructor`

### Package
    
A `CarBuilder` class will be generated in the same package as the source class with *builder* as suffix.
For the car example this will be `my.garage.CarBuilder`

The location of the builder can be changed:

    @Buildable(package = "my.other.garage")
    public class Car {
      ...
            
The generated builder can be used now:

    Car redCar = new CarBuilder().color("red").build();

Fields can be excluded:

    @Buildable(excludes = {"brand", "color"})
    public class Car {
      ...
      
By default Bob will generated setter methods consisting out of *new style setters* (`name(String name)` instead of `setName(String name)` or the default builder pattern setter style `withName(String name)`)
If you want to change the prefix of those setter methods you can:

    @Buildable(prefix = "with")
    public class Car {
      ...      
      
Bob is not afraid of generics

    @Buildable
    public class Cup<T, R extends String> {
        private T contents;
        private R topping;
        
    // usage
    
    Cup<BigDecimal, String> string = new CupBuilder<BigDecimal, String>().topping("String").contents(BigDecimal.ZERO).build();
    
Bob can handle final fields    

    @Buildable
    public class Car {
        private final String color; 
        
        public Car(String color) {
            ....
