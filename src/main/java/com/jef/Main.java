package com.jef;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

/**
 * @Author: Jef
 * @Date: 2021/11/16 15:51
 * @Description
 */
public class Main {

    public static void main(String[] args) {


        try {
            KettleEnvironment.init();
            TransMeta transMeta=new TransMeta("D:\\test.ktr");
            Trans trans=new Trans(transMeta);
            trans.execute(null);
            trans.waitUntilFinished();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
