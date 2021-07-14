package br.com.congregationreport.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.print.PDFPrint;

import com.fabio.pdfcreator.activity.PDFViewerActivity;
import com.fabio.pdfcreator.utils.FileManager;
import com.fabio.pdfcreator.utils.PDFUtil;

import java.io.File;

import br.com.congregationreport.pdf.PdfViewerActivity;
import br.com.congregationreport.ui.publisher.PublisherActivity;

import com.fabio.pdfcreator.activity.PDFViewerActivity;
import com.fabio.pdfcreator.utils.FileManager;
import com.fabio.pdfcreator.utils.PDFUtil;

import android.print.PDFPrint;

public class UtilPDF {

    public static void generatePdfFromHtlm(final Context context, String html) {
        FileManager.getInstance().cleanTempFolder(context);
        // Create Temp File to save Pdf To
        final File savedPDFFile = FileManager.getInstance().createTempFile(
                context,
                "pdf",
                false
        );
        // Generate Pdf From Html
        PDFUtil.generatePDFFromHTML(context, savedPDFFile, html, new PDFPrint.OnPDFPrintListener() {
            @Override
            public void onSuccess(File file) {
                // Open Pdf Viewer
                Uri pdfUri = Uri.fromFile(savedPDFFile);

                Intent intentPdfViewer = new Intent(context, PdfViewerActivity.class);
                intentPdfViewer.putExtra(PdfViewerActivity.PDF_FILE_URI, pdfUri);

                Activity activity = (Activity) context;
                activity.startActivityForResult(intentPdfViewer, 1);
            }

            @Override
            public void onError(Exception exception) {
                exception.printStackTrace();
            }
        });
    }
}
