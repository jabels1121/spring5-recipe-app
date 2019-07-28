package guru.springframework.commands;

import javax.validation.constraints.NotNull;

public class UserRegistrationDto {

    @NotNull
    private String login;

    @NotNull
    private String confirmPassword;

    @NotNull
    private String password;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(@NotNull String login, @NotNull String confirmPassword, @NotNull String password) {
        this.login = login;
        this.confirmPassword = confirmPassword;
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
