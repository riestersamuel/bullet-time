package de.hdmstuttgart.bulletjournalapp.NotePackage;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdmstuttgart.bulletjournalapp.Note;
import de.hdmstuttgart.bulletjournalapp.R;

public class NoteListAdapter extends RecyclerView.Adapter <NoteListAdapter.NoteViewHolder>{

	private final List<Note> NoteList;
	private onMovieClickListener listener;

	public interface onMovieClickListener {
		void onMovieClicked(Note note, int position);
	}

	public NoteListAdapter(
			List<Note> NoteList,
			onMovieClickListener listener
	){
		this.NoteList = NoteList;
		this.listener = listener;
	}

	public NoteListAdapter() {
		this.NoteList = new ArrayList<>();
	}

	@NonNull
	@Override
	public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.note_preview, parent,false);

		return new NoteViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
		Note note = NoteList.get(position);

		holder.titleNotePreview.setText(note.getTitle());
		holder.contentNotePreview.setText(note.getContent());

		holder.itemView.setOnClickListener(v -> {
			listener.onMovieClicked(note, position);
		});


	}

	@Override
	public int getItemCount() {
		return NoteList.size();
	}

	static class NoteViewHolder extends RecyclerView.ViewHolder {

		TextView titleNotePreview;
		TextView contentNotePreview;

		public NoteViewHolder(@NonNull View itemView){
			super(itemView);

			titleNotePreview = itemView.findViewById(R.id.titleNotePre);
			contentNotePreview = itemView.findViewById(R.id.contentTextNotePre);
		}
	}
}