package com.powtronic.constructionplatform.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.powtronic.constructionplatform.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pp on 2017/1/17.
 */

public class Update {
    private Context mContext;
    private ProgressDialog mProgress;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String lastVersion = msg.obj.toString();
                    checkUpdate(lastVersion);
                    break;
            }
        }
    };
    private int total;
    private int current;

    public Update(Context context) {
        mContext = context;
    }

    public void checkUpdate(String lastVersion) {
        if (!getVersionCode(mContext).equals(lastVersion)) {
            Log.d("TAG", "handleMessage: " + getVersionCode(mContext) + lastVersion);
            showNoticeDialog();
        }else {
            showNoticeDialog2();
        }
    }

    private void downloadApk() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    URL u = new URL(Constants.DOWNLOAD_APK_URL);
                    //打开连接
                    HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");

                    if (200 == conn.getResponseCode()) {
                        //得到输入流
                        InputStream is = conn.getInputStream();
                        FileOutputStream fos = new FileOutputStream(new File(mContext.getExternalCacheDir(), "new.apk"));

                        handler.sendEmptyMessage(2);

                        total = conn.getContentLength();
                        current = 0;

                        byte[] buf = new byte[1024];
                        int length = 0;
                        while ((length = is.read(buf)) > 0) {
                            fos.write(buf, 0, length);
                            // Log.d("TAG","1");
                            current += length;

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mProgress.setProgress(current * 100 / total);
                                }
                            });
                        }
                        installApk();
                    }
                    mProgress.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void showDownloadDialog() {
        mProgress = new ProgressDialog(mContext);
        mProgress.setTitle("版本更新");
        mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgress.setProgress(0);
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.setCancelable(false);
        mProgress.show();
    }

    public void update() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    URL u = new URL(Constants.VERSION_URL);
                    //打开连接
                    HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");

                    if (200 == conn.getResponseCode()) {
                        //得到输入流
                        InputStream is = conn.getInputStream();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int len = 0;
                        while (-1 != (len = is.read(buffer))) {
                            baos.write(buffer, 0, len);
                            baos.flush();
                        }
                        String resp = baos.toString("utf-8");
                        String version = new JSONObject(resp).getString("data");
                        Log.d("TAG", "run: " + version);
                        handler.obtainMessage(1, version).sendToTarget();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    protected void installApk() {
        // 调用系统的安装器来安装软件
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        //设置要传输的数据 data ：uri ：格式的数据 type ：数据的MIME类型
        intent.setDataAndType(
                Uri.fromFile(new File(mContext.getExternalCacheDir(), "new.apk")),
                "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    private void showNoticeDialog() {
        // 构造对话框
        new AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("软件有新的版本!")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showDownloadDialog();
                        downloadApk();
                    }
                }).show();
    }

    private void showNoticeDialog2() {
        // 构造对话框
        new AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("已是最新版本!")
                .show();
    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    private String getVersionCode(Context context) {
        try {
            PackageInfo manager = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return manager.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Unknown";
        }
    }
}
