// @author nitterhe

package com.scrab5.network.test;

import org.junit.jupiter.api.Test;
import com.scrab5.network.NetworkError;
import com.scrab5.network.NetworkError.NetworkErrorType;

class ServerTest {

  @Test
  void errorTest() {
    NetworkError error = new NetworkError(NetworkErrorType.COMMUNICATION);
    error.handleError();
  }
}

