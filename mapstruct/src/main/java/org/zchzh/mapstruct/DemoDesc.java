package org.zchzh.mapstruct;


/**
 * @author zengchzh
 * @date 2021/8/27
 */
public class DemoDesc {

    private String content;

    private Integer len;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLen() {
        return len;
    }

    public void setLen(Integer len) {
        this.len = len;
    }

    @Override
    public String toString() {
        return "DemoDesc{" +
                "content='" + content + '\'' +
                ", len=" + len +
                '}';
    }
}
