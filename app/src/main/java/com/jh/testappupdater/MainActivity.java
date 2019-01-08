package com.jh.testappupdater;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.king.app.dialog.AppDialog;
import com.king.app.dialog.AppDialogConfig;
import com.king.app.updater.AppUpdater;
import com.king.app.updater.UpdateConfig;
import com.king.app.updater.callback.AppUpdateCallback;
import com.king.app.updater.callback.UpdateCallback;

import java.io.File;

/**
 * apk版本更新的测试demo
 *
 * AppUpdater一个专注于App更新，一键傻瓜式集成App版本升级的开源库。(无需担心通知栏适配；无需担心重复点击下载；
 * 无需担心App安装等问题；这些AppUpdater都已帮您处理好。)
 * https://github.com/jenly1314/AppUpdater
 *
 * 是否是最新版本，接下来要调试的，验证即可！
 */
public class MainActivity extends AppCompatActivity {

    private final Object mLock = new Object();

    private String mUrl = "https://raw.githubusercontent.com/jenly1314/AppUpdater/master/app/release/app-release.apk";
    //    private String mUrl = "https://pro-app-qn.fir.im/1ddfe25998acd3d861d746101e6e079e1611b666.apk?attname=app-release.apk_1.2.apk";

    private ProgressBar progressBar;

    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

    }

    private Context getContext() {
        return this;
    }

    /**
     * 显示toast
     * @param text
     */
    private void showToast(String text) {
        if(toast == null){
            synchronized (mLock){
                if (toast == null){
                    toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
                }
            }
        }
        toast.setText(text);
        toast.show();
    }

    public void OnClick(View view){
        switch (view.getId()){
            case R.id.btn1:
                clickBtn1();
                break;
            case R.id.btn2:
                clickBtn2();
                break;
            case R.id.btn3:
                clickBtn3();
                break;
            case R.id.btn4:
                clickBtn4();
                break;
            case R.id.btn5:
                clickBtn5();
                break;
            case R.id.btn6:
                clickBtn6();
                break;
            case R.id.btn7:
                clickBtn7();
                break;
        }
    }

    /**
     * 简单DialogFragment升级
     */
    private void clickBtn7() {
        AppDialogConfig config = new AppDialogConfig();
        config.setTitle("简单DialogFragment升级")
                .setOk("升级")
                .setContent("1、新增某某功能、\n2、修改某某问题、\n3、优化某某BUG、")
                .setOnClickOk(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AppUpdater.Builder()
                                .serUrl(mUrl)
                                .setFilename("AppUpdater.apk")
                                .build(getContext())
                                .start();
                        AppDialog.INSTANCE.dismissDialogFragment(getSupportFragmentManager());
                    }
                });
        AppDialog.INSTANCE.showDialogFragment(getSupportFragmentManager(), config);
    }

    /**
     * 自定义弹框升级
     */
    private void clickBtn6() {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_custom, null);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText("自定义弹框升级");
        TextView tvContent = view.findViewById(R.id.tvContent);
        tvContent.setText("1、新增某某功能、\n2、修改某某问题、\n3、优化某某BUG、");
        Button btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDialog.INSTANCE.dismissDialog();
            }
        });
        Button btnOK = view.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AppUpdater.Builder()
                        .serUrl(mUrl)
                        .setFilename("AppUpdater.apk")
                        .build(getContext())
                        .start();
                AppDialog.INSTANCE.dismissDialog();
            }
        });
        AppDialog.INSTANCE.showDialog(getContext(), view);
    }

    /**
     * 简单自定义弹框升级
     */
    private void clickBtn5() {
        AppDialogConfig config = new AppDialogConfig();
        config.setLayoutId(R.layout.dialog)
                .setOk("升级")
                .setHideCancel(true)
                .setTitle("简单自定义弹框升级")
                .setContent("1、新增某某功能、\n2、修改某某问题、\n3、优化某某BUG、")
                .setOnClickOk(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AppUpdater.Builder()
                                .serUrl(mUrl)
                                .setFilename(Environment.getExternalStorageDirectory() + "/.AppUpdater")
                                .setFilename("AppUpdater.apk")
                                .build(getContext())
                                .start();
                        AppDialog.INSTANCE.dismissDialog();
                    }
                });
        AppDialog.INSTANCE.showDialog(getContext(), AppDialog.INSTANCE.createAppDialogView(getContext(), config), false);

    }

    /**
     * 简单弹框升级
     */
    private void clickBtn4() {
        AppDialogConfig config = new AppDialogConfig();
        config.setTitle("简单弹框升级")
                .setOk("升级")
                .setContent("1、新增某某功能、\n2、修改某某问题、\n3、优化某某BUG、")
                .setOnClickOk(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AppUpdater.Builder()
                                .serUrl(mUrl)
                                .setFilename("AppUpdaterTest.apk")
                                .build(getContext())
                                .start();
                        AppDialog.INSTANCE.dismissDialog();
                    }
                });
        AppDialog.INSTANCE.showDialog(getContext(), config);
    }

    /**
     * 系统弹框升级
     */
    private void clickBtn3() {
        new AlertDialog.Builder(this)
                .setTitle("发现新版本")
                .setMessage("1、新增某某功能、\n2、修改某某问题、\n3、优化某某BUG、")
                .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new AppUpdater.Builder()
                                .serUrl(mUrl)
                                .build(getContext())
                                .setUpdateCallback(new AppUpdateCallback() {
                                    @Override
                                    public void onProgress(int progress, int total, boolean isChange) {

                                    }

                                    @Override
                                    public void onFinish(File file) {
                                        showToast("下载完成");
                                    }
                                });

                    }
                }).show();
    }

    /**
     * 一键下载并监听
     */
    private void clickBtn2() {
        UpdateConfig config = new UpdateConfig();
        config.setUrl(mUrl);
        new AppUpdater(getContext(), config)
                .setUpdateCallback(new UpdateCallback() {
                    @Override
                    public void onDownloading(boolean isDownloading) {
                        if (isDownloading) {
                            showToast("已经在下载中,请勿重复下载。");
                        }
                    }

                    @Override
                    public void onStart(String url) {
                        progressBar.setProgress(0);
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onProgress(int progress, int total, boolean isChange) {
                        if (isChange){
                            progressBar.setMax(total);
                            progressBar.setProgress(progress);
                        }
                    }

                    @Override
                    public void onFinish(File file) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancel() {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }).start();
    }

    /**
     * 简单一键后台升级
     */
    private void clickBtn1() {
        new AppUpdater(getContext(), mUrl).start();
    }


}
