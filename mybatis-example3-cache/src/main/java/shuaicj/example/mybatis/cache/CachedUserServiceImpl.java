package shuaicj.example.mybatis.cache;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The implementation for CachedUserService.
 *
 * @author shuaicj 2019/08/15
 */
@Service
public class CachedUserServiceImpl implements CachedUserService {

    private static final Logger logger = LoggerFactory.getLogger(CachedUserServiceImpl.class);

    @Override
    public User find(String username) {
        logger.info("----------- create user");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new User(1L, username, "china");
    }

    @Override
    public void clearCache(String username) {
    }

    @Override
    public User update(String username, String address) {
        return new User(3L, username, address);
    }
}
