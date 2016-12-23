package com.dfn.alerts.dataaccess.impl;

import com.dfn.alerts.dataaccess.api.IMultipleSocket;
import com.dfn.alerts.dataaccess.api.ISocket;
import com.dfn.alerts.dataaccess.beans.ResponseObj;
import com.dfn.alerts.exception.SocketAccessException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 1/2/13
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class MixHttpServerImpl extends DataRetriever implements ISocket, IMultipleSocket {
    private String mixURL;
    private String requestMethod;
    private String scheme;
    private String host;
    private String resourcePath;

    public String getMixURL() {
        return mixURL;
    }

    public void setMixURL(String mixURL) {
        this.mixURL = mixURL;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public Object getData(String requestData) throws SocketAccessException {
        return getData(requestData, -1);
    }


    public synchronized Object getData(String url, int timeout) {
        ResponseObj response = null;
        //Standard Request Retry Handler
        HttpRequestRetryHandler retryHandler = new StandardHttpRequestRetryHandler();

        //Set Socket time out
        timeout = (timeout > 0) ? timeout : this.getSocketTimeOut();
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(timeout).build();

        //Build the http client
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setRetryHandler(retryHandler)
                .setDefaultSocketConfig(socketConfig)
                .build();

        try {
            //Get the URI
            URI uri = new URIBuilder()
                    .setScheme(scheme)
                    .setHost(host)
                    .setPath(resourcePath)
                    .setCustomQuery(url)
                    .build();

            //Create http GET Method
            HttpGet getMethod = new HttpGet(uri);

            ResponseHandler<ResponseObj> responseHandler = new ResponseHandler<ResponseObj>() {
                @Override
                public ResponseObj handleResponse(final HttpResponse response) throws IOException {
                    StatusLine statusLine = response.getStatusLine();
                    HttpEntity entity = response.getEntity();

                    if (statusLine.getStatusCode() >= 300)
                        throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());

                    if (entity == null)
                        throw new ClientProtocolException("Response contains no content");

                    Gson gson = new GsonBuilder().create();
                    // The EntityUtils provides useful methods to read the response content.
                    // I also use the Gson lib to easily convert Json to Java objects and vise versa.
                    return gson.fromJson(EntityUtils.toString(entity), ResponseObj.class);
                }
            };

            response = httpClient.execute(getMethod, responseHandler);

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return response;
    }

    @Override
    public String getAjaxData(String requestData) throws SocketAccessException {
        return null;
    }

    @Override
    public Map<String, ResponseObj> getData(Map<String, String> requestData) throws SocketAccessException {
        return null;
    }

    @Override
    public Map<String, ResponseObj> getData(Map<String, String> requestData, int timeout) throws SocketAccessException {
        return null;
    }
}
