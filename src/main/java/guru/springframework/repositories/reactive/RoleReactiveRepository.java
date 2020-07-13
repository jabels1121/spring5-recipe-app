package guru.springframework.repositories.reactive;

import guru.springframework.security.entities.Role;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RoleReactiveRepository extends ReactiveMongoRepository<Role, String> {
}
