package com.example.desafiofluxitmvvm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.desafiofluxitmvvm.models.RamdomUserResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.CustomViewHolder> {
    private static final int VIEW_TYPE_ITEM = 0;
    public static final int VIEW_TYPE_LOADING = 1;
    private List<RamdomUserResponse> responseList;
    private Context context;
    private UserClick userClick;


    public AdapterUser(List<RamdomUserResponse> responseList, Context context, UserClick userClick) {
        this.responseList = responseList;
        this.context = context;
        this.userClick = userClick;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
            return new DataHolder(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_progress, parent, false);
            return new ProgressViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        if (holder instanceof DataHolder) {
         ((DataHolder) holder).textViewName.setText(responseList.get(position).getName().getFirst());
            ((DataHolder) holder).textViewSurName.setText(responseList.get(position).getName().getLast());
            Glide.with(context).load(responseList.get(position).getPicture().getMedium()).into(((DataHolder) holder).circleImageView);
            holder.itemView.setOnClickListener(v -> userClick.onClick(responseList.get(position)));
        }
    }


    @Override
    public int getItemCount() {
        return responseList.size();
    }

    public void addData(List<RamdomUserResponse> data) {
        responseList.addAll(data);
        notifyDataSetChanged();
    }

    public void addNullData() {
        responseList.add(null);
        notifyItemInserted(responseList.size() - 1);
    }

    public void removeNull() {
        responseList.remove(responseList.size() - 1);
        notifyItemRemoved(responseList.size());
    }



    class DataHolder extends CustomViewHolder {
        CircleImageView circleImageView;
        TextView textViewName;
        TextView textViewSurName;
        DataHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textView_Name);
            textViewSurName = itemView.findViewById(R.id.textView_surName);
            circleImageView = itemView.findViewById(R.id.iv_thumbail);
        }
    }

    class ProgressViewHolder extends CustomViewHolder {
        ProgressBar progressBar;
        public ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbarRow);
        }
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        public CustomViewHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (responseList.get(position) != null)
            return VIEW_TYPE_ITEM;
        else
            return VIEW_TYPE_LOADING;
    }



    public interface UserClick {
        void onClick(RamdomUserResponse response);
    }
}
