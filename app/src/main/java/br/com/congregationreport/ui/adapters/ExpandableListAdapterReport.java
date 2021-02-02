package br.com.congregationreport.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import br.com.congregationreport.R;
import br.com.congregationreport.models.Report;
import br.com.congregationreport.util.UtilConstants;

public class ExpandableListAdapterReport extends BaseExpandableListAdapter {

    private List<String> lstMonths;
    private HashMap<String, List<Report>> lstItems;
    private Context context;

    public ExpandableListAdapterReport(Context context, List<String> lstMonths, HashMap<String, List<Report>> lstItems) {
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
    public Object getGroup(int groupPosition) {
        // returns a group
        return lstMonths.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // create main items (groups)
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.lv_item_group_month, null);
        }

        TextView txtMonth = (TextView) convertView.findViewById(R.id.txtMonth);

        txtMonth.setText((String) getGroup(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        // create the subitems (items of groups)

        Report report = (Report) getChild(groupPosition, childPosition);

        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (report == null) {
            view = layoutInflater.inflate(R.layout.lv_vazio, null);
            TextView txtMsgEmpty = (TextView) view.findViewById(R.id.txtMsgEmpty);
            txtMsgEmpty.setText(this.context.getResources().getString(R.string.msg_no_report));
        } else {
            view = layoutInflater.inflate(R.layout.lv_item_detail_report, null);

            TextView txtPublications = (TextView) view.findViewById(R.id.txtPublications);
            TextView txtVideos = (TextView) view.findViewById(R.id.txtVideos);
            TextView txtHour = (TextView) view.findViewById(R.id.txtHour);
            TextView txtRevisited = (TextView) view.findViewById(R.id.txtRevisited);
            TextView txtCourses = (TextView) view.findViewById(R.id.txtCourses);
            TextView txtCredit = (TextView) view.findViewById(R.id.txtCredit);

            LinearLayout llAuxiliaryPioneer = (LinearLayout) view.findViewById(R.id.llAuxiliaryPioneer);
            TextView txtAuxiliaryPioneer = (TextView) view.findViewById(R.id.txtAuxiliaryPioneer);
            LinearLayout llPreachingFifteenMinLess = (LinearLayout) view.findViewById(R.id.llPreachingFifteenMinLess);

            TextView txtNotes = (TextView) view.findViewById(R.id.txtNotes);

            try {
                txtPublications.setText(report.getPublications() == null
                        ? ""
                        : report.getPublications().toString());
                // -------------------------------------------------------------
                txtVideos.setText(report.getVideos() == null
                        ? ""
                        : report.getVideos().toString());
                // -------------------------------------------------------------
                txtHour.setText(report.getHours() == null
                        ? ""
                        : report.getHours().toString());
                // -------------------------------------------------------------
                txtRevisited.setText(report.getRevisited() == null
                        ? ""
                        : report.getRevisited().toString());
                // -------------------------------------------------------------
                txtCourses.setText(report.getBibleCourses() == null
                        ? ""
                        : report.getBibleCourses().toString());
                // -------------------------------------------------------------
                txtCredit.setText(report.getCredit() == null
                        ? ""
                        : report.getCredit().toString());
                // -------------------------------------------------------------
                llAuxiliaryPioneer.setVisibility(report.isAuxiliaryPioneer() ? View.VISIBLE : View.GONE);
                String type = report.getAuxiliaryPioneerType() == null ? ""
                        : report.getAuxiliaryPioneerType().equals(UtilConstants.HOURS_30) ? this.context.getResources().getString(R.string.thirty_hours)
                        : report.getAuxiliaryPioneerType().equals(UtilConstants.HOURS_50) ? this.context.getResources().getString(R.string.fifty_hours)
                        : "";

                txtAuxiliaryPioneer.setText(
                        view.getResources().getString(R.string.auxiliary_pioneer_in)
                                + type
                                + view.getResources().getString(R.string.this_month)
                );
                llPreachingFifteenMinLess.setVisibility(report.isPreachingFifteenMinLess() ? View.VISIBLE : View.GONE);
                // -------------------------------------------------------------
                txtNotes.setText(report.getNotes() == null
                        ? ""
                        : report.getNotes().toString());
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
