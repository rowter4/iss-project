package finalProject.ssfpaf.project.repository;

public interface Queries {

    public static final String SQL_CHECK_FOR_USERNAME = 
        "select * from users where email = ? and password = sha1(?)";

    public static final String SQL_ADD_NEW_USER =
        "insert into users(username, email, password) values (?,?,sha(?))";

    public static final String SQL_REMOVE_USER =
        "delete from users where username = ?";
    
}
