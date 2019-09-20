package tools;

import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Iterator;
import java.util.*;
import java.io.*;

public class LoaderAnalyzer {

    public static ArrayList<int[]> findProperNames(String text){
        int z = text.length();
        ArrayList<int[]> list = new ArrayList<int[]>();
        int start;
        int end;
        int i = 3;
        while ( i < z-1 ){
            if( text.charAt(i) == '.'){
                i=i+3;
                continue;
            }
            if( (text.charAt(i) == ' ' || text.charAt(i) == '(') && Character.isUpperCase(text.charAt(i+1)) ){
                start = i+1;
                while( i<z-1 ){
                    if (text.charAt(i) == ' ' && Character.isLowerCase(text.charAt(i+1))) break;
                    if (text.charAt(i) == '.') break;
                    if (text.charAt(i) == ',') break;
                    if (text.charAt(i) == '\'') break;
                    if (text.charAt(i) == ':') break;
                    if (text.charAt(i) == '?') break;
                    if (text.charAt(i) == '!') break;
                    if (text.charAt(i) == ' ' && text.charAt(i+1) == '(') break;
                    if (text.charAt(i) == ')') break;
                    i++;
                }
                end = i;
                list.add(new int[]{start,end});
            } else {
                i++;
            }
        }
        Collections.reverse(list);
        return list;
    }

    public static int[] findNextPN(String text, int index){
        int z = text.length();
        int start;
        int end;
        int i = index;
        while ( i < z-1 ){
            if( text.charAt(i) == '.'){
                i=i+3;
                continue;
            }
            if( (text.charAt(i) == ' ' || text.charAt(i) == '(') && Character.isUpperCase(text.charAt(i+1)) ){
                start = i+1;
                while( i<z-1 ){
                    if (text.charAt(i) == ' ' && Character.isLowerCase(text.charAt(i+1))) break;
                    if (text.charAt(i) == '.') break;
                    if (text.charAt(i) == ',') break;
                    if (text.charAt(i) == '\'') break;
                    if (text.charAt(i) == ':') break;
                    if (text.charAt(i) == '?') break;
                    if (text.charAt(i) == '!') break;
                    if (text.charAt(i) == ' ' && text.charAt(i+1) == '(') break;
                    if (text.charAt(i) == ')') break;
                    i++;
                }
                end = i;
                return new int[]{start,end};
            } else {
                i++;
            }
        }
        return new int[]{z+10,-10};
    }
}


