package guru.springframework.security.dao;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, String> {
}
