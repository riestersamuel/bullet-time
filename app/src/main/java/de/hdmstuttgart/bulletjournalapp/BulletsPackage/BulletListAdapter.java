package de.hdmstuttgart.bulletjournalapp.BulletsPackage;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdmstuttgart.bulletjournalapp.NotePackage.Note;
import de.hdmstuttgart.bulletjournalapp.R;

public class BulletListAdapter extends RecyclerView.Adapter<BulletListAdapter.BulletViewHolder> {
    public ArrayList<Bullet> getBulletList() {
        return bulletList;
    }
    public void setBulletList(ArrayList<Bullet> bulletList) {
        this.bulletList = bulletList;
    }
    private ArrayList<Bullet> bulletList;
    private TextWatcher textWatcher;
    iOnContentClickListener iOnContentClickListener;
    iOnIconClickListener iOnIconClickListener;
    iOnBulletLongClickListener iOnBulletLongClickListener;

    public interface iOnContentClickListener {
        void onContentChanged(Bullet bullet, int position);
    }
    public interface iOnIconClickListener {
        void onBulletClicked(Bullet bullet, int position);
    }
    public interface iOnBulletLongClickListener {
        void onBulletLongClicked(Bullet bullet, int position);
    }

    public BulletListAdapter(ArrayList<Bullet> bulletList, iOnContentClickListener iOnContentClickListener, iOnIconClickListener iOnIconClickListener, iOnBulletLongClickListener iOnBulletLongClickListener,TextWatcher textWatcher){
        this.bulletList = bulletList;
        this.iOnContentClickListener = iOnContentClickListener;
        this.iOnIconClickListener = iOnIconClickListener;
        this.textWatcher = textWatcher;
        this.iOnBulletLongClickListener = iOnBulletLongClickListener;
    }

    @NonNull
    @Override
    public BulletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bullets_layout, parent, false);
        return new BulletViewHolder(view);
    }

    /**
     *  Called by RecyclerView to display the data at the specified position
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull BulletListAdapter.BulletViewHolder holder, int position) {
        Bullet bullet = bulletList.get(position);
        holder.bulletText.setText(bullet.getContent());

        holder.bulletCategory.setOnClickListener(view -> {
            iOnIconClickListener.onBulletClicked(bullet, position);
        });

        holder.bulletText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE){
                iOnContentClickListener.onContentChanged(bullet, position);
            }
            return false;
        });

        holder.isDone = bullet.isChecked();
        setIcon(holder, bullet);
        holder.bulletText.addTextChangedListener(textWatcher);
        holder.bulletLayout.setOnLongClickListener(view -> {
            iOnBulletLongClickListener.onBulletLongClicked(bullet, position);
            return false;
        });
        holder.bulletText.setOnLongClickListener(view -> {
            iOnBulletLongClickListener.onBulletLongClicked(bullet, position);
            return false;
        });

    }


    // Changing the icons depending on the bullet's category and status
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
        if (bullet.isChecked() && bullet.getCategory() == BulletCategories.EVENT) {
            holder.bulletCategory.setImageResource(R.drawable.baseline_event_checked_24);
        }
        if (!bullet.isChecked() && bullet.getCategory() == BulletCategories.EVENT) {
            holder.bulletCategory.setImageResource(R.drawable.baseline_event_unchecked_24);
        }
        if (bullet.getCategory() == BulletCategories.NOTE) {
            holder.bulletCategory.setImageResource(R.drawable.baseline_remove_24);
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
        LinearLayout bulletLayout;
        public BulletViewHolder(@NonNull View itemView) {
            super(itemView);
            bulletText = itemView.findViewById(R.id.editText);
            bulletCategory = itemView.findViewById(R.id.imageButton);
            bulletLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}