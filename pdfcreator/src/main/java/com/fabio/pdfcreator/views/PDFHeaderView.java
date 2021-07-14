package com.fabio.pdfcreator.views;

import android.content.Context;
import android.widget.LinearLayout;

import com.fabio.pdfcreator.views.basic.PDFVerticalView;
import com.fabio.pdfcreator.views.basic.PDFView;

import java.io.Serializable;

public class PDFHeaderView extends PDFVerticalView implements Serializable {

    public PDFHeaderView(Context context) {
        super(context);
    }

    @Override
    public PDFHeaderView addView(PDFView viewToAdd) {
        super.addView(viewToAdd);
        return this;
    }

    @Override
    public LinearLayout getView() {
        return (LinearLayout) super.getView();
    }
}
