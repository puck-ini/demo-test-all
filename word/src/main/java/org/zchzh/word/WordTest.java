package org.zchzh.word;

import com.deepoove.poi.XWPFTemplate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author zengchzh
 * @date 2021/8/4
 */
public class WordTest {

    private static final String TEMPLATE = "D:\\testdata\\word\\template.docx";

    private static final String OUTPUT = "D:\\testdata\\word\\";

    public static void main(String[] args) throws IOException {
        String outputPath = OUTPUT + UUID.randomUUID().toString() + ".docx";
        Map<String, Object> map = new HashMap<>();
        map.put("test","hello world");
        XWPFTemplate template = XWPFTemplate.compile(TEMPLATE).render(map);
        template.writeAndClose(new FileOutputStream(outputPath));
    }
}
