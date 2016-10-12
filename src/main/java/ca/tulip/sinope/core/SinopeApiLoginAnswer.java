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

public class SinopeApiLoginAnswer extends SinopeAnswer {
    protected static final int STATUS_SIZE = 1;
    protected static final int BACKOFF_SIZE = 2;
    protected static final int SW_REV_MAJ_SIZE = 1;
    protected static final int SW_REV_MIN_SIZE = 1;
    protected static final int SW_REV_BUG_SIZE = 1;
    protected static final int DEVICE_ID_SIZE = 4;

    public SinopeApiLoginAnswer(InputStream r) throws IOException {
        super(r);
    }

    @Override
    protected byte[] getCommand() {
        return new byte[] { 0x01, 0x11 };
    }

    public byte getStatus() {
        byte[] b = this.getFrameData();
        return b[0];
    }

    public byte[] getBackoff() {
        byte[] b = this.getFrameData();
        b = Arrays.copyOfRange(b, STATUS_SIZE, STATUS_SIZE + BACKOFF_SIZE);
        return b;
    }

    public byte getSwRevMaj() {
        byte[] b = this.getFrameData();
        return b[STATUS_SIZE + BACKOFF_SIZE];
    }

    public byte getSwRevMin() {
        byte[] b = this.getFrameData();
        return b[STATUS_SIZE + BACKOFF_SIZE + SW_REV_MAJ_SIZE];
    }

    public byte getSwRevBug() {
        byte[] b = this.getFrameData();
        return b[STATUS_SIZE + BACKOFF_SIZE + SW_REV_MAJ_SIZE + SW_REV_MIN_SIZE];
    }

    public byte[] getDeviceId() {
        byte[] b = this.getFrameData();
        b = Arrays.copyOfRange(b, STATUS_SIZE + BACKOFF_SIZE + SW_REV_MAJ_SIZE + SW_REV_MIN_SIZE + SW_REV_BUG_SIZE,
                STATUS_SIZE + BACKOFF_SIZE + SW_REV_MAJ_SIZE + SW_REV_MIN_SIZE + SW_REV_BUG_SIZE + DEVICE_ID_SIZE);
        return ByteUtil.reverse(b);
    }

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