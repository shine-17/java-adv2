package io.start;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class StreamStratMain4 {

    private static final String fileName = "temp/hello.dat";

    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        byte[] input = {65, 66, 67, 68};
        fos.write(input);
        fos.close();

        FileInputStream fis = new FileInputStream(fileName);
        byte[] readBytes = fis.readAllBytes();
        System.out.println(Arrays.toString(readBytes));
        fis.close();
    }
}
