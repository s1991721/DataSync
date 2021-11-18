package com.jef.kettle;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

import java.io.*;
import java.util.Map;

/**
 * @Author: Jef
 * @Date: 2021/11/18 14:15
 * @Description
 */
public class KettleUtils {


    public static void copyFile(Map<String, String> params) throws IOException {
        File inFile = new File("D:\\GitHub\\DataSync\\src\\main\\resources\\test.ktr");
        FileReader fileReader = new FileReader(inFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        File outFile = new File("D:\\GitHub\\DataSync\\src\\main\\resources\\test1.ktr");
        FileWriter fileWriter = new FileWriter(outFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            String newLine = replaceLine(line, params);

            bufferedWriter.write(newLine);
            bufferedWriter.newLine();
        }
        bufferedReader.close();
        fileReader.close();

        bufferedWriter.close();
        fileWriter.close();
    }

    public static String replaceLine(String line, Map<String, String> params) {

        int a = line.indexOf("${");
        int b = line.indexOf("}$");
        if (a < 0) {
            return line;
        }

        String key = line.substring(a + 2, b);
        System.out.println(a + ":" + b + "---->" + key);
        String value = params.getOrDefault(key, "${null}$");
        line = line.replace("${" + key + "}$", value);

        return line;
    }

    private void trans() {
        try {
            KettleEnvironment.init();
            TransMeta transMeta = new TransMeta("D:\\test.ktr");
            Trans trans = new Trans(transMeta);
            trans.execute(null);
            trans.waitUntilFinished();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteFile() {
        File file = new File("D:\\GitHub\\DataSync\\src\\main\\resources\\test1.ktr");
        file.delete();
    }
}
