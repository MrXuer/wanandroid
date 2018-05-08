package us.xingkong.wanandroid.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import us.xingkong.wanandroid.R;
import us.xingkong.wanandroid.bean.HotkeyBean;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid
 * @类名: myAdapter
 * @创建时间: 2018/3/24 23:04
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public class HotkeyAdapter extends RecyclerView.Adapter<HotkeyAdapter.HotkeyHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<HotkeyBean.data> nameList;
    private onItemClickListener listener;

    public HotkeyAdapter(Context context, List<HotkeyBean.data> nameList) {
        this.context = context;
        this.nameList = nameList;
        inflater = LayoutInflater.from(this.context);
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public void clear(){
        nameList.clear();
        notifyDataSetChanged();
    }

    @Override
    public HotkeyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_hotkey, parent, false);
        HotkeyHolder viewHolder = new HotkeyHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HotkeyHolder holder, final int position) {
        HotkeyBean.data data = nameList.get(position);
        holder.key.setText(data.getName());
        if (listener != null) {
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (nameList != null) {
            return nameList.size();
        } else {
            return 0;
        }

    }

    /*public void onDestory() {
        nameList.clear();
        nameList = null;
        for (int i = 0; i < holderList.size(); i++) {
            holderList.get(i).itemView.setOnClickListener(null);
        }
        holderList.clear();
        holderList = null;
    }*/

    public static class HotkeyHolder extends RecyclerView.ViewHolder {

        private TextView key;
        private CardView card;

        public HotkeyHolder(View itemView) {
            super(itemView);
            key = itemView.findViewById(R.id.key);
            card = itemView.findViewById(R.id.card_hotkey);
        }
    }
}
