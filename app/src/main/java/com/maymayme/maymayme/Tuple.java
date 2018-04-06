package com.maymayme.maymayme;

public class Tuple<R, S, T> {

    private R name;
    private S data;
    private T index;

    public Tuple(R r, S s, T t) {
        this.name = r;
        this.data = s;
        this.index = t;
    }

    public R getName()
    {
        return name;
    }

    public void setName(R name)
    {
        this.name = name;
    }

    public S getData()
    {
        return data;
    }

    public void setData(S data)
    {
        this.data = data;
    }

    public T getIndex()
    {
        return index;
    }

    public void setIndex(T index)
    {
        this.index = index;
    }

}
