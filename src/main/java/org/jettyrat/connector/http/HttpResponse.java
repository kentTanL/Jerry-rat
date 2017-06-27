package org.jettyrat.connector.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.jettyrat.net.httpserver.Constants;


public class HttpResponse implements HttpServletResponse {
    private static final int BUFFER_SIZE = 1024;
    private HttpRequest request;
    private OutputStream output;
    private PrintWriter writer;
    
    public HttpResponse(OutputStream output) {
        this.output = output;
    }
    
    public void setReqeust(HttpRequest request) {
        this.request = request;
    }
    
    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;

        try {
            File file = new File(Constants.WEB_ROOT, request.getUri());
            if (file.exists()) {
                fis = new FileInputStream(file);
                int ch = -1;
                while ((ch = fis.read(bytes, 0, BUFFER_SIZE)) != -1) {
                    output.write(bytes, 0, ch);
                }
            } else { // file not found
                String errorMessage = "HTTP/1.1 404 File Not Foud\r\n" + "Content-Type: text/html\r\n"
                        + "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>";
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
    
    @Override
    public void setHeader(String string, String string2) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getCharacterEncoding() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getContentType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        writer = new PrintWriter(output, true);
        return writer;
    }

    @Override
    public void setCharacterEncoding(String charset) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setContentLength(int len) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setContentLengthLong(long len) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setContentType(String type) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setBufferSize(int size) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getBufferSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resetBuffer() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isCommitted() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setLocale(Locale loc) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Locale getLocale() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addCookie(Cookie cookie) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean containsHeader(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String encodeURL(String url) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String encodeRedirectURL(String url) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String encodeUrl(String url) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String encodeRedirectUrl(String url) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void sendError(int sc) throws IOException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void sendRedirect(String location) throws IOException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDateHeader(String name, long date) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addDateHeader(String name, long date) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addHeader(String name, String value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setIntHeader(String name, int value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addIntHeader(String name, int value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setStatus(int sc) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setStatus(int sc, String sm) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getStatus() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getHeader(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<String> getHeaders(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<String> getHeaderNames() {
        // TODO Auto-generated method stub
        return null;
    }


}
