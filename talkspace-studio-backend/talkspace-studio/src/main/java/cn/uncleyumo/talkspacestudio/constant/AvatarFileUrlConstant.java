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
            "http://localhost:9000/talkspace-studio/8fe43117-8983-430e-8c68-d6d38b8e7bf0",
            "http://localhost:9000/talkspace-studio/f5e5f8a9-1ef1-4c3e-a668-fefdc3aede14"
    );
    public static final List<String> FEMALE_AVATAR_URLS = Arrays.asList(
            "http://localhost:9000/talkspace-studio/80d678aa-8f4d-47f3-adef-67bba38ef016",
            "http://localhost:9000/talkspace-studio/b50bfe78-592d-43f1-9f3d-ce87c8636534"
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
        if (gender == 0) {
            return getOneFemaleAvatarUrl();
        } else {
            return getOneMaleAvatarUrl();
        }
    }
}
