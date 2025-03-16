package cn.uncleyumo.talkspacestudio.constant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author uncle_yumo
 * @fileName AvatarFileUrlConstantTest
 * @createDate 2025/3/13 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

class AvatarFileUrlConstantTest {
    @Test
    void getAvatarUrl() {
        for (int i = 0; i < 5; i++) {
            String url = AvatarFileUrlConstant.getOneMaleAvatarUrl();
            System.out.println(url);
        }
        System.out.println("--------");
        for (int i = 0; i < 5; i++) {
            String url = AvatarFileUrlConstant.getOneFemaleAvatarUrl();
            System.out.println(url);
        }
        System.out.println("--------");
        String url = AvatarFileUrlConstant.getOneAvatarUrl(1);
        System.out.println(url);
    }

}