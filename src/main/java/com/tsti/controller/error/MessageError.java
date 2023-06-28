package com.tsti.controller.error;
import java.util.List;
import java.util.Map;

public class MessageError {
    private String code;
    private List<Map<String , String>> menssage;


    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public List<Map<String, String>> getMenssage() {
        return menssage;
    }
    public void setMenssage(List<Map<String, String>> menssage) {
        this.menssage = menssage;
    }

}