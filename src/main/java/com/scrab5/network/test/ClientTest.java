// @author nitterhe

package com.scrab5.network.test;

import org.junit.jupiter.api.Test;
import com.scrab5.network.Client;

class NetworkTest {

  @Test
  void ClientGetIpTest() {
    Client client = new Client("nitterhe");
    System.out.println(client.getIp());
  }
}

