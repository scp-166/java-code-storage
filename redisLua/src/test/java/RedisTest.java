import com.RedisAppStarter;
import com.bean.FriendInfo;
import com.service.FriendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

/**
 * @author lyl
 * @date 2020/9/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisAppStarter.class)
public class RedisTest {

    @Autowired
    public RedisTemplate<String, Object> jacksonRedisTemplate;


    @Autowired
    private FriendService friendService;

    @Test
    public void testRedis() {
        System.out.println(jacksonRedisTemplate.opsForValue().get("test"));
    }

    @Test
    public void testLua1() {
        System.out.println(friendService.executeLua(Long.class, "lua/logTest.lua", Collections.singletonList("10086")));
    }

    @Test
    public void testSubmit() {
        System.out.println(friendService.applyForFriend(910001L, 910002L));
        System.out.println(friendService.applyForFriend(910001L, 910003L));
        System.out.println(friendService.applyForFriend(910001L, 910002L));
        System.out.println(friendService.applyForFriend(910001L, 910003L));
        System.out.println(friendService.submitApplicationDecision(910001L, 910002L, true));
        System.out.println(friendService.submitApplicationDecision(910001L, 910003L, false));
    }

    @Test
    public void testLua2() {

        System.out.println(friendService.executeLua(Long.class, "lua/applyForFriend.lua",
                Collections.singletonList("910001"),
                FriendInfo.createApplyingFriendInfo(20L)));
    }

    public static void main(String[] args) {
        System.out.println(new FriendInfo());
    }


}
