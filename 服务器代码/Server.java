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
			// ����ServerSocket
			ServerSocket serverSocket = new ServerSocket(6868);
			while (true) {
				// ���ܿͻ�������
				Socket client = serverSocket.accept();
				if (client == null) {
					continue;
				}
				System.out.println("�ͻ�������");
				try {
					// ���տͻ�����Ϣ
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					String str = new String(in.readLine().getBytes(),"UTF-8");
					System.out.println("�յ���" + str);
					// �������������Ϣ
					PrintWriter out = new PrintWriter(
							new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
					String fu = "�������յ���";
					out.println(fu);
					// �ر���
					out.close();
					in.close();
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				} finally {
					// �ر�
					client.close();
					System.out.println("�رտͻ���");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// main�����������������������һ����ͨ��Java�࣬���Էŵ�����������ִ�е�
	public static void main(String a[]) {
		Thread desktopServerThread = new Thread(new Server());
		desktopServerThread.start();
	}
}