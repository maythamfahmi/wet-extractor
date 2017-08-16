package com.itbackyard.System;

public class CustomException extends Exception implements ISystem {

    public static final long serialVersionUID = 42L;

    @Override
    public String getMessage() {
        return log.getCurrentMethodName();
    }

}
