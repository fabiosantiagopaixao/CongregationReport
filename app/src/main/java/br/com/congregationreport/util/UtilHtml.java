package br.com.congregationreport.util;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.IOException;
import java.util.Map;

import br.com.congregationreport.R;
import br.com.congregationreport.db.dao.GroupDAO;
import br.com.congregationreport.db.dao.PublisherDAO;
import br.com.congregationreport.enumerator.EnumMonth;
import br.com.congregationreport.models.AssistanceReport;
import br.com.congregationreport.models.DataReportS21;
import br.com.congregationreport.models.DataReportS88;
import br.com.congregationreport.models.Params;
import br.com.congregationreport.models.Publisher;
import br.com.congregationreport.models.Report;

public class UtilHtml {

    public static String parseHtmltoString(Context context, String fileName) {
        String html = "";
        try {
            html = Util.parseInputStreamToString(context.getAssets().open(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }

    public static String getPublisherPageTop() {
        String html = "";
        try {
            html = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "\n" +
                    "<head>\n" +
                    "\n" +
                    "\n" +
                    "<style>\n" +
                    "\tbody {\n" +
                    "        margin: 0;\n" +
                    "        padding: 0;\n" +
                    "        background-color: rgb(250, 250, 250);\n" +
                    "        font: 11pt \"Tahoma\";\n" +
                    "        margin-top: 0px;\n" +
                    "        margin-bottom: 0px;\n" +
                    "        margin-left: 0px;\n" +
                    "        margin-right: 0px;\n" +
                    "        padding: 0;\n" +
                    "        margin-top: 0px;\n" +
                    "    }\n" +
                    "    * {\n" +
                    "        box-sizing: border-box;\n" +
                    "        -moz-box-sizing: border-box;\n" +
                    "    }\n" +
                    "\tp {\n" +
                    "\t\tmargin: 0;\n" +
                    "\t\tmargin-bottom: 4px;\n" +
                    "\t}\n" +
                    "    .page {\n" +
                    "        width: 21cm;\n" +
                    "        -- min-height: 29.7cm;\n" +
                    "\t\theight: 29.3cm;\n" +
                    "        margin-top: 0;\n" +
                    "        padding: 2cm 1cm 1cm 1cm;\n" +
                    "        border: 1px rgb(211, 211, 211) solid;\n" +
                    "        border-radius: 5px;\n" +
                    "        background: #fff;\n" +
                    "        box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);\n" +
                    "    }\n" +
                    "    .subpage {\n" +
                    "        padding: 0.5cm;\n" +
                    "        height: 256mm;\n" +
                    "        overflow: hidden !important;\n" +
                    "        float: left;\n" +
                    "    }\n" +
                    "    \n" +
                    "    @page {\n" +
                    "        size: A4;\n" +
                    "        margin: 0;\n" +
                    "    }\n" +
                    "    @media print {\n" +
                    "        .page {\n" +
                    "            margin: 0;\n" +
                    "            border: initial;\n" +
                    "            border-radius: initial;\n" +
                    "            width: initial;\n" +
                    "            min-height: initial;\n" +
                    "            box-shadow: initial;\n" +
                    "            background: initial;\n" +
                    "            page-break-after: always;\n" +
                    "        }\n" +
                    "    }\n" +
                    "\t.title{\n" +
                    "\t\tfont-weight: bold;\n" +
                    "\t}\n" +
                    "\t.box{\n" +
                    "\t\tpadding: 10px;\n" +
                    "\t\tborder-width: 1px;\n" +
                    "\t\tborder-style: solid;\n" +
                    "\t\tborder-color: rgb(191 188 188);\n" +
                    "\t\tmargin-top: 10px;\n" +
                    "\t}\n" +
                    "\t.box .title {\n" +
                    "\t\tfloat: left;\n" +
                    "\t\twidth: 100%;\n" +
                    "\t\ttext-align: center;\n" +
                    "\t}\n" +
                    "\t.box p span {\n" +
                    "\t\tfont-weight: bold;\n" +
                    "\t\twidth: 105px;\n" +
                    "\t\tfloat: left;\n" +
                    "\t}\n" +
                    "\ttable {\n" +
                    "\t\tfont-family: arial;\n" +
                    "\t\tfont-size: 12pt;\n" +
                    "\t}\n" +
                    "\ttable, td, th {\n" +
                    "\t\tborder: 1px solid rgb(191 188 188); \n" +
                    "\t\tcolor: rgb(30, 29, 29);\n" +
                    "\t\tfont-size: 10pt;\n" +
                    "\t\tfont-weight: normal;\n" +
                    "\t}\n" +
                    "\ttable {\n" +
                    "\t\tborder-collapse: collapse;\n" +
                    "\t}\n" +
                    "\ttr.header th, tr.header td {\n" +
                    "\t\tpadding: 5px;\n" +
                    "\t\twidth: 100px;\n" +
                    "\t\tfont-weight: bold;\n" +
                    "\t\tcolor: #000;\n" +
                    "\t}\n" +
                    "\ttr {\n" +
                    "\t\theight: 40px;\n" +
                    "\t}\n" +
                    "\ttr.header {\n" +
                    "\t\tcolor: #000;\n" +
                    "\t\tfont-size: 10pt;\n" +
                    "\t}\n" +
                    "\tinput[type=checkbox] {\n" +
                    "         position: relative;\n" +
                    "\t       cursor: pointer;\n" +
                    "    }\n" +
                    "\tinput[type=checkbox]:before {\n" +
                    "\t\t html: \"\";\n" +
                    "\t\t display: block;\n" +
                    "\t\t position: absolute;\n" +
                    "\t\t width: 16px;\n" +
                    "\t\t height: 16px;\n" +
                    "\t\t top: 0;\n" +
                    "\t\t left: 0;\n" +
                    "\t\t border: 2px solid rgb(85, 85, 85);\n" +
                    "\t\t border-radius: 3px;\n" +
                    "\t\t background-color: white;\n" +
                    "\t}\n" +
                    "\tinput[type=checkbox]:checked:after {\n" +
                    "\t\t html: \"\";\n" +
                    "\t\t display: block;\n" +
                    "\t\t width: 5px;\n" +
                    "\t\t height: 10px;\n" +
                    "\t\t border: solid #000;\n" +
                    "\t\t border-width: 0 2px 2px 0;\n" +
                    "\t\t -webkit-transform: rotate(45deg);\n" +
                    "\t\t -ms-transform: rotate(45deg);\n" +
                    "\t\t transform: rotate(45deg);\n" +
                    "\t\t position: absolute;\n" +
                    "\t\t top: 2px;\n" +
                    "\t\t left: 6px;\n" +
                    "\t}\n" +
                    "\t.box-content {\n" +
                    "\t\tpadding: 10px;\n" +
                    "\t\tmargin-bottom: 20px;\n" +
                    "\t\tborder-radius: 10px;\n" +
                    "\t}\n" +
                    "\th1.title {\n" +
                    "\t\tmargin: 0;\n" +
                    "\t\tfloat: left;\n" +
                    "\t\tmargin-top: -50px;\n" +
                    "\t}\n" +
                    "\timg.logo {\n" +
                    "\t\twidth: 140px;\n" +
                    "\t\t/* position: absolute; */\n" +
                    "\t\t/* top: 1045px; */\n" +
                    "\t\t/* left: 1cm; */\n" +
                    "\t}\n" +
                    "\t.page-number {\n" +
                    "\t\tfont-weight: bold;\n" +
                    "\t\tfloat: right;\n" +
                    "\t\tmargin-top: 30px;\n" +
                    "\t}\n" +
                    "span.title.top.right {\n" +
                    "\t\tfloat: right;\n" +
                    "\t}" +
                    "</style>\n" +
                    "\n" +
                    "</head>\n" +
                    "\n" +
                    "\t<body>";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }

    public static String getPublisherPageInit(String title) {
        String html = "";
        try {
            html = "<div class=\"page\">\n" +
                    "\t\t\t\t<h1 class=\"title\">$P{label_title}</h1>\n" +
                    "\t\t\t\t<div class=\"subpage\">";
            html = html.replace("$P{label_title}", title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }

    public static String getPublisherContentHtml(Context context, Publisher publisher, boolean groupSelected) {
        String html = "";
        try {
            html = "<div class=\"box-content\">\n" +
                    "\t\t\t\t\t<span class=\"title top\">$P{label_name}</span>\n" +
                    "\t\t\t\t\t<span class=\"title top right\">$P{label_group}</span>" +
                    "\t\t\t\t\t<div class=\"box\">\n" +
                    "\t\t\t\t\t\t<span class=\"title\">$P{label_personal_data}</span>\n" +
                    "\t\t\t\t\t\t<p><span>$P{label_gender}</span>$P{gender}</p>\n" +
                    "\t\t\t\t\t\t<p><span>$P{label_birth}</span>$P{date_birth}</p>\n" +
                    "\t\t\t\t\t\t<p><span>$P{label_mobile}</span>$P{mobile}</p>\n" +
                    "\t\t\t\t\t\t<p><span>$P{label_email}</span>$P{email}</p>\n" +
                    "\t\t\t\t\t\t<p><span>$P{label_address}</span>$P{address}</p>\n" +
                    "\t\t\t\t\t</div>\n" +
                    "\n" +
                    "\t\t\t\t\t<div class=\"box\">\n" +
                    "\t\t\t\t\t\t<span class=\"title\">$P{label_emergency_contacts}</span>\n" +
                    "\t\t\t\t\t\t\n" +
                    "\t\t\t\t\t\t<table style=\"width:100%; margin-bottom: 20px; margin-top: 30px\">\n" +
                    "\t\t\t\t\t\t  <tr class=\"header\">\n" +
                    "\t\t\t\t\t\t\t<th>$P{label_mobile}</th>\n" +
                    "\t\t\t\t\t\t\t<th>$P{label_nota}</th> \n" +
                    "\t\t\t\t\t\t\t<th>$P{label_jehovahs_witness}</th>\n" +
                    "\t\t\t\t\t\t\t<th>$P{label_mobile}</th>\n" +
                    "\t\t\t\t\t\t\t<th style=\"width: 200px;\">$P{label_address}</th>\n" +
                    "\t\t\t\t\t\t  </tr>\n" +
                    "\t\t\t\t\t\t  <tr>\n" +
                    "\t\t\t\t\t\t\t<th>$P{contact_name1}</th>\n" +
                    "\t\t\t\t\t\t\t<th>$P{contact_nota1}</th> \n" +
                    "\t\t\t\t\t\t\t<th><input type=\"checkbox\" $P{is_jehova_witness1}></th>\n" +
                    "\t\t\t\t\t\t\t<th><a href=\"tel:$P{contact_phone1}\">$P{contact_phone1}</a></th>\n" +
                    "\t\t\t\t\t\t\t<th style=\"width: 200px;\">$P{contact_address1}</th>\n" +
                    "\t\t\t\t\t\t  </tr>\n" +
                    "\t\t\t\t\t\t</table>\n" +
                    "\t\t\t\t\t\t<table style=\"width:100%\">\n" +
                    "\t\t\t\t\t\t  <tr class=\"header\">\n" +
                    "\t\t\t\t\t\t\t<th>$P{label_contact}</th>\n" +
                    "\t\t\t\t\t\t\t<th>$P{label_nota}</th> \n" +
                    "\t\t\t\t\t\t\t<th>$P{label_jehovahs_witness}</th>\n" +
                    "\t\t\t\t\t\t\t<th>$P{label_mobile}</th>\n" +
                    "\t\t\t\t\t\t\t<th style=\"width: 200px;\">$P{label_address}</th>\n" +
                    "\t\t\t\t\t\t  </tr>\n" +
                    "\t\t\t\t\t\t  <tr>\n" +
                    "\t\t\t\t\t\t\t<th>$P{contact_name2}</th>\n" +
                    "\t\t\t\t\t\t\t<th>$P{contact_nota2}</th> \n" +
                    "\t\t\t\t\t\t\t<th><input type=\"checkbox\" $P{is_jehova_witness2}></th>\n" +
                    "\t\t\t\t\t\t\t<th><a href=\"tel:$P{contact_phone2}\">$P{contact_phone2}</a></th>\n" +
                    "\t\t\t\t\t\t\t<th style=\"width: 200px;\">$P{contact_address2}</th>\n" +
                    "\t\t\t\t\t\t  </tr>\n" +
                    "\t\t\t\t\t\t</table>\n" +
                    "\n" +
                    "\t\t\t\t\t</div>\n" +
                    "\t\t\t\t</div>";


            // Name
            html = html.replace("$P{label_name}", publisher.getFullName());
            PublisherDAO publisherDAO = new PublisherDAO(context);
            Publisher publisherSup = publisherDAO.findSupGroup(publisher.getGroupCongregation());
            if (groupSelected) {
                html = html.replace("$P{label_group}", publisher.getGroupCongregation() + " - " + publisherSup.getFullName());
            } else {
                html = html.replace("$P{label_group}", "");
            }

            // Labels
            html = html.replace("$P{label_personal_data}", context.getResources().getString(R.string.label_personal_data));
            html = html.replace("$P{label_gender}", context.getResources().getString(R.string.label_gender));
            html = html.replace("$P{label_birth}", context.getResources().getString(R.string.label_birth));
            html = html.replace("$P{label_mobile}", context.getResources().getString(R.string.label_mobile));
            html = html.replace("$P{label_email}", context.getResources().getString(R.string.label_email));
            html = html.replace("$P{label_address}", context.getResources().getString(R.string.label_address));
            html = html.replace("$P{label_emergency_contacts}", context.getResources().getString(R.string.label_emergency_contacts));

            html = html.replace("$P{label_contact}", context.getResources().getString(R.string.label_contact));
            html = html.replace("$P{label_nota}", context.getResources().getString(R.string.label_nota));
            html = html.replace("$P{label_jehovahs_witness}", context.getResources().getString(R.string.label_jehovahs_witness));


            // Personal Data
            html = html.replace("$P{gender}", publisher.getGender() == null ? "&nbsp;"
                    : publisher.getGender().equals(UtilConstants.Man)
                    ? context.getResources().getString(R.string.male)
                    : context.getResources().getString(R.string.female));
            html = html.replace("$P{date_birth}", publisher.getBirth() == null ? "&nbsp;" : publisher.getBirth());

            String mobile = publisher.getCellPhone() == null || publisher.getCellPhone().isEmpty() ? ""
                    : "<a href=\"tel:$P{mobile}\">$P{mobile}</a>".replace("$P{mobile}", publisher.getCellPhone());
            html = html.replace("$P{mobile}", mobile);

            String email = "<a href=\"mailto:$P{email}?Subject=$P{label_contact_emergency}\">$P{email}</a>";
            if (publisher.getEmail() == null) {
                email = "&nbsp;";
            } else {
                email = email.replace("$P{email}", publisher.getEmail() == null ? "&nbsp;" : publisher.getEmail());
                email = email.replace("$P{label_contact_emergency}", context.getResources().getString(R.string.label_emergency_contact));
            }
            html = html.replace("$P{email}", email);
            html = html.replace("$P{address}", publisher.getAddress() == null ? "&nbsp;" : publisher.getAddress());

            // Contact Data 1
            html = html.replace("$P{contact_name1}", publisher.getContactName1() == null ? "" : publisher.getContactName1());
            html = html.replace("$P{contact_nota1}", publisher.getContactNote1() == null ? "" : publisher.getContactNote1());
            html = html.replace("$P{is_jehova_witness1}", publisher.isJehovahsWitness1() ? "checked=\"true\"" : "");
            html = html.replace("$P{contact_phone1}", publisher.getContactPhone1() == null ? "" : publisher.getContactPhone1());
            html = html.replace("$P{contact_address1}", publisher.getContactAddress1() == null ? "" : publisher.getContactAddress1());

            // Contact Data 2
            html = html.replace("$P{contact_name2}", publisher.getContactName2() == null ? "" : publisher.getContactName2());
            html = html.replace("$P{contact_nota2}", publisher.getContactNote2() == null ? "" : publisher.getContactNote2());
            html = html.replace("$P{is_jehova_witness2}", publisher.isJehovahsWitness2() ? "checked=\"true\"" : "");
            html = html.replace("$P{contact_phone2}", publisher.getContactPhone2() == null ? "" : publisher.getContactPhone2());
            html = html.replace("$P{contact_address2}", publisher.getContactAddress2() == null ? "" : publisher.getContactAddress2());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }

    public static String getPageEnd(String numberPage) {
        String html = "";
        try {
            html = "</div>\n" +
                    "\t\t\t\t\n" +
                    "\t\t\t\t<span class=\"page-number\">$P{label_page}</span>\n" +
                    "\t\t\t</div>";
            html = html.replace("$P{label_page}", numberPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }

    public static String getReport21PageTop() {
        String html = "";
        try {
            html = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "\n" +
                    "<head>\n" +
                    "\t<style>\n" +
                    "\t\tbody {\n" +
                    "\t\t\tmargin: 0;\n" +
                    "\t\t\tpadding: 0;\n" +
                    "\t\t\tbackground-color: rgb(250, 250, 250);\n" +
                    "\t\t\tfont: 11pt \"Tahoma\";\n" +
                    "\t\t\tmargin-top: 0px;\n" +
                    "\t\t\tmargin-bottom: 0px;\n" +
                    "\t\t\tmargin-left: 0px;\n" +
                    "\t\t\tmargin-right: 0px;\n" +
                    "\t\t\tpadding: 0;\n" +
                    "\t\t\tmargin-top: 0px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t* {\n" +
                    "\t\t\tbox-sizing: border-box;\n" +
                    "\t\t\t-moz-box-sizing: border-box;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\tp {\n" +
                    "\t\t\tmargin: 0;\n" +
                    "\t\t\tmargin-bottom: 4px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.page {\n" +
                    "\t\t\twidth: 21cm;\n" +
                    "\t\t\t-- min-height: 29.7cm;\n" +
                    "\t\t\theight: 29.3cm;\n" +
                    "\t\t\tmargin-top: 0;\n" +
                    "\t\t\tpadding: 1cm 1cm 1cm 1cm;\n" +
                    "\t\t\tbackground: #fafafa;\n" +
                    "\t\t\tbox-shadow: 0 0 5px rgba(0, 0, 0, 0.1);\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.subpage {\n" +
                    "\t\t\theight: 256mm;\n" +
                    "\t\t\toverflow: hidden !important;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t@page {\n" +
                    "\t\t\tsize: A4;\n" +
                    "\t\t\tmargin: 0;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t@media print {\n" +
                    "\t\t\t.page {\n" +
                    "\t\t\t\tmargin: 0;\n" +
                    "\t\t\t\tborder: initial;\n" +
                    "\t\t\t\tborder-radius: initial;\n" +
                    "\t\t\t\twidth: initial;\n" +
                    "\t\t\t\tmin-height: initial;\n" +
                    "\t\t\t\tbox-shadow: initial;\n" +
                    "\t\t\t\tbackground: initial;\n" +
                    "\t\t\t\tpage-break-after: always;\n" +
                    "\t\t\t}\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.title {\n" +
                    "    font-weight: bold;\n" +
                    "    width: 100%;\n" +
                    "    float: left;\n" +
                    "    margin-bottom: 20px;\n" +
                    "    text-align: center;\n" +
                    "}\n" +
                    "\n" +
                    "\t\t.box {\n" +
                    "\t\t\tmargin-top: 20px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.box .title {\n" +
                    "\t\t\tcolor: rgb(252, 83, 58);\n" +
                    "\t\t\tfloat: left;\n" +
                    "\t\t\twidth: 100%;\n" +
                    "\t\t\ttext-align: center;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.box p span {\n" +
                    "\t\t\tfont-weight: bold;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\ttable {\n" +
                    "\t\t\tfont-family: arial;\n" +
                    "\t\t\tfont-size: 12pt;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\ttable,\n" +
                    "\t\ttd,\n" +
                    "\t\tth {\n" +
                    "\t\t\tborder: 1px solid rgb(191 188 188);\n" +
                    "\t\t\t;\n" +
                    "\t\t\tcolor: rgb(30, 29, 29);\n" +
                    "\t\t\tfont-weight: normal;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\ttable {\n" +
                    "\t\t\tborder-collapse: collapse;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\ttr.header th,\n" +
                    "\t\ttr.header td {\n" +
                    "\t\t\tpadding: 5px;\n" +
                    "\t\t\tfont-weight: bold;\n" +
                    "\t\t\tcolor: #000;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\ttr {\n" +
                    "\t\t\tfont-size: 0.8em;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\ttr.header {\n" +
                    "\t\t\tcolor: #000;\n" +
                    "\t\t\tfont-size: 10pt;\n" +
                    "\t\t\theight: 40px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\tinput[type=checkbox] {\n" +
                    "\t\t\tposition: relative;\n" +
                    "\t\t\tcursor: pointer;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\tinput[type=checkbox]:before {\n" +
                    "\t\t\tcontent: \"\";\n" +
                    "\t\t\tdisplay: block;\n" +
                    "\t\t\tposition: absolute;\n" +
                    "\t\t\twidth: 16px;\n" +
                    "\t\t\theight: 16px;\n" +
                    "\t\t\ttop: 0;\n" +
                    "\t\t\tleft: 0;\n" +
                    "\t\t\tborder: 2px solid rgb(85, 85, 85);\n" +
                    "\t\t\tborder-radius: 3px;\n" +
                    "\t\t\tbackground-color: white;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\tinput[type=checkbox]:checked:after {\n" +
                    "\t\t\tcontent: \"\";\n" +
                    "\t\t\tdisplay: block;\n" +
                    "\t\t\twidth: 5px;\n" +
                    "\t\t\theight: 10px;\n" +
                    "\t\t\tborder: solid #000;\n" +
                    "\t\t\tborder-width: 0 2px 2px 0;\n" +
                    "\t\t\t-webkit-transform: rotate(45deg);\n" +
                    "\t\t\t-ms-transform: rotate(45deg);\n" +
                    "\t\t\ttransform: rotate(45deg);\n" +
                    "\t\t\tposition: absolute;\n" +
                    "\t\t\ttop: 2px;\n" +
                    "\t\t\tleft: 6px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.box-content {\n" +
                    "\t\t\tpadding: 10px;\n" +
                    "\t\t\tmargin-bottom: 10px;\n" +
                    "\t\t\t/*height: 455px;*/\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\th1.title {\n" +
                    "\t\t\tcolor: rgb(6, 37, 86);\n" +
                    "\t\t\tmargin: 0;\n" +
                    "\t\t\tfloat: left;\n" +
                    "\t\t\tmargin-top: -50px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\timg.logo {\n" +
                    "\t\t\twidth: 140px;\n" +
                    "\t\t\t/* position: absolute; */\n" +
                    "\t\t\t/* top: 1045px; */\n" +
                    "\t\t\t/* left: 1cm; */\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.page-number {\n" +
                    "\t\t\tcolor: #000;\n" +
                    "\t\t\tfont-weight: bold;\n" +
                    "\t\t\tfloat: right;\n" +
                    "\t\t\tmargin-top: 30px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.check {\n" +
                    "\t\t\tfloat: right;\n" +
                    "\t\t\twidth: 130px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.check span {\n" +
                    "\t\t\tfloat: left;\n" +
                    "\t\t\tmargin-top: 3px;\n" +
                    "\t\t\tmargin-left: 10px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.check input {\n" +
                    "\t\t\tfloat: left;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.check2 {\n" +
                    "\t\t\tfloat: right;\n" +
                    "\t\t\tmargin-right: 10px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.check2 span {\n" +
                    "\t\t\tfloat: left;\n" +
                    "\t\t\tmargin-top: 3px;\n" +
                    "\t\t\tmargin-left: 10px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.check2 input {\n" +
                    "\t\t\tfloat: left;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.box.container {\n" +
                    "\t\t\tmargin-top: 40px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.month {\n" +
                    "\t\t\ttext-align: left;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.mark {\n" +
                    "\t\t\tfont-weight: bold;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\tp.bottom-table {\n" +
                    "\t\t\tmargin: 0;\n" +
                    "\t\t\tpadding: 0;\n" +
                    "\t\t\tfont-size: 0.75em;\n" +
                    "\t\t}\n" +
                    "\t</style>\n" +
                    "\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }

    public static String getReportS21PageInit() {
        String html = "";
        try {
            html = "<div class=\"page\">\n" +
                    "\t\t<div class=\"subpage\">";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }

    public static String getReportS21ContentHtml(Context context, DataReportS21 reportS21, boolean selectedTotalMonth, String year) {
        String html = "";
        try {
            html = "<div class=\"box-content\">\n" +
                    "\t\t\t\t<span class=\"title top\">$P{label_title_box}</span>\n" +
                    "\t\t\t\t<div class=\"box\">\n" +
                    "\t\t\t\t\t<p><span>$P{label_name}:</span> $P{name}</p>\n" +
                    "\t\t\t\t\t<p>\n" +
                    "\t\t\t\t\t\t<span>$P{label_birth}:</span> $P{birth}\n" +
                    "\t\t\t\t\t\t<span class=\"check\">\n" +
                    "\t\t\t\t\t\t\t<input type=\"checkbox\" $P{is_woman} />\n" +
                    "\t\t\t\t\t\t\t<span>$P{label_woman}</span>\n" +
                    "\t\t\t\t\t\t</span>\n" +
                    "\t\t\t\t\t\t<span class=\"check\">\n" +
                    "\t\t\t\t\t\t\t<input type=\"checkbox\" $P{is_man} />\n" +
                    "\t\t\t\t\t\t\t<span>$P{label_man}</span>\n" +
                    "\t\t\t\t\t\t</span>\n" +
                    "\t\t\t\t\t</p>\n" +
                    "\t\t\t\t\t<p>\n" +
                    "\t\t\t\t\t\t<span>$P{label_baptism}:</span> $P{baptism}\n" +
                    "\t\t\t\t\t\t<span class=\"check\">\n" +
                    "\t\t\t\t\t\t\t<input type=\"checkbox\" $P{is_anointed}><span>$P{label_anointed}</span>\n" +
                    "\t\t\t\t\t\t</span>\n" +
                    "\t\t\t\t\t\t<span class=\"check\">\n" +
                    "\t\t\t\t\t\t\t<input type=\"checkbox\" $P{is_others}><span>$P{label_others}</span>\n" +
                    "\t\t\t\t\t\t</span>\n" +
                    "\t\t\t\t\t</p>\n" +
                    "\t\t\t\t\t<p>\n" +
                    "\t\t\t\t\t\t<span class=\"check2\">\n" +
                    "\t\t\t\t\t\t\t<input type=\"checkbox\" $P{is_regular}>\n" +
                    "\t\t\t\t\t\t\t<span>$P{label_regular}</span>\n" +
                    "\t\t\t\t\t\t</span>\n" +
                    "\t\t\t\t\t\t<span class=\"check2\">\n" +
                    "\t\t\t\t\t\t\t<input type=\"checkbox\" $P{is_ministerial}>\n" +
                    "\t\t\t\t\t\t\t<span>$P{label_ministerial}</span>\n" +
                    "\t\t\t\t\t\t</span>\n" +
                    "\t\t\t\t\t\t<span class=\"check2\">\n" +
                    "\t\t\t\t\t\t\t<input type=\"checkbox\" $P{is_elder}>\n" +
                    "\t\t\t\t\t\t\t<span>$P{label_elder}</span>\n" +
                    "\t\t\t\t\t\t</span>\n" +
                    "\t\t\t\t\t</p>\n" +
                    "\t\t\t\t</div>";

            // Title box
            String title = context.getResources().getString(R.string.label_s21_title);
            html = html.replace("$P{label_title_box}", title);

            Publisher publisher = reportS21.getPublisher();


            // Labels
            html = html.replace("$P{label_name}", context.getResources().getString(R.string.label_name));
            html = html.replace("$P{label_birth}", context.getResources().getString(R.string.label_birth));
            html = html.replace("$P{label_woman}", context.getResources().getString(R.string.woman));
            html = html.replace("$P{label_man}", context.getResources().getString(R.string.man));

            // Values
            html = html.replace("$P{name}", publisher.getFullName());
            html = html.replace("$P{birth}", publisher.getBirth() == null ? "" : publisher.getBirth());
            html = html.replace("$P{is_woman}", publisher.getGender() == null ? "" : publisher.getGender().equals(UtilConstants.Woman) ? "checked=\"true\"" : "");
            html = html.replace("$P{is_man}", publisher.getGender() == null ? "" : publisher.getGender().equals(UtilConstants.Man) ? "checked=\"true\"" : "");
            html = html.replace("$P{baptism}", publisher.getBaptism() == null ? "" : publisher.getBaptism());
            html = html.replace("$P{is_anointed}", publisher.isAnointed() ? "checked=\"true\"" : "");
            html = html.replace("$P{is_others}", !publisher.isAnointed() && !selectedTotalMonth ? "checked=\"true\"" : "");
            html = html.replace("$P{is_regular}", publisher.isRegularPioneer() ? "checked=\"true\"" : "");
            html = html.replace("$P{is_ministerial}", publisher.isServantMinisterial() ? "checked=\"true\"" : "");
            html = html.replace("$P{is_elder}", publisher.isElder() ? "checked=\"true\"" : "");


            html = html.replace("$P{label_baptism}", context.getResources().getString(R.string.label_batism_s21).replace(":", ""));
            html = html.replace("$P{label_anointed}", context.getResources().getString(R.string.anointed));
            html = html.replace("$P{label_others}", context.getResources().getString(R.string.other_sheep));

            html = html.replace("$P{label_regular}", context.getResources().getString(R.string.regular_pioneer));
            html = html.replace("$P{label_ministerial}", context.getResources().getString(R.string.servant));
            html = html.replace("$P{label_elder}", context.getResources().getString(R.string.elder));

            html = html + getReportS21BoxContentHtml(context, reportS21, year);

            // Fim
            html = html + "</div>";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }

    private static String getReportS21BoxContentHtml(Context context, DataReportS21 reportS21, String year) {
        String html = "";
        try {
            html = "<div class=\"box container\">\n" +
                    "\n" +
                    "\t\t\t\t\t<table style=\"width:100%; margin-top: 30px\">\n" +
                    "\t\t\t\t\t\t<tr class=\"header\">\n" +
                    "\t\t\t\t\t\t\t<th style=\"width: 110px;\">$P{label_year_service} $P{year}</th>\n" +
                    "\t\t\t\t\t\t\t<th>$P{label_publications}</th>\n" +
                    "\t\t\t\t\t\t\t<th style=\"width: 100px;\">$P{label_videos}</th>\n" +
                    "\t\t\t\t\t\t\t<th>$P{label_hours}</th>\n" +
                    "\t\t\t\t\t\t\t<th>$P{label_revisits}</th>\n" +
                    "\t\t\t\t\t\t\t<th style=\"width: 60px;\">$P{label_studies}</th>\n" +
                    "\t\t\t\t\t\t\t<th>$P{label_notes}</th>\n" +
                    "\t\t\t\t\t\t</tr>";

            // Title box
            String yearText = year.equals(context.getResources().getString(R.string.label_all)) ? reportS21.getYear() : year;
            html = html.replace("$P{year}", yearText);

            // Labels
            html = html.replace("$P{label_year_service}", context.getResources().getString(R.string.label_year_service_s21));
            html = html.replace("$P{label_publications}", context.getResources().getString(R.string.label_publications_service_s21));
            html = html.replace("$P{label_videos}", context.getResources().getString(R.string.label_videos_service_s21));
            html = html.replace("$P{label_hours}", context.getResources().getString(R.string.label_hour_service_s21));
            html = html.replace("$P{label_revisits}", context.getResources().getString(R.string.label_revisited_service_s21));
            html = html.replace("$P{label_studies}", context.getResources().getString(R.string.studies));
            html = html.replace("$P{label_notes}", context.getResources().getString(R.string.label_notes_service_s21));

            Float publications = 0f, hours = 0f,
                    videos = 0f, revisited = 0f, bibleCourses = 0f, credit = 0f;
            Integer totalReports = 0;
            // Cria os meses
            int lengthList = EnumMonth.values().length + 2;
            for (int i = 0; i < lengthList; i++) {
                EnumMonth enumMonth = null;
                if (i <= 11) {
                    enumMonth = EnumMonth.values()[i];
                } else {
                    System.out.println("Teste");
                }
                String nameMonth = enumMonth == null ? ""
                        : UtilDateHour.getMonthReportByValue(context, enumMonth.getValue());

                String currentMonth = i == Report.TOTAL ? context.getResources().getString(R.string.label_total)
                        : i == Report.AVERAGE ? context.getResources().getString(R.string.label_average)
                        : nameMonth;


                Report report = null;
                if (i <= 11) {
                    report = reportS21.getReports().get(enumMonth.getName());
                }

                if (report == null) {
                    report = new Report();
                } else {
                    publications = publications + (report.getPublications() == null ? 0f : report.getPublications());
                    hours = hours + (report.getHours() == null ? 0f : report.getHours());
                    videos = videos + (report.getVideos() == null ? 0f : report.getVideos());
                    revisited = revisited + (report.getRevisited() == null ? 0f : report.getRevisited());
                    bibleCourses = bibleCourses + (report.getBibleCourses() == null ? 0f : report.getBibleCourses());
                    credit = credit + (report.getCredit() == null ? 0f : report.getCredit());
                    if (report.getHours() > 0 || report.isPreachingFifteenMinLess()) {
                        totalReports++;
                    }
                }

                String publicationsValue = i ==
                        Report.TOTAL && publications > 0 ? UtilFormatte.parseInteger(publications).toString()
                        : i == Report.AVERAGE && publications > 0 ? UtilFormatte.getFormate(publications / totalReports)
                        : report.getPublications() == 0 ? "" : report.getPublications().toString();


                String videosValue = i ==
                        Report.TOTAL && publications > 0 ? UtilFormatte.parseInteger(videos).toString()
                        : i == Report.AVERAGE && videos > 0 ? UtilFormatte.getFormate(videos / totalReports)
                        : report.getVideos() == 0 ? "" : report.getVideos().toString();

                String hoursValue = i ==
                        Report.TOTAL && publications > 0 ? UtilFormatte.parseInteger(hours).toString()
                        : i == Report.AVERAGE && hours > 0 ? UtilFormatte.getFormate(hours / totalReports)
                        : report.getHours() == 0 ? "" : report.getHours().toString();


                String revisitedValue = i ==
                        Report.TOTAL && publications > 0 ? UtilFormatte.parseInteger(revisited).toString()
                        : i == Report.AVERAGE && revisited > 0 ? UtilFormatte.getFormate(revisited / totalReports)
                        : report.getRevisited() == 0 ? "" : report.getRevisited().toString();

                String bibleCoursesValue = i ==
                        Report.TOTAL && publications > 0 ? UtilFormatte.parseInteger(bibleCourses).toString()
                        : i == Report.AVERAGE && bibleCourses > 0 ? UtilFormatte.getFormate(bibleCourses / totalReports)
                        : report.getBibleCourses() == 0 ? "" : report.getBibleCourses().toString();


                String text = i ==
                        Report.TOTAL && publications > 0 ? UtilFormatte.parseInteger(credit).toString()
                        : i == Report.AVERAGE && credit > 0 ? UtilFormatte.getFormate(credit / totalReports)
                        : report.getCredit().toString();

                String creditText = !text.equals("0") ? "[" + context.getResources().getString(R.string.credit) + " " + text + "]" : "";
                String notes = report.getNotes().isEmpty() ? creditText : report.getNotes() + " " + creditText;
                String newNotes = notes == null || notes.isEmpty() ? "" : notes.length() > 17 ? (notes.substring(0, 17) + "...") : notes;

                String mark = i == Report.TOTAL || i == Report.AVERAGE ? "mark" : "";


                // Add values
                String line = "<tr>\n" +
                        "\t\t\t\t\t\t\t<th class=\"month " + mark + "\">" + UtilFormatte.stringToUpCaseOnlyFirstLetter(currentMonth) + "</th>\n" +
                        "\t\t\t\t\t\t\t<th class=\"" + mark + "\">" + publicationsValue + "</th>\n" +
                        "\t\t\t\t\t\t\t<th class=\"" + mark + "\">" + videosValue + "</th>\n" +
                        "\t\t\t\t\t\t\t<th class=\"" + mark + "\">" + hoursValue + "</th>\n" +
                        "\t\t\t\t\t\t\t<th class=\"" + mark + "\">" + revisitedValue + "</th>\n" +
                        "\t\t\t\t\t\t\t<th class=\"" + mark + "\">" + bibleCoursesValue + "</th>\n" +
                        "\t\t\t\t\t\t\t<th class=\"" + mark + "\">" + newNotes + "</th>\n" +
                        "\t\t\t\t\t\t</tr>";

                html = html + line;
            }
            html = html + "</table><p class=\"bottom-table\">S-21-S 12/18</p></div>";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }

    public static String getReportS88PageTop(String title) {
        String html = "";
        try {
            html = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "\n" +
                    "<head>\n" +
                    "\n" +
                    "\n" +
                    "\t<style>\n" +
                    "\t\tbody {\n" +
                    "\t\t\tmargin: 0;\n" +
                    "\t\t\tpadding: 0;\n" +
                    "\t\t\tbackground-color: rgb(250, 250, 250);\n" +
                    "\t\t\tfont: 11pt \"Tahoma\";\n" +
                    "\t\t\tmargin-top: 0px;\n" +
                    "\t\t\tmargin-bottom: 0px;\n" +
                    "\t\t\tmargin-left: 0px;\n" +
                    "\t\t\tmargin-right: 0px;\n" +
                    "\t\t\tpadding: 0;\n" +
                    "\t\t\tmargin-top: 0px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t* {\n" +
                    "\t\t\tbox-sizing: border-box;\n" +
                    "\t\t\t-moz-box-sizing: border-box;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\tp {\n" +
                    "\t\t\tmargin: 0;\n" +
                    "\t\t\tmargin-bottom: 4px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.page {\n" +
                    "\t\t\twidth: 21cm;\n" +
                    "\t\t\t-- min-height: 29.7cm;\n" +
                    "\t\t\theight: 29.3cm;\n" +
                    "\t\t\tmargin-top: 0;\n" +
                    "\t\t\tpadding: 1cm 1cm 1cm 1cm;\n" +
                    "\t\t\tbackground: #fafafa;\n" +
                    "\t\t\tbox-shadow: 0 0 5px rgba(0, 0, 0, 0.1);\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.subpage {\n" +
                    "\t\t\theight: 256mm;\n" +
                    "\t\t\toverflow: hidden !important;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t@page {\n" +
                    "\t\t\tsize: A4;\n" +
                    "\t\t\tmargin: 0;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t@media print {\n" +
                    "\t\t\t.page {\n" +
                    "\t\t\t\tmargin: 0;\n" +
                    "\t\t\t\tborder: initial;\n" +
                    "\t\t\t\tborder-radius: initial;\n" +
                    "\t\t\t\twidth: initial;\n" +
                    "\t\t\t\tmin-height: initial;\n" +
                    "\t\t\t\tbox-shadow: initial;\n" +
                    "\t\t\t\tbackground: initial;\n" +
                    "\t\t\t\tpage-break-after: always;\n" +
                    "\t\t\t}\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.title {\n" +
                    "\t\t\tfont-weight: bold;\n" +
                    "\t\t\twidth: 100%;\n" +
                    "\t\t\tfloat: left;\n" +
                    "\t\t\tfont-size: 20px;\n" +
                    "\t\t\tmargin-bottom: 20px;\n" +
                    "\t\t\ttext-align: center;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.box {\n" +
                    "\t\t\tmargin-top: 20px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.box .title {\n" +
                    "\t\t\tcolor: rgb(252, 83, 58);\n" +
                    "\t\t\tfloat: left;\n" +
                    "\t\t\twidth: 100%;\n" +
                    "\t\t\ttext-align: center;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.box p span {\n" +
                    "\t\t\tfont-weight: bold;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\ttable {\n" +
                    "\t\t\tfont-family: arial;\n" +
                    "\t\t\tfont-size: 12pt;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\ttable,\n" +
                    "\t\ttd,\n" +
                    "\t\tth {\n" +
                    "\t\t\tborder: 1px solid rgb(191 188 188);\n" +
                    "\t\t\t;\n" +
                    "\t\t\tcolor: rgb(30, 29, 29);\n" +
                    "\t\t\tfont-weight: normal;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\ttable {\n" +
                    "\t\t\tborder-collapse: collapse;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\ttr.header th,\n" +
                    "\t\ttr.header td {\n" +
                    "\t\t\tpadding: 5px;\n" +
                    "\t\t\tfont-weight: bold;\n" +
                    "\t\t\tcolor: #000;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\ttr {\n" +
                    "\t\t\tfont-size: 0.8em;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\ttr.header {\n" +
                    "\t\t\tcolor: #000;\n" +
                    "\t\t\tfont-size: 10pt;\n" +
                    "\t\t\theight: 40px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\tinput[type=checkbox] {\n" +
                    "\t\t\tposition: relative;\n" +
                    "\t\t\tcursor: pointer;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\tinput[type=checkbox]:before {\n" +
                    "\t\t\tcontent: \"\";\n" +
                    "\t\t\tdisplay: block;\n" +
                    "\t\t\tposition: absolute;\n" +
                    "\t\t\twidth: 16px;\n" +
                    "\t\t\theight: 16px;\n" +
                    "\t\t\ttop: 0;\n" +
                    "\t\t\tleft: 0;\n" +
                    "\t\t\tborder: 2px solid rgb(85, 85, 85);\n" +
                    "\t\t\tborder-radius: 3px;\n" +
                    "\t\t\tbackground-color: white;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\tinput[type=checkbox]:checked:after {\n" +
                    "\t\t\tcontent: \"\";\n" +
                    "\t\t\tdisplay: block;\n" +
                    "\t\t\twidth: 5px;\n" +
                    "\t\t\theight: 10px;\n" +
                    "\t\t\tborder: solid #000;\n" +
                    "\t\t\tborder-width: 0 2px 2px 0;\n" +
                    "\t\t\t-webkit-transform: rotate(45deg);\n" +
                    "\t\t\t-ms-transform: rotate(45deg);\n" +
                    "\t\t\ttransform: rotate(45deg);\n" +
                    "\t\t\tposition: absolute;\n" +
                    "\t\t\ttop: 2px;\n" +
                    "\t\t\tleft: 6px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.box-content {\n" +
                    "\t\t\tpadding: 10px;\n" +
                    "\t\t\tmargin-bottom: 10px;\n" +
                    "\t\t\t/*height: 455px;*/\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\th1.title {\n" +
                    "\t\t\tcolor: rgb(6, 37, 86);\n" +
                    "\t\t\tmargin: 0;\n" +
                    "\t\t\tfloat: left;\n" +
                    "\t\t\tmargin-top: -50px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\timg.logo {\n" +
                    "\t\t\twidth: 140px;\n" +
                    "\t\t\t/* position: absolute; */\n" +
                    "\t\t\t/* top: 1045px; */\n" +
                    "\t\t\t/* left: 1cm; */\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.page-number {\n" +
                    "\t\t\tcolor: #000;\n" +
                    "\t\t\tfont-weight: bold;\n" +
                    "\t\t\tfloat: right;\n" +
                    "\t\t\tmargin-top: 30px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.check {\n" +
                    "\t\t\tfloat: right;\n" +
                    "\t\t\twidth: 130px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.check span {\n" +
                    "\t\t\tfloat: left;\n" +
                    "\t\t\tmargin-top: 3px;\n" +
                    "\t\t\tmargin-left: 10px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.check input {\n" +
                    "\t\t\tfloat: left;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.check2 {\n" +
                    "\t\t\tfloat: right;\n" +
                    "\t\t\tmargin-right: 10px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.check2 span {\n" +
                    "\t\t\tfloat: left;\n" +
                    "\t\t\tmargin-top: 3px;\n" +
                    "\t\t\tmargin-left: 10px;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.check2 input {\n" +
                    "\t\t\tfloat: left;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.month {\n" +
                    "\t\t\ttext-align: left;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.mark {\n" +
                    "\t\t\tfont-weight: bold;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\tp.bottom-table {\n" +
                    "\t\t\tmargin: 0;\n" +
                    "\t\t\tpadding: 0;\n" +
                    "\t\t\tfont-size: 0.75em;\n" +
                    "\t\t}\n" +
                    "\t\t.space {\n" +
                    "\t\t\theight: 100px;\n" +
                    "\t\t}\n" +
                    "\t\tspan.title.top.week {\n" +
                    "\t\t\ttext-align: left;\n" +
                    "\t\t\tmargin-bottom: 10px;\n" +
                    "\t\t\tfont-size: 14px;\n" +
                    "\t\t}\n" +
                    "\t</style>\n" +
                    "\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "\n" +
                    "\n" +
                    "\t<div class=\"page\">\n" +
                    "\t\t<div class=\"subpage\">\n" +
                    "\t\t\t<span class=\"title top\">#P{label_title}</span>";
            html = html.replace("#P{label_title}", title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }

    public static String getReportS88Content(
            Context context,
            DataReportS88 reportS88,
            int yearSelected,
            boolean addBottom
    ) {
        String html = "";
        try {
            String contentTop = "<div class=\"box-content\">\n" +
                    "\t\t\t\t<span class=\"title top week\">#P{label_title_week}</span>\n" +
                    "\n" +
                    "\t\t\t\t<div class=\"box container\">\n" +
                    "\n" +
                    "\t\t\t\t\t<table style=\"width:100%; margin-top: 0px\">\n" +
                    "\t\t\t\t\t\t<tr class=\"header\">\n" +
                    "\n" +
                    "\t\t\t\t\t\t\t<th style=\"width: 110px;\">#P{label_year_service} #P{last_year}</th>\n" +
                    "\t\t\t\t\t\t\t<th>#P{label_number_meetings}</th>\n" +
                    "\t\t\t\t\t\t\t<th style=\"width: 100px;\">#P{label_total}</th>\n" +
                    "\t\t\t\t\t\t\t<th>#P{average_weekly}</th>\n" +
                    "\n" +
                    "\n" +
                    "\t\t\t\t\t\t\t<th style=\"width: 110px;\">#P{label_year_service} #P{current_year}</th>\n" +
                    "\t\t\t\t\t\t\t<th>#P{label_number_meetings}</th>\n" +
                    "\t\t\t\t\t\t\t<th style=\"width: 100px;\">#P{label_total}</th>\n" +
                    "\t\t\t\t\t\t\t<th>#P{average_weekly}</th>\n" +
                    "\t\t\t\t\t\t</tr>";
            if (reportS88.getTypeWeek().equals(UtilConstants.MID_WEEK)) {
                contentTop = contentTop.replace("#P{label_title_week}", context.getString(R.string.label_s88_middle_week));
            }
            if (reportS88.getTypeWeek().equals(UtilConstants.WEEKEND)) {
                contentTop = contentTop.replace("#P{label_title_week}", context.getString(R.string.label_s88_weekend));
            }
            contentTop = contentTop.replace("#P{label_number_meetings}", context.getString(R.string.label_number_meetings_s88));
            contentTop = contentTop.replace("#P{label_total}", context.getString(R.string.label_total_meetings_s88));
            contentTop = contentTop.replace("#P{average_weekly}", context.getString(R.string.label_average_meetings_s88));
            contentTop = contentTop.replace("#P{label_year_service}", context.getString(R.string.label_year_service_s21));


            String lines = "";
            Float totalFirstYear = 0f;
            Integer totalReportsFirst = 0;
            Float totalPreviousYear = 0f;
            Integer totalReportsPrevious = 0;
            Integer currentYear = yearSelected;
            Integer yearPrevious = yearSelected - 1;
            contentTop = contentTop.replace("#P{last_year}", yearPrevious.toString());
            contentTop = contentTop.replace("#P{current_year}", currentYear.toString());


            int lengthList = EnumMonth.values().length;
            for (int i = 0; i < lengthList; i++) {
                EnumMonth enumMonth = EnumMonth.values()[i];
                String currentMonth = UtilDateHour.getMonthReportByValue(context, enumMonth.getValue());
                AssistanceReport firstAssistance = reportS88.getFirstYear().get(enumMonth.getName());

                if (firstAssistance == null) {
                    if (currentYear == 2020) {
                        firstAssistance = new AssistanceReport(
                                context,
                                currentYear,
                                reportS88.getType(),
                                reportS88.getTypeWeek(),
                                currentMonth
                        );
                        totalFirstYear = totalFirstYear + firstAssistance.getAverage();
                        totalReportsFirst++;
                    } else {
                        firstAssistance = new AssistanceReport();
                    }

                } else {
                    totalFirstYear = totalFirstYear + firstAssistance.getAverage();
                    totalReportsFirst++;
                }

                AssistanceReport previsousAssistance = reportS88.getPreviousYear().get(enumMonth.getName());
                if (previsousAssistance == null) {
                    if (yearPrevious == 2020) {
                        previsousAssistance = new AssistanceReport(
                                context,
                                yearPrevious,
                                reportS88.getType(),
                                reportS88.getTypeWeek(),
                                currentMonth
                        );
                        totalPreviousYear = totalPreviousYear + previsousAssistance.getAverage();
                        totalReportsPrevious++;
                    } else {
                        previsousAssistance = new AssistanceReport();
                    }
                } else {
                    totalPreviousYear = totalPreviousYear + previsousAssistance.getAverage();
                    totalReportsPrevious++;
                }

                // Create Line
                String mark = i == Report.TOTAL || i == Report.AVERAGE ? "mark" : "";

                String numberMeetingsValue = previsousAssistance.getNumberMeeting() == 0 ? ""
                        : previsousAssistance.getNumberMeeting().toString();


                String totalValue = previsousAssistance.getTotal() == 0 ? ""
                        : previsousAssistance.getTotal().toString();

                String averageWeeklyValue =  previsousAssistance.getAverage() == 0 ? ""
                        : UtilFormatte.getFormate(previsousAssistance.getAverage());


                String numberMeetingsCurrentValue = firstAssistance.getNumberMeeting() == 0 ? ""
                        : firstAssistance.getNumberMeeting().toString();

                String totalCurrentValue = firstAssistance.getTotal() == 0 ? ""
                        : firstAssistance.getTotal().toString();

                String averageWeeklyCurrentValue = firstAssistance.getAverage() == 0 ? ""
                        : UtilFormatte.getFormate(firstAssistance.getAverage());


                // Add values
                String line = "<tr>\n" +
                        "\t\t\t\t\t\t\t<th class=\"month " + mark + "\">" + UtilFormatte.stringToUpCaseOnlyFirstLetter(currentMonth) + "</th>\n" +
                        "\t\t\t\t\t\t\t<th class=\"" + mark + "\">" + numberMeetingsValue + "</th>\n" +
                        "\t\t\t\t\t\t\t<th class=\"" + mark + "\">" + totalValue + "</th>\n" +
                        "\t\t\t\t\t\t\t<th class=\"" + mark + "\">" + averageWeeklyValue + "</th>\n" +
                        "\t\t\t\t\t\t\t<th class=\"month " + mark + "\">" + UtilFormatte.stringToUpCaseOnlyFirstLetter(currentMonth) + "</th>\n" +
                        "\t\t\t\t\t\t\t<th class=\"" + mark + "\">" + numberMeetingsCurrentValue + "</th>\n" +
                        "\t\t\t\t\t\t\t<th class=\"" + mark + "\">" + totalCurrentValue + "</th>\n" +
                        "\t\t\t\t\t\t\t<th class=\"" + mark + "\">" + averageWeeklyCurrentValue + "</th>\n" +
                        "\t\t\t\t\t\t</tr>";

                lines = lines + line;
            }
            String tPrevious = "";
            if (totalPreviousYear > 0) {
                Float previousResult = totalPreviousYear / totalReportsPrevious;
                tPrevious = previousResult == 0f ? "" : UtilFormatte.getFormate(previousResult);
            }
            // ----
            String tFirstYear = "";
            if (totalFirstYear > 0) {
                Float currentResult = totalFirstYear / totalReportsFirst;
                tFirstYear = currentResult == 0f ? "" : UtilFormatte.getFormate(currentResult);
            }
            String linesEnd = "<tr>\n" +
                    "\t\t\t\t\t\t\t<th class=\"month mark\" colspan=\"3\">"+ context.getString(R.string.label_average_meetings_s88)+"</th>\n" +
                    "\t\t\t\t\t\t\t<th class=\"mark\">"+tPrevious+"</th>\n" +
                    "\t\t\t\t\t\t\t<th class=\"month mark\" colspan=\"3\">"+context.getString(R.string.label_average_meetings_s88)+"</th>\n" +
                    "\t\t\t\t\t\t\t<th class=\"mark\">"+tFirstYear+"</th>\n" +
                    "\t\t\t\t\t\t</tr>";
            lines = lines + linesEnd;

            String endContent = "</table>\n" +
                    "\t\t\t\t\t#P{label_bottom}\n" +
                    "\t\t\t\t</div>\n" +
                    "\t\t\t</div>";
            String labelBottom = addBottom ? "<p class=\"bottom-table\">S-88-S 12/18</p>" : "";
            endContent = endContent.replace("#P{label_bottom}", labelBottom);

            // End
            html = contentTop + lines + endContent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }

}
