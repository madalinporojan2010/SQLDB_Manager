package application.model;

/**
 * The Order model for the Order db table.
 */
public class Order {
    private int idOrder;
    private int idClient;
    private int idProduct;
    private int amount;

    /**
     * Order id getter.
     *
     * @return The order id.
     */
    public int getIdOrder() {
        return idOrder;
    }

    /**
     * Order id setter.
     */
    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    /**
     * Client id getter.
     *
     * @return The client id.
     */
    public int getIdClient() {
        return idClient;
    }

    /**
     * Client id setter.
     */
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

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
     * Order amount getter.
     *
     * @return The order amount.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Order amount setter.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
