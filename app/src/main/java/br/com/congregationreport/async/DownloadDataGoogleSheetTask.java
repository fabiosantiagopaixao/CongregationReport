package br.com.congregationreport.async;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import br.com.congregationreport.LoginActivity;
import br.com.congregationreport.MainActivity;
import br.com.congregationreport.R;
import br.com.congregationreport.db.dao.AssistanceDAO;
import br.com.congregationreport.db.dao.GroupDAO;
import br.com.congregationreport.db.dao.AppDAO;
import br.com.congregationreport.db.dao.LoginDAO;
import br.com.congregationreport.db.dao.PublisherDAO;
import br.com.congregationreport.db.dao.ReportDAO;
import br.com.congregationreport.db.dao.SettingDAO;
import br.com.congregationreport.models.App;
import br.com.congregationreport.models.Assistance;
import br.com.congregationreport.models.Group;
import br.com.congregationreport.models.Login;
import br.com.congregationreport.models.Publisher;
import br.com.congregationreport.models.Report;
import br.com.congregationreport.models.Setting;
import br.com.congregationreport.task.BaseTask;
import br.com.congregationreport.util.HttpRequestUtil;
import br.com.congregationreport.util.Util;
import br.com.congregationreport.util.UtilDataMemory;

public class DownloadDataGoogleSheetTask extends BaseTask {

    private TextView txtMessage;
    private RelativeLayout layoutLoading;
    private String json;
    private Context context;
    private AppDAO appDAO;
    private String url;
    private Activity activity;
    private SettingDAO settingDAO;
    private GroupDAO groupDAO;
    private PublisherDAO publisherDAO;
    private ReportDAO reportDAO;
    private LoginDAO loginDAO;
    private AssistanceDAO assistanceDAO;
    private String message;

    public DownloadDataGoogleSheetTask(Context context, String url) {
        this.context = context;
        this.url = url;
        this.appDAO = UtilDataMemory.getLocalDAO(this.context);
        this.appDAO = UtilDataMemory.getLocalDAO(this.context);
        this.settingDAO = new SettingDAO(this.context);
        this.groupDAO = UtilDataMemory.getGroupDAO(this.context);
        this.publisherDAO = UtilDataMemory.getPublisherDAO(this.context);
        this.reportDAO = UtilDataMemory.getReportDAO(this.context);
        this.loginDAO = new LoginDAO(this.context);
        this.assistanceDAO = UtilDataMemory.getAssistanceDAO(this.context);
        this.activity = (Activity) this.context;
        this.message = null;
    }


