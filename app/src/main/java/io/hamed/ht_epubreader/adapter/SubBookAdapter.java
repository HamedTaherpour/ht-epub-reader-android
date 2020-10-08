package io.hamed.ht_epubreader.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import io.hamed.ht_epubreader.R;
import io.hamed.htepubreadr.entity.SubBookEntity;


/**
 * Author: Hamed Taherpour
 * *
 * Created: 10/7/2020
 * *
 * Address: https://github.com/HamedTaherpour
 */
public class SubBookAdapter extends ListAdapter<SubBookEntity, SubBookAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<SubBookEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<SubBookEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull SubBookEntity oldItem, @NonNull SubBookEntity newItem) {
            return oldItem.getHref().equalsIgnoreCase(newItem.getHref());
        }

        @Override
        public boolean areContentsTheSame(@NonNull SubBookEntity oldItem, @NonNull SubBookEntity newItem) {
            return oldItem.getHref().equalsIgnoreCase(newItem.getHref()) &&
                    oldItem.getTitle().equalsIgnoreCase(newItem.getTitle());
        }
    };
    private OnItemClickListener itemClickListener;

    public SubBookAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, SubBookEntity entity, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            itemView.setOnClickListener(this);
        }

        public void bind(SubBookEntity entity) {
            textView.setText(entity.getTitle());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (itemClickListener != null && position != RecyclerView.NO_POSITION) {
                SubBookEntity entity = getItem(position);
                itemClickListener.onItemClick(v, entity, position);
            }
        }
    }
}

