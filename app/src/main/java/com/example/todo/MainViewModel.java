package com.example.todo;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private NotesDao notesDao;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(@NonNull Application application) {
        super(application);
        notesDao = NotesDB.getInstance(application).notesDao();
    }

    public LiveData<List<Note>> getNotes() {
        return notesDao.getNotes();
    }

//    public void refreshList() {
//        Disposable disposable = notesDao.getNotes()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<Note>>() {
//                    @Override
//                    public void accept(List<Note> notesFromDB) throws Throwable {
//                        notes.setValue(notesFromDB);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Throwable {
//                        Log.d("MainViewModel", "error refresh list");
//                    }
//                });
//        compositeDisposable.add(disposable);
//    }

//    private Single<List<Note>> getNotesRx() {
//        return Single.fromCallable(new Callable<List<Note>>() {
//            @Override
//            public List<Note> call() throws Exception {
//                return notesDao.getNotes();
//                throw new RuntimeException();
//            }
//        });
//    }


    public void removeNote(Note note) {
        Disposable disposable = notesDao.remove(note.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        Log.d("MainViewModel", "removed note " + note.getId());
//                        refreshList();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("MainViewModel", throwable.toString());
//                        refreshList();
                    }
                });
        compositeDisposable.add(disposable);
    }

//    private Completable removeRx(Note note) {
//        return Completable.fromAction(new Action() {
//            @Override
//            public void run() throws Throwable {
//                notesDao.remove(note.getId());
//                throw new RuntimeException();
//            }
//        });
//    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
