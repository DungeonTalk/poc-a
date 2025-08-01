package f6.poc.Domain.WebFluxTest.Controller;

import f6.poc.Domain.WebFluxTest.Entity.User;
import f6.poc.Domain.WebFluxTest.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class WebFluxTestController {

    private final UserRepository userRepository;

    /**
     * 인물 Get
     *
     * [
     *   {
     *     "id": "xxxx",
     *     "name": "홍길동",
     *     "age": 30
     *   }
     * ]
     *
     * @return 인물 리스트
     */
    @GetMapping
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 인물 Post
     *
     * {
     *   "name": "Alice",
     *   "age": 25
     * }
     *
     * @param user 유저 고유 번호
     * @return 유저 생성 결과
     */
    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    /**
     * 수정
     *
     * {
     *   "name": "Alice Updated",
     *   "age": 27
     * }
     *
     * @param id 유저의 고유 번호
     * @param updatedUser 수정된 유저의 정보
     * @return 수정된 결과
     */
    @PutMapping("/{id}")
    public Mono<User> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.setName(updatedUser.getName());
                    existingUser.setAge(updatedUser.getAge());
                    return userRepository.save(existingUser);
                });
    }

    /**
     * 인물 Delete
     *
     * @param id 인물 고유 번호
     * @return 없음
     */
    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable String id) {
        return userRepository.deleteById(id);
    }

}
