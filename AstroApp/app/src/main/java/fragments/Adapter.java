package fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class Adapter extends FragmentStateAdapter {
    public static List<Fragment> fragmentList;
    public static int CAPACITY = 2;
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return CAPACITY;
    }

    public Adapter(@NonNull FragmentActivity fragmentActivity,List<Fragment> tempList) {
        super(fragmentActivity);
        fragmentList = tempList;
    }
}
