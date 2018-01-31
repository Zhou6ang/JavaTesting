package com.zg.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class SocketChannelNIO {
	
	private static int[] ports = new int[]{6666,7777,8888,9999};
	
	/**
	 * Note: 1. SocketChannel is not supported the SelectionKey.OP_ACCEPT, can check
	 *          from SocketChannel.validOps() 
	 *       2. currently selector.select() is not blocked. why?
	 *       3. could not use way: for (SelectionKey selectionKey : set) {...set.remove(selectionKey);}, 
	 *          it will throw ConcurrentModificationException
	 * 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		Selector selector = Selector.open();

		for (int port : ports) {
			SocketChannel sc = SocketChannel.open();
			sc.configureBlocking(false);
			sc.connect(new InetSocketAddress(port));
			sc.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
			System.out.println("connect to server port:"+port+" ready.");
		}
		
		new Thread(new MySelector(selector)).start();

	}

	static class MySelector implements Runnable {
		private Selector selector;
		private ByteBuffer bf = ByteBuffer.allocate(500);
		private String request = "This message was sent by client side.";

		public MySelector(Selector selector) {
			this.selector = selector;
		}

		@Override
		public void run() {
			int count = 0;
			while (true) {
				try {
					if (!selector.isOpen()) {
						System.out.println("selector is closed");
						break;
					}

					int i = selector.select();// not blocked.
					if (i == 0)
						continue;
					Set<SelectionKey> set = selector.selectedKeys();
					Iterator<SelectionKey> iter = set.iterator();
					while(iter.hasNext()) {
						SelectionKey selectionKey = iter.next();//could not use way: for (SelectionKey selectionKey : set) {...set.remove(selectionKey);}, it will throw ConcurrentModificationException
						iter.remove();
						// here no need to process selectionKey.isAcceptable() since it's not supported for SocketChannel
						if (selectionKey.isReadable()) {
							//TODO do some logic.
							SocketChannel ssc = (SocketChannel) selectionKey.channel();
							String sb = receivedMsg(ssc);
							System.out.println(LocalDateTime.now() + " received from [" + ssc.getRemoteAddress() + "]: " + sb);
							Random r = new Random();

							if (count >= 30) {
								sendMsg("@END@", ssc);
								ssc.close();
							} else {
								sendMsg(request + r.nextInt(1000), ssc);
								count++;
							}

						} else if (selectionKey.isConnectable()) {
							SocketChannel ssc = (SocketChannel) selectionKey.channel();
							System.out.println("connection is establishing...");
							while (!ssc.finishConnect()) {// waiting for connection established.
								Thread.sleep(100);
							}
							System.out.println("connection has established...");
							sendMsg("@START@", ssc);
							
						} else if (selectionKey.isWritable()) {
							System.out.println("This channel can be writing ... but it's not used normally.");
						}

					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		}
		
		private String receivedMsg(SocketChannel sc) throws IOException {
			StringBuilder sb = new StringBuilder();
			bf.clear();
			while (sc.read(bf) > 0) {
				bf.flip();
				sb.append(new String(bf.array(), bf.position(), bf.limit()));
				bf.clear();
			}
			return sb.toString();
		}

		private void sendMsg(String request, SocketChannel sc) throws IOException {
			bf.clear();
			bf.put(request.getBytes());
			bf.flip();
			sc.write(bf);
		}
	}
}