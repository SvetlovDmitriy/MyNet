package com.my.db.dbconstant;

public class Constant {

    private Constant() {
        super();
    }

    public static final String INSERT_USER = "INSERT INTO user (login, password) VALUES (?, ?)";
    public static final String SELECT_USER_BY_LOGIN = "SELECT * FROM user WHERE login LIKE ? ";
    public static final String SELECT_ALL_USER = "SELECT * FROM user ORDER BY id";
    public static final String SELECT_ALL_CATEGORY = "SELECT * FROM category";
    public static final String SELECT_ALL_PRODUCT = "SELECT * FROM product WHERE category_id LIKE ?";
    public static final String SELECT_SERVICE_BY_USER_ID = "SELECT * FROM service WHERE user_id LIKE ?";
    public static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM category WHERE id LIKE ?";
    public static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM product WHERE id LIKE ?";
    public static final String SELECT_STATUS_BY_ID = "SELECT * FROM status WHERE id LIKE ?";
    public static final String UPDATE_SERVICE_STATUS = "UPDATE service SET status_id = ? WHERE id = ?";
    public static final String DELETE_CATEGORY_BY_ID = "DELETE FROM category WHERE id LIKE ?";
    public static final String INSERT_CATEGORY = "INSERT INTO category (name) VALUES (?)";
    public static final String INSERT_PRODUCT = "INSERT INTO product (name, price, description, category_id) " +
            "VALUES (?, ?, ?, ?)";
    public static final String DELETE_PRODUCT_BY_ID = "DELETE FROM product WHERE id LIKE ?";
    public static final String SELECT_ALL_USER_BY_PRODUCT_ID = "SELECT user.id, user.login, product_id\n" +
            "FROM user, service\n" +
            "WHERE product_id= ? and user.id = user_id";
    public static final String UPDATE_PRODUCT = "UPDATE product SET name=?, price=?, description=? WHERE id=?";
    public static final String DELETE_SERVICE_BY_ID = "DELETE FROM service WHERE id LIKE ?";
    public static final String INSERT_SERVICE_BY_PRODUCT_ID = "INSERT INTO service " +
            "(user_id, product_id, category_id, status_id) " +
            "VALUES(?, ?, (SELECT category_id FROM product WHERE ? = id), " +
            "(SELECT id FROM status WHERE name LIKE \"Run\"))";
    public static final String UPDATE_CASH = "UPDATE user SET cash=? WHERE id=?";
    public static final String SELECT_ALL_SERVICE = "SELECT * FROM service";
    public static final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE id LIKE ?";
    public static final String SELECT_TIME_BY_SERVICEID = "SELECT * FROM timeT WHERE service_id LIKE ?";
    public static final String UPDATE_TIMET = "UPDATE timeT SET `time`=CURRENT_TIMESTAMP, total=0 WHERE id=?";
    public static final String SELECT_SERVICE_BY_ID = "SELECT * FROM service WHERE id LIKE ?";
    public static final String ID = "id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String CASH = "cash";
    public static final String ROLE = "role";
    public static final String ROLE_ID = "role_id";
    public static final String USER_ID = "user_id";
    public static final String PRODUCT_ID = "product_id";
    public static final String CATEGORY_ID = "category_id";
    public static final String STATUS_ID = "status_id";
    public static final String PRICE = "price";
    public static final String DESCRIPTION = "description";
    public static final String SERVICE_ID = "service_id";
    public static final String TOTAL = "total";
    public static final String TIME = "time";
}
