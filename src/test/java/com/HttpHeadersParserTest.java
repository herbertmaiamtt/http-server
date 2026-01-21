package com;

import com.http.HttpParser;
import com.http.HttpParsingException;
import com.http.HttpRequest;
import com.http.HttpStatusCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static junit.framework.Assert.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HttpHeadersParserTest {

    private HttpParser httpParser;
    private Method parseHeadersMethod;

    @BeforeAll
    public void beforeClass() throws NoSuchMethodException{
        httpParser = new HttpParser();
        Class<HttpParser> cls = HttpParser.class;
        parseHeadersMethod = cls.getDeclaredMethod("parseHeaders", InputStreamReader.class, HttpRequest.class);
        parseHeadersMethod.setAccessible(true);
    }

    @Test
    public void testSimpleSingleHeader() throws InvocationTargetException, IllegalAccessException {
        HttpRequest request = new HttpRequest();
        parseHeadersMethod.invoke(
                httpParser,
                generateSimpleSingleHeaderMessage(),
                request);
        assertEquals(1, request.getHeaderNames().size());
        assertEquals("localhost:8080", request.getHeader("host"));
    }

    @Test
    public void testMultipleHeaders() throws InvocationTargetException, IllegalAccessException {
        HttpRequest request = new HttpRequest();
        parseHeadersMethod.invoke(
                httpParser,
                generateMultipleHeadersMessage(),
                request);
        assertEquals(13, request.getHeaderNames().size());
        assertEquals("localhost:8080", request.getHeader("host"));
    }

    @Test
    public void testErrorSpaceBeforeColonHeader() throws InvocationTargetException, IllegalAccessException {
        HttpRequest request = new HttpRequest();
        try {
            parseHeadersMethod.invoke(
                    httpParser,
                    generateSpaceBeforeColonErrorHeaderMessage(),
                    request);
        } catch(InvocationTargetException e){
            if(e.getCause() instanceof HttpParsingException){
                assertEquals(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST, ((HttpParsingException)e.getCause()).getErrorCode());
            }
        }
    }

    private InputStreamReader generateSimpleSingleHeaderMessage(){
        String rawData = "Host: localhost:8080\r\n";
//                "User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:146.0) Gecko/20100101 Firefox/146.0\r\n" +
//                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n" +
//                "Accept-Language: en-US,en;q=0.5\r\n" +
//                "Accept-Encoding: gzip, deflate, br, zstd\r\n" +
//                "Sec-GPC: 1\r\n" +
//                "Connection: keep-alive\r\n" +
//                "Cookie: JSESSIONID=9ED3505AB3AE1ADCA1AE7449B0EA15C8\r\n" +
//                "Upgrade-Insecure-Requests: 1\r\n" +
//                "Sec-Fetch-Dest: document\r\n" +
//                "Sec-Fetch-Mode: navigate\r\n" +
//                "Sec-Fetch-Site: none\r\n" +
//                "Priority: u=0, i\r\n" +
//                "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        return reader;
    }

    private InputStreamReader generateMultipleHeadersMessage(){
        String rawData = "Host: localhost:8080\r\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:146.0) Gecko/20100101 Firefox/146.0\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "Accept-Encoding: gzip, deflate, br, zstd\r\n" +
                "Sec-GPC: 1\r\n" +
                "Connection: keep-alive\r\n" +
                "Cookie: JSESSIONID=9ED3505AB3AE1ADCA1AE7449B0EA15C8\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Priority: u=0, i\r\n" +
                "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        return reader;
    }

    private InputStreamReader generateSpaceBeforeColonErrorHeaderMessage(){
        String rawData = "Host : localhost:8080\r\n\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        return reader;
    }

}
