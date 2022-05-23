package finalProject.ssfpaf.project.models;

// import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Order {
    private String orderId;
    private String username;
    private List<Metal> metalList = new LinkedList<>();

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    // public Date getDate() { return date; }
    // public void setDate(Date date) { this.date = date; }

    public List<Metal> getMetalList() { return metalList; }
    public void setMetalList(List<Metal> metalList) { this.metalList = metalList;  }
    public void addMetalList(Metal metalItem) { this.metalList.add(metalItem); }

    
}
