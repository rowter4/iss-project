package finalProject.ssfpaf.project.repository;

// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

// import finalProject.ssfpaf.project.models.IndividualItem;
import finalProject.ssfpaf.project.models.Metal;
import finalProject.ssfpaf.project.models.Order;

import static finalProject.ssfpaf.project.repository.Queries.*;

// import java.lang.StackWalker.Option;
// import java.util.List;
// import java.util.Map;
import java.util.Optional;

@Repository
public class DetailOrderRepository {
    
    @Autowired
    public JdbcTemplate template;

    public boolean insertAllOrderDetails(Order orderDetails) {
        for (Metal perItem: orderDetails.getMetalList())
            if (!insertLineItem(orderDetails.getOrderId(), perItem))
                return false;
        return true;
    }

    public boolean insertLineItem(String orderId, Metal perItem) {
        int count = template.update(SQL_INSERT_ITEMS_LIST,
                                    orderId, perItem.getCurrency(), perItem.getPrice() , perItem.getAmount(), perItem.getMetal());
        return 1 == count;
    }

    public Optional<Metal> getGoldByOrderId(String orderid) {
        final SqlRowSet result1 = template.queryForRowSet(SQL_GET_GOLD_DETAILS_FROM_ORDER_ID, orderid);

        if (!result1.next())
            return Optional.empty();

        return Optional.of(Metal.convertRs(result1));
    }


    public Optional<Metal> getSilverByOrderId(String orderid) {
        final SqlRowSet result = template.queryForRowSet(SQL_GET_SILVER_DETAILS_FROM_ORDER_ID, orderid);

        if (!result.next())
            return Optional.empty();

        return Optional.of(Metal.convertRs(result));
    }
}
