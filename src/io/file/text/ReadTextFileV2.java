package io.file.text;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ReadTextFileV2 {

    private static final String PATH = "temp/hello2.txt";

    public static void main(String[] args) throws IOException {
        String writeString = "abc\n가나다";
        System.out.println("== Write String ==");
        System.out.println(writeString);

        Path path = Path.of(PATH);

        // 파일에 쓰기
        Files.writeString(path, writeString, UTF_8);

        // 파일 읽기
        System.out.println("== Read String ==");

        // 스트림을 이용해서 1줄씩 읽어옴 (메모리에 1줄씩 로드)
        Stream<String> lineStream = Files.lines(path, UTF_8);
        lineStream.forEach(System.out::println);
        lineStream.close();

        // 한 번에 모두 읽음 (메모리에 한 번에 올라감)
//        List<String> lines = Files.readAllLines(path, UTF_8);
//        for (int i=0; i<lines.size(); i++) {
//            System.out.println((i+1) + ": " + lines.get(i));
//        }
    }
}
