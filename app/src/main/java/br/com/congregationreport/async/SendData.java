package br.com.congregationreport.async;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.congregationreport.R;
import br.com.congregationreport.db.dao.AssistanceDAO;
import br.com.congregationreport.db.dao.AppDAO;
import br.com.congregationreport.db.dao.LoginDAO;
import br.com.congregationreport.db.dao.PublisherDAO;
import br.com.congregationreport.db.dao.ReportDAO;
import br.com.congregationreport.models.App;
import br.com.congregationreport.models.Assistance;
import br.com.congregationreport.models.Login;
import br.com.congregationreport.models.Publisher;
import br.com.congregationreport.models.Report;
import br.com.congregationreport.task.BaseTask;
import br.com.congregationreport.util.HttpRequestUtil;
import br.com.congregationreport.util.UtilDataMemory;
import br.com.congregationreport.util.UtilConstants;

public class SendData extends BaseTask {

    private TextView txtMessage;
    private RelativeLayout layoutLoading;
    private Context context;
    private Activity activity;
    private AppDAO appDAO;
    private JSONObject data;
    private String type;
    private ReportDAO reportDAO;
    private AssistanceDAO assistanceDAO;
    private PublisherDAO publisherDAO;
    private LoginDAO loginDAO;
    private App app;

    public SendData(Context context, JSONObject data) {
        this.context = context;
        this.data = data;
        try {
            this.type = data.getString("type");
        } catch (JSONException e) {
        }
        this.appDAO = UtilDataMemory.getLocalDAO(this.context);
        this.publisherDAO = UtilDataMemory.getPublisherDAO(this.context);
        this.loginDAO = new LoginDAO(this.context);
        this.activity = (Activity) this.context;
    }


    @Override
    public void setUiForLoading() {
        this.layoutLoading = (RelativeLayout) this.activity.findViewById(
                R.id.layoutLoading);
        this.layoutLoading.setVisibility(View.VISIBLE);
        this.txtMessage = (TextView) this.activity.findViewById(R.id.txtMessage);

        if (this.type == UtilConstants.CREATE) {
            this.txtMessage.setText(this.context.getResources().getString(R.string.msg_creating));
        }
        if (this.type == UtilConstants.UPDATE) {
            this.txtMessage.setText(this.context.getResources().getString(R.string.msg_updating));
        }
        if (this.type == UtilConstants.DELETE) {
            this.txtMessage.setText(this.context.getResources().getString(R.string.msg_deleting));
        }
    }

    @Override
    public Object callInBackground() throws Exception {
        try {
            this.app = this.appDAO.getApp();
            String url = this.app.getUrl();

            // Save local
            String id;
            switch (this.data.getString("model")) {
                case "report":
                    this.reportDAO = new ReportDAO(this.context);
                    id = this.saveReport(Report.convertJson(this.data.getJSONObject("object")));
                    this.data.getJSONObject("object").put("id", id);
                    break;
                case "assistance":
                    this.assistanceDAO = new AssistanceDAO(this.context);
                    id = this.saveAssistance(Assistance.convertJson(this.data.getJSONObject("object")));
                    this.data.getJSONObject("object").put("id", id);
                    break;
                case "publisher":
                    if (this.type == UtilConstants.CREATE) {
                        // TODO: Implements function
                    }
                    if (this.type == UtilConstants.UPDATE) {
                        this.assistanceDAO = new AssistanceDAO(this.context);
                        id = this.updatePublisher(Publisher.convertJson(this.data.getJSONObject("object")));
                        this.data.getJSONObject("object").put("id", id);
                    }
                    break;
            }

            HttpRequestUtil httpRequest = new HttpRequestUtil(
                    this.activity,
                    url,
                    "POST",
                    data.toString(),
                    true,
                    true
            );

            if (httpRequest.getResultCode() == 200) {
                JSONObject jsonData = new JSONObject(httpRequest.getResult());
                return jsonData.getInt("status");
            }


            return httpRequest.getResultCode();
        } catch (Exception e) {
            return 401;
        }
    }

    @Override
    public void setDataAfterLoading(Object object) {
        Integer result = (Integer) object;
        if (result >= 200 && result < 300) {
            try {
                switch (this.data.getString("model")) {
                    case "report":
                        this.activity.finish();
                        UtilDataMemory.reportActivity.init();
                        break;
                    case "assistance":
                        this.activity.finish();
                        UtilDataMemory.assistanceActivity.init();
                        break;
                }
            } catch (JSONException e) {
                System.out.println(e.getMessage());
            }

        } else {
            if (result == HttpRequestUtil.NOT_FOUND) {
                Toast.makeText(
                        this.context, this.context.getResources().getString(R.string.msg_send_data_row_erro),
                        Toast.LENGTH_LONG
                ).show();
            } else {
                Toast.makeText(
                        this.context, this.context.getResources().getString(R.string.msg_send_data_erro),
                        Toast.LENGTH_LONG
                ).show();
            }
        }
        this.layoutLoading.setVisibility(View.GONE);
    }


    private String saveReport(Report report) {
        report = this.reportDAO.save(report);
        return report.getId().toString();
    }

    private String saveAssistance(Assistance assistance) {
        assistance = this.assistanceDAO.save(assistance);
        return assistance.getId().toString();
    }

    private String updatePublisher(Publisher publisher) {
        publisher = this.publisherDAO.update(publisher);

        // Save data login
        Login login = this.loginDAO.getDataLogin();
        if (login != null) {
            login.setPassword(publisher.getPassword());
            this.loginDAO.update(login);
        }

        // Save firt login
        this.app.setFirstRun(false);
        appDAO.update(this.app);

        return publisher.getId().toString();
    }


}
