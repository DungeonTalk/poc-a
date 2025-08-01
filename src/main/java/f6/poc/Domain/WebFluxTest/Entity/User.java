package f6.poc.Domain.WebFluxTest.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String name;
    private int age;

    // 생성자, getter/setter 생략
}
