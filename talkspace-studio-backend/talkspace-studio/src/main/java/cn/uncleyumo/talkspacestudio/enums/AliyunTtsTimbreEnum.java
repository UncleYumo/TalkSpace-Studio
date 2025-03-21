package cn.uncleyumo.talkspacestudio.enums;

import cn.uncleyumo.talkspacestudio.entity.pojo.TtsTimbre;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author uncle_yumo
 * @fileName AliyunTtsTimbreEnum.java
 * @createDate 2025/3/9 March
 * @school 无锡学院
 * @studentID 22344131
 * @description Aliyun TTS Timbre Enum
 */

public enum AliyunTtsTimbreEnum {
    LONG_WAN("longwan", "龙婉 - 语音助手、导航播报、聊天数字人 - 中文普通话", "中文为主"),
//    LONG_WAN_V2("longwan_v2", "龙婉V2 - 语音助手、导航播报、聊天数字人 - 中文普通话", "中文为主"),
    LONG_CHENG("longcheng", "龙橙 - 语音助手、导航播报、聊天数字人 - 中文普通话", "中文为主"),
//    LONG_CHENG_V2("longcheng_v2", "龙橙V2 - 语音助手、导航播报、聊天数字人 - 中文普通话", "中文为主"),
    LONG_HUA("longhua", "龙华 - 语音助手、导航播报、聊天数字人 - 中文普通话", "中文为主"),
//    LONG_HUA_V2("longhua_v2", "龙华V2 - 语音助手、导航播报、聊天数字人 - 中文普通话", "中文为主"),
    LONG_XIAO_CHUN("longxiaochun", "龙小淳 - 语音助手、导航播报、聊天数字人 - 中文+英文", "中英混杂"),
//    LONG_XIAO_CHUN_V2("longxiaochun_v2", "龙小淳V2 - 语音助手、导航播报、聊天数字人 - 中文+英文", "中英混杂"),
    LONG_XIAO_XIA("longxiaoxia", "龙小夏 - 语音助手、聊天数字人 - 中文普通话", "中文为主"),
//    LONG_XIAO_XIA_V2("longxiaoxia_v2", "龙小夏V2 - 语音助手、聊天数字人 - 中文普通话", "中文为主"),
    LONG_XIAO_CHENG("longxiaocheng", "龙小诚 - 语音助手、导航播报、聊天数字人 - 中文+英文", "中英混杂"),
    LONG_XIAO_BAI("longxiaobai", "龙小白 - 聊天数字人、有声书、语音助手 - 中文普通话", "中文为主"),
    LONG_LAO_TIE("longlaotie", "龙老铁 - 新闻播报、有声书、语音助手、直播带货、导航播报 - 中文东北口音", "中文为主"),
    LONG_SHU("longshu", "龙书 - 有声书、语音助手、导航播报、新闻播报、智能客服 - 中文普通话", "中文为主"),
//    LONG_SHU_V2("longshu_v2", "龙书V2 - 有声书、语音助手、导航播报、新闻播报、智能客服 - 中文普通话", "中文为主"),
    LONG_SHUO("longshuo", "龙硕 - 语音助手、导航播报、新闻播报、客服催收 - 中文普通话", "中文为主"),
    LONG_JING("longjing", "龙婧 - 语音助手、导航播报、新闻播报、客服催收 - 中文普通话", "中文为主"),
    LONG_MIAO("longmiao", "龙妙 - 客服催收、导航播报、有声书、语音助手 - 中文普通话", "中文为主"),
    LONG_YUE("longyue", "龙悦 - 语音助手、诗词朗诵、有声书朗读、导航播报、新闻播报、客服催收 - 中文普通话", "中文为主"),
    LONG_YUAN("longyuan", "龙媛 - 有声书、语音助手、聊天数字人 - 中文普通话", "中文为主"),
    LONG_FEI("longfei", "龙飞 - 会议播报、新闻播报、有声书 - 中文普通话", "中文为主"),
    LONG_JIELIDOU("longjielidou", "龙杰力豆 - 新闻播报、有声书、聊天助手 - 中文+英文", "中英混杂"),
    LONG_TONG("longtong", "龙彤 - 有声书、导航播报、聊天数字人 - 中文普通话", "中文为主"),
    LONG_XIANG("longxiang", "龙祥 - 新闻播报、有声书、导航播报 - 中文普通话", "中文为主"),
    STELLA("loongstella", "Stella - 语音助手、直播带货、导航播报、客服催收、有声书 - 中文+英文", "中英混杂"),
    BELLA("loongbella", "Bella - 语音助手、客服催收、新闻播报、导航播报 - 中文普通话", "中文为主");
//    BELLA_V2("loongbella_v2", "BellaV2 - 语音助手、客服催收、新闻播报、导航播报 - 中文普通话", "中文为主");

    @Getter
    private final String timbreName;
    @Getter
    private final String description;
    @Getter
    private final String language;

    AliyunTtsTimbreEnum(String timbreName, String description, String language) {
        this.timbreName = timbreName;
        this.description = description;
        this.language = language;
    }

    public static List<TtsTimbre> getTimbreList() {
        ArrayList<TtsTimbre> list = new ArrayList<>();
        for (AliyunTtsTimbreEnum timbre : AliyunTtsTimbreEnum.values()) {
            list.add(new TtsTimbre(timbre.timbreName, timbre.description, timbre.language));
        }
        return list;
    }

    /**
     * 获取指定名称的模型
     */
    public static TtsTimbre getTimbreByName(String timbreName) {
        for (AliyunTtsTimbreEnum timbre : AliyunTtsTimbreEnum.values()) {
            if (timbre.timbreName.equals(timbreName)) {
                return new TtsTimbre(timbre.timbreName, timbre.description, timbre.language);
            }
        }
        throw new IllegalArgumentException("Invalid timbre name: " + timbreName);
    }
}
