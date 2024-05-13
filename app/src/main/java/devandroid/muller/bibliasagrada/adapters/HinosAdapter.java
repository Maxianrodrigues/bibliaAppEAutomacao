package devandroid.muller.bibliasagrada.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import devandroid.muller.bibliasagrada.R;
import devandroid.muller.bibliasagrada.model.Hinario;

public class HinosAdapter extends RecyclerView.Adapter<HinosAdapter.HinoViewHolder> {
    private List<Hinario> hinosList;
    private OnItemClickListener listener;

    public HinosAdapter(List<Hinario> hinosList) {
        this.hinosList = hinosList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public HinoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hino, parent, false);
        return new HinoViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull HinoViewHolder holder, int position) {
        Hinario hino = hinosList.get(position);
        holder.textViewHino.setText(hino.getTitulo());
        holder.bind(hinosList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return hinosList.size();
    }

    public static class HinoViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewHino;

        public HinoViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            textViewHino = itemView.findViewById(R.id.textViewHino);
        }

        public void bind(final Hinario hino, final OnItemClickListener listener) {
            textViewHino.setText(hino.getTitulo());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(hino);
                    }
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(Hinario hino);
    }
}