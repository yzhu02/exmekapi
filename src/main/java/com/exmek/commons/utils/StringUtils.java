package com.exmek.commons.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public abstract class StringUtils extends org.springframework.util.StringUtils {

	public static void requireNonEmpty(String s, String message) {
      if (ObjectUtils.isEmpty(s)) {
         throw new NullPointerException(message);
      }
   }

   public static void requireAllNonEmpty(String[] ss, String message) {
      if (ObjectUtils.isEmpty(ss)) {
         throw new NullPointerException(message);
      } else {
         String[] var2 = ss;
         int var3 = ss.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            if (ObjectUtils.isEmpty(s)) {
               throw new NullPointerException(message);
            }
         }

      }
   }

   public static String joinAsStr(List<?> objs, String delimiter, String quote) {
      if (objs == null) {
         return null;
      } else {
         return CollectionUtils.isEmpty(objs) ? "" : (String)objs.stream().map((o) -> {
            return toStrQuoted(o, quote);
         }).collect(Collectors.joining(delimiter));
      }
   }

   private static String toStrQuoted(Object o, String quote) {
      String s = o.toString();
      return quote != null ? quote + s + quote : s;
   }
}
