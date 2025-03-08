package cn.uncleyumo.talkspacestudio;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class TalkspaceStudioApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testTtsGenerate() {
        log.warn("test tts generate start");
        System.out.println("test tts generate start");
        log.warn("test tts generate end");
    }

}
