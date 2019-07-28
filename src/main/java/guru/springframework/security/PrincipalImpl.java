package guru.springframework.security;

import guru.springframework.security.dao.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PrincipalImpl implements UserDetails {

    private String pass;
    private String login;
    private Set<Role> roles;

    public PrincipalImpl() {
    }

    public PrincipalImpl(String pass, String login, Set<Role> roles) {
        this.pass = pass;
        this.login = login;
        this.roles = roles;
    }

    public String getPass() {
        return pass;
    }

    public String getLogin() {
        return login;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.pass;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrincipalImpl)) return false;
        PrincipalImpl principal = (PrincipalImpl) o;
        return Objects.equals(getPass(), principal.getPass()) &&
                Objects.equals(getLogin(), principal.getLogin()) &&
                Objects.equals(getRoles(), principal.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPass(), getLogin(), getRoles());
    }

    @Override
    public String toString() {
        return "PrincipalImpl{" +
                "pass='" + pass + '\'' +
                ", login='" + login + '\'' +
                ", roles=" + roles +
                '}';
    }

    public static class PrincipalImplBuilder {

        private String pass;
        private String login;
        private Set<Role> roles;

        public PrincipalImplBuilder() {
        }

        public PrincipalImplBuilder withLogin(String login) {
            this.login = login;
            return this;
        }

        public PrincipalImplBuilder withPass(String pass) {
            this.pass = pass;
            return this;
        }

        public <T extends Collection<? extends GrantedAuthority>> PrincipalImplBuilder withRoles(T roles) {
            if (this.roles == null) {
                this.roles = new HashSet<>();
            }
            roles.forEach(e -> {
                        Role role = new Role();
                        role.setRole(((GrantedAuthority) e).getAuthority());
                        this.roles.add(role);
                    });
            return this;
        }

        public PrincipalImpl build() {
            PrincipalImpl principal = new PrincipalImpl();
            principal.setLogin(this.login);
            principal.setPass(this.pass);
            principal.setRoles(this.roles);
            return principal;
        }

    }
}
