package cn.uncleyumo.talkspacestudio.controller;

import cn.dev33.satoken.util.SaResult;
import cn.uncleyumo.talkspacestudio.entity.temp.CountTokenDto;
import cn.uncleyumo.talkspacestudio.server.WebSocketServer;
import cn.uncleyumo.talkspacestudio.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * @author uncle_yumo
 * @fileName TestController
 * @createDate 2025/3/8 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@RestController
@Tag(name = "测试接口", description = "测试模块的描述")
@Slf4j
@RequestMapping("/test")
public class TestController {

    private final TestService testService;
    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/test01")
    @Operation(summary = "测试接口01", description = "测试接口01的说明")
    public SaResult test01(
            @RequestParam(value = "param1", required = true)
            String param1
    ) {
        log.warn("test01 param1: " + param1);
        return SaResult.data(param1);
    }

    @PostMapping("/countToken")
    @Operation(summary = "计算字符串将消耗的token数量的完整结果", description = "计算token数量的接口")
    public SaResult countToken(@RequestBody CountTokenDto countTokenDto) {
        testService.countToken(countTokenDto);
        return SaResult.data(testService.countToken(countTokenDto));
    }

    @PostMapping("/countTokenNumber")
    @Operation(summary = "计算字符串将消耗的token数量", description = "计算token数量的接口")
    public SaResult countTokenNumber(@RequestBody CountTokenDto countTokenDto) {
        testService.countToken(countTokenDto);
        return SaResult.data(testService.countTokenNumber(countTokenDto));
    }

    @GetMapping("/testWebsocket")
    @Operation(summary = "测试websocket接口", description = "测试websocket接口的说明")
    public SaResult testWebsocket(@Parameter(required = true) String userId, @Parameter(required = true) String message) {
        WebSocketServer.sendMessage(userId, message);
        return SaResult.ok("测试websocket接口成功" + LocalDateTime.now());
    }
}
