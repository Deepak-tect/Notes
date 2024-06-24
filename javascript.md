# Closures:
A closure is a feature in JavaScript where an inner function has access to the outer (enclosing) function's variables—even after the outer function has returned. For example:

```js
function outerFunction() {
    let outerVariable = 'I am outside!';

    function innerFunction() {
        console.log(outerVariable); // This will log 'I am outside!'
    }

    return innerFunction;
}

const myInnerFunction = outerFunction();
myInnerFunction(); // This will log 'I am outside!'

```

# What is the event loop in JavaScript?

The event loop is a fundamental concept that allows JavaScript to perform non-blocking I/O operations, despite being single-threaded. It handles asynchronous callbacks by placing them in a queue and executing them sequentially after the main code execution is completed.


# What are promises and how do they work?
Promises are used to handle asynchronous operations in JavaScript. A promise represents an operation that hasn't completed yet but is expected in the future. It can be in one of three states: pending, fulfilled, or rejected.

```js
let promise = new Promise((resolve, reject) => {
    // asynchronous operation
    if (/* operation is successful */) {
        resolve('Success!');
    } else {
        reject('Failure!');
    }
});

promise.then((message) => {
    console.log(message); // 'Success!'
}).catch((message) => {
    console.log(message); // 'Failure!'
});

```

# What is hoisting in JavaScript?
Hoisting is a JavaScript mechanism where variables and function declarations are moved to the top of their containing scope during the compile phase. However, only the declarations are hoisted, not the initializations.

```js
console.log(myVar); // undefined
var myVar = 5;
console.log(myVar); // 5
```

# What are JavaScript prototypes?

Prototypes are the mechanism by which JavaScript objects inherit features from one another. Every JavaScript object has a prototype, which is also an object. When trying to access a property that doesn't exist on the current object, JavaScript looks at the prototype chain.


```js
function Person(name, age) {
    this.name = name;
    this.age = age;
}

Person.prototype.sayHello = function() {
    console.log('Hello, my name is ' + this.name);
};

const john = new Person('John', 30);
john.sayHello(); // 'Hello, my name is John'

```

# Explain the difference between == and === in JavaScript.
The == operator is the equality operator that performs type coercion if the types differ, while === is the strict equality operator that checks both the value and the type.
```js
console.log(5 == '5');  // true (type coercion happens)
console.log(5 === '5'); // false (different types)

```

# How do you handle asynchronous operations in JavaScript?

Asynchronous operations in JavaScript can be handled using callbacks, promises, and async/await. For example, 
1. using promises:
```js
function fetchData() {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            resolve('Data received');
        }, 2000);
    });
}

fetchData().then(data => {
    console.log(data); // Logs 'Data received' after 2 seconds
});
```
2. And using async/await:

```js
async function fetchData() {
    return 'Data received';
}

async function getData() {
    const data = await fetchData();
    console.log(data); // Logs 'Data received'
}

getData();
```

# Understanding `this` in JavaScript

The `this` keyword in JavaScript is a powerful and flexible way to refer to the context in which a function is executed. The value of `this` is determined by how a function is called. Here are the different contexts in which `this` can be used and what it refers to in each case.

## 1. Global Context

When used in the global context, `this` refers to the global object. In a browser, this is the `window` object.

```js
console.log(this); // In a browser, this refers to the window object

function globalContext() {
    console.log(this);
}
globalContext(); // window (in browser) or global (in Node.js)
```

## 2. Object Method
When a function is called as a property of an object, this refers to the object the method is called on.
```js
const person = {
    firstName: 'John',
    lastName: 'Doe',
    getFullName: function() {
        console.log(this);
        return this.firstName + ' ' + this.lastName;
    }
};

console.log(person.getFullName()); // Logs the person object and returns 'John Doe'
```

## 3. Constructor Function:
When a function is used as a constructor with the new keyword, this refers to the new instance of the object that is created.
```js
function Person(firstName, lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
}

const person1 = new Person('John', 'Doe');
console.log(person1.firstName); // 'John'

```

