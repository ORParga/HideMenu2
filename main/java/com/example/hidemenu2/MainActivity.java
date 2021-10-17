package com.example.hidemenu2;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    static int height, width;
    static int WorkAreaDesviation;

    int MenuDestination, WorkAreaDestination;
    RelativeLayout mainLayout;
    MenuLayout menuLayout;
    WorkAreaLayout workAreaLayout;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Inicializa las variables globales;
        mainLayout=findViewById(R.id.MainLayout);
        menuLayout = findViewById(R.id.MenuLayout1);
        workAreaLayout = findViewById(R.id.WorkAreaID);
        floatingActionButton = findViewById(R.id.fab);
        SharingData.ViewWorkArea = workAreaLayout;

        IniTagsOnMenu1();
        IniTagsOnWorkArea();
        InicializeUI();
    }

    private void InicializeUI() {

        //Calcular el tamañode los contenedores de menu y workArea
        mainLayout.post(new Runnable() {
            @Override
            public void run() {
                height = mainLayout.getHeight(); //height is ready in "post()"
                width = mainLayout.getWidth(); //width is ready in "post()"
                if (height > width) {
                    SharingData.Orientation = SharingData.ORIENTATION.PORTRAIT;
                    //menuLayout.setLayoutParams(new RelativeLayout.LayoutParams(width, height / 2));
                } else {
                    SharingData.Orientation = SharingData.ORIENTATION.LANDSCAPE;
                    //menuLayout.setLayoutParams(new RelativeLayout.LayoutParams(width / 2, height));
                }

            }
        });
        workAreaLayout.post(new Runnable() {
            @Override
            public void run() {
                if (mainLayout != null)
                {
                    height = mainLayout.getHeight(); //height is ready
                    width = mainLayout.getWidth(); //width is ready
                }
                if (height > width) {
                    SharingData.Orientation = SharingData.ORIENTATION.PORTRAIT;
                    //WorkArea.setLayoutParams(new ConstraintLayout.LayoutParams(width, height /2));
                    WorkAreaDesviation = height / 2;
                    workAreaLayout.setY((SharingData.FolderContainerIsShowing) ? WorkAreaDesviation : 0);
                } else {
                    SharingData.Orientation = SharingData.ORIENTATION.LANDSCAPE;
                    //WorkArea.setLayoutParams(new ConstraintLayout.LayoutParams(width , height/2));
                    WorkAreaDesviation = width / 2;
                    workAreaLayout.setX((SharingData.FolderContainerIsShowing) ? WorkAreaDesviation : 0);

                }

            }
        });

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator animation1;
                ObjectAnimator animation2;
                ObjectAnimator animation3;

                if (SharingData.FolderContainerIsShowing) {
                    MenuDestination = -WorkAreaDesviation;
                    WorkAreaDestination = 0;
                    SharingData.FolderContainerIsShowing = false;
                } else {
                    MenuDestination = 0;
                    WorkAreaDestination = WorkAreaDesviation;
                    SharingData.FolderContainerIsShowing = true;
                }

                if (SharingData.Orientation == SharingData.ORIENTATION.LANDSCAPE) {
                    animation1 = ObjectAnimator.ofFloat(menuLayout, "translationX", MenuDestination);
                    animation2 = ObjectAnimator.ofFloat(workAreaLayout, "translationX", WorkAreaDestination);
                    animation3 = ObjectAnimator.ofFloat(fab, "translationX", WorkAreaDestination);
                    fab.setImageResource((SharingData.FolderContainerIsShowing) ? R.drawable.arrowleft32x32 : R.drawable.arrowright32x32);
                } else {
                    animation1 = ObjectAnimator.ofFloat(menuLayout, "translationY", MenuDestination);
                    animation2 = ObjectAnimator.ofFloat(workAreaLayout, "translationY", WorkAreaDestination);
                    animation3 = ObjectAnimator.ofFloat(fab, "translationY", WorkAreaDestination);
                    fab.setImageResource((SharingData.FolderContainerIsShowing) ? R.drawable.arrowup32x32 : R.drawable.arrowdown32x32);
                }
                animation1.setDuration(250);
                animation1.start();

                animation2.setDuration(250);
                animation2.start();

                animation3.setDuration(250);
                animation3.start();

            }
        });
    }
    private void IniTagsOnMenu1(){

        com.example.hidemenu2.MenuLayout menuLayout1 = (MenuLayout) findViewById(R.id.MenuLayout1);
        LayoutInflater layoutInflater = getLayoutInflater();
        String tag;
        for (int i = 0; i <= 20; i++) {
            tag = "#tag" + i;
            View tagView = layoutInflater.inflate(R.layout.tag_item, null, false);

            TextView tagTextView = (TextView) tagView.findViewById(R.id.tagTextView);
            tagTextView.setText(tag);
            menuLayout1.addView(tagView);
        }
    }
    private void IniTagsOnWorkArea(){

        com.example.hidemenu2.WorkAreaLayout workAreaLayout = (WorkAreaLayout) findViewById(R.id.WorkAreaID);
        LayoutInflater layoutInflater = getLayoutInflater();
        String tag;
        for (int i = 0; i <= 20; i++) {
            tag = "#tag" + i;
            View tagView = layoutInflater.inflate(R.layout.tag_item, null, false);

            TextView tagTextView = (TextView) tagView.findViewById(R.id.tagTextView);
            tagTextView.setText(tag);
            workAreaLayout.addView(tagView);
        }
    }
}