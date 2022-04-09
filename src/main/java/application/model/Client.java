package application.model;

public class Client {
    private int idClient;
    private String name;
    private int age;
    private String phone;
    private String address;
    private String email;


    public int getIdClient() {
        return idClient;
    }

    public String getName() {
        return name;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }
}
