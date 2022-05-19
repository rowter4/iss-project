package finalProject.ssfpaf.project.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import finalProject.ssfpaf.project.models.IndividualItem;
import finalProject.ssfpaf.project.models.Order;

import static finalProject.ssfpaf.project.repository.Queries.*;

@Repository
public class DetailOrderRepository {
    
    @Autowired
    public JdbcTemplate template;

    public boolean insertAllOrderDetails(Order orderDetails) {
        for (IndividualItem perItem: orderDetails.getIndividualItems())
            if (!insertLineItem(orderDetails.getOrderId(), perItem))
                return false;
        return true;
    }

    public boolean insertLineItem(String orderId, IndividualItem perItem) {
        int count = template.update(SQL_INSERT_ITEMS_LIST,
                                    perItem.getAmount(), perItem.getPrice() , orderId);
        return 1 == count;
    }
}
