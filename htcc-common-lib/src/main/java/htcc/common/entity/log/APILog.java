package htcc.common.entity.log;

public class APILog {

    public APILog() {

    }

    public APILog(String ymd, String serviceId, String requestUrl, String method, String path, String params, String body, String response, String requestTime, String responseTime, String userIP) {
        this.ymd = ymd;
        this.serviceId = serviceId;
        this.requestUrl = requestUrl;
        this.method = method;
        this.path = path;
        this.params = params;
        this.body = body;
        this.response = response;
        this.requestTime = requestTime;
        this.responseTime = responseTime;
        this.userIP = userIP;
    }

    private String ymd;
    private String serviceId;
    private String requestUrl;
    private String method;
    private String path;
    private String params;
    private String body;
    private String response;
    private String requestTime;
    private String responseTime;
    private String userIP;

    @Override
    public String toString() {
        return requestUrl;
    }

    public String getYmd() {
        return ymd;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

}
