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
import org.junit.Test;
import com.scrab5.ui.Data;
import com.scrab5.util.database.Database;

public class ClientServerTest {

  Client testClient;
  Server testServer;

  /**
   * Tests the constructor of the Client.
   * 
   * @author nitterhe
   */
  @Test
  public void ClientTest() {
    try {
      testClient = new Client("clientTest");

      assertEquals(testClient.getIp(), InetAddress.getLocalHost().getHostAddress());
      assertEquals(testClient.getUsername(), "clientTest");
      testClient.setUsername("Mike");
      assertEquals(testClient.getUsername(), "Mike");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Tests the constructor of the Server.
   * 
   * @author nitterhe
   */
  @Test
  public void ServerTest() {
    try {
      testServer = new Server("serverTest", 4, false);

      assertEquals(testServer.getHost(), "serverTest");
      assertNotNull(testServer.getServerSocket());
      assertEquals(testServer.getIp4(), InetAddress.getLocalHost().getHostAddress());

      testServer.shutDownServer();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Test method for the communication between the Server and Client. Not all methods are tested
   * since some are included in test classes of other packages (Database, UI, etc.). Also methods
   * that are called by other methods are not tested (for instance sendMessageToServer()).
   * 
   * @author nitterhe
   */
  @Test
  public void ClientServerCommuncationTest() {
    Database.reconnect();
    try {
      testClient = new Client("networkTest");
      testClient.hostServer(4);
      testServer = testClient.getHostedServer();

      this.delay();

      /**
       * Testing hosting a server.
       */
      assertTrue(testServer.getClients().containsKey("networkTest"));
      assertEquals(testServer.getClientMaximum(), 4);
      assertFalse(testServer.getStatus());

      ServerThread st = testServer.getConnections().get(testServer.getClients().get("networkTest"));
      assertTrue(testClient.getClientThread().isAlive());
      assertTrue(st.isAlive());

      /**
       * Testing connecting to a server with multiple clients.
       */
      Client testClient2 = new Client("testClient2");

      testClient2.connectToServer(testServer.getIp4());
      this.delay();
      assertTrue(testServer.getClients().containsKey("testClient2"));


      /**
       * Testing chat.
       */
      testClient.sendChatMessage("hallo ");
      this.delay();
      System.out.println(Data.getChatHistory().toString());

      /**
       * Testing kicking.
       */
      testServer.kickClient("testClient2");

      /**
       * Random method testing.
       */
      testClient.setReady(true, null);
      assertTrue(testClient.isReady());

      /**
       * Testing the data that the UI uses.
       */
      Server UIInstance = testClient.getCurrentServer();
      ServerStatistics.ClientStatistic realData =
          testServer.getServerStatistics().getServerStatistics().get("networkTest");
      ServerStatistics.ClientStatistic UIData =
          UIInstance.getServerStatistics().getServerStatistics().get("networkTest");

      assertEquals(UIInstance.getStatus(), testServer.getStatus());
      assertEquals(realData.getClientName(), UIData.getClientName());
      assertEquals(realData.getIPAddress(), UIData.getIPAddress());
      assertEquals(UIInstance.getClientCounter(), testServer.getClientCounter());
      assertEquals(testServer.getClients().get("networkTest").getUsername(),
          UIInstance.getClients().get("networkTest").getUsername());

      testClient.disconnectFromServer();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Delay needed since client and server must first exchange messages.
   * 
   * @author nitterhe
   */
  private void delay() {
    synchronized (this) {
      try {
        wait(100);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}

