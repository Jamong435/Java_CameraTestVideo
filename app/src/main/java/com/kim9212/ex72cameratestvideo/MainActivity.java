package com.kim9212.ex72cameratestvideo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {


    VideoView vv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        vv=findViewById(R.id.vv);

        //동적퍼미션
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED);
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},200);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode){
        case 200:
            {if (grantResults[0]==PackageManager.PERMISSION_DENIED){
                Toast.makeText(this, "앱사용불가", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }
    }

    public void clickbtn(View view) {
        //비디오촬영 화면 carmera앱 실행
        Intent intent= new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent,50);}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //비디오는 용량떄문에 무조건 파일저장방식....
        //즉,무조건 uri로 캡쳐된 동영상의 경로가 옴....권장하지않음
        switch (requestCode){
            case 50:
                if(resultCode== RESULT_OK){
                    Uri uri=data.getData();
                    vv.setVideoURI(uri);

                    //비디오뷰를 클릭했을때 아래쪽에 컨트롤바 올라오도록
                    MediaController mediaController= new MediaController(this);
                    mediaController.setAnchorView(vv);
                    vv.setMediaController(mediaController);

                    //로딩이 완료되는걸듣고 시작하는걸권장
                    vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            vv.start();
                        }
                    });

                }else {
                    Toast.makeText(this, "canceled", Toast.LENGTH_SHORT).show();
                }
                break;
        }


    }
}
