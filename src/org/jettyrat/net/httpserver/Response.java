package org.jettyrat.net.httpserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class Response {
	private static final int BUFFER_SIZE = 1024;
	private Request request;
	private OutputStream output;
	
	public Response(OutputStream output) {
		this.output = output;
	}

	public void setRqeust(Request request) {
		this.request = request;
	}

	public void sendStaticResource() throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		
		try {
			File file = new File(HttpServer.WEB_ROOT, request.getUri()); 
			if (file.exists()) {
				fis = new FileInputStream(file);
				int ch = -1;
				while ((ch = fis.read(bytes, 0, BUFFER_SIZE)) != -1) {
					output.write(bytes, 0, ch);
				}
			} else { // file not found
				String errorMessage = "HTTP/1.1 404 File Not Foud\r\n"
						+ "Content-Type: text/html\r\n"
						+ "Content-Length: 23\r\n"
						+ "\r\n"
						+ "<h1>File Not Found</h1>";
				output.write(errorMessage.getBytes());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		
	}

}
