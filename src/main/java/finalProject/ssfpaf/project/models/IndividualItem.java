package finalProject.ssfpaf.project.models;

// can throw this document

public class IndividualItem {
    private Integer itemId;
    private String metalMaterial;
    private Integer amount;
    private Integer price;

    public Integer getItemId() { return itemId; }
    public void setItemId(Integer itemId) { this.itemId = itemId; }

    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public String getMetalMaterial() { return metalMaterial; }
    public void setMetalMaterial(String metalMaterial) {  this.metalMaterial = metalMaterial; }
    
}
