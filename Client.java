package com.mcnz.sse;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.SseEventSource;

public class Client {

	public static void main(String[] args) {
		WebTarget target = ClientBuilder.newClient().target("server-sent-events");

		SseEventSource eventSource = SseEventSource.target(target).build();

		// EventSource#register(Consumer<InboundSseEvent>)
		// Registered event handler will print the received message.
		eventSource.register(System.out::println);

		// Subscribe to the event stream.
		eventSource.open();
	}
}

package com.mcnz.sse;




import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;

@Path("server-sent-events")
@Singleton
 class ServerSentEventsResource {
 

    private final Sse sse;
 

 
    @Inject
    public ServerSentEventsResource(Sse sse) {
        this.sse = sse;
    }
 
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void getMessageQueue(@Context SseEventSink eventSink) {
    	eventSink.send(sse.newEvent("event1"));
    }
 

 
}


<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.mcnz.microprofile</groupId>
	<artifactId>sse</artifactId>
	<version>0.0.1-SNAPSHOT</version>


<dependencies>
<!-- https://mvnrepository.com/artifact/org.eclipse.microprofile/microprofile -->
<!-- https://mvnrepository.com/artifact/javax.json.bind/javax.json.bind-api -->
<dependency>
    <groupId>javax.json.bind</groupId>
    <artifactId>javax.json.bind-api</artifactId>
    <version>1.0</version>
</dependency>

<dependency>
    <groupId>javax.json</groupId>
    <artifactId>javax.json-api</artifactId>
    <version>1.0</version>
</dependency>
<dependency>
    <groupId>org.eclipse.microprofile</groupId>
    <artifactId>microprofile</artifactId>
    <version>2.0</version>
    <type>pom</type>
</dependency>
</dependencies>


</project>
