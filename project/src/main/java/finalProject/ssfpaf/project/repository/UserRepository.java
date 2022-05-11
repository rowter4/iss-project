package finalProject.ssfpaf.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
// import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import static finalProject.ssfpaf.project.repository.Queries.*;


@Repository
public class UserRepository {
    
    @Autowired
    private JdbcTemplate template;

    public int getUsername(String email, String password) {
        SqlRowSet response = template.queryForRowSet(SQL_CHECK_FOR_USERNAME, email, password);
        
        if (!response.next())
            return 0;

        return response.getInt("no_of_users");
    }

}
