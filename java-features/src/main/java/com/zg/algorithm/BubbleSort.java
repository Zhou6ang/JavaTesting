package com.zg.algorithm;

import java.util.Arrays;

public class BubbleSort {

	static{
		System.out.println("static initial");
	}
	{
		System.out.println("instance initial");
	}
	public static void main(String[] args) {
		new BubbleSort().run1(new int[]{4,1,3,2,5,8,10,9});
		new BubbleSort().run2(new int[]{4,1,3,2,5,8,10,9});
	}

	public void run1(int[] args){
		/**
		 * first FOR indicates that how many rounds that need to be run.
		 * second FOR indicates that each two elements compared from back(last element) to front(first element).
		 */
		int temp = 0;
		for (int i = 0; i < args.length; i++) {
			
			for (int j = args.length-1; j > i; j--) {
				// from small to big
				if(args[j] < args[j-1]){
					temp = args[j-1];
					args[j-1] = args[j];
					args[j] = temp;
				}
			}
			
		}
		
		System.out.println(Arrays.toString(args));
	}
	
	public void run2(int[] args){
		/**
		 * first FOR indicates that how many rounds that need to be run.
		 * second FOR indicates that each two elements compared from front(first element) to back(last element).
		 */
		int temp = 0;
		for (int i = args.length-1; i >= 0; i--) {
			for (int j = 0; j < i; j++) {
				// from small to big
				if (args[j] > args[j + 1]) {
					temp = args[j + 1];
					args[j + 1] = args[j];
					args[j] = temp;
				}
			}
		}
		
		System.out.println(Arrays.toString(args));
	}
	
	public void run3(int[] args){
		int temp = 0;
		for (int i = args.length-1; i >= 0; i--) {
			for (int j = 0; j < i; j++) {
				// from small to big
				if (args[j] > args[j + 1]) {
					temp = args[j + 1];
					args[j + 1] = args[j];
					args[j] = temp;
				}
			}
		}
		
		System.out.println(Arrays.toString(args));
	}
}
