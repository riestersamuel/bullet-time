package de.hdmstuttgart.bulletjournalapp.BulletsPackage;

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
    public ArrayList<Bullet> getBulletList() {
        return bulletList;
    }

    public void setBulletList(ArrayList<Bullet> bulletList) {
        this.bulletList = bulletList;
    }

    private ArrayList<Bullet> bulletList;

    iOnContentClickListener iOnContentClickListener;
    public interface iOnContentClickListener {
        void onContentChanged(Bullet bullet, int position);
    }
    iOnIconClickListener iOnIconClickListener;
    public interface iOnIconClickListener {
        void onBulletClicked(Bullet bullet, int position);
    }

    public BulletListAdapter(ArrayList<Bullet> bulletList, iOnContentClickListener iOnContentClickListener, iOnIconClickListener iOnIconClickListener){
        this.bulletList = bulletList;
        this.iOnContentClickListener = iOnContentClickListener;
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
            System.out.println("------------------------------------HIER bulletlistadapter.java holder.bulletCategory.setOnClickListener------------------------------------");
        });

        holder.bulletText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE){
                iOnContentClickListener.onContentChanged(bullet, position);
            }
            return false;
        });


        holder.isDone = bullet.isChecked();
        setIcon(holder, bullet);
    }

    private void setIcon(BulletViewHolder holder, Bullet bullet){
        if (bullet.isChecked() && bullet.getCategory() == BulletCategories.TASK) {
            holder.bulletCategory.setImageResource(R.drawable.baseline_check_box_24);
        }
        if (!bullet.isChecked() && bullet.getCategory() == BulletCategories.TASK) {
            holder.bulletCategory.setImageResource(R.drawable.baseline_check_box_outline_blank_24);
        }
        if (bullet.isChecked() && bullet.getCategory() == BulletCategories.DAILY_HIGHLIGHT) {
            holder.bulletCategory.setImageResource(R.drawable.baseline_star_24);
        }
        if (!bullet.isChecked() && bullet.getCategory() == BulletCategories.DAILY_HIGHLIGHT) {
            holder.bulletCategory.setImageResource(R.drawable.baseline_star_outline_24);
        }
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