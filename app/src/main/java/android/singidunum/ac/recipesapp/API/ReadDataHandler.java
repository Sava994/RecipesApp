package android.singidunum.ac.recipesapp.API;

import android.os.Handler;

public class ReadDataHandler extends Handler {
    private String json;

    public void setJson(String json) {
        this.json = json;
    }

    public String getJson() {
        return json;
    }
}
