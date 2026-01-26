package com.http;

import java.util.HashMap;
import java.util.Set;

public class HttpRequest extends HttpMessage{

    private String requestTarget;
    /*
    A method is a case sensitive token.
    All general purpose servers MUST support the methods GET and HEAD,
    all others methods are OPTIONAL.
    */
    private HttpMethod method;
    private String originalHttpVersion; // literal from the request
    private HttpVersion bestCompatibleHttpVersion;

    public HttpRequest(){

    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpVersion getBestCompatibleHttpVersion() {
        return bestCompatibleHttpVersion;
    }

    public String getOriginalHttpVersion() {
        return originalHttpVersion;
    }

    public String getRequestTarget(){
        return requestTarget;
    }

    protected void setMethod(String methodName) throws HttpParsingException {
        for(HttpMethod method: HttpMethod.values()){
            if(methodName.equals(method.name())){
                this.method = method;
                return;
            }
        }
        throw new HttpParsingException(
                HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED
        );
    }

    protected void setRequestTarget(String requestTarget) throws HttpParsingException {
        if(requestTarget == null || requestTarget.length() == 0){
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
        }
        this.requestTarget = requestTarget;
    }

    void setHttpVersion(String originalHttpVersion) throws BadHttpVersionException, HttpParsingException {
        this.originalHttpVersion = originalHttpVersion;
        this.bestCompatibleHttpVersion = HttpVersion.getBestCompatibleVersion(originalHttpVersion);
        if(this.bestCompatibleHttpVersion == null){
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED);
        }
    }

}
