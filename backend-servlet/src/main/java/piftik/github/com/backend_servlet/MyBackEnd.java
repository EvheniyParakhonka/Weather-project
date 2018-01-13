package piftik.github.com.backend_servlet;

import com.google.appengine.repackaged.com.google.gson.Gson;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyBackEnd extends HttpServlet {
    @Override
    protected void doGet(final HttpServletRequest pRequest, final HttpServletResponse pResponse) throws ServletException, IOException {
        pResponse.setContentType("application/json");

        try{
            final String weatherMain = pRequest.getParameter("wetherMain");
            final UrlSwitcher urlSwitcher = new UrlSwitcher();
            final String urlImage = urlSwitcher.getUrl(weatherMain);
            new Gson().toJson(urlImage, pResponse.getWriter());
        }catch (final Exception ignored){
        }
    }
}
