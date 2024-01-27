package com.ispan.mingle.projmingle.util;

import java.util.Base64;

/**
 * 僅供圖片(image)類
 */
public class BaseUtil {
    public static String byteToBase64(String contentType, byte[] bytes) {
        if (contentType != null && bytes != null) {
            return "data:image/" + contentType + ";base64," + Base64.getEncoder().encodeToString(bytes);
        }
        return null;
    }
}
