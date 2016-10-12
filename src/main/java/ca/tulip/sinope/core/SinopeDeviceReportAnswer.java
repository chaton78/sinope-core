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

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import ca.tulip.sinope.core.internal.SinopeAnswer;
import ca.tulip.sinope.util.ByteUtil;

/**
 * The Class SinopeDeviceReportAnswer.
 */
public class SinopeDeviceReportAnswer extends SinopeAnswer {

    /** The Constant STATUS_SIZE. */
    protected static final int STATUS_SIZE = 1;
    
    /** The Constant DEVICE_ID_SIZE. */
    protected static final int DEVICE_ID_SIZE = 4;

    /**
     * Instantiates a new sinope device report answer.
     *
     * @param r the r
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public SinopeDeviceReportAnswer(InputStream r) throws IOException {
        super(r);
    }

    /**
     * @see ca.tulip.sinope.core.internal.SinopeFrame#getCommand()
     */
    @Override
    protected byte[] getCommand() {
        return new byte[] { 0x01, 0x16 };
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public byte getStatus() {
        byte[] b = this.getFrameData();
        return b[0];
    }

    /**
     * Gets the device id.
     *
     * @return the device id
     */
    public byte[] getDeviceId() {
        byte[] b = this.getFrameData();
        b = Arrays.copyOfRange(b, STATUS_SIZE, STATUS_SIZE + DEVICE_ID_SIZE);
        return ByteUtil.reverse(b);
    }

    /**
     * @see ca.tulip.sinope.core.internal.SinopeFrame#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(String.format("\nStatus: 0x%02X ", getStatus()));
        sb.append(String.format("\n\tDeviceId: %s", ByteUtil.toString(getDeviceId())));
        return sb.toString();
    }

}
