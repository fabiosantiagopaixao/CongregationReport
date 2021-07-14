package br.com.congregationreport.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.congregationreport.R;
import br.com.congregationreport.models.Assistance;
import br.com.congregationreport.util.UtilDateHour;

public class ExpandableListAdapterAssistance extends BaseExpandableListAdapter {

    private List<String> lstMonths;
    private HashMap<String, List<Assistance>> lstItems;
    private Context context;

    public ExpandableListAdapterAssistance(Context context, List<String> lstMonths, HashMap<String, List<Assistance>> lstItems) {
        // initialize class variables
        this.context = context;
        this.lstMonths = lstMonths;
        this.lstItems = lstItems;
    }

    @Override
    public int getGroupCount() {
        // returns groups count
        return lstMonths.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // returns items count of a group
        return lstItems.get(getGroup(groupPosition)).size();
    }

    @Override
    public String getGroup(int groupPosition) {
        // returns a group
        return lstMonths.get(groupPosition);
    }

    @Override
    public Assistance getChild(int groupPosition, int childPosition) {
        // returns a group item
        return lstItems.get(getGroup(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        // return the group id
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // returns the item id of group
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // returns if the ids are specific ( unique for each group or item)
        // or relatives
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        // create main items (groups)
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.lv_item_group_month, null);
        }

        TextView txtMonth = (TextView) view.findViewById(R.id.txtMonth);

        txtMonth.setText((String) getGroup(groupPosition));

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        // create the subitems (items of groups)

        Assistance assistance = getChild(groupPosition, childPosition);

        String group = getGroup(groupPosition);

        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (assistance == null) {
            view = layoutInflater.inflate(R.layout.lv_vazio, null);
            TextView txtMsgEmpty = (TextView) view.findViewById(R.id.txtMsgEmpty);
            txtMsgEmpty.setText(this.context.getResources().getString(R.string.msg_no_assistance));
        } else {
            view = layoutInflater.inflate(R.layout.lv_item_detail_assistance, null);
            TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            LinearLayout llBarra = (LinearLayout) view.findViewById(R.id.llBarra);
            LinearLayout ll1Linha = (LinearLayout) view.findViewById(R.id.ll1Linha);
            TextView txtId = (TextView) view.findViewById(R.id.txtId);
            TextView txtDate = (TextView) view.findViewById(R.id.txtDate);
            TextView txtAmountDeaf = (TextView) view.findViewById(R.id.txtAmountDeaf);
            TextView txtAmountTotal = (TextView) view.findViewById(R.id.txtAmountTotal);

            try {
                String labeType = "";
                if (assistance.getDate() != null) {
                    Date data = UtilDateHour.stringToDate(UtilDateHour.DATE_HOUR, assistance.getDate() + " 00:00:00");
                    labeType = UtilDateHour.getTypeAssistanceByDate(this.context, data, true);
                }


                String labelAverage =
                        assistance.getOrder() != null
                                && assistance.getOrder().equals(this.context.getResources().getString(R.string.average_a))
                                ? this.context.getResources().getString(R.string.average_for) + group
                                : labeType;

                txtTitle.setText(labelAverage);
                txtTitle.setTextColor(assistance.getOrder() != null
                        && assistance.getOrder().equals(this.context.getResources().getString(R.string.average_a))
                        ? view.getResources().getColor(R.color.colorRed)
                        : view.getResources().getColor(R.color.colorGreen));


                ll1Linha.setVisibility(assistance.getOrder() != null && assistance.getOrder().equals(this.context.getResources().getString(R.string.average_a))
                        ? View.GONE : View.VISIBLE);


                txtId.setText(assistance.getOrder() == null ? "" : assistance.getOrder());
                txtId.setVisibility(
                        assistance.getOrder() != null
                                && assistance.getOrder().equals(this.context.getResources().getString(R.string.average_a))
                                ? View.GONE : View.VISIBLE);
                llBarra.setVisibility(assistance.getOrder() != null
                        && assistance.getOrder().equals(this.context.getResources().getString(R.string.average_a))
                        ? View.GONE : View.VISIBLE);


                txtAmountDeaf.setText(assistance.getAmountDeaf() == null ? assistance.getAmountDeafM() : assistance.getAmountDeaf().toString());

                txtAmountTotal.setText(assistance.getAmountTotal() == null ? assistance.getAmountTotalM() : assistance.getAmountTotal().toString());


                txtDate.setText(assistance.getDate() == null
                        ? ""
                        : assistance.getDate());


                view.setBackgroundColor(assistance.getOrder() == null ? View.GONE
                        : assistance.getOrder().equals(
                        this.context.getResources().getString(R.string.average_a)
                ) ? view.getResources().getColor(R.color.colorYellow) : view.getResources().getColor(R.color.transparente));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // returns if the subitem (item of group) can be selected
        return true;
    }
}
