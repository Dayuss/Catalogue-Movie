package com.learn.dayuss.cataloguemovie.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.learn.dayuss.cataloguemovie.Fragment.PopularFragment;
import com.learn.dayuss.cataloguemovie.Fragment.UpComingFragment;
import com.learn.dayuss.cataloguemovie.Fragment.nowPlayingFragment;
import com.learn.dayuss.cataloguemovie.R;

/**
 * Created by dayuss on 29/11/18.
 */

public class viewAdapter  extends FragmentPagerAdapter {
    private Context mContext;

    public viewAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new nowPlayingFragment();
        } else if (position == 1){
            return new UpComingFragment();
        } else {
            return new PopularFragment();
        }
    }
    @Override
    public int getCount() {
        return 3;
    }
    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position

        switch (position) {
            case 0:
                return mContext.getString(R.string.menu_now_playing);
            case 1:
                return mContext.getString(R.string.menu_up_coming);
            case 2:
                return mContext.getString(R.string.menu_popular);
            default:
                return null;
        }
    }
}
