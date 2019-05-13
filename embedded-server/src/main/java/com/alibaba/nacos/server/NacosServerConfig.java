package com.alibaba.nacos.server;

/**
 * Nacos server config
 *
 * @author nkorange
 * @since 1.0.1
 */
public class NacosServerConfig {

    /**
     * Running port of Nacos server
     */
    private int port = 8848;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
