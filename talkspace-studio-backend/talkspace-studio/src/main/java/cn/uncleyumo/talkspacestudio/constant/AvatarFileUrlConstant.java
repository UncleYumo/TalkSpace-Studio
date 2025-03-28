package cn.uncleyumo.talkspacestudio.constant;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author uncle_yumo
 * @fileName AvatarFileUrlConstant
 * @createDate 2025/3/13 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

public class AvatarFileUrlConstant {
    public static final List<String> MALE_AVATAR_URLS = Arrays.asList(
            "http://uncleyumo.cn:9000/talkspace-studio/default_avatar/male_avatar_01.png",
            "http://uncleyumo.cn:9000/talkspace-studio/default_avatar/male_avatar_02.png"
    );
    public static final List<String> FEMALE_AVATAR_URLS = Arrays.asList(
            "http://uncleyumo.cn:9000/talkspace-studio/default_avatar/female_avatar_01.png",
            "http://uncleyumo.cn:9000/talkspace-studio/default_avatar/female_avatar_02.png"
    );

    public static String getOneMaleAvatarUrl() {
        int randomIndex = ThreadLocalRandom.current().nextInt(MALE_AVATAR_URLS.size());
        return MALE_AVATAR_URLS.get(randomIndex);
    }

    public static String getOneFemaleAvatarUrl() {
        int randomIndex = ThreadLocalRandom.current().nextInt(FEMALE_AVATAR_URLS.size());
        return FEMALE_AVATAR_URLS.get(randomIndex);
    }

    public static String getOneAvatarUrl(int gender) {
        if (gender != 0 && gender != 1) {
            throw new IllegalArgumentException(FieldValidateMessageConstant.GENDER_PATTERN_FAIL);
        }
        if (gender == 1) {
            return getOneFemaleAvatarUrl();
        } else {
            return getOneMaleAvatarUrl();
        }
    }
}
