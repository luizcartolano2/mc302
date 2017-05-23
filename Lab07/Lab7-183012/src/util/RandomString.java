package util;

import base.Carta;
import base.TipoCarta;

import java.util.Random;

/**
 * Created by LuizCartolan on 08/05/17.
 */
public class RandomString {

    private static final char[] symbols;

    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch)
            tmp.append(ch);
        for (char ch = 'a'; ch <= 'z'; ++ch)
            tmp.append(ch);
        symbols = tmp.toString().toCharArray();
    }

    private final Random random;

    private final char[] buff;

    public RandomString(Random gerador, int length) {
        if (length < 1)
            throw new IllegalArgumentException("Length < 1: " + length);
        buff = new char[length];
        random = gerador;
    }

    public String nextString() {
        for (int idx = 0; idx < buff.length; ++idx)
            buff[idx] = symbols[random.nextInt(symbols.length)];

        return new String(buff);
    }

}
