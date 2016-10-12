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
package ca.tulip.sinope.core;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import ca.tulip.sinope.util.ByteArrayConverter;

public class ByteArrayConverterTest {

    @Test
    public void convertTest() {
        byte[] expected = new byte[] { 00, 0x1e, (byte) (0xc0 & 0xff), (byte) (0xc1 & 0xff), (byte) (0xdd & 0xff), 0x79,
                (byte) (0xa0 & 0xff), (byte) (0xa8 & 0xff) };

        ByteArrayConverter bac = new ByteArrayConverter();
        byte[] actual = bac.convert("001e-c0c1-dd79-a0a8");
        assertArrayEquals("failure - byte arrays not same", expected, actual);

    }

}