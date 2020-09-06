import java.util.ArrayList;

public class Cola
{
    private ArrayList contenido;

    public Cola()
    {
        contenido = new ArrayList();
    }

    public void addFirst(Object in)
    {
        contenido.add(in);
    }

    public Boolean isEmpty()
    {
        return(contenido.size() == 0);
    }

    public Object removeLast()
    {
        Object out = null;

        if(!isEmpty())
        {
            out = contenido.get(0);
            contenido.remove(0);
        }
        
        return out;
    }

}
