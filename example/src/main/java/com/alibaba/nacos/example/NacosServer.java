package com.alibaba.nacos.example;

import com.alibaba.nacos.Nacos;
import com.alibaba.nacos.core.utils.SystemUtils;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author nkorange
 * @since 1.0.1
 */
public class NacosServer {

    public static NacosServer newServer() {
        return new NacosServer();
    }

    private ConfigurableApplicationContext applicationContext;

    private boolean started = false;

    /**
     * Start a local Nacos server
     */
    public void start() {
        start(new NacosServerConfig());
    }

    public void start(NacosServerConfig serverConfig) {

        if (started) {
            throw new RuntimeException("Nacos server already started!");
        }

        // Set standalone mode for Nacos:
        System.setProperty("nacos.standalone", "true");

        // Set server configuration of Nacos:
        List<String> args = new ArrayList<String>(8);
        args.add("--server.port=" + serverConfig.getPort());

        // Run the server:
        Nacos.main(args.toArray(new String[args.size()]));

        applicationContext = Nacos.getApplicationContext();
        started = true;
    }

    /**
     * Stop Nacos server
     */
    public void stop() {
        if (applicationContext != null) {
            applicationContext.close();
            started = false;
        }
    }

    /**
     * Stop Nacos server and clean up the resources
     */
    public void close() {
        String nacosHome = SystemUtils.NACOS_HOME;
        // Clear all logs:
        String logDir = nacosHome + File.separator + "logs";
        deleteDir(new File(logDir));

        // Clear all data:
        String dataDir = nacosHome + File.separator + "data";
        deleteDir(new File(dataDir));
    }

    private static boolean deleteDir(File file) {

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteDir(f);
                }
            }
        }
        return file.delete();
    }


    public static void main(String[] args) throws Exception {

        NacosServer server1 = NacosServer.newServer();

        server1.start();

        NacosServer server2 = NacosServer.newServer();

        NacosServerConfig serverConfig = new NacosServerConfig();
        serverConfig.setPort(8888);

        server2.start(serverConfig);


        System.out.println(System.getProperty("user.home"));

        TimeUnit.SECONDS.sleep(10L);

        server1.stop();

        TimeUnit.SECONDS.sleep(10L);

        server2.stop();
    }

}
