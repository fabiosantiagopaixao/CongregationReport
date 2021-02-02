package br.com.congregationreport.models;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class ButtonCustom {

    private LinearLayout linearLayout;
    private Button imageButton;

    public ButtonCustom() {
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    public Button getImageButton() {
        return imageButton;
    }

    public void setImageButton(Button imageButton) {
        this.imageButton = imageButton;
    }
}
