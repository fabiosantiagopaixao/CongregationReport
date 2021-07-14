package br.com.congregationreport.ui.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.com.congregationreport.R;
import br.com.congregationreport.models.ButtonCustom;
import br.com.congregationreport.ui.assistance.AssistanceActivity;
import br.com.congregationreport.ui.congregationreports.CongregationReportActivity;
import br.com.congregationreport.ui.publisher.PublisherActivity;
import br.com.congregationreport.ui.report.ReportActivity;
import br.com.congregationreport.util.UtilDataMemory;

public class HomeFragment extends Fragment {

    private View root;
    private Button btnReports;
    private LinearLayout llBtnAssistance;
    private Button btnAssistance;
    private LinearLayout llActions2;
    private Button btnCongregationReport;
    private Button btnPublisher;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.root = inflater.inflate(R.layout.fragment_home, container, false);

        this.init();

        return this.root;
    }

    /*Enable options menu in this fragment*/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflate menu
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void init() {
        try {
            this.btnReports = (Button) this.root.findViewById(R.id.btnReports);
            this.llBtnAssistance = (LinearLayout) this.root.findViewById(R.id.llBtnAssistance);
            this.btnAssistance = (Button) this.root.findViewById(R.id.btnAssistance);
            this.llActions2 = (LinearLayout) this.root.findViewById(R.id.llActions2);
            this.btnCongregationReport = (Button) this.root.findViewById(R.id.btnCongregationReport);
            this.btnPublisher = (Button) this.root.findViewById(R.id.btnPublisher);
            this.initOnClickListener();
            this.removeButtons();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initOnClickListener() {
        try {
            this.btnReports.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(root.getContext(), ReportActivity.class);
                    root.getContext().startActivity(it);
                }
            });
            this.btnAssistance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(root.getContext(), AssistanceActivity.class);
                    root.getContext().startActivity(it);
                }
            });
            this.btnCongregationReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(root.getContext(), CongregationReportActivity.class);
                    root.getContext().startActivity(it);
                }
            });
            this.btnPublisher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(root.getContext(), PublisherActivity.class);
                    root.getContext().startActivity(it);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeButtons() {
        try {
            // Button assistence
            if (UtilDataMemory.publisher.isElderOrServant()) {
                this.llBtnAssistance.setVisibility(View.VISIBLE);
            }
            if (UtilDataMemory.publisher.isElder()) {
                this.llActions2.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}