package guru.springframework.commands;

import java.util.Objects;

public class RoleCommand {

    private String authority;

    public RoleCommand() {
    }


    public RoleCommand(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleCommand)) return false;
        RoleCommand that = (RoleCommand) o;
        return Objects.equals(getAuthority(), that.getAuthority());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthority());
    }
}
