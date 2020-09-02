/*


*/
import java.io.*;
import java.security.Key;
import java.util.*;



public class Main {

    public static void main(String[] args)
    {
        File Archivo = null;
        FileReader FR = null;
        BufferedReader BR = null;
        
        int CantidadLineas = 0;
        int Comillas = 0;
        int Banderita = 0;
        int SuppBanderita = 0;

        String Line;
        String Dato = "";
        
        ArrayList <ChampId> Campeones = new ArrayList<ChampId>();
        ArrayList <ArrayList<String>> DataSet = new ArrayList<ArrayList<String>>();
        ArrayList <ArrayList<String>> Ignorar = new ArrayList<ArrayList<String>>();
        
        try
        {
            Archivo = new File("teambans.csv");
            FR = new FileReader(Archivo);
            BR = new BufferedReader(FR);

            while((Line = BR.readLine()) != null)
                CantidadLineas++;

            BR.close();

            BR = new BufferedReader(new FileReader(Archivo));

            for(int i = 0; i < CantidadLineas; i++)
                DataSet.add(new ArrayList<String>());

            for(int i = 0; i < CantidadLineas; i++)
            {
                Line = BR.readLine();
                Comillas = 0;
                Banderita = 0;

                for(int j = 0; j < Line.length(); j++)
                {
                    //System.out.println("\"" + Line + "\"");
                    
                    if(Line.charAt(j) != ',')
                    {
                        //System.out.println(Comillas);
                        //System.out.println(Line.charAt(j));

                        if(Line.charAt(j) != '\"')
                        {   
                            //System.out.println(Comillas);
                            Dato += Line.charAt(j);
                        }
                        else
                        {   
                            Comillas++;
                            //System.out.println("mas comas");
                            if(Comillas%2 == 0 && Comillas != 0)
                            {
                                DataSet.get(i).add(Dato);
                                Dato = "";
                                //System.out.println("Guarde dato uwu");
                            }
                        
                        }
                    }
                }
            }
            
            BR.close();

            //System.out.println(CantidadLineas);
            //System.out.println(Comillas);
            //System.out.println(DataSet.get(0).size());

            
            for(int j = DataSet.size() - 7; j < DataSet.size() ; j++)
            {   
                for(int i = 0; i < DataSet.get(j).size(); i++)
                    System.out.print("\"" + DataSet.get(j).get(i) + "\"");
        
                System.out.println();
            }
        

            //System.out.println(DataSet.get(0).size());


        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try 
            {
                FR.close();    
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        try
        {
            Archivo = new File("out.txt");
            FR = new FileReader(Archivo);
            BR = new BufferedReader(FR); 

            CantidadLineas = 0;

            while((Line = BR.readLine()) != null)
            {
                CantidadLineas++;
            }

            BR.close();

            BR = new BufferedReader(new FileReader(Archivo));

            for(int i = 0; i < CantidadLineas; i++)
            {
                Ignorar.add(new ArrayList<String>());
            }

            for(int i = 0; i < CantidadLineas; i++)
            {
                Line = BR.readLine();

                for(int j = 0; j < Line.length(); j++)
                {
                    if(Line.charAt(j) != ' ')
                    {
                        Dato += Line.charAt(j);
                        
                        if(j == Line.length() - 1)
                        {
                            Ignorar.get(i).add(Dato);
                            Dato = "";
                        }
                    }
                    else
                    {
                        Ignorar.get(i).add(Dato);
                        Dato = "";
                    }
                }
            }
            /*
            for(int j = 0; j < 1 ; j++)
            {   
                for(int i = 0; i < Ignorar.get(j).size(); i++)
                    System.out.print("\"" + Ignorar.get(j).get(i) + "\"");
        
                System.out.println();
            }
            */
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try 
            {
                FR.close();    
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }   

        Map <String , Stack> Orden = new LinkedHashMap<String , Stack>();
        ArrayList <String> MatchIgnorar = new ArrayList<String>();
        //Map <Integer , Integer> Prueba = new LinkedHashMap<Integer , Integer>();
        //List <LinkedList> NOrden = new ArrayList<LinkedList>();

        //Busca Match a ignorar
        for(int i = 0; i < DataSet.size(); i++)
        {
            for(int j = 0; j < Ignorar.size(); j++)
            {
                if( Integer.parseInt(Ignorar.get(j).get(3)) == i)
                {
                    MatchIgnorar.add(DataSet.get(i).get(0));
                    //System.out.println(DataSet.get(i).get(0));
                    //System.out.println(i);
                }
            }
        }
        //System.out.println(Integer.parseInt(Ignorar.get(0).get(3)));
        //System.out.println(MatchIgnorar.get(0));
        //System.out.println(MatchIgnorar.get(1));

        //Elimina Match incompletos
        for(int i = 1; i < DataSet.size(); i++)
        {
            for(int j = 0; j < MatchIgnorar.size(); j++)
            { 
                if(Integer.parseInt(MatchIgnorar.get(j)) == Integer.parseInt(DataSet.get(i).get(0)))
                {   
                    //revisa que entren todas las matchid
                    //System.out.println(DataSet.get(i));
                    //System.out.println(MatchIgnorar.get(j));
                    //System.out.println(i);
                    //System.out.println(j);
                    DataSet.remove(i);
                    i--;
                    break;
                }
                
            }
        }

        //Rellena ChampId
        for(int  i = 1; i < DataSet.size(); i++)
        {
            Campeones.add(new ChampId(DataSet.get(i).get(2), DataSet.get(i).get(3)) );
        }

        //Proceso que guarda matchId
        for(int i = 0; i < DataSet.size(); i++)
        {
            if( i != 0 && i%6 == 0) 
            {
                Orden.put(DataSet.get(i).get(0), new Stack<>());
            }
        }

        /*
        for(int i = 0; i < MatchId.size(); i++)
        {   
            Prueba.put(i, i);
        }
        */
        //System.out.println(Orden.get("10").size());
        //System.out.println(Prueba.size());
        //System.out.println(Orden.size());
        /*
        for(int i = 0; i < DataSet.size()/6; i++)
        {
            Orden.get(i).push(DataSet.get(Banderita).get(0));
            for(int j = 0; j < 6; j++)
            {
                Orden.get(i).push(DataSet.get(Banderita).get(2));
                Banderita--;
            }

            Banderita = SuppBanderita + 6;
            SuppBanderita = Banderita;
        }
        
        */

        Banderita = 6;
        SuppBanderita = Banderita;

        Iterator it = Orden.keySet().iterator();
        while(it.hasNext())
        {
            String Key = it.next().toString();
            for(int j = 0; j < 6; j++)
            {
                /*
                if(Banderita <= 6)
                {
                    //System.out.println(Banderita);
                    //System.out.println(j);
                    //System.out.println(Orden.get("10").size());
                    System.out.println(DataSet.get(Banderita).get(2));
                    System.out.println(Key);
                }
                */
                Orden.get(Key).push(DataSet.get(Banderita).get(2));
                Banderita--;
            }

            Banderita = SuppBanderita + 6;
            SuppBanderita = Banderita;
        }


        //System.out.println(DataSet.size());
        //System.out.println(Banderita);

        /*
        for(int i = 0; i < DataSet.size(); i++)
        {
            NOrden.add(new LinkedList<String>());
        }
        
        for(int i = 0; i < DataSet.size(); i++)
        {
            for(int j = 0; j < DataSet.get(i).size() ; j++)
            {    
                if( i != 0)
                NOrden.get(i).addFirst(DataSet.get(i).get(j));
            }
        }
        */
        //for(int i = 0; i < 5; i++)
        //{
        //    System.out.println(Orden.get(i));
        //}
        /*
        it = Orden.keySet().iterator();
        while(it.hasNext())
        {
            String Key = it.next().toString();
            System.out.println( Key + ": " + Orden.get(Key));
            if(Key == "20")
                break;
        }
        */
        //System.out.println(Orden.size());
        //System.out.println(Orden.get("10").size());
        System.out.println(Orden.get("10"));
        System.out.println(Orden.get("187588"));
        /*
        for(int i = DataSet.size()/6 - 6; i < DataSet.size()/6; i++)
        {
            System.out.println(NOrden.get(i));
        }
        */
    }
}