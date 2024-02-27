package edu.school21.models;

import java.util.Objects;

public class Product {
    private int id;
    private String product_name;
    private int price;

    public Product(int id, String product_name, int price) {
        this.id = id;
        this.product_name = product_name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && price == product.price && Objects.equals(product_name, product.product_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product_name, price);
    }
}
