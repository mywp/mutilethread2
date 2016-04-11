package com.example.scorpio.mutilethread2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private EditText et_path;
    private TextView tv_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_path = (EditText) findViewById(R.id.et_path);
        tv_info = (TextView) findViewById(R.id.tv_info);
    }
    
    public void download(View view){
        String path = et_path.getText().toString().trim();
        if (TextUtils.isEmpty(path)){
            Toast.makeText(this,"请输入下载的路径",Toast.LENGTH_SHORT).show();
            return;
        }else {
            HttpUtils http = new HttpUtils();
            HttpHandler handler = http.download(path, "/sdcard/xxx.apk",
                    true,//如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时重新下载。
                    true,//如果从请求返回信息中获取到文件名，下载完成后自动重命名.
                    new RequestCallBack<File>() {
                        @Override
                        public void onSuccess(ResponseInfo<File> responseInfo) {
                            tv_info.setText("downloaded:"+responseInfo.result.getPath());
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            tv_info.setText(s);
                        }
                    });
        }
    }
}
