package com.feicuiedu.com.easyshop.main.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feicuiedu.com.easyshop.R;
import com.feicuiedu.com.easyshop.commons.ActivityUtils;
import com.feicuiedu.com.easyshop.main.details.GoodsDetailActivity;
import com.feicuiedu.com.easyshop.model.GoodsInfo;
import com.feicuiedu.com.easyshop.network.EasyShopClient;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 商城展示的Fragment
 */
public class ShopFragment extends MvpFragment<ShopView, ShopPresenter>
        implements ShopView<List<GoodsInfo>> {

    private static final String KEY_GENRE = "key_genre";

    public static ShopFragment getInstance(int genre) {
        ShopFragment shopFragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_GENRE, genre);
        shopFragment.setArguments(args);
        return shopFragment;
    }

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.tv_load_error)
    TextView tv_load_error;
    @Bind(R.id.refreshLayout)
    PtrClassicFrameLayout ptrLayout;
    @BindString(R.string.load_more_end)
    String load_more_end;
    @BindString(R.string.refresh_more_end)
    String refresh_more_end;

    private ActivityUtils activityUtils;
    private GoodsAdapter goodsAdapter;
    private HashMap<String, String> shopMap;
    /**
     * 获取商品时,分页下标
     */
    private int pageInt = 1;
    /**
     * 获取商品时,商品的类型,获取全部商品时为空
     */
    private String pageType = "";
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        goodsAdapter = new GoodsAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_shop, container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    @NonNull
    @Override
    public ShopPresenter createPresenter() {
        return new ShopPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
        recyclerView.setAdapter(goodsAdapter);
    }

    /*RecyclerView和PtrClassicFrameLayout的初始化*/
    private void initView() {
        /*设置recyclerView的manager*/
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        /*设置refreshLayout*/
        /*使用本对象作为key，来记录上一次刷新时间，如果两次下拉间隔太近，不会触发刷新方法*/
        ptrLayout.setLastUpdateTimeRelateObject(this);
        ptrLayout.setBackgroundResource(R.color.recycler_bg);
        /*关闭Header所耗时长*/
        ptrLayout.setDurationToCloseHeader(1500);

        ptrLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                onLoaNextPage();
                presenter.loadData(shopMap);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initData();
                presenter.refreshData(shopMap);
            }
        });
        /*商城页,商品的单击事件*/
        goodsAdapter.setListener(new GoodsAdapter.OnItemClickedListener() {
            @Override
            public void onPhotoClicked(GoodsInfo goodsInfo) {
                Intent intent = new Intent(getContext(), GoodsDetailActivity.class);
                intent.putExtra("uuid", goodsInfo.getUuid());
                startActivity(intent);
            }
        });
    }

    /*刚进入页面时请求数据的初始化*/
    private void initData() {
        shopMap = new HashMap<>();
        pageInt = 1;
        shopMap.put("pageNo", pageInt + "");
        shopMap.put("type", pageType);
    }

    /*上拉加载时请求数据中Page下标的累加*/
    private void onLoaNextPage() {
        pageInt++;
        shopMap.put("pageNo", pageInt + "");
        shopMap.put("type", pageType);
    }

    /*点击错误视图时刷新数据*/
    @OnClick(R.id.tv_load_error)
    public void onClick() {
        ptrLayout.autoRefresh();
    }

    @Override
    public void onStart() {
        super.onStart();
        /*当前页面没有数据时,刷新*/
        if (goodsAdapter.getItemCount() == 0) {
            ptrLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrLayout.autoRefresh();
                }
            }, 200);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*取消网络请求*/
        EasyShopClient.getInstance().cancelRequest(presenter.refreshRequest);
        EasyShopClient.getInstance().cancelRequest(presenter.loadRequest);
    }

    @Override
    public void showRefresh() {
        /*数据刷新时,重置pageInt*/
        pageInt = 1;
        tv_load_error.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showRefreshError(String msg) {
        ptrLayout.refreshComplete();
        if (goodsAdapter.getItemCount() > 0) {
            activityUtils.showToast(msg);
            return;
        }
        tv_load_error.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRefreshEnd() {
        activityUtils.showToast(refresh_more_end);
        ptrLayout.refreshComplete();
    }

    @Override
    public void hideRefresh() {
        ptrLayout.refreshComplete();
    }

    @Override
    public void showLoadMoreLoading() {
        tv_load_error.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLoadMoreError(String msg) {
        ptrLayout.refreshComplete();
        if (goodsAdapter.getItemCount() > 0) {
            activityUtils.showToast(msg);
            return;
        }
        tv_load_error.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadMoreEnd() {
        activityUtils.showToast(load_more_end);
        ptrLayout.refreshComplete();
    }

    @Override
    public void hideLoadMore() {
        ptrLayout.refreshComplete();
    }

    @Override
    public void addMoreData(List<GoodsInfo> data) {
        goodsAdapter.addData(data);
    }

    @Override
    public void addRefreshData(List<GoodsInfo> data) {
        goodsAdapter.clear();
        if (data != null) {
            goodsAdapter.addData(data);
        }
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

}