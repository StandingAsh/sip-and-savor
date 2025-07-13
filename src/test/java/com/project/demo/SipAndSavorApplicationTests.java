package com.project.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest
class SipAndSavorApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("Hello World");
    }

}
