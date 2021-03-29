// @author nitterhe

package com.scrab5.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import com.scrab5.network.messages.GetServerDataMessage;
import com.scrab5.network.messages.Message;
import com.scrab5.network.messages.MessageType;

public class Client {

  public final int clientPort = 2345;
  private InetAddress ipAddress;
  private String ip;
  private final String username;
  private ClientThread clientThread;
  private ArrayList<ServerData> serverList;

  public Client(String username) {
    this.username = username;
    serverList = new ArrayList<ServerData>();
    try {
      this.ipAddress = InetAddress.getLocalHost();
      this.ip = ipAddress.getHostAddress();
    } catch (Exception e) {
      // Exception handling required
    }
  }


  // @author from Stackoverflow
  // https://stackoverflow.com/questions/24082077/java-find-server-in-network
  public ArrayList<ServerData> searchServers() {
    int port = 1234;
    for (int j = 1; j < 255; j++) {
      for (int k = 1; k < 255; k++) {
        final String ip4 = "192.168." + j + "." + k;
        Thread connectionCheck = new Thread(new Runnable() {
          public void run() {
            try {
              InetAddress serverCheck = InetAddress.getByName(ip4);
              if (!serverCheck.isReachable(10000))
                return;

              Socket getServerDataSocket = new Socket(ip4, port);
              ObjectOutputStream out =
                  new ObjectOutputStream(getServerDataSocket.getOutputStream());
              ObjectInputStream in = new ObjectInputStream(getServerDataSocket.getInputStream());
              out.writeObject(new GetServerDataMessage(username));
              out.flush();
              out.reset();
              Message m = (Message) in.readObject();
              if (m.getType() == MessageType.DEFAULT) {
                ServerData serverdata = new ServerData(m.getSender() + "'s Server", ip4, port);
                addServerToServerList(serverdata);
              }
            } catch (Exception e) {
              // requires Exception handling
            }
          }
        });
      }
    }
    return this.getServerList();
  }

  private synchronized void addServerToServerList(ServerData serverdata) {
    serverList.add(serverdata);
  }

  public synchronized ArrayList<ServerData> getServerList() {
    return this.serverList;
  }

  public boolean connectToServer(ServerData serverdata) {
    clientThread = new ClientThread(this);
    clientThread.connectToServer(serverdata);
    return true;
  }

  public String getUsername() {
    return this.username;
  }

  public String getIp() {
    return this.ip;
  }

  public InetAddress getIpAdress() {
    return this.ipAddress;
  }

  public class ServerData {
    public String servername;
    public String ip4;
    public int port;

    public ServerData(String servername, String ip4, int port) {
      this.servername = servername;
      this.ip4 = ip4;
      this.port = port;
    }
  }
}
