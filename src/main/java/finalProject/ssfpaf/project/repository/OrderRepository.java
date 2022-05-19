package finalProject.ssfpaf.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import finalProject.ssfpaf.project.models.Order;
import static finalProject.ssfpaf.project.repository.Queries.*;

@Repository
public class OrderRepository {
    
    @Autowired
    public JdbcTemplate template;

    public boolean insertOrderDetails(Order orderMetaData) {
        int count = template.update(SQL_INSERT_ORDER_DETAILS,
                                     orderMetaData.getOrderId(), orderMetaData.getUsername(), orderMetaData.getDate());

        return 1 == count;
    }

}
