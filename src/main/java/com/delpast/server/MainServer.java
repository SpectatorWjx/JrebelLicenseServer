package com.delpast.server;

import com.delpast.handler.IndexHandler;
import com.delpast.handler.JetBrainsHandler;
import com.delpast.handler.JrebelHandler;
import com.delpast.handler.ToolHandler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainServer extends AbstractHandler {

    private static Map<String, String> parseArguments(String[] args) {
        if (args.length % 2 != 0) {
            throw new IllegalArgumentException("Error in argument's length ");
        }

        Map<String, String> params = new HashMap<String, String>();

        for (int i = 0, len = args.length; i < len; ) {
            String argName = args[i++];

            if (argName.charAt(0) == '-') {
                if (argName.length() < 2) {
                    throw new IllegalArgumentException("Error at argument " + argName);
                }

                argName = argName.substring(1);
            }

            params.put(argName, args[i++]);
        }

        return params;
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> arguments = parseArguments(args);
        String port = arguments.get("p");

        if (port == null || !port.matches("\\d+")) {
            port = "8081";
        }

        Server server = new Server(Integer.parseInt(port));
        server.setHandler(new MainServer());
        server.start();

        String serverStr = "http://localhost";
		if (!"80".equals(port)) {
			serverStr = serverStr + ":" + port;
		}

        System.out.println("License Server started at " + serverStr);
        System.out.println("JetBrains Activation address was: " + serverStr + "/");
        System.out.println("JRebel 7.1 and earlier version Activation address was: " + serverStr + "/{tokenname}, with any email.");
        System.out.println("JRebel 2018.1 and later version Activation address was: " + serverStr + "/{guid}(eg:http://localhost:" + port + "/" + UUID.randomUUID().toString() + "), with any email.");

        server.join();
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        System.out.println(target);
        if (target.equals("/")) {
            IndexHandler.indexHandler(target, baseRequest, request, response);
        } else if (target.equals("/jrebel/leases")) {
            JrebelHandler.jrebelLeasesHandler(target, baseRequest, request, response);
        } else if (target.equals("/jrebel/leases/1")) {
            JrebelHandler.jrebelLeases1Handler(target, baseRequest, request, response);
        } else if (target.equals("/agent/leases")) {
            JrebelHandler.jrebelLeasesHandler(target, baseRequest, request, response);
        } else if (target.equals("/agent/leases/1")) {
            JrebelHandler.jrebelLeases1Handler(target, baseRequest, request, response);
        } else if (target.equals("/jrebel/validate-connection")) {
            JrebelHandler.jrebelValidateHandler(target, baseRequest, request, response);
        } else if (target.equals("/rpc/ping.action")) {
            JetBrainsHandler.pingHandler(target, baseRequest, request, response);
        } else if (target.equals("/rpc/obtainTicket.action")) {
            JetBrainsHandler.obtainTicketHandler(target, baseRequest, request, response);
        } else if (target.equals("/rpc/releaseTicket.action")) {
            JetBrainsHandler.releaseTicketHandler(target, baseRequest, request, response);
        } else if (target.equals("/guid")) {
            ToolHandler.guidHandler(target, baseRequest, request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

}