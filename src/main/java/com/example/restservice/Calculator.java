package com.example.restservice;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

public class Calculator {
  String finalResult;

  public static double add(double first, double second) {
    double result = 0;

    result = first + second;

    return result;
  }

  public String parseExpression(String expression) {
    // Remove whitespace to simplify expression manipulation
    expression = expression.replaceAll("\\s+", "");

    // Use a stack to store numbers and intermediate results
    Deque<Double> numberStack = new ArrayDeque<>();
    // Use a stack to store operators
    Deque<Character> operatorStack = new ArrayDeque<>();

    for (int i = 0; i < expression.length(); i++) {
      char currentChar = expression.charAt(i);

      if (Character.isDigit(currentChar) || currentChar == '.') {
        // If the character is a digit or decimal point, read the entire number
        StringBuilder numberBuilder = new StringBuilder();
        while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
          numberBuilder.append(expression.charAt(i));
          i++;
        }
        i--;

        // Convert the number to double and push it onto the number stack
        double number = Double.parseDouble(numberBuilder.toString());
        numberStack.push(number);
      } else if (currentChar == '(') {
        // If the character is an opening parenthesis, push it onto the operator stack
        operatorStack.push(currentChar);
      } else if (currentChar == ')') {
        // If the character is a closing parenthesis, evaluate the expression inside the
        // parentheses
        evaluateExpression(numberStack, operatorStack);
        // Remove the corresponding opening parenthesis from the operator stack
        try {
          operatorStack.pop();
        } catch (NoSuchElementException e) {
          finalResult = "ERROR";
          break;
        }
      } else {
        // If the character is an operator, compare its precedence with the operator at
        // the top of the operator stack
        while (!operatorStack.isEmpty() && hasPrecedence(currentChar, operatorStack.peek())) {
          // Evaluate the expression using the operator at the top of the operator stack
          evaluateExpression(numberStack, operatorStack);
        }
        // Push the current operator onto the operator stack
        operatorStack.push(currentChar);
      }
    }

    // Evaluate any remaining operators in the operator stack
    while (!operatorStack.isEmpty()) {
      evaluateExpression(numberStack, operatorStack);
    }

    // The final result is the only element remaining in the number stack
    return finalResult;
  }

  private void evaluateExpression(Deque<Double> numberStack, Deque<Character> operatorStack) {
    // Pop the operator from the operator stack
    char operator = operatorStack.pop();
    // Pop the two operands from the number stack
    try {
      double operand2 = numberStack.pop();
      double operand1 = numberStack.pop();
      double result = performOperation(operator, operand1, operand2);

      // Push the result back onto the number stack
      numberStack.push(result);

      // Check if the result is a integer or a double
      if (result % 1 == 0) {
        finalResult = String.valueOf((int) result);
      } else {
        finalResult = String.valueOf(result);
      }

    } catch (NoSuchElementException e) {
      finalResult = "ERROR";
    } catch (IllegalArgumentException e) {
      finalResult = "ERROR";
    }
  }

  private double performOperation(char operator, double operand1, double operand2) {
    // Perform the expression based on the operator
    switch (operator) {
      case '+':
        return operand1 + operand2;
      case '-':
        return operand1 - operand2;
      case '*':
        return operand1 * operand2;
      case '/':
        return operand1 / operand2;
      case '^':
        return Math.pow(operand1, operand2);
      default:
        throw new IllegalArgumentException("Invalid operator: " + operator);
    }
  }

  private boolean hasPrecedence(char operator1, char operator2) {
    // Check the precedence of the two operators
    if (operator2 == '(' || operator2 == ')') {
      return false;
    }
    if ((operator1 == '^') && (operator2 == '*' || operator2 == '/' || operator2 == '+' || operator2 == '-')) {
      return false;
    }
    if ((operator1 == '*' || operator1 == '/') && (operator2 == '+' || operator2 == '-')) {
      return false;
    }
    return true;
  }
}
