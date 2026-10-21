package de.danoeh.antennapod.ui.screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.storage.database.DBReader;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import de.danoeh.antennapod.model.feed.SortOrder;
import de.danoeh.antennapod.ui.episodeslist.EpisodesListFragment;

import java.util.List;

public class NewsFragment extends EpisodesListFragment {
    public static final String TAG = "NewsFragment";
    private static final int TAB_NEWS = 0;
    private static final int TAB_AUDIO = 1;
    private static final int TAB_VIDEO = 2;
    private static final FeedItemFilter FILTER_NEWS = new FeedItemFilter(FeedItemFilter.NO_MEDIA);
    private static final FeedItemFilter FILTER_AUDIO = new FeedItemFilter(FeedItemFilter.AUDIO_MEDIA);
    private static final FeedItemFilter FILTER_VIDEO = new FeedItemFilter(FeedItemFilter.VIDEO_MEDIA);
    private static int selectedTab = TAB_NEWS;
    private static Pair<Integer, Integer> scrollPosition = null;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = super.onCreateView(inflater, container, savedInstanceState);
        toolbar.inflateMenu(R.menu.favorites);
        toolbar.setTitle(R.string.media_hub_label);
        updateToolbar();

        TabLayout tabLayout = new TabLayout(requireContext());
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_news).setText(R.string.news_label));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_music_note).setText(R.string.audio_tab_label));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_videocam).setText(R.string.video_tab_label));
        AppBarLayout appBar = root.findViewById(R.id.appbar);
        appBar.addView(tabLayout, 1, new AppBarLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TabLayout.Tab initialTab = tabLayout.getTabAt(selectedTab);
        if (initialTab != null) {
            initialTab.select();
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTab = tab.getPosition();
                scrollPosition = null;
                updateEmptyView();
                swipeActions.setFilter(getFilter());
                page = 1;
                recyclerView.scrollToPosition(0);
                loadItems();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                recyclerView.scrollToPosition(0);
            }
        });
        updateEmptyView();
        swipeActions.setFilter(getFilter());
        return root;
    }

    private void updateEmptyView() {
        switch (selectedTab) {
            case TAB_AUDIO:
                emptyView.setIcon(R.drawable.ic_music_note);
                emptyView.setTitle(R.string.no_audio_head_label);
                emptyView.setMessage(R.string.no_audio_label);
                break;
            case TAB_VIDEO:
                emptyView.setIcon(R.drawable.ic_videocam);
                emptyView.setTitle(R.string.no_video_head_label);
                emptyView.setMessage(R.string.no_video_label);
                break;
            case TAB_NEWS:
            default:
                emptyView.setIcon(R.drawable.ic_news);
                emptyView.setTitle(R.string.no_news_head_label);
                emptyView.setMessage(R.string.no_news_label);
                break;
        }
    }

    @Override
    protected FeedItemFilter getFilter() {
        switch (selectedTab) {
            case TAB_AUDIO:
                return FILTER_AUDIO;
            case TAB_VIDEO:
                return FILTER_VIDEO;
            case TAB_NEWS:
            default:
                return FILTER_NEWS;
        }
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
        return DBReader.getEpisodes(0, page * EPISODES_PER_PAGE, getFilter(), SortOrder.DATE_NEW_OLD);
    }

    @NonNull
    @Override
    protected List<FeedItem> loadMoreData(int page) {
        return DBReader.getEpisodes((page - 1) * EPISODES_PER_PAGE, EPISODES_PER_PAGE, getFilter(),
                SortOrder.DATE_NEW_OLD);
    }

    @Override
    protected int loadTotalItemCount() {
        return DBReader.getTotalEpisodeCount(getFilter());
    }
}
