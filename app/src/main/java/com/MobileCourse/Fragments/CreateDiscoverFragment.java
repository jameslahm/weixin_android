package com.MobileCourse.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MobileCourse.Adapters.ImageAdapter;
import com.MobileCourse.Api.ApiService;
import com.MobileCourse.Api.Request.CreateDiscoverRequest;
import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.UploadResponse;
import com.MobileCourse.MainActivity;
import com.MobileCourse.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

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

public class CreateDiscoverFragment extends DialogFragment {
    public static final String TAG = "EditDialogFragment";

    public interface ConfirmCallback {
        void confirmCallback(String text, List<String> images,String video);
    }

    @BindView(R.id.cancelButton)
    TextView cancelButton;

    @BindView(R.id.confirmButton)
    Button confirmButton;

    @BindView(R.id.edit)
    EditText editText;

    @BindView(R.id.editDialogTitle)
    TextView editDialogTitleTextView;

    @BindView(R.id.uploadImage)
    Button uploadImageButton;

    @BindView(R.id.uploadVideo)
    Button uploadViewpButton;

    @BindView(R.id.images)
    RecyclerView imagesRecyclerView;

    String video;

    @BindView(R.id.video)
    PlayerView videoView;


    List<String> images = new ArrayList<>();

    SimpleExoPlayer player;

    CreateDiscoverFragment.ConfirmCallback confirmCallbackObj;

    public static CreateDiscoverFragment display( CreateDiscoverFragment.ConfirmCallback confirmCallbackObj, FragmentManager fragmentManager) {
        CreateDiscoverFragment dialogFragment = new CreateDiscoverFragment();
        dialogFragment.confirmCallbackObj = confirmCallbackObj;

        dialogFragment.show(fragmentManager, TAG);
        return dialogFragment;
    }

    @SuppressLint("CheckResult")
    public void init(){

        cancelButton.setOnClickListener((View view)->{
            dismiss();
        });
        confirmButton.setOnClickListener((View view)->{
            this.confirmCallbackObj.confirmCallback(editText.getText().toString(),images,video);
            dismiss();
        });

        ImageAdapter imageAdapter = new ImageAdapter(new ImageAdapter.ImageDiff());
        imagesRecyclerView.setAdapter(imageAdapter);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP);

        imagesRecyclerView.setLayoutManager(layoutManager);

        uploadImageButton.setOnClickListener((view)->{
            new ImagePickerHelper(new ImagePicker.Builder(getActivity())
                    .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                    .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                    .directory(ImagePicker.Directory.DEFAULT)
//                    .extension(ImagePicker.Extension.PNG)
                    .scale(600, 600)
                    .allowMultipleImages(false)
                    .enableDebuggingMode(true)).getObservable().onErrorReturnItem(new ArrayList<>()).subscribe((list)->{
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
                            images.add(url);
                            Log.e(TAG,String.valueOf(images));
                            imageAdapter.submitList(new ArrayList<>(images));
                        } else {
                            String url =  ((ApiResponse.ApiErrorResponse<UploadResponse>) response).getErrorMessage();
                            Log.e(getTag(),url);
                        }
                    });
                }
            });
        });

        player = new SimpleExoPlayer.Builder(getContext()).build();
        videoView.setPlayer(player);

        uploadViewpButton.setOnClickListener((view)->{

            new VideoPickerHelper(new VideoPicker.Builder(getActivity())
                    .mode(VideoPicker.Mode.CAMERA_AND_GALLERY)
                    .directory(VideoPicker.Directory.DEFAULT)
                    .extension(VideoPicker.Extension.MP4)
                    .enableDebuggingMode(true)).getObservable().onErrorReturnItem(new ArrayList<>()).subscribe((list)->{
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
                            video = url;
                            player.setMediaItem(MediaItem.fromUri(url));
                            player.prepare();
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
        View view = inflater.inflate(R.layout.fragment_create_discover, container, false);
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
        player.release();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }
}
