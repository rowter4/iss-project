package finalProject.ssfpaf.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import finalProject.ssfpaf.project.models.User;

// import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import static finalProject.ssfpaf.project.repository.Queries.*;
import static finalProject.ssfpaf.project.models.ConversionUtils.*;

import java.util.Optional;


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

    public Optional<User> findUserByEmail(String email, String password) {
        final SqlRowSet rs = template.queryForRowSet(SQL_CHECK_FOR_USERNAME, email, password);
        if (!rs.next())
            return Optional.empty();

        return Optional.of(convertRs(rs));
    }

    public int createNewUser(String email, String username, String password) {
        SqlRowSet response = template.queryForRowSet(SQL_ADD_NEW_USER,email,username,password);
        return 1;
    }

    public boolean addUserDetails(String username, String email, String password) {
        int count = template.update(SQL_ADD_NEW_USER, username, email, password);        
        return 1 == count;
    }

    public boolean removeUserDetails(String username) {
        int count = template.update(SQL_REMOVE_USER, username);        
        return 1 == count;
    }

}
