package com.powtronic.constructionplatform.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.powtronic.constructionplatform.Callback.DialogCallback;
import com.powtronic.constructionplatform.Constants;
import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.adapter.ParamAdapter;
import com.powtronic.constructionplatform.bean.HttpMsg;
import com.powtronic.constructionplatform.bean.Product;
import com.powtronic.constructionplatform.view.MyListView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class AddProductActivity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.lv_params)
    MyListView lvParams;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.iv_ware)
    ImageView iv_ware;
    private ArrayList<Product.Param> params;
    private ParamAdapter paramAdapter;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);
        setTitleBar("新增设备", TITLEBAR_ONLY_BACK);

        initView();

    }

    private void initView() {

        params = new ArrayList<>();
        params.add(new Product.Param());
        paramAdapter = new ParamAdapter(this, params);
        lvParams.setAdapter(paramAdapter);
    }


    @OnClick(R.id.iv_add)
    public void add(View v) {
        params.add(new Product.Param());
        Log.d("TAG", "add: " + params.get(0));
        paramAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_submit)
    public void submit(View v) {
        if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etPrice.getText().toString())) {
            Toast.makeText(this, "请完善产品信息!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (filePath != null) {
            updateImage(filePath);
        }else{
            Product product = new Product();
            product.setName(etName.getText().toString());
            product.setPrice(etPrice.getText().toString());
            product.setImgUrl(Constants.IMAGE_URL + "new.jpg");

            Intent intent = new Intent();
            intent.putExtra("data", product);
            setResult(RESULT_OK, intent);
        }

    }

    private void updateImage(String imagePath) {
        File imageFile = new File(imagePath);
        OkGo.post(Constants.UPLOAD)
                .headers("Content-Disposition", imageFile.getName())
                .params("10", imageFile).execute(new DialogCallback<HttpMsg>(this) {
            @Override
            public void onSuccess(HttpMsg httpMsg, Call call, Response response) {
                Log.d("TAG", "onSuccess: " + httpMsg);
                Product product = new Product();
                product.setName(etName.getText().toString());
                product.setPrice(etPrice.getText().toString());
                product.setImgUrl(httpMsg.getData());

                Intent intent = new Intent();
                intent.putExtra("data", product);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }

    @OnClick(R.id.iv_ware)
    public void add_image(View v) {
        chooseImage();
    }

    private void chooseImage() {
        //创建打开系统图库的Intent1
        Intent intent1 = new Intent(Intent.ACTION_PICK);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

        //创建打开系统相机的Intnet2
        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //指定拍摄照片保存的位置
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + ".png");
        filePath = file.getAbsolutePath();
        Uri imgUri = Uri.fromFile(file);
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);

        //创建Intent选择器
        Intent chooser = Intent.createChooser(intent1, "请选择照片...");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent2});

        startActivityForResult(chooser, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 101) {

            //data不为空则为图库返回；否则为相机返回
            if (data != null) {
                Uri uri = data.getData();
                filePath = getPath(this, uri);
            }
            iv_ware.setImageBitmap(BitmapFactory.decodeFile(filePath));
        }
    }

    public String getPath(Context context, Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                //
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

//    private void uploadFile()
//    {
//        String end = "/r/n";
//        String Hyphens = "--";
//        String boundary = "*****";
//        try
//        {
//            URL url = new URL(actionUrl);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//      /* 允许Input、Output，不使用Cache */
//            con.setDoInput(true);
//            con.setDoOutput(true);
//            con.setUseCaches(false);
//      /* 设定传送的method=POST */
//            con.setRequestMethod("POST");
//      /* setRequestProperty */
//            con.setRequestProperty("Connection", "Keep-Alive");
//            con.setRequestProperty("Charset", "UTF-8");
//            con.setRequestProperty("Content-Type",
//                    "multipart/form-data;boundary=" + boundary);
//      /* 设定DataOutputStream */
//            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
//            ds.writeBytes(Hyphens + boundary + end);
//            ds.writeBytes("Content-Disposition: form-data; "
//                    + "name=\"file1\";filename=\"" + newName + "\"" + end);
//            ds.writeBytes(end);
//      /* 取得文件的FileInputStream */
//            FileInputStream fStream = new FileInputStream(uploadFile);
//      /* 设定每次写入1024bytes */
//            int bufferSize = 1024;
//            byte[] buffer = new byte[bufferSize];
//            int length = -1;
//      /* 从文件读取数据到缓冲区 */
//            while ((length = fStream.read(buffer)) != -1)
//            {
//        /* 将数据写入DataOutputStream中 */
//                ds.write(buffer, 0, length);
//            }
//            ds.writeBytes(end);
//            ds.writeBytes(Hyphens + boundary + Hyphens + end);
//            fStream.close();
//            ds.flush();
//      /* 取得Response内容 */
//            InputStream is = con.getInputStream();
//            int ch;
//            StringBuffer b = new StringBuffer();
//            while ((ch = is.read()) != -1)
//            {
//                b.append((char) ch);
//            }
//            System.out.println("上传成功");
//            Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_LONG)
//                    .show();
//            ds.close();
//        } catch (Exception e)
//        {
//            System.out.println("上传失败" + e.getMessage());
//            Toast.makeText(MainActivity.this, "上传失败" + e.getMessage(),
//                    Toast.LENGTH_LONG).show();
//        }
//    }


}
