package br.com.congregationreport.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.congregationreport.R;
import br.com.congregationreport.models.Group;


public class AdapterItemGroup extends ArrayAdapter<Group> implements Filterable {

    private Context context;
    private int resource;
    private List<Group> groups = new ArrayList<>();
    private boolean emptyTable;
    private boolean selectOne;
    public static int cont = 0;

    public AdapterItemGroup(Context context, int resource, List<Group> Groups) {
        super(context, resource, Groups);
        this.context = context;
        this.resource = resource;
        this.groups = Groups;
        this.emptyTable = this.groups.isEmpty();
        if (this.emptyTable) {
            this.groups = new ArrayList<>();
            this.groups.add(new Group());
        }
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        return this.getCustomView(position, view, parent);
    }


    @Override
    public View getDropDownView(int position, View view, ViewGroup parent) {
        return this.getCustomView(position, view, parent);
    }


    public View getCustomView(final int position, View view, ViewGroup parent) {
        // Pega os dados do usuario
        final Group Group = getItem(position);
        if (view == null) {
            // Ganho de performance
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(this.resource, parent, false);
        }


        // Verifica se está vazia a lista
        if (this.emptyTable) {
            TextView msgemptyTable = (TextView) view.findViewById(R.id.txtMsgEmpty);
            msgemptyTable.setText("Nenhum resultado encontrado...");
            return view;
        }
        // Pega os dados
        TextView txtId = (TextView) view.findViewById(R.id.txtId);
        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        if (Group != null) {
            txtId.setText(Group.getId().toString());
            txtName.setText(this.context.getResources().getText(R.string.group));
        }
        return view;
    }


    @Override
    public int getPosition(Group item) {
        return super.getPosition(item);
    }


    @Override
    public int getCount() {
        return this.groups.size();
    }


    @Override
    public Group getItem(int position) {
        return this.groups.get(position);
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> produtos) {
        this.groups = produtos;
    }

    public boolean isemptyTable() {
        return emptyTable;
    }

    public void setemptyTable(boolean emptyTable) {
        this.emptyTable = emptyTable;
    }


    public void setSelectOne(boolean selectOne) {
        this.selectOne = selectOne;
    }

    public boolean isSelectOne() {
        return selectOne;
    }
}