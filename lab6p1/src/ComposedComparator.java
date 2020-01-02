import java.util.Comparator;

public class ComposedComparator implements Comparator<Song> {

    private Comparator<Song> c1;
    private Comparator<Song> c2;

    public ComposedComparator (Comparator<Song> c1, Comparator<Song> c2){
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public int compare(Song a, Song b) {
        int val = 0;
        if (c1.compare(a, b) == 0) {
            val += c2.compare(a, b);
        } else {
            val += c1.compare(a, b);
        }
        return val;
    }
}
