package com.powtronic.encryption_decryption;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private String jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int testMaxCount = 1000;//测试的最大数据条数

        //添加测试数据
        jsonData = "abcdefgh";
        Log.e("TAG", "加密前json数据 ---->" + jsonData);
        Log.e("TAG", "加密前json数据长度 ---->" + jsonData.length());

        //MD5Test
//        md5Test();

//        Ase加密
        aesEncryption();

        //  des加密
        ///desEncryption();



    }

    private void desEncryption() {
        String encode = DesUtils.encode("aaaaaaaa", jsonData);
        Log.e("TAG", " ---->" + encode);
        String decode = DesUtils.decode("aaaaaaaa", encode);
        Log.e("TAG", " ---->" + decode);

    }

    private void md5Test() {
        String md5 = MD5Utils.md5(jsonData);
        Log.e("TAG", "加密前json数据长度 ---->" + md5);
    }

    /**
     * Aes加密测试
     */
    private void aesEncryption() {
        //生成一个动态key
        String secretKey = AesUtils.generateKey();
        Log.e("TAG", "AES动态secretKey ---->" + secretKey);

        //AES加密
        long start = System.currentTimeMillis();
        String encryStr = AesUtils.encrypt(secretKey, jsonData);
        long end = System.currentTimeMillis();
        Log.e("TAG", "AES加密耗时 cost time---->" + (end - start));
        Log.e("TAG", "AES加密后json数据 ---->" + encryStr);
        Log.e("TAG", "AES加密后json数据长度 ---->" + encryStr.length());

        //AES解密
        start = System.currentTimeMillis();
        String decryStr = AesUtils.decrypt(secretKey, encryStr);
        end = System.currentTimeMillis();
        Log.e("TAG", "AES解密耗时 cost time---->" + (end - start));
        Log.e("TAG", "AES解密后json数据 ---->" + decryStr);
    }
}
