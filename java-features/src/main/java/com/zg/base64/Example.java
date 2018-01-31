package com.zg.base64;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Example {

	public static void main(String[] args) throws IOException {
      zipJPG(Paths.get("D:\\zhougang\\1.jpg"));
      unzipJPG(Paths.get("D:\\zhougang\\1.jpg.gz"));
	}
	
	public static void zipJPG(Path path) throws IOException {
    	gzip(path,encodeJPG(Files.readAllBytes(path)));
    }
    
    public static void unzipJPG(Path path) throws IOException {
    	byte[] decodeBytes = decodeJPG(ungzip(path));
		Files.write(Paths.get(path.getParent().toString(),path.getFileName().toString()+".jpg"), decodeBytes);
    }
    
    private static void unzip(Path path) throws IOException {
    	ZipFile zf = new ZipFile(path.toFile());
    	Enumeration<? extends ZipEntry> entyies = zf.entries();
    	while(entyies.hasMoreElements()) {
    		ZipEntry zipEntry = entyies.nextElement();
    		
    		InputStream is = zf.getInputStream(zipEntry);
    		byte[] b = new byte[1024];
    		int i = 0;
    		StringBuilder sb = new StringBuilder();
    		while((i = is.read(b)) != -1) {
    			sb.append(new String(b, 0, i, Charset.defaultCharset()));
    		}
    		is.close();
    		
    		byte[] decodeBytes = decodeJPG(sb.toString().getBytes());
    		
    		Files.write(Paths.get(path.getParent().toString(),zipEntry.getName().toString()+".jpg"), decodeBytes);
    		
    	}
    	zf.close();
    	
    }
    
    private static byte[] ungzip(Path path) throws IOException {
    	GZIPInputStream ginput = new GZIPInputStream(Files.newInputStream(path));
    	byte[] b = new byte[1024];
		int i = 0;
		StringBuilder sb = new StringBuilder();
		while((i = ginput.read(b)) != -1) {
			sb.append(new String(b, 0, i, Charset.defaultCharset()));
		}
		ginput.close();
		return sb.toString().getBytes();
		
    }
    
    private static void zip(Path path,byte[] b) throws IOException {
    	Path outputPath = Paths.get(path.getParent().toString(), path.getFileName().toString()+".zip");
    	ZipOutputStream zo = new ZipOutputStream(Files.newOutputStream(outputPath));
        zo.putNextEntry(new ZipEntry(path.getFileName().toString()+"_zip"));
        zo.setLevel(9);
        zo.write(b);
        zo.finish();
        zo.close();
    }
    public static void gzip(Path path, byte[] b) throws IOException {
    	Path outputPath = Paths.get(path.getParent().toString(), path.getFileName().toString()+".gz");
    	GZIPOutputStream gz = new GZIPOutputStream(Files.newOutputStream(outputPath));
    	gz.write(b);
        gz.finish();
        gz.close();
    }
    
    private static byte[] encodeJPG(byte[] b) throws IOException {
      return Base64.getEncoder().encode(b);
    }
    private static byte[] decodeJPG(byte[] b) throws IOException {
          return Base64.getDecoder().decode(b);
      }
}
