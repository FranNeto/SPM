package br.com.spm.model.domain;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import br.com.spm.R;
import br.com.spm.SPMApplication;
import okhttp3.ResponseBody;

public class ResponseRequest {

    public static final String NO_CONNECTION = "222";
    public static final String UNEXPECTED = "333";

    @SerializedName("type")
    private String code;

    @SerializedName("message")
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static final ResponseRequest getConnectionError() {
        final ResponseRequest error = new ResponseRequest();
        error.setCode(NO_CONNECTION);
        error.setMessage(SPMApplication.getInstance().getString(R.string.error_msg_no_network));
        return error;
    }

    public static final ResponseRequest getResponseError(final ResponseBody responseBody) {
        ResponseRequest error;
        try {
            error = new Gson().fromJson(responseBody.string(), ResponseRequest.class);
        } catch (Exception e) {
            error = getGsonParseError();
        }
        return error;
    }

    public static final ResponseRequest getGsonParseError() {
        final ResponseRequest error = new ResponseRequest();
        error.setCode("Error to parse Gson");
        error.setMessage("The return is not compatible with Object");
        return getExceptionError();
    }

    public static final ResponseRequest getExceptionError() {
        final ResponseRequest error = new ResponseRequest();
        error.setCode(UNEXPECTED);
        error.setMessage(SPMApplication.getInstance().getString(R.string.default_error_msg));
        return error;
    }

}
