package com.my.bd;

import com.my.db.DBException;
import com.my.db.DBManager;
import com.my.db.Worker;
import com.my.db.entity.*;
import com.my.db.sqlworker.MySqlWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DBManager.class, LogManager.class, InitialContext.class, Context.class, Worker.class, MySqlWorker.class})
public class TestDBManager {

    private static MySqlWorker mySqlWorkerMock;
    private static DBManager dbManager;
    private static User user1;
    private static User user2;
    private static User user3;
    private static Connection conMock;
    private static Service service1;
    private static Service service2;
    private static Product product1;
    private static Product product2;
    private static Product product3;

    @BeforeClass
    public static void setup() throws Exception {
        mockStatic(LogManager.class);
        Logger logger = mock(Logger.class);
        when(LogManager.getLogger(any(Class.class))).thenReturn(logger);
        mockStatic(Worker.class);
        mySqlWorkerMock = Mockito.mock(MySqlWorker.class);
        when(Worker.getInstance()).thenReturn(mySqlWorkerMock);
        Context contextMok = Mockito.mock(Context.class);
        InitialContext initcontextMok = Mockito.mock(InitialContext.class);
        PowerMockito.whenNew(InitialContext.class)
                .withAnyArguments().thenReturn(initcontextMok);
        DataSource dataSourceMock = Mockito.mock(DataSource.class);
        when(initcontextMok.lookup(anyString())).thenReturn(contextMok);
        when(contextMok.lookup(anyString())).thenReturn(dataSourceMock);
        dbManager = DBManager.getDbManager();
        conMock = Mockito.mock(Connection.class);
        when(dbManager.getConnection()).thenReturn(conMock);
        doNothing().when(conMock).commit();
        user1 = new User(1, 2, "user1", "user1", 33);
        user2 = new User(2, 2, "user2", "user2", 44);
        user3 = new User(3, 2, "user3", "user3", 55);
        service1 = new Service(1, 2, 1, 1, 1);
        service2 = new Service(2, 2, 4, 2,1);
        product1 = new Product(1, 33., "TV++", "", 1);
        product2 = new Product(2, 44., "TV mega+", "", 1);
        product3 = new Product(4, 55., "TV +all", "", 1);
    }

