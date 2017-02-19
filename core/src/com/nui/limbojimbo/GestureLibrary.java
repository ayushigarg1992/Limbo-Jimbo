package com.nui.limbojimbo;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Maddy on 17/02/17.
 */





public class GestureLibrary {

    private static GestureLibrary instance = new GestureLibrary();
    private PointCloudLibrary _library;


    GestureLibrary (){
        _library = PointCloudLibrary.getDemoLibrary();
     }

    public static GestureLibrary getInstance() {
        return instance;
    }

    public PointCloudLibrary getLibrary(){
        return _library;

    }

    public void ClearLibrary() {
        _library.clear();
    }


    public void LoadLibrary(){

        String filepath = "gestures/";
        FileHandle handle = Gdx.files.internal("gestures/");
       // String locRoot = Gdx.files.getLocalStoragePath();

        FileHandle dirHandle;
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            dirHandle = Gdx.files.internal("gestures/");
        } else {
            // ApplicationType.Desktop ..
            dirHandle = Gdx.files.internal("./bin/some/directory");
        }
        for (FileHandle entry: dirHandle.list()) {
            // yadda ...
            try {
               // FileInputStream f =  new FileInputStream(entry.file());
                String text = entry.readString();
                InputStream is = new ByteArrayInputStream(text.getBytes());

                // read it with BufferedReader
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
               // BufferedReader br = new BufferedReader(new FileReader(entry.file()));
                String line;
                String name = "";
                int stroke= 0;
                ArrayList<PointCloudPoint> _curGesture =new ArrayList<PointCloudPoint>();;
                boolean segment = false;
                while ((line = br.readLine()) != null) {
                    if (line.equals("BEGIN")){
                        if (segment)
                            throw new IOException("segment fault");
                        stroke++;
                        segment = true;
                    }else if (line.equals("END")){
                        if (segment == false)
                            throw new IOException("segment fault");

                        segment = false;
                    } else if (segment == false){
                        String[] str=line.split(":");
                        if (str[0].equals("type")){
                            name = str[1];
                        }
                        //name = line;
                    } else if (segment == true ){
                        String[] xy=line.split(",");
                        double X=Double.parseDouble(xy[0]);
                        double Y=Double.parseDouble(xy[1]);
                        PointCloudPoint p = new PointCloudPoint(X,Y, stroke);
                        _curGesture.add(p);
                    }
                }
                if (!name.equals("")){
                    _library.addPointCloud(new PointCloud(name, _curGesture));
                    //System.out.println("success name ="+name);
                    _curGesture = new ArrayList<PointCloudPoint>();
                }
            }catch (IOException e) {
                System.out.println(e.toString());
                e.printStackTrace();

            }
        }

       /* File folder = handle.file(); //new File(filepath);
        String[] fileNames = folder.list();
        for (final File fileEntry : folder.listFiles()) {
            //System.out.println(fileEntry);
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileEntry));
                String line;
                String name = "";
                int stroke= 0;
                ArrayList<PointCloudPoint> _curGesture =new ArrayList<PointCloudPoint>();;
                boolean segment = false;
                while ((line = br.readLine()) != null) {
                    if (line.equals("BEGIN")){
                        if (segment)
                            throw new IOException("segment fault");
                        stroke++;
                        segment = true;
                    }else if (line.equals("END")){
                        if (segment == false)
                            throw new IOException("segment fault");

                        segment = false;
                    } else if (segment == false){
                        String[] str=line.split(":");
                        if (str[0].equals("type")){
                            name = str[1];
                        }
                        //name = line;
                    } else if (segment == true ){
                        String[] xy=line.split(",");
                        double X=Double.parseDouble(xy[0]);
                        double Y=Double.parseDouble(xy[1]);
                        PointCloudPoint p = new PointCloudPoint(X,Y, stroke);
                        _curGesture.add(p);
                    }
                }
                if (!name.equals("")){
                    _library.addPointCloud(new PointCloud(name, _curGesture));
                    //System.out.println("success name ="+name);
                    _curGesture = new ArrayList<PointCloudPoint>();
                }
            }catch (IOException e) {
                System.out.println(e.toString());
                e.printStackTrace();

            }
        }*/
    }

}