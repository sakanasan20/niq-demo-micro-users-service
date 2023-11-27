package tw.niq.micro.model;

import java.util.Date;

import lombok.Data;

@Data
public class ErrorModel {

	private final Date timestamp;
	
	private final String message;
	
}
