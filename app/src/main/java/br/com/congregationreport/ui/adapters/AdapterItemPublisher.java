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
import br.com.congregationreport.models.Publisher;


public class AdapterItemPublisher extends ArrayAdapter<Publisher> implements Filterable {

    private Context context;
    private int resource;
    private List<Publisher> publishers = new ArrayList<>();
    private boolean emptyTable;
    private boolean selectOne;
    public static int cont = 0;

    public AdapterItemPublisher(Context context, int resource, List<Publisher> publishers) {
        super(context, resource, publishers);
        this.context = context;
        this.resource = resource;
        this.publishers = publishers;
        this.emptyTable = this.publishers.isEmpty();
        if (this.emptyTable) {
            this.publishers = new ArrayList<>();
            this.publishers.add(new Publisher());
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
        final Publisher publisher = getItem(position);
        if (view == null) {
            // Ganho de performance
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(this.resource, parent, false);
        }


        // Verifica se está vazia a lista
        if (this.emptyTable) {
            TextView msgemptyTable = (TextView) view.findViewById(R.id.txtMsgEmpty);
            msgemptyTable.setText(this.context.getResources().getString(R.string.nothing_found));
            return view;
        }
        // Pega os dados
        TextView txtId = (TextView) view.findViewById(R.id.txtId);
        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        if (publisher != null) {
            txtId.setText(publisher.getId().toString());

            String name = (publisher.getEmail() == null || publisher.getEmail() == "")
                    ? publisher.getName()
                    : publisher.getName() + " (" + publisher.getEmail() + ")";


            view.setBackgroundColor(view.getResources().getColor(R.color.transparente));
            if(publisher.getEmail() == null || publisher.getEmail() == ""){
                view.setBackgroundColor(view.getResources().getColor(R.color.colorAccent));
            }
            txtName.setText(name);
        }
        return view;
    }


    @Override
    public int getPosition(Publisher item) {
        return super.getPosition(item);
    }


    @Override
    public int getCount() {
        return this.publishers.size();
    }


    @Override
    public Publisher getItem(int position) {
        return this.publishers.get(position);
    }

    public List<Publisher> getpublishers() {
        return publishers;
    }

    public void setpublishers(List<Publisher> produtos) {
        this.publishers = produtos;
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