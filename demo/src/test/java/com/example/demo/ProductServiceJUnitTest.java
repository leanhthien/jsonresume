package com.example.demo;

import com.example.demo.entity.Product;
import com.example.demo.services.ProductService;
import com.example.demo.services.ProductServiceJdbcImpl;
import com.example.demo.services.UserService;
import com.example.demo.services.UserServiceImpl;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceJUnitTest {

    private ProductService productService = new ProductServiceJdbcImpl();
    private UserService userService = new UserServiceImpl();
    private final String USERNAME = "dbadmin1";
    private final long PRODUCT_ID = 1L;
    private final int MAX_NUMBER = 999999999;
    private final int MIN_NUMBER = 100000000;
    private long productId = 18L;
    private String userName;

    @BeforeAll
    public static void setup() {
        System.out.println("Start testing ProductService ...");
    }

    @BeforeEach
    public void init() {

        System.out.println("Init fields ...");

        List<Product> products = productService.listAllProducts();

        if (!products.isEmpty()) {
            productId = products.get(products.size()-1).getProductId();
            userName = products.get(products.size()-1).getAppUser().getUserName();
        }
    }

    @Test
    public void countId_fromListAllProduct() {

        System.out.println("Start testing countId_fromListAllProduct ...");

        List<Product> products = productService.listAllProducts();

        HashSet<String> noDupUser = new HashSet<>();

        for (Product product : products) {
            noDupUser.add(product.getAppUser().getUserName());
        }

        assertEquals(1, noDupUser.size());
    }

    @Test
    public void checkUserWithProduct_byListAllProductByUser() {

        System.out.println("Start testing checkUserWithProduct_byListAllProductByUser ...");

        List<Product> products = productService.listAllProductsByUser(userName);

        boolean isAllBelong = true;

        for (Product product : products) {
            if (!product.getAppUser().getUserName().equals(userName)) {
                isAllBelong = false;
                break;
            }
        }

        assertTrue(isAllBelong);
    }

    @Test
    public void compareId_fromGetProductById() {
        System.out.println("Start testing compareId_fromGetProductById ...");
        assertEquals(productId, (long) productService.getProductById(productId).getProductId());
    }

    @Test
    public void checkCreateProductWithIdAndTelephone_bySaveOrUpdateProduct() {
        System.out.println("Start testing checkCreateProductWithIdAndTelephone_bySaveOrUpdateProduct ...");

        List<Product> products = productService.listAllProducts();

        String randomTelephone = String.valueOf( (int) ((Math.random() * ((MAX_NUMBER - MIN_NUMBER) + 1)) + MIN_NUMBER));

        Product product = new Product();
        product.setName("Eugune");
        product.setJobTitle("Developer");
        product.setAddress("Cecilia Chapman 711-2880 Nulla St. Mankato Mississippi 96522");
        product.setTelephone(randomTelephone);
        product.setEmail("eugune@ltd.com");
        product.setWebsite("http://social.com/eugune");
        product.setLanguage("English");
        product.setAbout("My name is Albert, and I’m a Computer Engineer. My job is to provide job seekers with expert advice on career-related topics. I read a lot and consult recruiting professionals so you don’t have to. I show you how to hack the recruitment process, create a job-winning resume, ace the job interview, and... introduce yourself, among others.");

        Product newProduct = productService.saveOrUpdateProduct(product, userName);

        boolean isExist = false;
        for (Product item : products) {
            if (item.getProductId().equals(newProduct.getProductId())) {
                isExist = true;
                break;
            }
        }

        productService.deleteProduct(newProduct.getProductId());

        assertTrue(newProduct.getTelephone().equals(randomTelephone) && !isExist);
    }

    @Test
    public void checkUpdateProduct_bySaveOrUpdateProduct() {

        System.out.println("Start testing checkReturnProduct_bySaveOrUpdateProduct ...");

        Product product = productService.getProductById(productId);

        String oldTelephone = product.getTelephone();

        String randomTelephone = String.valueOf( (int) ((Math.random() * ((MAX_NUMBER - MIN_NUMBER) + 1)) + MIN_NUMBER));

        product.setTelephone(randomTelephone);

        Product editedProduct = productService.saveOrUpdateProduct(product, userName);

        product.setTelephone(oldTelephone);

        productService.saveOrUpdateProduct(product, userName);

        assertTrue(editedProduct.getTelephone().equals(randomTelephone)
                && editedProduct.getProductId() == productId);
    }

    @Test
    public void checkDeleteProduct_byDeleteProduct() {

        System.out.println("Start testing checkDeleteProduct_byDeleteProduct ...");

        Product product = productService.getProductById(productId);

        productService.deleteProduct(productId);

        Long product_id = productService.getProductById(productId).getProductId();

        productId = productService.saveOrUpdateProduct(product, product.getAppUser().getUserName()).getProductId();

        assertNull(product_id);
    }

    @Disabled
    @Test
    public void wrongSQLSyntax_inListAllProducts() {

        System.out.println("Start testing wrongSQLSyntax_inListAllProducts ...");
        assertNull(productService.listAllProducts(), "List product must be null");
    }

    @Disabled
    @Test
    public void invalidInput_inListAllProductsByUser() {

        System.out.println("Start testing invalidInput_inListAllProductsByUser ...");
        assertNull(productService.listAllProductsByUser("Jane"), "List product must be null");
    }

    @Disabled
    @Test
    public void invalidInput_inGetProductById() {

        System.out.println("Start testing invalidInput_inGetProductById...");
        assertNull(productService.getProductById(33L), "List product must be null");
    }

    @Disabled
    @Test
    public void wrongSQLSyntax_inGetProductById() {

        System.out.println("Start testing wrongSQLSyntax_inGetProductById...");
        assertNull( productService.getProductById(1L), "List product must be null");
    }

    @AfterAll
    public static void finish() {
        System.out.println("End testing ProductService ...");
    }
}
