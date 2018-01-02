package com.example.servlet;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;

@WebFilter(asyncSupported=true,value={"/a","/b"},filterName="HelloFilter",initParams={@WebInitParam(name="filter",value="value")},dispatcherTypes={DispatcherType.REQUEST,DispatcherType.FORWARD})
public class HelloFilter implements Filter {
	ServletContext context;
	@Override
	public void destroy() {
		context.log("destroy Filter.");
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		context.log("filter processing ...");
		System.out.println("filter processing ...");
		arg0.setAttribute("key", "throughing filter...");
		HttpServletRequest req = (HttpServletRequest)arg0;
		arg1.getWriter().println("this is from filter. [SessionId:"+req.getRequestedSessionId()+"]");
		arg2.doFilter(arg0, arg1);
		
		/*e.g. authentication
		if(req.getSession().getAttribute("authorized") != null){
			arg2.doFilter(arg0, arg1);
		}else{
			//forward to login servlet.
			arg0.getRequestDispatcher("login_servlet").forward(arg0, arg1);;
		}*/

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		context= arg0.getServletContext();
		context.log("initial Filter.");
	}

}
