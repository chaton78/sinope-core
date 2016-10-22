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
import java.nio.ByteBuffer;

import ca.tulip.sinope.core.internal.SinopeRequest;
import ca.tulip.sinope.util.ByteUtil;

/**
 * The Class SinopeApiLoginRequest.
 */
public class SinopeApiLoginRequest extends SinopeRequest {
    
    /** The api key. */
    private byte[] apiKey;
    
    /** The id. */
    private byte[] id;

    /**
     * Instantiates a new sinope api login request.
     *
     * @param id the id
     * @param apiKey the api key
     */
    public SinopeApiLoginRequest(byte[] id, byte[] apiKey) {
        this.id = id;
        this.apiKey = apiKey;
    }

    /**
     * @see ca.tulip.sinope.core.internal.SinopeFrame#getCommand()
     */
    @Override
    protected byte[] getCommand() {
        return new byte[] { 0x01, 0x10 };
    }

    /**
     * @see ca.tulip.sinope.core.internal.SinopeFrame#getFrameData()
     */
    @Override
    protected byte[] getFrameData() {
        byte[] b = new byte[id.length + apiKey.length];

        ByteBuffer bb = ByteBuffer.wrap(b);

        bb.put(ByteUtil.reverse(id));
        bb.put(ByteUtil.reverse(apiKey));

        // System.out.println(toString(bb.array()));
        return bb.array();

    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public byte[] getId() {
        return id;
    }

    /**
     * @see ca.tulip.sinope.core.internal.SinopeRequest#getReplyAnswer(java.io.InputStream)
     */
    @Override
    public SinopeApiLoginAnswer getReplyAnswer(InputStream r) throws IOException {
        return new SinopeApiLoginAnswer(r);
    }

}
