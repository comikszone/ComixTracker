/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.marvelfetcher;

import java.io.IOException;

/**
 *
 * @author Phoenix1092
 */
public class App {
        
    public void run() throws IOException {
        //Fetcher.fetchChars(2, 0);
        Fetcher.fetchComics(100, 0);
    };
    
    public static void main(String[] args) throws IOException { //the one and only
        App app = new App();
        app.run();
    };    
}
