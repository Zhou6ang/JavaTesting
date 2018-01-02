package com.zg.jndi;

import com.sun.jna.platform.win32.User32;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        final User32 lib = User32.INSTANCE;
        System.out.println(lib.GetWindowThreadProcessId(null, null)); ;
    }
}
