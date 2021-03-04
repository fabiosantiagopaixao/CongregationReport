package br.com.congregationreport.util;

import android.content.Context;

import java.io.IOException;

import br.com.congregationreport.R;
import br.com.congregationreport.db.dao.GroupDAO;
import br.com.congregationreport.db.dao.PublisherDAO;
import br.com.congregationreport.models.Publisher;

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
                    "        background: rgb(255, 255, 255);\n" +
                    "        box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);\n" +
                    "    }\n" +
                    "    .subpage {\n" +
                    "        padding: 0.5cm;\n" +
                    "        border: 3px rgb(6, 37, 86) solid;\n" +
                    "        height: 256mm;\n" +
                    "        overflow: hidden !important;\n" +
                    "        float: left;\n" +
                    "        background: rgb(214 214 214);\n" +
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
                    "\t\tborder-color: rgb(30, 29, 29);\n" +
                    "\t\tmargin-top: 10px;\n" +
                    "\t}\n" +
                    "\t.box .title {\n" +
                    "\t\tcolor: rgb(252, 83, 58);\n" +
                    "\t\tfloat: left;\n" +
                    "\t\twidth: 100%;\n" +
                    "\t\ttext-align: center;\n" +
                    "\t}\n" +
                    "\t.box p span {\n" +
                    "\t\tcolor: rgb(6, 37, 86);\n" +
                    "\t\tfont-weight: bold;\n" +
                    "\t\twidth: 105px;\n" +
                    "\t\tfloat: left;\n" +
                    "\t}\n" +
                    "\ttable {\n" +
                    "\t\tfont-family: arial;\n" +
                    "\t\tfont-size: 12pt;\n" +
                    "\t}\n" +
                    "\ttable, td, th {\n" +
                    "\t\tborder: 1px solid rgb(30, 29, 29);; \n" +
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
                    "\t\tcolor: rgb(255, 255, 255);\n" +
                    "\t}\n" +
                    "\ttr {\n" +
                    "\t\theight: 40px;\n" +
                    "\t}\n" +
                    "\ttr.header {\n" +
                    "\t\tbackground: rgb(252, 83, 58);\n" +
                    "\t\tcolor: rgb(255, 255, 255);\n" +
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
                    "\t\t border: solid rgb(252, 83, 58);\n" +
                    "\t\t border-width: 0 2px 2px 0;\n" +
                    "\t\t -webkit-transform: rotate(45deg);\n" +
                    "\t\t -ms-transform: rotate(45deg);\n" +
                    "\t\t transform: rotate(45deg);\n" +
                    "\t\t position: absolute;\n" +
                    "\t\t top: 2px;\n" +
                    "\t\t left: 6px;\n" +
                    "\t}\n" +
                    "\t.box-content {\n" +
                    "\t\tbackground: rgb(255, 255, 255);\n" +
                    "\t\tpadding: 10px;\n" +
                    "\t\tmargin-bottom: 20px;\n" +
                    "\t\tborder-radius: 10px;\n" +
                    "\t}\n" +
                    "\th1.title {\n" +
                    "\t\tcolor: rgb(6, 37, 86);\n" +
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
                    "\t\tcolor: rgb(6, 37, 86);\n" +
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

    public static String getPublisherPageEnd(String numberPage) {
        String html = "";
        try {
            html = "</div>\n" +
                    "\t\t\t\t\n" +
                    "\t\t\t\t<span class=\"page-number\">$P{label_page}</span>\n" +
                    "\t\t\t\t<img class=\"logo\" src=\"http://drive.google.com/uc?id=1-jjANE7WlRbQj_x4YHsrKOBzb-Kqmy7W\" />\n" +
                    "\t\t\t</div>";
            html = html.replace("$P{label_page}", numberPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }
}
