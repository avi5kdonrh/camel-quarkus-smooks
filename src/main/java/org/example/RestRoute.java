package org.example;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class RestRoute extends RouteBuilder {

        @Override
        public void configure() {

                from("timer:time?period=10s")
                        .log("Polling:::")
                        .pollEnrich("file:src/main/resources?fileName=input-message.xml&noop=true&repeatCount=1&idempotent=false",1000)
                        .log(">> INPUT ${body}")
                        //.marshal().smooks("smooks.xml") // Marshal if you can represent the XML as a Java POJO or Object
                        .to("smooks:file:src/main/resources/smooks.xml")
                        .log("OUTPUT::\n${body}");

        }
}