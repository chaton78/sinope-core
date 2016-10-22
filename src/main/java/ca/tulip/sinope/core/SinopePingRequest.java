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

import ca.tulip.sinope.core.internal.SinopeRequest;

/**
 * The Class SinopePingRequest.
 */
public class SinopePingRequest extends SinopeRequest {

    /**
     * @see ca.tulip.sinope.core.internal.SinopeFrame#getCommand()
     */
    @Override
    protected byte[] getCommand() {
        return new byte[] { 0x00, 0x12 };
    }

    /**
     * @see ca.tulip.sinope.core.internal.SinopeFrame#getFrameData()
     */
    @Override
    protected byte[] getFrameData() {
        return new byte[0];
    }

    /**
     * @see ca.tulip.sinope.core.internal.SinopeRequest#getReplyAnswer(java.io.InputStream)
     */
    @Override
    public SinopePingAnswer getReplyAnswer(InputStream r) throws IOException {
        return new SinopePingAnswer(r);
    }

}
