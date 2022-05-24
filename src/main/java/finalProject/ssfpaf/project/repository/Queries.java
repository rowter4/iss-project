package finalProject.ssfpaf.project.repository;

public interface Queries {

    public static final String SQL_CHECK_FOR_USERNAME = 
        "select * from users where email = ? and password = sha1(?)";

    public static final String SQL_ADD_NEW_USER =
        "insert into users(username, email, password) values (?,?,sha(?))";

    public static final String SQL_REMOVE_USER =
        "delete from users where username = ?";
    
    public static final String SQL_INSERT_ORDER_DETAILS = 
        "insert into po(ord_id, username) values (?,?) " ;

    public static final String SQL_INSERT_ITEMS_LIST = 
        "insert into individual_item(ord_id, currency, price, amount, material) values (?,?,?,?,?)" ; 

    public static final String SQL_GET_GOLD_DETAILS_FROM_ORDER_ID = 
        "select * from individual_item where ord_id = ? and material = 'Gold'";
    
    public static final String SQL_GET_SILVER_DETAILS_FROM_ORDER_ID = 
        "select * from individual_item where ord_id = ? and material = 'Silver' ";
}
