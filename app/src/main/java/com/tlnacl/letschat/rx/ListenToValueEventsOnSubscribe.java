package com.tlnacl.letschat.rx;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.internal.subscriptions.BooleanSubscription;


class ListenToValueEventsOnSubscribe<T> implements Observable.OnSubscribe<T> {

    private final Query query;
    private final Function<DataSnapshot, T> marshaller;

    ListenToValueEventsOnSubscribe(Query query, Function<DataSnapshot, T> marshaller) {
        this.query = query;
        this.marshaller = marshaller;
    }

    @Override
    public void call(Subscriber<? super T> subscriber) {
        final ValueEventListener eventListener = query.addValueEventListener(new RxValueListener<>(subscriber, marshaller));
        subscriber.add(BooleanSubscription.create(new Action0() {
            @Override
            public void call() {
                query.removeEventListener(eventListener);
            }
        }));
    }

    private static class RxValueListener<T> implements ValueEventListener {

        private final Subscriber<? super T> subscriber;
        private final Function<DataSnapshot, T> marshaller;

        RxValueListener(Subscriber<? super T> subscriber, Function<DataSnapshot, T> marshaller) {
            this.subscriber = subscriber;
            this.marshaller = marshaller;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (!subscriber.isUnsubscribed()) {
                subscriber.onNext(marshaller.call(dataSnapshot));
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            subscriber.onError(databaseError.toException()); //TODO handle errors in pipeline
        }

    }

}
