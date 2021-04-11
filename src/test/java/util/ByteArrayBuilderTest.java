package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByteArrayBuilderTest {

    @Test
    public void testByteArrayConstructor1() {
        byte[] testArray1 = new byte[] {1, 2};
        byte[] testArray2 = new byte[] {3, 4};
        ByteArrayBuilder builder = new ByteArrayBuilder();

        builder.append(testArray1);
        builder.append(testArray2);

        byte[] correctArray = new byte[] {1, 2, 3, 4};
        assertArrayEquals(builder.getByteArray(), correctArray);
    }

    @Test
    public void testByteArrayConstructor2() {
        byte[] testArray1 = new byte[] {1, 2};
        byte[] testArray2 = new byte[] {3, 4};
        ByteArrayBuilder builder = new ByteArrayBuilder(testArray1);

        builder.append(testArray2);

        byte[] correctArray = new byte[] {1, 2, 3, 4};
        assertArrayEquals(builder.getByteArray(), correctArray);
    }

    @Test
    public void testBasicByteAppend() {
        byte byteToAppend = 4;
        byte[] testArray = new byte[] {1, 2, 3};
        ByteArrayBuilder builder = new ByteArrayBuilder();

        builder.append(testArray);
        builder.append(byteToAppend);

        byte[] correctArray = new byte[] {1, 2, 3, 4};
        assertArrayEquals(builder.getByteArray(), correctArray);
    }

    @Test
    public void testBasicByteArrayAppend() {
        byte[] testArray1 = new byte[] {1, 2, 3, 4};
        byte[] testArray2 = new byte[] {5, 6, 7, 8};
        ByteArrayBuilder builder = new ByteArrayBuilder();

        builder.append(testArray1);
        builder.append(testArray2);

        byte[] correctArray = new byte[] {1, 2, 3, 4, 5, 6, 7, 8};
        assertArrayEquals(builder.getByteArray(), correctArray);
    }

    @Test
    public void testBasicIntAppend() {
        int intToAppend = 5;
        byte[] testArray = new byte[] {1, 2, 3, 4};
        ByteArrayBuilder builder = new ByteArrayBuilder(testArray);

        builder.append(intToAppend);

        byte[] correctArray = new byte[] {1, 2, 3, 4, 0, 0, 0, 5};
        assertArrayEquals(builder.getByteArray(), correctArray);
    }


}
