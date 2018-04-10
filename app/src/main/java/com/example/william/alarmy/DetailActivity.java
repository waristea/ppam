package com.example.william.alarmy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Getting clicked-item index
        Intent in = getIntent();
        int index = in.getIntExtra("com.example.william.alarmy.ITEM_INDEX", -1); // use -1 if item is not in array

        if (index > -1){
            int pic = getImage(index);
            ImageView img = (ImageView) findViewById(R.id.imageView);
            scaleImage(img, pic);
        }
    }

    private int getImage(int index){
        switch(index){
            case 0: return R.drawable.peach;
            case 1: return R.drawable.tomato;
            case 2: return R.drawable.apple;
            default: return -1;
        }
    }

    private void scaleImage(ImageView img, int pic){
        Display screen = getWindowManager().getDefaultDisplay();
        // to resize we need to change it to bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();

        // feel the boundaries of the image
        options.inJustDecodeBounds = true;
        // look at resources without drawing it
        BitmapFactory.decodeResource(getResources(), pic, options);

        // get the sizes
        int imgWidth = options.outWidth;
        int screenWidth = screen.getWidth(); // strike : deprecated

        if (imgWidth > screenWidth){
            int ratio = Math.round((float) imgWidth/(float)screenWidth);
            options.inSampleSize = ratio; // size is multiplied by ratio
        }

        options.inJustDecodeBounds = false;
        Bitmap scaledImg = BitmapFactory.decodeResource(getResources(), pic, options);
        img.setImageBitmap(scaledImg);
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Getting clicked-item index
        Intent in = getIntent();
        int index = in.getIntExtra("com.example.william.alarmy.ITEM_INDEX", -1); // use -1 if item is not in array

    }
}
