package br.com.congregationreport.ui.group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

import br.com.congregationreport.R;
import br.com.congregationreport.db.dao.GroupDAO;
import br.com.congregationreport.models.Group;
import br.com.congregationreport.ui.adapters.AdapterItemGroup;
import br.com.congregationreport.util.UtilDataMemory;

public class GroupFragment extends Fragment {

    private ListView lvGroups;
    private GroupDAO groupDAO;
    private List<Group> groups;
    private AdapterItemGroup adapterItemGroup;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.root = inflater.inflate(R.layout.fragment_simple, container, false);
        this.init();
        return this.root;
    }

    private void init() {
        try {
            this.groupDAO = UtilDataMemory.getGroupDAO(this.root.getContext());
            this.lvGroups = this.root.findViewById(R.id.lvListaData);
            this.groups = this.groupDAO.findGroups();
            this.setData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setData() {
        try {
            this.lvGroups.setAdapter(null);
            if (this.groups.isEmpty()) {
                this.adapterItemGroup = new AdapterItemGroup(
                        this.root.getContext(),
                        R.layout.lv_vazio,
                        this.groups
                );
            } else {
                this.adapterItemGroup = new AdapterItemGroup(
                        this.root.getContext(),
                        R.layout.lv_item_simple,
                        this.groups
                );
            }
            this.lvGroups.setAdapter(this.adapterItemGroup);
            this.lvGroups.requestFocus();
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }

    }
}