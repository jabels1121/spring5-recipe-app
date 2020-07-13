package guru.springframework.repositories.reactive;

import guru.springframework.security.entities.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserReactiveRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByLogin(final String login);

}
