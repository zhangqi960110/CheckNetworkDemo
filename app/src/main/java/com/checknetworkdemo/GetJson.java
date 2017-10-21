package com.checknetworkdemo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by zhangqi on 2017/10/20.
 */

public class GetJson {
    public static String readStream(InputStream is) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1){
            baos.write(buffer,0,len);
        }
        is.close();
        String content = new String(baos.toByteArray(),"gbk");
        return content;
    }
}
