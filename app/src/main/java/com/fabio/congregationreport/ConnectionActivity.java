package com.fabio.congregationreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fabio.congregationreport.db.dao.GroupDAO;
import com.fabio.congregationreport.db.dao.LocalDAO;
import com.fabio.congregationreport.db.dao.PublisherDAO;
import com.fabio.congregationreport.db.dao.SettingDAO;
import com.fabio.congregationreport.db.dao.UserDAO;
import com.fabio.congregationreport.models.Group;
import com.fabio.congregationreport.models.Local;
import com.fabio.congregationreport.models.Publisher;
import com.fabio.congregationreport.models.Setting;
import com.fabio.congregationreport.models.User;
import com.fabio.congregationreport.util.HttpRequestUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConnectionActivity extends AppCompatActivity {

    private Button btnConectar;
    private EditText txtUrl;
    private SettingDAO settingDAO;
    private UserDAO userDAO;
    private GroupDAO groupDAO;
    private PublisherDAO publisherDAO;
    private LocalDAO localDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        this.initComponents();
        this.initOnClickListener();
    }

    private void initComponents() {
        this.localDAO = new LocalDAO(this);
        this.settingDAO = new SettingDAO(this);
        this.userDAO = new UserDAO(this);
        this.groupDAO = new GroupDAO(this);
        this.publisherDAO = new PublisherDAO(this);
        this.btnConectar = (Button) findViewById(R.id.btnConectar);
        this.txtUrl = (EditText) findViewById(R.id.txtUrl);
    }

    private void initOnClickListener() {
        this.btnConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtUrl.getText().toString().isEmpty()) {
                    txtUrl.setError(getResources().getString(R.string.msg_conectar_url_vazia));
                } else {
                    new BaixarBancoDadosTask().execute(txtUrl.getText().toString());
                }

            }
        });
    }

    // AsyncTask<P1, P2, P3>
    // P1 = doInBackground parameter
    // P2 = onProgressUpdate parameter
    // P3 = onPostExecute parameter
    private class BaixarBancoDadosTask extends AsyncTask<String, Void, Integer> {

        private TextView txtMessage;
        private RelativeLayout layoutLoading;
        private String json;

        protected void onPreExecute() {
            this.layoutLoading = (RelativeLayout) ConnectionActivity.this.findViewById(
                    R.id.layoutLoading);
            this.txtMessage = (TextView) ConnectionActivity.this.findViewById(
                    R.id.txtMessage);
            this.txtMessage.setText("Validando dados...");
            layoutLoading.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        protected Integer doInBackground(String... params) {
            try {
                String url = params[0];
                HttpRequestUtil httpRequest = new HttpRequestUtil(
                        ConnectionActivity.this,
                        url,
                        "GET",
                        null,
                        true,
                        true
                );
                if (httpRequest.isSucess()) {
                    this.json = httpRequest.getResult();

                    this.createDataBase();

                    // Salva os dados da url
                    Local local = localDAO.save(new Local(txtUrl.getText().toString()));
                    System.out.println("Dados slavos: " + local);
                }
                return httpRequest.getResultCode();
            } catch (Exception e) {
                return 401;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            layoutLoading.setVisibility(View.GONE);
            if (result >= 200 && result < 300) {
                // Chama a tela de login
                Intent it = new Intent(ConnectionActivity.this, LoginActivity.class);
                ConnectionActivity.this.startActivity(it);
                this.cancel(true);

                // Fecha a tela
                ConnectionActivity.this.finish();
            } else {
                Toast.makeText(
                        getBaseContext(), getResources().getString(R.string.msg_conectar_erro),
                        Toast.LENGTH_LONG
                ).show();
            }
            super.onPostExecute(result);
        }

        private boolean createDataBase() {
            try {

                JSONArray jsonArray = new JSONObject(this.json).getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String sheet = jsonObject.getString("sheet");
                    JSONArray jArrayDatos = jsonObject.getJSONArray("data");
                    switch (sheet) {
                        case "setting":
                            this.addDataSetting(jArrayDatos);
                            break;
                        case "user":
                            this.addDataUser(jArrayDatos);
                            break;
                        case "group":
                            this.addDataGroup(jArrayDatos);
                            break;
                        case "publisher":
                            this.addDataPublisher(jArrayDatos);
                            break;
                        default:
                            break;
                    }

                }
                // Settings
                System.out.println(jsonArray);

                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        private void addDataSetting(JSONArray jsonArray) {
            try {
                JSONObject data = (JSONObject) jsonArray.get(0);
                Setting setting = Setting.convertJson(data);
                Setting settingDB = settingDAO.getSetting(setting.getNameCongregation());
                if (settingDB.getId() == null || settingDB.getId() == 0) {
                    settingDAO.save(setting);
                } else {
                    settingDAO.update(Setting.getUpdateSetting(settingDB, setting));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void addDataUser(JSONArray jsonArray) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject data = (JSONObject) jsonArray.get(i);
                    User user = User.convertJson(data);
                    User userDB = userDAO.getUser(user.getUserName());
                    if (userDB.getId() == null || userDB.getId() == 0) {
                        userDAO.save(user);
                    } else {
                        userDAO.update(User.getUpdateUser(userDB, user));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void addDataGroup(JSONArray jsonArray) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject data = (JSONObject) jsonArray.get(i);
                    Group group = Group.convertJson(data);
                    Group groupDB = groupDAO.getGroup(group.getName());
                    if (groupDB.getId() == null || groupDB.getId() == 0) {
                        groupDAO.save(group);
                    } else {
                        groupDAO.update(Group.getUpdateGroup(groupDB, group));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void addDataPublisher(JSONArray jsonArray) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject data = (JSONObject) jsonArray.get(i);
                    Publisher publisher = Publisher.convertJson(data);
                    if (publisher.getEmail() != null) {
                        Publisher publisherDB = publisherDAO.getPublisher(publisher.getEmail());
                        if (publisherDB.getId() == null || publisherDB.getId() == 0) {
                            publisherDAO.save(publisher);
                        } else {
                            publisherDAO.update(Publisher.getUpdatePublisher(publisherDB, publisher));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //Disables back button so user cannot skip accepting terms and conditions
    @Override
    public void onBackPressed() {
    }
}