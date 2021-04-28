package com.MobileCourse.Api;

import org.jetbrains.annotations.NotNull;

public class Resource<T> {
    public enum Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    @NotNull
    public final Status status;

    @NotNull
    public final T data;

    @NotNull
    public final String message;

    public Resource(@NotNull Status status,@NotNull T data,@NotNull String message){
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static<T> Resource<T> success(@NotNull T data){
        return new Resource<>(Status.SUCCESS,data,null);
    }

    public static <T> Resource<T> error(@NotNull T data,@NotNull String message){
        return new Resource<>(Status.ERROR,data,message);
    }

    public static <T> Resource<T> loading(@NotNull T data){
        return new Resource<>(Status.LOADING,data,null);
    }
}
