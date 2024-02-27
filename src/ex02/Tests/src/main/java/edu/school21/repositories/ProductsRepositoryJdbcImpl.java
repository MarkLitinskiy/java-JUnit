package edu.school21.repositories;

import edu.school21.models.Product;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository{

    public ProductsRepositoryJdbcImpl(EmbeddedDatabase connection) throws SQLException {
        this.connection = connection.getConnection();
    }

    private Connection connection;
    String findAllQuery = "SELECT * FROM test_database.product_tbl";
    String findByIDQuery = "SELECT * FROM test_database.product_tbl WHERE test_database.product_tbl.id = ";
    String updateQuery = "UPDATE test_database.product_tbl SET product_name = ?, price = ? WHERE id = ?";
    String saveQuery = "INSERT INTO test_database.product_tbl VALUES (DEFAULT, ?, ?)";
    String deleteQuery = "DELETE FROM test_database.product_tbl WHERE id = ?";
    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> allProducts = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(findAllQuery);

            while (resultSet.next()) {
                allProducts.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("product_name"),
                        resultSet.getInt("price")));
            }
            return allProducts;
        }
    }

    @Override
    public Optional<Product> findById(Long id){
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(findByIDQuery + id);
            resultSet.next();
            return Optional.of(new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("product_name"),
                    resultSet.getInt("price")));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void update(Product product) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)){
            preparedStatement.setString(1, product.getProduct_name());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setInt(3, product.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void save(Product product) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(saveQuery)){
            preparedStatement.setString(1, product.getProduct_name());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)){
            preparedStatement.setInt(1, id.intValue());
            preparedStatement.executeUpdate();
        }
    }
}
