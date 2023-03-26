package de.androidcrypto.talktoyourcreditcard;

public class DolTag {

    private byte[] tag;
    private String tagName;
    private byte[] defaultValue;

    public DolTag(byte[] tag, String tagName, byte[] defaultValue) {
        this.tag = tag;
        this.tagName = tagName;
        this.defaultValue = defaultValue;
    }

    public byte[] getTag() {
        return tag;
    }

    public void setTag(byte[] tag) {
        this.tag = tag;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public byte[] getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(byte[] defaultValue) {
        this.defaultValue = defaultValue;
    }
}
