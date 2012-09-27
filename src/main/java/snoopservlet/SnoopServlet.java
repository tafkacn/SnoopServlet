package snoopservlet;

import com.google.common.base.Joiner;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Locale;
import java.util.Map.Entry;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author raymond.mak
 */
public class SnoopServlet extends HttpServlet {
    
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        
        ByteArrayOutputStream encodedResponseStream = new ByteArrayOutputStream();
        OutputStreamWriter responseWriter = new OutputStreamWriter(encodedResponseStream, "UTF-8");

        responseWriter.write("Scheme: " + request.getScheme());
        responseWriter.write("\nProtocol: " + request.getProtocol());
        responseWriter.write("\nServerName: " + request.getServerName());
        responseWriter.write("\nMethod: " + request.getMethod());
        responseWriter.write("\nContentLength: " + Integer.toString(request.getContentLength()));
        responseWriter.write("\nContentType: " + request.getContentType());
        responseWriter.write("\nRequestURI: " + request.getRequestURI());
        responseWriter.write("\nContenxtPath: " + request.getContextPath());
        responseWriter.write("\nPathInfo: " + request.getPathInfo());
        responseWriter.write("\nQueryString: " + request.getQueryString());
        responseWriter.write("\nPathTranslated: " + request.getPathTranslated());
        responseWriter.write("\nLocalAddr: " + request.getLocalAddr());
        responseWriter.write("\nLocalName: " + request.getLocalName());
        responseWriter.write("\nServerPort: " + Integer.toString(request.getServerPort()));
        responseWriter.write("\nRemoteAddr: " + request.getRemoteAddr());
        responseWriter.write("\nRemoteHost: " + request.getRemoteHost());
        responseWriter.write("\nRemotePort: " + Integer.toString(request.getRemotePort()));
        responseWriter.write("\nRemoteUser: " + request.getRemoteUser());
        responseWriter.write("\nCharacterEncoding: " + request.getCharacterEncoding());
        responseWriter.write("\nLocale: " + request.getLocale().toString());
        responseWriter.write("\nLocales:");
        if (request.getLocales() != null) {
            for (Locale locale : Collections.list(request.getLocales())) {
                responseWriter.write("\n\t" + locale.toString());
            }
        }
        responseWriter.write("\nHeaders:");
        if (request.getHeaderNames() != null) {
            for (String headerName : Collections.list(request.getHeaderNames())) {
                responseWriter.write("\n\t" + headerName + ": " + Joiner.on(",").join(Collections.list(request.getHeaders(headerName))));
            }
        }
        responseWriter.write("\nCookies:");
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                responseWriter.write("\n\t" + cookie.getName() + ": " + cookie.getValue());
            }            
        }
        responseWriter.write("\nAttributes:");
        if (request.getAttributeNames() != null) {
            for (String attributeName : Collections.list(request.getAttributeNames())) {
                responseWriter.write("\n\t" + attributeName + ": " + request.getAttribute(attributeName).toString());
            }
        }
        responseWriter.write("\nParameters:");
        if (request.getParameterMap() != null) {
            for (Entry<String, String[]> parameterMapEntry : request.getParameterMap().entrySet()) {
                responseWriter.write("\n\t" + parameterMapEntry.getKey() + ": " + Joiner.on(",").join(parameterMapEntry.getValue()));
            }
        }
        responseWriter.flush();
        response.setContentLength(encodedResponseStream.size());
        response.setContentType("text/plain;  charset=utf-8");
    
        OutputStream responseStream = null;
        try {
            responseStream = response.getOutputStream();
            encodedResponseStream.writeTo(responseStream);            
        }
        finally {
            if (responseStream != null) {
                responseStream.flush();
                responseStream.close();
            }
        }
    }
}
