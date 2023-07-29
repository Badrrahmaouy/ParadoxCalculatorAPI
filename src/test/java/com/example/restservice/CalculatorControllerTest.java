package com.example.restservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CalculatorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private final TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void testCalculateExpression() throws Exception {
		String expression = "2 + 3";
		String expectedResponse = "2 + 3 = 5";

		this.mockMvc.perform(post("/calculate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(expression))
				.andExpect(status().isOk())
				.andExpect(content().string(expectedResponse));
	}

    @Test
    public void testCalculateExpressionInvalidInput() throws Exception {
        String expression = "2 + n";
        String expectedResponse = "Invalid input";

        // Send a POST request to the /calculate route with the body of the string
        mockMvc.perform(post("/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expression))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    public void testInvalidRoute() throws Exception {
        String expression = "2 + 3";

        // Send a POST request to the /calculates route with the body of the string
        mockMvc.perform(post("/calculates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expression))
                .andExpect(status().isNotFound());
    }
}
