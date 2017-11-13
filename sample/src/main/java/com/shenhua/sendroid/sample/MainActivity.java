package com.shenhua.sendroid.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenhua.sendroid.SendroidActivity;
import com.shenhua.sendroid.annotation.view.BindView;
import com.shenhua.sendroid.annotation.view.OnClick;
import com.shenhua.sendroid.annotation.view.OnLongClick;

public class MainActivity extends SendroidActivity {

    @BindView(R.id.tv)
    TextView textView;
    @BindView(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView.setText("123456789");
    }

    @OnClick({R.id.btn, R.id.btn2})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                Toast.makeText(this, "点我干嘛", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn2:
                Toast.makeText(this, "点我干嘛222222", Toast.LENGTH_SHORT).show();
//                Glide.with(this).load("https://gss0.baidu.com/94o3dSag_xI4khGko9WTAnF6hhy/zhidao/wh%3D450%2C600/sign=a99a10b771ec54e741b9121a8c08b766/42166d224f4a20a4ce86a14e9a529822730ed09b.jpg")
//                        .asGif()
//                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .into(imageView);
                break;
        }
    }

    @OnLongClick(R.id.btn2)
    boolean ck(View view) {
        Toast.makeText(this, "长按", Toast.LENGTH_SHORT).show();
        return true;
    }
}
