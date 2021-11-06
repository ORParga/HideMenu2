package com.example.hidemenu2;

import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SharingData {

    public static ORIENTATION Orientation;
    public static boolean FolderContainerIsShowing=false;
    public static WorkAreaLayout ViewWorkArea;

    FloatingActionButton floatingActionButton;
    public enum ORIENTATION {LANDSCAPE, PORTRAIT}
}