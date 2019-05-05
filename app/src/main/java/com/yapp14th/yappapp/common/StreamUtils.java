package com.yapp14th.yappapp.common;

import android.content.Context;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {

    public static byte[] getBytes(Context context, Uri uri) throws IOException {
        InputStream iStream = context.getContentResolver().openInputStream(uri);
        try {
            return getBytes(iStream);
        }
        finally {
            // close the stream
            try {
                iStream.close();
            }
            catch (IOException ignored) { /* do nothing */ }
        }
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {

        byte[] bytesResult = null;
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        try {
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            bytesResult = byteBuffer.toByteArray();
        }
        finally {
            // close the stream
            try {
                byteBuffer.close();
            }
            catch (IOException ignored) { /* do nothing */ }
        }
        return bytesResult;
    }

}
