package coder.com.app;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtTitle, txtview, txtview2;
    SeekBar SKBar;
    ImageView imghinh;
    ImageButton btnNext1, btnPlay, btnStop, btnNext2;

    ArrayList<song> arraysong;
    int position = 0;
    MediaPlayer mediaPlayer;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Khaibao();
        Addnhac();
        animation = AnimationUtils.loadAnimation(this, R.anim.dis_rotate);
        khoitaoMediaPlayer();

        btnNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if (position < 0){
                    position = arraysong.size() - 1;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                khoitaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });

        btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if (position > arraysong.size() -1){
                    position = 0;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                khoitaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });


        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                btnPlay.setImageResource(R.drawable.play);
                khoitaoMediaPlayer();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    // nếu đang phát thì pause
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                }else {
                    // dang ngung play lai
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause);
                }
                SetTimeTotal();
                UpdateTimeSong();
                imghinh.startAnimation(animation);
            }
        });

        SKBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    private void UpdateTimeSong(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhdanggio = new SimpleDateFormat("mm:ss");
                txtview.setText(dinhdanggio.format(mediaPlayer.getCurrentPosition()));
                // update progress seeBar
                SKBar.setProgress(mediaPlayer.getCurrentPosition());
                // kiểm tra bài hát ->> nếu Stop ->> Next
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if (position > arraysong.size() -1){
                            position = 0;
                        }
                        if (mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        khoitaoMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause);
                        SetTimeTotal();
                        UpdateTimeSong();
                    }
                });
                handler.postDelayed(this, 500);
            }
        },100);
    }

    private void SetTimeTotal(){
        SimpleDateFormat dingdanggio = new SimpleDateFormat("mm:ss");
        txtview2.setText(dingdanggio.format(mediaPlayer.getDuration()));
        //gán max của seekBar = mediaPlayer.getDuration()
        SKBar.setMax(mediaPlayer.getDuration());
    }

    protected void khoitaoMediaPlayer(){
        mediaPlayer = MediaPlayer.create(MainActivity.this, arraysong.get(position).getFile());
        txtTitle.setText(arraysong.get(position).getTitle());
    }

    private void Addnhac() {
        arraysong = new ArrayList<>();
        arraysong.add(new song("Đừng Như Thói Quen", R.raw.dung_nhu_thoi_qen));
        arraysong.add(new song("Chạm Đáy Nổi Đau", R.raw.cham_day_noi_dau));
        arraysong.add(new song("Cô Gái M52", R.raw.co_gai_m52));
        arraysong.add(new song("Người Âm Phủ", R.raw.nguoi_am_phu));
    }

    private void Khaibao() {
        txtview     = (TextView) findViewById(R.id.Textview);
        txtTitle    = (TextView) findViewById(R.id.TextTitle);
        txtview2    = (TextView) findViewById(R.id.Textview2);
        SKBar     = (SeekBar) findViewById(R.id.SeekBar);
        btnNext1    = (ImageButton) findViewById(R.id.ButtonNext1);
        btnPlay     = (ImageButton) findViewById(R.id.ButtonPlay);
        btnStop     = (ImageButton) findViewById(R.id.ButtonStop);
        btnNext2    = (ImageButton) findViewById(R.id.ButtonNext2);
        imghinh     = (ImageView) findViewById(R.id.imageView);

    }
}
