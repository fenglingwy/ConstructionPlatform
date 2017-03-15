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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.powtronic.constructionplatform.Callback.DialogCallback;
import com.powtronic.constructionplatform.MyApplication;
import com.powtronic.constructionplatform.R;
import com.powtronic.constructionplatform.adapter.ParamAdapter;
import com.powtronic.constructionplatform.bean.Constants;
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
    @BindView(R.id.Rb_product)
    RadioButton RbProduct;
    @BindView(R.id.Rb_part)
    RadioButton RbPart;
    @BindView(R.id.Rg_product_type)
    RadioGroup RgProductType;
    @BindView(R.id.Rb_rent_sale)
    RadioButton RbRentSale;
    @BindView(R.id.Rb_sale)
    RadioButton RbSale;
    @BindView(R.id.Rb_rent)
    RadioButton RbRent;
    @BindView(R.id.Rg_sale_type)
    RadioGroup RgSaleType;


    private ArrayList<Product.Param> params;
    private ParamAdapter paramAdapter;
    private String filePath;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);
        setTitleBar("新增设备", TITLEBAR_ONLY_BACK);
        initView();
    }

    private void initView() {
        product = new Product();
        params = new ArrayList<>();
        product.setParams(params);
        product.setUser_id(MyApplication.mUser.get_id());

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
        //设置产品信息
        product.setName(etName.getText().toString());
        product.setPrice(etPrice.getText().toString());

        switch (RgProductType.getCheckedRadioButtonId()){
            case R.id.Rb_product:
                product.setProduct_type(1);
                break;
            case R.id.Rb_part:
                product.setProduct_type(2);
                break;

        }
        switch (RgSaleType.getCheckedRadioButtonId()){
            case R.id.Rb_rent_sale:
                product.setSales_type(3);
                break;
            case R.id.Rb_sale:
                product.setSales_type(1);
                break;
            case R.id.Rb_rent:
                product.setSales_type(2);
                break;
        }

        if (filePath != null) {
            updateImage(filePath);
        } else {
            product.setImgUrl("upLoading\\photo\\new.jpg");
            Intent intent = new Intent();
            intent.putExtra("data", product);
            setResult(RESULT_OK, intent);
            addProduct(product);
        }

    }

    /**
     * 上传图片
     *
     * @param imagePath
     */
    private void updateImage(String imagePath) {
        File imageFile = new File(imagePath);
        OkGo.post(Constants.UPLOAD)
                .headers("Content-Disposition", imageFile.getName())
                .params("10", imageFile).execute(new DialogCallback<HttpMsg>(this) {
            @Override
            public void onSuccess(HttpMsg httpMsg, Call call, Response response) {
                if(httpMsg==null){
                    Toast.makeText(AddProductActivity.this,"网络异常!",Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("TAG", "onSuccess: " + httpMsg);

                product.setImgUrl(httpMsg.getData());

                Intent intent = new Intent();
                intent.putExtra("data", product);
                setResult(RESULT_OK, intent);

                addProduct(product);
            }
        });
    }

    /**
     * 上传product信息
     *
     * @param product
     */
    private void addProduct(Product product) {
        OkGo.post(Constants.ADD_PRODUCT_URL)
                .params("params", new Gson().toJson(product))
                .execute(new DialogCallback<HttpMsg>(AddProductActivity.this) {
                    @Override
                    public void onSuccess(HttpMsg httpMsg, Call call, Response response) {
                        if(httpMsg==null){
                            Toast.makeText(AddProductActivity.this,"网络异常!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        finish();
                    }
                });
    }

    @OnClick(R.id.iv_ware)
    public void add_image(View v) {
        chooseImage();
    }

    /**
     * 选择照片
     */
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
            } else if (!new File(filePath).exists()) {
                filePath = null;
                return;
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

}
