package com.iyzico.config;

/**
 * Created by flux on 7.12.2016.
 */
import com.iyzico.filter.CORSResponseFilter;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(RequestContextFilter.class);
        packages("com.loan.rest");
        register(LoggingFilter.class);
        register(CORSResponseFilter.class);
    }
}