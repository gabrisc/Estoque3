package com.example.estoque3.Activity.Establishment.ui.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.estoque3.Activity.Establishment.CheckListFragment;
import com.example.estoque3.Activity.Establishment.FloatingCapitalFragment;
import com.example.estoque3.Activity.Establishment.GoalsFragment;
import com.example.estoque3.R;

public class SectionsPagerAdapterEstablishment extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{
            R.drawable.ic_baseline_checklist_24,
            R.drawable.ic_baseline_ballot_24,
            R.drawable.ic_baseline_payments_24,
            R.drawable.ic_baseline_published_with_changes_24,
            R.drawable.ic_baseline_miscellaneous_services_24};
    private final Context mContext;

    public SectionsPagerAdapterEstablishment(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        if (position == 0){
            fragment = PlaceholderFragmentEstablishment.newInstance(1);
        }else if (position == 1){
            fragment = GoalsFragment.newInstance(2);
        }else if (position == 2){
            fragment = CheckListFragment.newInstance(3);
        }else if (position == 3){
            fragment = FloatingCapitalFragment.newInstance(4);
        }else  if (position ==4){
            fragment = ProfitabilityFragment.newInstance(5);
        }
        return fragment;
    }

    Drawable drawable;

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        drawable = mContext.getResources().getDrawable(TAB_TITLES[position]);
        SpannableStringBuilder sb = new SpannableStringBuilder("  ");
        try {
            drawable.setBounds(5,5,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            ImageSpan span = new ImageSpan(drawable, DynamicDrawableSpan.ALIGN_BASELINE);
            sb.setSpan(span,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }catch (Exception e){}
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}