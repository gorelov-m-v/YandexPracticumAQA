public class CourierWithoutLogin {
    private String password;
    private String firstName;

    public CourierWithoutLogin(String password, String firstName) {
        this.password = password;
        this.firstName = firstName;
    }

    public CourierWithoutLogin() {
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
