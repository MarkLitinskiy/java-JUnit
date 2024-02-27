package edu.school21.repositories;

import edu.school21.models.Product;
import edu.school21.numbers.IllegalNumberException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

public class ProductsRepositoryJdbcImplTest {
    private EmbeddedDatabase db;
    ProductsRepositoryJdbcImpl productsRepositoryJdbc;
    List<Product> EXPECTED_FIND_ALL_PRODUCTS = fillList();
    final Optional<Product> EXPECTED_FIND_BY_ID_PRODUCT = Optional.of(new Product(1, "Macbook", 1000));
    final Product EXPECTED_UPDATED_PRODUCT = new Product(5, "Dyson", 650);
    final Product EXPECTED_SAVED_PRODUCT = new Product(6, "Dyson", 650);

    private List<Product> fillList(){
        List<Product> EXPECTED_FIND_ALL_PRODUCTS = new ArrayList<>();
        EXPECTED_FIND_ALL_PRODUCTS.add(new Product(1, "Macbook", 1000));
        EXPECTED_FIND_ALL_PRODUCTS.add(new Product(2, "Pixel 8", 600));
        EXPECTED_FIND_ALL_PRODUCTS.add(new Product(3, "Mi Band", 30));
        EXPECTED_FIND_ALL_PRODUCTS.add(new Product(4, "Nikon F4", 900));
        EXPECTED_FIND_ALL_PRODUCTS.add(new Product(5, "Canon Printer", 100));
        return EXPECTED_FIND_ALL_PRODUCTS;
    }
    @BeforeEach
    public void init(){
        db = new EmbeddedDatabaseBuilder()
                .setName("test_database")
                .setType(HSQL)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("schema.sql")
                .addScripts("data.sql")
                .build();
    }

    @Test
    public void testDataAccess() throws SQLException {
        assertNotNull(db.getConnection());
    }

    @Test
    public void testFindAllProducts() throws SQLException {
        productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(db);
        assertEquals(productsRepositoryJdbc.findAll(), EXPECTED_FIND_ALL_PRODUCTS);
    }
    @Test
    public void testFindByIDProducts() throws SQLException {
        productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(db);
        assertEquals(productsRepositoryJdbc.findById(1L), EXPECTED_FIND_BY_ID_PRODUCT);
    }

    @Test
    public void testUpdate() throws SQLException {
        productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(db);
        productsRepositoryJdbc.update(EXPECTED_UPDATED_PRODUCT);
        assertEquals(productsRepositoryJdbc.findById(5L), Optional.of(EXPECTED_UPDATED_PRODUCT));
    }

    @Test
    public void testSave() throws SQLException {
        productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(db);
        productsRepositoryJdbc.save(EXPECTED_SAVED_PRODUCT);
        assertEquals(productsRepositoryJdbc.findById(6L), Optional.of(EXPECTED_SAVED_PRODUCT));
    }

    @Test
    public void testDelete() throws SQLException {
        productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(db);
        productsRepositoryJdbc.delete(6L);
        assertEquals(productsRepositoryJdbc.findById(6L), Optional.empty());
    }

    @AfterEach
    public void tearDown() {
        db.shutdown();
    }
}
