<!-- ![](https://www.c-sharpcorner.com/UploadFile/73d82f/interfaces-of-collection-in-java/Images/The-interface-and-class.gif) -->

![](https://d1m75rqqgidzqn.cloudfront.net/wp-data/2021/07/20101620/java20.png)

# Iterable

The Iterable interface in Java is at the root of the Iterable hierarchy. It belongs to the java.lang package and defines a single method iterator(), which returns an iterator over elements of the implementing class. This interface is implemented by all classes whose instances can be iterated over using the enhanced for loop (for-each loop) and supports sequential iteration.

Here's the basic structure of the Iterable interface:

```java
package java.lang;

import java.util.Iterator;

public interface Iterable<T> {
    Iterator<T> iterator();
}

```

The iterator() method returns an iterator that allows iterating through the elements of the implementing class.

A class that implements the Iterable interface allows its instances to be the target of the for-each loop, which simplifies the process of iteration over its elements.

Here's a simple example demonstrating the usage of the Iterable interface:

```java
import java.util.Iterator;

public class MyIterable implements Iterable<Integer> {
    private final int[] array;

    public MyIterable(int[] array) {
        this.array = array;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < array.length;
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new IndexOutOfBoundsException("No more elements to iterate");
                }
                return array[index++];
            }
        };
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5};
        MyIterable iterable = new MyIterable(array);

        // Iterate over the elements using the enhanced for loop
        for (int num : iterable) {
            System.out.println(num);
        }
    }
}

```

output:

```
1
2
3
4
5
```

In this example, MyIterable class implements the Iterable<Integer> interface, providing an iterator over an array of integers. The iterator() method returns an instance of an anonymous inner class that implements the Iterator interface. The for-each loop iterates over the elements of MyIterable, printing each element to the console.

# Collection

Collection interface, which is the root interface in the Java Collections Framework hierarchy. The Collection interface represents a group of objects known as elements. It extends the Iterable interface and is a part of the java.util package.

Here's the basic structure of the Collection interface:

```java
import java.util.Iterator;

public interface Collection<E> extends Iterable<E> {
    // Returns the number of elements in this collection
    int size();

    // Returns true if this collection contains no elements
    boolean isEmpty();

    // Returns true if this collection contains the specified element
    boolean contains(Object o);

    // Returns an iterator over the elements in this collection
    Iterator<E> iterator();

    // Returns an array containing all of the elements in this collection
    Object[] toArray();

    // Returns an array containing all of the elements in this collection;
    // the runtime type of the returned array is that of the specified array
    <T> T[] toArray(T[] a);

    // Adds the specified element to this collection
    boolean add(E e);

    // Removes a single instance of the specified element from this collection, if it is present
    boolean remove(Object o);

    // Returns true if this collection contains all of the elements in the specified collection
    boolean containsAll(Collection<?> c);

    // Adds all of the elements in the specified collection to this collection
    boolean addAll(Collection<? extends E> c);

    // Removes all of this collection's elements that are also contained in the specified collection
    boolean removeAll(Collection<?> c);

    // Retains only the elements in this collection that are contained in the specified collection
    boolean retainAll(Collection<?> c);

    // Removes all of the elements from this collection
    void clear();

    // Compares the specified object with this collection for equality
    boolean equals(Object o);

    // Returns the hash code value for this collection
    int hashCode();
}

```

The Collection interface provides a framework for classes representing groups of objects. Implementations of the Collection interface typically provide specific behavior for operations such as adding, removing, and querying elements.

Here's a simple example demonstrating the usage of the Collection interface with an ArrayList:

# Iterator

In Java, the Iterator interface is a part of the `java.util` package. It provides a way to iterate over the elements of a collection, such as lists, sets, or maps, without exposing the underlying implementation of the collection. The Iterator interface allows you to traverse the collection sequentially and perform certain operations like removing elements during iteration.

Here's the basic structure of the Iterator interface:

```java
import java.util.Iterator;

public interface Iterator<E> {
    boolean hasNext();   // Returns true if there are more elements in the collection
    E next();            // Returns the next element in the collection
    void remove();       // Removes the last element returned by the iterator from the underlying collection
}

```

# ListIterator

In Java, ListIterator is an interface that extends the Iterator interface and provides additional functionalities for iterating over lists, such as ArrayLists, LinkedLists, etc., bidirectionally. It allows you to traverse elements in both forward and backward directions, as well as perform various operations like adding, removing, or modifying elements during the iteration.

Here's the basic structure of the ListIterator interface:

```java
import java.util.ListIterator;

public interface ListIterator<E> extends Iterator<E> {
    // Returns true if there are more elements in the list in the forward direction
    boolean hasNext();

    // Returns the next element in the list and advances the cursor position
    E next();

    // Returns true if there are more elements in the list in the backward direction
    boolean hasPrevious();

    // Returns the previous element in the list and moves the cursor position backwards
    E previous();

    // Returns the index of the next element to be returned by next() or previous()
    int nextIndex();

    // Returns the index of the previous element to be returned by next() or previous()
    int previousIndex();

    // Removes from the list the last element that was returned by next() or previous()
    void remove();

    // Replaces the last element returned by next() or previous() with the specified element
    void set(E e);

    // Inserts the specified element into the list at the current position of the iterator
    void add(E e);
}

```

# List

In Java, the List interface represents an ordered collection of elements that allows for duplicate elements and maintains the insertion order. It's a part of the java.util package and extends the Collection interface.

```java
import java.util.Collection;

public interface List<E> extends Collection<E> {
    // Inserts the specified element at the specified position in the list
    void add(int index, E element);

    // Returns the element at the specified position in the list
    E get(int index);

    // Removes the element at the specified position in the list
    E remove(int index);

    // Replaces the element at the specified position in the list with the specified element
    E set(int index, E element);

    // Returns the index of the first occurrence of the specified element in the list, or -1 if the list does not contain the element
    int indexOf(Object o);

    // Returns the index of the last occurrence of the specified element in the list, or -1 if the list does not contain the element
    int lastIndexOf(Object o);

    // Returns a ListIterator over the elements in the list (in proper sequence)
    ListIterator<E> listIterator();

    // Returns a ListIterator over the elements in the list (in proper sequence), starting at the specified position in the list
    ListIterator<E> listIterator(int index);

    // Returns a view of the portion of this list between the specified fromIndex, inclusive, and toIndex, exclusive
    List<E> subList(int fromIndex, int toIndex);
}

```

# Queue

In Java, the Queue interface represents a collection that holds elements prior to processing. It follows the First-In-First-Out (FIFO) principle, meaning the elements are processed in the same order they were added. The Queue interface is a part of the java.util package and extends the Collection interface.

Here's the basic structure of the Queue interface:

```java
import java.util.Collection;

public interface Queue<E> extends Collection<E> {
    // Inserts the specified element into this queue if it is possible to do so immediately without violating capacity restrictions
    boolean add(E e);

    // Retrieves, but does not remove, the head of this queue
    E element();

    // Inserts the specified element into this queue if it is possible to do so immediately without violating capacity restrictions
    boolean offer(E e);

    // Retrieves and removes the head of this queue, or returns null if this queue is empty
    E poll();

    // Retrieves and removes the head of this queue
    E remove();
}

```

- `Exception vs. Special Value:` Methods like add() and remove() throw exceptions if the operation fails (e.g., if the queue is full or empty), whereas methods like offer() and poll() return a special value (typically true/false or null) to indicate success or failure without throwing an exception.
- `Blocking vs. Non-blocking:` Some implementations of the Queue interface provide blocking methods, which means the methods like take() and put() will block the thread until they can successfully perform the operation.
- `Concurrent vs. Non-concurrent:` Java provides both concurrent and non-concurrent implementations of the Queue interface. Concurrent implementations allow multiple threads to access the queue safely.

# Deque

In Java, the Deque interface represents a double-ended queue, which allows insertion and removal of elements from both ends: the front and the rear. It's pronounced as "deck" and is short for "double-ended queue." The Deque interface is a part of the java.util package and extends the Queue interface.

Here's the basic structure of the Deque interface:

```java
import java.util.Queue;

public interface Deque<E> extends Queue<E> {
    // Inserts the specified element at the front of this deque if it is possible to do so immediately without violating capacity restrictions
    void addFirst(E e);

    // Inserts the specified element at the end of this deque if it is possible to do so immediately without violating capacity restrictions
    void addLast(E e);

    // Retrieves, but does not remove, the first element of this deque
    E getFirst();

    // Retrieves, but does not remove, the last element of this deque
    E getLast();

    // Inserts the specified element at the front of this deque unless it would violate capacity restrictions
    boolean offerFirst(E e);

    // Inserts the specified element at the end of this deque unless it would violate capacity restrictions
    boolean offerLast(E e);

    // Retrieves and removes the first element of this deque, or returns null if this deque is empty
    E pollFirst();

    // Retrieves and removes the last element of this deque, or returns null if this deque is empty
    E pollLast();

    // Retrieves and removes the first element of this deque
    E removeFirst();

    // Retrieves and removes the last element of this deque
    E removeLast();
}

```

# Comparable Interface

The Comparable interface in Java is used to impose a natural ordering on the objects of a class. By implementing the Comparable interface, a class defines a way to compare its objects with one another.

Here's the basic structure of the Comparable interface:

```java
public interface Comparable<T> {
    int compareTo(T o);
}

```

The compareTo() method returns a negative integer, zero, or a positive integer if the current object is less than, equal to, or greater than the specified object o, respectively.

Let's demonstrate the usage of the Comparable interface with a simple example. Suppose we have a class Person that represents a person with a name and an age. We want to compare Person objects based on their ages.

```java
public class Person implements Comparable<Person> {
    private String name;
    private int age;

    // Constructor
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Implementing the compareTo method
    @Override
    public int compareTo(Person otherPerson) {
        // Compare based on age
        return Integer.compare(this.age, otherPerson.age);
    }

    // toString method for printing
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

```

Now, let's use this Person class to compare Person objects based on their ages:

```java
import java.util.ArrayList;
import java.util.Collections;

public class ComparableExample {
    public static void main(String[] args) {
        // Create some Person objects
        Person person1 = new Person("Alice", 30);
        Person person2 = new Person("Bob", 25);
        Person person3 = new Person("Charlie", 35);

        // Create an ArrayList of Person objects
        ArrayList<Person> people = new ArrayList<>();
        people.add(person1);
        people.add(person2);
        people.add(person3);

        // Print the ArrayList before sorting
        System.out.println("Before sorting:");
        for (Person person : people) {
            System.out.println(person);
        }

        // Sort the ArrayList based on age using Collections.sort()
        Collections.sort(people);

        // Print the ArrayList after sorting
        System.out.println("\nAfter sorting:");
        for (Person person : people) {
            System.out.println(person);
        }
    }
}

```

output:

```java
Before sorting:
Person{name='Alice', age=30}
Person{name='Bob', age=25}
Person{name='Charlie', age=35}

After sorting:
Person{name='Bob', age=25}
Person{name='Alice', age=30}
Person{name='Charlie', age=35}

```

In this example, the Person class implements the Comparable interface, overriding the compareTo() method to compare Person objects based on their ages. Then, we create a list of Person objects and sort it using Collections.sort(). The output demonstrates that the list has been sorted based on the ages of the Person objects.

# Comparator

The Comparator interface in Java is used to define custom comparison logic for objects that don't implement the Comparable interface or when you need alternative sorting orders. It provides a way to compare two objects for sorting purposes.

Here's the basic structure of the Comparator interface:

```java
import java.util.Comparator;

public interface Comparator<T> {
    int compare(T o1, T o2);
    // Other default and static methods
}

```

The compare() method compares its two arguments for order, returning a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.

Let's demonstrate the usage of the Comparator interface with a simple example. Suppose we have a class Student representing a student with a name and a GPA. We want to compare Student objects based on their GPAs using a custom comparator.

```java
public class Student {
    private String name;
    private double gpa;

    // Constructor
    public Student(String name, double gpa) {
        this.name = name;
        this.gpa = gpa;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    // toString method for printing
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", gpa=" + gpa +
                '}';
    }
}

```

Now, let's create a Comparator to compare Student objects based on their GPAs:

```java
import java.util.Comparator;

public class GpaComparator implements Comparator<Student> {
    @Override
    public int compare(Student student1, Student student2) {
        // Compare based on GPA
        return Double.compare(student1.getGpa(), student2.getGpa());
    }
}

```

Now, let's use this Comparator to sort a list of Student objects based on their GPAs:

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComparatorExample {
    public static void main(String[] args) {
        // Create some Student objects
        Student student1 = new Student("Alice", 3.5);
        Student student2 = new Student("Bob", 3.2);
        Student student3 = new Student("Charlie", 3.7);

        // Create a list of Student objects
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);

        // Print the list before sorting
        System.out.println("Before sorting:");
        for (Student student : students) {
            System.out.println(student);
        }

        // Sort the list based on GPA using Collections.sort() with a Comparator
        Collections.sort(students, new GpaComparator());


        // Sort the list based on GPA using Collections.sort() with a lambda expression
        // Collections.sort(students, (s1, s2) -> Double.compare(s1.getGpa(), s2.getGpa()));

        // Print the list after sorting
        System.out.println("\nAfter sorting:");
        for (Student student : students) {
            System.out.println(student);
        }
    }
}

```

output:

```java
Before sorting:
Student{name='Alice', gpa=3.5}
Student{name='Bob', gpa=3.2}
Student{name='Charlie', gpa=3.7}

After sorting:
Student{name='Bob', gpa=3.2}
Student{name='Alice', gpa=3.5}
Student{name='Charlie', gpa=3.7}

```

In this example, we create a custom Comparator named GpaComparator to compare Student objects based on their GPAs. We then use this comparator to sort a list of Student objects. The output demonstrates that the list has been sorted based on the GPAs of the Student objects.

# Collections class

The Collections class in Java is a utility class provided by the Java Collections Framework. It contains only static methods and cannot be instantiated because it has a private constructor. The Collections class is located in the java.util package.

Below is a simplified representation of the Collections class:

```java
package java.util;

import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;

public class Collections {
    // Suppress default constructor for non-instantiability
    private Collections() {
        throw new AssertionError();
    }

    // Sorting methods
    public static <T extends Comparable<? super T>> void sort(List<T> list);
    public static <T> void sort(List<T> list, Comparator<? super T> c);

    // Searching methods
    public static <T> int binarySearch(List<? extends Comparable<? super T>> list, T key);
    public static <T> int binarySearch(List<? extends T> list, T key, Comparator<? super T> c);

    // Shuffling method
    public static void shuffle(List<?> list);

    // Reversing method
    public static void reverse(List<?> list);

    // Filling method
    public static <T> void fill(List<? super T> list, T obj);

    // Copy method
    public static <T> void copy(List<? super T> dest, List<? extends T> src);

    // Min and max methods
    public static <T extends Object & Comparable<? super T>> T min(Collection<? extends T> coll);
    public static <T extends Object & Comparable<? super T>> T max(Collection<? extends T> coll);

    // Frequency and disjoint methods
    public static int frequency(Collection<?> c, Object o);
    public static boolean disjoint(Collection<?> c1, Collection<?> c2);

    // Unmodifiable collection view
    public static <T> Collection<T> unmodifiableCollection(Collection<? extends T> c);

    // Synchronized collection view
    public static <T> Collection<T> synchronizedCollection(Collection<T> c);

    // Other methods...
}

```

Note: This is just a simplified representation of the Collections class for illustration purposes. The actual implementation is more complex and contains additional methods for working with various types of collections.

The Collections class provides a wide range of static methods for manipulating collections in various ways, such as sorting, searching, shuffling, and creating unmodifiable or synchronized views of collections. It is a central part of the Java Collections Framework and is widely used in Java programming.

# Set

In Java, the Set interface is part of the Java Collections Framework and represents a collection that does not allow duplicate elements. It is a subtype of the Collection interface and is typically used when you want to store unique elements.

Here are some key characteristics of the Set interface:

1. `No Duplicates:` A Set does not allow duplicate elements. If you attempt to add an element that already exists in the set, the operation will have no effect and the set will remain unchanged.

2. `Unordered:` Unlike lists, sets do not guarantee any specific order of elements. The order of elements in a set may vary based on the specific set implementation being used.

3. `Fast Retrieval:` Sets typically provide fast retrieval of elements. However, the time complexity of operations such as adding, removing, and checking for the presence of an element may vary depending on the implementation.

4. `Distinct Elements:` Each element in a set must be distinct. The equality of elements is determined using the equals() method.

The Set interface defines several methods for manipulating sets, including adding elements, removing elements, checking for the presence of elements, and performing set operations such as union, intersection, and difference.

```java
import java.util.Collection;
import java.util.Iterator;

public interface Set<E> extends Collection<E> {

    // Adds the specified element to the set if it is not already present.
    boolean add(E e);

    // Removes the specified element from the set if it is present.
    boolean remove(Object o);

    // Removes all of the elements from the set.
    void clear();

    // Returns true if the set contains the specified element.
    boolean contains(Object o);

    // Returns true if the set contains no elements.
    boolean isEmpty();

    // Returns the number of elements in the set.
    int size();

    // Returns an iterator over the elements in the set.
    Iterator<E> iterator();

    // Adds all of the elements in the specified collection to the set if they're not already present.
    boolean addAll(Collection<? extends E> c);

    // Removes from the set all of its elements that are contained in the specified collection.
    boolean removeAll(Collection<?> c);

    // Retains only the elements in the set that are contained in the specified collection.
    boolean retainAll(Collection<?> c);

    // Returns true if the set contains all of the elements of the specified collection.
    boolean containsAll(Collection<?> c);

    // Returns an array containing all of the elements in the set.
    Object[] toArray();

    // Returns an array containing all of the elements in the set.
    <T> T[] toArray(T[] a);
}

```

`When working with HashSet (or any hash-based collection), it's important to override both the equals() and hashCode() methods in a custom class if you intend to store instances of that class in the HashSet. This is necessary to ensure correct behavior and efficient operation of the HashSet.`

## Custom class -- ( hashCode() and equals() )

The hashCode() and equals() methods are fundamental methods in Java used for implementing object equality and for working with hash-based data structures like HashMap, HashSet, etc.

Here's how they are used:

1. `equals() Method:`

- The equals() method is used to determine whether two objects are equal or not.
- It is a method defined in the Object class and needs to be overridden in custom classes for meaningful object comparison.
- The method signature is boolean equals(Object obj).
- When implementing equals(), you typically compare the fields of the objects to determine equality.
- If you override equals(), you should also override hashCode() to maintain the general contract between equals() and hashCode().

2. `hashCode() Method:`

- The hashCode() method returns a hash code value for the object.
- It is used by hash-based data structures to efficiently locate a particular object.
- The general contract of hashCode() is that if two objects are equal according to the equals(Object) method, then calling hashCode() on each of the two objects must produce the same integer result.
- If you override equals(), you should also override hashCode() to ensure consistent behavior.
- The method signature is int hashCode().

Here's an example demonstrating the use of hashCode() and equals():

```java
public class Person {
    private String firstName;
    private String lastName;
    private int age;

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return age == person.age &&
                firstName.equals(person.firstName) &&
                lastName.equals(person.lastName);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + age;
        return result;
    }

    public static void main(String[] args) {
        Person person1 = new Person("John", "Doe", 30);
        Person person2 = new Person("John", "Doe", 30);

        // Using equals() to check equality
        System.out.println("Using equals(): " + person1.equals(person2)); // true

        // Using hashCode() to get hash code
        System.out.println("Hash code of person1: " + person1.hashCode());
        System.out.println("Hash code of person2: " + person2.hashCode());
    }
}

```

In this example, equals() compares the firstName, lastName, and age fields of two Person objects for equality. hashCode() calculates a hash code based on these fields. `When comparing two Person objects for equality, both equals() and hashCode() are used by hash-based data structures.`

# SortedSet

The SortedSet interface in Java represents a set that maintains its elements in sorted order. It is a subinterface of the Set interface and provides additional methods for accessing and manipulating elements based on their natural ordering or a specified comparator.

```java
import java.util.Comparator;

public interface SortedSet<E> extends Set<E> {

    // Returns the comparator used to order the elements in this set, or null if this set uses the natural ordering of its elements.
    Comparator<? super E> comparator();

    // Returns a view of the portion of this set whose elements range from fromElement, inclusive, to toElement, exclusive.
    SortedSet<E> subSet(E fromElement, E toElement);

    // Returns a view of the portion of this set whose elements are strictly less than toElement.
    SortedSet<E> headSet(E toElement);

    // Returns a view of the portion of this set whose elements are greater than or equal to fromElement.
    SortedSet<E> tailSet(E fromElement);

    // Returns the first (lowest) element currently in this set.
    E first();

    // Returns the last (highest) element currently in this set.
    E last();
}
```

Example:

```java
import java.util.SortedSet;
import java.util.TreeSet;

public class SortedSetMethodsExample {
    public static void main(String[] args) {
        // Create a SortedSet using TreeSet
        SortedSet<Integer> sortedSet = new TreeSet<>();

        // Add elements to the SortedSet
        sortedSet.add(5);
        sortedSet.add(2);
        sortedSet.add(8);
        sortedSet.add(3);
        sortedSet.add(10);

        // Printing the elements in the SortedSet
        System.out.println("Elements in the SortedSet: " + sortedSet);

        // Accessing elements in sorted order
        System.out.println("First element: " + sortedSet.first());
        System.out.println("Last element: " + sortedSet.last());

        // Finding elements less than or equal to a specified element
        System.out.println("Elements less than or equal to 5: " + sortedSet.headSet(5));

        // Finding elements greater than a specified element
        System.out.println("Elements greater than 3: " + sortedSet.tailSet(3));

        // Finding elements within a specified range
        System.out.println("Elements between 3 (inclusive) and 8 (exclusive): " + sortedSet.subSet(3, 8));

        // Removing the first and last elements
        System.out.println("SortedSet before removal: " + sortedSet);
        sortedSet.remove(sortedSet.first());
        sortedSet.remove(sortedSet.last());
        System.out.println("SortedSet after removal: " + sortedSet);
    }
}
```

Output:

```
Elements in the SortedSet: [2, 3, 5, 8, 10]
First element: 2
Last element: 10
Elements less than or equal to 5: [2, 3]
Elements greater than 3: [3, 5, 8, 10]
Elements between 3 (inclusive) and 8 (exclusive): [3, 5]
SortedSet before removal: [2, 3, 5, 8, 10]
SortedSet after removal: [3, 5, 8]
```

# Maps

![](https://1.bp.blogspot.com/-I18T7tTN0VI/XZxrG4VwSSI/AAAAAAAAAUs/Cq_GcZG3Ok0IV6Z4vHrxfe8dV0wY14zZgCLcBGAsYHQ/s640/jdkjrejvmheirarchy.PNG)

```java
import java.util.Collection;
import java.util.Set;

public interface Map<K, V> {

    // Basic Operations

    // Returns the number of key-value mappings in this map.
    int size();

    // Returns true if this map contains no key-value mappings.
    boolean isEmpty();

    // Returns true if this map contains a mapping for the specified key.
    boolean containsKey(Object key);

    // Returns true if this map maps one or more keys to the specified value.
    boolean containsValue(Object value);

    // Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
    V get(Object key);

    // Associates the specified value with the specified key in this map.
    V put(K key, V value);

    // Removes the mapping for a key from this map if it is present.
    V remove(Object key);

    // Bulk Operations

    // Copies all of the mappings from the specified map to this map.
    void putAll(Map<? extends K, ? extends V> m);

    // Removes all of the mappings from this map.
    void clear();

    // Collection Views

    // Returns a Set view of the keys contained in this map.
    Set<K> keySet();

    // Returns a Collection view of the values contained in this map.
    Collection<V> values();

    // Returns a Set view of the mappings contained in this map.
    Set<Map.Entry<K, V>> entrySet();

    // Nested Interface: Map.Entry

    // A map entry (key-value pair).
    interface Entry<K, V> {
        // Returns the key corresponding to this entry.
        K getKey();

        // Returns the value corresponding to this entry.
        V getValue();

        // Replaces the value corresponding to this entry with the specified value.
        V setValue(V value);

        // Compares the specified object with this entry for equality.
        boolean equals(Object obj);

        // Returns the hash code value for this map entry.
        int hashCode();
    }
}

```

Example:

```java
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Collection;

public class MapExample {
    public static void main(String[] args) {
        // Creating a HashMap
        Map<String, Integer> map = new HashMap<>();

        // Basic Operations
        // Adding key-value pairs to the map
        map.put("Apple", 10);
        map.put("Banana", 20);
        map.put("Orange", 15);

        // Size of the map
        System.out.println("Size of the map: " + map.size());

        // Checking if the map is empty
        System.out.println("Is the map empty? " + map.isEmpty());

        // Retrieving values from the map
        System.out.println("Value for 'Apple': " + map.get("Apple"));
        System.out.println("Value for 'Banana': " + map.get("Banana"));

        // Checking if the map contains a key
        System.out.println("Does the map contain 'Apple'? " + map.containsKey("Apple"));

        // Removing a key-value pair from the map
        map.remove("Orange");
        System.out.println("Map after removing 'Orange': " + map);

        // Bulk Operations
        // Creating another map
        Map<String, Integer> anotherMap = new HashMap<>();
        anotherMap.put("Mango", 25);
        anotherMap.put("Pineapple", 30);

        // Adding all key-value pairs from anotherMap to map
        map.putAll(anotherMap);
        System.out.println("Map after adding all from anotherMap: " + map);

        // Clearing the map
        map.clear();
        System.out.println("Map after clearing: " + map);

        // Collection Views
        // Creating a new map
        map.put("Grapes", 35);
        map.put("Watermelon", 40);

        // Key Set
        Set<String> keySet = map.keySet();
        System.out.println("Keys in the map: " + keySet);

        // Values Collection
        Collection<Integer> values = map.values();
        System.out.println("Values in the map: " + values);

        // Entry Set
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        System.out.println("Entries in the map: " + entrySet);

        // Iterating over entries using Map.Entry
        System.out.println("Iterating over entries:");
        for (Map.Entry<String, Integer> entry : entrySet) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }
}

```

```
Size of the map: 3
Is the map empty? false
Value for 'Apple': 10
Value for 'Banana': 20
Does the map contain 'Apple'? true
Map after removing 'Orange': {Apple=10, Banana=20}
Map after adding all from anotherMap: {Apple=10, Banana=20, Pineapple=30, Mango=25}
Map after clearing: {}
Keys in the map: [Grapes, Watermelon]
Values in the map: [35, 40]
Entries in the map: [Grapes=35, Watermelon=40]
Iterating over entries:
Key: Grapes, Value: 35
Key: Watermelon, Value: 40
```

# SortedMap

```java
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;

public interface SortedMap<K, V> extends Map<K, V> {

    // Comparator Methods

    // Returns the comparator used to order the keys in this map,
    // or null if this map uses the natural ordering of its keys.
    Comparator<? super K> comparator();

    // Range View Methods

    // Returns a view of the portion of this map whose keys range
    // from fromKey, inclusive, to toKey, exclusive.
    SortedMap<K, V> subMap(K fromKey, K toKey);

    // Returns a view of the portion of this map whose keys are
    // strictly less than toKey.
    SortedMap<K, V> headMap(K toKey);

    // Returns a view of the portion of this map whose keys are
    // greater than or equal to fromKey.
    SortedMap<K, V> tailMap(K fromKey);

    // Boundary Element Methods

    // Returns the first (lowest) key currently in this map.
    K firstKey();

    // Returns the last (highest) key currently in this map.
    K lastKey();
}
```

Example:

```java
import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

public class SortedMapExample {
    public static void main(String[] args) {
        // Creating a SortedMap with a custom comparator
        Comparator<String> comparator = Comparator.reverseOrder(); // Reverse order comparator
        SortedMap<String, Integer> sortedMap = new TreeMap<>(comparator);

        // Adding key-value pairs to the sorted map
        sortedMap.put("Apple", 10);
        sortedMap.put("Banana", 20);
        sortedMap.put("Orange", 15);
        sortedMap.put("Grapes", 25);
        sortedMap.put("Watermelon", 30);

        // Printing the sorted map
        System.out.println("Sorted Map: " + sortedMap);

        // Accessing elements in sorted order
        System.out.println("First key: " + sortedMap.firstKey());
        System.out.println("Last key: " + sortedMap.lastKey());

        // SubMap - Range view
        SortedMap<String, Integer> subMap = sortedMap.subMap("Banana", "Orange");
        System.out.println("SubMap (Banana to Orange): " + subMap);

        // HeadMap - Elements less than the specified key
        SortedMap<String, Integer> headMap = sortedMap.headMap("Grapes");
        System.out.println("HeadMap (Elements less than Grapes): " + headMap);

        // TailMap - Elements greater than or equal to the specified key
        SortedMap<String, Integer> tailMap = sortedMap.tailMap("Grapes");
        System.out.println("TailMap (Elements greater than or equal to Grapes): " + tailMap);
    }
}
```
