package com.zg.protobuf.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.IntStream;

import com.protobuf.Demo;
import com.protobuf.Demo.Person;
import com.protobuf.Demo.Person.PhoneNumber;
import com.protobuf.Demo.Person.PhoneType;
import com.protobuf.Task;
import com.protobuf.Task.Foo;
import com.protobuf.Task.Foos;
import com.protobuf.Task.Foos.Builder;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        System.out.println( "Hello World!" );
       new App().sender();
       new App().receiver();
    }
    
    public void sender() throws IOException{
    	Person p = Demo.Person.newBuilder().setAge(10).setId(1).setName("zg").setMail("xxx@xxx.xx").addPhone(PhoneNumber.newBuilder().setNum("123456789").setType(PhoneType.Work).build()).build();
//        HttpURLConnection urlconn = (HttpURLConnection)new URL("http://127.0.0.1").openConnection();
    	String path = this.getClass().getResource("/").getPath()+"data";
    	FileOutputStream output = new FileOutputStream(path);
//		p.writeTo(output);
        
        
        Builder f=Foos.newBuilder();
        IntStream.range(1, 100).forEach(e->{
        	f.addFoo(Foo.newBuilder().setId(e).setContent("xxx"+e).setName("test-"+e));
        });
        f.build().writeTo(output);
        
        output.close();
    }
    
    public void receiver() throws IOException{
//    	Person p = Person.parseFrom(this.getClass().getResourceAsStream("/data"));
//    	System.out.println(p);
    	
    	System.out.println(Foos.parseFrom(this.getClass().getResourceAsStream("/data")));
    }
}
