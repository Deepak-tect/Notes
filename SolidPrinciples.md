# Explanation of Single Responsibility Principle (SRP)

The Single Responsibility Principle (SRP) is a fundamental principle in software development that states that a class should have only one reason to change, meaning it should have only one responsibility or purpose. In other words, a class should do one thing and do it well. Let's illustrate this principle with an example:

## Example: User Authentication System

Suppose we have a user authentication system that manages user registration, login, and password reset functionalities. Initially, the system might have a single class responsible for handling all these functionalities:

```java

class AuthenticationSystem {
    public void registerUser(String username, String password) {
        // Register new user
    }

    public boolean loginUser(String username, String password) {
        // Login user
        return false;
    }

    public void resetPassword(String username) {
        // Reset user's password
    }
}

```

In this initial implementation, the AuthenticationSystem class handles multiple responsibilities: user registration, user login, and password reset. This violates the Single Responsibility Principle because the class has more than one reason to change. For example, if the authentication logic needs to be updated, modified, or extended, changes to one method might inadvertently affect the behavior of other methods.

## Refactoring to Adhere to SRP

```java
class UserRegistration {
    public void registerUser(String username, String password) {
        // Register new user
    }
}

class UserLogin {
    public boolean loginUser(String username, String password) {
        // Login user
        return false;
    }
}

class PasswordReset {
    public void resetPassword(String username) {
        // Reset user's password
    }
}

```

Each class now has a single responsibility and encapsulates a specific aspect of the overall functionality. This adheres to the Single Responsibility Principle because each class has only one reason to change. For example, if there are changes to the user registration process, only the UserRegistration class needs to be modified, and other classes remain unaffected.

# Explanation of Open-Closed Principle (OCP)

The Open-Closed Principle (OCP) is a fundamental principle in object-oriented design that states that software entities (such as classes, modules, functions, etc.) should be open for extension but closed for modification. This means that the behavior of a system can be extended without modifying its existing codebase. Let's illustrate this principle with a simple example involving geometric shapes.

## Example: Shape Calculation System

Suppose we have a system that calculates the total area of shapes. Initially, the system directly depends on concrete implementations of shapes:

```java

class AreaCalculator {
    public double calculateTotalArea(Object[] shapes) {
        double totalArea = 0;
        for (Object shape : shapes) {
            if (shape instanceof Circle) {
                Circle circle = (Circle) shape;
                totalArea += circle.calculateArea();
            } else if (shape instanceof Square) {
                Square square = (Square) shape;
                totalArea += square.calculateArea();
            }
        }
        return totalArea;
    }
}

class Circle {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class Square {
    private double side;

    public Square(double side) {
        this.side = side;
    }

    public double calculateArea() {
        return side * side;
    }
}

```

In this initial implementation, the AreaCalculator class directly depends on concrete implementations of shapes (Circle and Square). If we want to add a new shape type, such as Triangle, we would need to modify the AreaCalculator class to handle the new shape, violating the Open-Closed Principle.

## Refactoring to Adhere to OCP

To adhere to the Open-Closed Principle, we can introduce an abstraction (interface) Shape:

```java

interface Shape {
    double calculateArea();
}

class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

// Concrete implementation for Square
class Square implements Shape {
    private double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }
}


```

Now, the AreaCalculator class can depend on the Shape interface:

```java
class AreaCalculator {
    public double calculateTotalArea(Shape[] shapes) {
        double totalArea = 0;
        for (Shape shape : shapes) {
            totalArea += shape.calculateArea();
        }
        return totalArea;
    }
}

```

With this modification, the AreaCalculator class depends on an abstraction (Shape interface) rather than concrete implementations. This allows new shapes to be added without modifying the AreaCalculator class, adhering to the Open-Closed Principle.

# Explanation of Liskov Substitution Principle (LSP)

The Liskov Substitution Principle (LSP) is a fundamental principle in object-oriented design, which states that objects of a superclass should be replaceable with objects of its subclasses without affecting the correctness of the program. In simpler terms, this means that derived classes must be substitutable for their base classes without altering the desirable properties of the program.

Let's illustrate the Liskov Substitution Principle with a classic example involving geometric shapes:

## Example: Geometric Shapes
Suppose we have a system that calculates and prints the area of different geometric shapes. We have a base class Shape and two derived classes Rectangle and Square:

```java
class Shape {
    public double calculateArea() {
        return 0; // Default implementation for unknown shape
    }
}

class Rectangle extends Shape {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return length * width;
    }
}

class Square extends Shape {
    private double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }
}

```
In this example, both Rectangle and Square inherit from the Shape class. According to the Liskov Substitution Principle, objects of the Rectangle and Square classes should be substitutable for objects of the Shape class without affecting the correctness of the program.

Now, let's use these classes to calculate and print the area of various shapes:

