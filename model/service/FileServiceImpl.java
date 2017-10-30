package model.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import util.FileProperties;
import view.MainView;

public class FileServiceImpl implements FileService {
	Socket socket;
	InputStream is;
	OutputStream os;
	PrintWriter pw;

	@Override
	public boolean transFile(String gisu, String id, String abFile) throws Exception {
		connectServer();
		FileInputStream fis = null;
		FileInputStream fis2 = null;
		BufferedReader br = null;
		BufferedInputStream bis = null;
		BufferedInputStream bis2 = null;
		BufferedOutputStream bos = null;
		try {
			pw = new PrintWriter(os, true);
			fis = new FileInputStream(abFile);
			bis = new BufferedInputStream(fis);
			br = new BufferedReader(new InputStreamReader(is));
			int count = 0;

			while ((bis.read()) != -1) {
				count++;
			}
			bis.close();
			System.out.println(count);
			pw.println("301 " + gisu + " " + id + " " + count);
			System.out.println("보낸둥");
			
			bos = new BufferedOutputStream(os);
			fis2 = new FileInputStream(abFile);
			bis2 = new BufferedInputStream(fis2);
			System.out.println(br.readLine());
			int data = 0;
			while ((data = bis2.read()) != -1) {
				System.out.println("보내");
				bos.write(data);
			}
			bos.flush();
			pw.println("201 Finish");
		} finally {
			try {
				if (bos != null)
					bos.close();
				if (bis2 != null)
					bis2.close();
				if (socket != null)
					socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public boolean saveFile(String gisu, String id, String imgLoc) throws Exception {
		connectServer();
		makeFolder(gisu);
		FileOutputStream fos = null;
		BufferedReader br = null;
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		
		try {
			pw = new PrintWriter(os, true);
			br = new BufferedReader(new InputStreamReader(is));
			pw.println("401 " + gisu + " " +  id + "." + imgLoc);
			System.out.println(br.readLine());
			String readMessage = br.readLine();
			String[] readArr = readMessage.split(" ");
			String size = readArr[3];
			int intSize = Integer.parseInt(size);
			fos = new FileOutputStream("C:/image/" +gisu + "/" + id + "." + imgLoc);
			bos = new BufferedOutputStream(fos);
			bis = new BufferedInputStream(is);
			
			int result = 0;
			System.out.println(intSize);
			pw.println("200 쿼리받았음");
			while (intSize > 0) {
				result = bis.read();
				bos.write(result);
				intSize--;
				System.out.println("intSize: " + intSize + " data: " + result);
			}
			System.out.println("응답하라");
//			pw.println("202 OK");
//			System.out.println("A:" + br.readLine());
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
				if(socket!=null)
					socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public void makeFolder(String gisu) throws Exception {
		File file = new File("C:/image/" + gisu);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public void connectServer() throws Exception {
		socket = new Socket("1.237.178.84", 11003);
		is = socket.getInputStream();
		os = socket.getOutputStream();

	}

}
