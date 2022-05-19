package finalProject.ssfpaf.project.models;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Order {
    private String orderId;
    private String username;
    private Date date;
    private List<IndividualItem> individualItems = new LinkedList<>();

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public List<IndividualItem> getIndividualItems() { return individualItems;  }
    public void setIndividualItems(List<IndividualItem> individualItems) {  this.individualItems = individualItems; }
    public void addLineItem(IndividualItem individualItem) { this.individualItems.add(individualItem); }

    
}
