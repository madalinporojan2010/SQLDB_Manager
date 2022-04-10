package application.model;

/**
 * A BillData class used to print the table data to a bill.
 */
public class BillData {
    private int idOrder;
    private int idClient;
    private String clientName;
    private int age;
    private String phone;
    private String address;
    private String email;
    private int idProduct;
    private String productName;
    private int price;
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
     * Client name getter.
     *
     * @return The client name.
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Client name setter.
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Client age getter.
     *
     * @return The client age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Client age setter.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Client phone getter.
     *
     * @return The client phone.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Client phone setter.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Client address getter.
     *
     * @return The client address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Client address setter.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Client email getter.
     *
     * @return The client email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Client email setter.
     */
    public void setEmail(String email) {
        this.email = email;
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
     * Product name getter.
     *
     * @return The product name.
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Product name setter.
     */
    public void setProductName(String productName) {
        this.productName = productName;
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

    @Override
    public String toString() {
        return "Name: " + clientName + " | " +
                "Age: " + age + " | " +
                "Phone: " + phone + " | " +
                "Address: " + address + " | " +
                "Email: " + email;
    }
}
