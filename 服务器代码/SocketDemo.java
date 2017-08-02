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
	private static int PORT = 54327;// �˿ں�
	private static List<Socket> mSocketList = new ArrayList<Socket>();// �������Ŀͻ��˵�Socket
	private static ExecutorService mExcutorService;// һ���̳߳�
	private static ServerSocket mServerSocket;// Socket�������˷���

	public static void main(String[] args) {
		try {
			mServerSocket = new ServerSocket(PORT);
			mExcutorService = Executors.newCachedThreadPool();// ʵ�����̳߳ض���(һ��ִ�������߳�)
			Socket client = null;
			while (true) {
				client = mServerSocket.accept();
				mSocketList.add(client);// �����ӵĿͻ�����ӽ�����
				mExcutorService.execute(new ThreadServer(client));// ��ÿһ���ͻ��˿�һ���̷߳Ž��̳߳���
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
		private BufferedReader mBufferedReader;// ������ȡ�ͻ��˷���������
		private PrintWriter mPrintWriter;// ����д����Ϣ�����ͻ���
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
				while ((MSG = mBufferedReader.readLine()) != null) {// readLine()�����������ģ�����������ʱִ�У�û��������
					sendMessage();// ������Ϣ���ͻ���
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
