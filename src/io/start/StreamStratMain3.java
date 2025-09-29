package io.start;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class StreamStratMain3 {

    private static final String fileName = "temp/hello.dat";

    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        byte[] input = {65, 66, 67, 68};
        fos.write(input);
        fos.close();

        FileInputStream fis = new FileInputStream(fileName);

        byte[] buffer = new byte[10];
        int readCount = fis.read(buffer);
        System.out.println("readCount = " + readCount);
        System.out.println(Arrays.toString(buffer));
        fis.close();
    }
}
