package ca.tulip.sinope.util;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

public class ByteArrayConverter implements IStringConverter<byte[]> {
    public byte[] convert(String value) {
        value = value.replace("-", "");
        value = value.replace("0x", "");
        value = value.replace(" ", "");
        if (value.length() % 2 == 0 && value.length() > 1) {
            byte[] b = new byte[value.length() / 2];

            for (int i = 0; i < value.length(); i = i + 2) {
                b[i / 2] = (byte) Integer.parseInt(value.substring(i, i + 2), 16);
            }
            return b;
        } else {
            throw new ParameterException("Odd number of character. Each byte should be represented by two characters.");
        }
    }
}