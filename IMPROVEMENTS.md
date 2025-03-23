
---

# Potential Improvements for Shunting Yard Calculator  

Based on my current calculator implementation, here are several improvements and features I plan to add in the future:

1. **Fix handling of negative numbers** – This is a priority since the calculator doesn't currently handle negative numbers correctly.

2. **Improve error handling** – I need to add more specific error messages for different cases, like division by zero, invalid syntax, or unbalanced parentheses.

3. **Add support for additional operations**:  
   - Exponentiation (`^`)  
   - Modulus operation (`%`)  
   - Mathematical functions (e.g., `sin`, `cos`, `sqrt`)  

4. **Enhance the tokenizer**:  
   - Better handling of spaces in expressions  
   - Support for scientific notation (e.g., `2.5E-3`)  

5. **Add input validation**:  
   - Check for invalid characters  
   - Validate the overall structure of the expression  

6. **Expand the Shunting Yard algorithm**:  
   - Ensure it properly handles all operator precedence rules  
   - Fix any potential issues with emptying the operand stack at the end  

7. **Implement a command-line interface (CLI)** with additional options:  
   - History of calculations  
   - Support for variables  
   - Proper program termination  

8. **Add unit tests**:  
   - Create JUnit tests for all components  
   - Test various edge cases  

9. **Improve code organization**:  
   - Consider using the **Strategy pattern** for different operations  
   - Organize classes into packages based on functionality  

10. **Add documentation**:  
    - Write JavaDoc for all classes and methods  
    - Add comments to the more complex parts of the algorithm  

---