```java
public class Main {
    public static void printArea(Shape shape) {
        System.out.println("Area: " + shape.calculateArea());
    }

    public static void main(String[] args) {
        Shape rectangle = new Rectangle(4, 5);
        Shape square = new Square(4);
        //This statement illustrates polymorphism and inheritance in Java, where an object of a subclass (rectangle,square ) is instantiated and assigned to a reference variable of its superclass (Shape), showcasing the ability to treat subclass objects as superclass objects, promoting code flexibility and reusability.

        printArea(rectangle); // Outputs: Area: 20.0
        printArea(square);    // Outputs: Area: 16.0
    }
}


```
In this example, the printArea method accepts objects of type Shape, and both Rectangle and Square objects can be passed to this method without any issue. This demonstrates adherence to the Liskov Substitution Principle. The program behaves correctly regardless of whether a Rectangle or Square object is passed, indicating that derived classes (Rectangle and Square) are substitutable for their base class (Shape) without affecting the program's correctness.

This adherence to LSP allows for flexibility and extensibility in the design, as new shapes can be added in the future without impacting the existing functionality of the program.



# Explanation of Interface Segregation Principle (ISP)

The Interface Segregation Principle (ISP) is one of the five SOLID principles of object-oriented design, coined by Robert C. Martin. ISP states that "clients should not be forced to depend on interfaces they do not use." In simpler terms, it suggests that classes should not be forced to implement interfaces that contain methods they do not need.

### Interface Segregation Principle (ISP) Example

Let's consider a simple example involving interfaces and classes representing different types of electronic devices: a printer and a scanner.

We start with an interface called `MultiFunctionDevice` that combines the functionalities of printing and scanning:

```java
interface MultiFunctionDevice {
    void print();
    void scan();
}

class Printer implements MultiFunctionDevice {
    @Override
    public void print() {
        System.out.println("Printing...");
    }

    @Override
    public void scan() {
        // Empty implementation, as printers cannot scan
    }
}

class Scanner implements MultiFunctionDevice {
    @Override
    public void print() {
        // Empty implementation, as scanners cannot print
    }

    @Override
    public void scan() {
        System.out.println("Scanning...");
    }
}
```

In this example, we've created a single interface MultiFunctionDevice that combines the functionalities of both printing and scanning. However, not all devices support both functionalities in real-world scenarios.

To adhere to the Interface Segregation Principle (ISP), we should avoid forcing classes to implement methods they don't need. Instead of having a single interface, we can split it into smaller, more focused interfaces:

```java
interface Printer {
    void print();
}

interface Scanner {
    void scan();
}
```

And then our classes would implement only the interfaces they need:

```java
class BasicPrinter implements Printer {
    @Override
    public void print() {
        System.out.println("Printing...");
    }
}

class BasicScanner implements Scanner {
    @Override
    public void scan() {
        System.out.println("Scanning...");
    }
}
```

This way, each class only implements the methods that are relevant to its functionality, adhering to the Interface Segregation Principle. Classes are not forced to implement methods they don't need, resulting in cleaner, more maintainable code.

# Dependency Inversion Principle (DIP)

The Dependency Inversion Principle (DIP) is one of the five SOLID principles of object-oriented design. It states that high-level modules should not depend on low-level modules, but both should depend on abstractions. Additionally, abstractions should not depend on details; rather, details should depend on abstractions. This principle promotes loose coupling between modules and facilitates easier maintenance, extension, and testing.

Let's illustrate the Dependency Inversion Principle with a simple example:

#### Initial Implementation

```java
class LightBulb {
    public void turnOn() {
        System.out.println("Light bulb turned on");
    }

    public void turnOff() {
        System.out.println("Light bulb turned off");
    }
}

class LightSwitch {
    private LightBulb bulb;

    public LightSwitch() {
        this.bulb = new LightBulb(); // Dependency on concrete implementation
    }

    public void turnOn() {
        bulb.turnOn();
    }

    public void turnOff() {
        bulb.turnOff();
    }
}
```

In this example, `LightSwitch` directly creates an instance of `LightBulb`, creating a tight coupling between the two classes. If we want to switch to a different type of light source, such as a LED light, we would need to modify the `LightSwitch` class, violating the Open/Closed Principle.

To adhere to the Dependency Inversion Principle, we introduce an abstraction (interface) `Switchable` representing anything that can be turned on and off. Both `LightBulb` and any future light sources will implement this interface:

```java
interface Switchable {
    void turnOn();
    void turnOff();
}

class LightBulb implements Switchable {
    public void turnOn() {
        System.out.println("Light bulb turned on");
    }

    public void turnOff() {
        System.out.println("Light bulb turned off");
    }
}
```

Now, we modify LightSwitch to depend on the abstraction (Switchable) instead of the concrete implementation (LightBulb):

```java
class LightSwitch {
    private Switchable switchable;

    public LightSwitch(Switchable switchable) {
        this.switchable = switchable; // Dependency on abstraction
    }

    public void turnOn() {
        switchable.turnOn();
    }

    public void turnOff() {
        switchable.turnOff();
    }
}

```

With this change, LightSwitch no longer depends on the concrete implementation of the light source. Instead, it depends on the Switchable abstraction. This allows us to easily switch between different types of light sources without modifying the LightSwitch class, thus adhering to the Dependency Inversion Principle.
