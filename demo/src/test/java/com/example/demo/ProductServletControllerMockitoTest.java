package com.example.demo;

import com.example.demo.controllers.servlet.product.ViewUserProductController;
import com.example.demo.entity.Product;
import com.example.demo.services.ProductService;
import com.example.demo.services.ProductServiceJdbcImpl;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServletControllerMockitoTest extends Mockito {

    private ProductService productService = new ProductServiceJdbcImpl();
    private final long TOTAL_USER_HAS_PRODUCT = 1L;
    private final long INVALID_ID = -1L;
    private final int MAX_NUMBER = 999999999;
    private final int MIN_NUMBER = 100000000;
    private long productId;
    private String userName;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @BeforeAll
    public static void initAll() {
        System.out.println("Start testing ProductServletController ...");
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

        MockitoAnnotations.initMocks(this);
    }

    @Disabled
    @Test
    public void testFullName() throws IOException {

        when(request.getParameter("fn")).thenReturn("Vinod");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        ViewUserProductController myServlet = new ViewUserProductController();
        myServlet.init();
//        myServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        assertEquals(result, "Full Name: Vinod Kashyap");

    }

    @AfterAll
    public static void tearDownAll() {
        System.out.println("End testing ProductServletController ...");
    }

}
