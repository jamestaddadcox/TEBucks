package com.techelevator.tebucks.client.services;

import com.techelevator.tebucks.client.model.LoggerLoginDto;
import com.techelevator.tebucks.model.LogDto;
import org.apache.juli.logging.Log;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
public class LoggingService {
	private static final String API_BASE_URL = "https://te-pgh-api.azurewebsites.net/";
	private final RestTemplate restTemplate = new RestTemplate();
	private String authToken = null;


	public void login(String username, String password) {
		LoggerLoginDto loginDto = new LoggerLoginDto();
		loginDto.setUsername(username);
		loginDto.setPassword(password);

		try {
			ResponseEntity<LoggerLoginDto> response = restTemplate.postForEntity(API_BASE_URL + "/api/Login", loginDto, LoggerLoginDto.class);
			if (response.getStatusCode() == HttpStatus.OK) {
				this.authToken = response.getBody().getToken();
			}
		} catch (RestClientResponseException | ResourceAccessException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Credentials");
		}
	}

	public void register(String username, String password) {
		LoggerLoginDto registerDto = new LoggerLoginDto();
		registerDto.setUsername(username);
		registerDto.setPassword(password);

		try {
			restTemplate.postForEntity(API_BASE_URL + "/api/Login/register", registerDto, Void.class);
		} catch (RestClientResponseException | ResourceAccessException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Registration failed");
		}
	}

	public LogDto logTransaction(LogDto logDto) {
		if (authToken == null) {
			throw new IllegalStateException("Must be logged in to log transaction");
		}

		try {
			HttpEntity<LogDto> entity = makeLogEntity(logDto);
			ResponseEntity<LogDto> response = restTemplate.postForEntity(API_BASE_URL + "/api/TxLog", entity, LogDto.class);
			return response.getBody();
		} catch (RestClientResponseException | ResourceAccessException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User must be logged in to log data");
		}
	}

	private HttpEntity<LogDto> makeLogEntity(LogDto log) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(authToken);
		return new HttpEntity<>(log, headers);
	}


}
