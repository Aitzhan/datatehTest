import com.aitzhan.datatehTest.rest.HelloWorldService;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.util.StringParser;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ClientBinding;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.net.URI;

/**
 * Created by Aitzhan on 30.05.2016.
 */
public class HelloWorldServiceTests {
    public static final String BASE_URI = "http://localhost:8480/datatehTest-1.0-SNAPSHOT/rest/";
    private HttpServer server;

    @Before
    public void setUp() {
        final ResourceConfig rc = new ResourceConfig(HelloWorldService.class);
        server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    @After
    public void testDown() {
        server.shutdown();
    }

    @Test
    public void testService() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(BASE_URI).path("hello/Aitzhan");

        String response = target.request().get(String.class);

        Assert.assertEquals(response, "Jersey say : Aitzhan");
    }
}
