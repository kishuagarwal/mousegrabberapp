package com.kishu.mousegrabberapp;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

import android.content.Context;
import android.text.TextUtils.StringSplitter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MouseView extends View {

	
	public MouseView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	public MouseView(Context context)
	{
		super(context);
	}
	public MouseView(Context context, AttributeSet attrs)
	{
		super(context,attrs);
	}

	private byte[] ipAddress;
	private int portNumber = 10003;
	private Socket socket;
	private PrintWriter printWriter;
	private double width; 
	private double height ;

	private void setupSocket() {
		// TODO Auto-generated method stub
		height = getHeight();
		width = getWidth(); 
		Log.d("WIDTH",width+"");
		Log.d("Height",height+"");
		try {
			byte ad[] = {(byte) 192,(byte) 168,43,(byte) 130};
			//Log.d("MouseView", Arrays.toString(ad));
			InetAddress address = Inet4Address.getByAddress(ad);
			Log.d("InetAddress",address.toString());
			socket = new Socket(address,portNumber);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("MouseView", " Created Socket");
		Log.d("SOcket",socket.toString());
		try {
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		final int x = (int) event.getX();
		final int y = (int) event.getY(); 
	//	Log.d("Ontouch",x+" "+y);
		Thread post = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(printWriter != null)
				{
				printWriter.println((double)(x/width) +" "+(double)(y/height)+"\n");
				printWriter.flush();
				}
			//	Log.d("MouseView",x+" "+y);
			}
		});
		post.start();
		return true;
	}
	
	public void setupIpaddress(String ip){
		//ipAddress = ip;
		ipAddress = new byte[4];
		String i[] = ip.split("\\.");
		Log.d("IpAddress",Arrays.toString(i));
		ipAddress[0] = (byte) Integer.parseInt(i[0]);
		ipAddress[1] = (byte) Integer.parseInt(i[0]);
		ipAddress[2] = (byte) Integer.parseInt(i[0]);
		ipAddress[3] = (byte) Integer.parseInt(i[0]);
		//Log.d("MouseView",ip);
		Thread setup = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				setupSocket();
			}
		});
		setup.start();
	}

	public void close() {
		// TODO Auto-generated method stub
		Thread post = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				printWriter.println("q\n");
				printWriter.flush();
				printWriter.close();
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		post.start();
	}

}
