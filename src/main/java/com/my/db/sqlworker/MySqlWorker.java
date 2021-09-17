package com.my.db.sqlworker;

import com.my.db.Worker;
import com.my.db.entity.Service;
import com.my.db.entity.*;
import com.my.db.entity.ViewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.db.dbUtilit.DbUtilit.*;
import static com.my.constant.AppConstant.EXCEPTION;
import static com.my.db.dbconstant.Constant.*;

public class MySqlWorker implements Worker {
    private static final Logger log = LogManager.getLogger(MySqlWorker.class);
    private static MySqlWorker mySqlWorker;

    private MySqlWorker(){}

    public static synchronized MySqlWorker getInstance(){
        if (mySqlWorker == null){
            return mySqlWorker = new MySqlWorker();
        }
        return mySqlWorker;
    }
    @Override
    public boolean insertUser(Connection con, User user){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setString(k++, user.getLogin());
            pstmt.setString(k++, user.getPassword());
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }
        } catch (SQLException ex){
            log.error(EXCEPTION, "Don't insert user in method insertUser", ex);
            return false;
        }
        finally {
            close(rs, pstmt);
        }
        return true;
    }
    @Override
    public User getUser(Connection con, String login) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            pstmt = con.prepareStatement(SELECT_USER_BY_LOGIN);
            int k = 1;
            pstmt.setString(k++, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt(ID));
                user.setPassword(rs.getString(PASSWORD));
                user.setCash(rs.getDouble(CASH));
                user.setRole(rs.getInt(ROLE_ID));
                user.setLogin(login);
            }
        } catch (SQLException ex){
            log.error(EXCEPTION, "Don't select user in method getUser", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
        return user;
    }

    @Override
    public User getUser(Connection con, int id) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            pstmt = con.prepareStatement(SELECT_USER_BY_ID);
            int k = 1;
            pstmt.setInt(k++, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt(ID));
                user.setPassword(rs.getString(PASSWORD));
                user.setCash(rs.getDouble(CASH));
                user.setRole(rs.getInt(ROLE_ID));
                user.setLogin(rs.getString(LOGIN));
            }
        } catch (SQLException ex){
            log.error(EXCEPTION, "Don't select user in method getUser", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
        return user;
    }

    @Override
    public List<User> selectAllUser(Connection con) throws SQLException{
        List<User> userL = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SELECT_ALL_USER);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(ID));
                user.setLogin(rs.getString(LOGIN));
                user.setRole(rs.getInt(ROLE_ID));
                user.setCash(rs.getDouble(CASH));
                userL.add(user);
            }
        } catch (SQLException ex){
            log.error(EXCEPTION, "Can't find user. Method findAllUsers()", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
        return userL;
    }

    @Override
    public List<User> selectAllUser(Connection con, int productId) throws SQLException{
        List<User> userL = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SELECT_ALL_USER_BY_PRODUCT_ID);
            int k = 1;
            pstmt.setInt(k++, productId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(ID));
                user.setLogin(rs.getString(LOGIN));
                userL.add(user);
            }
        } catch (SQLException ex){
            log.error(EXCEPTION, "Can't find user. Method selectUsers(productId)", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
        return userL;
    }

    @Override
    public List<Service> selectService(Connection con, User user) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Service> serviceL = new ArrayList<>();
        try {
            pstmt = con.prepareStatement(SELECT_SERVICE_BY_USER_ID);
            int k = 1;
            pstmt.setInt(k++, user.getId());
            rs = pstmt.executeQuery();
            while (rs.next()){
                Service service = new Service();
                service.setId(rs.getInt(ID));
                service.setUserId(rs.getInt(USER_ID));
                service.setProductId(rs.getInt(PRODUCT_ID));
                service.setCategoryId(rs.getInt(CATEGORY_ID));
                service.setStatusId(rs.getInt(STATUS_ID));
                serviceL.add(service);
            }
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant select Service in table Service method selectService()", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
        return serviceL;
    }

    @Override
    public List<ViewService> getServiceAsView(Connection con, List<Service> serviceL) throws SQLException {
        List<ViewService> serviceView = new ArrayList<>();
        try {
            for (Service service : serviceL) {
                Category category = selectCategory(con, service.getCategoryId());
                Product product = selectProduct(con, service.getProductId());
                Status status = selectStatus(con, service.getStatusId());
                ViewService viewService = new ViewService();
                viewService.setServiceID(service.getId());
                viewService.setProductName(product.getName());
                viewService.setProductPrice(product.getPrice());
                viewService.setProductDescription(product.getDescription());
                viewService.setStatusName(status.getName());
                viewService.setCategoryName(category.getName());
                serviceView.add(viewService);
            }
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Cant do View Service Map method getServiceAsMap()", ex);
            throw new SQLException(ex);
        }
        return serviceView;
    }

    @Override
    public Category selectCategory(Connection con, int id) throws SQLException {
        Category category = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SELECT_CATEGORY_BY_ID);
            int k = 1;
            pstmt.setInt(k++, id);
            rs = pstmt.executeQuery();
            if (rs.next()){
                category = new Category();
                category.setId(id);
                category.setName(rs.getString(NAME));
            }
        } catch (SQLException ex) {
            log.error(EXCEPTION, "can't select Category in method selectCategory", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
        return category;
    }

    @Override
    public Product selectProduct(Connection con, int id) throws SQLException {
        Product product = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SELECT_PRODUCT_BY_ID);
            int k = 1;
            pstmt.setInt(k++, id);
            rs = pstmt.executeQuery();
            if (rs.next()){
                product = new Product();
                product.setId(id);
                product.setName(rs.getString(NAME));
                product.setPrice(rs.getDouble(PRICE));
                product.setDescription(rs.getString(DESCRIPTION));
                product.setCategoryId(rs.getInt(CATEGORY_ID));
            }
        } catch (SQLException ex) {
            log.error(EXCEPTION, "can't select product in method selectProduct", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
        return product;
    }

    @Override
    public Status selectStatus(Connection con, int id) throws SQLException {
        Status status = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SELECT_STATUS_BY_ID);
            int k = 1;
            pstmt.setInt(k++, id);
            rs = pstmt.executeQuery();
            if (rs.next()){
                status = new Status();
                status.setId(id);
                status.setName(rs.getString(NAME));
            }
        } catch (SQLException ex) {
            log.error(EXCEPTION, "can't select status in method selectStatus", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
        return status;
    }

    @Override
    public void updateStatus(Connection con, int serviceId, int status) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(UPDATE_SERVICE_STATUS);
            int k = 1;
            pstmt.setInt(k++, status);
            pstmt.setInt(k++, serviceId);
            pstmt.executeUpdate();
        } catch (SQLException ex){
            log.error(EXCEPTION, "Don't update status service  updateStatus", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
    }

    @Override
    public void updateProduct(Connection con, Product product) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(UPDATE_PRODUCT);
            int k = 1;
            pstmt.setString(k++, product.getName());
            pstmt.setDouble(k++, product.getPrice());
            pstmt.setString(k++, product.getDescription());
            pstmt.setInt(k++, product.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex){
            log.error(EXCEPTION, "Cant update product service  updateProduct", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
    }

    @Override
    public void deleteService(Connection con, int serviceId) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(DELETE_SERVICE_BY_ID);
            int k = 1;
            pstmt.setInt(k++, serviceId);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Can't find delete service in metod deleteService", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
    }

    @Override
    public Service insertService(Connection con, int userId, int idp) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Service service = null;
        try {
            pstmt = con.prepareStatement(INSERT_SERVICE_BY_PRODUCT_ID, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setInt(k++, userId);
            pstmt.setInt(k++, idp);
            pstmt.setInt(k++, idp);
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    service = new Service();
                    service.setId(rs.getInt(1));
                }
            }
        } catch (SQLException ex){
            log.error(EXCEPTION, "Don't insert service in method insertservice", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
        return service;
    }

    @Override
    public void updateCash(Connection con, User user) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(UPDATE_CASH);
            int k = 1;
            pstmt.setDouble(k++, user.getCash());
            pstmt.setInt(k++, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex){
            log.error(EXCEPTION, "Cant update user method updateCash", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
    }

    @Override
    public List<Service> selectAllService(Connection con) throws SQLException {
        List<Service> servicesL = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SELECT_ALL_SERVICE);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Service service = new Service();
                service.setId(rs.getInt(ID));
                service.setStatusId(rs.getInt(STATUS_ID));
                service.setUserId(rs.getInt(USER_ID));
                service.setProductId(rs.getInt(PRODUCT_ID));
                service.setCategoryId(rs.getInt(CATEGORY_ID));
                servicesL.add(service);
            }
        } catch (SQLException ex){
            log.error(EXCEPTION, "Can't find user. Method findAllUsers()", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
        return servicesL;
    }

    @Override
    public TimeT getTimeT(Connection con, int id) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        TimeT timeT = null;
        try {
            pstmt = con.prepareStatement(SELECT_TIME_BY_SERVICEID);
            int k = 1;
            pstmt.setInt(k++, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                timeT = new TimeT();
                timeT.setId(rs.getInt(ID));
                timeT.setServiceId(rs.getInt(SERVICE_ID));
                timeT.setTotal(rs.getInt(TOTAL));
                timeT.setTime(rs.getTimestamp(TIME));
            }
        } catch (SQLException ex){
            log.error(EXCEPTION, "Don't select time in method getTime", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
        return timeT;
    }

    @Override
    public void updateTimeT(Connection con, TimeT timeT) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(UPDATE_TIMET);
            int k = 1;
            pstmt.setInt(k++, timeT.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex){
            log.error(EXCEPTION, "Cant update timeT method updateTimeT", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
    }

    @Override
    public List<Product> selectAllProduct(Connection con, int categoryId) throws SQLException{
        List<Product> productL = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SELECT_ALL_PRODUCT);
            int k = 1;
            pstmt.setInt(k++, categoryId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt(ID));
                product.setName(rs.getString(NAME));
                product.setPrice(rs.getDouble(PRICE));
                product.setDescription(rs.getString(DESCRIPTION));
                product.setCategoryId(rs.getInt(CATEGORY_ID));
                productL.add(product);
            }
        } catch (SQLException ex){
            log.error(EXCEPTION, "Can't find product. Method findAllProduct()", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
        return productL;
    }

    @Override
    public List<Category> selectAllCategory(Connection con) throws SQLException{
        List<Category> categoryL = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SELECT_ALL_CATEGORY);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt(ID));
                category.setName(rs.getString(NAME));
                categoryL.add(category);
            }
        } catch (SQLException ex){
            log.error(EXCEPTION, "Can't find category. Method findAllCategory()", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
        return categoryL;
    }

    public void deleteCategory(Connection con, int id) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(DELETE_CATEGORY_BY_ID);
            int k = 1;
            pstmt.setInt(k++, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Can't find delete category in metod deleteCategory", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
    }

    @Override
    public Category insertCategory(Connection con, String categoryName) throws SQLException {
        Category category = new Category();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(INSERT_CATEGORY, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setString(k++, categoryName);
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    category.setId(rs.getInt(1));
                    category.setName(categoryName);
                }
            }
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Can't insert category in method insertCategory", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
        return category;
    }

    @Override
    public void insertProduct(Connection con, Product product) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setString(k++, product.getName());
            pstmt.setDouble(k++, product.getPrice());
            pstmt.setString(k++, product.getDescription());
            pstmt.setInt(k++, product.getCategoryId());
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    product.setId(rs.getInt(1));
                }
            }
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Can't insert product in method insertProduct", ex);
            throw new SQLException(ex);
        } finally {
            close(rs, pstmt);
        }
    }

    @Override
    public void deleteProduct(Connection con, int id) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(DELETE_PRODUCT_BY_ID);
            int k = 1;
            pstmt.setInt(k++, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            log.error(EXCEPTION, "Can't delete product in metod deleteProduct", ex);
            throw new SQLException(ex);
        }
        finally {
            close(rs, pstmt);
        }
    }
}
