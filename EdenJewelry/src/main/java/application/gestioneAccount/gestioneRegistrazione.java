package main.java.application.gestioneAccount;

public class gestioneRegistrazione {

    //metodo che controlla se il parametro in ingresso contiene solo lettere (quindi rappresenta o un nome o un cognome)
    public boolean checkNomeCognome (String x) {
    boolean b = false;

        //scorriamo la stringa
        for (int i = 0; i < x.length(); i++) {

            //valutiamo, caratterre per carattere, se sono delle lettere;
            char lettera = x.charAt(i);

            if ((lettera >= 'A' && lettera <= 'Z') || (lettera >= 'a' && lettera <= 'z')) {
                b= true;


            }
            else{

                b= false; //al primo carattere che non è una lettera dell'alfabeto, b viene settato a false e il ciclo si blocca;
                break;
            }
        }

        return b;
    }

    //metodo che controlla se il parametro in ingresso è una mail valida
    public boolean checkEmail (String x){

        //scorriamo la stringa
        for (int i = 0; i < x.length(); i++) {

            //valutiamo, caratterre per carattere, se sono delle lettere;
            char lettera = x.charAt(i);

            //controlla in tutta la stringa, lettera per lettera, se c'è la chiocciola (ad indicare che si tratta di una mail)
            //se è presente, facciamo ritornare subito true;
            if (lettera=='@') {
                 return true;

            }
        }

        return false; //dopo aver valutato tutta la stringa, se non c'è la chicciola, ritorniamo false per indicare che non è una mail valida;
    }

}
