/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_app;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import java.util.ArrayList;
import java.util.List;
import  java.util.Arrays;//not required
import java.util.Collections;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author Bhaskar Sengupta
 */
public class My_app {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
        MyFrame frame=new MyFrame();
        frame.setVisible(true);
        frame.setBounds(300,100,500,450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        //frame.workarea();
        frame.setTitle("Case Tracking App");
        frame.workarea();
        frame.combo();
        frame.revalidate();
    }
    
}
class MyFrame extends JFrame implements ActionListener 
{   static Container c;
    static Elements el,el1,el2;
    static Document doc;
    static int x;
    MyFrame() throws IOException
    { 
        c=this.getContentPane();
        c.setLayout(null);
        c.setBackground(Color.WHITE);
        JOptionPane p=new JOptionPane("ok");
        JLabel l=new JLabel(" WorldMeter");
        JLabel l2=new JLabel("Corona Virus");
        JLabel l1=new JLabel(" -------LIVE CASES--------");
        Font f=new Font("Times New Roman",Font.BOLD,30);
        Font f1 = new Font("Times New Roman",Font.CENTER_BASELINE,15);
        l.setBounds(5,10,180,30);
        l2.setBounds(5,45,180,30);
        l1.setBounds(5,80,180,30);
        c.add(l);
        c.add(l2);
        c.add(l1);
        l.setFont(f);
        l2.setFont(f);
        l1.setFont(f1);
        l1.setForeground(Color.RED);
    }
    static JTextArea ja;
    static ArrayList<String> tiles,country,deaths,new_cases,total_cases,new_deaths,recovered,ref;
    static String d;
    public static void workarea() throws IOException, InterruptedException
    {
        ja=new JTextArea();
        ja.setBounds(200,10,285,400);
        c.add(ja);
        ja.setBackground(Color.GRAY);
        Font f3=new Font("Times New Roman",Font.BOLD,15);
        ja.setFont(f3);
        ja.setForeground(Color.white);
        
        ja.setText("Fetching World Data"+"\nPlease Wait .............");
        scraping();
        ja.setText("\n-------------------World Report--------------------\n\n\n"+el1.text()+"\n\n "+el.text()+"\n\n "+el2.text()
                +"\n\n---------------------------------------------------------");
    }
    JComboBox cb;
    public  void combo() throws IOException
    {Font f4=new Font("Times New Roman",Font.BOLD,15);
    JPanel jp=new JPanel();
    c.add(jp);
    jp.setBounds(5,120,190,250);
    jp.setLayout(null);
    event_scraping();
    String[] temp=new String[country.size()+1]; 
    country.toArray(temp);
    JButton btn=new JButton("Generate");
    btn.setBounds(25,210,140,30);
    jp.add(btn);
    btn.setFont(f4);
    btn.setBackground(Color.red);
    btn.setForeground(Color.WHITE);
   cb = new JComboBox(temp);
   cb.setBounds(5,10,180,30);
   jp.add(cb);
   cb.setFont(f4);
   cb.setEditable(true);
   fetch();
   btn.addActionListener(this);
    }
     public void actionPerformed(ActionEvent e)
    {
       String st=(String)(cb.getSelectedItem());
       get(st);
       ja.setText("\n-------------------World Report--------------------\n"+el1.text()+"\n\n "+el.text()+"\n\n "+el2.text()
                +"\n\n---------------------------------------------------------"+"\n\n"
       +"------------------"+st+" Report----------------------\n\n"+"TOTAL CASES: :   "+total_cases.get(x)+"\n\nNEW CASES: :  "+new_cases.get(x)
       +"\n\nTOTAL DEATHS: :  "+deaths.get(x)+"\n\nNEW DEATHS: :  "+new_deaths.get(x)+"\n\nRECOVERED: :  "+recovered.get(x));
       
       }    
    public static void scraping()throws IOException
    {   doc=Jsoup.connect("https://www.worldometers.info/coronavirus/").userAgent("jsoup using google").get();
        tiles=new ArrayList<>();//block manipulation
        try{
        el=doc.select("#maincounter-wrap:nth-of-type(6)");
        el1=doc.select("#maincounter-wrap:nth-of-type(4)");
        el2=doc.select("#maincounter-wrap:nth-of-type(7)");
        System.out.println(el.text());
        }
        catch(Exception e)
        {
            System.out.println("connect to internet");
            ja.setText("error");
        }
    }
    public static void event_scraping()throws IOException
    {doc=Jsoup.connect("https://www.worldometers.info/coronavirus/").userAgent("jsoup using google").get();
        country=new ArrayList<>();
    for(Element row : doc.select("table#main_table_countries_today > tbody > tr"))//table manipulation
    {
    
    for(Element col:row.select("td:nth-of-type(2)"))
    {   
    country.add((col.text()));
    }}
country.remove(6);
List<String> deleteing=new ArrayList();
deleteing.add("Total:");
country.removeAll(deleteing);
//System.out.println(country);
ref=(ArrayList<String>)country.clone();
Collections.sort(country);

    }
    public static void fetch()
    {
    deaths=new ArrayList();
    total_cases=new ArrayList();
    new_cases=new ArrayList();
    new_deaths=new ArrayList();
    recovered=new ArrayList();
     for(Element row : doc.select("table#main_table_countries_today > tbody > tr"))//table manipulation
    {
         for(Element col:row.select("  td:nth-of-type(3)"))
    {   
    total_cases.add((col.text()));
    }    
   for(Element col:row.select("td:nth-of-type(4)"))
    {   
    new_cases.add((col.text()));
    }
   for(Element col:row.select("td:nth-of-type(5)"))
    {   
    deaths.add((col.text()));
    }
   for(Element col:row.select("td:nth-of-type(6)"))
    {   
    new_deaths.add((col.text()));
    }
   for(Element col:row.select("td:nth-of-type(7)"))
    {   
    recovered.add((col.text()));
    }
    }
    deaths.remove(6);
    new_cases.remove(6);
    total_cases.remove(6);
    recovered.remove(6);
    new_deaths.remove(6);
    Collections.replaceAll(deaths,"","NA");
    Collections.replaceAll(new_cases,"","NA");
    Collections.replaceAll(total_cases,"","NA");
    Collections.replaceAll(recovered,"","NA");
    Collections.replaceAll(new_deaths,"","NA");
    }
    public static void get(String st)
    {
        System.out.println(ref);
        System.out.println(st);
        x=ref.indexOf(st);
        System.out.println(x);
        d=deaths.get(x);
        System.out.println(deaths);
    }} 

        

