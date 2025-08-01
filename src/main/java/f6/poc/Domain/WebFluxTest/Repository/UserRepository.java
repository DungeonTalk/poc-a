package f6.poc.Domain.WebFluxTest.Repository;

import f6.poc.Domain.WebFluxTest.Entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    /**
     * 리엑티브 쿼리
     *
     * @param name  이름
     * @return 이름 반환
     */
    Flux<User> findByName(String name);
}
