/**
 * Copyright (c) 2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Creator: Pascal Larin
 * https://github.com/chaton78
 */
package ca.tulip.sinope.util;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

/**
 * The Class ByteArrayConverter.
 */
public class ByteArrayConverter implements IStringConverter<byte[]> {

    /**
     * @see com.beust.jcommander.IStringConverter#convert(java.lang.String)
     */
    public byte[] convert(String value) {
        value = value.replace("-", "");
        value = value.replace("0x", "");
        value = value.replace(" ", "");
        if (value.length() % 2 == 0 && value.length() > 1) {
            byte[] b = new byte[value.length() / 2];

            for (int i = 0; i < value.length(); i = i + 2) {
                b[i / 2] = (byte) Integer.parseInt(value.substring(i, i + 2), 16);
            }
            return b;
        } else {
            throw new ParameterException("Odd number of character. Each byte should be represented by two characters.");
        }
    }
}