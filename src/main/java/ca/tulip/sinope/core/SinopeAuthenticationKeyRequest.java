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

import ca.tulip.sinope.core.internal.SinopeRequest;
import ca.tulip.sinope.util.ByteUtil;

public class SinopeAuthenticationKeyRequest extends SinopeRequest {

    private byte[] id;

    public SinopeAuthenticationKeyRequest(byte[] id) {
        this.id = id;
    }

    @Override
    protected byte[] getCommand() {
        return new byte[] { 0x01, 0x0a };
    }

    @Override
    protected byte[] getFrameData() {
        return ByteUtil.reverse(this.id);
    }

    @Override
    public SinopeAuthenticationKeyAnswer getReplyAnswer(InputStream r) throws IOException {

        return new SinopeAuthenticationKeyAnswer(r);

    }

    public byte[] getId() {
        return id;
    }

}
