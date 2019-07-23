package guru.springframework.commands;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserCommand {

    private Long id;
    private String login;
    private String password;
    private Set<RoleCommand> roles = new HashSet<>();

    public UserCommand() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<RoleCommand> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleCommand> roles) {
        this.roles = roles;
    }

    public UserCommand addRole(RoleCommand roleCommand) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.add(roleCommand);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCommand)) return false;
        UserCommand that = (UserCommand) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getLogin(), that.getLogin()) &&
                Objects.equals(getPassword(), that.getPassword()) &&
                Objects.equals(getRoles(), that.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin(), getPassword(), getRoles());
    }
}
