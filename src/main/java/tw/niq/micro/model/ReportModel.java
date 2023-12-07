package tw.niq.micro.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReportModel {
	
	private Long id;
	
	private Long version;
	
	private Timestamp createdDate;
	
	private Timestamp lastModifiedDate;
	
	private String name;

}