    @Test
    public void testFindAllUser() throws Exception {
        List<User> actual = new ArrayList<>();
        actual.add(user1);
        actual.add(user2);
        when(mySqlWorkerMock.selectAllUser(any())).thenReturn(actual);
        List<User> expected = dbManager.findAllUser();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testInsertUser() throws DBException {
        when(mySqlWorkerMock.insertUser(conMock, user1)).thenReturn(true);
        boolean expected = dbManager.insertUser(user1);
        Assert.assertTrue(expected);
    }

    @Test
    public void testFindAllUserLimit() throws DBException, SQLException {
        List<User> actual = new ArrayList<>();
        actual.add(user1);
        actual.add(user2);
        actual.add(user3);
        when(mySqlWorkerMock.selectAllUserLimit(conMock, 0, 3)).thenReturn(actual);
        List<User> expected = dbManager.findAllUserLimit(0,3);
        Assert.assertEquals(expected ,actual);
    }

    @Test
    public void TestGetTotalUser() throws DBException, SQLException {
        int actual = 3;
        when(mySqlWorkerMock.selectAllUserCount(conMock)).thenReturn(actual);
        int expected = dbManager.getTotalUser();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void TestFindAllUser() throws DBException, SQLException {
        List<User> actual  = new ArrayList<>();
        actual.add(user1);
        actual.add(user2);
        int productId = 1;
        when(mySqlWorkerMock.selectAllUser(conMock, productId)).thenReturn(actual);
        List<User> expected =  dbManager.findAllUser(productId);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetUserByLogin() throws DBException, SQLException {
        String login = "usere2";
        User actual = user2;
        when(mySqlWorkerMock.selectUser(conMock, login)).thenReturn(actual);
        User expected =  dbManager.getUser(login);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetUserById() throws DBException, SQLException {
        int id = 1;
        User actual = user1;
        when(mySqlWorkerMock.selectUser(conMock, id)).thenReturn(actual);
        User expected = dbManager.getUser(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetService() throws DBException, SQLException {
        User user = user2;
        List<Service> actual = new ArrayList<>();
        actual.add(service1);
        actual.add(service2);
        when(mySqlWorkerMock.selectService(conMock, user)).thenReturn(actual);
        List<Service> expected = dbManager.getService(user);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public  void testGetUserService() throws DBException, SQLException {
        List<Service> servicel = new ArrayList<>();
        servicel.add(service1);
        servicel.add(service2);
        ViewService viewService1 = new ViewService();
        viewService1.setServiceID(1);
        viewService1.setCategoryName("TV");
        viewService1.setProductName("TV++");
        viewService1.setProductPrice(22.);
        viewService1.setProductDescription("");
        viewService1.setStatusName("Run");
        ViewService viewService2 = new ViewService();
        viewService2.setServiceID(2);
        viewService2.setCategoryName("internet");
        viewService2.setProductName("internet 10Mb");
        viewService2.setProductPrice(33.);
        viewService2.setProductDescription("");
        viewService2.setStatusName("Run");
        List<ViewService> actual = new ArrayList<>();
        actual.add(viewService1);
        actual.add(viewService2);
        when(mySqlWorkerMock.getServiceAsView(conMock, servicel)).thenReturn(actual);
        List<ViewService> expected = dbManager.getUserService(servicel);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetCategory() throws DBException, SQLException {
        int id = 1;
        Category actual = new Category();
        actual.setId(1);
        actual.setName("TV");
        when(mySqlWorkerMock.selectCategory(conMock ,id)).thenReturn(actual);
        Category expected = dbManager.getCategory(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetProduct() throws DBException, SQLException {
        int id = 1;
        Product actual = product1;
        when(mySqlWorkerMock.selectProduct(conMock ,id)).thenReturn(actual);
        Product expected = dbManager.getProduct(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetStatus() throws DBException, SQLException {
        int id = 1;
        Status actual = new Status();
        actual.setId(1);
        actual.setName("Run");
        when(mySqlWorkerMock.selectStatus(conMock ,id)).thenReturn(actual);
        Status expected = dbManager.getStatus(id);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testUpdateStatus() throws DBException, SQLException {
        int serviceId = 1;
        int status = 1;
        dbManager.updateStatus(serviceId, status);
        verify(mySqlWorkerMock).updateStatus(conMock, serviceId, status);
    }

    @Test
    public void testFindAllProduct() throws DBException, SQLException {
        int categoryId = 1;
        List<Product> actual = new ArrayList<>();
        actual.add(product1);
        actual.add(product2);
        when(mySqlWorkerMock.selectAllProduct(conMock, categoryId)).thenReturn(actual);
        List<Product> expected = dbManager.findAllProduct(categoryId);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testFindAllCategory() throws DBException, SQLException {
        Category category1 = new Category();
        category1.setId(1);
        category1.setName("TV");
        Category category2 = new Category();
        category2.setId(2);
        category2.setName("Internet");
        List<Category> actual = new ArrayList<>();
        actual.add(category1);
        actual.add(category2);
        when(mySqlWorkerMock.selectAllCategory(conMock)).thenReturn(actual);
        List<Category> expected = dbManager.findAllCategory();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testDeleteCategory() throws DBException, SQLException {
        int categoryId = 1;
        dbManager.deleteCategory(categoryId);
        verify(mySqlWorkerMock).deleteCategory(conMock, categoryId);
    }

    @Test
    public void testAddCategory() throws DBException, SQLException {
        String categoryName = "TV";
        Category actual = new Category();
        actual.setId(2);
        actual.setName("TV");
        when(mySqlWorkerMock.insertCategory(conMock, categoryName)).thenReturn(actual);
        Category expected = dbManager.addCategory(categoryName);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testAddProduct() throws DBException, SQLException {
        Product product = product1;
        dbManager.addProduct(product);
        verify(mySqlWorkerMock).insertProduct(conMock, product);
    }

    @Test
    public void testDeleteProduct() throws DBException, SQLException {
        int id = 1;
        dbManager.deleteProduct(id);
        verify(mySqlWorkerMock).deleteProduct(conMock, id);
    }

    @Test
    public void testUpdateProduct() throws DBException, SQLException {
        dbManager.updateProduct(product1);
        verify(mySqlWorkerMock).updateProduct(conMock, product1);
    }

    @Test
    public void testDeleteService() throws DBException, SQLException {
        int serviceId = 1;
        TimeT timeT = new TimeT();
        timeT.setTime(new Timestamp(10L));
        timeT.setServiceId(serviceId);
        timeT.setId(1);
        timeT.setTotal(22);
        when(mySqlWorkerMock.selectUser(conMock, user2.getId())).thenReturn(user2);
        when(mySqlWorkerMock.selectTimeT(conMock, serviceId)).thenReturn(timeT);
        when(mySqlWorkerMock.selectService(conMock, serviceId)).thenReturn(service2);
        when(mySqlWorkerMock.selectProduct(conMock, service2.getProductId())).thenReturn(product3);
        dbManager.deleteService(user2, serviceId);
        verify(mySqlWorkerMock).updateCash(conMock, user2);
        verify(mySqlWorkerMock).deleteService(conMock, serviceId);
    }

    @Test
    public void testAddService() throws DBException, SQLException {
        int userid = 2;
        int idp = 1;
        Service actual = service1;
        when(mySqlWorkerMock.insertService(conMock, userid, idp)).thenReturn(actual);
        Service expected = dbManager.addService(userid, idp);
        Assert.assertEquals(expected, actual);
    }
}
