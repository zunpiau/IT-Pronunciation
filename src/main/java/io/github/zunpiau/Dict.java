package io.github.zunpiau;

import java.text.MessageFormat;

public class Dict {

    private static final String BASE_VOICE_URL = "http://dict.youdao.com/dictvoice?audio=";

    private String word;
    private String phone;
    private String speech;

    public Dict(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = "/" + phone + "/";
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    private String generateLink(String phonation, String url) {
        if (phonation == null) {
            return "";
        } else if (url == null) {
            return phonation;
        } else {
            return MessageFormat.format ("[{0}](" + BASE_VOICE_URL + "{1})", phonation, url);
        }
    }

    @Override
    public String toString() {
        return MessageFormat.format ("| {0} | {1} |",
                word,
                generateLink (phone, speech));
    }
}
