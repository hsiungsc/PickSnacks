package hsiungsc.com.picksnacks;

import java.util.ArrayList;
import java.util.Iterator;

public class Snacks {
    private ArrayList<Snack> mSnacks;

    public Snacks()
    {
        mSnacks = new ArrayList<Snack>();
    }

    public void addSnacks(String[] snackNames, boolean veg)
    {
        for(String snack : snackNames)
        {
            mSnacks.add(new Snack(snack, veg));
        }
    }

    public void addSnack(String name, boolean veg)
    {
        mSnacks.add(new Snack(name, veg));
    }

    public void removeSnacks(boolean veg)
    {
        Iterator<Snack> it = mSnacks.iterator();
        while (it.hasNext()) {
            Snack snack = it.next();
            if(snack.isVeg() == veg)
            {
                // Remove this item
                it.remove();
            }
        }
    }

    public boolean isExist(String name)
    {
        Iterator<Snack> it = mSnacks.iterator();
        while (it.hasNext()) {
            Snack snack = it.next();
            if(snack.getName().toUpperCase().equals(name.toUpperCase()))
            {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Snack> getSnacks()
    {
        return mSnacks;
    }

    public void resetSelection()
    {
        for(Snack snack : mSnacks)
        {
            snack.setChecked(false);
        }
    }
}
