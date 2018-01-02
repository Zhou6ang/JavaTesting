package com.zg.javapreference;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class MyPreference {

	public static void main(String[] args) throws IOException, BackingStoreException, ClassNotFoundException {
		//shared with all users.
		Preferences sysPref = Preferences.systemRoot().node("abc");
		sysPref.putInt("abc", 100);
		System.out.println(sysPref.get("abc", null));
		
		//only visible to the user who created.
		Preferences pref = Preferences.userRoot().node("testing");
		//step 1 storage, set java preference.
//		pref.putInt("a", 10);
//		pref.put("b", "string..");
//		User u = new User();
//		u.setUsername("obj_testing_name");
//		u.setAge(30);
//		u.setAddress("chengdu");
//		ByteArrayOutputStream output = new ByteArrayOutputStream();
//		ObjectOutputStream objoutput = new ObjectOutputStream(output);
//		objoutput.writeObject(u);
//		objoutput.flush();
//		pref.putByteArray("obj", output.toByteArray());
//		objoutput.close();
//		output.close();
//		pref.exportNode(Files.newOutputStream(Paths.get("D:\\javapref.xml")));
		
		//step 2 get, get java preference.
		System.out.println(pref.get("a", null));
		System.out.println(pref.get("b", null));
		byte b[] = pref.getByteArray("obj", null);
		ByteArrayInputStream input = new ByteArrayInputStream(b);
		ObjectInputStream obj_input = new ObjectInputStream(input);
		User uu = (User)obj_input.readObject();
		obj_input.close();
		input.close();
		
		System.out.println(uu.getUsername()+","+uu.getAge()+","+uu.getAddress());
	}
	
	static class User implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -6859349385921675049L;
		String username;
		int age;
		String address;
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		
	}

}
