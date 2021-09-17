package com.my.db;

import com.my.db.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface Worker {
    boolean insertUser(Connection con, User user) throws SQLException;
    User getUser(Connection con, String login) throws SQLException;
    User getUser(Connection con, int id) throws SQLException;
    List<User> selectAllUser(Connection con) throws SQLException;
    List<Service> selectService(Connection con, User user) throws SQLException;
    List<ViewService> getServiceAsView(Connection con, List<Service> serviceL) throws SQLException;
    Category selectCategory(Connection con, int id) throws SQLException;
    Product selectProduct(Connection con, int id) throws SQLException;
    Status selectStatus(Connection con, int id) throws SQLException;
    void updateStatus(Connection con, int serviceId, int status) throws SQLException;
    List<Product> selectAllProduct(Connection con, int categoryId) throws SQLException;
    List<Category> selectAllCategory(Connection con) throws SQLException;
    void deleteCategory(Connection con, int id) throws SQLException;
    Category insertCategory(Connection con, String categoryName) throws SQLException;
    void insertProduct(Connection con, Product product) throws SQLException;
    void deleteProduct(Connection con, int id) throws SQLException;
    List<User> selectAllUser(Connection con, int productId) throws SQLException;
    void updateProduct(Connection con, Product product) throws SQLException;
    void deleteService(Connection con, int serviceId) throws SQLException;
    Service insertService(Connection con, int userid, int idp) throws SQLException;
    void updateCash(Connection con, User user) throws SQLException;
    List<Service> selectAllService(Connection con) throws SQLException;
    TimeT getTimeT(Connection con, int id) throws SQLException;
    void updateTimeT(Connection con, TimeT timeT) throws SQLException;
}
