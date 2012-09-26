package com.ngnclht.libs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.ngnclht.apps.n_explorer.MainActivity;
import com.ngnclht.apps.n_explorer.NContentView;
import com.ngnclht.apps.n_explorer.SettingsActivity;

public class NFileManager {

	private SharedPreferences settings;
	private Stack<String> pathStack;
	private ArrayList<String> currentDirContent;
	private String currentPath;
	private final int BUFFER = 2048;
	private long fileSize;
	private ArrayList<String> imgList;
	private NContentView nContentView;
	private static boolean sortAcescing;

	public NFileManager(Context context, NContentView nct) {
		nContentView = nct;
		settings = context.getSharedPreferences("SettingsActivity",
				context.MODE_WORLD_WRITEABLE);
		String home = settings.getString(SettingsActivity.KEY_SETTING_HOMEDIR,
				Environment.getExternalStorageDirectory().getPath());
		Bundle bundle = ((MainActivity)context).getIntent().getExtras();
		if(bundle != null){
			Log.v("NFileManager", "" + String.valueOf("Run from short cut"));
			if(bundle.getString(MainActivity.CMENU_SHORTCUT_INTENT) != null){
				home = String.valueOf(bundle.getString(MainActivity.CMENU_SHORTCUT_INTENT));
			}
		}
		pathStack = new Stack<String>();
		pathStack.add("/");
		for (int i = 1; i < home.length(); i++) {
			String charAtI = String.valueOf(home.charAt(i)); 
			if( charAtI.equals("/")){
				pathStack.add(home.substring(0, i));
			}
		}
		pathStack.add(home);
		
		sortAcescing = (settings.getInt(
				SettingsActivity.KEY_SETTING_SORTINGVECTOR, 1) == SettingsActivity.SORT_ASCENDING) ? true
				: false;
		currentDirContent = new ArrayList<String>();
		imgList			  = new ArrayList<String>();
		refreshCurrentDirContent();
	}
	public void gotoHome(){
		gotoDir(settings.getString(SettingsActivity.KEY_SETTING_HOMEDIR,"/sdcard"));
	}
	public void goBack() {
		if(pathStack.size() > 1) pathStack.pop();
		refreshCurrentDirContent();
	}
	
