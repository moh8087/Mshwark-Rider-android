package com.mshwark.moh.mshwark;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by moh on 29 سبت، 2016 م.
 */

public class LoginRequest extends StringRequest{

    private static final String LOGIN_REQUEST_URL = "http://mshwark.com/mshwark/login.php";
    private Map<String, String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
