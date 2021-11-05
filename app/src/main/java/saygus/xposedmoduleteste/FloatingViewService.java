package saygus.xposedmoduleteste;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import eu.chainfire.libsuperuser.Shell;

public class FloatingViewService extends Service {

    private static RandomAccessFile raf;
    private WindowManager mWindowManager;
    private FrameLayout frameLayout;
    private RelativeLayout collapse_view;
    private LinearLayout expanded_container;
    private ImageView close_btn, close_button;
    private Button btnClose, btn1;

    private void setupMenu(){
        frameLayout = new FrameLayout(this);
        FrameLayout.LayoutParams fraLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        frameLayout.setLayoutParams(fraLayoutParams);

        RelativeLayout root_container = new RelativeLayout(this);
        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        root_container.setLayoutParams(relativeLayoutParams);

        collapse_view = new RelativeLayout(this);
        collapse_view.setLayoutParams(relativeLayoutParams);

        ImageView collapsed_iv = new ImageView(this);
        close_btn = new ImageView(this);
        LinearLayout.LayoutParams collapsed_ivparams = new LinearLayout.LayoutParams(dp(60), dp(60));
        collapsed_ivparams.topMargin = dp(15);
        LinearLayout.LayoutParams close_btnparams = new LinearLayout.LayoutParams(dp(30), dp(30));

        close_btnparams.leftMargin = dp(55);
        close_btnparams.topMargin = dp(10);
        collapsed_iv.setLayoutParams(collapsed_ivparams);
        collapsed_iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_men));
        close_btn.setLayoutParams(close_btnparams);
        close_btn.setImageDrawable(getResources().getDrawable(R.drawable.iconi_close));



        collapse_view.addView(collapsed_iv);
        collapse_view.addView(close_btn);


        expanded_container = new LinearLayout(this);
        LinearLayout.LayoutParams expanded_containerParams = new LinearLayout.LayoutParams(dp(200), LinearLayout.LayoutParams.WRAP_CONTENT);
        expanded_container.setLayoutParams(expanded_containerParams);
        expanded_container.setOrientation(LinearLayout.VERTICAL);
        expanded_container.setBackgroundColor(Color.parseColor("#414040"));
        expanded_container.setVisibility(View.GONE);

        LinearLayout fechar_expanded_imageview = new LinearLayout(this);
        LinearLayout.LayoutParams fechar_expanded_imageviewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp(30));
        fechar_expanded_imageview.setLayoutParams(fechar_expanded_imageviewParams);
        fechar_expanded_imageview.setGravity(Gravity.RIGHT);


        LinearLayout linearLayoutPrincipal = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParamsLinearLayoutPrincipal = new LinearLayout.LayoutParams(dp(200), LinearLayout.LayoutParams.WRAP_CONTENT);

        linearLayoutPrincipal.setLayoutParams(layoutParamsLinearLayoutPrincipal);
        linearLayoutPrincipal.setBackgroundColor(Color.parseColor("#414040"));
        linearLayoutPrincipal.setOrientation(LinearLayout.VERTICAL);
        //linearLayoutPrincipal.setPadding(dp(5),0,dp(5),0);

        TextView platinmodsTV = new TextView(this);
        LinearLayout.LayoutParams platinmodsTV_Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        platinmodsTV_Params.gravity = Gravity.CENTER_HORIZONTAL;
        platinmodsTV_Params.topMargin = dp(15);
        platinmodsTV_Params.bottomMargin = dp(5);
        platinmodsTV.setLayoutParams(platinmodsTV_Params);
        platinmodsTV.setText("MUSK MODS ");
        platinmodsTV.setTypeface(null, Typeface.BOLD);
        platinmodsTV.setTextColor(Color.parseColor("#ffffff"));

        ScrollView scrollView = new ScrollView(this);
        LinearLayout.LayoutParams scrollView_Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp(152));
        scrollView.setLayoutParams(scrollView_Params);


        /// LINEARLAYOUT COM OS BOTÕES DE FATO


        LinearLayout linearLayout_com_botoes = new LinearLayout(this);
        LinearLayout.LayoutParams linearLayout__com_botoes_Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout_com_botoes.setLayoutParams(linearLayout__com_botoes_Params);
        linearLayout_com_botoes.setOrientation(LinearLayout.VERTICAL);

        // params padrão dos bottoes
        LinearLayout.LayoutParams btns_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp(33));
        btns_params.topMargin = dp(5);
        btns_params.leftMargin = dp(5);
        btns_params.rightMargin = dp(5);
        int backgroundColor = Color.parseColor("#e90909");
        int bold = Typeface.BOLD;
        int textSize = 12;
        int textColor = Color.parseColor("#ffffff");
        // params padrão dos bottoes

        //params padrão dos seekbar
        LinearLayout.LayoutParams seekbar_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        seekbar_params.topMargin = dp(5);
        seekbar_params.leftMargin = dp(5);
        seekbar_params.rightMargin = dp(5);
        //params padrão dos seekbar

        //params padrão dos textview
        LinearLayout.LayoutParams txt_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        txt_params.leftMargin = dp(5);
        txt_params.rightMargin = dp(5);
        //


        btn1 = new Button(this);
        btn1.setLayoutParams(btns_params);
        btn1.setTextSize(textSize);
        btn1.setTypeface(null, bold);
        btn1.setBackgroundColor(backgroundColor);
        btn1.setTextColor(textColor);
        btn1.setText("SPEED HACK OFF");

        linearLayout_com_botoes.addView(btn1);
         TextView nomeJogoVersao = new TextView(this);
        LinearLayout.LayoutParams nomeJogoVersao_Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        nomeJogoVersao_Params.gravity = Gravity.CENTER;
        nomeJogoVersao_Params.topMargin = dp(12);
        nomeJogoVersao_Params.bottomMargin = dp(12);
        nomeJogoVersao.setLayoutParams(nomeJogoVersao_Params);
        nomeJogoVersao.setText("Free Fire v1.64.6");
        nomeJogoVersao.setTextColor(Color.parseColor("#ffffff"));

        btnClose =new Button(this);
        LinearLayout.LayoutParams btnClose_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dp(33));
        btnClose.setLayoutParams(btnClose_params);
        btnClose.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        btnClose.setTypeface(null, bold);
        btnClose.setBackgroundColor(backgroundColor);
        btnClose.setTextColor(textColor);
        btnClose.setText("CLOSE"); //SAIR DA PARTIDA


        scrollView.addView(linearLayout_com_botoes);
        linearLayoutPrincipal.addView(platinmodsTV);
        linearLayoutPrincipal.addView(scrollView);
        linearLayoutPrincipal.addView(nomeJogoVersao);
        linearLayoutPrincipal.addView(btnClose);

        expanded_container.addView(linearLayoutPrincipal);


        root_container.addView(collapse_view);
        root_container.addView(expanded_container);
        frameLayout.addView(root_container);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            String payload_source = this.getApplicationInfo().nativeLibraryDir + File.separator  +"libsaygus.so";

            String payload_dest = "/data/local/tmp/libsaygus.so";

            Shell.Pool.SU.run(new String[] {"cp " + payload_source + " " + payload_dest, "chmod 777 " + payload_dest});
        } catch (Shell.ShellDiedException e) {
            e.printStackTrace();
        }


        setupMenu();
        collapse_view.setAlpha(0.8f);
        expanded_container.setAlpha(0.8f);
        int flag;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1){
            flag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
        else {
            flag = WindowManager.LayoutParams.TYPE_PHONE;
        }

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,flag, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.addView(frameLayout, params);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = null;
                AlertDialog alertBuilder = new AlertDialog.Builder(getBaseContext())
                        .setMessage("\n\tDeseja fechar?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FloatingViewService.this.stopSelf();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();

                int flag;
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1){
                    flag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                }
                else {
                    flag = WindowManager.LayoutParams.TYPE_PHONE;
                }
                alertBuilder.getWindow().setType(flag);
                alertBuilder.show();


            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collapse_view.setVisibility(View.VISIBLE);
                expanded_container.setVisibility(View.GONE);
            }
        });

        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);
                        if (Xdiff < 10 && Ydiff < 10) {
                            if (isViewCollapsed()) {
                                collapse_view.setVisibility(View.GONE);
                                expanded_container.setVisibility(View.VISIBLE);
                            }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(frameLayout, params);
                        return true;
                }
                return false;
            }
        });


        try {
            raf = new RandomAccessFile("/sdcard/sound_tisk.config", "rw");
        } catch (FileNotFoundException e) {
            Toast.makeText(getBaseContext(), "Arquivo do jogo não encontrado abra o jogo primeiro!", Toast.LENGTH_LONG).show();

        }

        btn1.setOnClickListener(new View.OnClickListener() {
            private boolean isActive = true;
            @Override
            public void onClick(View v) {
                if (isActive){

                    writeMod("cp1");
                    btn1.setText("SPEED HACK ON");
                    btn1.setBackgroundColor(Color.parseColor("#FF378BB7"));
                    isActive = false;
                }
                else {
                    writeMod("cp0");
                    btn1.setText("SPEED HACK OFF");
                    isActive = true;
                    btn1.setBackgroundColor(Color.parseColor("#FFE90909"));
                }
            }
        });



    }

    private boolean isViewCollapsed() {
        return frameLayout == null || collapse_view.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (frameLayout != null) this.mWindowManager.removeView(frameLayout);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private int dp(int value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }

    private void writeMod(String hex) {
        try {
            raf.seek(0);
            raf.write(hex.getBytes());
            Log.i("Easy Log", "writeMod: "+ raf.readUTF());
        } catch (IOException paramString1) {
            paramString1.printStackTrace();
        }
    }
}
