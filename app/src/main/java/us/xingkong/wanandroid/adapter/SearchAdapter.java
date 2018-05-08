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
import us.xingkong.wanandroid.bean.SearchBean;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid
 * @类名: resultAdapter
 * @创建时间: 2018/3/25 17:03
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<SearchBean.datas> searchList;
    private onItemClickListener listener;

    public SearchAdapter(Context context, List<SearchBean.datas> searchList) {
        this.context = context;
        this.searchList = searchList;
        inflater = LayoutInflater.from(this.context);
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public void clear() {
        searchList.clear();
        notifyDataSetChanged();
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_card_item, parent, false);
        SearchHolder holder = new SearchHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SearchHolder holder, final int position) {
        SearchBean.datas datas = searchList.get(position);
        if (listener != null) {
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position);
                }
            });
        }
        holder.fresh.setVisibility(View.GONE);
        holder.tags_name.setVisibility(View.GONE);
        holder.title.setText(datas.getTitle());
        String details = "";
        details += "作者：" + datas.getAuthor() + '\n';
        if (!datas.getSuperChapterName().equals("") &&
                !datas.getChapterName().equals("")) {
            details += "分类：" + datas.getSuperChapterName() + '\b' + '\b' + '\\' + '\b' + '\b' + datas.getChapterName() + '\n';
        }
        details += "时间：" + datas.getNiceDate();
        holder.details.setText(details);
    }

    @Override
    public int getItemCount() {
        if (searchList != null) {
            return searchList.size();
        } else {
            return 0;
        }
    }

    public static class SearchHolder extends RecyclerView.ViewHolder {
        private TextView title, fresh, tags_name, details;
        private CardView card;

        public SearchHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            fresh = itemView.findViewById(R.id.fresh);
            tags_name = itemView.findViewById(R.id.tags_name);
            details = itemView.findViewById(R.id.details);
            card = itemView.findViewById(R.id.card);
        }
    }
}
