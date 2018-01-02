package com.example.servlet.protocolupgrade;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported=true,value={"/protocolupgrade"},name="HelloProtocolUpgradeServlet")
public class HelloProtocolUpgradeServlet extends HttpServlet {

	/**
	 * The purpose of this Servlet is to implement protocol upgrade, e.g. zg protocol.
	 * after that do some data transformation between client and server,
	 * the connection will be closed until all data has been transferred to client side.
	 * the connection close will be launched by server side, otherwise server side will raise EOF exception.
	 */
	private static final long serialVersionUID = -4589837146788007869L;
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String upgrade = request.getHeader("Connection");
		String protocol = request.getHeader("Upgrade");
		System.out.println(upgrade+" "+protocol);
		if(upgrade != null && protocol != null && "Upgrade".equalsIgnoreCase(upgrade)){
			switch (protocol) {
			case "zg":
				response.setStatus(HttpServletResponse.SC_SWITCHING_PROTOCOLS);
				response.setHeader("Connection", "Upgrade");
				response.setHeader("Upgrade", "zg");
				response.setHeader("Transfer-Encoding", "chunked");
				request.upgrade(MyProtocolHandler.class);
				System.out.println("Request upgraded to MyProtocolHandler");
				break;
			default:
				response.setContentType("text/html");
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "not support protocol.");
			}
		}else{
			response.setContentType("text/html");
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "it's not protocol upgrade.");
		}
	}

//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		if (request.getHeader("Upgrade").equals("echo")) {
//			response.setStatus(HttpServletResponse.SC_SWITCHING_PROTOCOLS);
//			response.setHeader("Connection", "Upgrade");
//			response.setHeader("Upgrade", "echo");
//			request.upgrade(MyProtocolHandler.class);
//			System.out.println("Request upgraded to MyProtocolHandler");
//		}
//	}

}
