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

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import ca.tulip.sinope.core.appdata.SinopeAppData;
import ca.tulip.sinope.util.ByteUtil;

/**
 * The Class SinopeDataAnswer.
 */
public abstract class SinopeDataAnswer extends SinopeAnswer {

    /** The Constant SEQ_SIZE. */
    protected static final int SEQ_SIZE = 4;

    /** The Constant STATUS_SIZE. */
    protected static final int STATUS_SIZE = 1;

    /** The Constant ATTEMPT_NBR_SIZE. */
    protected static final int ATTEMPT_NBR_SIZE = 1;

    /** The Constant MORE_SIZE. */
    protected static final int MORE_SIZE = 1;

    /** The Constant SRC_DEVICE_ID_SIZE. */
    protected static final int SRC_DEVICE_ID_SIZE = 4;

    /** The Constant APP_DATA_SIZE_SIZE. */
    protected static final int APP_DATA_SIZE_SIZE = 1;

    /** The app data. */
    private SinopeAppData appData;

    /**
     * Instantiates a new sinope data answer.
     *
     * @param r the r
     * @param appData the app data
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public SinopeDataAnswer(InputStream r, SinopeAppData appData) throws IOException {
        super(r);
        byte[] data = getData();
        this.appData = appData;
        this.appData.read(data);

    }

    /**
     * Gets the app data.
     *
     * @return the app data
     */
    public SinopeAppData getAppData() {
        return appData;
    }

    /**
     * Gets the seq.
     *
     * @return the seq
     */
    public byte[] getSeq() {
        byte[] b = this.getFrameData();
        return Arrays.copyOfRange(b, 0, SEQ_SIZE);
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public byte getStatus() {
        byte[] b = this.getFrameData();
        return b[SEQ_SIZE];
    }

    /**
     * Gets the attempt nbr.
     *
     * @return the attempt nbr
     */
    public byte getAttemptNbr() {
        byte[] b = this.getFrameData();
        return b[SEQ_SIZE + STATUS_SIZE];
    }

    /**
     * Gets the more.
     *
     * @return the more
     */
    public byte getMore() {
        byte[] b = this.getFrameData();
        return b[SEQ_SIZE + STATUS_SIZE + ATTEMPT_NBR_SIZE];
    }

    /**
     * Gets the src device id.
     *
     * @return the src device id
     */
    public byte[] getSrcDeviceId() {
        byte[] b = this.getFrameData();
        int start = SEQ_SIZE + STATUS_SIZE + ATTEMPT_NBR_SIZE + MORE_SIZE;
        int end = start + SRC_DEVICE_ID_SIZE;
        return ByteUtil.reverse(Arrays.copyOfRange(b, start, end));
    }

    /**
     * Gets the data.
     *
     * @return the data
     */
    public byte[] getData() {
        byte[] b = this.getFrameData();
        int start = SEQ_SIZE + STATUS_SIZE + ATTEMPT_NBR_SIZE + MORE_SIZE + SRC_DEVICE_ID_SIZE;
        int end = start + 1 + (b[start] & 0xff);
        return Arrays.copyOfRange(b, start + 1, end);
    }

    /**
     * @see ca.tulip.sinope.core.internal.SinopeFrame#toString()
     */
    /*
     * 
     *
     * @see ca.tulip.sinope.core.internal.SinopeFrame#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(String.format("\nData: %s", ByteUtil.toString(getFrameData())));
        sb.append(String.format("\n\tSeq: %s", ByteUtil.toString(getSeq())));
        sb.append(String.format("\n\tStatus: 0x%02X ", getStatus()));
        sb.append(String.format("\n\tAttempt Nbr: 0x%02X ", getAttemptNbr()));
        sb.append(String.format("\n\tMore: 0x%02X ", getMore()));

        sb.append(String.format("\n\tSrcDeviceId: %s", ByteUtil.toString(getSrcDeviceId())));

        sb.append(appData);

        return sb.toString();
    }

}
