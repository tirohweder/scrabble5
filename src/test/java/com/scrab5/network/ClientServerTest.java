/**
 * Class for testing the client and server communication. No separate classes for ClientTest and
 * ServerTest are implemented since the Client and Server methods need to be called in this
 * TestClass anyway.
 * 
 * @author nitterhe
 */

package com.scrab5.network;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.net.InetAddress;
import org.junit.Ignore;
import org.junit.Test;
import com.scrab5.util.database.Database;

public class ClientServerTest {

  Client testClient;
  Server testServer;

  @Ignore
  public void ClientTest() {
    try {
      testClient = new Client("clientTest");

      assertEquals(testClient.getIp(), InetAddress.getLocalHost().getHostAddress());
      assertEquals(testClient.getUsername(), "clientTest");

      // testClient.stopClientThread(); // to close sockets
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Ignore
  public void ServerTest() {
    testServer = new Server("serverTest", 4, false);

    assertEquals(testServer.getHost(), "serverTest");
    assertNotNull(testServer.getServerSocket());

    testServer.shutDownServer(); // to close Sockets
    try {
      assertEquals(testServer.getIp4(), InetAddress.getLocalHost().getHostAddress());
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Test
  public void ClientThreadTest() {
    Database.reconnect();
    try {
      testClient = new Client("networkTest");
      testClient.hostServer(4);
      testServer = testClient.getHostedServer();

      // assertFalse(testServer.getClients().containsKey("networkTest"));

      assertFalse(testServer.getStatus());
      assertTrue(testClient.getClientThread().isAlive());

      testClient.sendChatMessage("hallo");

      testClient.disconnectFromServer();

      // testClient.disconnectFromServer();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

