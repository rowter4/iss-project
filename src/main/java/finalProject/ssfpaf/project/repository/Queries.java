package finalProject.ssfpaf.project.repository;

public interface Queries {

    public static final String SQL_CHECK_FOR_USERNAME = 
        "select count(*) as no_of_users from users where email = ? and password = sha1(?)";
    
}
