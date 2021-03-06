package finalProject.ssfpaf.project.models;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.util.MultiValueMap;

// import finalProject.ssfpaf.project.service.UserService;

public class ConversionUtils {
    
    
    public static User convert(MultiValueMap<String, String> form) {
       
        User user = new User();
        user.setUsername(form.getFirst("username"));
        user.setEmail(form.getFirst("email"));
        user.setPassword(form.getFirst("password"));

        System.out.printf(">>> q = %s, limit = %s, rating = %s\n", form.getFirst("username"), form.getFirst("email")
                                , form.getFirst("password"));
        return user;
    }

    public static User convertRs(SqlRowSet rs) {
        User user = new User();
        user.setEmail(rs.getString("email"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        return user;
    }
}
