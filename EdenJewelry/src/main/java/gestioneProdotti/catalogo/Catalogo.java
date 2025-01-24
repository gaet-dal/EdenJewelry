package main.java.gestioneProdotti.catalogo;

import main.java.gestioneAccount.utente.UtenteDAO;
import main.java.gestioneProdotti.prodotto.ProdottoBean;
import main.java.gestioneProdotti.prodotto.ProdottoDAO;

import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;
import java.sql.SQLException;

//classe che permette la gestione dei prodotti nel catalogo;
public class Catalogo{
    //i prodotti sono salvati all'interno della tabella PRODOTTO.
    //catalogo si occupa della gestione dei prodotti


   //metodo per aggiungere prodotto al oggetto catalogo;
    public boolean addProduct(ProdottoBean prodotto,  ProdottoDAO prodottoDAO) throws SQLException {
        //poichè ci viene passato direttamente il ProdottoBean
        //ci occupiamo di valutare solo se t
        // utti i dati inseriti sono corretti e di inserirlo nel db;

        boolean validazione=checkProduct(prodotto); //verifichiamo se i dati del prodotto sono corretti;
        boolean ris=false;
        if(validazione==true){
            ris=prodottoDAO.doSave(prodotto);
        }

        if(ris == true){
            return true; //il salvataggio del prodotto è andato a buon fine
        }
        else return false;
    }

    //metodo per rimuovere un prodotto dal catalogo
    public boolean removeProduct(ProdottoBean prodotto, ProdottoDAO prodottoDAO) throws SQLException {

        String nome= prodotto.getNome();
        boolean ris=prodottoDAO.doDelete(nome);
        return ris;
    }

    public boolean checkProduct(ProdottoBean prodotto){

        String nome=prodotto.getNome();
        float prezzo=prodotto.getPrezzo();
        int quantita= prodotto.getQuantita();
        String categoria=prodotto.getCategoria();
        String immagine=prodotto.getImmagine(); //passiamo l'url di un'immagine;

        //recuperati i parametri, dobbiamo solo controllare se i formati sono corretti;

        boolean n=false;
        boolean c=false;

        //controlliamo il nome
        for(int i=0; i < nome.length(); i++) {
            //valutiamo, caratterre per carattere, se sono delle lettere;
            char lettera = nome.charAt(i);

            if ((lettera >= 'A' && lettera <= 'Z') || (lettera >= 'a' && lettera <= 'z')) {
                n= true;


            }
            else{

                n= false; //al primo carattere che non è una lettera dell'alfabeto, b viene settato a false e il ciclo si blocca;
                break;
            }
        }

        //controlliamo la categoria
        for(int i=0; i < categoria.length(); i++) {

            if(categoria.equals("collane") || categoria.equals("bracciali") || categoria.equals("anelli")){
               c= true;
            }
            else c= false; //se non corrisponde ad una delle 3 categorie, allora restituiamo false;

        }

        //controlliamo la quantità
        boolean q=false;
            if(quantita>0 ){
                q= true;
            }
            else q=false; //se non corrisponde ad una delle 3 categorie, allora restituiamo false;


        boolean p=false;
        if(prezzo >0 && prezzo<=9999){
            p= true;
        }
        else p=false;


        //se è presente anche solo un campo con degli errori, bisogna indicarli;
        //dobbiamo pensare un momento a come deve essere restituito l'errore. Se sulla sessione o altro
       boolean ris=false;
        if(n==true && c==true && q==true && p==true){
            ris= true; //se tutti i campi sono corretti, possiamo diree che tutti i dati sono corretti;
        }

        return ris;
    }
}


