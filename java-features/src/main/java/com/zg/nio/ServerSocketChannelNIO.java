package com.zg.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;

public class ServerSocketChannelNIO {
	
	private static int[] ports = new int[]{6666,7777,8888,9999};

	/**
	 * Note: 1. register of ServerSocketChannel only supports SelectionKey.OP_ACCEPT, other cases were not supported(can check from ServerSocketChannel.validOps()) ;
	 *          register of SocketChannel can support SelectionKey.OP_READ and SelectionKey.OP_WRITE and SelectionKey.OP_CONNECT,
	 *          but the SelectionKey.OP_WRITE and SelectionKey.OP_CONNECT is not used normally.
	 *       2. selector.select() is blocked, but selector.select(1000) is not blocked, and selector.selectNow() is not blocked also.
	 *       3. could not use way: for (SelectionKey selectionKey : set) {...set.remove(selectionKey);}, it will throw ConcurrentModificationException
	 */
	public static void main(String[] args) throws IOException {
		Selector selector = Selector.open();
		
		for (int port : ports) {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			ServerSocket ss = serverSocketChannel.socket();
			ss.bind(new InetSocketAddress(port));
			
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("initial server port:"+port+" ready.");
		}
		
		new Thread(new MySelector(selector)).start();
		
	}

	static class MySelector implements Runnable{
		
		private Selector selector;
		private static ByteBuffer bf = ByteBuffer.allocate(1000); 
		
		public MySelector(Selector selector) {
			this.selector = selector;
		}

		@Override
		public void run() {
			try {
				while(true) {
					int i = selector.select();//blocked. but selector.select(1000) is not blocked, and selector.selectNow() is not blocked also.
					if(i == 0) continue;
					Set<SelectionKey> set = selector.selectedKeys();
					Iterator<SelectionKey> iter = set.iterator();//could not use way: for (SelectionKey selectionKey : set) {...set.remove(selectionKey);}, it will throw ConcurrentModificationException
					while(iter.hasNext()) {
						SelectionKey selectionKey = iter.next();
						iter.remove();
						if(selectionKey.isAcceptable()) {
							ServerSocketChannel ssc = (ServerSocketChannel)selectionKey.channel();
							System.out.println("received connection request from client..."+ssc);
							SocketChannel sc = ssc.accept();
							sc.configureBlocking(false);
							sc.register(selector, SelectionKey.OP_READ);
							System.out.println("connection has been established..."+sc);
						}else if(selectionKey.isReadable()) {
							//TODO do some logic.
							SocketChannel ssc = (SocketChannel)selectionKey.channel();
							String msg = receivedMsg(bf, ssc);
							System.out.println(LocalDateTime.now()+" received from ["+ssc.getRemoteAddress()+"]: "+msg);
							
							Thread.sleep(1000);//response msg after 1 second
							if("@END@".equals(msg)) {
								ssc.close();
							}else {
								sendMsg(bf, ssc,"This message sent from server. "+msg);
							}
							
						} 
						
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		private String receivedMsg(ByteBuffer bf, SocketChannel ssc) throws IOException {
			StringBuilder sb = new StringBuilder();
			bf.clear();
			while(ssc.read(bf) > 0) {
				bf.flip(); 
				sb.append(new String(bf.array(),bf.position(),bf.limit()));
				bf.clear();
			}
			return sb.toString();
		}

		private void sendMsg(ByteBuffer bf, SocketChannel ssc,String msg) throws IOException {
			bf.clear();
			bf.put(msg.getBytes());
			bf.flip();
			ssc.write(bf);
		}
	}
}
