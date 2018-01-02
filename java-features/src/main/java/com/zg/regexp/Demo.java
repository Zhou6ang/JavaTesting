package com.zg.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo {

	public static void main(String[] args) {
		String s = "([^ ]+)=(\"[^\"]+\")";
		Matcher m = Pattern.compile(s).matcher("<project a=\"a b c d\" a1=\".../href/*\" name=\"Examples\" default=\"compile\">");
		while(m.find()){
			System.out.println(m.group());
			System.out.println(m.group(1)+":"+m.group(2));
		}
	}

}
