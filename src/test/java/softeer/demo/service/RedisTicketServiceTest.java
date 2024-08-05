package softeer.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //실제 데이터 베이스를 쓴다
class RedisTicketServiceTest {

    @Autowired
    private RedisTicketService redisTicketService;

    private String key = "ticket";


    @AfterEach
    void clear(){
        redisTicketService.deleteAllKeysCurrentDatabase();
    }

    @Test
    @DisplayName(value = "Redis에서 락 없이 티켓을 획득하는 테스트입니다")
    void getTicket(){

        //given
        int participant = 100;
        redisTicketService.saveValue(key, 100);

        //when
        ExecutorService executorService = Executors.newFixedThreadPool(2);//100명
        CountDownLatch latch = new CountDownLatch(participant);//100만큼 latch를 설정

        //when
        for(int i = 0 ; i < participant ; i++){
            executorService.submit(()->{
                try{
                    redisTicketService.decreaseValue(key);
                } catch (RuntimeException ex){
                    log.debug(ex.getMessage());
                } finally{
                    latch.countDown();
                }
            });
        }

        try{
            latch.await();//설정된 latch가 0이 될때 까지 다음코드로 안넘어간다
        } catch (InterruptedException ex){
            log.debug(ex.getMessage());
        }
        //then
        assertThat(redisTicketService.findByKey(key)).isEqualTo(0);
        executorService.shutdown();

    }

    @Test
    @DisplayName(value = "분산 락을 걸고 동시성 테스트입니다")
    void getTicketWithLock(){


        //given
        int participant = 100;
        redisTicketService.saveValue(key, participant);

        //when
        ExecutorService executorService = Executors.newFixedThreadPool(2);//100명
        CountDownLatch latch = new CountDownLatch(participant);//100만큼 latch를 설정

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        //when
        for(int i = 0 ; i < participant ; i++){
            executorService.submit(()->{
                try{
                    redisTicketService.decreaseValueWithLock(key);
                } catch (RuntimeException ex){
                    log.debug(ex.getMessage());
                } finally{
                    latch.countDown();
                }
            });
        }

        try{
            latch.await();//설정된 latch가 0이 될때 까지 다음코드로 안넘어간다
        } catch (InterruptedException ex){
            log.debug(ex.getMessage());
        }

        stopWatch.stop();
        //then
        assertThat(redisTicketService.findByKey(key)).isEqualTo(0);
        System.out.println(stopWatch.prettyPrint());
        executorService.shutdown();

    }

}