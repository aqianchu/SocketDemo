package com.scu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketDemo {
	private static int PORT = 54327;// 端口号
	private static List<Socket> mSocketList = new ArrayList<Socket>();// 存放请求的客户端的Socket
	private static ExecutorService mExcutorService;// 一个线程池
	private static ServerSocket mServerSocket;// Socket服务器端服务

	public static void main(String[] args) {
		try {
			mServerSocket = new ServerSocket(PORT);
			mExcutorService = Executors.newCachedThreadPool();// 实例化线程池对象(一次执行所有线程)
			Socket client = null;
			while (true) {
				client = mServerSocket.accept();
				mSocketList.add(client);// 将连接的客户端添加进集合
				mExcutorService.execute(new ThreadServer(client));// 给每一个客户端开一个线程放进线程池中
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				mServerSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	static class ThreadServer implements Runnable {

		private Socket mSocket;
		private BufferedReader mBufferedReader;// 用来读取客户端发来的数据
		private PrintWriter mPrintWriter;// 用来写入信息发给客户端
		private String MSG = "ggg";

		public ThreadServer(Socket socket) throws IOException {
			this.mSocket = socket;
			mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), "UTF-8"));
			mPrintWriter = new PrintWriter(mSocket.getOutputStream(), true);
			MSG = mBufferedReader.readLine();
			sendMessage();
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while ((MSG = mBufferedReader.readLine()) != null) {// readLine()方法是阻塞的，当有数据流时执行，没有则阻塞
					sendMessage();// 发送信息给客户端
				}
			} catch (Exception e) {

			} finally {

			}
		}

		private void sendMessage() throws IOException {
			for (Socket client : mSocketList) {
				mPrintWriter = new PrintWriter(client.getOutputStream(), true);
				mPrintWriter.println(MSG);
			}
		}
	}
}
