package finalProject.ssfpaf.project.models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import jakarta.json.Json;
import jakarta.json.JsonObject;
// import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

public class Metal {
    public Integer price;
    public String metal;

    public Integer getPrice() { return price;  }
    public void setPrice(Integer price) { this.price = price;  }

    public String getMetal() { return metal; }
    public void setMetal(String metal) { this.metal = metal; }

    public static Metal create(String json) throws IOException {
        Metal metalInfo = new Metal();

        System.out.println(">>> json data: " + json);
        System.out.println(">>> json data with bytes: " + json.getBytes());

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject o = reader.readObject();


            metalInfo.price = o.getInt("price");
            metalInfo.metal = o.getString("metal");
            // JsonObjectBuilder builder = Json.createObjectBuilder();
            
            // metal.metal = o.getString("metal");
            // metal.price = o.getInt("price");
            // builder.add("metal", o.getString("metal"));
            // builder.add("price", o.getInt("price"));
            
            System.out.println(">>>>> metal: \n" + metalInfo.getMetal());
            System.out.println(">>>>> price: \n" + metalInfo.getPrice());
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return metalInfo;
    }
}
