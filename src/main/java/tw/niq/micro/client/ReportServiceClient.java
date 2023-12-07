package tw.niq.micro.client;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import tw.niq.micro.model.ReportModel;

@FeignClient(name = "report-service")
public interface ReportServiceClient {
	
	Logger logger = LoggerFactory.getLogger(ReportServiceClient.class);
	
	@GetMapping(path = "/api/v1/reports")
	@Retry(name = "report-service")
	@CircuitBreaker(
			name = "report-service", 
			fallbackMethod="getReportsFallback")
	public List<ReportModel> getReports(@RequestParam(value = "userId", required = false) String userId);

	default List<ReportModel> getReportsFallback(String userId, Throwable exception) {
		logger.debug("userId: " + userId);
		logger.debug("exception.getMessage(): " + exception.getMessage());
		return new ArrayList<>();
	}
	
}
