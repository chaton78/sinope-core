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

import ca.tulip.sinope.core.appdata.SinopeAppData;
import ca.tulip.sinope.core.internal.SinopeDataRequest;

/**
 * The Class SinopeDataReadRequest.
 */
public class SinopeDataReadRequest extends SinopeDataRequest {

    /**
     * Instantiates a new sinope data read request.
     *
     * @param seq the seq
     * @param dstDeviceId the dst device id
     * @param appData the app data
     */
    public SinopeDataReadRequest(byte[] seq, byte[] dstDeviceId, SinopeAppData appData) {
        super(seq, dstDeviceId, appData);
        // Read Request, as per spec.. zap data part
        appData.cleanData();
    }

    /**
     * @see ca.tulip.sinope.core.internal.SinopeFrame#getCommand()
     */
    @Override
    protected byte[] getCommand() {
        return new byte[] { 0x02, 0x40 };
    }

    /**
     * @see ca.tulip.sinope.core.internal.SinopeDataRequest#getReplyAnswer(java.io.InputStream)
     */
    @Override
    public SinopeDataReadAnswer getReplyAnswer(InputStream r) throws IOException {
        return new SinopeDataReadAnswer(r, this.getAppData());
    }

}
