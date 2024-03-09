# Bob
Builder generator for Java

## Getting Started

Annotate the class you which to build with `@Buildable`
    
    package foo.bar

    @Buildable
    public class Car {
        private Brand brand;
        private String color;
        private BigDecimal price;
        
        ...
    }
    
A `CarBuilder` class will be generated for you in the same package as the source class with *builder* as suffix.
For the car example this will be `foo.bar.builder`

The location of the builder can be changed:

    @Buildable(package = "custom.package")
    public class Car {
      ...
            
The generated builder can be used now:

    Car redCar = new CarBuilder().color("red").build();

Bob will try to be smart about creating a builder for you. 
* If there are *standard* Java Bean setters available they will be used. (`setField`) 
* If you do not have any setters reflection will be used.
* If the fields are accessible directly (public or protected fields) they will be set directly.

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
