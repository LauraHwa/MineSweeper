package edu.nju.network.host;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import edu.nju.network.Configure;
import edu.nju.view.listener.MenuListener;

public class HostThread extends Thread {
	private ServerSocket server;
	private Socket client;
	private ObjectInputStream reader;
	private ObjectOutputStream out;
	public static boolean isConnected = false;
	
	public HostThread(){
		super();
		client = new Socket();
	}
	
	//read data
	@Override
	public void run(){
		while(!this.isInterrupted()){
			if(!client.isConnected()){
					try {
						server = new ServerSocket(Configure.PORT);
					} catch (IOException e1) {
					}
//					System.out.println("Waiting for Client!!!");
					try {
						client = server.accept();
					} catch (IOException e) {
						break;
					}
					isConnected = true;
//					System.out.println("Client accepted!!!");
					try {
						reader = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
						out = new ObjectOutputStream(client.getOutputStream());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
			}
			//read from socket
			if(client.isConnected()){
				try {
					Object obj = reader.readObject();
					if(obj != null){
						ServerAdapter.readData(obj);
					}
				} catch(SocketException se){
					this.close();
					break;
				}catch (IOException e1) {
					break;
				} catch (ClassNotFoundException e) {
					break;
				}
			}
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
	
	
	public void close(){
		try {
			if(isConnected){
				isConnected = false;
				reader.close();
				out.close();		
			}
			client.close();
			server.close();
			this.interrupt();
		} catch (IOException e) {
			return;
		}
	}

	public Object write(Object o) {
		try {
			out.writeObject(o);
			out.flush();
		} catch (IOException e) {
			close();
			return false;
		}
		
		return true;
	}
	
	public boolean connected(){
		if(client.isConnected()){
			return true;
		}else{
			return false;
		}
	}
}
