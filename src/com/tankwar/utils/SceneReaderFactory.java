package com.tankwar.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SceneReaderFactory {

	public static String[] readMap(int stage){
		String[] array = new String[Constant.MAP_Array_SIZE];
		File file = new File("source/stages/stage" + stage + ".txt");
		BufferedReader bur = null ;
		try {
			FileReader fr = new FileReader(file);
			bur = new BufferedReader(fr);
			String str = null;
			
			int i = 0 ;
			while((str = bur.readLine()) != null){
				array[i] = str ;
				i ++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if( bur != null )
				try {
					bur.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return array ;
	}
	
	/*public static char[][] readSceneChar(int stage){
		char[][] array = new char[Constant.MAP_Array_SIZE][Constant.MAP_Array_SIZE_HEIGHT];
		File file = new File("source/stages/stage" + stage + ".txt");
		BufferedReader bur = null ;
		try {
			FileReader fr = new FileReader(file);
			bur = new BufferedReader(fr);
			String str = null;
			
			
			while((str = bur.readLine()) != null){
				for( int i = 0 ; i < str.length(); i ++){
					System.out.print(str.charAt(i));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if( bur != null )
				try {
					bur.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return array ;
	}*/
	
	public static void main(String[] args) {
		
	}
}