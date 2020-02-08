package com.mshwark.moh.mshwark;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by moh on 29 سبت، 2016 م.
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://mshwark.com/mshwark/register.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String username, String mobile, String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("fullname", name);
        params.put("email", username);
        params.put("mobile", mobile);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
