package tsanten.cartita;
    import java.io.File;
    import java.io.FileInputStream;
    import java.io.IOException;
    import java.util.Collection;

    import org.apache.tika.exception.TikaException;
    import org.apache.tika.metadata.Metadata;
    import org.apache.tika.parser.ParseContext;
    import org.apache.tika.parser.pdf.PDFParser;
    import org.apache.tika.sax.BodyContentHandler;
    import java.lang.StackWalker;

    import org.xml.sax.SAXException;

    import javax.swing.JOptionPane;

    import java.util.Scanner;
    import org.apache.commons.io.FileUtils;

    import java.io.FileWriter;
//    import java.io.BufferedReader;
//import java.nio.charset.StandardCharsets;
    import java.util.ArrayList;
    import java.util.List;
//import java.util.regex.Pattern;
    import java.time.LocalDateTime;
//import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
/**
 *
 * @author Tsanten(Constantin Marius-Laurentiu)
 */

public class Cartita implements Runnable {
public static int yo;
public static int medie;
public static int nrcrt;
public static int differencecheck;
public static int numarfire;
//public static int threadid=0;
//public static Thread fir;
public static List<Thread> fire= new ArrayList<>();
public static int nrthread=0;
public static int threadidload=0;
public static int diferenta=0;
public static int startdiferenta=0;
public static int endnumberbasis;


public static String cuvantcheie;
public static List<String> harta=new ArrayList<>();
public static List<String> raportconstructie=new ArrayList<>();
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws SAXException, TikaException {
        //int numarfire;
        yo=0;
        differencecheck=0;
        try{
            File excelreport= new File("./raportCartita.txt");
            boolean excelreportcheck= excelreport.createNewFile();
            if(excelreportcheck){
                System.out.println("Situatie: Baza raportului in csv a fost creata.\n");
            }
            FileWriter excelreportwrite= new FileWriter("./raportCartita.txt");
            Scanner inputcuvantcheie= new Scanner(System.in);
            System.out.println("Introdu cuvantul/cuvintele cheie de cautat:");
            cuvantcheie="" + inputcuvantcheie.nextLine();
            Scanner inputlocatie= new Scanner(System.in);
            System.out.println("Introdu locatia folderului primar a documentelor(Ex: C:\\Documente):");
            String locatieacceptata="" + inputlocatie.nextLine();
            //scanner input Thread number
            Scanner inputnrthreads= new Scanner(System.in);
            System.out.println("Introdu numarul de 'Fire' dorite(Default: 1, Recomandat 1 fir pe CPU Core): ");
            numarfire=Integer.parseInt(inputnrthreads.nextLine());
            File locatiadocumentelor= new File("" + locatieacceptata);
            if(locatiadocumentelor.isDirectory()){
                System.out.println("Situatie: Locatia '" + locatieacceptata + "' a fost acceptata.\nProgramul va incepe scanarea..");
               //WildcardFileFilter extensie= new WildcardFileFilter("*.pdf");
            Collection documente= FileUtils.listFiles(locatiadocumentelor, new String[] {"pdf"}, true);
                //----------------------- Raport
                excelreportwrite.write("Cutie, Nume PDF, Verificare, Cuvant Cheie, Numarul potrivirilor, Locatie, Timpul Verificarii, Thread used");
                nrcrt=0;
                    for(Object raportfisier : documente){
                        File raportfisierct= new File("" + raportfisier);
                            String fullpath="" + raportfisierct.toString();
                            // Crearea Hartei;
                            harta.add("." + nrcrt + ".," + fullpath);
                            nrcrt++;
                    }
                    excelreportwrite.close();
                    
                    // Medie
                    startdiferenta=0;
                    if(numarfire > 1){
                        medie=nrcrt/numarfire;
                        if(nrcrt%numarfire > 0){
                            String strmedie="" + medie;
                            medie=Integer.parseInt(strmedie);
                            if(nrcrt-medie*numarfire > 0){
                                diferenta=nrcrt-medie*numarfire;
                                startdiferenta=1;
                            }
                        }
                    }else if(numarfire == 1){
                        medie=nrcrt;
                    }else if(numarfire < 1){
                        System.out.println("Situatie: Numarul de fire asimilat programului este mai mic decat 1. Programul se va inchide");
                            JOptionPane.showMessageDialog(null,
                                    "Numarul de fire asimilat programului este mai mic decat 1. Programul se va inchide.",
                                    "Informatie:",
                                    JOptionPane.PLAIN_MESSAGE);
                        System.exit(0);
                    }
                    // /Medie
                //------------------------------
            //------------------- Medie
            //-------------------------
            }else{
                System.out.println("Locatia '" + locatieacceptata + "' nu a fost acceptata deoarece nu exista.\n Programul a fost anulat.");
                            JOptionPane.showMessageDialog(null,
                                    "Locatie invalida.",
                                    "Eroare:",
                                    JOptionPane.ERROR_MESSAGE);
                            System.exit(0);
            }
               
        }catch(IOException e){
            throw new SAXException(e);
        }
        // MultiThreading
        for(int i=0;i<numarfire;i++){
        Cartita book= new Cartita();
        fire.add(new Thread(book));
        }
        for(Thread fir : fire){
            fir.start();
        }
        try{
            int raport=numarfire;
            for(Thread firyo : fire){
                System.out.println("Situatie: " + raport + " fire sunt in lucru, te rugam asteapta.");
                firyo.join();
                System.out.println("Situatie: Un fir s-a finalizat.");
                raport--;
            }
            System.out.println("Entering sleep mode");
        //TimeUnit.MINUTES.sleep(1);
            System.out.println("Exit sleep mode.");
        System.out.println("Situatie: Programul s-a finalizat. Raportul urmeaza a fi creat..");
        }catch(InterruptedException e){
            System.out.println("Fir a dat eroare la join. A fost intrerupt.");
        }
            try (FileWriter actyolo = new FileWriter(new File("./raportCartita.txt"))) {
                actyolo.write("Cutie, Nume PDF, Verificare, Cuvant Cheie, Numarul potrivirilor, Locatie, Timpul Verificarii, Thread used");
                for (String iteratielinie : raportconstructie) {
                actyolo.write(iteratielinie);
                //System.out.println("Actualizare: " + iteratielinie);
            }
            actyolo.close();
            }catch(IOException e){
                System.out.println("Eroare la printarea rezultatului.");
            }
    }//end main class
    
