package guru.springframework.security.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class User {

    private String id;

    @NotNull
    private String login;

    @NotNull
    private String password;

    private Set<Role> roles = new HashSet<>();

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User addRole(Role role) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.add(role);
        return this;
    }
}
