package com.example.spring.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * created by lyq
 */
public class BaseServer {

  AtomicInteger index = new AtomicInteger(0);

  ThreadPoolExecutor boss = new ThreadPoolExecutor(
      4, 4, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
      (r) -> {
        Thread thread = new Thread(r);
        thread.setName("boss" + index.getAndIncrement());
        return thread;
      }
  );

  ThreadPoolExecutor work = new ThreadPoolExecutor(
      8, 8, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
      (r) -> {
        Thread thread = new Thread(r);
        thread.setName("work" + index.getAndIncrement());
        return thread;
      }
  );


  LinkedBlockingQueue<Socket> linking = new LinkedBlockingQueue<>();
  LinkedBlockingQueue<Socket> linked = new LinkedBlockingQueue<>();

  ServerSocket serverSocket;

  public static void main(String[] args) throws Exception {
    BaseServer server = new BaseServer();
    server.start();
  }



  public void start() throws IOException, InterruptedException {

    serverSocket = new ServerSocket(8787);
AtomicInteger count = new AtomicInteger();
    boss.execute(() -> {
      while (true){
        try {
          linking.add(serverSocket.accept());
          System.out.println("1c"+count.getAndIncrement());
        } catch (Exception e) {
          //continue
        }
      }
    });
    AtomicInteger count2 = new AtomicInteger();
    AtomicInteger count3 = new AtomicInteger();
    boss.execute(() -> {
      while (true){
        try {
          Socket lingkingSocket = linking.take();
          System.out.println("3c"+ count3.getAndIncrement());
          //work线程池换成boss线程池才能正常将socket迁移到linked链表里
          work.execute(new Task(lingkingSocket, (success) -> {
//          boss.execute(new Task(lingkingSocket, (success) -> {
            System.out.println(count2.getAndIncrement());
            if(Boolean.TRUE.equals(success)){
              linked.add(lingkingSocket);
            }
          }));
        } catch (Exception e) {
          //continue
        }
      }
    });
//    Thread.sleep(4000);
    boss.execute(() -> {
      while (true){
        try {
          Socket lingedSocket = linked.take();
          work.execute(() -> {
            OutputStream outputStream = null;
            try {
              outputStream = lingedSocket.getOutputStream();
              outputStream.write(("hello " + lingedSocket.getPort()).getBytes(StandardCharsets.UTF_8));
              outputStream.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          });
        } catch (Exception e) {
          //continue
        }
      }
    });
  }



  interface CallBack{
    void notifly(boolean success);
  }


  static class Task implements Runnable{
    Socket socket;
    CallBack callBack;

    public Task(Socket socket, CallBack callBack) {
      this.socket = socket;
      this.callBack = callBack;
    }

    @Override
    public void run() {
      try {
        InputStream inputStream = socket.getInputStream();
        byte[] b = new byte[1024];
        while(inputStream.read(b) != -1){
          System.out.println(socket.getLocalAddress().getHostAddress()  + "-" + socket.getPort() + ":" + new String(b, StandardCharsets.UTF_8));
          callBack.notifly(true);
        }
      } catch (IOException e) {
        //
      }

    }
  }
}


