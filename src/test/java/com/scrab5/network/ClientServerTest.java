/**
 * Class for testing the client and server communication. No separate classes for ClientTest and
 * ServerTest are implemented since the Client and Server methods need to be called in this
 * TestClass anyway.
 * 
 * @author nitterhe
 */

package com.scrab5.network;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.net.InetAddress;
import org.junit.Test;

public class ClientServerTest {

  Client testClient;
  Server testServer;

  @Test
  public void ClientTest() {
    try {
      testClient = new Client("clientTest");

      assertEquals(testClient.getIp(), InetAddress.getLocalHost().getHostAddress());
      assertEquals(testClient.getUsername(), "clientTest");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void ServerTest() {
    testServer = new Server("serverTest", 4);

    assertEquals(testServer.getHost(), "serverTest");
    assertNotNull(testServer.getServerSocket());

    try {
      assertEquals(testServer.getIp(), InetAddress.getLocalHost().getHostAddress());
    } catch (Exception e) {
      e.printStackTrace();
    }
    testServer.shutDownServer();
  }

  @Test
  public void ClientThreadTest() {
    try {
      testClient = new Client("networkTest");
      testClient.hostServer(4);
      testServer = testClient.getHostedServer();

      assertNotNull(testServer.getClients().get("networkTest"));
      assertNotNull(testServer);
      assertNotNull(testClient.getClientThread());

      testClient.sendChatMessage("hallo");

      testServer.shutDownServer();
      testClient.getClientThread().stopThread();

      // testClient.disconnectFromServer();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

