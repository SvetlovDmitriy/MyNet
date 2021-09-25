package com.my.db;

import com.my.db.entity.*;
import com.my.db.sqlworker.MySqlWorker;
import com.my.web.utilit.CalculationOfPayments;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.my.constant.AppConstant.*;
import static com.my.db.dbUtilit.DbUtilit.*;

public class DBManager {
    private final DataSource ds;
    private final Worker sqlWorker;
    private static DBManager dbManager;
    private static final Logger log = LogManager.getLogger(MySqlWorker.class);

    public static synchronized DBManager getDbManager() {
        if (dbManager == null) {
            dbManager = new DBManager();
        }
        return dbManager;
    }

    private DBManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/MyNet");
            sqlWorker = Worker.getInstance();
        } catch (NamingException ex) {
            log.error(EXCEPTION, "Cannot init DBManager", ex);
            throw new IllegalStateException("Cannot init DBManager", ex);
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public boolean insertUser(User user) throws DBException {
        Connection con = null;
        boolean isInsert;
        try {
            con = getConnection();
            isInsert = sqlWorker.insertUser(con, user);
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            log.error(EXCEPTION, "Cant connection to database", ex);
            rollback(con);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
        return isInsert;
    }

    /**
     * Returns empty or not empty List<User>, but not Null
     */
    public List<User> findAllUser() throws DBException {
        List<User> userL = new ArrayList<>();
        Connection con = null;
        try {
            con = getConnection();
            userL = sqlWorker.selectAllUser(con);
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant connection to database", ex);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
        return userL;
    }

    public List<User> findAllUser(int productId) throws DBException {
        List<User> userL = new ArrayList<>();
        Connection con = null;
        try {
            con = getConnection();
            userL = sqlWorker.selectAllUser(con, productId);
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant connection to database", ex);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
        return userL;
    }

    public User getUser(String login) throws DBException {
        User user = null;
        Connection con = null;
        try {
            con = getConnection();
            user = sqlWorker.selectUser(con, login);
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant connection to database", ex);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
        return user;
    }

    public User getUser(int id) throws DBException {
        User user = null;
        Connection con = null;
        try {
            con = getConnection();
            user = sqlWorker.selectUser(con, id);
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant connection to database", ex);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
        return user;
    }

    public List<Service> getService(User user) throws DBException {
        List<Service> serviceL = new ArrayList<>();
        Connection con = null;
        try {
            con = getConnection();
            serviceL = sqlWorker.selectService(con, user);
        } catch (SQLException ex) {
            log.error(EXCEPTION, "can't select Service in metod selectService", ex);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
        return serviceL;
    }

    public List<ViewService> getUserService(List<Service> servicel) throws DBException {
        List<ViewService> serviceView = new ArrayList<>();
        Connection con = null;
        try {
            con = getConnection();
            serviceView = sqlWorker.getServiceAsView(con, servicel);
        } catch (SQLException ex) {
            log.error(EXCEPTION, "can't get User's Servicecs in metod getUserService", ex);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
        return serviceView;
    }

    public Category getCategory(int id) throws DBException {
        Category category = null;
        Connection con = null;
        try {
            con = getConnection();
            category = sqlWorker.selectCategory(con ,id);
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant connection to database", ex);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
        return category;
    }

    public Product getProduct(int id) throws DBException {
        Product product = null;
        Connection con = null;
        try {
            con = getConnection();
            product = sqlWorker.selectProduct(con ,id);
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant connection to database", ex);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
        return product;
    }

    public Status getStatus(int id) throws DBException {
        Status status = null;
        Connection con = null;
        try {
            con = getConnection();
            status = sqlWorker.selectStatus(con ,id);
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant connection to database", ex);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
        return status;
    }

    public void updateStatus(int serviceId, int status) throws DBException {
        Connection con = null;
        try {
            con = getConnection();
            sqlWorker.updateStatus(con, serviceId, status);
            con.commit();
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant connection to database", ex);
            rollback(con);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
    }

    /**
     * Returns empty or not empty List<User>, but not Null
     */
    public List<Product> findAllProduct(int categoryId) throws DBException {
        List<Product> productL = new ArrayList<>();
        Connection con = null;
        try {
            con = getConnection();
            productL = sqlWorker.selectAllProduct(con, categoryId);
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant connection to database", ex);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
        return productL;
    }

    public List<Category> findAllCategory() throws DBException {
        List<Category> categoryL = new ArrayList<>();
        Connection con = null;
        try {
            con = getConnection();
            categoryL = sqlWorker.selectAllCategory(con);
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant connection to database", ex);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
        return categoryL;
    }

    public void deleteCategory(int categoryId) throws DBException {
        Connection con = null;
        try {
            con = getConnection();
            sqlWorker.deleteCategory(con, categoryId);
            con.commit();
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant delete category", ex);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
    }

    public Category addCategory(String categoryName) throws DBException {
        Category category = new Category();
        Connection con = null;
        try {
            con = getConnection();
            category = sqlWorker.insertCategory(con, categoryName);
            con.commit();
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Can creat category method addCategory", ex);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
        return category;
    }

    public void addProduct(Product product) throws DBException {
        Connection con = null;
        try {
            con = getConnection();
            sqlWorker.insertProduct(con, product);
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            log.error(EXCEPTION, "Can creat product method addProduct", ex);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
    }

    public void deleteProduct(int id) throws DBException {
        Connection con = null;
        try {
            con = getConnection();
            sqlWorker.deleteProduct(con, id);
            con.commit();
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant delete product in method deleteProduct", ex);
            rollback(con);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
    }

    public void updateProduct(Product product) throws DBException {
        Connection con = null;
        try {
            con = getConnection();
            sqlWorker.updateProduct(con, product);
            con.commit();
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant connection to database", ex);
            rollback(con);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
    }

    public void deleteService(User user, int serviceId) throws DBException {
        Connection con = null;
        try {
            con = getConnection();
            TimeT timeT = sqlWorker.selectTimeT(con, serviceId);
            user = sqlWorker.selectUser(con, user.getId());
            Service service = sqlWorker.selectService(con, serviceId);
            Product product = sqlWorker.selectProduct(con, service.getProductId());
            CalculationOfPayments.getPayment(user, product, service, timeT);
            sqlWorker.updateCash(con, user);
            sqlWorker.deleteService(con, serviceId);
            con.commit();
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant delete service in method deleteservice", ex);
            rollback(con);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
    }

    public Service addService(int userid, int idp) throws DBException {
        Connection con = null;
        Service service = null;
        try {
            con = getConnection();
            service = sqlWorker.insertService(con, userid, idp);
            con.commit();
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant add service metho addservice", ex);
            rollback(con);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
        return service;
    }

    public void updateCash(User user) throws DBException {
        Connection con = null;
        try {
            con = getConnection();
            sqlWorker.updateCash(con, user);
            con.commit();
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant update cash in method updateCash", ex);
            rollback(con);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
    }

    public List<Service> findAllService() throws DBException {
        List<Service> serviceL = new ArrayList<>();
        Connection con = null;
        try {
            con = getConnection();
            serviceL = sqlWorker.selectAllService(con);
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant select service in method findAllService ", ex);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
        return serviceL;
    }

    public TimeT findTime(int serviceId) throws DBException {
        TimeT timeT = null;
        Connection con = null;
        try {
            con = getConnection();
            timeT = sqlWorker.selectTimeT(con, serviceId);
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant find time in method findTime", ex);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
        return timeT;
    }

    public void updateTimeT(TimeT timeT) throws DBException {
        Connection con = null;
        try {
            con = getConnection();
            sqlWorker.updateTimeT(con, timeT);
            con.commit();
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant update cash in method updateTimeT", ex);
            rollback(con);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
    }

    public Map<Category, List<Product>> getPriceList() throws DBException {
        List<Category> categoryL = new ArrayList<>();
        Map<Category, List<Product>> priceList = new HashMap<>();
        Connection con = null;
        try {
            con = getConnection();
            categoryL = sqlWorker.selectAllCategory(con);
            for(Category category : categoryL){
                List<Product> productL = sqlWorker.selectAllProduct(con, category.getId());
                priceList.put(category, productL);
            }
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant connection to database in method getPriceList", ex);
            throw new DBException(ex);
        }
        finally {
            close(con);
        }
        return priceList;
    }

    public void withdrawMoney() throws DBException {
        Connection con = null;
        try {
            con = getConnection();
            List<User> userL = sqlWorker.selectAllUser(con);
            for (User user : userL) {
                List<Service> serviceL = sqlWorker.selectService(con, user);
                for (Service service : serviceL) {
                    Product product = sqlWorker.selectProduct(con, service.getProductId());
                    TimeT timeT = sqlWorker.selectTimeT(con, service.getId());
                    CalculationOfPayments.getPayment(user, product, service, timeT);
                    sqlWorker.updateCash(con, user);
                    sqlWorker.updateTimeT(con, timeT);
                }
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            log.error(EXCEPTION, "Cant do method withdrawMoney", ex);
            throw new DBException(ex);
        } finally {
            close(con);
        }
    }
}

