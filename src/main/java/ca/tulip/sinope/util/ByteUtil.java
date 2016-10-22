/**
 * Copyright (c) 2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * @author Pascal Larin
 * https://github.com/chaton78
 */
package ca.tulip.sinope.util;

import java.util.Arrays;

/**
 * The Class Byte.
 */
public class ByteUtil {

    /**
     * Reverse.
     *
     * @param array to reverse
     * @return the reserved in byte[]
     */
    public static byte[] reverse(byte[] array) {

        if (array == null) {
            return null;
        }
        byte[] r = Arrays.copyOf(array, array.length);
        int i = 0;
        int j = r.length - 1;
        byte tmp;
        while (j > i) {
            tmp = r[j];
            r[j] = r[i];
            r[i] = tmp;
            j--;
            i++;
        }
        return r;
    }

    /**
     * To string.
     *
     * @param buf the buf
     * @return the string
     */
    public static String toString(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            sb.append(String.format("0x%02X ", b));
        }
        return sb.toString();
    }

}
