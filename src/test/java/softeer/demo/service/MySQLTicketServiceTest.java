package softeer.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;
import softeer.demo.entity.Ticket;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //실제 데이터 베이스를 쓴다
class MySQLTicketServiceTest {


    @Autowired
    private MySQLTicketService mySQLTicketService;


    private Ticket ticket;

    @AfterEach
    void clear(){
        mySQLTicketService.deleteAll();
    }

    @Test
    @DisplayName(value = "mysql에 멀티스레드 환경에서 락 없이 동시에 티켓 수를 감소시키는 테스트입니다")
    void getTicket() {
        int participant = 100;
        //given
        ticket = new Ticket();
        ticket.setTicket(participant);
        mySQLTicketService.save(ticket);

        ExecutorService executorService = Executors.newFixedThreadPool(2);//
        CountDownLatch latch = new CountDownLatch(participant);//100만큼 latch를 설정

        //when
        for(int i = 0 ; i < participant ; i++){

            executorService.submit(()->{
                try{
                    mySQLTicketService.decreaseTicket(ticket.getId());
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
        assertThat(mySQLTicketService.findById(ticket.getId()).getTicket()).isEqualTo(0);
        executorService.shutdown();
    }

    @Test
    @DisplayName(value = "멀티스레드 환경에서 MySQL로 락을 걸고 동시성 제어를 하여 티켓 수를 감소시키는 테스트입니다")
    void getLockTicket(){

        //given
        int participant = 100000;
        ticket = new Ticket();
        ticket.setTicket(participant);
        mySQLTicketService.save(ticket);

        ExecutorService executorService = Executors.newFixedThreadPool(2);//100명
        CountDownLatch latch = new CountDownLatch(participant);//100만큼 latch를 설정

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        //when
        for(int i = 0 ; i < participant ; i++){
            executorService.submit(()->{
                try{
                    mySQLTicketService.decreaseTicketWithLock(ticket.getId());
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
        assertThat(mySQLTicketService.findById(ticket.getId()).getTicket()).isEqualTo(0);

        System.out.println(stopWatch.prettyPrint());
        executorService.shutdown();

    }
}