package heavenhrservice.heavenhr;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractTestClass {	
	
	TestRestTemplate restTemplate = new TestRestTemplate();
	
	
	
	public String createURLWithPort(String uri) {
		return "http://localhost:" + port() + uri;
	}

	public ResponseEntity<String> getResponse(String url, HttpEntity<?> entity, HttpMethod httpMethod) {
		String createURLWithPort = createURLWithPort(url);		
		return restTemplate.exchange(createURLWithPort, httpMethod, entity, String.class);

	}
	
	public ResponseEntity<?> getResponse(String url, HttpEntity<?> entity, HttpMethod httpMethod,Class<?> cls) {
		String createURLWithPort = createURLWithPort(url);		
		return restTemplate.exchange(createURLWithPort, httpMethod, entity, cls);

	}
	
	public String toJson(Object obj) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}
	
	public abstract int port();
	

}
