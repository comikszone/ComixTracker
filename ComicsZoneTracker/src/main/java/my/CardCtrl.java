package my;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nofuture
 */

import com.comicszone.dao.CharacterFacade;
import com.comicszone.dao.ComicsFacade;
import com.comicszone.dao.Finder;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import org.json.JSONObject;
import javax.ejb.Stateless;

@Stateless
@ManagedBean
public class CardCtrl {
    
    private JSONObject card;
    
    public void init(){
        
    }

    public JSONObject getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = new JSONObject(card);
    }
    
    public String[] LHS(){
        String [] tmp = (String[]) card.keySet().toArray(new String[0]);
        return tmp;
    }
    
    public String RHS(String s){
        return card.getString(s);
    }
}
