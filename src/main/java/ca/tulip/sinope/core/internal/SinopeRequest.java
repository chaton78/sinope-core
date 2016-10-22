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
package ca.tulip.sinope.core.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.tulip.sinope.util.ByteUtil;

/**
 * The Class SinopeRequest.
 */
public abstract class SinopeRequest extends SinopeFrame {

    /** The Constant HEADER_COMMAND_CRC_SIZE. */
    final static protected int HEADER_COMMAND_CRC_SIZE = SinopeFrame.PREAMBLE_SIZE + SinopeFrame.FRAME_CTL_SIZE
            + SinopeFrame.SIZE_SIZE + SinopeFrame.COMMAND_SIZE + SinopeFrame.CRC_SIZE;
    
    /** The Constant logger. */
    final static private Logger logger = LoggerFactory.getLogger(SinopeRequest.class);

    /**
     * @see ca.tulip.sinope.core.internal.SinopeFrame#getPayload()
     */
    /*
     * 
     *
     * @see ca.tulip.sinope.core.internal.SinopeFrame#getPayload()
     */
    @Override
    public byte[] getPayload() {
        if (getInternal_payload() == null) {
            byte[] command = getCommand();
            byte[] data = getFrameData();
            int len = HEADER_COMMAND_CRC_SIZE + data.length;
            byte[] buffer = new byte[len];
            ByteBuffer bb = ByteBuffer.wrap(buffer);
            bb.put(PREAMBLE);
            bb.put(FRAME_CTL);
            bb.order(ByteOrder.LITTLE_ENDIAN);
            bb.putShort((short) (SinopeFrame.COMMAND_SIZE + data.length));

            bb.put(ByteUtil.reverse(command));
            bb.put(data);

            bb.put(getCRC8(bb.array()));

            setInternal_payload(bb.array());
        }
        return getInternal_payload();
    }

    /**
     * Gets the reply answer.
     *
     * @param r the r
     * @return the reply answer
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public abstract SinopeAnswer getReplyAnswer(InputStream r) throws IOException;

    /**
     * @see ca.tulip.sinope.core.internal.SinopeFrame#setInternal_payload(byte[])
     */
    @Override
    protected void setInternal_payload(byte[] internal_payload) {
        logger.debug("Request Frame: " + ByteUtil.toString(internal_payload));
        super.setInternal_payload(internal_payload);
    }
}