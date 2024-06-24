# `useState` in React

useState is a Hook provided by React that allows you to add state to functional components. It is a fundamental part of React's functional programming paradigm, making it possible to manage local state in a component without needing class-based components.

## Basic Usage

The useState Hook returns an array with two elements:

1. The current state value.
2. A function to update the state.

```js
const [state, setState] = useState(initialState);
```
