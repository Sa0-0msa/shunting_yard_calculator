# Shunting Yard Calculator

A powerful calculator for mathematical expressions implemented using the Shunting Yard algorithm.

## Features

* Supports basic mathematical operations (+, -, *, /)
* Handles exponentiation (^) and modulo (%) operations
* Properly processes parentheses to modify order of operations
* Supports unary minus operations (e.g., -5 + 3, 2 * (-4))
* Evaluates trigonometric functions (sin, cos)
* Processes expressions with decimal numbers
* Follows standard mathematical precedence rules

## How It Works

This calculator uses the Shunting Yard algorithm developed by Edsger Dijkstra to parse and evaluate mathematical expressions. The process works in three main stages:

1. **Tokenization**: The input expression is broken down into tokens (numbers, operators, functions, parentheses)
2. **Conversion to Postfix**: Tokens are converted from infix notation (standard mathematical notation) to postfix notation (Reverse Polish Notation)
3. **Evaluation**: The postfix expression is evaluated to produce the final result

## Implementation Details

The project consists of several key classes:

- `Token`: Represents individual elements in the expression
- `Tokenizer`: Parses the input string into tokens
- `ShuntingYard`: Implements the algorithm to convert infix to postfix notation
- `Evaluator`: Calculates the final result from the postfix expression

## Usage Example

```java
// Create a calculator instance
String expression = "sin(0.5) + 2 * (3 - 4)^2";
Tokenizer tokenizer = new Tokenizer(expression);
List<Token> tokens = tokenizer.tokenize();

ShuntingYard shuntingYard = new ShuntingYard();
List<String> postfixExpression = shuntingYard.infixToPostfix(tokens);

Evaluator evaluator = new Evaluator();
double result = evaluator.evaluate(postfixExpression);
System.out.println(result); // Output: 2.4794255386042030
```

## Future Enhancements

Planned improvements for future versions:

- Support for additional mathematical functions (tan, log, sqrt)
- Variable substitution
- Expression storage and recall
- Plotting capabilities
- User-friendly interface
