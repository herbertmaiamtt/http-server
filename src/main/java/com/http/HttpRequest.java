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

}
