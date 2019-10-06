package com.example.demo;

import com.example.demo.entity.Product;
import com.example.demo.services.ProductService;
import com.example.demo.services.ProductServiceJdbcImpl;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceJUnitTest {

    private ProductService productService = new ProductServiceJdbcImpl();
    private final long TOTAL_USER_HAS_PRODUCT = 1L;
    private final long INVALID_ID = -1L;
    private final int MAX_NUMBER = 999999999;
    private final int MIN_NUMBER = 100000000;
    private long productId;
    private String userName;

    @BeforeAll
    public static void initAll() {
        System.out.println("Start testing ProductService ...");
    }

    @BeforeEach
    public void init() {

        System.out.println("Init fields ...");

        List<Product> products = productService.listAllProducts();

        if (!products.isEmpty()) {
            productId = products.get(products.size()-1).getProductId();
            userName = products.get(products.size()-1).getAppUser().getUserName();
            System.out.println("Product id: " + productId);
            System.out.println("Username: "+ userName);
        }
        else
        {
            System.out.println("Cannot find list product!");
        }
    }

    @DisplayName("Test count amount id from listAllProduct")
    @Test
    public void countId_fromListAllProduct() {

        List<Product> products = productService.listAllProducts();

        HashSet<String> noDupUser = new HashSet<>();

        for (Product product : products) {
            noDupUser.add(product.getAppUser().getUserName());
        }

        assertEquals(TOTAL_USER_HAS_PRODUCT, noDupUser.size());
    }

    @DisplayName("Check user with product by listAllProductByUser")
    @Test
    public void checkUserWithProduct_byListAllProductByUser() {

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

    @DisplayName("Check null input in listAllProductByUser")
    @Test
    public void checkNullInput_inListAllProductByUser() {
        assertTrue(productService.listAllProductsByUser(null).isEmpty(), "List product must be empty");
    }

    @DisplayName("Check input is not exist in listAllProductByUser")
    @Test
    public void checkInputIsNotExist_inListAllProductsByUser() {
        assertTrue(productService.listAllProductsByUser("Jane").isEmpty(), "List product must be empty");
    }

    @DisplayName("Compare id by listAllProductByUser")
    @Test
    public void compareId_fromGetProductById() {
        assertEquals(productId, (long) productService.getProductById(productId).getProductId());
    }

    @DisplayName("Check invalid id in getProductById")
    @Test
    public void invalidInput_inGetProductById() {
        assertNull(productService.getProductById(INVALID_ID), "Product must be null");
    }

    @DisplayName("Check top product of user by getTopProduct")
    @Test
    public void checkTopProductOfUser_byGetTopProduct() {

        Product topProduct = productService.getTopProduct(userName);
        assertTrue(topProduct.getAppUser().getUserName().equals(userName) && topProduct.isEnabled());
    }

    @DisplayName("Check create new product with id and telephone by saveOrUpdateProduct")
    @Test
    public void checkCreateProductWithIdAndTelephone_bySaveOrUpdateProduct() {

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

    @DisplayName("Check update product with telephone by saveOrUpdateProduct")
    @Test
    public void checkUpdateProductWithTelephone_bySaveOrUpdateProduct() {

        Product product = productService.getProductById(productId);

        String oldTelephone = product.getTelephone();

        String randomTelephone = String.valueOf( (int) ((Math.random() * ((MAX_NUMBER - MIN_NUMBER) + 1)) + MIN_NUMBER));

        product.setTelephone(randomTelephone);

        productService.saveOrUpdateProduct(product, userName);

        Product editedProduct = productService.getProductById(productId);

        product.setTelephone(oldTelephone);

        productService.saveOrUpdateProduct(product, userName);

        assertTrue(editedProduct.getTelephone().equals(randomTelephone)
                && editedProduct.getProductId() == productId);
    }

    @DisplayName("Check delete product with id by deleteProduct")
    @Test
    public void checkDeleteProductWithId_byDeleteProduct() {

        Product product = productService.getProductById(productId);

        productService.deleteProduct(productId);

        Product deletedProduct = productService.getProductById(productId);

        productId = productService.saveOrUpdateProduct(product, product.getAppUser().getUserName()).getProductId();

        assertNull(deletedProduct);
    }

    @DisplayName("Check delete product with invalid id by deleteProduct")
    @Test
    public void checkDeleteProductWithInvalidId_byDeleteProduct() {
        assertEquals("Fail", productService.deleteProduct(INVALID_ID));
    }

    @Disabled
    @Test
    public void wrongSQLSyntax_inListAllProducts() {
        assertNull(productService.listAllProducts(), "List product must be null");
    }

    @Disabled
    @Test
    public void wrongSQLSyntax_inGetProductById() {
        assertNull( productService.getProductById(1L), "List product must be null");
    }

    @AfterAll
    public static void tearDownAll() {
        System.out.println("End testing ProductService ...");
    }
}
