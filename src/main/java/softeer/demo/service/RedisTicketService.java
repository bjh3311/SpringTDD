package softeer.demo.service;


import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class RedisTicketService {

    private final RedisTemplate<String , Integer> redisTemplate;
    private final RedissonClient redissonClient;

    public void saveValue(String key,Integer value){
        redisTemplate.opsForValue().set(key, value);
    }

    public int findByKey(String key){
        Integer value =  redisTemplate.opsForValue().get(key);
        if(value == null){
            return 0;
        }
        return value;
    }

    public void decreaseValue(String key){
        Integer value = redisTemplate.opsForValue().get(key);
        System.out.println("잔여 티켓 : " + --value);
        redisTemplate.opsForValue().set(key, value);
    }

    public void decreaseValueWithLock(String key){

        // RLock 인스턴스를 생성합니다. key 값에 "_lock"을 붙여서 고유한 락 이름을 생성합니다.
        RLock lock = redissonClient.getLock(key + "_lock");

        try{
            // tryLock 메서드를 사용하여 락을 시도합니다. 10초 동안 락을 기다리고, 락을 획득한 후에는 5초 동안 락을 유지합니다.
            if(lock.tryLock(10,5, TimeUnit.SECONDS)){
                try{
                    Integer value = redisTemplate.opsForValue().get(key);
                    if(value != null & value > 0){
                        value--;
                        redisTemplate.opsForValue().set(key,value);
                    }
                } finally{
                    // try 블록 내에서 작업이 끝나면 항상 락을 해제합니다.
                    lock.unlock();
                }
            } else{
                // 락을 획득하지 못했을 경우 메시지를 출력합니다.
                System.out.println("Could not acquire lock for key : " + key);
            }
        } catch (InterruptedException e){
            // 인터럽트 예외가 발생한 경우 현재 스레드의 인터럽트 상태를 설정합니다.
            // tryLock 메서드 호출 중 인터럽트가 발생할 수 있으므로 이를 처리하기 위한 catch 블록
            Thread.currentThread().interrupt();
        }
    }

    public void deleteAllKeysCurrentDatabase(){
        redisTemplate.execute((RedisCallback<Integer>) connection -> {
            connection.serverCommands().flushDb();
            return null;
        });
    }
}
