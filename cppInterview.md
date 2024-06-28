# Constructor
A constructor is a special member function that is automatically called when an object of the class is created. The main purpose of a constructor is to initialize the object's member variables or perform any setup steps required.

## Characteristics of Constructors:
* Same Name as Class: A constructor has the same name as the class.
* No Return Type: Constructors do not have a return type, not even void.
* Automatically Called: They are called automatically when an object is instantiated.
* Can be Overloaded: You can have multiple constructors with different parameter lists.
## Types of Constructors:

* Default Constructor: A constructor that takes no arguments.
* Parameterized Constructor: A constructor that takes one or more arguments.
* Copy Constructor: A constructor that initializes an object using another object of the same class.

Example:

```cpp
#include <iostream>
using namespace std;

class MyClass {
public:
    int value;

    // Default constructor
    MyClass() {
        value = 0;
        cout << "Default constructor called" << endl;
    }

    // Parameterized constructor
    MyClass(int val) {
        value = val;
        cout << "Parameterized constructor called" << endl;
    }

    // Copy constructor
    MyClass(const MyClass &obj) {
        value = obj.value;
        cout << "Copy constructor called" << endl;
    }
};

int main() {
    MyClass obj1;           // Default constructor
    MyClass obj2(10);       // Parameterized constructor
    MyClass obj3 = obj2;    // Copy constructor

    return 0;
}

```

# Destructor
A destructor is a special member function that is automatically called when an object goes out of scope or is explicitly deleted. The main purpose of a destructor is to release resources that the object may have acquired during its lifetime.

## Characteristics of Destructors:
* Same Name as Class: A destructor has the same name as the class, but prefixed with a tilde ~.
* No Return Type: Destructors do not have a return type, not even void.
* No Parameters: Destructors cannot have parameters and thus cannot be overloaded.
* Automatically Called: They are called automatically when an object is destroyed.

Example:

```cpp
#include <iostream>
using namespace std;

class MyClass {
public:
    int* data;

    // Constructor
    MyClass(int size) {
        data = new int[size];
        cout << "Constructor called, memory allocated" << endl;
    }

    // Destructor
    ~MyClass() {
        delete[] data;
        cout << "Destructor called, memory deallocated" << endl;
    }
};

int main() {
    MyClass obj(10);   // Constructor is called
    // Destructor will be called automatically when obj goes out of scope

    return 0;
}

```

# Automatic Storage (Stack Allocation)
When you create objects using automatic storage, they are automatically destroyed when they go out of scope. This is simple and efficient for most use cases.

Example:

```cpp
int main() {
    MyClass obj1;           // Default constructor
    MyClass obj2(10);       // Parameterized constructor
    MyClass obj3 = obj2;    // Copy constructor

    return 0;               // Destructors are called for obj1, obj2, and obj3
}
```

# Dynamic Storage (Heap Allocation)
When you create objects using dynamic storage, you need to manually manage their lifetime using the new and delete keywords. This is useful when you need objects to persist beyond the scope of a function or when the lifetime of the objects needs to be managed explicitly.

Example:
```cpp
int main() {
    MyClass* obj1 = new MyClass();         // Default constructor
    MyClass* obj2 = new MyClass(10);       // Parameterized constructor
    MyClass* obj3 = new MyClass(*obj2);    // Copy constructor

    // Use the objects
    cout << "obj1 value: " << obj1->value << endl;
    cout << "obj2 value: " << obj2->value << endl;
    cout << "obj3 value: " << obj3->value << endl;

    // Manually delete objects to free memory
    delete obj1;
    delete obj2;
    delete obj3;

    return 0;
}

```
## Key Differences:

1. Automatic Storage:

    * Managed by the scope: Objects are automatically destroyed when they go out of scope.
    * Simpler and less error-prone for most cases.
    * No need to manually manage memory.

2. Dynamic Storage:

    * Managed manually: You need to use new to allocate and delete to deallocate.
    * Useful for objects that need to persist beyond their scope.
    * Requires careful memory management to avoid leaks.


## Choosing Between Stack and Heap Allocation
* `Stack Allocation` is preferred for most cases due to simplicity and automatic management.
* `Heap Allocation` is useful when you need more control over the object's lifetime, or when dealing with large objects or arrays that might exceed stack size limits.


#  Virtual function:
A virtual function in C++ is a member function in a base class that you expect to override in derived classes. When you use a base class pointer or reference to a derived class object, the overridden function in the derived class is called. This behavior is known as dynamic binding or late binding.

Here's a simple example to illustrate virtual functions:

Example:

