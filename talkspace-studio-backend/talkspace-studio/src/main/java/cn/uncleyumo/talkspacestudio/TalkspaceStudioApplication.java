package cn.uncleyumo.talkspacestudio;

import cn.uncleyumo.talkspacestudio.utils.YumoColorPrintUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author uncle_yumo
 */
@SpringBootApplication
@MapperScan("cn.uncleyumo.talkspacestudio.mapper")
public class TalkspaceStudioApplication {

    public static void main(String[] args) {
        SpringApplication.run(TalkspaceStudioApplication.class, args);
        YumoColorPrintUtil yumoColorPrintUtil = new YumoColorPrintUtil();
        yumoColorPrintUtil.programStart();
        yumoColorPrintUtil.printlnRed("Server started: http://localhost:8081");
        yumoColorPrintUtil.printlnRed("Swagger API: http://localhost:8081/doc.html");
    }

}
