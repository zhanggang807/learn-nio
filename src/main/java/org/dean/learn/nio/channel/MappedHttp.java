package org.dean.learn.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Dummy(虚拟的) HTTP server using MappedByteBuffer
 * Given a filename on the command line, pretend(假装)
 * to be a web server and generate as HTTP response containing
 * the file content preceded(之前的) by appropriate headers. The
 * data is sent with a gathering write.
 * Created by zhanggang3 on 2016/3/16.
 */
public class MappedHttp {

    private static final String OUTPUT_FILE = "MappedHttp.out";
    private static final String LINE_SEP = "\r\n";
    private static final String SERVER_ID = "Server: Ronsoft Dummy Server";
    private static final String HTTP_HDR = "HTTP/1.0 200 OK" + LINE_SEP + SERVER_ID + LINE_SEP;
    private static final String HTTP_404_HDR = "HTTP/1.0 404 Not Found" + LINE_SEP + SERVER_ID + LINE_SEP;
    private static final String MSG_404 = "Could not open file: ";

    public static void main(String[] args) throws Exception {
        String[] param = new String[]{"blahalah.txt"};
        args = param;
        if (args.length < 1) {
            System.err.println("Usage: filename");
            return;
        }
        String file = args[0];
        ByteBuffer header = ByteBuffer.wrap(bytes(HTTP_HDR));
        ByteBuffer dynhdrs = ByteBuffer.allocate(128);
        ByteBuffer[] gather = {header, dynhdrs, null};
        String contentType = "unknow/unknow";
        long contentLength = -1;
        try {
            FileInputStream fis = new FileInputStream(file);
            FileChannel fc = fis.getChannel();
            MappedByteBuffer filedata = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());//这里会有异常
            gather[2] = filedata;
            contentLength = fc.size();
            contentType = URLConnection.guessContentTypeFromName(file);
        } catch (Exception e) {
            // file could not be opened; report problem
            ByteBuffer buf = ByteBuffer.allocate(128);
            String msg = MSG_404 + e + LINE_SEP;
            System.out.println("msg = " + msg);;
            buf.put(bytes(msg));
            buf.flip();
            // Use the HTTP error response
            gather[0] = ByteBuffer.wrap(bytes(HTTP_404_HDR));
            gather[2] = buf;
            contentLength = msg.length();
            contentType = "text/plain";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("Content-Length: " + contentLength);
        sb.append(LINE_SEP);
        sb.append("Content-Type: ").append(contentType);
        sb.append(LINE_SEP).append(LINE_SEP);
        dynhdrs.put(bytes(sb.toString()));
        dynhdrs.flip();
        FileOutputStream fos = new FileOutputStream(OUTPUT_FILE);
        FileChannel out = fos.getChannel();
        // All the bufferes have been prepared write out
        while (out.write(gather) > 0) {
            int i = 0;
            //Empty body; loop until all buffers are empty
        }
        out.close();
        System.out.println("output written to " + OUTPUT_FILE);
    }

    // Convert a string to its constituent bytes
    // from the ASCII character set
    private static byte[] bytes(String string) throws Exception{
        return string.getBytes("US-ASCII");
    }


}

