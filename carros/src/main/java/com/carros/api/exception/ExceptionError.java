package com.carros.api.exception;

import java.io.Serializable;

public class ExceptionError implements Serializable {
    private  String error;

    public ExceptionError (String error){
        this.error = error;
    }
    public String getError(){
        return error;
    }
}
