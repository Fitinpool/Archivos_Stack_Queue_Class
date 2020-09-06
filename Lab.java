/*


*/
import java.io.*;
import java.security.Key;
import java.util.*;



public class Lab {

    public static void main(String[] args)
    {
        //empiezan en null los datos para archivos
        File Archivo = null;
        FileReader FR = null;
        BufferedReader BR = null;
        
        //variables que se usaran como banderas, index etc
        int CantidadLineas = 0;
        int Comillas = 0;
        int Banderita = 0;
        int SuppBanderita = 0;
        ChampId Aux = new ChampId();
        String Line;
        String Dato = "";
        
        //array que guardara los campeones id de los campeones con los bans
        ArrayList <ChampId> Campeones = new ArrayList<ChampId>();

        //Arrays para guardar los datos 
        ArrayList <ArrayList<String>> DataSet = new ArrayList<ArrayList<String>>();
        ArrayList <ArrayList<String>> Ignorar = new ArrayList<ArrayList<String>>();
        
        //Lee el archivo teambans.csv
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
                    if(Line.charAt(j) != ',')
                    {
                        if(Line.charAt(j) != '\"')
                        { 
                            Dato += Line.charAt(j);
                        }
                        else
                        {   
                            Comillas++;
                            if(Comillas%2 == 0 && Comillas != 0)
                            {
                                DataSet.get(i).add(Dato);
                                Dato = "";
                            }
                        
                        }
                    }
                }
            }
            
            BR.close();

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

        //Lee el archivo out.txt para encontrar los match incompletos
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

        //Busca Match a ignorar
        for(int i = 0; i < DataSet.size(); i++)
        {
            for(int j = 0; j < Ignorar.size(); j++)
            {
                if( Integer.parseInt(Ignorar.get(j).get(3)) == i)
                {
                    MatchIgnorar.add(DataSet.get(i).get(0));
                }
            }
        }

        //Elimina Match incompletos
        for(int i = 1; i < DataSet.size(); i++)
        {
            for(int j = 0; j < MatchIgnorar.size(); j++)
            { 
                if(Integer.parseInt(MatchIgnorar.get(j)) == Integer.parseInt(DataSet.get(i).get(0)))
                {   
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

        //Proceso que guarda matchId en las maps para colas y pilas
        for(int i = 0; i < DataSet.size(); i++)
        {
            if( i != 0 && i%6 == 0) 
            {
                Orden.put(DataSet.get(i).get(0), new Cola());
                NOrden.put(DataSet.get(i).get(0), new Pila());
            }
        }

        //Ordena los campeones respecto a su banId
        Banderita = 6;
        SuppBanderita = 0;
        while(DataSet.size() - 1 != SuppBanderita)
        {
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
    }
}