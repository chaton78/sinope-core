/**
 * Copyright (c) 2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * @author: Pascal Larin
 * https://github.com/chaton78
 */
package ca.tulip.sinope.core.appdata;

/**
 * The Class SinopeRoomTempData.
 */
public class SinopeHeatLevelData extends SinopeAppData {

    /**
     * Instantiates a new sinope set point temp data.
     */
    public SinopeHeatLevelData() {

        super(new byte[] { 0x00, 0x00, 0x02, 0x20 }, new byte[] { 0 });
    }

    /**
     * Gets the room temp.
     *
     * @return the room temp
     */
    public int getHeatLevel() {
        if (getData() != null) {
            return getData()[0] & 0xFF;
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
            sb.append(String.format("\nHeat Level is %d %%", this.getHeatLevel()));
        }
        return sb.toString();
    }

}
