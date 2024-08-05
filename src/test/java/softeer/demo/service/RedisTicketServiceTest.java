package softeer.demo.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;

class RedisTicketServiceTest {

    @Autowired
    private RedisTicketService redisTicketService;

    @Autowired
    private RedisTemplate<String,Integer> redisTemplate;


    @AfterEach
    void clear(){
        redisTicketService.deleteAllKeysCurrentDatabase();
    }

    @Test
    @DisplayName(value = "Redis에서 락 없이 티켓을 획득하는 테스트입니다")
    void getTicket(){

    }

    @Test
    @DisplayName(value = "분산 락을 걸고 동시성 테스트입니다")
    void getTicketWithLock(){

    }

}