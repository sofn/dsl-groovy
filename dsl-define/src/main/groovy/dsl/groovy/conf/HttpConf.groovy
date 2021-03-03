package dsl.groovy.conf

/**
 * @author lesofn
 * @version 1.0 2021-03-02 11:59
 */
class HttpConf {
    private int poolSize = 16
    private int connectTimeout = 1000
    private int socketTimeout = 100
    private int tryTime = 1
    private String params = ""
    private String url
    private String method = "GET"

    HttpConf poolSize(int poolSize) {
        this.poolSize = poolSize
        return this
    }

    HttpConf ponnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout
        return this
    }

    HttpConf socketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout
        return this
    }

    HttpConf params(String params) {
        this.params = params
        return this
    }

    HttpConf url(String url) {
        this.url = url
        return this
    }

    HttpConf method(String method) {
        this.method = method
        return this
    }

    HttpConf tryTime(int tryTime) {
        this.tryTime = tryTime
        return this
    }

    int getPoolSize() {
        return poolSize
    }

    int getConnectTimeout() {
        return connectTimeout
    }

    int getSocketTimeout() {
        return socketTimeout
    }

    int getTryTime() {
        return tryTime
    }

    String getParams() {
        return params
    }

    String getUrl() {
        return url
    }

    String getMethod() {
        return method
    }
}
