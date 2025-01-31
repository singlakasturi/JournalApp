package com.firstP.jour;

import com.firstP.jour.repository.userRepository;
import com.firstP.jour.service.userService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class userServiceTest {

    @Autowired
    private userRepository userRepository;


    @Disabled
    @Test
    public void testFindByUserName() {
        assertNotNull(userRepository.findByUserName("KasturiSingla"));
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1, 1, 2",
            "2, 3, 5",
            "3, 3, 9"
    })
    public void test(int a, int b, int expected) {
        assertEquals(expected, a, b);
    }
}
