public class CourierWithoutPassword {
    private String login;
    private String firstName;

    public CourierWithoutPassword(String login, String firstName) {
        this.login = login;
        this.firstName = firstName;
    }

    public CourierWithoutPassword() {
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
