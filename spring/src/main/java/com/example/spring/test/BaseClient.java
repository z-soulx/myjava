package com.example.spring.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * created by lyq
 */
public class BaseClient {
  public static void main(String[] args) throws IOException {
    ThreadPoolExecutor work = new ThreadPoolExecutor(
        8, 8, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
        (r) -> {
          Thread thread = new Thread(r);
          thread.setName("work");
          return thread;
        }
    );

    for (int k = 0; k < 100; k++) {
      work.execute(() -> {
          try{
            Socket socket = new Socket("localhost", 8787);
            socket.getOutputStream().write(("i am " + socket.getLocalPort()).getBytes(StandardCharsets.UTF_8));
            byte[] b = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while(true){
              while(inputStream.read(b) != -1){
                System.out.println(new String(b, StandardCharsets.UTF_8));
                socket.close();
              }
            }
          }catch (Exception e){
            //
          }
      });
    }

  }
}
