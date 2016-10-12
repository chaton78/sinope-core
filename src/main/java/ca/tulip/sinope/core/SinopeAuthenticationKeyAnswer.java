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
 * The Class SinopeAuthenticationKeyAnswer.
 */
public class SinopeAuthenticationKeyAnswer extends SinopeAnswer {

    /** The Constant STATUS_SIZE. */
    protected static final int STATUS_SIZE = 1;
    
    /** The Constant BACKOFF_SIZE. */
    protected static final int BACKOFF_SIZE = 2;
    
    /** The Constant API_KEY_SIZE. */
    protected static final int API_KEY_SIZE = 8;

    /**
     * Instantiates a new sinope authentication key answer.
     *
     * @param r the r
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public SinopeAuthenticationKeyAnswer(InputStream r) throws IOException {
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
    public int getStatus() {
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
     * Gets the api key.
     *
     * @return the api key
     */
    public byte[] getApiKey() {
        byte[] b = this.getFrameData();
        b = Arrays.copyOfRange(b, STATUS_SIZE + BACKOFF_SIZE, STATUS_SIZE + BACKOFF_SIZE + API_KEY_SIZE);
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
        sb.append(String.format("\n\tApi Key: %s", ByteUtil.toString(getApiKey())));
        sb.append(String.format("\n\tBackoff: %s", ByteUtil.toString(getBackoff())));
        return sb.toString();
    }

}