```cpp
#include <iostream>
using namespace std;

// Base class
class Animal {
public:
    // Virtual function
    virtual void makeSound() {
        cout << "Animal sound" << endl;
    }
};

// Derived class
class Dog : public Animal {
public:
    // Override the base class virtual function
    void makeSound() override {
        cout << "Woof" << endl;
    }
};

// Another derived class
class Cat : public Animal {
public:
    // Override the base class virtual function
    void makeSound() override {
        cout << "Meow" << endl;
    }
};

int main() {
    // Create objects of derived classes
    Dog dog;
    Cat cat;

    // Create pointers of base class type
    Animal* animal1 = &dog;
    Animal* animal2 = &cat;

    // Call makeSound on base class pointers
    animal1->makeSound(); // Outputs: Woof
    animal2->makeSound(); // Outputs: Meow

    return 0;
}
```

### Key Points:
* Virtual Function: The virtual keyword in the base class function declaration allows derived classes to override it.
* Override: Derived classes provide their own implementation of the virtual function.
* Base Class Pointer: A pointer of the base class type can point to objects of derived classes.
* Dynamic Binding: At runtime, the appropriate overridden function is called based on the actual object type, not the pointer type.


# pure virtual function:

A pure virtual function is a function that has no implementation in the base class and must be overridden by all derived classes. A class containing at least one pure virtual function is called an abstract class, and it cannot be instantiated directly.

### Syntax
To declare a pure virtual function, you use the = 0 syntax in the base class.

### Example
Here is a simple example demonstrating the concept of pure virtual functions:

```cpp
#include <iostream>
using namespace std;

// Base class (abstract class)
class Animal {
public:
    // Pure virtual function
    virtual void makeSound() = 0;

    // Virtual destructor
    virtual ~Animal() {}
};

// Derived class
class Dog : public Animal {
public:
    void makeSound() override {
        cout << "Woof" << endl;
    }
};

// Another derived class
class Cat : public Animal {
public:
    void makeSound() override {
        cout << "Meow" << endl;
    }
};

int main() {
    // Create objects of derived classes dynamically
    Animal* animal1 = new Dog(); // Using new keyword
    Animal* animal2 = new Cat(); // Using new keyword

    // Call makeSound on base class pointers
    animal1->makeSound(); // Outputs: Woof
    animal2->makeSound(); // Outputs: Meow

    // Manually delete objects to free memory
    delete animal1;
    delete animal2;

    return 0;
}

```

## Explanation:

1. Base Class Animal:

    * Contains a pure virtual function makeSound() declared with = 0. This makes Animal an abstract class.
    * Contains a virtual destructor to ensure proper cleanup of derived class objects.

2. Derived Classes Dog and Cat:

    * Override the pure virtual function makeSound() with their specific implementations.

3. Main Function:

    * Creates objects of the derived classes Dog and Cat dynamically using the new keyword.
    * Uses base class pointers animal1 and animal2 to point to these derived class objects.
    * Calls the makeSound() function on these base class pointers, demonstrating polymorphism.

## Key Points:
* Pure Virtual Function: Declared with = 0, indicating that it must be overridden in any non-abstract derived class.
* Abstract Class: A class with at least one pure virtual function cannot be instantiated directly.
* Derived Classes: Must provide implementations for all pure virtual functions in the base class to be instantiable.
* Polymorphism: Allows derived class objects to be treated as objects of the base class, enabling dynamic method binding.
* Using pure virtual functions is a powerful way to define interfaces in C++ and ensure that derived classes provide specific implementations for required functions.


# What is the difference between new and malloc?

* new allocates memory and calls the constructor for object initialization, while malloc only allocates memory without initializing the object.
* new returns a pointer of the correct type and does not need to be cast, while malloc returns a void* that needs to be cast to the correct type.

# What is a memory leak and how can it be avoided?

A memory leak occurs when dynamically allocated memory is not deallocated after use, leading to a loss of available memory. It can be avoided by ensuring that every new or malloc is paired with a corresponding delete or free.


# friend function:

In C++, a friend function is a function that is not a member of a class but has access to the class's private and protected members. It is declared by using the friend keyword inside the class.

## Purpose of Friend Functions
* Access Private Members: Friend functions can access the private and protected members of a class, which allows them to operate on objects of the class more freely.
* Non-Member Functionality: Sometimes, certain functionality logically belongs outside of a class but still needs access to the class's internal state. Friend functions provide a way to achieve this without exposing the class's internal state publicly.

## Syntax and Example
Here's an example to illustrate how friend functions work:

