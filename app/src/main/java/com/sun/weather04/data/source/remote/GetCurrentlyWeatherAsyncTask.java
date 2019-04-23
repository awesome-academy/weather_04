package com.sun.weather04.data.source.remote;

import android.os.AsyncTask;
import android.util.Log;

import com.sun.weather04.data.model.DataResponse;
import com.sun.weather04.data.model.SevenDaysNext;
import com.sun.weather04.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetCurrentlyWeatherAsyncTask extends AsyncTask<String, Void, DataResponse> {

    private static final String LOCATION = "timezone";
    private static final String CURRENTLY = "currently";
    private static final String SEVEN_DAYS_NEXT = "daily";

    private OnFetchDataListener<DataResponse> mListener;
    private Exception mException;

    GetCurrentlyWeatherAsyncTask(OnFetchDataListener<DataResponse> onFetchDataListener) {
        mListener = onFetchDataListener;
    }

    @Override
    protected DataResponse doInBackground(String... params) {

        DataResponse dataResponse = null;
        try {
            String json = getDataFromUrl(params[0]);
            dataResponse = readDataFromCurrentlyJson(json);
            dataResponse.setSevenDaysNext(readDataFromSevenDaysNextJson(json));
        } catch (IOException iOE) {
            mException = iOE;
            iOE.printStackTrace();
        } catch (JSONException jsonE) {
            mException = jsonE;
            jsonE.printStackTrace();
        }
        return dataResponse;
    }

    @Override
    protected void onPostExecute(DataResponse dataResponse) {
        super.onPostExecute(dataResponse);
        if (dataResponse == null) {
            mListener.onError(mException);
        }
        mListener.onSuccess(dataResponse);
    }

    private String getDataFromUrl(String stringUrl) throws IOException {
        URL myUrl = new URL(stringUrl);
        String inputLine;
        HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
        connection.setRequestMethod(Constant.REQUEST_METHOD);
        connection.setReadTimeout(Constant.READ_TIMEOUT);
        connection.setConnectTimeout(Constant.CONNECTION_TIMEOUT);
        connection.connect();
        InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
        BufferedReader reader = new BufferedReader(streamReader);
        StringBuilder stringBuilder = new StringBuilder();
        while ((inputLine = reader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        return stringBuilder.toString();
    }

    private DataResponse readDataFromCurrentlyJson(String json) throws JSONException {
        DataResponse dataResponse = new DataResponse();
        try {
            JSONObject jsonObject = new JSONObject(json);
            dataResponse = new DataResponse.DataResponseBuilder()
                    .timezone(jsonObject.getString(LOCATION))
                    .currently(jsonObject.getJSONObject(CURRENTLY))
                    .build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    private List<SevenDaysNext> readDataFromSevenDaysNextJson(String json) throws JSONException {
        List<SevenDaysNext> sevenDaysNext = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(json);
            sevenDaysNext = new DataResponse.DataResponseBuilder()
                    .sevenDaysNext(jsonObject.getJSONObject(SEVEN_DAYS_NEXT));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sevenDaysNext;
    }
}
