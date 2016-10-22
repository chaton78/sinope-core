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

import ca.tulip.sinope.core.appdata.SinopeAppData;
import ca.tulip.sinope.core.internal.SinopeDataAnswer;

/**
 * The Class SinopeDataReadAnswer.
 */
public class SinopeDataWriteAnswer extends SinopeDataAnswer {

    /**
     * Instantiates a new sinope data read answer.
     *
     * @param r the r
     * @param appData the app data
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public SinopeDataWriteAnswer(InputStream r, SinopeAppData appData) throws IOException {
        super(r, appData);
    }

    /**
     * @see ca.tulip.sinope.core.internal.SinopeFrame#getCommand()
     */
    @Override
    protected byte[] getCommand() {
        return new byte[] { 0x02, 0x45 };
    }

}
