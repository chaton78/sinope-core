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
import java.nio.ByteBuffer;

import ca.tulip.sinope.core.internal.SinopeRequest;
import ca.tulip.sinope.util.ByteUtil;

public class SinopeApiLoginRequest extends SinopeRequest {
    private byte[] apiKey;
    private byte[] id;

    public SinopeApiLoginRequest(byte[] id, byte[] apiKey) {
        this.id = id;
        this.apiKey = apiKey;
    }

    @Override
    protected byte[] getCommand() {
        return new byte[] { 0x01, 0x10 };
    }

    @Override
    protected byte[] getFrameData() {
        byte[] b = new byte[id.length + apiKey.length];

        ByteBuffer bb = ByteBuffer.wrap(b);

        bb.put(ByteUtil.reverse(id));
        bb.put(ByteUtil.reverse(apiKey));

        // System.out.println(toString(bb.array()));
        return bb.array();

    }

    public byte[] getId() {
        return id;
    }

    @Override
    public SinopeApiLoginAnswer getReplyAnswer(InputStream r) throws IOException {
        return new SinopeApiLoginAnswer(r);
    }

}
