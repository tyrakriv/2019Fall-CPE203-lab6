import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.*;

import org.junit.Test;
import org.junit.Before;

public class TestCases
{
   private static final Song[] songs = new Song[] {
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Gerry Rafferty", "Baker Street", 1978)
      };

   @Test
   public void testArtistComparator()
   {
      ArtistComparator artist = new ArtistComparator();
      int val = artist.compare(songs[0], songs[1]);
      int val2 = artist.compare(songs[4], songs[4]);
      assertTrue(val < 0);
      assertTrue(val2 == 0);

   }

   @Test
   public void testLambdaTitleComparator()
   {
      Comparator<Song> comp = (t1, t2) -> t1.getTitle().compareTo(t2.getTitle());
      int val = comp.compare(songs[0], songs[1]);
      assertTrue(val > 0);
   }

   @Test
   public void testYearExtractorComparator()
   {
      Comparator<Song> comp = Comparator.comparing(Song::getYear).reversed();
      int val = comp.compare(new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
              new Song("Avett Brothers", "Talk on Indolence", 2006));
      assertTrue(val > 0);

   }

   @Test
   public void testComposedComparator()
   {
      Comparator<Song> artcomp = Comparator.comparing(Song::getArtist);
      Comparator<Song> yearcomp = Comparator.comparing(Song::getYear);
      ComposedComparator comp = new ComposedComparator(artcomp, yearcomp);
      int val = comp.compare(new Song("Gerry Rafferty", "Baker Street", 1998),
              new Song("Foo Fighters", "Baker Street", 1997));
      assertTrue(val > 0);
   }

   @Test
   public void testThenComparing()
   {
      Comparator<Song> titlecomp = Comparator.comparing(Song::getTitle);
      Comparator<Song> artcomp = Comparator.comparing(Song::getArtist).reversed();
      Comparator<Song> thencomp = titlecomp.thenComparing(artcomp);
      int val = thencomp.compare(new Song("Foo Fighters", "Baker Street", 1997),
              new Song("Gerry Rafferty", "Baker Street", 1978));
      assertTrue(val > 0);
   }

   @Test
   public void runSort()
   {
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
      List<Song> expectedList = Arrays.asList(
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Gerry Rafferty", "Baker Street", 1978),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
         );

      Comparator<Song> titlecomp = Comparator.comparing(Song::getTitle);
      Comparator<Song> artcomp = Comparator.comparing(Song::getArtist);
      Comparator<Song> yearcomp = Comparator.comparing(Song::getYear);
      Comparator<Song> thencomp = artcomp.thenComparing(titlecomp).thenComparing(yearcomp);

      songList.sort(thencomp);

      assertEquals(songList, expectedList);
   }
}
