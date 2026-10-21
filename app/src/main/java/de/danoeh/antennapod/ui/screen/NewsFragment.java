package de.danoeh.antennapod.ui.screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.storage.database.DBReader;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import de.danoeh.antennapod.model.feed.SortOrder;
import de.danoeh.antennapod.ui.episodeslist.EpisodesListFragment;

import java.util.List;

public class NewsFragment extends EpisodesListFragment {
    public static final String TAG = "NewsFragment";
    private static final FeedItemFilter FILTER_NEWS = new FeedItemFilter(FeedItemFilter.NO_MEDIA);
    private static Pair<Integer, Integer> scrollPosition = null;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = super.onCreateView(inflater, container, savedInstanceState);
        toolbar.inflateMenu(R.menu.favorites);
        toolbar.setTitle(R.string.news_label);
        updateToolbar();
        emptyView.setIcon(R.drawable.ic_news);
        emptyView.setTitle(R.string.no_news_head_label);
        emptyView.setMessage(R.string.no_news_label);
        return root;
    }

    @Override
    protected FeedItemFilter getFilter() {
        return FILTER_NEWS;
    }

    @Override
    protected String getFragmentTag() {
        return TAG;
    }

    @Override
    public void onPause() {
        super.onPause();
        scrollPosition = recyclerView.getScrollPosition();
    }

    @Override
    protected void onItemsFirstLoaded() {
        recyclerView.restoreScrollPosition(scrollPosition);
    }

    @NonNull
    @Override
    protected List<FeedItem> loadData() {
        return DBReader.getEpisodes(0, page * EPISODES_PER_PAGE, FILTER_NEWS, SortOrder.DATE_NEW_OLD);
    }

    @NonNull
    @Override
    protected List<FeedItem> loadMoreData(int page) {
        return DBReader.getEpisodes((page - 1) * EPISODES_PER_PAGE, EPISODES_PER_PAGE, FILTER_NEWS,
                SortOrder.DATE_NEW_OLD);
    }

    @Override
    protected int loadTotalItemCount() {
        return DBReader.getTotalEpisodeCount(FILTER_NEWS);
    }
}
