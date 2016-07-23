package fr.azelart.artnetstack.utils;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: matthias
 * Date: 06.08.12
 * Time: 22:49
 * To change this template use File | Settings | File Templates.
 */
public class ByteUtilsArtTest {
    @Test
    public void testByte2toIn() throws Exception {
        byte[][] test_data = new byte[][]{
                new byte[]{0x00, 0x00},
                new byte[]{(byte) 0xff, 0x00},
                new byte[]{0x00, (byte) 0xff},
                new byte[]{(byte) 0xff, (byte) 0xff}};
        int[] results = new int[]{
                0x0000,
                0x00ff,
                0xff00,
                0xffff};
        for (int i = 0; i < test_data.length; i++) {
            Assert.assertEquals(Arrays.toString(test_data[i]) + " should be converted to " + results[i], results[i], ByteUtilsArt.byte2toIn(test_data[i], 0));
        }
    }
}
