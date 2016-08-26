package com.feicuiedu.com.easyshop.main.shop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.feicuiedu.com.easyshop.R;
import com.feicuiedu.com.easyshop.model.GoodsInfo;
import com.feicuiedu.com.easyshop.network.EasyShopApi;
import com.feicuiedu.com.easyshop.network.EasyShopClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 商品展示的瀑布流
 */
public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsView> {

    private final List<GoodsInfo> list = new ArrayList<>();

    @Override
    public GoodsView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new GoodsView(view);
    }

    @Override
    public void onBindViewHolder(GoodsView holder, int position) {

        holder.tv_name.setText(list.get(position).getName());
        holder.tv_price.setText(list.get(position).getPrice());
        ImageLoader imageLoader = EasyShopClient.getInstance().
                getImageLoader();
        ImageLoader.ImageListener imageListener = imageLoader.getImageListener(
                holder.imageView,
                R.drawable.image_loding,
                R.drawable.error);
        imageLoader.get(EasyShopApi.IMAGE_URL + list.get(position).getPage(), imageListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<GoodsInfo> data) {
        list.addAll(data);
        notifyDataSetChanged();
    }

    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }

    public static class GoodsView extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_item_recycler)
        ImageView imageView;
        @Bind(R.id.tv_item_name)
        TextView tv_name;
        @Bind(R.id.tv_item_price)
        TextView tv_price;

        public GoodsView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
