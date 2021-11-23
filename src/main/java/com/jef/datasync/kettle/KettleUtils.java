package com.jef.datasync.kettle;

import com.jef.datasync.mq.SimpleProducer;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;
import java.util.Random;

/**
 * @Author: Jef
 * @Date: 2021/11/18 14:15
 * @Description
 */
@Component
public class KettleUtils {

    @Autowired
    private SimpleProducer producer;

    public void trans(String templateId, Map<String, String> params) {
        File source = null;
        try {
            source = getTemplate(templateId);
        } catch (IOException e) {
            //todo 模板不存在
            e.printStackTrace();
            return;
        }

        Random random = new Random();
        File target = new File("template" + random.nextInt(50));
        if (null != source) {
            try {
                copyFile(source, target, params);
            } catch (IOException e) {
                //todo 模板参数化出错
                e.printStackTrace();
                return;
            }
        } else {
            //todo 出错
            return;
        }

        try {
            KettleEnvironment.init();
            TransMeta transMeta = new TransMeta(target.getAbsolutePath());
            Trans trans = new Trans(transMeta);
            trans.execute(null);
            trans.waitUntilFinished();

        } catch (Exception e) {
            e.printStackTrace();
        }

        producer.sendDirectMsg("同步数据至业务服务成功");
        deleteFile(target);
    }

    public File getTemplate(String templateId) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("template/" + templateId);
        return classPathResource.getFile();
    }

    private void copyFile(File source, File target, Map<String, String> params) throws IOException {
        FileReader fileReader = new FileReader(source);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        FileWriter fileWriter = new FileWriter(target);
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

    private String replaceLine(String line, Map<String, String> params) {

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

    private void deleteFile(File file) {
        file.delete();
    }

}
