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
package ca.tulip.sinope.core.appdata;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * The Class SinopeRoomTempData.
 */
public class SinopeRoomTempData extends SinopeAppData {

    /**
     * Instantiates a new sinope room temp data.
     */
    public SinopeRoomTempData() {

        super(new byte[] { 0x00, 0x00, 0x02, 0x03 }, new byte[] { 0, 0 });
    }

    /**
     * Gets the room temp.
     *
     * @return the room temp
     */
    public int getRoomTemp() {
        if (getData() != null) {
            ByteBuffer bb = ByteBuffer.wrap(getData());
            bb.order(ByteOrder.LITTLE_ENDIAN);
            return bb.getShort();
        }
        return -273;
    }

    /**
     * @see ca.tulip.sinope.core.appdata.SinopeAppData#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        if (getData() != null) {
            sb.append(String.format("\n\tRoom temperature is %2.2f C", this.getRoomTemp() / 100.0));
        }
        return sb.toString();
    }

}
