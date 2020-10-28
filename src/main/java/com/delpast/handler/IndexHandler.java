package com.delpast.handler;

import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class IndexHandler {
    public static void indexHandler(Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        // 拼接服务器地址
        String licenseUrl = request.getScheme() + "://" + request.getServerName();

        StringBuffer html = new StringBuffer("<h3>使用说明（Instructions for use）</h3>");

        html.append("<hr/>");

        html.append("<h1>Hello,This is a Jrebel & JetBrains License Server!</h1>");
        html.append("<p>License Server started at ").append(licenseUrl);
        html.append("<p>JetBrains(before 2018.1+) Activation address was: <span style='color:red'>").append(licenseUrl).append("/");
        html.append("<p>JRebel 7.1 and earlier version Activation address was: <span style='color:red'>")
                .append(licenseUrl).append("/{tokenname}")
                .append("</span>, with any email.");
        html.append("<p>JRebel 2018.1 and later version Activation address was: ")
                .append(licenseUrl).append("/{guid}")
                .append("(eg:<span style='color:red'>")
                .append(licenseUrl).append("/").append(UUID.randomUUID().toString())
                .append("</span>), with any email.");

        html.append("<hr/>");

        html.append("<h1>Hello，此地址是 Jrebel & JetBrains License Server!</h1>");
        html.append("<p>JetBrains(2018.1+版本之前)许可服务器激活地址 ").append(licenseUrl);
        html.append("<p>JetBrains激活地址是: <span style='color:red'>").append(licenseUrl).append("/");
        html.append("<p>JRebel 7.1 及旧版本激活地址: <span style='color:red'>")
                .append(licenseUrl).append("/{tokenname}")
                .append("</span>, 以及任意邮箱地址。");
        html.append("<p>JRebel 2018.1+ 版本激活地址: ")
                .append(licenseUrl).append("/{guid}")
                .append("(例如：<span style='color:red'>")
                .append(licenseUrl).append("/").append(UUID.randomUUID().toString())
                .append("</span>), 以及任意邮箱地址。");

        response.getWriter().println(html);
    }
}
