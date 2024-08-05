package softeer.demo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RedisTicketService {

    private final RedisTemplate<String , Integer> redisTemplate;

    public void saveValue(String key,Integer value){
        redisTemplate.opsForValue().set(key, value);
    }

    public void decreaseValue(String key){
        Integer value = redisTemplate.opsForValue().get(key);
        value--;
        redisTemplate.opsForValue().set(key, value);
    }

    public void deleteAllKeysCurrentDatabase(){
        redisTemplate.execute((RedisCallback<Integer>) connection -> {
            connection.serverCommands().flushDb();
            return null;
        });
    }
}
