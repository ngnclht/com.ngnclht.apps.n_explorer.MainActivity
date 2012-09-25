package com.ngnclht.libs;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

public class NXMLParser {

	public String getXmlFromUrl(String Url) {
		String xml = null;
		try {
			// khoi tao client
			DefaultHttpClient hClient = new DefaultHttpClient();
			// khoi tao dia chi dich
			HttpPost hPost = new HttpPost(Url);
			// client thuc thi dia chi, lay ve Respone
			HttpResponse hRespone = hClient.execute(hPost);

			HttpEntity hEntity = hRespone.getEntity();

			xml = EntityUtils.toString(hEntity);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("getXmlFromUrl message", e.getMessage());
		}

		return xml;
	}
	public String getXmlFromFile(String path){
		String xml = "";
		try {
			FileReader fRead       = new FileReader(path);
			BufferedReader bReader = new BufferedReader(fRead);
			String tmp="";
			int i=0;
			while ((tmp = bReader.readLine()) != null) {
				xml = xml + tmp;
				tmp ="";
			}
			
			fRead.close();
			bReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return xml;
	}
	public Document getDomElement(String Xml) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder buider = dbf.newDocumentBuilder();
			InputSource Is = new InputSource();
			Is.setCharacterStream(new StringReader(Xml));

			doc = buider.parse(Is);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	public String getTxtNodeValue(Element element, String name) {
		NodeList n = element.getElementsByTagName(name);
		return this.getElementValue(n.item(0));
	}

	public final String getElementValue(Node element) {
		Node child;
		if (element != null) {
			if (element.hasChildNodes()) {
				for (child = element.getFirstChild(); child != null; child = child.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}
}
