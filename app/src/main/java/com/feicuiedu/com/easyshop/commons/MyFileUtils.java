package com.feicuiedu.com.easyshop.commons;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MyFileUtils {
	
	public static String SD_PATH = Environment.getExternalStorageDirectory()
			+ "/Photo_LJ/";

	public static void saveBitmap(Bitmap bm, String picName) {
		try {
			if (!isFileExist("")) {
				File tempf = createSDDir("");
			}
			File f = new File(SD_PATH, picName + ".JPEG");
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SD_PATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(SD_PATH + fileName);
		file.isFile();
		return file.exists();
	}

	/*删除指定文件*/
	public static void delFile(String fileName){
		File file = new File(SD_PATH + fileName);
		if(file.isFile()){
			file.delete();
        }
		file.exists();
	}

	/*删除缓存文件夹*/
	public static void deleteDir() {
		File dir = new File(SD_PATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;
		
		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); 
			else if (file.isDirectory())
				deleteDir(); 
		}
		dir.delete();
	}

	/*返回文件的(路径+文件名)的字符串集合*/
	public static ArrayList<String> getAllFilePath(){
		ArrayList<String> list=new ArrayList<>();
		File dir = new File(SD_PATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return list;
		for (File file : dir.listFiles()) {
			if (file.isFile())
				list.add(SD_PATH +file.getName());
			else if (file.isDirectory())
				getAllFilePath();
		}
		return list;
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(SD_PATH +path);
			if (f.exists()) {
				return true;
			}
		} catch (Exception e) {

			return false;
		}
		return false;
	}

}