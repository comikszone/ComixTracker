/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao.util;

import com.comicszone.dao.CharacterDaoImpl;
import com.comicszone.dao.ComicsDaoImpl;
import com.comicszone.dao.Finder;
import java.sql.SQLException;
import com.comicszone.entitynetbeans.Character;
import com.comicszone.entitynetbeans.Comics;

/**
 *
 * @author ArsenyPC
 */
//МОЖНО ИСПОЛЬЗОВАТЬ ДЛЯ КОНСОЛЬНОЙ ОТЛАДКИ JPA
public class Project {
    public static void main(String[] args) throws SQLException {
        
    
//    EntityManager em=PersistenceUtil.getEntityManagerFactory().createEntityManager();
//        em.getTransaction().begin();
//        Author author=em.find(Author.class, 1);
//        System.out.println(author.getFirstName());
//        Universe universe=new Universe();
//        universe.setName("aaa");
//        universe.set
//        em.persist(universe);
//        Universe universe=em.find(Universe.class, 1);
//        System.out.println(universe.getName());
//        Users user=new Users();
//        user.setNickname("user3");user.setPass("pass3");user.setSex(1);user.setEmail("user3@.ru");user.setBanned(false);
////        em.persist(user);
//        Users user1=em.find(Users.class,1);
//        Users user2=em.find(Users.class, 2);
//        Users user3=em.find(Users.class, 3);
//        user1.setUsersList(new ArrayList<Users>(Arrays.asList(user2,user3)));
//        em.merge(user1);
//        Users user1=em.find(Users.class,2);
//        System.out.println(user1.getUsersList1().get(0).getNickname());
        
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        ComicsDaoImpl daoImpl=new ComicsDaoImpl();
//        Comics c=daoImpl.getComicsListByName("100th Anniversary Special - X-Men").get(0);
//        System.out.println(c.getName());
//        ComicsBean bean=new ComicsBean();
//        bean.createComics(2);
//        Comics comics=bean.getComics();
//        System.out.println(comics.getName());
//        ComicsDaoImpl impl=new ComicsDaoImpl();
//        Comics comics=impl.getComics(1);
//        System.out.println(comics.getName());
//        AbstractDao impl=new ComicsDaoImpl();
//        Comics comics=(Comics) impl.findAll().get(0);
//        System.out.println(comics.getName());
        
//        Comics comics=new Comics();
//        comics.setComicsId(25);
//        comics.setName("my Comiqqqqqqqqqqqqqqqcs");
//        impl.save(comics);
//        System.out.println(comics.getName());
//        em.getTransaction().commit();
//        em.close();
//        Finder finder=new CharacterDaoImpl();
//        Character character=(Character) finder.findByNameStartsWith("MoiRa").get(0);
//        System.out.println(character.getName());
        ComicsDaoImpl cdi=new ComicsDaoImpl();
        Comics c=cdi.findByName("100th Anniversary Special - Fantastic Four", "name").get(0);
        System.out.println(c.getId());
        Character character=new Character();
        character.setName("Monkey (Vietnamese) (Earth-616)"); character.setImage("http://img3.wikia.nocookie.net/__cb20140205190724/marveldatabase/images/thumb/f/f7/Monkey_%28Vietnamese%29_%28Earth-616%29.jpg/200px-0%2C519%2C0%2C519-Monkey_%28Vietnamese%29_%28Earth-616%29.jpg");
        CharacterDaoImpl cdi1=new CharacterDaoImpl();
        cdi1.save(character);
    }
}
