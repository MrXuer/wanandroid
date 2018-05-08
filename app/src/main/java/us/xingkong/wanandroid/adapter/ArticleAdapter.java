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
import us.xingkong.wanandroid.bean.ArticleBean;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid.adapter
 * @类名: recAdapter
 * @创建时间: 2018/4/2 19:51
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int TYPE_EMPTY = -1;//空白
    private int TYPE_HEADER = 0;//带header
    private int TYPE_NORMAL = 1;//常规
    private int TYPE_FOOTER = 2;//带footer
    private Context context;
    private LayoutInflater inflater;
    private List<ArticleBean.datas> list;
    private View emptyView;
    private View headerView;
    private View footerView;
    private onItemClickListener listener;

    public ArticleAdapter(Context context, List<ArticleBean.datas> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(this.context);
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    public View getEmptyView() {
        return emptyView;
    }

    public void setEmptyView(View emptyView) {
        list.clear();
        this.emptyView = emptyView;
        notifyDataSetChanged();
    }

    public View getHeaderView() {
        return headerView;
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    public View getFooterView() {
        return footerView;
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    public void insertArticles(List<ArticleBean.datas> list, int position) {
        this.list.addAll(list);
        //notifyDataSetChanged();
        notifyItemRangeInserted(position,list.size());
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (emptyView != null && list.size() == 0) {
            return TYPE_EMPTY;
        }
       /* if (headerView == null && footerView == null) {
            return TYPE_NORMAL;
        }*/
        if (headerView != null && position == 0) {
            return TYPE_HEADER;
        }
        if (footerView != null && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (emptyView != null && viewType == TYPE_EMPTY) {
            ArticleHolder holder = new ArticleHolder(emptyView);
            return holder;
        }
        if (headerView != null && viewType == TYPE_HEADER) {
            ArticleHolder holder = new ArticleHolder(headerView);
            return holder;
        }
        if (footerView != null && viewType == TYPE_FOOTER) {
            ArticleHolder holder = new ArticleHolder(footerView);
            return holder;
        }
        View view = inflater.inflate(R.layout.activity_card_item, parent, false);
        ArticleHolder holder = new ArticleHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_EMPTY) {
            if (listener != null) {
                ((ArticleHolder) holder).reload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(position);
                    }
                });
            }
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            //if (holder instanceof articleHolder) {}
            ArticleBean.datas datas = list.get(position - 1);
            ((ArticleHolder) holder).title.setText(datas.getTitle());
            if (!datas.getFresh()) {
                ((ArticleHolder) holder).fresh.setVisibility(View.GONE);
            }
            if (datas.getTags().size() == 0) {
                ((ArticleHolder) holder).tags_name.setVisibility(View.GONE);
            }

            String details = "";
            details += "作者：" + datas.getAuthor() + '\n';

            if (!datas.getSuperChapterName().equals("") &&
                    !datas.getChapterName().equals("")) {
                details += "分类：" + datas.getSuperChapterName() + '\b' + '\b' + '\\' + '\b' + '\b' + datas.getChapterName() + '\n';
            }
            details += "时间：" + datas.getNiceDate();

            ((ArticleHolder) holder).details.setText(details);
            //return;
            /*else if (getItemViewType(position) == TYPE_HEADER) {
                return;
            } else {
                return;
            }*/
        }
    }

    @Override
    public int getItemCount() {
        if (emptyView != null && list == null) {
            return 0;
        } else if (headerView == null && footerView == null) {
            return list.size();
        } else if /*(headerView == null && footerView != null) {
            return list.size() + 1;
        } else if*/ (headerView == null || footerView == null) {
            return list.size() + 1;
        } else {
            return list.size() + 2;
        }
    }

    class ArticleHolder extends RecyclerView.ViewHolder {
        private TextView reload;
        private TextView title, fresh, tags_name, details;
        private CardView card;

        public ArticleHolder(View itemView) {
            super(itemView);
            /*if (itemView == emptyView) {
                return;
            }
            if (itemView == footerView) {
                return;
            }
            if (itemView == headerView) {
                return;
            }*/
            reload = itemView.findViewById(R.id.reload);
            title = itemView.findViewById(R.id.title);
            fresh = itemView.findViewById(R.id.fresh);
            tags_name = itemView.findViewById(R.id.tags_name);
            details = itemView.findViewById(R.id.details);
            card = itemView.findViewById(R.id.card);
        }
    }
}
