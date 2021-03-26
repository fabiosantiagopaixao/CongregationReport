package br.com.congregationreport.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpRequestUtil {

    private final Context context;
    private final String url;
    private final String type;
    private final boolean doInput;
    private final boolean doOutput;
    private final String mimeType;
    private final String accept;
    private final String response;
    private String result;
    private String resultErro;
    private int resultCode;
    private String resultMessage;
    private static final String DELETE = "DELETE";
    public static int NOT_FOUND = 404;

    /**
     * Connect request
     *
     * @param context
     * @param url
     * @param type          GET | POST | PUT | PACH | DELETE
     * @param response
     * @param doInput
     * @param doOutput
     */
    @SuppressLint("NewApi")
    public HttpRequestUtil(
            Context context,
            String url,
            String type,
            String response,
            boolean doInput,
            boolean doOutput
    ) {
        this.context = context;
        this.url = url;
        this.type = type;
        this.response = response;
        this.doInput = doInput;
        this.doOutput = doOutput;
        this.mimeType = "application/json";
        this.accept = "application/json";
        this.connect();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public HttpRequestUtil connect() {
        try {
            // PUT image on S3
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(this.type);
            if (this.accept != null) {
                conn.setRequestProperty("Accept", this.accept);
            }
            if (this.mimeType != null) {
                conn.setRequestProperty("Content-Type", this.mimeType);
            }
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches (false);
            if (this.type != DELETE) {
                //conn.setDoOutput(this.doOutput);
                //conn.setDoInput(this.doInput);
                if (this.doOutput && (this.response != null && !this.response.isEmpty())) {
                    DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());
                    dataOutputStream.write(this.response.getBytes());
                    dataOutputStream.flush();
                    dataOutputStream.close();
                }
            }
            this.resultCode = conn.getResponseCode();
            this.resultMessage = conn.getResponseMessage();
            StringBuilder content = null;
            if ((this.resultCode >= 200 && this.resultCode < 300) && this.doInput) {
                if (this.type != DELETE) {
                    try {
                        if (conn.getInputStream() != null) {

                            try (BufferedReader in = new BufferedReader(
                                    new InputStreamReader(conn.getInputStream()))) {
                                String line;
                                content = new StringBuilder();
                                while ((line = in.readLine()) != null) {
                                    content.append(line);
                                    content.append(System.lineSeparator());
                                }
                            }
                        }
                    } catch (IOException ex) {
                        System.out.println("Error: " + ex.getMessage());
                        this.resultCode = 401;
                    }
                    this.result = content.toString();
                }
            } else {
                try {
                    if (conn.getErrorStream() != null) {
                        try (BufferedReader in = new BufferedReader(
                                new InputStreamReader(conn.getErrorStream()))) {
                            String line;
                            content = new StringBuilder();
                            while ((line = in.readLine()) != null) {
                                content.append(line);
                                content.append(System.lineSeparator());
                            }
                        }
                    }
                } catch (IOException ex) {
                    System.out.println("Error: " + ex.getMessage());
                    this.resultCode = 401;
                }
                this.resultErro = content.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public static  void saveImageFromUrl(String mURL, String ofile) throws Exception {
        InputStream in = null;
        FileOutputStream out = null;
        try {
            URL url = new URL(mURL);
            URLConnection urlConn = url.openConnection();
            in = urlConn.getInputStream();
            out = new FileOutputStream(ofile);
            int c;
            byte[] b = new byte[1024];
            while ((c = in.read(b)) != -1)
                out.write(b, 0, c);
        } finally {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
        }
    }

    public boolean isSucess() {
        return this.getResultCode() >= 200 && this.getResultCode() < 300;
    }

    public String getResult() {
        return result;
    }

    public int getResultCode() {
        return resultCode;
    }


    public String getResultMessage() {
        return resultMessage;
    }

    public String getResultErro() {
        return resultErro;
    }
}