    @Override
    public void setUiForLoading() {
        this.layoutLoading = (RelativeLayout) this.activity.findViewById(
                R.id.layoutLoading);
        this.txtMessage = (TextView) this.activity.findViewById(R.id.txtMessage);
        this.txtMessage.setText(this.activity.getResources().getString(R.string.msg_loading_data));
        this.layoutLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public Object callInBackground() throws Exception {
        try {
            HttpRequestUtil httpRequest = new HttpRequestUtil(
                    this.context,
                    url,
                    "GET",
                    null,
                    true,
                    true
            );
            if (httpRequest.isSucess()) {
                this.json = httpRequest.getResult();

                boolean created = this.createDataBase();
                if (!created) {
                    return 401;
                }
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
            // Chama a tela de login
            Login login = this.loginDAO.getDataLogin();
            if (login == null) {
                Intent it = new Intent(((Activity) this.context), LoginActivity.class);
                ((Activity) this.context).startActivity(it);
            } else {
                Intent it = new Intent(((Activity) this.context), MainActivity.class);
                ((Activity) this.context).startActivity(it);
            }
        } else {

            if (this.message == null) {
                Util.createMessageAlert(
                        this.context,
                        this.context.getResources().getString(R.string.msg_conectar_erro)
                );
            } else {
                Util.createMessageAlert(
                        this.context,
                        this.message
                );
            }

        }

        // Fecha a tela
        this.layoutLoading.setVisibility(View.GONE);
    }

    private boolean createDataBase() {
        try {
            JSONObject jsonData = new JSONObject(this.json);

            // Pegar data da alteração da planilha
            JSONObject jsonApp = (JSONObject) jsonData.get("app");

            App currentApp = App.convertJson(jsonApp);
            Integer version = Integer.parseInt(this.activity.getResources().getString(R.string.app_version));

            if (version != currentApp.getVersion()) {
                this.message = this.activity.getResources().getString(R.string.app_version_different);
                return false;
            }

            App appBD = appDAO.getApp();
            Date dateNow = new Date();
            boolean baixarDados = false;
            boolean saveSetting = false,
                    saveSaveGroup = false,
                    savePublisher = false,
                    saveReport = false,
                    saveAssistance = false;
            if (appBD == null) {
                currentApp.setUrl(this.url);
                currentApp.setFirstRun(true);
                baixarDados = true;
            } else if (dateNow.getTime() > appBD.getLastUpdated().getTime()) {
                baixarDados = true;
            }
            appBD = App.getUpdate(appBD, currentApp, this.url);

            if (baixarDados) {

                JSONArray jsonArray = jsonData.getJSONArray("data");


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String sheet = jsonObject.getString("sheet");
                    JSONArray jArrayDatos = jsonObject.getJSONArray("data");
                    switch (sheet) {
                        case "setting":
                            saveSetting = this.addDataSetting(jArrayDatos);
                            break;
                        case "group":
                            saveSaveGroup = this.addDataGroup(jArrayDatos);
                            break;
                        case "publisher":
                            savePublisher = this.addDataPublisher(jArrayDatos);
                            break;
                        case "report":
                            saveReport = this.addDataReport(jArrayDatos);
                            break;
                        case "assistance":
                            saveAssistance = this.addDataAssistance(jArrayDatos);
                            break;
                        default:
                            break;
                    }

                }

                boolean saved = saveSetting && saveSaveGroup && savePublisher && saveReport && saveAssistance;
                if (saved) {
                    if (appBD == null) {
                        this.appDAO.save(currentApp);
                    } else if (dateNow.getTime() > appBD.getLastUpdated().getTime()) {
                        appBD.setLastUpdated(dateNow);
                        this.appDAO.update(appBD);
                    }
                    Login login = loginDAO.getDataLogin();
                    if (login != null) {
                        Publisher publisher = publisherDAO.findPublisherByUser(login.getUserName());
                        UtilDataMemory.publisher = publisher;
                        if (publisher == null || !publisher.getPassword().equals(login.getPassword())) {
                            loginDAO.delete(login.getId());
                            UtilDataMemory.publisher = null;
                        }
                    }
                }
                return saved;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean addDataSetting(JSONArray jsonArray) {
        try {
            JSONObject data = (JSONObject) jsonArray.get(0);
            Setting setting = Setting.convertJson(data);
            Setting settingDB = settingDAO.getSetting();
            if (settingDB == null) {
                settingDAO.save(setting);
            } else {
                settingDAO.update(Setting.getUpdate(settingDB, setting));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean addDataGroup(JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject data = (JSONObject) jsonArray.get(i);
                Group group = Group.convertJson(data);
                Group groupDB = groupDAO.findGroup(group.getName());
                if (groupDB == null) {
                    groupDAO.save(group);
                } else {
                    groupDAO.update(Group.getUpdate(groupDB, group));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean addDataPublisher(JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject data = (JSONObject) jsonArray.get(i);
                Publisher publisher = Publisher.convertJson(data);
                Publisher publisherDB = publisherDAO.findPublisherByUser(publisher.getUserName());
                if (publisherDB == null) {
                    publisherDAO.save(publisher);
                } else {
                    publisherDAO.update(Publisher.getUpdate(publisherDB, publisher));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean addDataReport(JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject data = (JSONObject) jsonArray.get(i);
                Report report = Report.convertJson(data);
                Report reportDB = reportDAO.getReport(report.getId());
                if (reportDB == null) {
                    report.setId(0);
                    reportDAO.save(report);
                } else {
                    reportDAO.update(Report.getUpdate(reportDB, report));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean addDataAssistance(JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject data = (JSONObject) jsonArray.get(i);
                Assistance assistance = Assistance.convertJson(data);

                Assistance assistanceDB = assistanceDAO.getAssistance(assistance.getId());
                if (assistanceDB == null) {
                    assistance.setId(0);
                    assistanceDAO.save(assistance);
                } else {
                    assistanceDAO.update(Assistance.getUpdate(assistanceDB, assistance));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}


