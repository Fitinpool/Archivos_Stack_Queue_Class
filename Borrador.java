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
        ChampId Aux = new ChampId();

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

            /*            
            for(int j = 5394; j < 5400+6 ; j++)
            {   
                for(int i = 0; i < DataSet.get(j).size(); i++)
                    System.out.print("\"" + DataSet.get(j).get(i) + "\"");
        
                System.out.println();
            }
            */

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

        Map <String , Cola> Orden = new LinkedHashMap<String , Cola>();
        Map <String , Pila> NOrden = new LinkedHashMap<String , Pila>();
        ArrayList <String> MatchIgnorar = new ArrayList<String>();
        //Map <Integer , Integer> Prueba = new LinkedHashMap<Integer , Integer>();

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
                Orden.put(DataSet.get(i).get(0), new Cola());
                NOrden.put(DataSet.get(i).get(0), new Pila());
            }
        }

        //System.out.println(Orden.size());
        //System.out.println(DataSet.size());
        //Ordena champ respecto al banId
        Banderita = 6;
        SuppBanderita = 0;
        while(DataSet.size() - 1 != SuppBanderita)
        {
            //System.out.println(Banderita);
            //System.out.println(SuppBanderita);
            for(int i = SuppBanderita; i < Banderita; i++)
            {
                for(int j = SuppBanderita;j < i; j++)
                {
                    if(Integer.parseInt(Campeones.get(i).GetBanId()) < Integer.parseInt(Campeones.get(j).GetBanId()))
                    {
                        Aux = Campeones.get(j);
                        Campeones.set(j , Campeones.get(i));
                        Campeones.set(i , Aux);
                    }
                }
            }

            SuppBanderita += 6;
            Banderita += 6;

        }

        /*
        for(int i = DataSet.size() - 13; i < DataSet.size() - 1; i++)
        {
            System.out.println(Campeones.get(i).GetBanId());
        }
        */

        //Cola
        Banderita = 6;
        SuppBanderita = 0;

        Iterator it = Orden.keySet().iterator();
        while(it.hasNext())
        {
            String Key = it.next().toString();
            for(int i = SuppBanderita; i < Banderita; i++)
            {
                Orden.get(Key).addFirst(Campeones.get(i).GetChampId());
            }

            Banderita += 6;
            SuppBanderita += 6;
        }

        //imprime cola
        System.out.println("Imprimiendo Cola: ");
        it = Orden.keySet().iterator();
        //Para imprimir todo los match, reemplazar el for por el while comentado
        //ya que el for solo imprime los primeros 6 match
        //while(it.hasNext())
        for(int j = 0; j < 6; j++)
        {
            String Key = it.next().toString();
            System.out.print(Key + " : ");
            for(int i = 0; i < 6; i++)
            {
                /*
                -para imprimir match especificos reemplazar el Key por el numero de match a buscar
                -considerar que tiene que estar entre comillas "187588" (Solo hay 187588 matchId)
                -en caso de tirar error es porque el match que busca fue eliminado o no lo ingreso bien
                */
                System.out.print(Orden.get(Key).removeLast()); 
                if(i != 5)
                {
                    System.out.print(",");
                }
                else
                {
                    System.out.print(".\n");
                }
            }
        }

        //Pila
        Banderita = 6;
        SuppBanderita = 0;
        Iterator itNot = NOrden.keySet().iterator();
        while(itNot.hasNext())
        {
            String Key = itNot.next().toString();
            for(int i = SuppBanderita; i < Banderita; i++)
            {
                NOrden.get(Key).push(Campeones.get(i).GetChampId());
            }

            Banderita += 6;
            SuppBanderita += 6;
        }

        //imprime pila
        System.out.println("Imprimiendo Pila: ");
        itNot = NOrden.keySet().iterator();
        //Para imprimir todo los match, reemplazar el for por el while comentado
        //ya que el for solo imprime los primeros 6 match
        //while(itNot.hasNext())
        for(int j = 0; j < 6; j++)
        {
            String Key = itNot.next().toString();
            System.out.print(Key + " : ");
            for(int i = 0; i < 6; i++)
            {   
                /*
                -para imprimir match especificos reemplazar el Key por el numero de match a buscar
                -considerar que tiene que estar entre comillas "187588" (Solo hay 187588 matchId)
                -en caso de tirar error es porque el match que busca fue eliminado o no lo ingreso bien
                */
                System.out.print(NOrden.get(Key).pop());
                if(i != 5)
                {
                    System.out.print(",");
                }
                else
                {
                    System.out.print(".\n");
                }
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
        */
        //System.out.println(Orden.size());
        //System.out.println(Orden.get("10").size());
        //System.out.println(Orden.get("10"));
        //System.out.println(Orden.get("187588"));
        /*
        for(int i = DataSet.size()/6 - 6; i < DataSet.size()/6; i++)
        {
            System.out.println(NOrden.get(i));
        }
        */
    }
}