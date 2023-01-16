package com.ThParkers.TheParkers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class read_file {
    public String readFile(String fileName) throws IOException {
        try ( BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), "ISO-8859-1"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
