package finalProject.ssfpaf.project.models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
// import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

public class Metal {
    private Integer itemId;
    public String metal;
    public String currency;
    public Integer price;
    public Integer amount;

    public Integer getItemId() { return itemId; }
    public void setItemId(Integer itemId) {  this.itemId = itemId; }

    public Integer getPrice() { return price;  }
    public void setPrice(Integer price) { this.price = price;  }

    public String getMetal() { return metal; }
    public void setMetal(String metal) { this.metal = metal; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) {  this.amount = amount;}

    public static Metal create(String json) throws IOException {
        Metal metalInfo = new Metal();

        System.out.println(">>> json data: " + json);
        System.out.println(">>> json data with bytes: " + json.getBytes());

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject o = reader.readObject();


            metalInfo.setPrice(o.getInt("price"));
            metalInfo.setMetal(o.getString("metal"));
            metalInfo.setCurrency(o.getString("currency"));
            // JsonObjectBuilder builder = Json.createObjectBuilder();
            
            // metal.metal = o.getString("metal");
            // metal.price = o.getInt("price");
            // builder.add("metal", o.getString("metal"));
            // builder.add("price", o.getInt("price"));
            
            System.out.println(">>>>> metal: \n" + metalInfo.getMetal());
            System.out.println(">>>>> price: \n" + metalInfo.getPrice());
            System.out.println(">>>>> currency: \n" + metalInfo.getCurrency());
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return metalInfo;
    }

    public static Metal convertRs(SqlRowSet rs) {
        Metal metal = new Metal();

        metal.setMetal(rs.getString("material"));
        metal.setAmount(rs.getInt("amount"));
        metal.setCurrency(rs.getString("currency"));
        metal.setPrice(rs.getInt("price"));

        return metal;
    }
}
