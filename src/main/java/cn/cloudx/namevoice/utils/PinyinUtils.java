package cn.cloudx.namevoice.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.stereotype.Component;

/**
 * 拼音工具类
 *
 * @author zhanghao
 * @date 2018/06/24
 */
@Component
public class PinyinUtils {
    private static HanyuPinyinOutputFormat format = null;

    public enum Type {
        //全部大写
        UPPERCASE,
        //全部小写
        LOWERCASE,
        //首字母大写
        FIRSTUPPER
    }

    public PinyinUtils() {
        format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    public static String toPinYin(String str) throws BadHanyuPinyinOutputFormatCombination {
        return toPinYin(str, "", Type.FIRSTUPPER);
    }

    public static String toPinYin(String str, String spera) throws BadHanyuPinyinOutputFormatCombination {
        return toPinYin(str, spera, Type.FIRSTUPPER);
    }

    /**
     * 将str转换成拼音，如果不是汉字或者没有对应的拼音，则不作转换
     * 如： 明天 转换成 MINGTIAN
     *
     * @param str：要转化的汉字
     * @param spera：转化结果的分割符
     */
    public static String toPinYin(String str, String spera, Type type) throws BadHanyuPinyinOutputFormatCombination {
        if (str == null || str.trim().length() == 0) {
            return "";
        }
        if (type == Type.UPPERCASE) {
            format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        } else {
            format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        }

        StringBuilder py = new StringBuilder();
        String temp = "";
        String[] t;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((int) c <= 128) {
                py.append(c);
            } else {
                t = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if (t == null) {
                    py.append(c);
                } else {
                    temp = t[0];
                    if (type == Type.FIRSTUPPER) {
                        temp = t[0].toUpperCase().charAt(0) + temp.substring(1);
                    }
                    py.append(temp).append(i == str.length() - 1 ? "" : spera);
                }
            }
        }
        return py.toString().trim();
    }

}
