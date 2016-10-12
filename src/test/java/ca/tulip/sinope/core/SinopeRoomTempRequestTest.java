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

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

import ca.tulip.sinope.core.appdata.SinopeRoomTempData;
import ca.tulip.sinope.util.ByteUtil;

public class SinopeRoomTempRequestTest {

    @Test
    public void requestToByteTest() {
        byte[] expected = { 0x55, 0x00, 0x16, 0x00, 0x40, 0x02, 0x78, 0x56, 0x34, 0x12, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x44, 0x04, 0x00, 0x00, 0x04, 0x03, 0x02, 0x00, 0x00, 0x29 };
        System.out.println(ByteUtil.toString(expected));
        SinopeDataReadRequest dr = new SinopeDataReadRequest(new byte[] { 0x12, 0x34, 0x56, 0x78 },
                new byte[] { 0, 0, 0x04, 0x44 }, new SinopeRoomTempData());
        byte[] actual = dr.getPayload();
        System.out.println(ByteUtil.toString(actual));
        assertArrayEquals("failure - byte arrays not same", expected, actual);

    }

    @Test
    public void answerMoreByteToObjectTest() throws IOException {
        byte[] expected = { 0x55, 0x00, 0x0E, 0x00, 0x41, 0x02, 0x78, 0x56, 0x34, 0x12, 0x00, 0x00, 0x01, 0x44, 0x04,
                0x00, 0x00, 0x00, 0x0D };

        ByteArrayInputStream is = new ByteArrayInputStream(expected);

        SinopeDataReadAnswer dr = new SinopeDataReadAnswer(is, new SinopeRoomTempData());

        assertArrayEquals(dr.getSrcDeviceId(), new byte[] { 0, 0, 0x04, 0x44 });
        assertEquals(dr.getMore(), 1);
        byte[] actual = dr.getPayload();
        System.out.println(ByteUtil.toString(actual));

    }

    @Test
    public void answerByteToObjectTest() throws IOException {
        byte[] expected = { 0x55, 0x00, 0x15, 0x00, 0x41, 0x02, 0x78, 0x56, 0x34, 0x12, 0x0A, 0x01, 0x00, 0x44, 0x04,
                0x00, 0x00, 0x07, 0x03, 0x02, 0x00, 0x00, 0x02, (byte) (0x82 & 0xFF), 0x07, 0x43 };

        ByteArrayInputStream is = new ByteArrayInputStream(expected);

        SinopeDataReadAnswer dr = new SinopeDataReadAnswer(is, new SinopeRoomTempData());

        assertArrayEquals(dr.getSrcDeviceId(), new byte[] { 0, 0, 0x04, 0x44 });
        assertEquals(dr.getMore(), 0);
        byte[] actual = dr.getPayload();
        System.out.println(ByteUtil.toString(actual));

    }

}
