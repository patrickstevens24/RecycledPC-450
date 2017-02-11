package pws24.uw.tacoma.edu.recycledpc.item;
import android.app.ListFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pws24.uw.tacoma.edu.recycledpc.R.drawable.computer;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ItemContent extends ListFragment {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public static final String DETAIL_PARAM = "detail_param";
    private ItemContent.DummyItem mCourseItem;


    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 12;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createCourseItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createCourseItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }


    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem implements Serializable {
        public final String id;
        public final String content;
        public final String details;
        public final String title;
        public final String shortDesc;
        public final int imageTest;

        public DummyItem(String id, String content, String details) {
            this.imageTest = computer;
            this.id = id;
            this.content = content;
            this.details = details;
            this.title = "Item " + id;
            this.shortDesc = "This is the description for item number " + id;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}