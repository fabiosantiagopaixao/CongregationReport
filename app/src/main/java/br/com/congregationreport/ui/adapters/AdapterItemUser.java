package br.com.congregationreport.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.congregationreport.R;
import br.com.congregationreport.models.User;


public class AdapterItemUser extends ArrayAdapter<User> implements Filterable {

    private Context context;
    private int resource;
    private List<User> users = new ArrayList<>();
    private boolean emptyTable;
    private boolean selectOne;
    public static int cont = 0;

    public AdapterItemUser(Context context, int resource, List<User> users) {
        super(context, resource, users);
        this.context = context;
        this.resource = resource;
        this.users = users;
        this.emptyTable = this.users.isEmpty();
        if (this.emptyTable) {
            this.users = new ArrayList<>();
            this.users.add(new User());
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
        final User user = getItem(position);
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
        if (user != null) {
            txtId.setText(user.getId().toString());
            txtName.setText(
                    this.context.getResources().getText(R.string.usuario)
                            + " " + user.getName()
                            + " (" + user.getType() + ")"
            );
        }
        return view;
    }


    @Override
    public int getPosition(User item) {
        return super.getPosition(item);
    }


    @Override
    public int getCount() {
        return this.users.size();
    }


    @Override
    public User getItem(int position) {
        return this.users.get(position);
    }

    public List<User> getusers() {
        return users;
    }

    public void setusers(List<User> produtos) {
        this.users = produtos;
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