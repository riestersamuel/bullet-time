package de.hdmstuttgart.bulletjournalapp.BulletsPackage;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdmstuttgart.bulletjournalapp.R;

public class BulletListAdapter extends RecyclerView.Adapter<BulletListAdapter.BulletViewHolder> {
    private ArrayList<Bullet> bulletList;

    TextWatcher textWatcher;
    iOnIconClickListener iOnIconClickListener;
    public interface iOnIconClickListener {
        void onBulletClicked(Bullet bullet, int position);
    }

    public BulletListAdapter(ArrayList<Bullet> bulletList, TextWatcher textWatcher, iOnIconClickListener iOnIconClickListener){
        this.bulletList = bulletList;
        this.iOnIconClickListener = iOnIconClickListener;
    }

    @NonNull
    @Override
    public BulletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bullets_layout, parent, false);
        return new BulletViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BulletListAdapter.BulletViewHolder holder, int position) {
        Bullet bullet = bulletList.get(position);
        holder.bulletText.setText(bullet.getContent());

        // If the bullet category is X -> Change image

        holder.bulletCategory.setOnClickListener(view -> {
            iOnIconClickListener.onBulletClicked(bullet, position);
        });

        holder.isDone = bullet.isChecked();
        holder.bulletCategory.setImageResource(bullet.getCategory().getImage());
    }

    @Override
    public int getItemCount() {
        if (bulletList == null) return 0;
        else return bulletList.size();
    }

    public class BulletViewHolder extends RecyclerView.ViewHolder{
        EditText bulletText;
        boolean isDone;
        ImageButton bulletCategory;
        public BulletViewHolder(@NonNull View itemView) {
            super(itemView);
            bulletText = itemView.findViewById(R.id.editText);
            bulletCategory = itemView.findViewById(R.id.imageButton);
        }
    }
}