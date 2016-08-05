package com.wepiao.springmvc.asynch.teststub.config;
import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.stereotype.Component;

//@Component
public class MyEmbeddedServletContainerCustomizer implements EmbeddedServletContainerCustomizer{

    private final Logger logger = LoggerFactory.getLogger(MyEmbeddedServletContainerCustomizer.class);
    
    @Value("${servlet.container.maxThreads}")
    private int MAX_THREADS;
    
    public void customize(ConfigurableEmbeddedServletContainer factory) {
        if (factory instanceof TomcatEmbeddedServletContainerFactory) {
            customizeTomcat((TomcatEmbeddedServletContainerFactory)factory);
        }
    }
    
    public void customizeTomcat(TomcatEmbeddedServletContainerFactory factory) {
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            
            public void customize(Connector connector) {
                Object defaultMaxThreads = connector.getAttribute("maxThreads");
                connector.setAttribute("maxThreads", MAX_THREADS);
                logger.info("Changed Tomcat connector maxThreads from " + defaultMaxThreads +" to " + MAX_THREADS);
            }
        });
    }

}
