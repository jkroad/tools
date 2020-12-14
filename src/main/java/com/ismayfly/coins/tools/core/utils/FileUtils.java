package com.ismayfly.coins.tools.core.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtils {


    public static void main(String[] args) throws IOException {
        File file = new File("/Users/jl/Downloads/weibo2019.txt");
        try{
            FileInputStream in = new FileInputStream(file);
            FileChannel fic = in.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(60480);
            while (fic.read(buf) != -1) {

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void fileChannelRead(File file, File newFile) {

        long d1 = System.currentTimeMillis();
        FileInputStream in = null;
        FileOutputStream output = null;
        FileChannel fic = null;
        FileChannel foc = null;
        try {

            output = new FileOutputStream(newFile);
            fic = in.getChannel();
            foc = output.getChannel();

            //fic.transferTo(0, fic.size(), foc);
            ByteBuffer buf = ByteBuffer.allocate(20480);
            while (fic.read(buf) != -1) {
                buf.flip();//切换到读取数据模式
                foc.write(buf);//将缓冲区的数据写入通道中
                buf.clear();//清空缓冲区
            }

            long d2 = System.currentTimeMillis();
            System.out.println("fileChannelRead读取完成，耗时：" + (d2 - d1));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
