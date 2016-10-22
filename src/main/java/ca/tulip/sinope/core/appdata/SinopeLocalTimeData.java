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
package ca.tulip.sinope.core.appdata;

/**
 * The Class SinopeLocalTimeData.
 */
public class SinopeLocalTimeData extends SinopeAppData {

    /**
     * Instantiates a new sinope local time data.
     */
    public SinopeLocalTimeData() {

        super(new byte[] { 0x00, 0x00, 0x06, 0x00 }, new byte[] { 0, 0, 0 });
    }

}
