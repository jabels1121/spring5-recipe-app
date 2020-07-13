package guru.springframework.security.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Role implements GrantedAuthority {

    private String id;

    private String role;

    private String description;

    public Role(String role, String description) {
        this.role = role;
        this.description = description;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }
}
