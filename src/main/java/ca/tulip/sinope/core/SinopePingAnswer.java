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

import ca.tulip.sinope.core.internal.SinopeAnswer;

public class SinopePingAnswer extends SinopeAnswer {

    public SinopePingAnswer(InputStream r) throws IOException {
        super(r);
    }

    @Override
    protected byte[] getCommand() {
        return new byte[] { 0x00, 0x13 };
    }

}
