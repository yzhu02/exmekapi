package com.exmek.commons.net;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Protocol {
   SMTP(25),
   SMTPS(465),
   SSL(465),
   TLS(587);

   private Integer defaultPort;
   private static Map<Integer, Protocol> defaultPort2Protocol = new HashMap<>();

   private Protocol(Integer defaultPort) {
      this.defaultPort = defaultPort;
   }

   public Integer getDefaultPort() {
      return this.defaultPort;
   }

   public static Protocol byDefaultPort(Integer defaultPort) {
      return defaultPort2Protocol.get(defaultPort);
   }

   static {
      Arrays.stream(values()).forEach((p) -> {
         defaultPort2Protocol.put(p.getDefaultPort(), p);
      });
   }
}
