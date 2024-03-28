# Type of Expections

In Java, exceptions are categorized into two main types: checked exceptions and unchecked exceptions.

1. `Checked Exceptions:` These are exceptions that are checked at compile time. This means that the compiler ensures that the code handles these exceptions either by catching them using a try-catch block or by declaring them to be thrown in the method signature.

2. `Unchecked Exceptions:` Also known as runtime exceptions, these exceptions are not checked at compile time. They occur at runtime and typically represent programming errors such as invalid cast, null pointer dereference, or arithmetic overflow.

Here's a simple example demonstrating both checked and unchecked exceptions:

```java
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ExceptionExample {
    public static void main(String[] args) {
        // Checked Exception: FileNotFoundException
        try {
            FileInputStream fileInputStream = new FileInputStream("nonexistent_file.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        // Unchecked Exception: ArithmeticException
        try {
            int result = 10 / 0; // Division by zero
        } catch (ArithmeticException e) {
            System.out.println("Arithmetic error: " + e.getMessage());
        }
    }
}
```

In this example:

- We attempt to open a file "nonexistent_file.txt" using FileInputStream, which may throw a checked exception FileNotFoundException. We handle this exception using a try-catch block.
- We attempt to perform division by zero (10 / 0), which will result in an unchecked exception ArithmeticException. We also handle this exception using a try-catch block.

Both checked and unchecked exceptions provide mechanisms to handle errors gracefully in Java programs.

`Checked Exceptions:`

1. ClassNotFoundException
2. NoSuchMethodException
3. SQLException
4. ParseException
5. IOException

`Unchecked Exceptions:`

1. NullPointerException
2. ArrayIndexOutOfBoundsException
3. IllegalArgumentException
4. IllegalStateException
5. NumberFormatException

# finally

In Java, the finally block is used in conjunction with try and catch blocks to handle exceptions and ensure that certain code is always executed, regardless of whether an exception is thrown or not. The finally block contains code that you want to execute regardless of whether an exception occurs or not. Here's a simple explanation with code:

```java
public class FinallyExample {
    public static void main(String[] args) {
        try {
            // Code that might throw an exception
            int result = 10 / 0; // This will throw an ArithmeticException
            System.out.println("Result: " + result); // This line won't be executed
        } catch (ArithmeticException e) {
            System.out.println("Exception caught: " + e.getMessage());
        } finally {
            // Code that will always execute
            System.out.println("Finally block executed.");
        }
    }
}
```

In this example:

- We have a try block where we're attempting a division by zero operation, which will throw an ArithmeticException.
- We catch the ArithmeticException in the catch block and print an error message.
- We have a finally block which will always execute, regardless of whether an exception occurs or not. In this block, we print a message indicating that the finally block is executed.

Now, regarding the cases where finally may not execute:

1. If the JVM exits while the try block is executing (for example, due to a `System.exit()` call), the finally block may not execute.
2. If there's a fatal error or crash in the JVM that prevents further execution, the finally block may not execute.
3. If the thread executing the try block is interrupted, and there's no corresponding catch block to handle InterruptedException, the finally block may not execute.
4. In Java 7 and earlier, if there's a return statement within the try or catch blocks, and there's also a return statement in the finally block, the return statement in the finally block will override any return statement in the try or catch blocks. This can lead to the finally block not executing if the finally block contains a return statement.
5. If there's a power outage or hardware failure while the try block is executing, the finally block may not execute.

These are some of the cases where the finally block may not execute. However, in most normal execution scenarios, the finally block will execute as expected, ensuring that cleanup or necessary actions are performed regardless of exceptions or other conditions.