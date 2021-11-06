package com.example.hidemenu2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    static int height_main, width_main;
    static int height_menu, width_menu;
    static int MenuDisplacement;

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

        //Aqui se pueden agregar tareas a ejecutar cuando
        //el mainLayout esté completamente dibujado
        //Decide si la orientacion es PORTRAIT O LANDSCAPE
        mainLayout.post(new Runnable() {
            @Override
            public void run() {

                final FloatingActionButton fab = findViewById(R.id.fab);
                height_main = mainLayout.getHeight(); //height is ready in "post()"
                width_main = mainLayout.getWidth(); //width is ready in "post()"

                if (height_main > width_main) {
                    SharingData.Orientation = SharingData.ORIENTATION.PORTRAIT;
                    fab.setImageResource((SharingData.FolderContainerIsShowing) ? R.drawable.arrowup32x32 : R.drawable.arrowdown32x32);

                    MenuDisplacement = height_main /2;
                    int padding=fab.getPaddingTop();
                    fab.setY((SharingData.FolderContainerIsShowing) ? MenuDisplacement+padding:padding );

                } else {
                    SharingData.Orientation = SharingData.ORIENTATION.LANDSCAPE;
                    fab.setImageResource((SharingData.FolderContainerIsShowing) ? R.drawable.arrowleft32x32 : R.drawable.arrowright32x32);

                    MenuDisplacement = width_main /2;
                    int padding=fab.getPaddingLeft();
                    fab.setX((SharingData.FolderContainerIsShowing) ? MenuDisplacement+padding:padding );
                }
            }
        });

        //Aqui se pueden agregar tarear a ejecutar cuando el
        //workAreaLayout esté completamente dibujado
        workAreaLayout.post(new Runnable() {
            @Override
            public void run() {
                if (mainLayout != null)
                {
                    height_main = mainLayout.getHeight(); //height is ready
                    width_main = mainLayout.getWidth(); //width is ready
                }
                if (height_main > width_main) {
                    SharingData.Orientation = SharingData.ORIENTATION.PORTRAIT;
                    //MenuDisplacement = height_main /2;
                    //workAreaLayout.setY((SharingData.FolderContainerIsShowing) ? MenuDisplacement : 0);
                } else {
                    SharingData.Orientation = SharingData.ORIENTATION.LANDSCAPE;
                    //MenuDisplacement = width_main/2;
                    //workAreaLayout.setX((SharingData.FolderContainerIsShowing) ? MenuDisplacement : 0);

                }

            }
        });

        //Aqui se pueden ejecutar tareas cuando el
        //menuLAyout esté comletamente dibujado
        menuLayout.post(new Runnable() {
            @Override
            public void run() {
                if (mainLayout != null)
                {
                    height_main = mainLayout.getHeight(); //height is ready
                    width_main = mainLayout.getWidth(); //width is ready
                    height_menu = menuLayout.getHeight(); //height is ready
                    width_menu = menuLayout.getWidth(); //width is ready
                }
                if (height_main > width_main) {
                    SharingData.Orientation = SharingData.ORIENTATION.PORTRAIT;
                    menuLayout.setLayoutParams(new RelativeLayout.LayoutParams(width_main, height_main /2));
                    MenuDisplacement = height_main /2;
                    menuLayout.setY((SharingData.FolderContainerIsShowing) ? 0:-MenuDisplacement );
                } else {
                    SharingData.Orientation = SharingData.ORIENTATION.LANDSCAPE;
                    menuLayout.setLayoutParams(new RelativeLayout.LayoutParams(width_main/2 , height_main));
                    MenuDisplacement = width_main/2;
                    menuLayout.setX((SharingData.FolderContainerIsShowing) ?0:-MenuDisplacement);

                }

            }
        });

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator animation1_Menu;
                ObjectAnimator animation2_WorkArea;
                ObjectAnimator animation3_Button;

                if (SharingData.FolderContainerIsShowing) {
                    MenuDestination = -MenuDisplacement;
                    WorkAreaDestination = 0;
                    SharingData.FolderContainerIsShowing = false;
                } else {
                    MenuDestination = 0;
                    WorkAreaDestination = MenuDisplacement;
                    SharingData.FolderContainerIsShowing = true;
                }

                if (SharingData.Orientation == SharingData.ORIENTATION.LANDSCAPE) {
                    animation1_Menu = ObjectAnimator.ofFloat(menuLayout, "translationX", MenuDestination);
                    animation2_WorkArea = ObjectAnimator.ofFloat(workAreaLayout, "translationX", WorkAreaDestination);
                    animation3_Button = ObjectAnimator.ofFloat(fab, "translationX", WorkAreaDestination);
                    fab.setImageResource((SharingData.FolderContainerIsShowing) ? R.drawable.arrowleft32x32 : R.drawable.arrowright32x32);
                } else {
                    animation1_Menu = ObjectAnimator.ofFloat(menuLayout, "translationY", MenuDestination);
                    animation2_WorkArea = ObjectAnimator.ofFloat(workAreaLayout, "translationY", WorkAreaDestination);
                    animation3_Button = ObjectAnimator.ofFloat(fab, "translationY", WorkAreaDestination);
                    fab.setImageResource((SharingData.FolderContainerIsShowing) ? R.drawable.arrowup32x32 : R.drawable.arrowdown32x32);
                }
                animation1_Menu.setDuration(250);
                animation1_Menu.start();

                animation2_WorkArea.setDuration(250);
                //animation2_WorkArea.start();

                animation3_Button.setDuration(250);
                animation3_Button.start();

            }
        });
    }
    private void IniTagsOnMenu1(){

        com.example.hidemenu2.MenuLayout menuLayout1 = (MenuLayout) findViewById(R.id.MenuLayout1);
        LayoutInflater layoutInflater = getLayoutInflater();
        String tag;
        for (int i = 0; i <= 20; i++) {
            tag = "#Menu" + i;
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
            tag = "#Work" + i;
            View tagView = layoutInflater.inflate(R.layout.tag_item, null, false);

            TextView tagTextView = (TextView) tagView.findViewById(R.id.tagTextView);
            tagTextView.setText(tag);
            workAreaLayout.addView(tagView);
        }
    }
}