package com.example.restservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

	@GetMapping("/hello")
	public String hello() {
		return "Hello, World!";
	}

	@PostMapping("/calculate")
	public ResponseEntity<String> calculateExpression(@RequestBody String expression) {
		Calculator calculator = new Calculator();
		String result = calculator.parseExpression(expression);

		if (result.equals("ERROR")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
		} else {
			return ResponseEntity.ok(expression + " = " + calculator.parseExpression(expression));
		}
	}
}
