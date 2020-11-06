package com.zxod.springbootsimple.util;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hzhao1
 * @date 2019/5/27 4:34 PM
 */
public class FileUtils {
    /**
     * 读取文件内容
     *
     * @param resourcePath 文件路径
     * @return 文件内容
     * @throws IOException IO异常
     */
    public static List<String> readFileRows(String resourcePath) throws IOException {
        List<String> rows = new ArrayList<>();
        Resource resource = new ClassPathResource(resourcePath);
        InputStream inputStream = resource.getInputStream();
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(streamReader);
        try {
            String line = reader.readLine();
            while (line != null && !"".equals(line)) {
                rows.add(line);
                line = reader.readLine();
            }

        } catch (IOException e) {
            throw e;
        } finally {
            inputStream.close();
            streamReader.close();
            reader.close();
        }
        return rows;
    }

    /**
     * 保存数据到file中
     *
     * @param message 需要保存的数据
     * @throws IOException IO异常
     */
    public static void saveToFile(String message, File file) throws IOException {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter writer = null;
        try {
            fos = new FileOutputStream(file, true);
            osw = new OutputStreamWriter(fos);
            writer = new BufferedWriter(osw);
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (osw != null) {
                osw.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static void main(String[] args) {
        List<String> rows = new ArrayList<>();
        try {
            rows = FileUtils.readFileRows("data/test.csv");
        } catch (IOException e) {
            System.out.printf("read dish query rewrite error: {}", e);
        }
        rows.stream().forEach(System.out::println);
    }
}
