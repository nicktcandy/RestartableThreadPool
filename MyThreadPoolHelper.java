

import com.cj.core.util.RestartableThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chenjie 2020-09-29
 */
public class MyThreadPoolHelper {
    private static Logger logger = LoggerFactory.getLogger(MyThreadPoolHelper .class);

    private static MyThreadPoolHelper  instance = new MyThreadPoolHelper ();
    public static MyThreadPoolHelper getInstance(){
        return instance;
    }

    public static final long PRE_READ_MS = 5000;    // pre read

    private ThreadPoolExecutor myThreadPool = new RestartableThreadPoolExecutor(
                5,
                10,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(10),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, myThreadPool + r.hashCode());
                    }
                });

    private volatile boolean threadStop = false;

    public void start(){

        myThreadPool .execute(new Runnable() {
            @Override
            public void run() {
                
                while (!threadStop){
                    
                }

                logger.info("myThreadPool  stop");
            }
        });
    }

    public void toStop(){

        // 1、stop
        threadStop = true;
        try {
            TimeUnit.SECONDS.sleep(1);  // wait
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
       myThreadPool.shutdown();
        logger.info("myThreadPool  stop");
    }

}
