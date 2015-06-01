package com.yinhe.susproject.download;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yinhe.susproject.data.PackagefileRepository;
import com.yinhe.susproject.model.Packagefile;

  
/** 
 * Servlet implementation class ServletDownload 
 */  
@WebServlet(asyncSupported = true, urlPatterns = { "/ServletDownload" })  
public class ServletDownload extends HttpServlet {  
    private static final long serialVersionUID = 1L;  
    @Inject
    private PackagefileRepository repository;  
	@Inject
	private Logger log;
   
	private byte[] contentInBytes=null;
    /** 
     * @see HttpServlet#HttpServlet() 
     */  
    public ServletDownload() {  
    	
        super();  
        // TODO Auto-generated constructor stub  
    }  
  @Override
public void init() throws ServletException {
	// TODO Auto-generated method stub
	  log.info("ServletDownload init  new 10M cache");
	  contentInBytes = new byte[1024*2014*100]; 
	super.init();
}
  @Override
public void destroy() {
	// TODO Auto-generated method stub
	log.info("ServletDownload destroy  ");
	super.destroy();
}
    /** 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) 
     */  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        // TODO Auto-generated method stub  
          
    	try{
	        //获得请求文件名     
	        String fileid = request.getParameter("id");  
	        Packagefile pfile = null;
	        System.out.println("fileid="+fileid);  
	        Long id =Long.parseLong(fileid);
	        pfile = repository.findById(id);
	        String fileLocation = pfile.getLocation();
	        System.out.println("filename=:"+fileLocation);
	
	        //获取目标文件的绝对路径  
	       // String fullFileName = getServletContext().getRealPath(fileLocation);  
	    
	        //读取文件  
	        if(fileLocation !=null)
	        {
	
	        	 //设置文件MIME类型  
	            response.setContentType(getServletContext().getMimeType("update.zip"));  
	            //设置Content-Disposition  
	            response.setHeader("Content-Disposition", "attachment;filename=update.zip");  
	            //读取目标文件，通过response将目标文件写到客户端  
	        	response.setContentLengthLong(pfile.getFilesize());
	        	InputStream in = new FileInputStream(fileLocation);  
	            OutputStream out = response.getOutputStream();  
	           // out.write(contentInBytes);
	              //写文件
	              
	              int len=0;
	              while((len=in.read(contentInBytes))!= -1)  
	              {   
	            	 // System.out.println("write length=:"+len);
	                  out.write(contentInBytes,0,len);  
	              }  
	             System.out.println("write length=:"+len);
	              in.close();  
	              out.close();  
	        }else
	        {
	        	ERROR(response);
	        }
      
	    }
	    catch (Exception e)
	    {
	    	ERROR(response);
	    }
    }  
  
    /** 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response) 
     */  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        // TODO Auto-generated method stub  
    }  
    private void ERROR(HttpServletResponse response) throws IOException
    {
    	PrintWriter pw = response.getWriter();	
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<title>Error</title>");
		pw.println("</head>");
		pw.println("<body><h1>");
		pw.println("404 Not Found");
		pw.println("</h1></body>");
		pw.println("</html>");
    }
  
}  