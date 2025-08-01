package f6.poc.Domain.RouteTest.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
public class RouteTestController {

    /**
     * 로컬 도메인을 연결한 https 라우팅 테스트 컨트롤러
     *
     * WebFlux + Nginx + 유저가 임시로 지정한 가짜 Domain(local.dev)를 로컬에서 https 연결 테스트
     * 
     * [테스트 방법]
     * 브라우저에 https://local.dev/route를 입력하면 됩니다.
     *
     * @return 반환 스트링
     */
    @GetMapping("/route")
    public String hello() {

        LocalDateTime now = LocalDateTime.now();
        String returnString = "도메인을 연결한 로컬 https 세팅 테스트 성공했습니다. 테스트 일시 : " + now;
        log.info(returnString);

        return returnString;
    }
}
