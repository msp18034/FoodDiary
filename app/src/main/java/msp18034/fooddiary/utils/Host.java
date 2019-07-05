package msp18034.fooddiary.utils;

/**
 * Created by tom13 on 06/03/2018.
 * Host class to provide information of the server to send an image to.
 * Contains a static Host builder class.
 */

/**
 * Defines a Host object
 */
public class Host {
    private final String ipv4;
    //private final String dns;
    private final int port;
    //private final String route;

    private Host(HostBuilder builder) {
        this.ipv4 = builder.ipv4;
        //this.dns = builder.dns;
        this.port = builder.port;
        //this.route = builder.route;
    }

    public String getIpv4() {
        return ipv4;
    }

//    public String getDns() { return dns; }

//    public String getRoute() { return route;}

    public int getPort() {
        return port;
    }

    public String getUrl() {
        return "http://" + ipv4 + ":" + port ;
    }

    @Override
    public String toString() {
        return new StringBuilder(this.getIpv4())
//                .append(",").append(this.getDns())
                .append(",").append(this.getPort())
                .append("\n").toString();
    }

    public static class HostBuilder {
        private final String ipv4;
//        private String dns;
        private int port;
//        private String route;

        public HostBuilder(String ipv4) {
            this.ipv4 = ipv4;
        }

//        public HostBuilder withDns(String dns) {
//            this.dns = dns;
//            return this;
//        }

        public HostBuilder withPort(int port) {
            this.port = port;
            return this;
        }

//        public HostBuilder withRoute(String route) {
//            this.route = route;
//            return this;
//        }

        public Host build() {
            return new Host(this);
        }

    }
}
