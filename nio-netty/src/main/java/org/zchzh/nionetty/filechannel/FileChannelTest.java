package org.zchzh.nionetty.filechannel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zengchzh
 * @date 2021/5/7
 */
public class FileChannelTest {

    public static void main(String[] args) {
//        String sourcePath = "C:\\Users\\zengchzh\\Desktop\\testFileChannel\\redis.txt";
//        String targetPath = "C:\\Users\\zengchzh\\Desktop\\testFileChannel\\testFileChannel.txt";
//        copyFile(sourcePath, targetPath);

        String filePath = "C:\\Users\\zengchzh\\Desktop\\testFileChannel\\testdata.txt";
        try(FileChannel fileChannel = new RandomAccessFile(new File(filePath), "rw").getChannel()) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(48);
            int bytesRead = fileChannel.read(byteBuffer);
            StringBuilder stringBuilder = new StringBuilder();
            while (bytesRead != -1) {
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    stringBuilder.append((char) byteBuffer.get());
                }
                int num = Integer.parseInt(stringBuilder.toString());
                System.out.println("result = " + (num * num));
                byteBuffer.clear();
                bytesRead = fileChannel.read(byteBuffer);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void copyFile(String source, String target) {
//        File sourceFile = new File(source);
//        File targetFile = new File(target);
//        FileChannel sourceChannel = new FileInputStream(sourceFile).getChannel();
//        FileChannel targetChannel = new FileOutputStream(targetFile).getChannel();
//        try(FileChannel sourceChannel = new FileInputStream(sourceFile).getChannel();
//            FileChannel targetChannel = new FileOutputStream(targetFile).getChannel()) {
//            sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try(RandomAccessFile sourceFile = new RandomAccessFile(source, "r");
            RandomAccessFile targetFile = new RandomAccessFile(target, "rw");
            FileChannel sourceChannel = sourceFile.getChannel();
            FileChannel targetChannel = targetFile.getChannel()) {
            sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
