package msp18034.fooddiary.utils;

/**
 * Created by tom13 on 06/03/2018.
 * Factory class to provide a simple endpoint to change host destination.
 */

public class HostFactory {

    public Host createHost() {
        Host host = new Host.HostBuilder("202.45.128.135")
                    //.withDns("ec2-52-214-205-157.eu-west-1.compute.amazonaws.com")
                    .withPort(11450)
                    //.withRoute("/classifyImage/")
                    .build();
        return host;
    }
}
