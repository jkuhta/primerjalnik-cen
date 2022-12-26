package si.fri.prpo.skupina8;

import java.util.ArrayList;
public class NinjasObj{
    public ArrayList<Item> items;

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for(Item i : items) {
            sb.append(items.toString());
            sb.append("\n\n");
        }

        return sb.toString();
    }
}

