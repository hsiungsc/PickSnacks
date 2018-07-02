package hsiungsc.com.picksnacks;

public class Snack {
    private String name;
    private boolean veg;
    private boolean isChecked=false;

    public Snack(String name, boolean veg)
    {
        this.name = name;
        this.veg = veg;
    }

    public String getName()
    {
        return name;
    }

    public boolean isVeg()
    {
        return veg;
    }

    public boolean getChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
