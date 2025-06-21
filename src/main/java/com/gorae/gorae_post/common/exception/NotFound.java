package com.gorae.gorae_post.common.exception;

public class NotFound extends ClientError{
    public NotFound(String message) {
        this.errorCode = "NotFound";
        this.errorMessage = message;
    }
}
