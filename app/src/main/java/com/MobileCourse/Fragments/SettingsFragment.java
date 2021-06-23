package com.MobileCourse.Fragments;
import com.MobileCourse.Activities.AuthActivity;
import com.MobileCourse.Api.ApiService;
import com.MobileCourse.Api.Resource;
import com.MobileCourse.Api.Response.ApiResponse;
import com.MobileCourse.Api.Response.UploadResponse;
import com.MobileCourse.Models.User;
import com.MobileCourse.R;
import com.MobileCourse.Repositorys.UserRepository;
import com.MobileCourse.Utils.Constants;
import com.MobileCourse.Utils.MiscUtil;
import com.MobileCourse.ViewModels.MeViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import net.alhazmy13.mediapicker.Image.ImagePicker;
import net.alhazmy13.mediapicker.rxjava.image.ImagePickerHelper;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@AndroidEntryPoint
public class SettingsFragment  extends Fragment {

    @BindView(R.id.avatarMenu)
    ViewGroup avatarMenuViewGroup;

    @BindView(R.id.avatarImg)
    ImageView avatarImageView;

    @BindView(R.id.usernameMenu)
    ViewGroup usernameMenuViewGroup;

    @BindView(R.id.usernameText)
    TextView usernameTextView;

    @BindView(R.id.weixinIdMenu)
    ViewGroup weixinIdMenuViewGroup;

    @BindView(R.id.weixinIdText)
    TextView weixinIdTextView;

    @BindView(R.id.logOutButton)
    ViewGroup logOutButton;

    @BindView(R.id.passwordMenu)
    ViewGroup passwordMenuViewGroup;

    boolean isLogOut = false;

    MeViewModel meViewModel;

    String id;
    String username;
    String weixinId;
    String avatar;
    String password;

    public SettingsFragment(){

    }

    @SuppressLint("CheckResult")
    public void init(){
        Log.e(getTag(),String.valueOf(isLogOut));
        meViewModel = new ViewModelProvider(getActivity()).get(MeViewModel.class);
        LiveData<User> meLiveData = meViewModel.getMe();
        meLiveData.observe(getViewLifecycleOwner(), (user -> {
            if(user==null){
                if(isLogOut) {
                    Intent intent = new Intent(getContext(), AuthActivity.class);
                    startActivity(intent);
                }
            } else {
                id = user.getId();
                weixinId = user.getWeixinId();
                username = user.getUsername();
                avatar = user.getAvatar();

                weixinIdTextView.setText(weixinId);
                usernameTextView.setText(username);
                MiscUtil.loadImage(avatarImageView,avatar);
            }
        }));



        usernameMenuViewGroup.setOnClickListener((view)->{
            FragmentManager fragmentManager = getFragmentManager();
            EditDialogFragment.display("设置名字",usernameTextView.getText().toString(),(text)->{
                username = text;
                meViewModel.updateUser(id,weixinId,username,avatar,null).observe(getViewLifecycleOwner(),(resource)->{
                    if(resource!=null){
                        if(resource.status== Resource.Status.SUCCESS){
                            Toast.makeText(getContext(), "更新成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            },fragmentManager);
        });

        weixinIdMenuViewGroup.setOnClickListener((view)->{
            FragmentManager fragmentManager = getFragmentManager();
            EditDialogFragment.display("设置微信号",weixinIdTextView.getText().toString(),(text)->{
                weixinId = text;
                meViewModel.updateUser(id,weixinId,username,avatar,null).observe(getViewLifecycleOwner(),(resource)->{
                    if(resource!=null){
                        if(resource.status== Resource.Status.SUCCESS){
                            Toast.makeText(getContext(), "更新成功", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            },fragmentManager);
        });

        passwordMenuViewGroup.setOnClickListener((view)->{
            FragmentManager fragmentManager = getFragmentManager();
            EditDialogFragment.display("设置密码","",(text)->{
                password = text;
                meViewModel.updateUser(id,weixinId,username,avatar,password).observe(getViewLifecycleOwner(),(resource)->{
                    if(resource!=null){
                        if(resource.status== Resource.Status.SUCCESS){
                            Toast.makeText(getContext(), "更新成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            },fragmentManager);
        });

        avatarMenuViewGroup.setOnClickListener((view)->{
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

                    ApiService.getUploadApi().uploadFile(body).observe(getViewLifecycleOwner(),(response)->{
                        if(response instanceof ApiResponse.ApiSuccessResponse){
                            String url =  ((ApiResponse.ApiSuccessResponse<UploadResponse>) response).getBody().getUrl();
                            avatar = url;
                            meViewModel.updateUser(id,weixinId,username,avatar,null).observe(getViewLifecycleOwner(),(resource)->{
                                if(resource!=null){
                                    if(resource.status== Resource.Status.SUCCESS){
                                        Toast.makeText(getContext(), "更新成功", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            String url =  ((ApiResponse.ApiErrorResponse<UploadResponse>) response).getErrorMessage();
                            Log.e(getTag(),url);
                        }
                    });
                }
            });
        });

        logOutButton.setOnClickListener((view)->{
            isLogOut = true;
            meViewModel.logOut();
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        isLogOut=false;
    }
}
