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
package ca.tulip.sinope.core.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.tulip.sinope.util.ByteUtil;
import ca.tulip.sinope.util.CRC8;

/**
 * The Class SinopeFrame.
 */
abstract class SinopeFrame {

    /** The Constant logger. */
    final static Logger logger = LoggerFactory.getLogger(SinopeFrame.class);
    /** The Constant PREAMBLE. */
    protected static final byte PREAMBLE = 0x55;

    /** The Constant FRAME_CTL. */
    protected static final byte FRAME_CTL = 0x00;

    /** The Constant PREAMBLE_SIZE. */
    protected static final int PREAMBLE_SIZE = 1;

    /** The Constant FRAME_CTL_SIZE. */
    protected static final int FRAME_CTL_SIZE = 1;

    /** The Constant CRC_SIZE. */
    protected static final int CRC_SIZE = 1;

    /** The Constant SIZE_SIZE. */
    protected static final int SIZE_SIZE = 2;

    /** The Constant COMMAND_SIZE. */
    protected static final byte COMMAND_SIZE = 2;

    /** The crc 8. */
    final private CRC8 crc8 = new CRC8();

    /** The internal payload. */
    protected byte[] internal_payload;

    /**
     * Instantiates a new sinope frame.
     */
    public SinopeFrame() {

    }

    /**
     * Gets the command.
     *
     * @return the command
     */
    protected abstract byte[] getCommand();

    /**
     * Gets the frame data.
     *
     * @return the frame data
     */
    protected abstract byte[] getFrameData();

    /**
     * Gets the payload.
     *
     * @return the payload
     */
    protected abstract byte[] getPayload();

    /**
     * Gets the crc8.
     *
     * @param buffer the buffer
     * @return the crc8
     */
    protected byte getCRC8(byte[] buffer) {
        crc8.reset();
        crc8.update(buffer, 0, buffer.length - 1);
        return (byte) (crc8.getValue());
    }

    /**
     * @see java.lang.Object#toString()
     */
    /*
     * 
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        getPayload();
        sb.append(ByteUtil.toString(internal_payload));
        return sb.toString();
    }

    /**
     * Sets the payload.
     *
     * @param payload the new payload
     */
    public void setPayload(byte[] payload) {
        setInternal_payload(payload);
    }

    /**
     * Gets the internal payload.
     *
     * @return the internal payload
     */
    protected byte[] getInternal_payload() {
        return internal_payload;
    }

    /**
     * Sets the internal payload.
     *
     * @param internal_payload the new internal payload
     */
    protected void setInternal_payload(byte[] internal_payload) {
        this.internal_payload = internal_payload;
    }

}
