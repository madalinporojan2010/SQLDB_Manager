package application.model;

/**
 * The Product model for the Product db table.
 */
public class Product {
    private int idProduct;
    private String name;
    private int price;
    private int stock;

    /**
     * Product id getter.
     *
     * @return The product id.
     */
    public int getIdProduct() {
        return idProduct;
    }

    /**
     * Product id setter.
     */
    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    /**
     * Product name getter.
     *
     * @return The product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Product name setter.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Product price getter.
     *
     * @return The product price.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Product price setter.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Product stock getter.
     *
     * @return The product stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Product stock setter.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
}
