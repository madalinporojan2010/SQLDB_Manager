package application.model;

/**
 * The Client model for the Client db table.
 */
public class Client {
    private int idClient;
    private String name;
    private int age;
    private String phone;
    private String address;
    private String email;

    /**
     * Client id getter.
     *
     * @return The client id.
     */
    public int getIdClient() {
        return idClient;
    }

    /**
     * Client name getter.
     *
     * @return The client name.
     */
    public String getName() {
        return name;
    }

    /**
     * Client id setter.
     */
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    /**
     * Client name setter.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Client age setter.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Client phone setter.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Client address setter.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Client email setter.
     */
    public void setEmail(String email) {
        this.email = email;
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
     * Client phone getter.
     *
     * @return The client phone.
     */
    public String getPhone() {
        return phone;
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
     * Client email getter.
     *
     * @return The client email.
     */
    public String getEmail() {
        return email;
    }
}
