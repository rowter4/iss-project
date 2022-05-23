package finalProject.ssfpaf.project.service;

import java.io.IOException;
// import java.io.StringReader;
import java.util.Optional;

// import com.mysql.cj.xdevapi.JsonArray;

// import java.net.http.HttpHeaders;

// import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import finalProject.ssfpaf.project.models.Metal;
// import jakarta.json.Json;
// import jakarta.json.JsonObject;
// import jakarta.json.JsonReader;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class MetalService {

    String goldPrice;

    // @Value("${metal.api.key}")
    private String metalKey = "goldapi-205eqr1al31k7c1k-io";
    
    private static final String METAL_DEFAULT_URL = "https://www.goldapi.io/api/XAU/%s"; 
    // private static final String CURRENCY = "/XAU/%s";

    public Optional<Metal> getPrice(String currency) {

        String url = UriComponentsBuilder.fromUriString(METAL_DEFAULT_URL.formatted(currency))
                    .toUriString();
        
        // need to add a second URL for the second metal that we would like to get 
        System.out.println(">>>>> url: " + url);

        HttpHeaders headers = new HttpHeaders();

		headers.add("x-access-token", metalKey);
        headers.add("Content-Type", "application/json");

		RequestEntity<Void> req = RequestEntity
			.get(url)
			.headers(headers)
            .accept(MediaType.APPLICATION_JSON)
			.build();

        RestTemplate template = new RestTemplate();
        // ResponseEntity<String> resp = null;
        ResponseEntity<String> resp = template.exchange(req, String.class);

        System.out.printf(">>>> Payload: %s\n", resp.getBody());

        try {
            Metal m = Metal.create(resp.getBody());
            return Optional.of(m);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
