package com.MobileCourse.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MobileCourse.Adapters.ImageAdapter;
import com.MobileCourse.Api.ApiService;
import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.UploadResponse;
import com.MobileCourse.R;
import com.MobileCourse.Utils.Constants;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.badge.BadgeUtils;

import net.alhazmy13.mediapicker.Image.ImagePicker;
import net.alhazmy13.mediapicker.Video.VideoPicker;
import net.alhazmy13.mediapicker.rxjava.image.ImagePickerHelper;
import net.alhazmy13.mediapicker.rxjava.video.VideoPickerHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SendActionFragment extends DialogFragment {
    public static final String TAG = "SendActionFragment";

    public interface ConfirmCallback {
        void confirmCallback(String content, String contentType);
    }

    @BindView(R.id.cancelButton)
    TextView cancelButton;

    @BindView(R.id.confirmButton)
    Button confirmButton;

    @BindView(R.id.sendImage)
    Button sendImageButton;

    @BindView(R.id.sendAudio)
    Button sendAudioButton;

    @BindView(R.id.sendVideo)
    Button sendVideoButton;

    SendActionFragment.ConfirmCallback confirmCallbackObj;

    public static SendActionFragment display(SendActionFragment.ConfirmCallback confirmCallbackObj, FragmentManager fragmentManager) {
        SendActionFragment dialogFragment = new SendActionFragment();
        dialogFragment.confirmCallbackObj = confirmCallbackObj;

        dialogFragment.show(fragmentManager, TAG);
        return dialogFragment;
    }

    @SuppressLint("CheckResult")
    public void init(){

        cancelButton.setOnClickListener((View view)->{
            dismiss();
        });

        sendImageButton.setOnClickListener((view)->{
            new ImagePickerHelper(new ImagePicker.Builder(getActivity())
                    .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                    .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                    .directory(ImagePicker.Directory.DEFAULT)
//                    .extension(ImagePicker.Extension.PNG)
                    .scale(600, 600)
                    .allowMultipleImages(false)
                    .enableDebuggingMode(true)).getObservable().subscribe((list)->{
                String path = list.get(0);
                if(path!=null){
                    //pass it like this
                    File file = new File(path);
                    RequestBody requestFile =
                            RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                    ApiService.getUploadApi().uploadFile(body).observe(this,(response)->{
                        if(response instanceof ApiResponse.ApiSuccessResponse){
                            String url =  ((ApiResponse.ApiSuccessResponse<UploadResponse>) response).getBody().getUrl();
                            confirmCallbackObj.confirmCallback(url, Constants.ContentType.IMAGE);
                            dismiss();
                        } else {
                            String url =  ((ApiResponse.ApiErrorResponse<UploadResponse>) response).getErrorMessage();
                            Log.e(getTag(),url);
                        }
                    });
                }
            });
        });


        sendVideoButton.setOnClickListener((view)->{

            new VideoPickerHelper(new VideoPicker.Builder(getActivity())
                    .mode(VideoPicker.Mode.CAMERA_AND_GALLERY)
                    .directory(VideoPicker.Directory.DEFAULT)
                    .extension(VideoPicker.Extension.MP4)
                    .enableDebuggingMode(true)).getObservable().subscribe((list)->{
                String path = list.get(0);
                if(path!=null){
                    //pass it like this
                    File file = new File(path);
                    RequestBody requestFile =
                            RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                    ApiService.getUploadApi().uploadFile(body).observe(this,(response)->{
                        if(response instanceof ApiResponse.ApiSuccessResponse){
                            String url =  ((ApiResponse.ApiSuccessResponse<UploadResponse>) response).getBody().getUrl();
                            Log.e(TAG,url);
                            confirmCallbackObj.confirmCallback(url,Constants.ContentType.VIDEO);
                            dismiss();
                        } else {
                            String url =  ((ApiResponse.ApiErrorResponse<UploadResponse>) response).getErrorMessage();
                            Log.e(getTag(),url);
                        }
                    });
                }
            });

        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_send_action, container, false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }
}
