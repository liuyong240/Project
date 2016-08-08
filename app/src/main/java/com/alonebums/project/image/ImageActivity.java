package com.alonebums.project.image;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alonebums.project.R;
import com.library.base.BaseActivity;
import com.library.image.photo.bean.Image;
import com.library.image.photoview.PreviewPhotoActivity;
import com.library.image.utils.ImageUtils;
import com.library.utils.toast.ToastUtils;

import java.util.ArrayList;

public class ImageActivity extends BaseActivity {

    private ImageView mIvImg;

    private String mImgUrl = "http://www.haopic.me/wp-content/uploads/2014/10/20141015011203530.jpg";
    private String mImgUrl_1 = "http://www.haopic.me/wp-content/uploads/2016/08/20160808105305131.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_image;
    }

    @Override
    public void initUI() {
        mIvImg = (ImageView) findViewById(R.id.iv_image_img);
    }

    @Override
    public void initLogic() {
        addOnClick(R.id.btn_image_show);
        addOnClick(R.id.btn_image_circle);

        addOnClick(mIvImg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_image_show:
                //显示图片
                ImageUtils.displayImage(this, mImgUrl, mIvImg);
                //ImageUtils.displayImage(this, R.drawable.icon_image_show, mIvImg);
                break;
            case R.id.btn_image_circle:
                //圆形图片
                ImageUtils.displayCircleImage(this, R.drawable.icon_image_show, mIvImg);
                //ImageUtils.displayCircleImage(this, mImgUrl, mIvImg);
                break;
            case R.id.iv_image_img:
                Intent intent = new Intent(this, PreviewPhotoActivity.class);
                ArrayList<Image> arrayList = new ArrayList<>();
                Image image = new Image();
                image.setImagePath(mImgUrl);
                image.setThumbnailPath(mImgUrl);
                arrayList.add(image);

                image = new Image();
                image.setImagePath(mImgUrl_1);
                image.setThumbnailPath(mImgUrl_1);
                arrayList.add(image);

                intent.putParcelableArrayListExtra(PreviewPhotoActivity.IMAGE_SELECT, arrayList);
                startActivityForResult(intent, 100);
                //startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            if (100 == requestCode) {
                ArrayList<Image> arrayList = data.getParcelableArrayListExtra(PreviewPhotoActivity.IMAGE_SELECT);
                if(null != arrayList){
                    ToastUtils.showLongMsg(this, "the select image size : " + arrayList.size());
                }
            }
        }
    }
}
