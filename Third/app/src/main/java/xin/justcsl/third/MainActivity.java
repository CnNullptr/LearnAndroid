package xin.justcsl.third;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import xin.justcsl.third.fragments.AbsoluteFragment;
import xin.justcsl.third.fragments.LinearFragment;
import xin.justcsl.third.fragments.RelativeFragment;

public class MainActivity extends AppCompatActivity
        implements LinearFragment.OnFragmentInteractionListener, AbsoluteFragment.OnFragmentInteractionListener, RelativeFragment.OnFragmentInteractionListener
        //必须实现 fragment 的 onFragmentInteractionListener 接口
{

    private TabLayout tabForContent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init()
    {
        final List<Fragment> fragments = new ArrayList<>();
        /*fixme 如果有人看到了这里，我希望你能重构接下来三行代码
         *
         *   即：使用 Google 推荐的  newInstance(param1, param2) 函数来高效的创建 fragment
         */
        fragments.add(new LinearFragment());
        fragments.add(new RelativeFragment());
        fragments.add(new AbsoluteFragment());

        //先绑定组件，再设置监听，最后添加 tab
        //如果先添加 tab 再设置监听会导致第一次打开时不加载默认选中的
        tabForContent = (TabLayout) findViewById(R.id.tabForContent);

        //设置监听
        tabForContent.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                replaceFragment(fragments.get(tabForContent.getSelectedTabPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        //添加 tab
        tabForContent.addTab(tabForContent.newTab().setText("线性布局"), true);
        tabForContent.addTab(tabForContent.newTab().setText("绝对布局"));
        tabForContent.addTab(tabForContent.newTab().setText("相对布局"));
    }

    //替换 Fragment 的函数
    private void replaceFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayoutForContent, fragment).commit();
    }

    //必须实现 fragment 的 onFragmentInteraction 接口
    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }
}
