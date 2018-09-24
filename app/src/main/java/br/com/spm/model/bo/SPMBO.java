package br.com.spm.model.bo;

public class SPMBO {
    private static SPMBO instance = null;
    private String token;

    public static SPMBO getInstance() {
        if(instance == null){
            instance = new SPMBO();
        }
        return instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
