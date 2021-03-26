// @author nitterhe
package com.scrab5.network;



import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class Server {

  private Server server;
  private ServerSocket serverSocket;
  private boolean running;
  public static final int serverPort = 1234;
  private int playerNumber;

  public Server() {
    server = this;
    server.runLobby();
  }

  public void startServer() {
    server = new Server();
  }

  // runs the Server by setting up a ServerSocket and waits for Clients to connect
  private void runLobby() {
    running = true;
    playerNumber = 0;
    try {
      serverSocket = new ServerSocket(serverPort);
      while (running && playerNumber <= 4) {
        Socket clientSocket = serverSocket.accept();
        wait(50);
      }
    } catch (Exception e) {
      if (serverSocket.isClosed()) {
        // Bedarf Exception handling
      }
    }
  }



  // Kopiert aus altem PP Projekt, wo es aus Stackoverflow kopiert wurde
  public static String getLocalHostIp4Address() {
    String hostIP = "";
    try {

      InetAddress candidateAddress = null;
      for (Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces(); ifaces
          .hasMoreElements();) {

        NetworkInterface iface = ifaces.nextElement();

        for (Enumeration<InetAddress> inetAddrs = iface.getInetAddresses(); inetAddrs
            .hasMoreElements();) {

          InetAddress inetAddr = inetAddrs.nextElement();

          if (!inetAddr.isLoopbackAddress() && inetAddr instanceof Inet4Address) {
            if (inetAddr.isSiteLocalAddress()) {
              return inetAddr.getHostAddress();
            } else if (candidateAddress == null) {
              candidateAddress = inetAddr;
            }
          }
        }
      }
      if (candidateAddress != null) {
        return candidateAddress.getHostAddress();
      }

      InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
      if (jdkSuppliedAddress == null) {
        throw new UnknownHostException(
            "The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
      }
      return jdkSuppliedAddress.getHostAddress();
    } catch (Exception e) {
      // UnknownHostException unknownHostException =
      // new UnknownHostException("Failed to determine LAN address: " + e);
      // unknownHostException.initCause(e);
      // throw unknownHostException;
      hostIP = "unknown (failed to determine LAN address)";
    }
    return hostIP;
  }
}
