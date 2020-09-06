import java.util.ArrayList;

public class Pila {

    private ArrayList contenido;

    public Pila()
    {
        contenido = new ArrayList();
    }

    public Object pop()
    {
        Object out = null;
        if(!isEmpty())
        {
            out = contenido.get((contenido.size()-1));
            contenido.remove((contenido.size()-1));
        }
        return out;
    }

    public void push(Object in)
    {
        contenido.add(in);
    }

    public Boolean isEmpty()
    {
        return (contenido.size() == 0);
    }
}