package com.wszh.multiplethreadbyasy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private Button mLoadImageByAsycnButton;
    private Button mLoadImageByHandlerButton;
    private Button mToasButton;
    private ProgressBar mProgressBar;  //4.加进度条

    //5.
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setProgress(0);
            mProgressBar.setMax(100);
            Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
            for(int i=1;i<11;i++) {
                sleep();
                mProgressBar.setProgress(i*10);

            }
            mImageView.setImageBitmap(bitmap);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.activity_main_image);
        mLoadImageByAsycnButton = (Button) findViewById(R.id.load_image_by_asycn_button);
        mLoadImageByHandlerButton = (Button) findViewById(R.id.load_image_by_handler_button);
        mToasButton = (Button) findViewById(R.id.activity_main_toast_button);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_main_progressbar);

        mLoadImageByAsycnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadImage();   //1,2
                new LoadImageTask().execute();  //3
            }
        });


        mLoadImageByHandlerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg=new Message();
                        mHandler.sendMessage(msg);
                    }
                }).start();

                //Toast.makeText(MainActivity.this,"shishi",Toast.LENGTH_SHORT).show();
            }
        });

        mToasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"终于吐一下！！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //4.加进度条
    class LoadImageTask extends AsyncTask<Void, Integer, Bitmap> {

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            for (int i=1;i<=10;i++) {
                sleep();
                publishProgress(i*10);
            }
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            return bitmap;
        }

        /*
        private void sleep() {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        */

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mImageView.setImageBitmap(bitmap);
        }
    }

    //
}
