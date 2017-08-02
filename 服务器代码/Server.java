package com.scu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
	public void run() {
		try {
			// 创建ServerSocket
			ServerSocket serverSocket = new ServerSocket(6868);
			while (true) {
				// 接受客户端请求
				Socket client = serverSocket.accept();
				if (client == null) {
					continue;
				}
				System.out.println("客户端来了");
				try {
					// 接收客户端消息
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					String str = new String(in.readLine().getBytes(),"UTF-8");
					System.out.println("收到：" + str);
					// 向服务器发送消息
					PrintWriter out = new PrintWriter(
							new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
					String fu = "服务器收到了";
					out.println(fu);
					// 关闭流
					out.close();
					in.close();
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				} finally {
					// 关闭
					client.close();
					System.out.println("关闭客户端");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// main函数，开启服务器，这就是一个普通的Java类，可以放到命令行里面执行的
	public static void main(String a[]) {
		Thread desktopServerThread = new Thread(new Server());
		desktopServerThread.start();
	}
}