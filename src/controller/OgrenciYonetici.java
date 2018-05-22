/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Ogrenciler;
import org.hibernate.Session;

public class OgrenciYonetici {
    private JTable musteriTablo;
    private final static String SORGU_KALIP = "from Ogrenciler m";
    private Session session;
    private Vector<String> sutunlar = new Vector<>();
    private Vector<Object> satir;
    private DefaultTableModel model;
    public OgrenciYonetici(JTable musteriTablo) {
        this.musteriTablo = musteriTablo;
        sutunlar.add("Öğrenci ID");
        sutunlar.add("Öğrenci No");
        sutunlar.add("Ad Soyad");
        sutunlar.add("Şehir");
        sutunlar.add("Tel No");
        model=(DefaultTableModel)musteriTablo.getModel();
        model.setColumnIdentifiers(sutunlar);
    }

    public void musteriGetir(String aranan, String filtre) {
        String sorguMetin = "";
        if (filtre.equalsIgnoreCase("adsoyad")) {
            sorguMetin = SORGU_KALIP + " where m.adsoyad like '%" + aranan + "%'";
        } else if (filtre.equalsIgnoreCase("ogrencino")) {
            sorguMetin = SORGU_KALIP + " where m.ogrencino like '%" + aranan + "%'";
        }else{
            sorguMetin = SORGU_KALIP;
        }
        session.beginTransaction();
        List musterilerList = session.createQuery(sorguMetin).list();
        session.getTransaction().commit();
        musteriGoster(musterilerList);

    }
    public void sil(int ogrenciid){
        session.close();
        session = HibernateUtil.getSessionFactory().openSession();
        Ogrenciler b = new Ogrenciler();
        b.setOgrenciid(ogrenciid);
        session.delete(b);
    }
    
    public void guncelle(Integer ogrenciid, String ogrencino, String adsoyad, String sehir, String telno){
        session.close();
        session = HibernateUtil.getSessionFactory().openSession();
        Ogrenciler o = new Ogrenciler();
        o.setOgrenciid(ogrenciid);
        o.setOgrencino(ogrencino);
        o.setAdsoyad(adsoyad);
        o.setSehir(sehir);
        o.setTelno(telno);
        session.merge(o);
    }
    public void ekle(String ogrencino, String adsoyad, String sehir, String telno){
        Ogrenciler o = new Ogrenciler();
        o.setOgrencino(ogrencino);
        o.setAdsoyad(adsoyad);
        o.setSehir(sehir);
        o.setTelno(telno);
        session.save(o);
    }
    public void ac() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void kapat() {
        session.close();
    }
    private void musteriGoster(List<Ogrenciler> musterilerList) {
        model.getDataVector().removeAllElements();
        for (Ogrenciler gelenMusteri : musterilerList) {
            satir=new Vector();
            satir.add(gelenMusteri.getOgrenciid());
            satir.add(gelenMusteri.getOgrencino());
            satir.add(gelenMusteri.getAdsoyad());
            satir.add(gelenMusteri.getSehir());
            satir.add(gelenMusteri.getTelno());
            model.addRow(satir);
        }
    }
    
}
    
