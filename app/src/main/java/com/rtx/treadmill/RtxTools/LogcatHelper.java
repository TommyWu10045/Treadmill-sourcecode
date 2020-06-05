package com.rtx.treadmill.RtxTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LogcatHelper {

    public static ArrayList<String> read(String[] command) {
        ArrayList<String> result = new ArrayList<String>();

        try {
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String s = "";
            while (s != null) {
                s = bufferedReader.readLine();
                if(s != null) {
                    result.add(s);
                }
            }

        } catch (IOException e) {

        }

        return result;

    }
}