## 4. Explicit Binding with call, apply, and bind
call and apply: These methods call a function with a specified this value and arguments.
```js
function greet() {
    console.log(this.name);
}

const person = { name: 'Alice' };

greet.call(person); // 'Alice'
greet.apply(person); // 'Alice'

```

bind: This method returns a new function with a specified this value.

```js
function greet() {
    console.log(this.name);
}

const person = { name: 'Alice' };

const greetPerson = greet.bind(person);
greetPerson(); // 'Alice'

```

## 5. Arrow Functions
Arrow functions do not have their own this. They inherit this from the parent scope at the time they are defined.
```js
const person = {
    name: 'John',
    greet: function() {
        const innerGreet = () => {
            console.log(this.name);
        };
        innerGreet();
    }
};

person.greet(); // 'John'

```

## 6. Event Handlers
In event handlers, this refers to the element that received the event.

```js
document.getElementById('myButton').addEventListener('click', function() {
    console.log(this); // Logs the button element
});
```

## 7. this in Classes
In ES6 classes, this refers to the instance of the class.
```js
class Person {
    constructor(name) {
        this.name = name;
    }

    greet() {
        console.log(this.name);
    }
}

const person1 = new Person('John');
person1.greet(); // 'John'

```

## Summary
1. Global Context: this refers to the global object.
2. Object Method: this refers to the object the method is called on.
3. Constructor Function: this refers to the new instance of the object.
4. Explicit Binding: call, apply, and bind methods set this explicitly.
5. Arrow Functions: this is inherited from the parent scope.
6. Event Handlers: this refers to the element that received the event.
7. Classes: this refers to the instance of the class.
8. Default Binding: this defaults to the global object or undefined in strict mode.
9. Implicit Binding Lost: this refers to the global object or undefined if the method is detached from its object.


# Can you explain the JavaScript event loop and how it handles asynchronous operations?
The event loop is a mechanism in JavaScript that handles asynchronous operations by pushing callback functions to a queue. The loop continuously checks the call stack and the task queue. When the call stack is empty, it pushes the first task from the queue to the call stack. This ensures non-blocking execution despite single-threading.

```js
console.log('Start');

setTimeout(() => {
    console.log('Timeout');
}, 0);

console.log('End');

// Output: 
// Start
// End
// Timeout
```

# What is the difference between a deep copy and a shallow copy in JavaScript?
A shallow copy of an object copies the object's top-level properties, but nested objects are still referenced, not copied. A deep copy copies all properties, including nested objects, ensuring no references to the original objects remain.

```js
const obj = { a: 1, b: { c: 2 } };
const shallowCopy = { ...obj };
const deepCopy = JSON.parse(JSON.stringify(obj));

shallowCopy.b.c = 3;
console.log(obj.b.c); // 3

deepCopy.b.c = 4;
console.log(obj.b.c); // 3

```

# What is a higher-order function and can you provide an example?

A higher-order function is a function that takes another function as an argument, returns a function, or both. For example:

```js
function higherOrderFunction(callback) {
    const data = 'Hello, World!';
    return callback(data);
}

function logData(data) {
    console.log(data);
}

higherOrderFunction(logData); // Logs 'Hello, World!'
```

# How can you ensure immutability in your JavaScript code?

Ensuring immutability can be done by using const for variables, and methods like Object.freeze, or using libraries like Immutable.js. Additionally, avoid modifying existing objects or arrays directly, but rather return new instances

```js
const obj = Object.freeze({ a: 1 });

obj.a = 2; // Does nothing because the object is frozen

const newObj = { ...obj, b: 2 }; // Create a new object
```

# What are debouncing and throttling, and how do they differ?

Debouncing and throttling are techniques to control how often a function is executed.

`Debouncing`: Ensures a function is only called once after a specified period of inactivity.

`Throttling`: Ensures a function is called at most once every specified interval.

```js
// Debounce
function debounce(func, delay) {
    let timeout;
    return function(...args) {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, args), delay);
    };
}

// Throttle
function throttle(func, limit) {
    let inThrottle;
    return function(...args) {
        if (!inThrottle) {
            func.apply(this, args);
            inThrottle = true;
            setTimeout(() => (inThrottle = false), limit);
        }
    };
}
```