```cpp
#include <iostream>
using namespace std;

class Box {
private:
    double width;

public:
    // Constructor to initialize width
    Box(double w) : width(w) {}

    // Friend function declaration
    friend void printWidth(Box box);
};

// Definition of friend function
void printWidth(Box box) {
    // Accessing private member width
    cout << "Width of box: " << box.width << endl;
}

int main() {
    Box box(10.5);

    // Call the friend function
    printWidth(box); // Outputs: Width of box: 10.5

    return 0;
}
```

## Explanation:
* Class Box: Contains a private member width and a constructor to initialize it.
* Friend Function Declaration: Inside the Box class, the friend keyword is used to declare printWidth(Box box) as a friend function. This gives printWidth access to the private member width.
* Friend Function Definition: The printWidth function is defined outside the class and can access the private member width of the Box object.
* Main Function: Creates a Box object and calls the printWidth function, which prints the width of the box.

## Key Points:
* Friend Functions Are Not Members: They are not members of the class, even though they have access to its private and protected members.
* Access Control: Friend functions can access private and protected members, but this should be used judiciously to maintain encapsulation.
* Friend Functions Are Declared Inside the Class: They are declared inside the class whose private members they need to access, but their definitions are outside the class.

## Use Cases:
* Operator Overloading: Friend functions are often used to overload operators that require access to private members of a class.
* Non-Member Utility Functions: Functions that need to access private data but logically do not belong as members of the class.

# friend class:

In C++, a friend class is a class that is granted access to the private and protected members of another class. This access is declared explicitly using the friend keyword within the class that wants to allow access. Here's how friend classes work and their implications

## Purpose of Friend Classes
* Access to Private and Protected Members: Normally, only member functions of a class and its derived classes can access its private and protected members. However, by declaring another class as a friend, you grant it access to these members as well.

* Enhanced Encapsulation: While generally breaking encapsulation, friend classes can be useful in specific scenarios where tight coupling and close interaction between classes are necessary.

Syntax and Example
Here's how you declare a friend class in C++:

```cpp
class Box {
private:
    double width;

    // Declare Friend Class
    friend class BoxFriend;

public:
    Box(double w) : width(w) {}

    void printWidth() {
        cout << "Width of box: " << width << endl;
    }
};

// Friend Class Definition
class BoxFriend {
public:
    void setWidth(Box& box, double width) {
        box.width = width; // Can access private member 'width'
    }
};

int main() {
    Box box(10.0);
    box.printWidth(); // Outputs: Width of box: 10.0

    BoxFriend friendObj;
    friendObj.setWidth(box, 20.0);

    box.printWidth(); // Outputs: Width of box: 20.0

    return 0;
}

```

## Explanation:
* Class Box: Contains a private member width and declares BoxFriend as a friend class using friend class BoxFriend;.

* Class BoxFriend: Defined separately, it has a member function setWidth that takes a Box object reference and a double value. Inside setWidth, it directly accesses and modifies the private member width of the Box object.

* Main Function: Demonstrates how BoxFriend can modify the private member width of Box objects through its setWidth function, effectively demonstrating the access granted by the friend declaration.

## Key Points:
* Friendship is not mutual: If class A is a friend of class B, it doesn't mean that class B is also a friend of class A. Friendship is not transitive.

* Use with Caution: While friend classes can be useful in certain situations (like providing efficient access for utility or helper classes), they can also lead to decreased encapsulation and increased coupling between classes, which may complicate maintenance and increase the risk of errors.

* Alternative Approaches: Prefer using encapsulation and providing necessary interfaces through public member functions rather than granting broad access via friend declarations, unless there is a clear and justified need for such access.

# What are templates in C++?

Templates are a feature in C++ that allows functions and classes to operate with generic types. This allows a function or class to work on different data types without being rewritten for each type.

# What is the Standard Template Library (STL)?

The STL is a collection of C++ template classes to provide general-purpose classes and functions with templates. It includes classes for data structures like vectors, lists, queues, and algorithms like sort, search, and manipulate.

# What is the use of the const keyword in C++?

The const keyword specifies that a variable's value cannot be changed. It can be used with variables, pointers, and member functions to enforce immutability.

# What is the difference between struct and class in C++?

`In C++, both struct and class are used to define user-defined types that can contain data members (variables) and member functions (methods).`

The main difference is the default access specifier. In a struct, members are public by default, while in a class, members are private by default.

## Similarities between struct and class:
* Both can have data members and member functions.
* Both can use inheritance.
* Both can use access specifiers (public, private, protected) to control access to members.
* Both can have constructors, destructors, and overloaded operators.
* Both can be used interchangeably in many cases, except for the default access control.

# Compile-time polymorphism:

Compile-time polymorphism, also known as static polymorphism, is a type of polymorphism that is resolved during compile time. This is achieved in C++ using two main techniques:

* Function Overloading
* Operator Overloading
* Template Programming (can also be considered a form of compile-time polymorphism)

## Function Overloading
Function overloading allows you to define multiple functions with the same name but different parameter lists (different number or types of parameters). The correct function to call is determined at compile time based on the arguments passed to the function.

Example:

```cpp
#include <iostream>
using namespace std;

class Print {
public:
    void show(int i) {
        cout << "Integer: " << i << endl;
    }

    void show(double f) {
        cout << "Float: " << f << endl;
    }

    void show(string s) {
        cout << "String: " << s << endl;
    }
};

int main() {
    Print p;
    p.show(10);        // Calls show(int)
    p.show(5.5);       // Calls show(double)
    p.show("Hello");   // Calls show(string)

    return 0;
}
```

## Operator Overloading
Operator overloading allows you to define or redefine the behavior of operators for user-defined types. This lets you use operators such as +, -, *, etc., with your custom classes and structs.

Example:

```cpp
#include <iostream>
using namespace std;

class Complex {
private:
    float real;
    float imag;

public:
    Complex() : real(0), imag(0) {}
    Complex(float r, float i) : real(r), imag(i) {}

    // Overload + operator to add two Complex objects
    Complex operator + (const Complex& obj) {
        Complex res;
        res.real = real + obj.real;
        res.imag = imag + obj.imag;
        return res;
    }

    void display() {
        cout << real << " + " << imag << "i" << endl;
    }
};

int main() {
    Complex c1(3.0, 4.0);
    Complex c2(1.0, 2.0);
    Complex c3;

    c3 = c1 + c2; // Calls overloaded operator+

    c3.display(); // Outputs: 4.0 + 6.0i

    return 0;
}
```

## Template Programming
Templates in C++ allow you to write generic and reusable code. Function templates and class templates enable the creation of functions and classes that can operate with any data type.

Example:

```cpp
#include <iostream>
using namespace std;

// Function template to find the maximum of two values
template <typename T>
T getMax(T a, T b) {
    return (a > b) ? a : b;
}

int main() {
    cout << getMax(10, 20) << endl;        // Outputs: 20
    cout << getMax(1.5, 2.5) << endl;      // Outputs: 2.5
    cout << getMax('a', 'b') << endl;      // Outputs: b

    return 0;
}
```

### Key Points:
* Function Overloading: Allows multiple functions with the same name but different parameter lists.
* Operator Overloading: Allows custom behavior for operators with user-defined types.
* Templates: Enable generic programming by allowing functions and classes to operate with any data type.
### Advantages of Compile-Time Polymorphism:
* Efficiency: Since the decision of which function or operator to use is made at compile time, there is no runtime overhead associated with function pointers or virtual tables.
* Type Safety: Errors related to polymorphism can be caught at compile time, leading to safer and more robust code.
* Readability: Overloaded functions and operators can make code more readable and easier to understand by providing intuitive operations on user-defined types.

### Limitations:
* Code Bloat: Templates can lead to code bloat if many different types are used, as the compiler generates a separate instance of the template for each type.
* Complexity: Overuse of overloading and templates can make code more complex and harder to maintain.


# Inline function:
In C++, an inline function is a function for which the compiler attempts to expand the function's code at the point of each call to the function, rather than inserting a call to the function. This can reduce the overhead associated with function calls, particularly for small, frequently called functions.

## Syntax and Declaration
You declare an inline function by using the inline keyword before the function definition. Here is a simple example:
```cpp
inline int add(int a, int b) {
    return a + b;
}

int main() {
    int sum = add(3, 4); // The compiler will try to replace this call with the actual code of the add function
    return 0;
}
```
## How Inline Functions Work
When you declare a function as inline, you are requesting the compiler to replace each call to the function with the actual code of the function. This process is known as "inlining."

## Advantages of Inline Functions
1. Performance Improvement: By avoiding the overhead of function calls (such as stack manipulation and jumps), inline functions can make the program run faster.
2. Simplicity: Inlining can simplify the debugging process since the function body appears in place of the call.

## Limitations and Considerations
* Code Size: Excessive use of inline functions can lead to code bloat, where the size of the compiled code increases significantly, potentially causing performance degradation.
* Complex Functions: The compiler may ignore the inline request for large or complex functions, as inlining such functions could lead to inefficient code.
* Recursion: Inline functions cannot be used for recursive functions in a meaningful way, as this would lead to infinite inlining.

## When to Use Inline Functions
* Use inline functions for small, simple functions that are called frequently.
* Avoid using inline functions for large, complex functions.
* Inline functions are often used for functions defined in header files, especially in the context of templates.