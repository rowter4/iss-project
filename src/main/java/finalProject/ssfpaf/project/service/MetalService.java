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
    
    private static final String METAL_DEFAULT_URL = "https://www.goldapi.io/api%s"; 
    private static final String CURRENCY = "/%s/%s";

    public Optional<Metal> getPrice(String type, String currency) {

        String url = UriComponentsBuilder.fromUriString(METAL_DEFAULT_URL.formatted(CURRENCY.formatted(type,currency)))
                    .toUriString();
        
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

        // try {
        //     resp = template.exchange(req, String.class);
        //     Metal metal = Metal.create(resp.getBody());
        //     return Optional.of(metal); 

        // } catch (Exception ex) {
        //     ex.printStackTrace();
        //     return Optional.empty();
        // }

        // image fixed_width.url
        // JsonReader reader = Json.createReader(new StringReader(resp.getBody()));
        // JsonObject o = reader.readObject();

        // Metal metal = new Metal();
        // metal.setPrice(o.getInt("price"));

        // System.out.println(">>>>> metal: \n" + o.getString("metal"));
        // System.out.println(">>>>> price: \n" + o.getInt("price"));

        // return metal;
        // return null;


        // JsonArray data = gifs.getJsonArray("data");
        // for (int i = 0; i < data.size(); i++) {
        //     JsonObject gif = data.getJsonObject(i);
        //     String image = gif.getJsonObject("images").getJsonObject("fixed_width").getString("url");
        //     result.add(image);
        // }
        
        // return result;

        // JsonReader r = Json.createReader(new ByteArrayInputStream(rec.getBytes()));
        // JsonObject o = r.readObject();
        // Game g = new Game();
        // g.setGameId(o.getInt("gid"));
        // g.setName(o.getString("name"));
        // g.setImage(o.getString("image"));
        // g.setRanking(o.getInt("ranking"));
        // g.setUsersRated(o.getInt("users_rated"));
        // g.setYear(o.getInt("year"));
        // g.setUrl(o.getString("url"));
        // return g;

        // return null;
    }
}
