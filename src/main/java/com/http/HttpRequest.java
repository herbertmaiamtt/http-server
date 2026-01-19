package com.http;

public class HttpRequest extends HttpMessage{

    private String requestTarget;
    /*
    A method is a case sensitive token.
    All general purpose servers MUST support the methods GET and HEAD,
    all others methods are OPTIONAL.
    */
    private HttpMethod method;
    private String httpVersion;

    protected HttpRequest(){

    }

    public HttpMethod getMethod() {
        return method;
    }

    protected void setMethod(HttpMethod method) {
        this.method = method;
    }
}
