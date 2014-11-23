/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.comicszone.ajaxtest;

/**
 *
 * @author GuronPavorro
 */
import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@ManagedBean
public class ListenerView
{
    private String text;
 
    public String getText() {

        return text;
    }
    public void setText(String text)
    {
        this.text = text;
    }
     
    public void handleKeyEvent()
    {
        text = text.toUpperCase();
    }
}