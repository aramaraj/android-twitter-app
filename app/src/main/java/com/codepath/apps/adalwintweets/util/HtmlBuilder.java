package com.codepath.apps.adalwintweets.util;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by aramar1 on 6/12/16.
 */
public class HtmlBuilder {
    public static final String COLOR = "blue";

    public enum Type{
        BOLD("strong"),
        ITALIC("em"),
        COLOR("font color=\"#"+ HtmlBuilder.COLOR + "\""),
        SMALL("small")
        ;
        public final String stringType;

        Type(String stringType){
            this.stringType = stringType;
        }

        @Override
        public String toString() {
            return stringType;
        }
    }

    private StringBuilder data = new StringBuilder();


    public HtmlBuilder openColor(String color){
        data.append("<").append(Type.COLOR.toString().replace(COLOR, color));
        return this;
    }

    public HtmlBuilder append(String s) {
        data.append(s);
        return this;
    }

    public HtmlBuilder open(Type type){
        data.append("<").append(type).append(">");
        return this;
    }

    public HtmlBuilder close(Type type){
        if(type == Type.COLOR){
            data.append("</font>");
        }
        else {
            data.append("</").append(type).append(">");
        }
        return this;
    }

    public Spanned build(){
        System.out.println(data.toString());
        return Html.fromHtml(data.toString());
    }
}