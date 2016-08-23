package com.feicuiedu.com.easyshop.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.feicuiedu.com.easyshop.main.message.MessageFragment;
import com.feicuiedu.com.easyshop.main.maillist.MailListFragment;
import com.feicuiedu.com.easyshop.main.me.MeFragment;
import com.feicuiedu.com.easyshop.main.shop.ShopFragment;

import java.util.ArrayList;

/**
 * 主页面ViewPager的适配器
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragmentList = new ArrayList<>();

    public MainPagerAdapter(FragmentManager fm,int genre) {
        super(fm);
        fragmentList.add(ShopFragment.getInstance(genre));
        fragmentList.add(new MessageFragment());
        fragmentList.add(new MailListFragment());
        fragmentList.add(MeFragment.getInstance(genre));
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
}
