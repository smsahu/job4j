package com.talentica.job4j.ui;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.talentica.job4j.api.JobManager;


@WebServlet(urlPatterns = "/joblist", loadOnStartup = 1)
public class JobListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(JobListServlet.class);
	
	@Autowired
	private JobManager jobManager;
	
	@Autowired
	protected ApplicationContext applicationContext;
	
    public JobListServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.debug("Executing JobList Servlet .... ");
		
		String name = request.getParameter("name");
		String action = request.getParameter("action");
		if(action!=null){
			jobManager.processAction(name, action);
		}
		
		request.setAttribute("jobList", jobManager.getJobList());
	    request.getRequestDispatcher("jsp/joblist.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void init(ServletConfig config) throws ServletException {
		    super.init(config);
		    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
		      config.getServletContext());
	 }
	
}
