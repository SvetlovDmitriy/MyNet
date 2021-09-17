package com.my.db;

import com.my.db.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class Worker {
    private static String classFQN;
    private static Worker instance;

    public static synchronized void init (String fqn){
        if (classFQN != null){
            throw new IllegalStateException("Cannot init twice");
        }
        classFQN = fqn;
    }

    public static synchronized Worker getInstance() {
        if (instance == null) {
            try {
                return instance = (Worker)Class.forName(classFQN).newInstance();
            } catch (ReflectiveOperationException ex) {
                throw new IllegalStateException("Cannot obtain an instance", ex);
            }
        }
        return instance;
    }

    protected Worker() {
    }

    public abstract boolean insertUser(Connection con, User user) throws SQLException;
    public abstract User getUser(Connection con, String login) throws SQLException;
    public abstract User getUser(Connection con, int id) throws SQLException;
    public abstract List<User> selectAllUser(Connection con) throws SQLException;
    public abstract List<Service> selectService(Connection con, User user) throws SQLException;
    public abstract List<ViewService> getServiceAsView(Connection con, List<Service> serviceL) throws SQLException;
    public abstract Category selectCategory(Connection con, int id) throws SQLException;
    public abstract Product selectProduct(Connection con, int id) throws SQLException;
    public abstract Status selectStatus(Connection con, int id) throws SQLException;
    public abstract void updateStatus(Connection con, int serviceId, int status) throws SQLException;
    public abstract List<Product> selectAllProduct(Connection con, int categoryId) throws SQLException;
    public abstract List<Category> selectAllCategory(Connection con) throws SQLException;
    public abstract void deleteCategory(Connection con, int id) throws SQLException;
    public abstract Category insertCategory(Connection con, String categoryName) throws SQLException;
    public abstract void insertProduct(Connection con, Product product) throws SQLException;
    public abstract void deleteProduct(Connection con, int id) throws SQLException;
    public abstract List<User> selectAllUser(Connection con, int productId) throws SQLException;
    public abstract void updateProduct(Connection con, Product product) throws SQLException;
    public abstract void deleteService(Connection con, int serviceId) throws SQLException;
    public abstract Service insertService(Connection con, int userid, int idp) throws SQLException;
    public abstract void updateCash(Connection con, User user) throws SQLException;
    public abstract List<Service> selectAllService(Connection con) throws SQLException;
    public abstract TimeT getTimeT(Connection con, int id) throws SQLException;
    public abstract void updateTimeT(Connection con, TimeT timeT) throws SQLException;
}
