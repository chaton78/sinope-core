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
package ca.tulip.sinope.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import ca.tulip.sinope.core.internal.SinopeAnswer;
import ca.tulip.sinope.util.ByteUtil;

/**
 * The Class SinopeApiLoginAnswer.
 */
public class SinopeApiLoginAnswer extends SinopeAnswer {
    
    /** The Constant STATUS_SIZE. */
    protected static final int STATUS_SIZE = 1;
    
    /** The Constant BACKOFF_SIZE. */
    protected static final int BACKOFF_SIZE = 2;
    
    /** The Constant SW_REV_MAJ_SIZE. */
    protected static final int SW_REV_MAJ_SIZE = 1;
    
    /** The Constant SW_REV_MIN_SIZE. */
    protected static final int SW_REV_MIN_SIZE = 1;
    
    /** The Constant SW_REV_BUG_SIZE. */
    protected static final int SW_REV_BUG_SIZE = 1;
    
    /** The Constant DEVICE_ID_SIZE. */
    protected static final int DEVICE_ID_SIZE = 4;

    /**
     * Instantiates a new sinope api login answer.
     *
     * @param r the r
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public SinopeApiLoginAnswer(InputStream r) throws IOException {
        super(r);
    }

    /**
     * @see ca.tulip.sinope.core.internal.SinopeFrame#getCommand()
     */
    @Override
    protected byte[] getCommand() {
        return new byte[] { 0x01, 0x11 };
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
     * Gets the backoff.
     *
     * @return the backoff
     */
    public byte[] getBackoff() {
        byte[] b = this.getFrameData();
        b = Arrays.copyOfRange(b, STATUS_SIZE, STATUS_SIZE + BACKOFF_SIZE);
        return b;
    }

    /**
     * Gets the sw rev maj.
     *
     * @return the sw rev maj
     */
    public byte getSwRevMaj() {
        byte[] b = this.getFrameData();
        return b[STATUS_SIZE + BACKOFF_SIZE];
    }

    /**
     * Gets the sw rev min.
     *
     * @return the sw rev min
     */
    public byte getSwRevMin() {
        byte[] b = this.getFrameData();
        return b[STATUS_SIZE + BACKOFF_SIZE + SW_REV_MAJ_SIZE];
    }

    /**
     * Gets the sw rev bug.
     *
     * @return the sw rev bug
     */
    public byte getSwRevBug() {
        byte[] b = this.getFrameData();
        return b[STATUS_SIZE + BACKOFF_SIZE + SW_REV_MAJ_SIZE + SW_REV_MIN_SIZE];
    }

    /**
     * Gets the device id.
     *
     * @return the device id
     */
    public byte[] getDeviceId() {
        byte[] b = this.getFrameData();
        b = Arrays.copyOfRange(b, STATUS_SIZE + BACKOFF_SIZE + SW_REV_MAJ_SIZE + SW_REV_MIN_SIZE + SW_REV_BUG_SIZE,
                STATUS_SIZE + BACKOFF_SIZE + SW_REV_MAJ_SIZE + SW_REV_MIN_SIZE + SW_REV_BUG_SIZE + DEVICE_ID_SIZE);
        return ByteUtil.reverse(b);
    }

    /**
     * @see ca.tulip.sinope.core.internal.SinopeFrame#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(String.format("\nData: %s", ByteUtil.toString(getFrameData())));
        sb.append(String.format("\n\tStatus: 0x%02X ", getStatus()));
        sb.append(String.format("\n\tBackoff: %s", ByteUtil.toString(getBackoff())));
        sb.append(String.format("\n\tSwRevMaj: 0x%02X ", getSwRevMaj()));
        sb.append(String.format("\n\tSwRevMin: 0x%02X ", getSwRevMin()));
        sb.append(String.format("\n\tSwRevBug: 0x%02X ", getSwRevBug()));
        sb.append(String.format("\n\tDeviceID: %s", ByteUtil.toString(getDeviceId())));

        return sb.toString();
    }
}