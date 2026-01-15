package com.httpserver;

import com.httpserver.config.Configuration;
import com.httpserver.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
*
* Driver Class for the Http Server
*
*/
public class HttpServer {

    public static void main(String[] args){

        System.out.println("Server starting");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        System.out.println("Using port: " + conf.getPort());
        System.out.println("Using WebRoot: " + conf.getWebroot());

        try {
            ServerSocket serverSocket =  new ServerSocket(conf.getPort());
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            // TODO writing
            String html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>This page was serve using a simple Java HTTP server</h1></body></html>";

            // carriage return, line feed
            final String CRLF = "\n\r"; // 13, 10

            String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                    "Content-Length: " + html.getBytes().length + CRLF + // HEADER
                            CRLF +
                            html +
                            CRLF + CRLF;

            outputStream.write(response.getBytes());

            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