	// calcula
	public String getMaxExtensionInFolder(){
		String ex ="";
		return ex;
	}
	public boolean goUp(){
		String parentPath  = new File(pathStack.peek()).getParent();
		Log.e("goUp", "" + String.valueOf(parentPath));
		if(!settings.getBoolean(SettingsActivity.KEY_SETTING_UPTOROOT, false)){
			if(parentPath.equals("/")) return false;
		}
		if(parentPath != null){
			pathStack.pop();
			refreshCurrentDirContent();
			return true;
		}
		return false;
	}
	public boolean gotoDir(String path){
		File des = new File(path);
		if (des!= null && des.canRead() && des.isDirectory()) {
			pathStack.add(path);
			refreshCurrentDirContent();
			return true;
		}
		return false;
	}
	public void refresh(){
		refreshCurrentDirContent();
	}
	public String getCurrentPath(){
		return pathStack.peek();
	}
	public int copyToDirectory(String old, String newDir) {
		File old_file = new File(old);
		File temp_dir = new File(newDir);
		byte[] data = new byte[BUFFER];
		int read = 0;
		
		if(old_file.isFile() && temp_dir.isDirectory() && temp_dir.canWrite()){
			String file_name = old.substring(old.lastIndexOf("/"), old.length());
			File cp_file = new File(newDir + file_name);

			try {
				BufferedOutputStream o_stream = new BufferedOutputStream(
												new FileOutputStream(cp_file));
				BufferedInputStream i_stream = new BufferedInputStream(
											   new FileInputStream(old_file));
				
				while((read = i_stream.read(data, 0, BUFFER )) != -1)
					o_stream.write(data, 0, read);
				
				o_stream.flush();
				i_stream.close();
				o_stream.close();
				
			} catch (FileNotFoundException e) {
				Log.e("FileNotFoundException", e.getMessage());
				return -1;
				
			} catch (IOException e) {
				Log.e("IOException", e.getMessage());
				return -1;
			}
			
		}else if(old_file.isDirectory() && temp_dir.isDirectory() && temp_dir.canWrite()) {
			String files[] = old_file.list();
			String dir = newDir + old.substring(old.lastIndexOf("/"), old.length());
			int len = files.length;
			
			if(!new File(dir).mkdir())
				return -1;
			
			for(int i = 0; i < len; i++)
				copyToDirectory(old + "/" + files[i], dir);
			
		} else if(!temp_dir.canWrite())
			return -1;
		
		return 0;
	}
	public String getCurrentDir() {
		return pathStack.peek();
	}
	private void search_file(String dir, String fileName, ArrayList<String> n) {
		File root_dir = new File(dir);
		String[] list = root_dir.list();
		
		if(list != null && root_dir.canRead()) {
			int len = list.length;
			
			for (int i = 0; i < len; i++) {
				File check = new File(dir + "/" + list[i]);
				String name = check.getName();
					
				if(check.isFile() && name.toLowerCase().
										contains(fileName.toLowerCase())) {
					n.add(check.getPath());
				}
				else if(check.isDirectory()) {
					if(name.toLowerCase().contains(fileName.toLowerCase()))
						n.add(check.getPath());
					
					else if(check.canRead() && !dir.equals("/"))
						search_file(check.getAbsolutePath(), fileName, n);
				}
			}
		}
	}
	public long getDirSize(String path) {
		get_dir_size(new File(path));

		return fileSize;
	}
	private void get_dir_size(File path) {
		File[] list = path.listFiles();
		int len;
		
		if(list != null) {
			len = list.length;
			
			for (int i = 0; i < len; i++) {
				try {
					if(list[i].isFile() && list[i].canRead()) {
						fileSize += list[i].length();

					} else if(list[i].isDirectory() && list[i].canRead() && !isSymlink(list[i])) { 
						get_dir_size(list[i]);
					}
				} catch(IOException e) {
					Log.e("IOException", e.getMessage());
				}
			}
		}
	}
	private static boolean isSymlink(File file) throws IOException {
		File fileInCanonicalDir = null;
		if (file.getParent() == null) {
			fileInCanonicalDir = file;
		} else {
			File canonicalDir = file.getParentFile().getCanonicalFile();
			fileInCanonicalDir = new File(canonicalDir, file.getName());
		}
		return !fileInCanonicalDir.getCanonicalFile().equals(fileInCanonicalDir.getAbsoluteFile());
	}
	public ArrayList<String> searchInDirectory(String dir, String pathName) {
		ArrayList<String> names = new ArrayList<String>();
		search_file(dir, pathName, names);

		return names;
	}
	public void extractZipFilesFromDir(String zipName, String toDir, String fromDir) {
		if(!(toDir.charAt(toDir.length() - 1) == '/'))
			toDir += "/";
		if(!(fromDir.charAt(fromDir.length() - 1) == '/'))
			fromDir += "/";
		
		String org_path = fromDir + zipName;		
		
		extractZipFiles(org_path, toDir);
	}
	public void extractZipFiles(String zip_file, String directory) {
		byte[] data = new byte[BUFFER];
		String name, path, zipDir;
		ZipEntry entry;
		ZipInputStream zipstream;
		
		if(!(directory.charAt(directory.length() - 1) == '/'))
			directory += "/";
		
		if(zip_file.contains("/")) {
			path = zip_file;
			name = path.substring(path.lastIndexOf("/") + 1, 
								  path.length() - 4);
			zipDir = directory + name + "/";
			
		} else {
			path = directory + zip_file;
			name = path.substring(path.lastIndexOf("/") + 1, 
		 			  			  path.length() - 4);
			zipDir = directory + name + "/";
		}

		new File(zipDir).mkdir();
		
		try {
			zipstream = new ZipInputStream(new FileInputStream(path));
			
			while((entry = zipstream.getNextEntry()) != null) {
				String buildDir = zipDir;
				String[] dirs = entry.getName().split("/");
				
				if(dirs != null && dirs.length > 0) {
					for(int i = 0; i < dirs.length - 1; i++) {
						buildDir += dirs[i] + "/";
						new File(buildDir).mkdir();
					}
				}
				
				int read = 0;
				FileOutputStream out = new FileOutputStream(
										zipDir + entry.getName());
				while((read = zipstream.read(data, 0, BUFFER)) != -1)
					out.write(data, 0, read);
				
				zipstream.closeEntry();
				out.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void createZipFile(String path) {
		File dir = new File(path);
		String[] list = dir.list();
		String name = path.substring(path.lastIndexOf("/"), path.length());
		String _path;
		
		if(!dir.canRead() || !dir.canWrite())
			return;
		
		int len = list.length;
		
		if(path.charAt(path.length() -1) != '/')
			_path = path + "/";
		else
			_path = path;
		
		try {
			ZipOutputStream zip_out = new ZipOutputStream(
									  new BufferedOutputStream(
									  new FileOutputStream(_path + name + ".zip"), BUFFER));
			
			for (int i = 0; i < len; i++)
				zip_folder(new File(_path + list[i]), zip_out);

			zip_out.close();
			
		} catch (FileNotFoundException e) {
			Log.e("File not found", e.getMessage());

		} catch (IOException e) {
			Log.e("IOException", e.getMessage());
		}
	}
	
	public int renameTarget(String filePath, String newName) {
		File src = new File(filePath);
		String ext = "";
		File dest;
		
		if(src.isFile())
			/*get file extension*/
			ext = filePath.substring(filePath.lastIndexOf("."), filePath.length());
		
		if(newName.length() < 1)
			return -1;
	
		String temp = filePath.substring(0, filePath.lastIndexOf("/"));
		
		dest = new File(temp + "/" + newName + ext);
		if(src.renameTo(dest))
			return 0;
		else
			return -1;
	}
	public int createFile(String path, String name){
		int len = path.length();
		if(len < 1 || len < 1)
			return -1;
		if(path.charAt(len - 1) != '/')
			path += "/";
		
		File f = new File(path+"/"+ name);
		if(!f.exists())
			try {
				f.createNewFile();
				return 1;
			} catch (IOException e) {
				return -1;
			}
		return -1;
	}
	public int createDir(String path, String name) {
		
		int len = path.length();
		
		if(len < 1 || len < 1)
			return -1;
		
		if(path.charAt(len - 1) != '/')
			path += "/";
		Log.v("createDir", "" + String.valueOf(path+name));
		File f = new File(path+name);
		if (!f.exists())
			if(f.mkdir())
				return 0;
		return -1;
	}
	public int multiDelete(ArrayList<String> l){
		int res = 0;
		for (String i : l) {
			 res += deleteTarget(i);
		}
		return res;
	}
	public int deleteTarget(String path) {
		File target = new File(path);
		
		if(target.exists() && target.isFile() && target.canWrite()) {
			target.delete();
			return 0;
		}
		
		else if(target.exists() && target.isDirectory() && target.canRead()) {
			String[] file_list = target.list();
			
			if(file_list != null && file_list.length == 0) {
				target.delete();
				return 0;
				
			} else if(file_list != null && file_list.length > 0) {
				
				for(int i = 0; i < file_list.length; i++) {
					File temp_f = new File(target.getAbsolutePath() + "/" + file_list[i]);

					if(temp_f.isDirectory())
						deleteTarget(temp_f.getAbsolutePath());
					else if(temp_f.isFile())
						temp_f.delete();
				}
			}
			if(target.exists())
				if(target.delete())
					return 0;
		}	
		return -1;
	}
	
	
	public boolean isDirectory(String name) {
		return new File(pathStack.peek() + "/" + name).isDirectory();
	}
	private void zip_folder(File file, ZipOutputStream zout) throws IOException {
		byte[] data = new byte[BUFFER];
		int read;
		
		if(file.isFile()){
			ZipEntry entry = new ZipEntry(file.getName());
			zout.putNextEntry(entry);
			BufferedInputStream instream = new BufferedInputStream(
										   new FileInputStream(file));

			while((read = instream.read(data, 0, BUFFER)) != -1)
				zout.write(data, 0, read);
			
			zout.closeEntry();
			instream.close();
		
		} else if (file.isDirectory()) {
			String[] list = file.list();
			int len = list.length;
										
			for(int i = 0; i < len; i++)
				zip_folder(new File(file.getPath() +"/"+ list[i]), zout);
		}
	}
	public void refreshCurrentDirContent() {
		nContentView.stopThumbnall();
		if (!currentDirContent.isEmpty())
			currentDirContent.clear();
		if (!imgList.isEmpty())
			imgList.clear();

		File file = new File(getCurrentDir());

		if (file.exists() && file.canRead()) {
			String[] list = file.list();
			int len = list.length;

			/* add files/folder to arraylist depending on hidden status */
			for (int i = 0; i < len; i++) {
				if (settings.getBoolean(
						SettingsActivity.KEY_SETTINGFILE_HIDDEN, false)) {
					if (list[i].toString().charAt(0) != '.'){
						currentDirContent.add(list[i]);
						if(list[i].lastIndexOf(".")!=-1){
							String ext = list[i].substring(list[i].lastIndexOf(".")+1);
							if (ext.equalsIgnoreCase("png") ||
									ext.equalsIgnoreCase("jpg") ||
									ext.equalsIgnoreCase("jpeg")|| 
									ext.equalsIgnoreCase("gif") ||
									ext.equalsIgnoreCase("tiff")) {
								imgList.add(list[i]);
							}
						}
					}

				} else {
					currentDirContent.add(list[i]);
					if(list[i].lastIndexOf(".")!=-1){
						String ext = list[i].substring(list[i].lastIndexOf(".")+1);
						if (ext.equalsIgnoreCase("png") ||
								ext.equalsIgnoreCase("jpg") ||
								ext.equalsIgnoreCase("jpeg")|| 
								ext.equalsIgnoreCase("gif") ||
								ext.equalsIgnoreCase("tiff")) {
							imgList.add(list[i]);
						}
					}
				}
			}
			// sorting options
			switch (settings.getInt(SettingsActivity.KEY_SETTING_SORTING, 1)) {

			case SettingsActivity.SORT_BYNAME:
				Object[] tt = currentDirContent.toArray();
				currentDirContent.clear();

				Arrays.sort(tt, alph);

				for (Object a : tt) {
					currentDirContent.add((String) a);
				}
				break;

			case SettingsActivity.SORT_BYSIZE:
				int index = 0;
				Object[] size_ar = currentDirContent.toArray();
				String dir = getCurrentDir();

				Arrays.sort(size_ar, size);

				currentDirContent.clear();
				for (Object a : size_ar) {
					if (new File(dir + "/" + (String) a).isDirectory())
						currentDirContent.add(index++, (String) a);
					else
						currentDirContent.add((String) a);
				}
				break;

			case SettingsActivity.SORT_BYDATE:
				int dirindex = 0;
				Object[] type_ar = currentDirContent.toArray();
				String current = getCurrentDir();

				Arrays.sort(type_ar, date);
				currentDirContent.clear();

				for (Object a : type_ar) {
					if (new File(current + "/" + (String) a).isDirectory())
						currentDirContent.add(dirindex++, (String) a);
					else
						currentDirContent.add((String) a);
				}
				break;
			case SettingsActivity.SORT_BYEXTENSION:
				int i = 0;
				Object[] type_arr = currentDirContent.toArray();
				String currents = getCurrentDir();
				Arrays.sort(type_arr, extension);
				currentDirContent.clear();

				for (Object a : type_arr) {
					if (new File(currents + "/" + (String) a).isDirectory())
						currentDirContent.add(i++, (String) a);
					else
						currentDirContent.add((String) a);
				}
				break;
			
			}

		}
	}
	public ArrayList<String> getCurrentDirContent() {
		return currentDirContent;
	}
	public ArrayList<String> getImgList() {
		return imgList;
	}
	private static final Comparator alph = new Comparator<String>() {
		@Override
		public int compare(String arg0, String arg1) {
			int result = arg0.toLowerCase().compareTo(arg1.toLowerCase());
			if (!sortAcescing) {
				result = result * (-1);
			}
			return result;
		}
	};

	private final Comparator size = new Comparator<String>() {
		@Override
		public int compare(String arg0, String arg1) {
			String dir = getCurrentDir();
			Long first = new File(dir + "/" + arg0).length();
			Long second = new File(dir + "/" + arg1).length();

			int result = first.compareTo(second);
			if (!sortAcescing) {
				result = result * (-1);
			}
			return result;
		}
	};
	private final Comparator date = new Comparator<String>() {
		@Override
		public int compare(String arg0, String arg1) {
			String dir = getCurrentDir();
			Date first = new Date(new File(dir + "/" + arg0).lastModified());
			Date second = new Date(new File(dir + "/" + arg1).lastModified());

			int result = first.compareTo(second);
			if (!sortAcescing) {
				result = result * (-1);
			}
			return result;
		}
	};

	private final Comparator extension = new Comparator<String>() {
		@Override
		public int compare(String arg0, String arg1) {
			String ext = null;
			String ext2 = null;
			int ret;

			try {
				ext = arg0.substring(arg0.lastIndexOf(".") + 1, arg0.length())
						.toLowerCase();
				ext2 = arg1.substring(arg1.lastIndexOf(".") + 1, arg1.length())
						.toLowerCase();

			} catch (IndexOutOfBoundsException e) {
				return 0;
			}

			int result = ext.compareTo(ext2);
			if (!sortAcescing) {
				result = result * (-1);
			}
			if (result == 0)
				return arg0.toLowerCase().compareTo(arg1.toLowerCase());

			return result;
		}
	};
}
