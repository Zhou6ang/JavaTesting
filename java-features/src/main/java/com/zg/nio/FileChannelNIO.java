package com.zg.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileChannelNIO {

	public static void main(String[] args) throws IOException {
//		fileCopy(Paths.get("D:\\software\\AcrobatPro_11_Web_Rsdown_cn.1400570375.rar"),Paths.get("E:\\1.rar"));
		fileReadWriteNIO();
	}
	
	public static void fileReadWriteNIO() throws IOException {
		FileInputStream fileInput = new FileInputStream(Paths.get("D:\\zhougang\\doc2any.ini").toFile());
		FileOutputStream output = new FileOutputStream(Paths.get("D:\\1.txt").toFile());
		FileChannel fileChannel = fileInput.getChannel();
		ByteBuffer bf = ByteBuffer.allocate(20);
		StringBuilder sb = new StringBuilder();
		System.out.println("size:"+fileChannel.size());
		while(fileChannel.read(bf) >= 0) {//step-1: write data to buffer.
			System.out.println("position:"+bf.position()+",limit:"+bf.limit()+",capacity:"+bf.capacity());
			bf.flip();//step-2: make buffer ready to read.
			if(bf.hasArray()) {
				System.out.println("position(start):"+bf.position()+",limit(end):"+bf.limit()+",capacity(max):"+bf.capacity());
				String s = new String(bf.array(),bf.position(),bf.limit());//step-3: get data from buffer.
				System.out.println("***"+s+"***");
				sb.append(s);
				while(bf.hasRemaining()) {
					output.getChannel().write(bf);
				}
				
			}else {
				System.out.println("No bytes array.");
			}
			bf.clear();//step-4: clear data for buffer.
			
		}
		
		fileInput.close();
		output.close();
		
		System.out.println(sb.toString());
		
	}
	
	public static void fileCopy(Path src,Path tgt) throws IOException {
		long a = System.currentTimeMillis();
		Files.copy(src, tgt);
		System.out.println("Cost time: "+(System.currentTimeMillis() - a));
		long b = System.currentTimeMillis();
		FileInputStream input = new FileInputStream(src.toFile());
		FileOutputStream output = new FileOutputStream(Paths.get("E:\\2.rar").toFile());
		input.getChannel().transferTo(0, input.getChannel().size(), output.getChannel());
		output.close();
		input.close();
		System.out.println("Cost time: "+(System.currentTimeMillis() - b));
	}

	
	
}
