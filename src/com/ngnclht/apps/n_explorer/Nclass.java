package com.ngnclht.apps.n_explorer;

public class Nclass{
	public Nclass(){
		
	}
	public static class ButtonInfo{
		public int imageRid;
		public int textRid;
		
		public boolean disableAble;
		public boolean disableOnload;
		public int disableImageID;
		
		
		public boolean activeAble;
		public boolean activeOnload;
		public int activeImageID;
		
		public String name;
		public ButtonInfo(){
			
		}
		public ButtonInfo(String n){
			name = n;
		}
		public ButtonInfo(String n,int imgID, int txtId, int disableAble,int disableOnload, int disableImgID,
										      			 int activeAble,int activeOnload, int activeImgID){
			name 				= n;
			imageRid 			= imgID;
			textRid 	 		= txtId;
			this.disableAble 	= (disableAble == 1) ? true: false;
			this.disableImageID = disableImgID;
			this.disableOnload 	= (disableOnload == 1) ? true: false;
			
			this.activeAble 	= (activeAble == 1) ? true: false;
			this.activeImageID 	= activeImgID;
			this.activeOnload 	= (activeOnload == 1) ? true: false;
		}
	}
	public static class FolderStyle{
		public String name;
		public int def;
		public int doc;
		public int music;
		public int photo;
		public int video;
		public FolderStyle(){
			
		}
		public FolderStyle(String name, int def, int doc, int music,int photo, int video){
			this.def = def;
			this.doc = doc;
			this.music = music;
			this.photo = photo;
			this.video = video;
		}
	}
}