    @Override
    public void run(){ // start new thread
        nrthread++;
        int threadid=nrthread;
        // id assertion
        //int debug=0;
        List<String> constructie=new ArrayList<>();
        System.out.println("ID fir: " + threadid + "; A inceput.");
        try{
        File cachethread= new File("./Data-thread-" + threadid);
            try (FileWriter cachethreadwrite = new FileWriter(cachethread)) {                
                //impartiread firului de lucru
                int startnumber=(threadid-1)*medie;
                int endnumber=startnumber+medie;
                if(startdiferenta == 2){
                    if(endnumber>endnumberbasis){
                        startnumber=startnumber+diferenta;
                        endnumber=endnumber+diferenta;
                    }
                }
                if(startdiferenta == 1){
                    startdiferenta=2;
                    endnumber=endnumber+diferenta;
                    endnumberbasis=endnumber;
                }
                //String rezultat="";
                LocalDateTime timp;
                
                Metadata metadata = new Metadata();
                ParseContext pcontext = new ParseContext();
                PDFParser pdfparser = new PDFParser();
                
                //int debug=0;
                for(int i=startnumber;i<endnumber;i++){
                    String locatieharta="." + i + ".,";
                    for(String locatiehartafinal : harta){
                        if(locatiehartafinal.contains(locatieharta)){
                            String locatiehartafinalyo=locatiehartafinal.replace("" + locatieharta, "");
                            File yolodocument= new File("" + locatiehartafinalyo);
                            if(yolodocument.isFile()){
                                BodyContentHandler handler= new BodyContentHandler(-1);
                                String numedocument="" + yolodocument.getName();
                                String caledocument="" + yolodocument.toString();
                                FileInputStream inputstream = new FileInputStream(new File("" + yolodocument));
                                pdfparser.parse(inputstream, handler, metadata, pcontext);
                                
                                int numaroglindiri=0;
                                //debug++;
                                //System.out.println(debug);
                                //-----------------Cache Builder
                                cachethreadwrite.write(".,PDF: " + caledocument + " :\n" + handler.toString());
                                //---------------------------------
                                if(handler.toString().contains(cuvantcheie)){
                                    numaroglindiri=handler.toString().split(cuvantcheie).length;
                                    //Rezultat Excel
                                    String cutie1= locatiehartafinalyo.substring(locatiehartafinalyo.lastIndexOf("\\"));
                                    String cutie2= locatiehartafinalyo.replace("" + cutie1, "");
                                    String cutie=cutie2.substring(cutie2.lastIndexOf("\\") + 1);
                                    timp= LocalDateTime.now();
                                    String constructiestr="\n" + cutie + ", " + numedocument + ", DA, " + cuvantcheie + ", " + numaroglindiri + ", " + locatiehartafinalyo + ", " + timp + ", " + threadid;
                                    constructie.add(constructiestr);
                                    //----------------------------- Rezultat
                                }else{
                                    String cutie1= caledocument.substring(caledocument.lastIndexOf("\\"));
                                    String cutie2= caledocument.replace("" + cutie1, "");
                                    String cutie=cutie2.substring(cutie2.lastIndexOf("\\") + 1);
                                    timp= LocalDateTime.now();
                                    String constructiestr="\n" + cutie + ", " + numedocument + ", NU, " + cuvantcheie + ", " + numaroglindiri + ", " + locatiehartafinalyo + ", " + timp + ", " + threadid;
                                    constructie.add(constructiestr);
                                    //----------------------------- Rezultat
                                }
                            }
                        }
                    }
                    
                }// end for
                cachethreadwrite.close();
            }
        }catch(IOException e){
            //throw new SAXException(e);
        } catch (SAXException | TikaException ex) {
        Logger.getLogger(Cartita.class.getName()).log(Level.SEVERE, null, ex);
    }
        System.out.println("ID fir " + threadid + " a completat procesul.");
        raportconstructie=Stream.concat(raportconstructie.stream(), constructie.stream()).toList();
    }
}
