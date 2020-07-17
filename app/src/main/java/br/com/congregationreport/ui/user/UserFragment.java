package br.com.congregationreport.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

import br.com.congregationreport.R;
import br.com.congregationreport.db.dao.UserDAO;
import br.com.congregationreport.models.User;
import br.com.congregationreport.ui.adapters.AdapterItemUser;
import br.com.congregationreport.util.UtilDataMemory;

public class UserFragment extends Fragment {

    private ListView lvUsers;
    private UserDAO userDAO;
    private List<User> users;
    private AdapterItemUser adapterItemUser;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.root = inflater.inflate(R.layout.fragment_simple, container, false);
        this.init();
        return this.root;
    }

    private void init() {
        try {
            this.userDAO = UtilDataMemory.getUserDAO(this.root.getContext());
            this.lvUsers = this.root.findViewById(R.id.lvListaData);
            this.users = this.userDAO.findUsers();
            this.setData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setData() {
        try {
            this.lvUsers.setAdapter(null);
            if (this.users.isEmpty()) {
                this.adapterItemUser = new AdapterItemUser(
                        this.root.getContext(),
                        R.layout.lv_vazio,
                        this.users
                );
            } else {
                this.adapterItemUser = new AdapterItemUser(
                        this.root.getContext(),
                        R.layout.lv_item_simple,
                        this.users
                );
            }
            this.lvUsers.setAdapter(this.adapterItemUser);
            this.lvUsers.requestFocus();
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }

    }
}