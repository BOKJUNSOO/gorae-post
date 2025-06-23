package com.gorae.gorae_post.common.exception;

public class BadParameter extends ClientError {
    public BadParameter(String errorMessage) {
        this.errorCode = "BadParameter";
        this.errorMessage = errorMessage;
    }
}