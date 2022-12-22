package de.hdmstuttgart.bulletjournalapp.NotePackage;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import de.hdmstuttgart.bulletjournalapp.Note;
import de.hdmstuttgart.bulletjournalapp.R;

public class NoteListAdapter extends RecyclerView.Adapter <NoteListAdapter.NoteViewHolder>{

	private List<Note> NoteList;
	public List<Note> searchedNotes;
	private onNoteClickListener listener;
	private onNoteLongClickListener longListener;

	public interface onNoteClickListener {
		void onMovieClicked(Note note, int position);
	}

	public interface onNoteLongClickListener {
		void onNoteLongClicked(Note note, int position);
	}

	public NoteListAdapter(List<Note> NoteList, onNoteClickListener listener, onNoteLongClickListener longListener){
		this.NoteList = NoteList;
		this.listener = listener;
		this.longListener = longListener;
		this.searchedNotes = new ArrayList<>();
	}

	public NoteListAdapter() {
		this.NoteList = new ArrayList<>();
		this.searchedNotes = new ArrayList<>();
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

		holder.dateNotePreview.setText(note.getEditDateAsDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")).toString());
		holder.titleNotePreview.setText(note.getTitle());
		holder.contentNotePreview.setText(note.getContent());

		holder.itemView.setOnClickListener(v -> {
			listener.onMovieClicked(note, position);
		});

		holder.itemView.setOnLongClickListener(v -> {
			longListener.onNoteLongClicked(note, position);
			return true;
		});
	}

	@Override
	public int getItemCount() {
		return NoteList.size();
	}

	static class NoteViewHolder extends RecyclerView.ViewHolder {

		TextView dateNotePreview;
		TextView titleNotePreview;
		TextView contentNotePreview;

		public NoteViewHolder(@NonNull View itemView){
			super(itemView);

			dateNotePreview = itemView.findViewById(R.id.TV_Date);
			titleNotePreview = itemView.findViewById(R.id.titleNotePre);
			contentNotePreview = itemView.findViewById(R.id.contentTextNotePre);
		}
	}
}