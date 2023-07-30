package com.example.restservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CalculatorController {

	@GetMapping("/hello")
	public String hello() {
		return "Hello, World!";
	}

	@CrossOrigin(origins = "http://localhost:3000")
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
