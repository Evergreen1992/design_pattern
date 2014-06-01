package com.tankwar.utils;

import java.io.*;

/**
 * 游戏场景保存
 * @author Administrator
 *
 */
public class FileUtils {
	
	/**
	 * 获取总关卡数量
	 * @return
	 */
	public static int getStagesSize(){
		return new File("source/stages/").list().length;
	}
	
	public static void saveScene(int[][] array){
		int totle = 0 ;
		File file = null ;
		file = new File("source/stages/");
		totle = file.list().length + 1;
		
		String fileName = "stage" + totle ;
		File current = new File("source/stages/" + fileName +".txt") ;
		FileWriter out = null; 
		
		try{
			if( !current.exists())
				current.createNewFile();
			out = new FileWriter(current);
			for(int i = 0; i<array.length; i++){
				for(int j = 0; j<array[i].length; j ++){
					if( i <= 2)
						out.write(0 + "");
					else
						out.write(array[i][j] + "");
				}
				if( i != array[i].length - 1)
					out.write("\n");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if( out != null)
					out.close();	
			}catch(Exception e){
				System.out.println("关闭异常!");
			}
		}
		System.out.println("游戏场景数据保存成功!");
	}
}
