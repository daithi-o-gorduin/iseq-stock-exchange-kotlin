package finalyearproject.drawer.Main;

/**
 * Created by Dvaid on 09/01/2015.
*/
public class NavDrawerItem {

    private String title;
    private int icon;
    private boolean isSelected = false;

    public NavDrawerItem(){}


    public NavDrawerItem(String title, int icon, boolean isSelected){
        this.title = title;
        this.icon = icon;
        this.isSelected = isSelected;
    }

    public String getTitle(){
        return this.title;
    }

    public int getIcon(){
        return this.icon;
    }


    public boolean getSelected(){
        return this.isSelected;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setIcon(int icon){
        this.icon = icon;
    }

    public void setSelected(boolean isCounterVisible){
        this.isSelected = isCounterVisible;
    }
}