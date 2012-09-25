/**
 * Java Class
 * 
 * LICENSE
 * 
 * Processing com.ngnclht.libs.nfilehelper
 * 
 * @category Java classes
 * @package com.ngnclht.libs.nfilehelper
 * @copyright Copyright (c) 2012 GiangNam - NGroup - ngnclht@yahoo.com
 * @author GiangNam (ngnclht@yahoo.com)
 * @license GNU General Public License Version 2 or later (the "GPL")
 * @version v1.0 Jul 27, 2012 - 10:50:18 AM
 */
package com.ngnclht.libs;
import java.io.File;
import java.io.FileFilter;

public class NFileFilter implements FileFilter{	
	private String  	_extension = null;
	private String  	_name	   = null;
	private boolean 	_alowDir   = false;
	private float 		_size	   = 0;
	private boolean 	_sizeBelow = false;
	
	public NFileFilter(){
		
	}
	public NFileFilter(String extension){
		this._extension 	= extension;
	}
	public NFileFilter(boolean alowDir){
		this._alowDir 		= alowDir;
	}
	public NFileFilter(float size, boolean bellow){
		this._size 			= size;
		this._sizeBelow 	= bellow;
	}
	
	
	public void setAlowDir(boolean alowDir){
		this._alowDir 		= alowDir;
	}
	public void setName(String name){
		this._name 			= name;
	}
	public void setExtension(String ex){
		this._extension 	= ex;
	}
	public void setSize(float size, boolean bellow){
		this._size 			= size;
		this._sizeBelow 	= bellow;
	}
	@Override
	public boolean accept(File pathname) {
		boolean accept 		= true;
		String filename		= pathname.getName();
		// check extension
		if(this._extension != null && accept){
			accept = (filename.endsWith('.'+_extension) || filename.endsWith('.'+_extension.toUpperCase()));
		}
		// check name
		if(this._name != null && accept){
			if(filename.indexOf(_name) == -1){
				accept = false;
			}
		}
		
		// check if directory
		if(this._alowDir){
			if(pathname.isDirectory()) accept = true;
		}
		return accept;
	}
	
}